package ch.admin.bar.siard2.api.primary;

import ch.admin.bar.siard2.api.Archive;
import ch.admin.bar.siard2.api.MetaSchema;
import ch.admin.bar.siard2.api.MetaTable;
import ch.admin.bar.siard2.api.Schema;
import ch.admin.bar.siard2.api.Table;
import ch.admin.bar.siard2.api.generated.SchemaType;
import ch.admin.bar.siard2.api.generated.SchemasType;
import ch.admin.bar.siard2.api.generated.TableType;
import ch.admin.bar.siard2.api.generated.TablesType;
import ch.admin.bar.siard2.api.meta.MetaDataImpl;
import ch.admin.bar.siard2.api.meta.MetaSchemaImpl;
import ch.admin.bar.siard2.api.meta.MetaTableImpl;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;


public class SchemaImpl implements Schema {
    public static final String _sSCHEMA_FOLDER_PREFIX = "schema";
    private Archive _archiveParent = null;
    public Archive getParentArchive() {
        return this._archiveParent;
    }
    private MetaSchema _ms = null;
    public MetaSchema getMetaSchema() {
        return this._ms;
    }
    private final Map<String, Table> _mapTables = new HashMap<>();

    private long schemaSize;

    public void registerTable(String sName, Table table) {
        this._mapTables.put(sName, table);
    }

    public boolean isEmpty() {
        boolean bEmpty = true;
        for (Iterator<String> iterTable = this._mapTables.keySet().iterator(); iterTable.hasNext(); ) {

            String sTable = iterTable.next();
            Table table = this._mapTables.get(sTable);
            if (!table.isEmpty())
                bEmpty = false;
        }
        return bEmpty;
    }


    public boolean isValid() {
        boolean bValid = getMetaSchema().isValid();
        for (int iTable = 0; bValid && iTable < getTables(); iTable++) {

            if (!getTable(iTable).isValid())
                bValid = false;
        }
        return bValid;
    }


    String getSchemaFolder() {
        return ArchiveImpl.getContentFolder() + getMetaSchema().getFolder() + "/";
    }


    private SchemaImpl(Archive archiveParent, String sName) throws IOException {
        this._archiveParent = archiveParent;
        MetaDataImpl mdi = (MetaDataImpl) getParentArchive().getMetaData();
        SchemasType sts = mdi.getSiardArchive().getSchemas();
        SchemaType st = null;
        for (int iSchema = 0; st == null && iSchema < sts.getSchema().size(); iSchema++) {

            SchemaType stTry = sts.getSchema().get(iSchema);
            if (sName.equals(stTry.getName()))
                st = stTry;
        }
        ArchiveImpl ai = (ArchiveImpl) getParentArchive();
        if (st == null) {

            String sFolder = "schema" + sts.getSchema().size();
//      ai.createFolderEntry(ArchiveImpl.getContentFolder() + sFolder + "/");
            st = MetaSchemaImpl.createSchemaType(sName, sFolder);
            sts.getSchema().add(st);
        }
        this._ms = MetaSchemaImpl.newInstance(this, st);
        ai.registerSchema(sName, this);

        TablesType tts = st.getTables();
        if (tts != null) {
            for (int iTable = 0; iTable < tts.getTable().size(); iTable++) {

                TableType tt = tts.getTable().get(iTable);
                TableImpl.newInstance(this, tt.getName());
            }
        }
    }


    public static Schema newInstance(Archive archiveParent, String sName) throws IOException {
        return new SchemaImpl(archiveParent, sName);
    }


    public int getTables() {
        return getMetaSchema().getMetaTables();
    }


    public Table getTable(int iTable) {
        Table table = null;
        MetaTable mt = getMetaSchema().getMetaTable(iTable);
        if (mt != null)
            table = getTable(mt.getName());
        return table;
    }


    public Table getTable(String sName) {
        return this._mapTables.get(sName);
    }


    public Table createTable(String sName) throws IOException {
        Table table = null;
        if (getParentArchive().canModifyPrimaryData()) {

            if (this._mapTables.get(sName) == null) {


                table = TableImpl.newInstance(this, sName);

                MetaSchemaImpl msi = (MetaSchemaImpl) getMetaSchema();
                SchemaType stTemplate = msi.getTemplate();
                if (stTemplate != null) {

                    TablesType tts = stTemplate.getTables();
                    if (tts != null) {

                        TableType ttTemplate = null;
                        for (int iTable = 0; iTable < tts.getTable().size(); iTable++) {

                            TableType ttTry = tts.getTable().get(iTable);
                            if (sName.equals(ttTry.getName()))
                                ttTemplate = ttTry;
                        }
                        if (ttTemplate != null) {

                            MetaTableImpl mti = (MetaTableImpl) table.getMetaTable();
                            mti.setTemplate(ttTemplate);
                        }
                    }
                }
            } else {

                throw new IOException("Table name must be unique within schema!");
            }
        } else {
            throw new IOException("Table cannot be created!\r\nSIARD archive is not open for modification of primary data!");
        }
        return table;
    }


    /**
     * 선택한 테이블로 교체
     *
     * @param selectedTableNameList
     */
    @Override
    public void replaceWithSelectedTables(List<String> selectedTableNameList) {
        replaceTables(
                selectedTableNameList.stream()
                        .collect(
                                Collectors.toMap(
                                        tableName -> tableName,
                                        tableName -> _mapTables.get(tableName)
                                )
                        )
        );
    }

    private void replaceTables(Map<String, Table> newTableMap) {
        _mapTables.clear();
        _mapTables.putAll(newTableMap);
    }

    @Override
    public long getRecordCount() {
        return _mapTables.values().stream().mapToLong(table -> table.getMetaTable().getRows()).sum();
    }

    @Override
    public Collection<Table> getSelectedTables() {
        return _mapTables.values();
    }

    @Override
    public long getSchemaSize() {
        return this.schemaSize;
    }

    @Override
    public void addTableSize(long tableSize) {
        this.schemaSize += tableSize;
    }

}