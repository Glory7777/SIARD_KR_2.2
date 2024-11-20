package ch.admin.bar.siard2.cmd;

import ch.admin.bar.dbexception.DatabaseExceptionHandlerHelper;
import ch.admin.bar.siard2.api.MetaAttribute;
import ch.admin.bar.siard2.api.MetaColumn;
import ch.admin.bar.siard2.api.MetaData;
import ch.admin.bar.siard2.api.MetaSchema;
import ch.admin.bar.siard2.api.MetaTable;
import ch.admin.bar.siard2.api.MetaType;
import ch.admin.bar.siard2.api.MetaUniqueKey;
import ch.admin.bar.siard2.api.generated.CategoryType;
import ch.enterag.sqlparser.SqlLiterals;
import ch.enterag.sqlparser.identifier.QualifiedId;
import ch.enterag.utils.background.Progress;
import ch.enterag.utils.jdbc.BaseDatabaseMetaData;

import java.io.IOException;
import java.sql.*;
import java.text.ParseException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static ch.admin.bar.dbexception.DatabaseExceptionHandlerHelper.doHandleSqlException;

public class MetaDataToDb extends MetaDataBase {
    private static final Logger LOG = LoggerFactory.getLogger(MetaDataToDb.class);


    private ArchiveMapping _am = null;

    public ArchiveMapping getArchiveMapping() {
        return this._am;
    }

    private int _iMaxTableNameLength = -1;
    private int _iMaxColumnNameLength = -1;
    private Progress _progress = null;
    private int _iTablesCreated = -1;
    private int _iTables = -1;
    private int _iTablesPercent = -1;


    private void incTablesCreated() {
        this._iTablesCreated++;
        if (this._progress != null && this._iTables > 0 && this._iTablesCreated % this._iTablesPercent == 0) {

            int iPercent = 100 * this._iTablesCreated / this._iTables;
            this._progress.notifyProgress(iPercent);
        }
    }


    private boolean cancelRequested() {
        if (this._progress != null && this._progress.cancelRequested()) {
            LOG.info("Cancel uploading of meta data because of request");
            return true;
        }
        return false;
    }


    private String createAttribute(MetaAttribute ma, TypeMapping tm) {
        StringBuilder sbSql = new StringBuilder(SqlLiterals.formatId(tm.getMappedAttributeName(ma.getName())));
        sbSql.append(" ");
        MetaType mt = ma.getMetaType();
        if (mt == null) {

            sbSql.append(ma.getType());
            if (ma.getCardinality() >= 0) {
                sbSql.append(" ARRAY[" + ma.getCardinality() + "]");
            }
        } else {

            SchemaMapping sm = this._am.getSchemaMapping(ma.getTypeSchema());

            QualifiedId qiType = new QualifiedId(null, sm.getMappedSchemaName(), sm.getMappedTypeName(ma.getTypeName()));
            sbSql.append(qiType.format());
        }
        return sbSql.toString();
    }


    private void createType(MetaType mt, SchemaMapping sm) throws SQLException {
        TypeMapping tm = sm.getTypeMapping(mt.getName());

        QualifiedId qiType = new QualifiedId(null, sm.getMappedSchemaName(), tm.getMappedTypeName());

        if (!existsType(qiType.getSchema(), qiType.getName())) {

            CategoryType cat = mt.getCategoryType();
            if (cat != CategoryType.DISTINCT || supportsDistincts()) {

                StringBuilder sbSql = new StringBuilder("CREATE TYPE " + qiType.format());
                sbSql.append(" AS ");
                if (cat == CategoryType.DISTINCT) {
                    sbSql.append(mt.getBase());
                } else {

                    sbSql.append("(");
                    for (int iAttribute = 0; iAttribute < mt.getMetaAttributes(); iAttribute++) {

                        if (iAttribute > 0)
                            sbSql.append(",\r\n");
                        MetaAttribute ma = mt.getMetaAttribute(iAttribute);
                        sbSql.append(createAttribute(ma, tm));
                    }
                    sbSql.append(")");
                    if (!mt.isFinal())
                        sbSql.append(" NOT");
                    sbSql.append(" FINAL\r\n");
                    if (!mt.isInstantiable())
                        sbSql.append(" NOT");
                    sbSql.append(" INSTANTIABLE");
                }
                String sqlStatement = sbSql.toString();
                LOG.trace("SQL statement: '{}'", sqlStatement);


                Statement stmt = this._dmd.getConnection().createStatement();
                stmt.setQueryTimeout(this._iQueryTimeoutSeconds);
                stmt.executeUpdate(sbSql.toString());
                stmt.close();

                LOG.debug("Type '{}.{}' successfully created", qiType.getSchema(), qiType.getName());
            }
        }
    }


    private void createTypes(MetaSchema ms, SchemaMapping sm) throws SQLException {
        if (supportsUdts() || supportsDistincts()) {
            for (int iType = 0; iType < ms.getMetaTypes(); iType++) {

                MetaType mt = ms.getMetaType(iType);
                CategoryType cat = mt.getCategoryType();
                if ((cat == CategoryType.DISTINCT && supportsDistincts()) || (cat == CategoryType.UDT &&
                        supportsUdts())) {
                    createType(mt, sm);
                }
            }
        }
    }


    private boolean existsType(String sMangledSchema, String sMangledType) throws SQLException {
        boolean bExists = false;
        ResultSet rs = this._dmd.getUDTs(null, ((BaseDatabaseMetaData) this._dmd)
                .toPattern(sMangledSchema), ((BaseDatabaseMetaData) this._dmd)
                .toPattern(sMangledType), new int[]{2002, 2001});

        while (rs.next())
            bExists = true;
        rs.close();
        return bExists;
    }


    private void dropTypes(MetaSchema ms, SchemaMapping sm) throws IOException, SQLException {
        if (supportsUdts()) {

            Set<String> setTypes = new HashSet<>();
            for (int iType = 0; iType < ms.getMetaTypes(); iType++) {

                MetaType mt = ms.getMetaType(iType);
                CategoryType cat = mt.getCategoryType();
                if (supportsDistincts() || cat != CategoryType.DISTINCT)
                    setTypes.add(mt.getName());
            }
            while (!setTypes.isEmpty()) {

                for (Iterator<String> iterType = setTypes.iterator(); iterType.hasNext(); ) {

                    String sTypeName = iterType.next();
                    TypeMapping tm = sm.getTypeMapping(sTypeName);
                    if (existsType(sm.getMappedSchemaName(), tm.getMappedTypeName())) {

                        MetaType mt = ms.getMetaType(sTypeName);
                        if (matchType(mt, sm)) {
                            iterType.remove();

                            continue;
                        }
                        QualifiedId qiType = new QualifiedId(null, sm.getMappedSchemaName(), tm.getMappedTypeName());
                        String sSql = "DROP TYPE " + qiType.format() + " RESTRICT";
                        LOG.trace("SQL statement: '{}'", sSql);

                        Statement stmt = this._dmd.getConnection().createStatement();
                        stmt.setQueryTimeout(this._iQueryTimeoutSeconds);


                        try {
                            stmt.executeUpdate(sSql);
                            iterType.remove();

                            LOG.info("Type '{}.{}' successfully dropped", qiType.getSchema(), qiType.getName());
                        } catch (SQLException sQLException) {
                        } finally {
                            stmt.close();
                        }

                        continue;
                    }
                    iterType.remove();
                }
            }
        }
    }


