package ch.admin.bar.siard2.jdbc;

import ch.enterag.sqlparser.SqlLiterals;
import ch.enterag.utils.jdbc.BaseDatabaseMetaData;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class MySqlDatabaseMetaData extends BaseDatabaseMetaData implements DatabaseMetaData {

    private MySqlConnection _conn = null;

    public MySqlDatabaseMetaData(DatabaseMetaData dmdWrapped, MySqlConnection conn) {
        super(dmdWrapped);
        this._conn = conn;
    }


    public Connection getConnection() throws SQLException {
        return this._conn;
    }


    public ResultSet getBestRowIdentifier(String catalog, String schema, String table, int scope, boolean nullable) throws SQLException {
        if (catalog != null) {
            throw new SQLException("MySQL does not support catalogs, argument must be NULL.");
        }
        return super.getBestRowIdentifier(schema, catalog, table, scope, nullable);
    }


    public ResultSet getVersionColumns(String catalog, String schema, String table) throws SQLException {
        ResultSet rs = null;
        if (catalog == null) {
            rs = super.getVersionColumns(schema, catalog, table);
        } else {
            throw new SQLException("MySQL does not support catalogs, argument must be NULL.");
        }
        return rs;
    }


    public boolean supportsResultSetConcurrency(int type, int concurrency) throws SQLException {
        return super.supportsResultSetConcurrency(type, concurrency);
    }


    public ResultSet getIndexInfo(String catalog, String schema, String table, boolean unique, boolean approximate) throws SQLException {
        if (catalog != null) {
            throw new SQLException("MySQL does not support catalogs, argument must be NULL.");
        }

        StringBuilder sb = new StringBuilder();
        sb.append("SELECT \r\n\t NULL AS TABLE_CAT,\r\n    TABLE_SCHEMA AS TABLE_SCHEM,\r\n    TABLE_NAME,\r\n    NON_UNIQUE,\r\n    INDEX_SCHEMA AS INDEX_QUALIFIER,\r\n    INDEX_NAME,\r\n    CASE INDEX_TYPE\r\n      WHEN 'BTREE' THEN " +


                String.valueOf(1) + "\r\n      WHEN 'HASH' THEN " +
                String.valueOf(2) + "\r\n      ELSE " +
                String.valueOf(3) + " END AS TYPE,\r\n    SEQ_IN_INDEX AS ORDINAL_POSITION,\r\n    COLUMN_NAME,\r\n    'A' AS ASC_OR_DESC,\r\n    CARDINALITY,\r\n    NULL AS PAGES,\r\n    NULL  AS FILTER_CONDITION\r\nFROM INFORMATION_SCHEMA.STATISTICS\r\n");


        ArrayList<String> whereClauseComponents = new ArrayList<>();
        if (schema != null) {
            whereClauseComponents.add("TABLE_SCHEMA LIKE " + SqlLiterals.formatStringLiteral(schema));
        }
        if (table != null) {
            whereClauseComponents.add("TABLE_NAME LIKE " + SqlLiterals.formatStringLiteral(table));
        }
        if (unique) {
            whereClauseComponents.add("NON_UNIQUE = 0");
        }


        if (whereClauseComponents.size() != 0) {
            StringBuilder sbWhereClause = new StringBuilder();
            for (int i = 0; i < whereClauseComponents.size(); i++) {
                if (i > 0) {
                    sbWhereClause.append(" AND ");
                }
                sbWhereClause.append(whereClauseComponents.get(i));
            }
            sb.append("WHERE ");
            sb.append(sbWhereClause.toString());
            sb.append("\r\n");
        }


        sb.append("ORDER BY NON_UNIQUE, INDEX_TYPE, INDEX_NAME, ORDINAL_POSITION");

        Statement stmt = getConnection().createStatement();
        return ((Statement) stmt.<Statement>unwrap(Statement.class)).executeQuery(sb.toString());
    }


    public ResultSet getColumnPrivileges(String catalog, String schema, String table, String columnNamePattern) throws SQLException {
        if (catalog != null) {
            throw new SQLException("MySQL does not support catalogs, argument must be NULL.");
        }
        return super.getColumnPrivileges(schema, catalog, table, columnNamePattern);
    }


    public ResultSet getTablePrivileges(String catalog, String schemaPattern, String tableNamePattern) throws SQLException {
        if (catalog != null) {
            throw new SQLException("MySQL does not support catalogs, argument must be NULL.");
        }

        StringBuilder sb = new StringBuilder();
        sb.append("SELECT\r\n\tNULL AS TABLE_CATALOG,\r\n    TABLE_SCHEMA AS TABLE_SCHEM,\r\n    TABLE_NAME,\r\n    'root' AS GRANTOR,\r\n    REPLACE(GRANTEE,'''','') AS GRANTEE,\r\n    PRIVILEGE_TYPE AS PRIVILEGE,\r\n    IS_GRANTABLE\r\nFROM INFORMATION_SCHEMA.TABLE_PRIVILEGES\r\n");


        ArrayList<String> whereClauseComponents = new ArrayList<>();
        if (schemaPattern != null) {
            whereClauseComponents.add("TABLE_SCHEMA LIKE " + SqlLiterals.formatStringLiteral(schemaPattern));
        }
        if (tableNamePattern != null) {
            whereClauseComponents.add("TABLE_NAME LIKE " + SqlLiterals.formatStringLiteral(tableNamePattern));
        }

        if (whereClauseComponents.size() != 0) {
            StringBuilder sbWhereClause = new StringBuilder();
            for (int i = 0; i < whereClauseComponents.size(); i++) {
                if (i > 0) {
                    sbWhereClause.append(" AND ");
                }
                sbWhereClause.append(whereClauseComponents.get(i));
            }
            sb.append("WHERE ");
            sb.append(sbWhereClause.toString());
            sb.append("\r\n");
        }


        sb.append("ORDER BY TABLE_CATALOG, TABLE_SCHEMA, TABLE_NAME, PRIVILEGE");

        Statement stmt = getConnection().createStatement();
        return ((Statement) stmt.<Statement>unwrap(Statement.class)).executeQuery(sb.toString());
    }


    public ResultSet getPrimaryKeys(String catalog, String schema, String table) throws SQLException {
        if (catalog != null) throw new SQLException("MySQL does not support catalogs, argument must be NULL.");
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT\r\n  NULL AS TABLE_CAT,\r\n  TABLE_SCHEMA AS TABLE_SCHEM,\r\n  TABLE_NAME AS TABLE_NAME,\r\n  COLUMN_NAME AS COLUMN_NAME,\r\n  CASE WHEN POSITION_IN_UNIQUE_CONSTRAINT IS NULL THEN ORDINAL_POSITION ELSE POSITION_IN_UNIQUE_CONSTRAINT END AS KEY_SEQ,\r\n  CONSTRAINT_NAME AS PK_NAME\r\nFROM INFORMATION_SCHEMA.KEY_COLUMN_USAGE\r\nWHERE CONSTRAINT_NAME = 'PRIMARY'\r\n");


        StringBuilder sbCondition = new StringBuilder();
        sbCondition.append(getNameCondition("TABLE_SCHEMA", schema));
        sbCondition.append(getNameCondition("TABLE_NAME", table));
        if (sbCondition.length() > 0) {

            sb.append(sbCondition.toString());
            sbCondition.append("\r\n");
        }

        sb.append("ORDER BY COLUMN_NAME");
        Statement stmt = getConnection().createStatement();
        return ((Statement) stmt.<Statement>unwrap(Statement.class)).executeQuery(sb.toString());
    }


    public ResultSet getAttributes(String catalog, String schemaPattern, String typeNamePattern, String attributeNamePattern) throws SQLException {
        if (catalog != null) {
            throw new SQLException("MySQL does not support catalogs, argument must be NULL.");
        }
        return super.getAttributes(schemaPattern, catalog, typeNamePattern, attributeNamePattern);
    }


    private String getNameCondition(String sColumn, String sName) {
        StringBuilder sbCondition = new StringBuilder();
        if (sName != null) {

            sbCondition.append("  AND ");
            if (sName.startsWith("\"")) {

                sbCondition.append(sColumn);
                sbCondition.append(" = ");
                sbCondition.append(SqlLiterals.formatStringLiteral(sName));
            } else {

                sbCondition.append("LOWER(");
                sbCondition.append(sColumn);
                sbCondition.append(") = LOWER(");
                sbCondition.append(SqlLiterals.formatStringLiteral(sName));
                sbCondition.append(")");
            }
            sbCondition.append("\r\n");
        }
        return sbCondition.toString();
    }


    public ResultSet getCrossReference(String parentCatalog, String parentSchema, String parentTable, String foreignCatalog, String foreignSchema, String foreignTable) throws SQLException {
        if (parentCatalog != null || foreignCatalog != null) {
            throw new SQLException("MySQL does not support catalogs, argument must be NULL.");
        }


        String sSql = "SELECT\r\n  NULL AS PKTABLE_CAT,\r\n  TCPK.TABLE_SCHEMA AS PKTABLE_SCHEM,\r\n  TCPK.TABLE_NAME AS PKTABLE_NAME,\r\n  KCU.REFERENCED_COLUMN_NAME AS PKCOLUMN_NAME,\r\n  NULL AS FKTABLE_CAT,\r\n  TCFK.TABLE_SCHEMA AS FKTABLE_SCHEM,\r\n  TCFK.TABLE_NAME AS FKTABLE_NAME,\r\n  KCU.COLUMN_NAME AS FKCOLUMN_NAME,\r\n  KCU.ORDINAL_POSITION AS KEY_SEQ,\r\n  CASE RC.UPDATE_RULE\r\n    WHEN 'NO ACTION' THEN " + String.valueOf(3) + "\r\n    WHEN 'CASCADE' THEN " + String.valueOf(0) + "\r\n    WHEN 'SET NULL' THEN " + String.valueOf(2) + "\r\n    WHEN 'SET DEFAULT' THEN " + String.valueOf(4) + "\r\n    WHEN 'RESTRICT' THEN " + String.valueOf(1) + "\r\n  END AS UPDATE_RULE,\r\n  CASE RC.DELETE_RULE\r\n    WHEN 'NO ACTION' THEN " + String.valueOf(3) + "\r\n    WHEN 'CASCADE' THEN " + String.valueOf(0) + "\r\n    WHEN 'SET NULL' THEN " + String.valueOf(2) + "\r\n    WHEN 'SET DEFAULT' THEN " + String.valueOf(4) + "\r\n    WHEN 'RESTRICT' THEN " + String.valueOf(1) + "\r\n  END AS DELETE_RULE,\r\n  TCFK.CONSTRAINT_NAME AS FK_NAME,\r\n  TCPK.CONSTRAINT_NAME AS PK_NAME,\r\n  " + String.valueOf(7) + " AS DEFERRABILITY\r\nFROM INFORMATION_SCHEMA.REFERENTIAL_CONSTRAINTS RC\r\n  INNER JOIN INFORMATION_SCHEMA.TABLE_CONSTRAINTS TCFK\r\n    ON (RC.CONSTRAINT_SCHEMA = TCFK.CONSTRAINT_SCHEMA AND\r\n        RC.CONSTRAINT_NAME = TCFK.CONSTRAINT_NAME AND\r\n        TCFK.CONSTRAINT_TYPE = 'FOREIGN KEY')\r\n  INNER JOIN INFORMATION_SCHEMA.TABLE_CONSTRAINTS TCPK\r\n    ON (RC.UNIQUE_CONSTRAINT_SCHEMA = TCPK.CONSTRAINT_SCHEMA AND\r\n        RC.UNIQUE_CONSTRAINT_NAME = TCPK.CONSTRAINT_NAME)\r\n  INNER JOIN INFORMATION_SCHEMA.KEY_COLUMN_USAGE KCU\r\n    ON (RC.CONSTRAINT_SCHEMA = KCU.CONSTRAINT_SCHEMA AND\r\n        RC.CONSTRAINT_NAME = KCU.CONSTRAINT_NAME AND\r\n        NOT KCU.REFERENCED_COLUMN_NAME IS NULL)\r\n";


        StringBuilder sbCondition = new StringBuilder();
        if (parentSchema != null) {

            if (sbCondition.length() > 0) sbCondition.append(" AND ");
            sbCondition.append("LOWER(TCPK.TABLE_SCHEMA) = " + SqlLiterals.formatStringLiteral(parentSchema.toLowerCase()));
        }
        if (parentTable != null) {

            if (sbCondition.length() > 0) sbCondition.append(" AND ");
            sbCondition.append("LOWER(TCPK.TABLE_NAME) LIKE " + SqlLiterals.formatStringLiteral(parentTable.toLowerCase()));
        }
        if (foreignSchema != null) {

            if (sbCondition.length() > 0) sbCondition.append(" AND ");
            sbCondition.append("LOWER(TCFK.TABLE_SCHEMA) = " + SqlLiterals.formatStringLiteral(foreignSchema.toLowerCase()));
        }
        if (foreignTable != null) {

            if (sbCondition.length() > 0) sbCondition.append(" AND ");
            sbCondition.append("LOWER(TCFK.TABLE_NAME) LIKE " + SqlLiterals.formatStringLiteral(foreignTable.toLowerCase()));
        }
        if (sbCondition.length() > 0) {
            sSql = sSql + "WHERE " + sbCondition + "\r\nORDER BY FKTABLE_SCHEM, FKTABLE_NAME, KEY_SEQ";
        }

        Statement stmt = getConnection().createStatement();
        return ((Statement) stmt.<Statement>unwrap(Statement.class)).executeQuery(sSql);
    }


    public ResultSet getImportedKeys(String catalog, String schema, String table) throws SQLException {
        if (catalog != null) throw new SQLException("MySQL does not support catalogs, argument must be NULL.");
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT\r\n  NULL AS PKTABLE_CAT,\r\n  KCU.REFERENCED_TABLE_SCHEMA AS PKTABLE_SCHEM,\r\n  KCU.REFERENCED_TABLE_NAME AS PKTABLE_NAME,\r\n  KCU.REFERENCED_COLUMN_NAME AS PKCOLUMN_NAME,\r\n  NULL AS FKTABLE_CAT,\r\n  KCU.TABLE_SCHEMA AS FKTABLE_SCHEM,\r\n  KCU.TABLE_NAME AS FKTABLE_NAME,\r\n  KCU.COLUMN_NAME AS FKCOLUMN_NAME,\r\n  CASE WHEN KCU.POSITION_IN_UNIQUE_CONSTRAINT IS NULL THEN KCU.ORDINAL_POSITION ELSE KCU.POSITION_IN_UNIQUE_CONSTRAINT END AS KEY_SEQ,\r\n  CASE RC.UPDATE_RULE\r\n      WHEN 'NO ACTION' THEN " +


                String.valueOf(3) + "\r\n      WHEN 'CASCADE' THEN " +
                String.valueOf(0) + "\r\n      WHEN 'SET NULL' THEN " +
                String.valueOf(2) + "\r\n      WHEN 'SET DEFAULT' THEN " +
                String.valueOf(4) + "\r\n      WHEN 'RESTRICT' THEN " +
                String.valueOf(1) + "\r\n    END AS UPDATE_RULE,\r\n  CASE RC.DELETE_RULE\r\n      WHEN 'NO ACTION' THEN " +


                String.valueOf(3) + "\r\n      WHEN 'CASCADE' THEN " +
                String.valueOf(0) + "\r\n      WHEN 'SET NULL' THEN " +
                String.valueOf(2) + "\r\n      WHEN 'SET DEFAULT' THEN " +
                String.valueOf(4) + "\r\n      WHEN 'RESTRICT' THEN " +
                String.valueOf(1) + "\r\n    END AS DELETE_RULE,\r\n  KCU.CONSTRAINT_NAME AS FK_NAME,\r\n  RC.UNIQUE_CONSTRAINT_NAME AS PK_NAME,\r\n  " +


                String.valueOf(7) + " AS DEFERRABILITY\r\nFROM INFORMATION_SCHEMA.KEY_COLUMN_USAGE KCU\r\n INNER JOIN INFORMATION_SCHEMA.REFERENTIAL_CONSTRAINTS RC\r\n ON(KCU.CONSTRAINT_NAME = RC.CONSTRAINT_NAME\r\n   AND KCU.CONSTRAINT_SCHEMA = RC.CONSTRAINT_SCHEMA\r\n   AND KCU.CONSTRAINT_CATALOG = RC.CONSTRAINT_CATALOG)\r\nWHERE NOT KCU.REFERENCED_COLUMN_NAME IS NULL\r\n");


        StringBuilder sbCondition = new StringBuilder();
        sbCondition.append(getNameCondition("KCU.TABLE_SCHEMA", schema));
        sbCondition.append(getNameCondition("KCU.TABLE_NAME", table));
        if (sbCondition.length() > 0) {

            sb.append(sbCondition.toString());
            sbCondition.append("\r\n");
        }

        sb.append("ORDER BY KCU.REFERENCED_TABLE_SCHEMA, KCU.REFERENCED_TABLE_NAME, KCU.POSITION_IN_UNIQUE_CONSTRAINT");
        Statement stmt = getConnection().createStatement();
        return ((Statement) stmt.<Statement>unwrap(Statement.class)).executeQuery(sb.toString());
    }


    public ResultSet getExportedKeys(String catalog, String schema, String table) throws SQLException {
        if (catalog != null) throw new SQLException("MySQL does not support catalogs, argument must be NULL.");
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT\r\n  NULL AS PKTABLE_CAT,\r\n  KCU.REFERENCED_TABLE_SCHEMA AS PKTABLE_SCHEM,\r\n  KCU.REFERENCED_TABLE_NAME AS PKTABLE_NAME,\r\n  KCU.REFERENCED_COLUMN_NAME AS PKCOLUMN_NAME,\r\n  NULL AS FKTABLE_CAT,\r\n  KCU.TABLE_SCHEMA AS FKTABLE_SCHEM,\r\n  KCU.TABLE_NAME AS FKTABLE_NAME,\r\n  KCU.COLUMN_NAME AS FKCOLUMN_NAME,\r\n  CASE WHEN KCU.POSITION_IN_UNIQUE_CONSTRAINT IS NULL THEN KCU.ORDINAL_POSITION ELSE KCU.POSITION_IN_UNIQUE_CONSTRAINT END AS KEY_SEQ,\r\n  CASE RC.UPDATE_RULE\r\n      WHEN 'NO ACTION' THEN " +


                String.valueOf(3) + "\r\n      WHEN 'CASCADE' THEN " +
                String.valueOf(0) + "\r\n      WHEN 'SET NULL' THEN " +
                String.valueOf(2) + "\r\n      WHEN 'SET DEFAULT' THEN " +
                String.valueOf(4) + "\r\n      WHEN 'RESTRICT' THEN " +
                String.valueOf(1) + "\r\n    END AS UPDATE_RULE,\r\n  CASE RC.DELETE_RULE\r\n      WHEN 'NO ACTION' THEN " +


                String.valueOf(3) + "\r\n      WHEN 'CASCADE' THEN " +
                String.valueOf(0) + "\r\n      WHEN 'SET NULL' THEN " +
                String.valueOf(2) + "\r\n      WHEN 'SET DEFAULT' THEN " +
                String.valueOf(4) + "\r\n      WHEN 'RESTRICT' THEN " +
                String.valueOf(1) + "\r\n    END AS DELETE_RULE,\r\n  KCU.CONSTRAINT_NAME AS FK_NAME,\r\n  'PRIMARY' AS PK_NAME,\r\n  " +


                String.valueOf(7) + " AS DEFERRABILITY\r\nFROM INFORMATION_SCHEMA.KEY_COLUMN_USAGE KCU\r\n INNER JOIN INFORMATION_SCHEMA.REFERENTIAL_CONSTRAINTS RC\r\n ON(KCU.CONSTRAINT_NAME = RC.CONSTRAINT_NAME\r\n   AND KCU.CONSTRAINT_SCHEMA = RC.CONSTRAINT_SCHEMA\r\n   AND KCU.CONSTRAINT_CATALOG = RC.CONSTRAINT_CATALOG)\r\nWHERE NOT KCU.REFERENCED_COLUMN_NAME IS NULL\r\n");


        StringBuilder sbCondition = new StringBuilder();
        sbCondition.append(getNameCondition("KCU.REFERENCED_TABLE_SCHEMA", schema));
        sbCondition.append(getNameCondition("KCU.REFERENCED_TABLE_NAME", table));
        if (sbCondition.length() > 0) {

            sb.append(sbCondition.toString());
            sbCondition.append("\r\n");
        }

        sb.append("ORDER BY KCU.TABLE_SCHEMA, KCU.TABLE_NAME, KCU.POSITION_IN_UNIQUE_CONSTRAINT");
        Statement stmt = getConnection().createStatement();
        return ((Statement) stmt.<Statement>unwrap(Statement.class)).executeQuery(sb.toString());
    }


    public ResultSet getFunctionColumns(String catalog, String schemaPattern, String functionNamePattern, String columnNamePattern) throws SQLException {
        if (catalog != null) {
            throw new SQLException("MySQL does not support catalogs, argument must be NULL.");
        }

        StringBuilder sb = new StringBuilder();
        sb.append("SELECT  \r\n\tNULL AS FUNCTION_CAT, \r\n    P.SPECIFIC_SCHEMA AS FUNCTION_SCHEM, \r\n    P.SPECIFIC_NAME AS FUNCTION_NAME, \r\n    P.PARAMETER_NAME AS COLUMN_NAME, \r\n    CASE \r\n\t\t WHEN P.PARAMETER_MODE = 'OUT' THEN 4 \r\n        WHEN P.PARAMETER_MODE = 'INOUT' THEN 2 \r\n        WHEN P.PARAMETER_MODE = 'IN' THEN 1 \r\n        WHEN P.PARAMETER_MODE = NULL THEN 5 \r\n\tELSE 0 END AS COLUMN_TYPE, \r\n    -1 AS DATA_TYPE, \r\n    P.DATA_TYPE AS TYPE_NAME, \r\n    P.NUMERIC_PRECISION AS 'PRECISION', \r\n    P.CHARACTER_MAXIMUM_LENGTH AS LENGTH, \r\n    P.NUMERIC_SCALE AS SCALE, \r\n    10 AS RADIX, \r\n    0 AS NULLABLE, \r\n    NULL AS REMARKS, \r\n    P.CHARACTER_OCTET_LENGTH AS CHAR_OCTET_LENGTH, \r\n    P.ORDINAL_POSITION, \r\n    '' AS IS_NULLABLE, \r\n    P.SPECIFIC_NAME \r\nFROM INFORMATION_SCHEMA.PARAMETERS P, INFORMATION_SCHEMA.ROUTINES R\r\n");


        ArrayList<String> whereClauseComponents = new ArrayList<>();
        whereClauseComponents.add("R.ROUTINE_TYPE = 'FUNCTION'");
        whereClauseComponents.add("R.ROUTINE_SCHEMA = P.SPECIFIC_SCHEMA");
        whereClauseComponents.add("R.ROUTINE_NAME = P.SPECIFIC_NAME");
        if (schemaPattern != null) {
            whereClauseComponents.add("R.ROUTINE_SCHEMA LIKE " + SqlLiterals.formatStringLiteral(schemaPattern));
        }
        if (schemaPattern != null) {
            whereClauseComponents.add("R.ROUTINE_NAME LIKE " + SqlLiterals.formatStringLiteral(functionNamePattern));
        }
        if (schemaPattern != null) {
            whereClauseComponents.add("P.PARAMETER_NAME LIKE " + SqlLiterals.formatStringLiteral(columnNamePattern));
        }

        StringBuilder sbWhereClause = new StringBuilder();
        for (int i = 0; i < whereClauseComponents.size(); i++) {
            if (i > 0) {
                sbWhereClause.append(" AND ");
            }
            sbWhereClause.append(whereClauseComponents.get(i));
        }
        sb.append("WHERE ");
        sb.append(sbWhereClause.toString());
        sb.append("\r\n");


        sb.append("ORDER BY FUNCTION_CAT, FUNCTION_SCHEM, FUNCTION_NAME, SPECIFIC_NAME, ORDINAL_POSITION");

        Statement stmt = getConnection().createStatement();
        return new MySqlMetaColumns(((Statement) stmt.<Statement>unwrap(Statement.class)).executeQuery(sb.toString()), 6, 7, 8, 9, this._conn);
    }


    public ResultSet getProcedureColumns(String catalog, String schemaPattern, String procedureNamePattern, String columnNamePattern) throws SQLException {
        if (catalog != null) {
            throw new SQLException("MySQL does not support catalogs, argument must be NULL.");
        }

        StringBuilder sb = new StringBuilder();
        sb.append("SELECT  \r\n\tNULL AS PROCEDURE_CAT, \r\n    P.SPECIFIC_SCHEMA AS PROCEDURE_SCHEM, \r\n    P.SPECIFIC_NAME AS PROCEDURE_NAME, \r\n    P.PARAMETER_NAME AS COLUMN_NAME, \r\n    CASE \r\n\t\t WHEN P.PARAMETER_MODE = 'OUT' THEN 4 \r\n        WHEN P.PARAMETER_MODE = 'INOUT' THEN 2 \r\n        WHEN P.PARAMETER_MODE = 'IN' THEN 1 \r\n        WHEN P.PARAMETER_MODE = NULL THEN 5 \r\n\tELSE 0 END AS COLUMN_TYPE, \r\n    -1 AS DATA_TYPE, \r\n    P.DATA_TYPE AS TYPE_NAME, \r\n    P.NUMERIC_PRECISION AS 'PRECISION', \r\n    P.CHARACTER_MAXIMUM_LENGTH AS LENGTH, \r\n    P.NUMERIC_SCALE AS SCALE, \r\n    10 AS RADIX, \r\n    0 AS NULLABLE, \r\n    NULL AS REMARKS, \r\n    P.CHARACTER_OCTET_LENGTH AS CHAR_OCTET_LENGTH, \r\n    P.ORDINAL_POSITION, \r\n    '' AS IS_NULLABLE, \r\n    P.SPECIFIC_NAME \r\nFROM INFORMATION_SCHEMA.PARAMETERS P, INFORMATION_SCHEMA.ROUTINES R\r\n");


        ArrayList<String> whereClauseComponents = new ArrayList<>();
        whereClauseComponents.add("R.ROUTINE_TYPE = 'PROCEDURE'");
        whereClauseComponents.add("R.ROUTINE_SCHEMA = P.SPECIFIC_SCHEMA");
        whereClauseComponents.add("R.ROUTINE_NAME = P.SPECIFIC_NAME");
        if (schemaPattern != null) {
            whereClauseComponents.add("R.ROUTINE_SCHEMA LIKE " + SqlLiterals.formatStringLiteral(schemaPattern));
        }
        if (schemaPattern != null) {
            whereClauseComponents.add("R.ROUTINE_NAME LIKE " + SqlLiterals.formatStringLiteral(procedureNamePattern));
        }
        if (schemaPattern != null) {
            whereClauseComponents.add("P.PARAMETER_NAME LIKE " + SqlLiterals.formatStringLiteral(columnNamePattern));
        }

        StringBuilder sbWhereClause = new StringBuilder();
        for (int i = 0; i < whereClauseComponents.size(); i++) {
            if (i > 0) {
                sbWhereClause.append(" AND ");
            }
            sbWhereClause.append(whereClauseComponents.get(i));
        }
        sb.append("WHERE ");
        sb.append(sbWhereClause.toString());
        sb.append("\r\n");


        sb.append("ORDER BY PROCEDURE_CAT, PROCEDURE_SCHEM, PROCEDURE_NAME, SPECIFIC_NAME, ORDINAL_POSITION");

        Statement stmt = getConnection().createStatement();
        return new MySqlMetaColumns(((Statement) stmt.<Statement>unwrap(Statement.class)).executeQuery(sb.toString()), 6, 7, 8, 9, this._conn);
    }


    public ResultSet getFunctions(String catalog, String schemaPattern, String functionNamePattern) throws SQLException {
        if (catalog != null) {
            throw new SQLException("MySQL does not support catalogs, argument must be NULL.");
        }

        StringBuilder sb = new StringBuilder();

        sb.append("SELECT \r\n\t NULL AS FUNCTION_CAT,\r\n    ROUTINE_SCHEMA AS FUNCTION_SCHEM,\r\n    ROUTINE_NAME AS FUNCTION_NAME,\r\n    ROUTINE_COMMENT AS REMARKS,\r\n    0 AS FUNCTION_TYPE,\r\n    SPECIFIC_NAME\r\nFROM INFORMATION_SCHEMA.ROUTINES\r\n");


        ArrayList<String> whereClauseComponents = new ArrayList<>();
        whereClauseComponents.add("ROUTINE_TYPE = 'FUNCTION'");
        if (schemaPattern != null) {
            whereClauseComponents.add("ROUTINE_SCHEMA LIKE " + SqlLiterals.formatStringLiteral(schemaPattern));
        }
        if (schemaPattern != null) {
            whereClauseComponents.add("ROUTINE_NAME LIKE " + SqlLiterals.formatStringLiteral(functionNamePattern));
        }

        StringBuilder sbWhereClause = new StringBuilder();
        for (int i = 0; i < whereClauseComponents.size(); i++) {
            if (i > 0) {
                sbWhereClause.append(" AND ");
            }
            sbWhereClause.append(whereClauseComponents.get(i));
        }
        sb.append("WHERE ");
        sb.append(sbWhereClause.toString());
        sb.append("\r\n");


        sb.append("ORDER BY FUNCTION_CAT, FUNCTION_SCHEM, FUNCTION_NAME, SPECIFIC_NAME");

        Statement stmt = getConnection().createStatement();
        return ((Statement) stmt.<Statement>unwrap(Statement.class)).executeQuery(sb.toString());
    }


    public ResultSet getProcedures(String catalog, String schemaPattern, String procedureNamePattern) throws SQLException {
        if (catalog != null) {
            throw new SQLException("MySQL does not support catalogs, argument must be NULL.");
        }

        StringBuilder sb = new StringBuilder();

        sb.append("SELECT \r\n\t NULL AS PROCEDURE_CAT,\r\n    ROUTINE_SCHEMA AS PROCEDURE_SCHEM,\r\n    ROUTINE_NAME AS PROCEDURE_NAME,\r\n    NULL AS reserved1,\r\n    NULL AS reserved2,\r\n    NULL AS reserved3,\r\n    ROUTINE_COMMENT AS REMARKS,\r\n    0 AS PROCEDURE_TYPE,\r\n    SPECIFIC_NAME\r\nFROM INFORMATION_SCHEMA.ROUTINES\r\n");


        ArrayList<String> whereClauseComponents = new ArrayList<>();
        whereClauseComponents.add("ROUTINE_TYPE = 'PROCEDURE'");
        if (schemaPattern != null) {
            whereClauseComponents.add("ROUTINE_SCHEMA LIKE " + SqlLiterals.formatStringLiteral(schemaPattern));
        }
        if (schemaPattern != null) {
            whereClauseComponents.add("ROUTINE_NAME LIKE " + SqlLiterals.formatStringLiteral(procedureNamePattern));
        }

        StringBuilder sbWhereClause = new StringBuilder();
        for (int i = 0; i < whereClauseComponents.size(); i++) {
            if (i > 0) {
                sbWhereClause.append(" AND ");
            }
            sbWhereClause.append(whereClauseComponents.get(i));
        }
        sb.append("WHERE ");
        sb.append(sbWhereClause.toString());
        sb.append("\r\n");


        sb.append("ORDER BY PROCEDURE_CAT, PROCEDURE_SCHEM, PROCEDURE_NAME, SPECIFIC_NAME");

        Statement stmt = getConnection().createStatement();
        return ((Statement) stmt.<Statement>unwrap(Statement.class)).executeQuery(sb.toString());
    }


    public ResultSet getSchemas() throws SQLException {
        String sSql = "SELECT SCHEMA_NAME AS TABLE_SCHEM, NULL AS TABLE_CATALOG FROM INFORMATION_SCHEMA.SCHEMATA";
        Statement stmt = getConnection().createStatement();
        return ((Statement) stmt.<Statement>unwrap(Statement.class)).executeQuery(sSql);
    }


    public ResultSet getSchemas(String catalog, String schemaPattern) throws SQLException {
        if (catalog != null) {
            throw new SQLException("MySQL does not support catalogs, argument must be NULL.");
        }

        StringBuilder sb = new StringBuilder("SELECT\r\n\tSCHEMA_NAME AS TABLE_SCHEM,\r\n NULL AS TABLE_CATALOG\r\nFROM\r\n\tINFORMATION_SCHEMA.SCHEMATA\r\n");


        ArrayList<String> whereClauseComponents = new ArrayList<>();
        if (schemaPattern != null) {
            whereClauseComponents.add("SCHEMA_NAME LIKE " + SqlLiterals.formatStringLiteral(schemaPattern));
        }

        StringBuilder sbWhereClause = new StringBuilder();
        for (int i = 0; i < whereClauseComponents.size(); i++) {
            if (i > 0) {
                sbWhereClause.append(" AND ");
            }
            sbWhereClause.append(whereClauseComponents.get(i));
        }
        sb.append("WHERE ");
        sb.append(sbWhereClause.toString());
        sb.append("\r\n");


        sb.append("ORDER BY TABLE_CATALOG, TABLE_SCHEM");

        Statement stmt = getConnection().createStatement();
        return ((Statement) stmt.<Statement>unwrap(Statement.class)).executeQuery(sb.toString());
    }


    public ResultSet getSuperTables(String catalog, String schemaPattern, String tableNamePattern) throws SQLException {
        if (catalog != null) {
            throw new SQLException("MySQL does not support catalogs, argument must be NULL.");
        }
        return super.getSuperTables(schemaPattern, catalog, tableNamePattern);
    }


    public ResultSet getSuperTypes(String catalog, String schemaPattern, String typeNamePattern) throws SQLException {
        if (catalog != null) {
            throw new SQLException("MySQL does not support catalogs, argument must be NULL.");
        }
        return super.getSuperTypes(schemaPattern, catalog, typeNamePattern);
    }


    public ResultSet getColumns(String catalog, String schemaPattern, String tableNamePattern, String columnNamePattern) throws SQLException {
        if (catalog != null) {
            throw new SQLException("MySQL does not support catalogs, argument must be NULL.");
        }
        StringBuilder sb = new StringBuilder();


        sb.append("SELECT\r\n  NULL AS TABLE_CAT,\r\n  TABLE_SCHEMA AS TABLE_SCHEM,\r\n  TABLE_NAME AS TABLE_NAME,\r\n  COLUMN_NAME AS COLUMN_NAME,\r\n  0 as DATA_TYPE,\r\n  CASE\r\n    WHEN COLUMN_TYPE LIKE '%unsigned%' AND DATA_TYPE <> 'set' THEN CONCAT(DATA_TYPE, ' unsigned')\r\n    ELSE DATA_TYPE\r\n  END AS TYPE_NAME,\r\n  CASE\r\n    WHEN NUMERIC_PRECISION IS NOT NULL THEN NUMERIC_PRECISION\r\n    WHEN CHARACTER_MAXIMUM_LENGTH IS NOT NULL THEN CHARACTER_MAXIMUM_LENGTH\r\n    WHEN DATA_TYPE = 'date' THEN 10\r\n    WHEN DATA_TYPE = 'time' THEN 8\r\n    WHEN DATA_TYPE = 'datetime' OR DATA_TYPE = 'timestamp' THEN 19\r\n    WHEN DATA_TYPE = 'year' THEN 4\r\n    ELSE NULL\r\n  END AS COLUMN_SIZE,\r\n  NULL AS BUFFER_LENGTH,\r\n  NUMERIC_SCALE AS DECIMAL_DIGITS,\r\n  10 AS NUM_PREC_RADIX,\r\n  CASE IS_NULLABLE\r\n    WHEN 'NO' THEN " +


                String.valueOf(0) + "\r\n    WHEN 'YES' THEN " +
                String.valueOf(1) + "\r\n    ELSE " +
                String.valueOf(2) + "\r\n  END AS NULLABLE,\r\n  COLUMN_COMMENT AS REMARKS,\r\n  COLUMN_DEFAULT AS COLUMN_DEF,\r\n  NULL AS SQL_DATA_TYPE,\r\n  NULL AS SQL_DATETIME_SUB,\r\n  CHARACTER_OCTET_LENGTH AS CHAR_OCTET_LENGTH,\r\n  ORDINAL_POSITION AS ORDINAL_POSITION,\r\n  IS_NULLABLE AS IS_NULLABLE,\r\n  NULL AS SCOPE_CATALOG,\r\n  NULL AS SCOPE_SCHEMA,\r\n  NULL AS SCOPE_TABLE,\r\n  NULL AS SOURCE_DATA_TYPE,\r\n  CASE\r\n    WHEN EXTRA LIKE '%auto_increment%' THEN 1\r\n    ELSE 0\r\n  END AS IS_AUTOINCREMENT,\r\n  CASE\r\n    WHEN EXTRA LIKE '%generated%' THEN 1\r\n    ELSE 0\r\n  END AS IS_GENERATED\r\nFROM INFORMATION_SCHEMA.COLUMNS\r\n");


        ArrayList<String> whereClauseComponents = new ArrayList<>();
        if (schemaPattern != null)
            whereClauseComponents.add("TABLE_SCHEMA LIKE " + SqlLiterals.formatStringLiteral(schemaPattern));
        if (tableNamePattern != null)
            whereClauseComponents.add("TABLE_NAME LIKE " + SqlLiterals.formatStringLiteral(tableNamePattern));
        if (columnNamePattern != null) {
            whereClauseComponents.add("COLUMN_NAME LIKE " + SqlLiterals.formatStringLiteral(columnNamePattern));
        }
        if (whereClauseComponents.size() != 0) {

            StringBuilder sbWhereClause = new StringBuilder();
            for (int i = 0; i < whereClauseComponents.size(); i++) {

                if (i > 0)
                    sbWhereClause.append(" AND ");
                sbWhereClause.append(whereClauseComponents.get(i));
            }
            sb.append("WHERE ");
            sb.append(sbWhereClause.toString());
            sb.append("\r\n");
        }


        sb.append("ORDER BY TABLE_CAT, TABLE_SCHEM, TABLE_NAME, ORDINAL_POSITION");

        Statement stmt = getConnection().createStatement();
        return new MySqlMetaColumns(((Statement) stmt.<Statement>unwrap(Statement.class)).executeQuery(sb.toString()), 5, 6, 7, 7, this._conn);
    }


    public ResultSet getTables(String catalog, String schemaPattern, String tableNamePattern, String[] types) throws SQLException {
        if (catalog != null) {
            throw new SQLException("MySQL does not support catalogs, argument must be NULL.");
        }

        StringBuilder sb = new StringBuilder();


        sb.append("SELECT\r\n  NULL AS TABLE_CAT,\r\n  t.TABLE_SCHEMA as TABLE_SCHEM,\r\n  t.TABLE_NAME,\r\n  CASE t.TABLE_TYPE\r\n      WHEN 'BASE TABLE' THEN 'TABLE'\r\n      ELSE t.TABLE_TYPE\r\n  END AS TABLE_TYPE,\r\n  t.TABLE_COMMENT AS REMARKS,\r\n  NULL AS TYPE_CAT,\r\n  NULL AS TYPE_SCHEM,\r\n  NULL AS TYPE_NAME,\r\n  NULL AS SELF_REFERENCING_COL_NAME,\r\n  NULL AS REF_GENERATION,\r\n  v.VIEW_DEFINITION AS QUERY_TEXT\r\nFROM information_schema.TABLES t\r\n  LEFT JOIN information_schema.VIEWS v\r\n    ON(t.TABLE_CATALOG = v.TABLE_CATALOG AND t.TABLE_SCHEMA = v.TABLE_SCHEMA AND t.TABLE_NAME = v.TABLE_NAME)\r\n");


        ArrayList<String> whereClauseComponents = new ArrayList<>();


        whereClauseComponents.add("t.TABLE_SCHEMA NOT LIKE 'performance_schema'");

        if (schemaPattern != null) {
            whereClauseComponents.add("t.TABLE_SCHEMA LIKE " + SqlLiterals.formatStringLiteral(schemaPattern));
        }
        if (tableNamePattern != null) {
            whereClauseComponents.add("t.TABLE_NAME LIKE " + SqlLiterals.formatStringLiteral(tableNamePattern));
        }


        if (types != null) {
            String sTypeList = "";
            for (int i = 0; i < types.length; i++) {
                if (types[i].equals("TABLE")) {
                    sTypeList = sTypeList + SqlLiterals.formatStringLiteral("BASE TABLE");
                } else {
                    sTypeList = sTypeList + SqlLiterals.formatStringLiteral(types[i]);
                }
                if (i != types.length - 1) {
                    sTypeList = sTypeList + ",";
                }
            }
            whereClauseComponents.add("t.TABLE_TYPE IN (" + sTypeList + ")");
        }

        if (whereClauseComponents.size() != 0) {
            StringBuilder sbWhereClause = new StringBuilder();
            for (int i = 0; i < whereClauseComponents.size(); i++) {
                if (i > 0) {
                    sbWhereClause.append(" AND ");
                }
                sbWhereClause.append(whereClauseComponents.get(i));
            }
            sb.append("WHERE ");
            sb.append(sbWhereClause.toString());
            sb.append("\r\n");
        }


        sb.append("ORDER BY t.TABLE_TYPE, t.TABLE_CATALOG, t.TABLE_SCHEMA, t.TABLE_NAME");

        Statement stmt = getConnection().createStatement();
        return ((Statement) stmt.<Statement>unwrap(Statement.class)).executeQuery(sb.toString());
    }


    public ResultSet getUDTs(String catalog, String schemaPattern, String typeNamePattern, int[] types) throws SQLException {
        if (catalog != null) {
            throw new SQLException("MySQL does not support catalogs, argument must be NULL.");
        }
        return super.getUDTs(schemaPattern, catalog, typeNamePattern, types);
    }
}


/* Location:              C:\Users\lenovo\IdeaProjects\siard-lib\lib\jdbcmysql-2.2.2.jar!\ch\admin\bar\siard2\jdbc\MySqlDatabaseMetaData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */