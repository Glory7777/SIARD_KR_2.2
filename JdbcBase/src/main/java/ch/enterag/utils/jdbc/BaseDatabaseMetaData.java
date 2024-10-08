package ch.enterag.utils.jdbc;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.RowIdLifetime;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;


public abstract class BaseDatabaseMetaData implements DatabaseMetaData {

    public static final String _sQUERY_TEXT = "QUERY_TEXT";

    public String toPattern(String sIdentifier) throws SQLException {
        String sPattern = sIdentifier;
        if (sPattern != null) {
            if (!sPattern.isEmpty()) {

                sPattern = sPattern.replace("%", getSearchStringEscape() + "%").replace("_", getSearchStringEscape() + "_");
            } else {

                sPattern = "%";
            }
        }
        return sPattern;
    }


    private DatabaseMetaData _dmdWrapped = null;

    private void throwUndefinedMethod(AbstractMethodError ame) throws SQLFeatureNotSupportedException {
        throw new SQLFeatureNotSupportedException("Undefined JDBC method!", ame);
    }

    public BaseDatabaseMetaData(DatabaseMetaData dmdWrapped) {
        this._dmdWrapped = dmdWrapped;
    }

    public Connection getConnection() throws SQLException {
        return this._dmdWrapped.getConnection();
    }

    public boolean allProceduresAreCallable() throws SQLException {
        return this._dmdWrapped.allProceduresAreCallable();
    }

    public boolean allTablesAreSelectable() throws SQLException {
        return this._dmdWrapped.allTablesAreSelectable();
    }

    public String getURL() throws SQLException {
        return this._dmdWrapped.getURL();
    }

    public String getUserName() throws SQLException {
        return this._dmdWrapped.getUserName();
    }

    public boolean isReadOnly() throws SQLException {
        return this._dmdWrapped.isReadOnly();
    }

    public boolean nullsAreSortedHigh() throws SQLException {
        return this._dmdWrapped.nullsAreSortedHigh();
    }

    public boolean nullsAreSortedLow() throws SQLException {
        return this._dmdWrapped.nullsAreSortedLow();
    }

    public boolean nullsAreSortedAtStart() throws SQLException {
        return this._dmdWrapped.nullsAreSortedAtStart();
    }

    public boolean nullsAreSortedAtEnd() throws SQLException {
        return this._dmdWrapped.nullsAreSortedAtEnd();
    }

    public String getDatabaseProductName() throws SQLException {
        return this._dmdWrapped.getDatabaseProductName();
    }

    public String getDatabaseProductVersion() throws SQLException {
        return this._dmdWrapped.getDatabaseProductVersion();
    }

    public String getDriverName() throws SQLException {
        return this._dmdWrapped.getDriverName();
    }

    public String getDriverVersion() throws SQLException {
        return this._dmdWrapped.getDriverVersion();
    }

    public int getDriverMajorVersion() {
        return this._dmdWrapped.getDriverMajorVersion();
    }

    public int getDriverMinorVersion() {
        return this._dmdWrapped.getDriverMinorVersion();
    }

    public boolean usesLocalFiles() throws SQLException {
        return this._dmdWrapped.usesLocalFiles();
    }

    public boolean usesLocalFilePerTable() throws SQLException {
        return this._dmdWrapped.usesLocalFilePerTable();
    }

    public boolean supportsMixedCaseIdentifiers() throws SQLException {
        return this._dmdWrapped.supportsMixedCaseIdentifiers();
    }

    public boolean storesUpperCaseIdentifiers() throws SQLException {
        return this._dmdWrapped.storesUpperCaseIdentifiers();
    }

    public boolean storesLowerCaseIdentifiers() throws SQLException {
        return this._dmdWrapped.storesLowerCaseIdentifiers();
    }

    public boolean storesMixedCaseIdentifiers() throws SQLException {
        return this._dmdWrapped.storesMixedCaseIdentifiers();
    }

    public boolean supportsMixedCaseQuotedIdentifiers() throws SQLException {
        return this._dmdWrapped.supportsMixedCaseQuotedIdentifiers();
    }

    public boolean storesUpperCaseQuotedIdentifiers() throws SQLException {
        return this._dmdWrapped.storesUpperCaseQuotedIdentifiers();
    }

    public boolean storesLowerCaseQuotedIdentifiers() throws SQLException {
        return this._dmdWrapped.storesLowerCaseQuotedIdentifiers();
    }

    public boolean storesMixedCaseQuotedIdentifiers() throws SQLException {
        return this._dmdWrapped.storesMixedCaseQuotedIdentifiers();
    }

    public String getIdentifierQuoteString() throws SQLException {
        return this._dmdWrapped.getIdentifierQuoteString();
    }

    public String getSQLKeywords() throws SQLException {
        return this._dmdWrapped.getSQLKeywords();
    }


    public String getNumericFunctions() throws SQLException {
        return this._dmdWrapped.getNumericFunctions();
    }


    public String getStringFunctions() throws SQLException {
        return this._dmdWrapped.getStringFunctions();
    }


    public String getSystemFunctions() throws SQLException {
        return this._dmdWrapped.getSystemFunctions();
    }


    public String getTimeDateFunctions() throws SQLException {
        return this._dmdWrapped.getTimeDateFunctions();
    }


    public String getSearchStringEscape() throws SQLException {
        return this._dmdWrapped.getSearchStringEscape();
    }


    public String getExtraNameCharacters() throws SQLException {
        return this._dmdWrapped.getExtraNameCharacters();
    }


    public boolean supportsAlterTableWithAddColumn() throws SQLException {
        return this._dmdWrapped.supportsAlterTableWithAddColumn();
    }


    public boolean supportsAlterTableWithDropColumn() throws SQLException {
        return this._dmdWrapped.supportsAlterTableWithDropColumn();
    }


    public boolean supportsColumnAliasing() throws SQLException {
        return this._dmdWrapped.supportsColumnAliasing();
    }


    public boolean nullPlusNonNullIsNull() throws SQLException {
        return this._dmdWrapped.nullPlusNonNullIsNull();
    }


    public boolean supportsConvert() throws SQLException {
        return this._dmdWrapped.supportsConvert();
    }


    public boolean supportsConvert(int fromType, int toType) throws SQLException {
        return this._dmdWrapped.supportsConvert(fromType, toType);
    }


    public boolean supportsTableCorrelationNames() throws SQLException {
        return this._dmdWrapped.supportsTableCorrelationNames();
    }


    public boolean supportsDifferentTableCorrelationNames() throws SQLException {
        return this._dmdWrapped.supportsDifferentTableCorrelationNames();
    }


    public boolean supportsExpressionsInOrderBy() throws SQLException {
        return this._dmdWrapped.supportsExpressionsInOrderBy();
    }


    public boolean supportsOrderByUnrelated() throws SQLException {
        return this._dmdWrapped.supportsOrderByUnrelated();
    }


    public boolean supportsGroupBy() throws SQLException {
        return this._dmdWrapped.supportsGroupBy();
    }


    public boolean supportsGroupByUnrelated() throws SQLException {
        return this._dmdWrapped.supportsGroupByUnrelated();
    }


    public boolean supportsGroupByBeyondSelect() throws SQLException {
        return this._dmdWrapped.supportsGroupByBeyondSelect();
    }


    public boolean supportsLikeEscapeClause() throws SQLException {
        return this._dmdWrapped.supportsLikeEscapeClause();
    }


    public boolean supportsMultipleResultSets() throws SQLException {
        return this._dmdWrapped.supportsMultipleResultSets();
    }


    public boolean supportsMultipleTransactions() throws SQLException {
        return this._dmdWrapped.supportsMultipleTransactions();
    }


    public boolean supportsNonNullableColumns() throws SQLException {
        return this._dmdWrapped.supportsNonNullableColumns();
    }


    public boolean supportsMinimumSQLGrammar() throws SQLException {
        return this._dmdWrapped.supportsMinimumSQLGrammar();
    }


    public boolean supportsCoreSQLGrammar() throws SQLException {
        return this._dmdWrapped.supportsCoreSQLGrammar();
    }


    public boolean supportsExtendedSQLGrammar() throws SQLException {
        return this._dmdWrapped.supportsExtendedSQLGrammar();
    }


    public boolean supportsANSI92EntryLevelSQL() throws SQLException {
        return this._dmdWrapped.supportsANSI92EntryLevelSQL();
    }


    public boolean supportsANSI92IntermediateSQL() throws SQLException {
        return this._dmdWrapped.supportsANSI92IntermediateSQL();
    }


    public boolean supportsANSI92FullSQL() throws SQLException {
        return this._dmdWrapped.supportsANSI92FullSQL();
    }


    public boolean supportsIntegrityEnhancementFacility() throws SQLException {
        return this._dmdWrapped.supportsIntegrityEnhancementFacility();
    }


    public boolean supportsOuterJoins() throws SQLException {
        return this._dmdWrapped.supportsOuterJoins();
    }


    public boolean supportsFullOuterJoins() throws SQLException {
        return this._dmdWrapped.supportsFullOuterJoins();
    }


    public boolean supportsLimitedOuterJoins() throws SQLException {
        return this._dmdWrapped.supportsLimitedOuterJoins();
    }


    public String getSchemaTerm() throws SQLException {
        return this._dmdWrapped.getSchemaTerm();
    }


    public String getProcedureTerm() throws SQLException {
        return this._dmdWrapped.getProcedureTerm();
    }


    public String getCatalogTerm() throws SQLException {
        return this._dmdWrapped.getCatalogTerm();
    }


    public boolean isCatalogAtStart() throws SQLException {
        return this._dmdWrapped.isCatalogAtStart();
    }


    public String getCatalogSeparator() throws SQLException {
        return this._dmdWrapped.getCatalogSeparator();
    }


    public boolean supportsSchemasInDataManipulation() throws SQLException {
        return this._dmdWrapped.supportsSchemasInDataManipulation();
    }


    public boolean supportsSchemasInProcedureCalls() throws SQLException {
        return this._dmdWrapped.supportsSchemasInProcedureCalls();
    }


    public boolean supportsSchemasInTableDefinitions() throws SQLException {
        return this._dmdWrapped.supportsSchemasInTableDefinitions();
    }


    public boolean supportsSchemasInIndexDefinitions() throws SQLException {
        return this._dmdWrapped.supportsSchemasInIndexDefinitions();
    }


    public boolean supportsSchemasInPrivilegeDefinitions() throws SQLException {
        return this._dmdWrapped.supportsSchemasInPrivilegeDefinitions();
    }


    public boolean supportsCatalogsInDataManipulation() throws SQLException {
        return this._dmdWrapped.supportsCatalogsInDataManipulation();
    }


    public boolean supportsCatalogsInProcedureCalls() throws SQLException {
        return this._dmdWrapped.supportsCatalogsInProcedureCalls();
    }


    public boolean supportsCatalogsInTableDefinitions() throws SQLException {
        return this._dmdWrapped.supportsCatalogsInTableDefinitions();
    }


    public boolean supportsCatalogsInIndexDefinitions() throws SQLException {
        return this._dmdWrapped.supportsCatalogsInIndexDefinitions();
    }


    public boolean supportsCatalogsInPrivilegeDefinitions() throws SQLException {
        return this._dmdWrapped.supportsCatalogsInPrivilegeDefinitions();
    }


    public boolean supportsPositionedDelete() throws SQLException {
        return this._dmdWrapped.supportsPositionedDelete();
    }


    public boolean supportsPositionedUpdate() throws SQLException {
        return this._dmdWrapped.supportsPositionedUpdate();
    }


    public boolean supportsSelectForUpdate() throws SQLException {
        return this._dmdWrapped.supportsSelectForUpdate();
    }


    public boolean supportsStoredProcedures() throws SQLException {
        return this._dmdWrapped.supportsStoredProcedures();
    }


    public boolean supportsSubqueriesInComparisons() throws SQLException {
        return this._dmdWrapped.supportsSubqueriesInComparisons();
    }


    public boolean supportsSubqueriesInExists() throws SQLException {
        return this._dmdWrapped.supportsSubqueriesInExists();
    }


    public boolean supportsSubqueriesInIns() throws SQLException {
        return this._dmdWrapped.supportsSubqueriesInIns();
    }


    public boolean supportsSubqueriesInQuantifieds() throws SQLException {
        return this._dmdWrapped.supportsSubqueriesInQuantifieds();
    }


    public boolean supportsCorrelatedSubqueries() throws SQLException {
        return this._dmdWrapped.supportsCorrelatedSubqueries();
    }


    public boolean supportsUnion() throws SQLException {
        return this._dmdWrapped.supportsUnion();
    }


    public boolean supportsUnionAll() throws SQLException {
        return this._dmdWrapped.supportsUnionAll();
    }


    public boolean supportsOpenCursorsAcrossCommit() throws SQLException {
        return this._dmdWrapped.supportsOpenCursorsAcrossCommit();
    }


    public boolean supportsOpenCursorsAcrossRollback() throws SQLException {
        return this._dmdWrapped.supportsOpenCursorsAcrossRollback();
    }


    public boolean supportsOpenStatementsAcrossCommit() throws SQLException {
        return this._dmdWrapped.supportsOpenStatementsAcrossCommit();
    }


    public boolean supportsOpenStatementsAcrossRollback() throws SQLException {
        return this._dmdWrapped.supportsOpenStatementsAcrossRollback();
    }


    public int getMaxBinaryLiteralLength() throws SQLException {
        return this._dmdWrapped.getMaxBinaryLiteralLength();
    }


    public int getMaxCharLiteralLength() throws SQLException {
        return this._dmdWrapped.getMaxCharLiteralLength();
    }


    public int getMaxColumnNameLength() throws SQLException {
        return this._dmdWrapped.getMaxColumnNameLength();
    }


    public int getMaxColumnsInGroupBy() throws SQLException {
        return this._dmdWrapped.getMaxColumnsInGroupBy();
    }


    public int getMaxColumnsInIndex() throws SQLException {
        return this._dmdWrapped.getMaxColumnsInIndex();
    }


    public int getMaxColumnsInOrderBy() throws SQLException {
        return this._dmdWrapped.getMaxColumnsInOrderBy();
    }


    public int getMaxColumnsInSelect() throws SQLException {
        return this._dmdWrapped.getMaxColumnsInSelect();
    }


    public int getMaxColumnsInTable() throws SQLException {
        return this._dmdWrapped.getMaxColumnsInTable();
    }


    public int getMaxConnections() throws SQLException {
        return this._dmdWrapped.getMaxConnections();
    }


    public int getMaxCursorNameLength() throws SQLException {
        return this._dmdWrapped.getMaxCursorNameLength();
    }


    public int getMaxIndexLength() throws SQLException {
        return this._dmdWrapped.getMaxIndexLength();
    }


    public int getMaxSchemaNameLength() throws SQLException {
        return this._dmdWrapped.getMaxSchemaNameLength();
    }


    public int getMaxProcedureNameLength() throws SQLException {
        return this._dmdWrapped.getMaxProcedureNameLength();
    }


    public int getMaxCatalogNameLength() throws SQLException {
        return this._dmdWrapped.getMaxCatalogNameLength();
    }


    public int getMaxRowSize() throws SQLException {
        return this._dmdWrapped.getMaxRowSize();
    }


    public boolean doesMaxRowSizeIncludeBlobs() throws SQLException {
        return this._dmdWrapped.doesMaxRowSizeIncludeBlobs();
    }


    public int getMaxStatementLength() throws SQLException {
        return this._dmdWrapped.getMaxStatementLength();
    }


    public int getMaxStatements() throws SQLException {
        return this._dmdWrapped.getMaxStatements();
    }


    public int getMaxTableNameLength() throws SQLException {
        return this._dmdWrapped.getMaxTableNameLength();
    }


    public int getMaxTablesInSelect() throws SQLException {
        return this._dmdWrapped.getMaxTablesInSelect();
    }


    public int getMaxUserNameLength() throws SQLException {
        return this._dmdWrapped.getMaxUserNameLength();
    }


    public int getDefaultTransactionIsolation() throws SQLException {
        return this._dmdWrapped.getDefaultTransactionIsolation();
    }


    public boolean supportsTransactions() throws SQLException {
        return this._dmdWrapped.supportsTransactions();
    }


    public boolean supportsTransactionIsolationLevel(int level) throws SQLException {
        return this._dmdWrapped.supportsTransactionIsolationLevel(level);
    }


    public boolean supportsDataDefinitionAndDataManipulationTransactions() throws SQLException {
        return this._dmdWrapped.supportsDataDefinitionAndDataManipulationTransactions();
    }


    public boolean supportsDataManipulationTransactionsOnly() throws SQLException {
        return this._dmdWrapped.supportsDataManipulationTransactionsOnly();
    }


    public boolean dataDefinitionCausesTransactionCommit() throws SQLException {
        return this._dmdWrapped.dataDefinitionCausesTransactionCommit();
    }


    public boolean dataDefinitionIgnoredInTransactions() throws SQLException {
        return this._dmdWrapped.dataDefinitionIgnoredInTransactions();
    }


    public ResultSet getProcedures(String catalog, String schemaPattern, String procedureNamePattern) throws SQLException {
        return this._dmdWrapped.getProcedures(catalog, schemaPattern, procedureNamePattern);
    }


    public ResultSet getProcedureColumns(String catalog, String schemaPattern, String procedureNamePattern, String columnNamePattern) throws SQLException {
        return this._dmdWrapped.getProcedureColumns(catalog, schemaPattern, procedureNamePattern, columnNamePattern);
    }


    public ResultSet getTables(String catalog, String schemaPattern, String tableNamePattern, String[] types) throws SQLException {
        return this._dmdWrapped.getTables(catalog, schemaPattern, tableNamePattern, types);
    }


    public ResultSet getSchemas() throws SQLException {
        return this._dmdWrapped.getSchemas();
    }


    public ResultSet getCatalogs() throws SQLException {
        return this._dmdWrapped.getCatalogs();
    }


    public ResultSet getTableTypes() throws SQLException {
        return this._dmdWrapped.getTableTypes();
    }


    public ResultSet getColumns(String catalog, String schemaPattern, String tableNamePattern, String columnNamePattern) throws SQLException {
        return this._dmdWrapped.getColumns(catalog, schemaPattern, tableNamePattern, columnNamePattern);
    }


    public ResultSet getColumnPrivileges(String catalog, String schema, String table, String columnNamePattern) throws SQLException {
        return this._dmdWrapped.getColumnPrivileges(catalog, schema, table, columnNamePattern);
    }


    public ResultSet getTablePrivileges(String catalog, String schemaPattern, String tableNamePattern) throws SQLException {
        return this._dmdWrapped.getTablePrivileges(catalog, schemaPattern, tableNamePattern);
    }


    public ResultSet getBestRowIdentifier(String catalog, String schema, String table, int scope, boolean nullable) throws SQLException {
        return this._dmdWrapped.getBestRowIdentifier(catalog, schema, table, scope, nullable);
    }


    public ResultSet getVersionColumns(String catalog, String schema, String table) throws SQLException {
        return this._dmdWrapped.getVersionColumns(catalog, schema, table);
    }