    private String createColumn(MetaColumn mc, TableMapping tm) throws IOException {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(SqlLiterals.formatId(tm.getMappedColumnName(mc.getName())));
        sbSql.append(" ");
        MetaType mt = mc.getMetaType();
        if (mt == null) {

            sbSql.append(mc.getType());
            if (mc.getCardinality() >= 0) {
                sbSql.append(" ARRAY[" + mc.getCardinality() + "]");
            }
        } else {

            CategoryType cat = mt.getCategoryType();
            if (cat == CategoryType.DISTINCT && !supportsDistincts()) {


                sbSql.append(mt.getBase());
            } else {

                SchemaMapping sm = this._am.getSchemaMapping(mc.getTypeSchema());

                QualifiedId qiType = new QualifiedId(null, sm.getMappedSchemaName(), sm.getMappedTypeName(mc.getTypeName()));
                sbSql.append(qiType.format());
            }
        }
        if (!mc.isNullable())
            sbSql.append(" NOT NULL");
        return sbSql.toString();
    }


    private Set<QualifiedId> getTables() throws SQLException {
        Set<QualifiedId> setTables = new HashSet<>();
        ResultSet rs = this._dmd.getTables(null, "%", "%", new String[]{"TABLE"});
        while (rs.next()) {

            String sCatalog = rs.getString("TABLE_CAT");
            String sSchema = rs.getString("TABLE_SCHEM");
            String sTable = rs.getString("TABLE_NAME");
            QualifiedId qiTable = new QualifiedId(sCatalog, sSchema, sTable);
            setTables.add(qiTable);
        }
        rs.close();
        return setTables;
    }


    private void createTable(MetaTable mt, SchemaMapping sm) throws IOException, SQLException {
        Set<QualifiedId> setBefore = getTables();
        TableMapping tm = sm.getTableMapping(mt.getName());
        QualifiedId qiTable = new QualifiedId(null, sm.getMappedSchemaName(), tm.getMappedTableName());
        StringBuilder sbSql = new StringBuilder("CREATE TABLE " + qiTable.format() + "(");
        List<List<String>> llColumnNames = mt.getColumnNames(supportsArrays(), supportsUdts());
        for (int iExtendedColumn = 0; iExtendedColumn < llColumnNames.size(); iExtendedColumn++) {

            List<String> listColumn = llColumnNames.get(iExtendedColumn);
            StringBuilder sbColumnName = new StringBuilder();
            for (int i = 0; i < listColumn.size(); i++) {

                if (i > 0)
                    sbColumnName.append(".");
                sbColumnName.append(listColumn.get(i));
            }
            if (iExtendedColumn > 0)
                sbSql.append(",\r\n");
            MetaColumn mc = mt.getMetaColumn(sbColumnName.toString());
            if (mc != null) {
                sbSql.append(createColumn(mc, tm));
            } else {

                String sMappedColumnName = tm.getMappedExtendedColumnName(sbColumnName.toString());
                sbSql.append(SqlLiterals.formatId(sMappedColumnName));
                sbSql.append(" ");
                sbSql.append(mt.getType(llColumnNames.get(iExtendedColumn)));
            }
        }

        MetaUniqueKey mpk = mt.getMetaPrimaryKey();
        if (mpk != null) {

            StringBuilder sbPrimaryKey = new StringBuilder();
            sbPrimaryKey.append("PRIMARY KEY(");
            for (int iColumn = 0; iColumn < mpk.getColumns(); iColumn++) {

                if (iColumn > 0)
                    sbPrimaryKey.append(",");
                String sMappedColumnName = tm.getMappedColumnName(mpk.getColumn(iColumn));
                sbPrimaryKey.append(SqlLiterals.formatId(sMappedColumnName));
            }
            sbPrimaryKey.append(")");
            sbSql.append(",\r\n");
            sbSql.append(sbPrimaryKey);
        }

        sbSql.append(")");


        String sqlStatement = sbSql.toString();
        LOG.trace("SQL statement: '{}'", sqlStatement);

        String databaseProductName = null;
        try {
            Connection connection = this._dmd.getConnection();
            Statement stmt = connection.createStatement();
            databaseProductName = connection.getMetaData().getDatabaseProductName();
            stmt.setQueryTimeout(this._iQueryTimeoutSeconds);
            stmt.executeUpdate(sqlStatement);
            stmt.close();
        } catch (Exception ex) {
            System.out.println("Failed SQL statement:\n" + sqlStatement);
            if (ex instanceof SQLException) {
                SQLException sqlException = (SQLException) ex;
                doHandleSqlException(databaseProductName, "createTable", sqlException);
            }
            throw ex;
        }


        Set<QualifiedId> setCreated = getTables();
        setCreated.removeAll(setBefore);
        if (!setCreated.contains(qiTable)) {

            for (Iterator<QualifiedId> iterCreated = setCreated.iterator(); iterCreated.hasNext(); )
                qiTable = iterCreated.next();
            sm.setMappedSchemaName(qiTable.getSchema());
            tm.setMappedTableName(qiTable.getName());
        }
        ResultSet rsColumns = this._dmd.getColumns(null, ((BaseDatabaseMetaData) this._dmd)
                .toPattern(qiTable.getSchema()), ((BaseDatabaseMetaData) this._dmd)
                .toPattern(qiTable.getName()), "%");

        while (rsColumns.next()) {

            String sMappedColumnName = rsColumns.getString("COLUMN_NAME");
            int iOrdinalPosition = rsColumns.getInt("ORDINAL_POSITION");
            List<String> listColumn = llColumnNames.get(iOrdinalPosition - 1);
            StringBuilder sbColumnName = new StringBuilder();
            for (int i = 0; i < listColumn.size(); i++) {

                if (i > 0)
                    sbColumnName.append(".");
                sbColumnName.append(listColumn.get(i));
            }
            String sExtendedColumnName = sbColumnName.toString();
            if (!sMappedColumnName.equals(tm.getMappedColumnName(sExtendedColumnName)))
                tm.putMappedExtendedColumnName(sExtendedColumnName, sMappedColumnName);
        }
        rsColumns.close();

        LOG.debug("Table '{}.{}' successfully created", sm.getMappedSchemaName(), tm.getMappedTableName());
    }


