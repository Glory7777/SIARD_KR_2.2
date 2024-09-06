package ch.admin.bar.siardsuite.service.database;

import ch.admin.bar.siard2.api.*;
import ch.admin.bar.siard2.api.generated.SchemaType;
import ch.admin.bar.siard2.api.generated.SchemasType;
import ch.admin.bar.siard2.api.generated.TableType;
import ch.admin.bar.siard2.api.generated.TablesType;
import ch.admin.bar.siard2.api.meta.MetaDataImpl;
import ch.admin.bar.siard2.api.meta.MetaSchemaImpl;
import ch.admin.bar.siard2.api.meta.MetaTableImpl;
import ch.admin.bar.siard2.api.primary.ArchiveImpl;
import ch.admin.bar.siard2.api.primary.SchemaImpl;
import ch.admin.bar.siard2.api.primary.TableImpl;
import ch.admin.bar.siardsuite.ui.presenter.archive.model.CustomArchiveProxy;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class TempSchemaImpl implements Schema {

    public static final String _sSCHEMA_FOLDER_PREFIX = "schema";
    private Archive _archiveParent = null;
    private MetaSchema _ms = null;
    private Map<String, Table> _mapTables = new HashMap();

    public Archive getParentArchive() {
        return this._archiveParent;
    }

    public MetaSchema getMetaSchema() {
        return this._ms;
    }

    public void registerTable(String sName, Table table) {
        this._mapTables.put(sName, table);
    }

    public boolean isEmpty() {
        boolean bEmpty = true;
        Iterator<String> iterTable = this._mapTables.keySet().iterator();

        while(iterTable.hasNext()) {
            String sTable = (String)iterTable.next();
            Table table = (Table)this._mapTables.get(sTable);
            if (!table.isEmpty()) {
                bEmpty = false;
            }
        }

        return bEmpty;
    }

    public boolean isValid() {
        boolean bValid = this.getMetaSchema().isValid();

        for(int iTable = 0; bValid && iTable < this.getTables(); ++iTable) {
            if (!this.getTable(iTable).isValid()) {
                bValid = false;
            }
        }

        return bValid;
    }

    String getSchemaFolder() {
        return ArchiveImpl.getContentFolder() + this.getMetaSchema().getFolder() + "/";
    }

    private TempSchemaImpl(Archive archiveParent, String sName) throws IOException {
        this._archiveParent = archiveParent;
        MetaDataImpl mdi = (MetaDataImpl)this.getParentArchive().getMetaData();
        SchemasType sts = mdi.getSiardArchive().getSchemas();
        SchemaType st = null;

        for(int iSchema = 0; st == null && iSchema < sts.getSchema().size(); ++iSchema) {
            SchemaType stTry = (SchemaType)sts.getSchema().get(iSchema);
            if (sName.equals(stTry.getName())) {
                st = stTry;
            }
        }

        ArchiveImpl ai = (ArchiveImpl)this.getParentArchive();
//        CustomArchiveProxy ai = (CustomArchiveProxy) this.getParentArchive();
        if (st == null) {
            String sFolder = "schema" + String.valueOf(sts.getSchema().size());
            ai.createFolderEntry(ArchiveImpl.getContentFolder() + sFolder + "/");
            st = MetaSchemaImpl.createSchemaType(sName, sFolder);
            sts.getSchema().add(st);
        }

        this._ms = MetaSchemaImpl.newInstance(this, st);
        ai.registerSchema(sName, this);
        TablesType tts = st.getTables();
        if (tts != null) {
            for(int iTable = 0; iTable < tts.getTable().size(); ++iTable) {
                TableType tt = (TableType)tts.getTable().get(iTable);
                CustomTableImpl.newInstance(this, tt.getName());
            }
        }

    }

    public static Schema newInstance(Archive archiveParent, String sName) throws IOException {
        return new TempSchemaImpl(archiveParent, sName);
    }

    public int getTables() {
        return this.getMetaSchema().getMetaTables();
    }

    public Table getTable(int iTable) {
        Table table = null;
        MetaTable mt = this.getMetaSchema().getMetaTable(iTable);
        if (mt != null) {
            table = this.getTable(mt.getName());
        }

        return table;
    }

    public Table getTable(String sName) {
        return (Table)this._mapTables.get(sName);
    }

    public Table createTable(String sName) throws IOException {
        Table table = null;
        if (this.getParentArchive().canModifyPrimaryData()) {
            if (this._mapTables.get(sName) != null) {
                throw new IOException("Table name must be unique within schema!");
            } else {
                table = TableImpl.newInstance(this, sName);
                MetaSchemaImpl msi = (MetaSchemaImpl)this.getMetaSchema();
                SchemaType stTemplate = msi.getTemplate();
                if (stTemplate != null) {
                    TablesType tts = stTemplate.getTables();
                    if (tts != null) {
                        TableType ttTemplate = null;

                        for(int iTable = 0; iTable < tts.getTable().size(); ++iTable) {
                            TableType ttTry = (TableType)tts.getTable().get(iTable);
                            if (sName.equals(ttTry.getName())) {
                                ttTemplate = ttTry;
                            }
                        }

                        if (ttTemplate != null) {
                            MetaTableImpl mti = (MetaTableImpl)table.getMetaTable();
                            mti.setTemplate(ttTemplate);
                        }
                    }
                }

                return table;
            }
        } else {
            throw new IOException("Table cannot be created!\r\nSIARD archive is not open for modification of primary data!");
        }
    }
}