    public ResultSet getPrimaryKeys(String catalog, String schema, String table) throws SQLException {
        return this._dmdWrapped.getPrimaryKeys(catalog, schema, table);
    }


    public ResultSet getImportedKeys(String catalog, String schema, String table) throws SQLException {
        return this._dmdWrapped.getImportedKeys(catalog, schema, table);
    }


    public ResultSet getExportedKeys(String catalog, String schema, String table) throws SQLException {
        return this._dmdWrapped.getExportedKeys(catalog, schema, table);
    }


    public ResultSet getCrossReference(String parentCatalog, String parentSchema, String parentTable, String foreignCatalog, String foreignSchema, String foreignTable) throws SQLException {
        return this._dmdWrapped.getCrossReference(parentCatalog, parentSchema, parentTable, foreignCatalog, foreignSchema, foreignTable);
    }


    public ResultSet getTypeInfo() throws SQLException {
        return this._dmdWrapped.getTypeInfo();
    }


    public ResultSet getIndexInfo(String catalog, String schema, String table, boolean unique, boolean approximate) throws SQLException {
        return this._dmdWrapped.getIndexInfo(catalog, schema, table, unique, approximate);
    }


    public boolean supportsResultSetType(int type) throws SQLException {
        return this._dmdWrapped.supportsResultSetType(type);
    }


    public boolean supportsResultSetConcurrency(int type, int concurrency) throws SQLException {
        return this._dmdWrapped.supportsResultSetConcurrency(type, concurrency);
    }


    public boolean ownUpdatesAreVisible(int type) throws SQLException {
        return this._dmdWrapped.ownUpdatesAreVisible(type);
    }


    public boolean ownDeletesAreVisible(int type) throws SQLException {
        return this._dmdWrapped.ownDeletesAreVisible(type);
    }


    public boolean ownInsertsAreVisible(int type) throws SQLException {
        return this._dmdWrapped.ownInsertsAreVisible(type);
    }


    public boolean othersUpdatesAreVisible(int type) throws SQLException {
        return this._dmdWrapped.othersUpdatesAreVisible(type);
    }


    public boolean othersDeletesAreVisible(int type) throws SQLException {
        return this._dmdWrapped.othersDeletesAreVisible(type);
    }


    public boolean othersInsertsAreVisible(int type) throws SQLException {
        return this._dmdWrapped.othersInsertsAreVisible(type);
    }


    public boolean updatesAreDetected(int type) throws SQLException {
        return this._dmdWrapped.updatesAreDetected(type);
    }


    public boolean deletesAreDetected(int type) throws SQLException {
        return this._dmdWrapped.deletesAreDetected(type);
    }


    public boolean insertsAreDetected(int type) throws SQLException {
        return this._dmdWrapped.insertsAreDetected(type);
    }


    public boolean supportsBatchUpdates() throws SQLException {
        return this._dmdWrapped.supportsBatchUpdates();
    }


    public ResultSet getUDTs(String catalog, String schemaPattern, String typeNamePattern, int[] types) throws SQLException {
        return this._dmdWrapped.getUDTs(catalog, schemaPattern, typeNamePattern, types);
    }


    public boolean supportsSavepoints() throws SQLException {
        return this._dmdWrapped.supportsSavepoints();
    }


    public boolean supportsNamedParameters() throws SQLException {
        return this._dmdWrapped.supportsNamedParameters();
    }


    public boolean supportsMultipleOpenResults() throws SQLException {
        return this._dmdWrapped.supportsMultipleOpenResults();
    }


    public boolean supportsGetGeneratedKeys() throws SQLException {
        return this._dmdWrapped.supportsGetGeneratedKeys();
    }


    public ResultSet getSuperTypes(String catalog, String schemaPattern, String typeNamePattern) throws SQLException {
        return this._dmdWrapped.getSuperTypes(catalog, schemaPattern, typeNamePattern);
    }


    public ResultSet getSuperTables(String catalog, String schemaPattern, String tableNamePattern) throws SQLException {
        return this._dmdWrapped.getSuperTables(catalog, schemaPattern, tableNamePattern);
    }