    private void createTables(MetaSchema ms, SchemaMapping sm) throws IOException, SQLException {
        for (int iTable = 0; iTable < ms.getMetaTables() && !cancelRequested(); iTable++) {

            MetaTable mt = ms.getMetaTable(iTable);
            QualifiedId qiTable = new QualifiedId(null, mt.getParentMetaSchema().getName(), mt.getName());
            System.out.println("  Table: " + qiTable.format());
            createTable(mt, sm);
            incTablesCreated();
        }
    }


    private boolean existsTable(String sMangledSchema, String sMangledTable) throws SQLException {
        boolean bExists = false;
        ResultSet rs = this._dmd.getTables(null, ((BaseDatabaseMetaData) this._dmd)
                .toPattern(sMangledSchema), ((BaseDatabaseMetaData) this._dmd)
                .toPattern(sMangledTable), new String[]{"TABLE"});

        if (rs.next())
            bExists = true;
        rs.close();
        return bExists;
    }


    private void dropTables(MetaSchema ms, SchemaMapping sm) throws SQLException {
        for (int iTable = 0; iTable < ms.getMetaTables(); iTable++) {

            String sTableName = ms.getMetaTable(iTable).getName();
            TableMapping tm = sm.getTableMapping(sTableName);
            Statement stmt = this._dmd.getConnection().createStatement();
            stmt.setQueryTimeout(this._iQueryTimeoutSeconds);
        }
    }


    private boolean existsSchema(String sMangledSchema) throws SQLException {
        boolean bExists = false;
        ResultSet rs = this._dmd.getSchemas(null, ((BaseDatabaseMetaData) this._dmd).toPattern(sMangledSchema));
        if (rs.next())
            bExists = true;
        rs.close();
        return bExists;
    }


    private void createSchema(MetaSchema ms, SchemaMapping sm) throws SQLException {
        if (!existsSchema(sm.getMappedSchemaName())) {

            String sSql = "CREATE SCHEMA \"" + sm.getMappedSchemaName() + "\"";
            LOG.trace("SQL statement: '{}'", sSql);
            Connection connection = this._dmd.getConnection();
            Statement stmt = connection.createStatement();
            String databaseProductName = connection.getMetaData().getDatabaseProductName();
            stmt.setQueryTimeout(this._iQueryTimeoutSeconds);

            try {
                stmt.executeUpdate(sSql);
                stmt.getConnection().commit();

                LOG.debug("Schema '{}' successfully created", sm.getMappedSchemaName());
            } catch (SQLException se) {

                LOG.error(
                        String.format("Can not create schema '%s' with SQL statement '%s'", sm.getMappedSchemaName(), sSql), se);

                stmt.getConnection().rollback();

                doHandleSqlException(databaseProductName, "createSchema", se);

                throw new SQLException(se.getMessage(), se.getCause());
            } finally {
                stmt.close();
            }
        }
    }


    public void upload(Progress progress) throws IOException, SQLException {
        LOG.info("Start meta data upload of archive {}", this._md
                .getArchive().getFile().getAbsoluteFile());

        System.out.println("Meta Data");
        this._progress = progress;

        this._iTables = 0;
        int iSchema;
        for (iSchema = 0; iSchema < this._md.getMetaSchemas(); iSchema++) {

            MetaSchema ms = this._md.getMetaSchema(iSchema);
            for (int iTable = 0; iTable < ms.getMetaTables(); iTable++)
                this._iTables++;
        }
        this._iTablesPercent = (this._iTables + 99) / 100;
        this._iTablesCreated = 0;
        for (iSchema = 0; iSchema < this._md.getMetaSchemas() && !cancelRequested(); iSchema++) {

            MetaSchema ms = this._md.getMetaSchema(iSchema);
            SchemaMapping sm = this._am.getSchemaMapping(ms.getName());
            createSchema(ms, sm);
        }
        for (iSchema = 0; iSchema < this._md.getMetaSchemas() && !cancelRequested(); iSchema++) {

            MetaSchema ms = this._md.getMetaSchema(iSchema);
            SchemaMapping sm = this._am.getSchemaMapping(ms.getName());
            if (existsSchema(sm.getMappedSchemaName())) {
                dropTables(ms, sm);
            } else {
                throw new SQLException("Schema \"" + sm.getMappedSchemaName() + "\" could not be created! Map \"" + ms
                        .getName() + "\" to existing schema.");
            }
        }
        for (iSchema = 0; iSchema < this._md.getMetaSchemas() && !cancelRequested(); iSchema++) {

            MetaSchema ms = this._md.getMetaSchema(iSchema);
            SchemaMapping sm = this._am.getSchemaMapping(ms.getName());
            if (existsSchema(sm.getMappedSchemaName())) {
                dropTypes(ms, sm);
            } else {
                throw new SQLException("Schema \"" + sm.getMappedSchemaName() + "\" could not be created! Map \"" + ms
                        .getName() + "\" to existing schema.");
            }
        }
        for (iSchema = 0; iSchema < this._md.getMetaSchemas() && !cancelRequested(); iSchema++) {

            MetaSchema ms = this._md.getMetaSchema(iSchema);
            SchemaMapping sm = this._am.getSchemaMapping(ms.getName());
            if (existsSchema(sm.getMappedSchemaName())) {
                createTypes(ms, sm);
            } else {
                throw new SQLException("Schema \"" + sm.getMappedSchemaName() + "\" could not be created! Map \"" + ms
                        .getName() + "\" to existing schema.");
            }
        }
        for (iSchema = 0; iSchema < this._md.getMetaSchemas() && !cancelRequested(); iSchema++) {

            MetaSchema ms = this._md.getMetaSchema(iSchema);
            SchemaMapping sm = this._am.getSchemaMapping(ms.getName());
            if (existsSchema(sm.getMappedSchemaName())) {
                createTables(ms, sm);
            } else {
                throw new SQLException("Schema \"" + sm.getMappedSchemaName() + "\" could not be created! Map \"" + ms
                        .getName() + "\" to existing schema.");
            }
        }
        if (cancelRequested())
            throw new IOException("Upload of meta data cancelled!");
        this._dmd.getConnection().commit();

        LOG.info("Meta data upload finished");
    }