    public ResultSet getAttributes(String catalog, String schemaPattern, String typeNamePattern, String attributeNamePattern) throws SQLException {
        return this._dmdWrapped.getAttributes(catalog, schemaPattern, typeNamePattern, attributeNamePattern);
    }


    public boolean supportsResultSetHoldability(int holdability) throws SQLException {
        return this._dmdWrapped.supportsResultSetHoldability(holdability);
    }


    public int getResultSetHoldability() throws SQLException {
        return this._dmdWrapped.getResultSetHoldability();
    }


    public int getDatabaseMajorVersion() throws SQLException {
        return this._dmdWrapped.getDatabaseMajorVersion();
    }


    public int getDatabaseMinorVersion() throws SQLException {
        return this._dmdWrapped.getDatabaseMinorVersion();
    }


    public int getJDBCMajorVersion() throws SQLException {
        return this._dmdWrapped.getJDBCMajorVersion();
    }


    public int getJDBCMinorVersion() throws SQLException {
        return this._dmdWrapped.getJDBCMinorVersion();
    }


    public int getSQLStateType() throws SQLException {
        return this._dmdWrapped.getSQLStateType();
    }


    public boolean locatorsUpdateCopy() throws SQLException {
        return this._dmdWrapped.locatorsUpdateCopy();
    }


    public boolean supportsStatementPooling() throws SQLException {
        return this._dmdWrapped.supportsStatementPooling();
    }


    public RowIdLifetime getRowIdLifetime() throws SQLException {
        return this._dmdWrapped.getRowIdLifetime();
    }


    public ResultSet getSchemas(String catalog, String schemaPattern) throws SQLException {
        return this._dmdWrapped.getSchemas(catalog, schemaPattern);
    }


    public boolean supportsStoredFunctionsUsingCallSyntax() throws SQLException {
        return this._dmdWrapped.supportsStoredFunctionsUsingCallSyntax();
    }


    public boolean autoCommitFailureClosesAllResultSets() throws SQLException {
        return this._dmdWrapped.autoCommitFailureClosesAllResultSets();
    }


    public ResultSet getClientInfoProperties() throws SQLException {
        return this._dmdWrapped.getClientInfoProperties();
    }


    public ResultSet getFunctions(String catalog, String schemaPattern, String functionNamePattern) throws SQLException {
        return this._dmdWrapped.getFunctions(catalog, schemaPattern, functionNamePattern);
    }


    public ResultSet getFunctionColumns(String catalog, String schemaPattern, String functionNamePattern, String columnNamePattern) throws SQLException {
        return this._dmdWrapped.getFunctionColumns(catalog, schemaPattern, functionNamePattern, columnNamePattern);
    }


    public ResultSet getPseudoColumns(String catalog, String schemaPattern, String tableNamePattern, String columnNamePattern) throws SQLException {
        ResultSet rs = null;
        try {
            rs = this._dmdWrapped.getPseudoColumns(catalog, schemaPattern, tableNamePattern, columnNamePattern);
        } catch (AbstractMethodError ame) {
            throwUndefinedMethod(ame);
        }
        return rs;
    }


    public boolean generatedKeyAlwaysReturned() throws SQLException {
        boolean bGeneratedKeyAlwaysReturned = false;
        try {
            bGeneratedKeyAlwaysReturned = this._dmdWrapped.generatedKeyAlwaysReturned();
        } catch (AbstractMethodError ame) {
            throwUndefinedMethod(ame);
        }
        return bGeneratedKeyAlwaysReturned;
    }


    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return (iface == DatabaseMetaData.class);
    }

    public <T> T unwrap(Class<T> iface) throws SQLException {
        DatabaseMetaData databaseMetaData = null;
        T wrapped = null;
        if (isWrapperFor(iface))
            databaseMetaData = this._dmdWrapped;
        return (T) databaseMetaData;
    }
}