    public int tablesDroppedByUpload() throws SQLException {
        int iTablesDropped = 0;
        for (int iSchema = 0; iSchema < this._md.getMetaSchemas(); iSchema++) {

            MetaSchema ms = this._md.getMetaSchema(iSchema);
            SchemaMapping sm = this._am.getSchemaMapping(ms.getName());
            for (int iTable = 0; iTable < ms.getMetaTables(); iTable++) {

                MetaTable mt = ms.getMetaTable(iTable);
                if (existsTable(sm.getMappedSchemaName(), sm.getMappedTableName(mt.getName())))
                    iTablesDropped++;
            }
        }
        return iTablesDropped;
    }


    private boolean matchAttributes(MetaType mt, SchemaMapping sm) throws IOException, SQLException {
        boolean bMatches = true;
        TypeMapping tm = sm.getTypeMapping(mt.getName());
        int iPosition = 0;
        ResultSet rs = this._dmd.getAttributes(null, ((BaseDatabaseMetaData) this._dmd)
                .toPattern(sm.getMappedSchemaName()), ((BaseDatabaseMetaData) this._dmd)
                .toPattern(tm.getMappedTypeName()), "%");
        while (bMatches && rs.next()) {

            iPosition++;
            String sTypeSchema = rs.getString("TYPE_SCHEM");
            if (!sTypeSchema.equals(this._am.getMappedSchemaName(mt.getParentMetaSchema().getName())))
                throw new IOException("Attribute with unexpected type schema found!");
            String sTypeName = rs.getString("TYPE_NAME");
            if (!sTypeName.equals(sm.getMappedTypeName(mt.getName())))
                throw new IOException("Attribute with unexpected type name found");
            String sAttributeName = rs.getString("ATTR_NAME");


            MetaAttribute ma = null;
            for (int iAttribute = 0; ma == null && iAttribute < mt.getMetaAttributes(); iAttribute++) {

                MetaAttribute maTemp = mt.getMetaAttribute(iAttribute);
                if (sAttributeName.equals(tm.getMappedAttributeName(maTemp.getName())))
                    ma = maTemp;
            }
            if (ma != null) {

                int iDataType = rs.getInt("DATA_TYPE");
                String sAttrTypeName = rs.getString("ATTR_TYPE_NAME");
                if (iDataType != 2001 && iDataType != 2003 && iDataType != 2002) {


                    if (iDataType != ma.getPreType())
                        bMatches = false;
                    continue;
                }
                if (iDataType == 2003) {


                    Matcher m = MetaDataFromDb._patARRAY_CONSTRUCTOR.matcher(sTypeName);
                    if (m.matches()) {

                        if (!ma.getType().equals(m.group(1)))
                            bMatches = false;
                        if (ma.getCardinality() != Integer.parseInt(m.group(2)))
                            bMatches = false;
                        continue;
                    }
                    throw new SQLException("Invalid ARRAY constructor for attribute " + ma.getName() + " of type " + mt.getName() + "!");
                }


                try {
                    QualifiedId qiAttrType = new QualifiedId(sAttrTypeName);
                    if (qiAttrType.getSchema() == null) {
                        qiAttrType.setSchema(sm.getMappedSchemaName());
                    }
                    MetaSchema msAttr = null;
                    SchemaMapping smAttr = null;
                    for (int iSchema = 0; msAttr == null && iSchema < this._md.getMetaSchemas(); iSchema++) {

                        MetaSchema msTemp = this._md.getMetaSchema(iSchema);
                        SchemaMapping smTemp = this._am.getSchemaMapping(msTemp.getName());
                        if (smTemp.getMappedSchemaName().equals(qiAttrType.getSchema())) {

                            msAttr = msTemp;
                            smAttr = smTemp;
                        }
                    }
                    if (msAttr != null) {


                        MetaType mtAttr = null;
                        for (int iType = 0; mtAttr == null && iType < msAttr.getMetaTypes(); iType++) {

                            MetaType mtTemp = msAttr.getMetaType(iType);
                            if (smAttr.getMappedTypeName(mtTemp.getName()).equals(qiAttrType.getName()))
                                mtAttr = mtTemp;
                        }
                        if (mtAttr != null) {
                            bMatches = matchType(mtAttr, smAttr);
                            continue;
                        }
                        bMatches = false;
                        continue;
                    }
                    bMatches = false;
                } catch (ParseException pe) {
                    throw new SQLException("Type of attribute " + ma.getName() + " of type " + mt.getName() + " could not be parsed!", pe);
                }
                continue;
            }
            bMatches = false;
        }
        rs.close();
        if (iPosition < mt.getMetaAttributes())
            bMatches = false;
        return bMatches;
    }


    private boolean matchType(MetaType mt, SchemaMapping sm) throws IOException, SQLException {
        boolean bMatches = true;
        TypeMapping tm = sm.getTypeMapping(mt.getName());
        CategoryType cat = mt.getCategoryType();
        int iDataType = 2002;
        if (cat == CategoryType.DISTINCT)
            iDataType = 2001;
        ResultSet rs = this._dmd.getUDTs(null, ((BaseDatabaseMetaData) this._dmd)
                .toPattern(sm.getMappedSchemaName()), ((BaseDatabaseMetaData) this._dmd)
                .toPattern(tm.getMappedTypeName()), null);
        while (rs.next()) {


            if (iDataType == rs.getInt("DATA_TYPE")) {

                if (iDataType == 2001) {

                    int iBaseType = rs.getInt("BASE_TYPE");
                    if (iBaseType != mt.getBasePreType()) {
                        bMatches = false;
                    }
                    continue;
                }
                if (!matchAttributes(mt, sm)) {
                    bMatches = false;
                }
                continue;
            }
            bMatches = false;
        }
        rs.close();
        return bMatches;
    }


    public int typesDroppedByUpload() throws IOException, SQLException {
        int iTypesDropped = 0;
        if (supportsUdts()) {
            for (int iSchema = 0; iSchema < this._md.getMetaSchemas(); iSchema++) {

                MetaSchema ms = this._md.getMetaSchema(iSchema);
                SchemaMapping sm = this._am.getSchemaMapping(ms.getName());
                for (int iType = 0; iType < ms.getMetaTypes(); iType++) {

                    MetaType mt = ms.getMetaType(iType);
                    if (!matchType(mt, sm))
                        iTypesDropped++;
                }
            }
        }
        return iTypesDropped;
    }


    private MetaDataToDb(DatabaseMetaData dmd, MetaData md, Map<String, String> mapSchemas) throws IOException, SQLException {
        super(dmd, md);
        dmd.getConnection().setAutoCommit(false);
        this._iMaxTableNameLength = this._dmd.getMaxTableNameLength();
        this._iMaxColumnNameLength = this._dmd.getMaxColumnNameLength();
        this._am = ArchiveMapping.newInstance(supportsArrays(), supportsUdts(), mapSchemas, this._md, this._iMaxTableNameLength, this._iMaxColumnNameLength);
    }


    public static MetaDataToDb newInstance(DatabaseMetaData dmd, MetaData md, Map<String, String> mapSchemas) throws IOException, SQLException {
        return new MetaDataToDb(dmd, md, mapSchemas);
    }
}