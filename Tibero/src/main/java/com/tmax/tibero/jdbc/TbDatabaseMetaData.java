package com.tmax.tibero.jdbc;

import com.tmax.tibero.DriverConstants;
import com.tmax.tibero.jdbc.data.DataTypeConverter;
import com.tmax.tibero.jdbc.data.charset.Charset;
import com.tmax.tibero.jdbc.driver.TbResultSet;
import com.tmax.tibero.jdbc.driver.TbResultSetBase;
import com.tmax.tibero.jdbc.driver.TbResultSetFactory;
import com.tmax.tibero.jdbc.driver.TbStatement;
import com.tmax.tibero.jdbc.err.TbError;
import com.tmax.tibero.jdbc.msg.TbColumnDesc;
import com.tmax.tibero.jdbc.util.TbDatabaseMetaQuery;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.RowIdLifetime;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

public class TbDatabaseMetaData implements DatabaseMetaData {
  protected com.tmax.tibero.jdbc.driver.TbConnection conn;
  
  public TbDatabaseMetaData(com.tmax.tibero.jdbc.driver.TbConnection paramTbConnection) {
    this.conn = paramTbConnection;
  }
  
  public boolean allProceduresAreCallable() throws SQLException {
    return false;
  }
  
  public boolean allTablesAreSelectable() throws SQLException {
    return false;
  }
  
  public boolean autoCommitFailureClosesAllResultSets() throws SQLException {
    return false;
  }
  
  public boolean dataDefinitionCausesTransactionCommit() throws SQLException {
    return true;
  }
  
  public boolean dataDefinitionIgnoredInTransactions() throws SQLException {
    return false;
  }
  
  public boolean deletesAreDetected(int paramInt) throws SQLException {
    return false;
  }
  
  public boolean doesMaxRowSizeIncludeBlobs() throws SQLException {
    return false;
  }
  
  public ResultSet getAttributes(String paramString1, String paramString2, String paramString3, String paramString4) throws SQLException {
    if (!this.conn.getExtFeatureInfo().supports(1))
      return getAttributesEmpty(); 
    PreparedStatement preparedStatement = this.conn.prepareStatement(TbDatabaseMetaQuery.getAttributesQuery(false, false));
    preparedStatement.setInt(1, this.conn.getMapDateToTimestamp() ? 93 : 91);
    preparedStatement.setString(2, (paramString2 == null) ? "%" : paramString2);
    preparedStatement.setString(3, (paramString3 == null) ? "%" : paramString3);
    preparedStatement.setString(4, (paramString4 == null) ? "%" : paramString4);
    TbResultSetBase tbResultSetBase = (TbResultSetBase)preparedStatement.executeQuery();
    tbResultSetBase.closeStatementOnClose();
    return (ResultSet)tbResultSetBase;
  }
  
  public ResultSet getAttributes(String paramString) throws SQLException {
    PreparedStatement preparedStatement = this.conn.prepareStatement(TbDatabaseMetaQuery.getAttributesQuery(true, false));
    preparedStatement.setInt(1, this.conn.getMapDateToTimestamp() ? 93 : 91);
    preparedStatement.setString(2, paramString);
    TbResultSetBase tbResultSetBase = (TbResultSetBase)preparedStatement.executeQuery();
    tbResultSetBase.closeStatementOnClose();
    return (ResultSet)tbResultSetBase;
  }
  
  private ResultSet getAttributesEmpty() throws SQLException {
    byte b1 = 21;
    int bool1 = 0;
    int bool2 = 0;
    int bool3 = 0;
    int bool4 = 0;
    TbStatement tbStatement = new TbStatement(this.conn);
    TbResultSet tbResultSet = TbResultSetFactory.buildEmptyResultSet(tbStatement, -2, b1, 0);
    DataTypeConverter dataTypeConverter = this.conn.getTypeConverter();
    TbColumnDesc[] arrayOfTbColumnDesc = new TbColumnDesc[b1];
    for (byte b2 = 0; b2 < b1; b2++)
      arrayOfTbColumnDesc[b2] = new TbColumnDesc(); 
    arrayOfTbColumnDesc[0].set("TYPE_CAT", 3, bool1, bool2, bool3, bool4);
    arrayOfTbColumnDesc[1].set("TYPE_SCHEM", 3, bool1, bool2, bool3, bool4);
    arrayOfTbColumnDesc[2].set("TYPE_NAME", 3, bool1, bool2, bool3, bool4);
    arrayOfTbColumnDesc[3].set("ATTR_NAME", 3, bool1, bool2, bool3, bool4);
    arrayOfTbColumnDesc[4].set("DATA_TYPE", 1, bool1, bool2, bool3, bool4);
    arrayOfTbColumnDesc[5].set("ATTR_TYPE_NAME", 3, bool1, bool2, bool3, bool4);
    arrayOfTbColumnDesc[6].set("ATTR_SIZE", 1, bool1, bool2, bool3, bool4);
    arrayOfTbColumnDesc[7].set("DECIMAL_DIGITS", 1, bool1, bool2, bool3, bool4);
    arrayOfTbColumnDesc[8].set("NUM_PREC_RADIX", 1, bool1, bool2, bool3, bool4);
    arrayOfTbColumnDesc[9].set("NULLABLE", 1, bool1, bool2, bool3, bool4);
    arrayOfTbColumnDesc[10].set("REMARKS", 3, bool1, bool2, bool3, bool4);
    arrayOfTbColumnDesc[11].set("ATTR_DEF", 3, bool1, bool2, bool3, bool4);
    arrayOfTbColumnDesc[12].set("SQL_DATA_TYPE", 1, bool1, bool2, bool3, bool4);
    arrayOfTbColumnDesc[13].set("SQL_DATETIME_SUB", 1, bool1, bool2, bool3, bool4);
    arrayOfTbColumnDesc[14].set("CHAR_OCTET_LENGTH", 1, bool1, bool2, bool3, bool4);
    arrayOfTbColumnDesc[15].set("ORDINAL_POSITION", 1, bool1, bool2, bool3, bool4);
    arrayOfTbColumnDesc[16].set("IS_NULLABLE", 3, bool1, bool2, bool3, bool4);
    arrayOfTbColumnDesc[17].set("SCOPE_CATALOG", 3, bool1, bool2, bool3, bool4);
    arrayOfTbColumnDesc[18].set("SCOPE_SCHEMA", 3, bool1, bool2, bool3, bool4);
    arrayOfTbColumnDesc[19].set("SCOPE_TABLE", 3, bool1, bool2, bool3, bool4);
    arrayOfTbColumnDesc[20].set("SOURCE_DATA_TYPE", 1, bool1, bool2, bool3, bool4);
    dataTypeConverter.buildColumnMetadata(arrayOfTbColumnDesc, tbResultSet, arrayOfTbColumnDesc.length);
    ((TbResultSetBase)tbResultSet).closeStatementOnClose();
    return (ResultSet)tbResultSet;
  }
  
  public synchronized ResultSet getBestRowIdentifier(String paramString1, String paramString2, String paramString3, int paramInt, boolean paramBoolean) throws SQLException {
    PreparedStatement preparedStatement = this.conn.prepareStatement(TbDatabaseMetaQuery.getBestRowIdentifierQuery(this.conn.getExtFeatureInfo().supports(2)));
    preparedStatement.setInt(1, this.conn.getMapDateToTimestamp() ? 93 : 91);
    switch (paramInt) {
      case 0:
        preparedStatement.setInt(2, 0);
        preparedStatement.setInt(3, 0);
        break;
      case 1:
        preparedStatement.setInt(2, 1);
        preparedStatement.setInt(3, 1);
        break;
      case 2:
        preparedStatement.setInt(2, 0);
        preparedStatement.setInt(3, 1);
        break;
    } 
    preparedStatement.setString(4, paramString3);
    preparedStatement.setString(5, (paramString2 != null) ? paramString2 : "%");
    preparedStatement.setString(6, paramBoolean ? "X" : "Y");
    TbResultSetBase tbResultSetBase = (TbResultSetBase)preparedStatement.executeQuery();
    tbResultSetBase.closeStatementOnClose();
    return (ResultSet)tbResultSetBase;
  }
  
  public synchronized ResultSet getCatalogs() throws SQLException {
    Statement statement = this.conn.createStatement();
    TbResultSetBase tbResultSetBase = (TbResultSetBase)statement.executeQuery(TbDatabaseMetaQuery.QUERY_CATALOGS);
    tbResultSetBase.closeStatementOnClose();
    return (ResultSet)tbResultSetBase;
  }
  
  public String getCatalogSeparator() throws SQLException {
    return "";
  }
  
  public String getCatalogTerm() throws SQLException {
    return "";
  }
  
  public ResultSet getClientInfoProperties() throws SQLException {
    Statement statement = this.conn.createStatement();
    return statement.executeQuery("SELECT      NULL NAME,     -1 MAX_LEN,     NULL DEFAULT_VALUE,     NULL DESCRIPTION FROM DUAL WHERE 0 = 1");
  }
  
  public synchronized ResultSet getColumnPrivileges(String paramString1, String paramString2, String paramString3, String paramString4) throws SQLException {
    PreparedStatement preparedStatement = this.conn.prepareStatement(TbDatabaseMetaQuery.QUERY_COLUMNPRIVILEGES);
    preparedStatement.setString(1, (paramString2 != null) ? paramString2 : "%");
    preparedStatement.setString(2, paramString3);
    preparedStatement.setString(3, (paramString4 != null) ? paramString4 : "%");
    TbResultSetBase tbResultSetBase = (TbResultSetBase)preparedStatement.executeQuery();
    tbResultSetBase.closeStatementOnClose();
    return (ResultSet)tbResultSetBase;
  }
  
  public synchronized ResultSet getColumns(String paramString1, String paramString2, String paramString3, String paramString4) throws SQLException {
    boolean bool1 = this.conn.getIncludeSynonyms();
    boolean bool2 = this.conn.getMapDateToTimestamp();
    PreparedStatement preparedStatement = this.conn.prepareStatement(TbDatabaseMetaQuery.getTableColumnsQuery(bool1, this.conn.getExtFeatureInfo().supports(2)));
    preparedStatement.setInt(1, bool2 ? 93 : 91);
    preparedStatement.setString(2, (paramString2 != null) ? paramString2 : "%");
    if (bool1) {
      preparedStatement.setString(3, (paramString2 != null) ? paramString2 : "%");
      preparedStatement.setString(4, (paramString3 != null) ? paramString3 : "%");
      preparedStatement.setString(5, (paramString3 != null) ? paramString3 : "%");
      preparedStatement.setString(6, (paramString4 != null) ? paramString4 : "%");
      preparedStatement.setString(7, (paramString2 != null) ? paramString2 : "%");
      preparedStatement.setString(8, (paramString2 != null) ? paramString2 : "%");
    } else {
      preparedStatement.setString(3, (paramString3 != null) ? paramString3 : "%");
      preparedStatement.setString(4, (paramString4 != null) ? paramString4 : "%");
    } 
    TbResultSetBase tbResultSetBase = (TbResultSetBase)preparedStatement.executeQuery();
    tbResultSetBase.closeStatementOnClose();
    return (ResultSet)tbResultSetBase;
  }
  
  public Connection getConnection() throws SQLException {
    return (Connection)this.conn;
  }
  
  public ResultSet getCrossReference(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6) throws SQLException {
    return getRelatedKeys(paramString2, paramString3, paramString5, paramString6, "ORDER BY fktable_schem, fktable_name, key_seq");
  }
  
  public int getDatabaseMajorVersion() throws SQLException {
    return this.conn.serverInfo.getTbMajor();
  }
  
  public int getDatabaseMinorVersion() throws SQLException {
    return this.conn.serverInfo.getTbMinor();
  }
  
  public String getDatabaseProductName() throws SQLException {
    String str = this.conn.info.getDatabaseProductName();
    return (str == null || str.equals("")) ? this.conn.serverInfo.getTbProductName() : str;
  }
  
  public String getDatabaseProductVersion() throws SQLException {
    String str = this.conn.info.getDatabaseProductVersion();
    return (str == null || str.equals("")) ? this.conn.serverInfo.getTbProductVersion() : str;
  }
  
  public synchronized ResultSet getDBParam(String paramString) throws SQLException {
    StringBuffer stringBuffer = new StringBuffer(TbDatabaseMetaQuery.QUERY_IPARAM);
    if (paramString != null)
      stringBuffer.append(" name like '%").append(paramString.toUpperCase()).append("%' and"); 
    stringBuffer.append(" name not like '").append(getSearchStringEscape()).append("_%' escape '").append(getSearchStringEscape()).append("'");
    PreparedStatement preparedStatement = this.conn.prepareStatement(stringBuffer.toString());
    TbResultSetBase tbResultSetBase = (TbResultSetBase)preparedStatement.executeQuery();
    tbResultSetBase.closeStatementOnClose();
    return (ResultSet)tbResultSetBase;
  }
  
  public int getDefaultTransactionIsolation() throws SQLException {
    return 2;
  }
  
  public int getDriverMajorVersion() {
    return Integer.parseInt(DriverConstants.JDBC_MAJOR);
  }
  
  public int getDriverMinorVersion() {
    return Integer.parseInt(DriverConstants.JDBC_MINOR);
  }
  
  public String getDriverName() throws SQLException {
    String str = this.conn.info.getDriverName();
    return (str == null || str.equals("")) ? (DriverConstants.BRAND_NAME + " " + DriverConstants.JDBC_PRODUCT_NAME) : str;
  }
  
  public String getDriverVersion() throws SQLException {
    String str = this.conn.info.getDriverVersion();
    return (str == null || str.equals("")) ? (DriverConstants.JDBC_MAJOR + "." + DriverConstants.JDBC_MINOR + "." + " ") : str;
  }
  
  public ResultSet getExportedKeys(String paramString1, String paramString2, String paramString3) throws SQLException {
    return getRelatedKeys(paramString2, paramString3, null, null, "ORDER BY fktable_schem, fktable_name, key_seq");
  }
  
  public String getExtraNameCharacters() throws SQLException {
    return "$#";
  }
  
  public ResultSet getFunctionColumns(String paramString1, String paramString2, String paramString3, String paramString4) throws SQLException {
    String str1 = "ORDER BY FUNCTION_SCHEM, FUNCTION_NAME\n";
    PreparedStatement preparedStatement = null;
    StringBuilder stringBuilder = new StringBuilder();
    String str2 = paramString2;
    if (paramString2 == null) {
      str2 = "%";
    } else if (paramString2.equals("")) {
      str2 = getUserName().toUpperCase();
    } 
    String str3 = paramString3;
    if (paramString3 == null) {
      str3 = "%";
    } else if (paramString3.equals("")) {
      TbError.newSQLException(-90649);
    } 
    String str4 = paramString4;
    if (paramString4 == null || paramString4.equals("%")) {
      str4 = "%";
      stringBuilder.append("  AND (ARG.ARGUMENT_NAME LIKE ? ESCAPE '").append(getSearchStringEscape()).append("'\n").append("       OR (ARG.ARGUMENT_NAME IS NULL\n").append("           AND ARG.DATA_TYPE IS NOT NULL))\n");
    } else if (paramString4.equals("")) {
      TbError.newSQLException(-90649);
    } else {
      stringBuilder.append("  AND ARG.ARGUMENT_NAME LIKE ? ESCAPE '").append(getSearchStringEscape()).append("'\n");
    } 
    boolean bool = this.conn.getMapDateToTimestamp();
    if (paramString1 == null) {
      String str = TbDatabaseMetaQuery.getFunctionColumnsQuery(bool) + stringBuilder + str1;
      preparedStatement = this.conn.prepareStatement(str);
      preparedStatement.setString(1, str2);
      preparedStatement.setString(2, str3);
      preparedStatement.setString(3, str4);
    } else if (paramString1.equals("")) {
      String str = TbDatabaseMetaQuery.getFunctionColumnsQuery(bool) + "  AND ARG.PACKAGE_NAME IS NULL\n" + stringBuilder + str1;
      preparedStatement = this.conn.prepareStatement(str);
      preparedStatement.setString(1, str2);
      preparedStatement.setString(2, str3);
      preparedStatement.setString(3, str4);
    } else {
      String str = TbDatabaseMetaQuery.getFunctionColumnsQuery(bool) + "  AND ARG.PACKAGE_NAME LIKE ? ESCAPE '" + getSearchStringEscape() + "'\n" + stringBuilder + str1;
      preparedStatement = this.conn.prepareStatement(str);
      preparedStatement.setString(1, str2);
      preparedStatement.setString(2, str3);
      preparedStatement.setString(3, paramString1);
      preparedStatement.setString(4, str4);
    } 
    TbResultSetBase tbResultSetBase = (TbResultSetBase)preparedStatement.executeQuery();
    tbResultSetBase.closeStatementOnClose();
    return (ResultSet)tbResultSetBase;
  }

  @Override
  public ResultSet getPseudoColumns(String catalog, String schemaPattern, String tableNamePattern, String columnNamePattern) throws SQLException {
    return null;
  }

  @Override
  public boolean generatedKeyAlwaysReturned() throws SQLException {
    return false;
  }

  public ResultSet getFunctions(String paramString1, String paramString2, String paramString3) throws SQLException {
    String str1 = "ORDER BY FUNCTION_SCHEM, FUNCTION_NAME\n";
    PreparedStatement preparedStatement = null;
    String str2 = paramString2;
    if (paramString2 == null) {
      str2 = "%";
    } else if (paramString2.equals("")) {
      str2 = getUserName().toUpperCase();
    } 
    String str3 = paramString3;
    if (paramString3 == null) {
      str3 = "%";
    } else if (paramString3.equals("")) {
      TbError.newSQLException(-90649);
    } 
    if (paramString1 == null) {
      String str = TbDatabaseMetaQuery.QUERY_FUNCTIONS_STANDALONE + "UNION ALL " + TbDatabaseMetaQuery.QUERY_FUNCTIONS_PKG + "  AND PACKAGE_NAME IS NOT NULL\n" + "  AND OWNER LIKE ? ESCAPE '" + getSearchStringEscape() + "'\n" + "  AND OBJECT_NAME LIKE ? ESCAPE '" + getSearchStringEscape() + "'\n" + str1;
      preparedStatement = this.conn.prepareStatement(str);
      preparedStatement.setString(1, str2);
      preparedStatement.setString(2, str3);
      preparedStatement.setString(3, str2);
      preparedStatement.setString(4, str3);
    } else if (paramString1.equals("")) {
      preparedStatement = this.conn.prepareStatement(TbDatabaseMetaQuery.QUERY_FUNCTIONS_STANDALONE);
      preparedStatement.setString(1, str2);
      preparedStatement.setString(2, str3);
    } else {
      String str = TbDatabaseMetaQuery.QUERY_FUNCTIONS_PKG + "  AND PACKAGE_NAME LIKE ? ESCAPE '" + getSearchStringEscape() + "'\n" + "  AND OWNER LIKE ? ESCAPE '" + getSearchStringEscape() + "'\n" + "  AND OBJECT_NAME LIKE ? ESCAPE '" + getSearchStringEscape() + "'\n" + str1;
      preparedStatement = this.conn.prepareStatement(str);
      preparedStatement.setString(1, str2);
      preparedStatement.setString(2, str2);
      preparedStatement.setString(3, str3);
    } 
    TbResultSetBase tbResultSetBase = (TbResultSetBase)preparedStatement.executeQuery();
    tbResultSetBase.closeStatementOnClose();
    return (ResultSet)tbResultSetBase;
  }
  
  public String getIdentifierQuoteString() throws SQLException {
    return "\"";
  }
  
  public ResultSet getImportedKeys(String paramString1, String paramString2, String paramString3) throws SQLException {
    return getRelatedKeys(null, null, paramString2, paramString3, "ORDER BY pktable_schem, pktable_name, key_seq");
  }
  
  public synchronized ResultSet getIndexInfo(String paramString1, String paramString2, String paramString3, boolean paramBoolean1, boolean paramBoolean2) throws SQLException {
    if (!paramBoolean2)
      try {
        gatherTableStats(paramString2, paramString3);
      } catch (SQLException sQLException) {
        if (sQLException.getErrorCode() != -20003)
          throw sQLException; 
      }  
    StringBuffer stringBuffer = new StringBuffer(TbDatabaseMetaQuery.QUERY_INDEXINFO01);
    if (paramString2 != null && paramString2.length() > 0)
      stringBuffer.append(" and owner = ? "); 
    stringBuffer.append(" union ");
    stringBuffer.append(TbDatabaseMetaQuery.QUERY_INDEXINFO02);
    if (paramString2 != null && paramString2.length() > 0)
      stringBuffer.append(" and i.owner = ? "); 
    if (paramBoolean1)
      stringBuffer.append(" and i.uniqueness = 'UNIQUE' "); 
    stringBuffer.append(TbDatabaseMetaQuery.QUERY_INDEXINFO03);
    PreparedStatement preparedStatement = this.conn.prepareStatement(stringBuffer.substring(0, stringBuffer.length()));
    byte b = 1;
    preparedStatement.setString(b++, paramString3);
    if (paramString2 != null && paramString2.length() > 0)
      preparedStatement.setString(b++, paramString2); 
    preparedStatement.setString(b++, paramString3);
    if (paramString2 != null && paramString2.length() > 0)
      preparedStatement.setString(b++, paramString2); 
    TbResultSetBase tbResultSetBase = (TbResultSetBase)preparedStatement.executeQuery();
    tbResultSetBase.closeStatementOnClose();
    return (ResultSet)tbResultSetBase;
  }
  
  private void gatherTableStats(String paramString1, String paramString2) throws SQLException {
    CallableStatement callableStatement = null;
    try {
      callableStatement = this.conn.prepareCall(TbDatabaseMetaQuery.QUERY_GATHER_TABLE_STATS);
      callableStatement.setString(1, (paramString1 != null && paramString1.length() > 0) ? paramString1 : this.conn.info.getUser());
      callableStatement.setString(2, paramString2);
      callableStatement.execute();
    } finally {
      if (callableStatement != null)
        try {
          callableStatement.close();
        } catch (SQLException sQLException) {} 
    } 
  }
  
  public int getJDBCMajorVersion() throws SQLException {
//    null = 3;
    return 4;
  }
  
  public int getJDBCMinorVersion() throws SQLException {
    return 0;
  }
  
  public int getMaxBinaryLiteralLength() throws SQLException {
    return 65535;
  }
  
  public int getMaxCatalogNameLength() throws SQLException {
    return 0;
  }
  
  public int getMaxCharLiteralLength() throws SQLException {
    return 65535;
  }
  
  public int getMaxColumnNameLength() throws SQLException {
    return 30;
  }
  
  public int getMaxColumnsInGroupBy() throws SQLException {
    return 0;
  }
  
  public int getMaxColumnsInIndex() throws SQLException {
    return 32;
  }
  
  public int getMaxColumnsInOrderBy() throws SQLException {
    return 0;
  }
  
  public int getMaxColumnsInSelect() throws SQLException {
    return 0;
  }
  
  public int getMaxColumnsInTable() throws SQLException {
    return 1000;
  }
  
  public int getMaxConnections() throws SQLException {
    return 0;
  }
  
  public int getMaxCursorNameLength() throws SQLException {
    return 0;
  }
  
  public int getMaxIndexLength() throws SQLException {
    return 0;
  }
  
  public int getMaxProcedureNameLength() throws SQLException {
    return 30;
  }
  
  public int getMaxRowSize() throws SQLException {
    return 0;
  }
  
  public int getMaxSchemaNameLength() throws SQLException {
    return 30;
  }
  
  public int getMaxStatementLength() throws SQLException {
    return 0;
  }
  
  public int getMaxStatements() throws SQLException {
    return 0;
  }
  
  public int getMaxTableNameLength() throws SQLException {
    return 30;
  }
  
  public int getMaxTablesInSelect() throws SQLException {
    return 0;
  }
  
  public int getMaxUserNameLength() throws SQLException {
    return 30;
  }
  
  public String getNumericFunctions() throws SQLException {
    return "ABS,ACOS,ASIN,ATAN,ATAN2,CEILING,COS,EXP,FLOOR,LOG,LOG10,MOD,PI,POWER,ROUND,SIGN,SIN,SQRT,TAN,TRUNCATE";
  }
  
  public synchronized ResultSet getPrimaryKeys(String paramString1, String paramString2, String paramString3) throws SQLException {
    PreparedStatement preparedStatement = this.conn.prepareStatement(TbDatabaseMetaQuery.QUERY_PRIMARYKEY);
    preparedStatement.setString(1, paramString3);
    preparedStatement.setString(2, (paramString2 != null) ? paramString2 : "%");
    TbResultSetBase tbResultSetBase = (TbResultSetBase)preparedStatement.executeQuery();
    tbResultSetBase.closeStatementOnClose();
    return (ResultSet)tbResultSetBase;
  }
  
  public synchronized ResultSet getProcedureColumns(String paramString1, String paramString2, String paramString3, String paramString4) throws SQLException {
    StringBuffer stringBuffer = new StringBuffer(TbDatabaseMetaQuery.getProcedureColumnsQuery(this.conn.getMapDateToTimestamp()));
    String str1 = getQualifiedSchemaName(paramString2);
    String str2 = getQualifiedObjName(paramString3);
    String str3 = getQualifiedObjName(paramString4);
    if (paramString1 != null && paramString1.length() == 0) {
      stringBuffer.append("   AND PACKAGE_NAME IS NULL");
    } else if (paramString1 != null) {
      stringBuffer.append("   AND PACKAGE_NAME LIKE ? ESCAPE '").append(getSearchStringEscape()).append("'");
    } 
    stringBuffer.append("   AND OBJECT_NAME LIKE ? ESCAPE '").append(getSearchStringEscape()).append("'");
    if (str3.equals("%")) {
      stringBuffer.append("   AND (ARGUMENT_NAME LIKE ? ESCAPE '").append(getSearchStringEscape()).append("' ");
      stringBuffer.append("        OR (ARGUMENT_NAME IS NULL AND DATA_TYPE IS NOT NULL))");
    } else {
      stringBuffer.append("   AND ARGUMENT_NAME LIKE ? ESCAPE '").append(getSearchStringEscape()).append("' ");
    } 
    stringBuffer.append("   ORDER BY PROCEDURE_SCHEM, PROCEDURE_NAME, POSITION");
    PreparedStatement preparedStatement = this.conn.prepareStatement(stringBuffer.toString());
    byte b = 1;
    preparedStatement.setString(b++, str1);
    if (paramString1 != null && paramString1.length() > 0)
      preparedStatement.setString(b++, paramString1); 
    preparedStatement.setString(b++, str2);
    preparedStatement.setString(b++, str3);
    TbResultSetBase tbResultSetBase = (TbResultSetBase)preparedStatement.executeQuery();
    tbResultSetBase.closeStatementOnClose();
    return (ResultSet)tbResultSetBase;
  }
  
  public synchronized ResultSet getProcedures(String paramString1, String paramString2, String paramString3) throws SQLException {
    String str1 = getQualifiedSchemaName(paramString2);
    String str2 = getQualifiedObjName(paramString3);
    StringBuffer stringBuffer = new StringBuffer(TbDatabaseMetaQuery.QUERY_PROCEDURES);
    if (paramString1 == null) {
      stringBuffer.append("   AND (OBJECT_NAME LIKE ? ESCAPE '").append(getSearchStringEscape()).append("' OR");
      stringBuffer.append("        PROCEDURE_NAME LIKE ? ESCAPE '").append(getSearchStringEscape()).append("')");
      stringBuffer.append(" ORDER BY PROCEDURE_SCHEM, PROCEDURE_NAME");
    } else if (paramString1.length() == 0) {
      stringBuffer.append("   AND OBJECT_NAME LIKE ? ESCAPE '").append(getSearchStringEscape()).append("'");
      stringBuffer.append("   AND PROCEDURE_NAME IS NULL");
    } else {
      stringBuffer.append("   AND OBJECT_NAME LIKE ? ESCAPE '").append(getSearchStringEscape()).append("'");
      stringBuffer.append("   AND PROCEDURE_NAME LIKE ? ESCAPE '").append(getSearchStringEscape()).append("'");
      stringBuffer.append(" ORDER BY PROCEDURE_SCHEM, PROCEDURE_NAME");
    } 
    PreparedStatement preparedStatement = this.conn.prepareStatement(stringBuffer.toString());
    preparedStatement.setString(1, str1);
    if (paramString1 == null) {
      preparedStatement.setString(2, str2);
      preparedStatement.setString(3, str2);
    } else if (paramString1.equals("")) {
      preparedStatement.setString(2, str2);
    } else {
      preparedStatement.setString(2, paramString1);
      preparedStatement.setString(3, str2);
    } 
    TbResultSetBase tbResultSetBase = (TbResultSetBase)preparedStatement.executeQuery();
    tbResultSetBase.closeStatementOnClose();
    return (ResultSet)tbResultSetBase;
  }
  
  public String getProcedureTerm() throws SQLException {
    return "procedure";
  }
  
  private String getQualifiedObjName(String paramString) throws SQLException {
    if (paramString == null)
      return "%"; 
    if (paramString.length() == 0)
      throw TbError.newSQLException(-90649); 
    return paramString;
  }
  
  private String getQualifiedSchemaName(String paramString) {
    return (paramString == null) ? "%" : (paramString.equals("") ? this.conn.info.getUser() : paramString);
  }
  
  private synchronized ResultSet getRelatedKeys(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5) throws SQLException {
    byte b = 1;
    StringBuffer stringBuffer = new StringBuffer(TbDatabaseMetaQuery.QUERY_RELATEDKEYS);
    if (paramString2 != null)
      stringBuffer.append(" AND p.table_name \t= ? "); 
    if (paramString4 != null)
      stringBuffer.append(" AND f.table_name \t= ? "); 
    if (paramString1 != null && paramString1.length() > 0)
      stringBuffer.append(" AND p.owner \t= ? "); 
    if (paramString3 != null && paramString3.length() > 0)
      stringBuffer.append(" AND f.owner \t= ? "); 
    stringBuffer.append(paramString5);
    PreparedStatement preparedStatement = this.conn.prepareStatement(stringBuffer.substring(0, stringBuffer.length()));
    if (paramString2 != null)
      preparedStatement.setString(b++, paramString2); 
    if (paramString4 != null)
      preparedStatement.setString(b++, paramString4); 
    if (paramString1 != null && paramString1.length() > 0)
      preparedStatement.setString(b++, paramString1); 
    if (paramString3 != null && paramString3.length() > 0)
      preparedStatement.setString(b++, paramString3); 
    TbResultSetBase tbResultSetBase = (TbResultSetBase)preparedStatement.executeQuery();
    tbResultSetBase.closeStatementOnClose();
    return (ResultSet)tbResultSetBase;
  }
  
  public int getResultSetHoldability() throws SQLException {
    return 1;
  }
  
  public RowIdLifetime getRowIdLifetime() throws SQLException {
    return RowIdLifetime.ROWID_VALID_FOREVER;
  }
  
  public ResultSet getSchemas() throws SQLException {
    Statement statement = this.conn.createStatement();
    TbResultSetBase tbResultSetBase = (TbResultSetBase)statement.executeQuery(TbDatabaseMetaQuery.QUERY_SCHEMAS_01 + TbDatabaseMetaQuery.QUERY_SCHEMAS_02);
    tbResultSetBase.closeStatementOnClose();
    return (ResultSet)tbResultSetBase;
  }
  
  public ResultSet getSchemas(String paramString1, String paramString2) throws SQLException {
    String str1 = paramString2;
    if (paramString2 == null) {
      str1 = "%";
    } else if (paramString2.equals("")) {
      str1 = getUserName().toUpperCase();
    } 
    String str2 = TbDatabaseMetaQuery.QUERY_SCHEMAS_01 + " WHERE USERNAME LIKE ? ESCAPE '" + getSearchStringEscape() + "' " + TbDatabaseMetaQuery.QUERY_SCHEMAS_02;
    PreparedStatement preparedStatement = this.conn.prepareStatement(str2);
    preparedStatement.setString(1, str1);
    TbResultSetBase tbResultSetBase = (TbResultSetBase)preparedStatement.executeQuery();
    tbResultSetBase.closeStatementOnClose();
    return (ResultSet)tbResultSetBase;
  }
  
  public String getSchemaTerm() throws SQLException {
    return "schema";
  }
  
  public String getSearchStringEscape() throws SQLException {
    return TbDatabaseMetaQuery.escapeChar;
  }
  
  public String getServerCharSet() throws SQLException {
    return Charset.getName(this.conn.getServerCharSet());
  }
  
  public String getSQLKeywords() throws SQLException {
    return "ACCESS, ADD, ALTER, AUDIT, CLUSTER, COLUMN, COMMENT, COMPRESS, CONNECT, DATE, DROP, EXCLUSIVE, FILE, IDENTIFIED, IMMEDIATE, INCREMENT, INDEX, INITIAL, INTERSECT, LEVEL, LOCK, LONG, MAXEXTENTS, MINUS, MODE, NOAUDIT, NOCOMPRESS, NOWAIT, NUMBER, OFFLINE, ONLINE, PCTFREE, PRIOR, all_PL_SQL_reserved_words";
  }
  
  public int getSQLStateType() throws SQLException {
    return 0;
  }
  
  public String getStringFunctions() throws SQLException {
    return "ASCII,CHAR,CHAR_LENGTH,CHARACTER_LENGTH,CONCAT,INSERT,LCASE,LEFT,LENGTH,LOCATE,LTRIM,REPEAT,REPLACE,RIGHT,RTRIM,SPACE,SUBSTRING,UCASE";
  }
  
  public ResultSet getSuperTables(String paramString1, String paramString2, String paramString3) throws SQLException {
    byte b1 = 4;
    int bool1 = 0;
    int bool2 = 0;
    int bool3 = 0;
    int bool4 = 0;
    TbStatement tbStatement = new TbStatement(this.conn);
    TbResultSet tbResultSet = TbResultSetFactory.buildEmptyResultSet(tbStatement, -2, b1, 0);
    DataTypeConverter dataTypeConverter = this.conn.getTypeConverter();
    TbColumnDesc[] arrayOfTbColumnDesc = new TbColumnDesc[b1];
    for (byte b2 = 0; b2 < b1; b2++)
      arrayOfTbColumnDesc[b2] = new TbColumnDesc(); 
    arrayOfTbColumnDesc[0].set("TABLE_CAT", 3, bool1, bool2, bool3, bool4);
    arrayOfTbColumnDesc[1].set("TABLE_SCHEM", 3, bool1, bool2, bool3, bool4);
    arrayOfTbColumnDesc[2].set("TABLE_NAME", 3, bool1, bool2, bool3, bool4);
    arrayOfTbColumnDesc[3].set("SUPERTABLE_NAME", 3, bool1, bool2, bool3, bool4);
    dataTypeConverter.buildColumnMetadata(arrayOfTbColumnDesc, tbResultSet, arrayOfTbColumnDesc.length);
    ((TbResultSetBase)tbResultSet).closeStatementOnClose();
    return (ResultSet)tbResultSet;
  }
  
  public ResultSet getSuperTypes(String paramString1, String paramString2, String paramString3) throws SQLException {
    byte b1 = 6;
    int bool1 = 0;
    int bool2 = 0;
    int bool3 = 0;
    int bool4 = 0;
    TbStatement tbStatement = new TbStatement(this.conn);
    TbResultSet tbResultSet = TbResultSetFactory.buildEmptyResultSet(tbStatement, -2, b1, 0);
    DataTypeConverter dataTypeConverter = this.conn.getTypeConverter();
    TbColumnDesc[] arrayOfTbColumnDesc = new TbColumnDesc[b1];
    for (byte b2 = 0; b2 < b1; b2++)
      arrayOfTbColumnDesc[b2] = new TbColumnDesc(); 
    arrayOfTbColumnDesc[0].set("TYPE_CAT", 3, bool1, bool2, bool3, bool4);
    arrayOfTbColumnDesc[1].set("TYPE_SCHEM", 3, bool1, bool2, bool3, bool4);
    arrayOfTbColumnDesc[2].set("TYPE_NAME", 3, bool1, bool2, bool3, bool4);
    arrayOfTbColumnDesc[3].set("SUPERTYPE_CAT", 3, bool1, bool2, bool3, bool4);
    arrayOfTbColumnDesc[4].set("SUPERTYPE_SCHEM", 3, bool1, bool2, bool3, bool4);
    arrayOfTbColumnDesc[5].set("SUPERTYPE_NAME", 3, bool1, bool2, bool3, bool4);
    dataTypeConverter.buildColumnMetadata(arrayOfTbColumnDesc, tbResultSet, arrayOfTbColumnDesc.length);
    ((TbResultSetBase)tbResultSet).closeStatementOnClose();
    return (ResultSet)tbResultSet;
  }
  
  public String getSystemFunctions() throws SQLException {
    return "DATABASE,IFNULL,USER";
  }
  
  public synchronized ResultSet getTablePrivileges(String paramString1, String paramString2, String paramString3) throws SQLException {
    PreparedStatement preparedStatement = this.conn.prepareStatement(TbDatabaseMetaQuery.QUERY_TABLEPRIVILEGES);
    preparedStatement.setString(1, (paramString2 != null) ? paramString2 : "%");
    preparedStatement.setString(2, (paramString3 != null) ? paramString3 : "%");
    preparedStatement.setString(3, (paramString2 != null) ? paramString2 : "%");
    preparedStatement.setString(4, (paramString3 != null) ? paramString3 : "%");
    TbResultSetBase tbResultSetBase = (TbResultSetBase)preparedStatement.executeQuery();
    tbResultSetBase.closeStatementOnClose();
    return (ResultSet)tbResultSetBase;
  }
  
  public synchronized ResultSet getTables(String paramString1, String paramString2, String paramString3, String[] paramArrayOfString) throws SQLException {
    StringBuffer stringBuffer = new StringBuffer(TbDatabaseMetaQuery.QUERY_TABLES);
    if (paramArrayOfString != null) {
      stringBuffer.append(" AND o.object_type IN ( ");
      for (byte b = 0; b < paramArrayOfString.length; b++) {
        if (b == 0) {
          stringBuffer.append("'").append(paramArrayOfString[b]).append("'");
        } else {
          stringBuffer.append(", '").append(paramArrayOfString[b]).append("'");
        } 
      } 
      stringBuffer.append(") ");
    } else {
      stringBuffer.append(" AND o.object_type IN ('TABLE', 'SYNONYM', 'VIEW') ");
    } 
    stringBuffer.append(" ORDER BY TABLE_TYPE, TABLE_SCHEM, TABLE_NAME");
    PreparedStatement preparedStatement = this.conn.prepareStatement(stringBuffer.substring(0, stringBuffer.length()));
    preparedStatement.setString(1, (paramString2 != null) ? paramString2 : "%");
    preparedStatement.setString(2, (paramString3 != null) ? paramString3 : "%");
    TbResultSetBase tbResultSetBase = (TbResultSetBase)preparedStatement.executeQuery();
    tbResultSetBase.closeStatementOnClose();
    return (ResultSet)tbResultSetBase;
  }
  
  public synchronized ResultSet getTableTypes() throws SQLException {
    Statement statement = this.conn.createStatement();
    TbResultSetBase tbResultSetBase = (TbResultSetBase)statement.executeQuery(TbDatabaseMetaQuery.QUERY_TABLETYPES);
    tbResultSetBase.closeStatementOnClose();
    return (ResultSet)tbResultSetBase;
  }
  
  public String getTimeDateFunctions() throws SQLException {
    return "CURDATE,CURRENT_DATE,CURRENT_TIME,CURRENT_TIMESTAMP,CURTIME,DAYOFMONTH,EXTRACT,HOUR,MINUTE,MONTH,NOW,SECOND,YEAR";
  }
  
  public ResultSet getTypeInfo() throws SQLException {
    PreparedStatement preparedStatement = this.conn.prepareStatement(TbDatabaseMetaQuery.QUERY_TYPEINFO);
    boolean bool = this.conn.getExtFeatureInfo().supports(2);
    preparedStatement.setInt(1, bool ? 1 : 0);
    preparedStatement.setInt(2, bool ? 1 : 0);
    TbResultSetBase tbResultSetBase = (TbResultSetBase)preparedStatement.executeQuery();
    tbResultSetBase.closeStatementOnClose();
    return (ResultSet)tbResultSetBase;
  }
  
  public ResultSet getUDTs(String paramString1, String paramString2, String paramString3, int[] paramArrayOfint) throws SQLException {
    if (this.conn.getExtFeatureInfo().supports(1) && (paramArrayOfint == null || Arrays.binarySearch(paramArrayOfint, 2002) > -1)) {
      PreparedStatement preparedStatement = this.conn.prepareStatement(TbDatabaseMetaQuery.QUERY_UDTS);
      preparedStatement.setString(1, (paramString2 != null) ? paramString2 : "%");
      preparedStatement.setString(2, (paramString3 != null) ? paramString3 : "%");
      TbResultSetBase tbResultSetBase = (TbResultSetBase)preparedStatement.executeQuery();
      tbResultSetBase.closeStatementOnClose();
      return (ResultSet)tbResultSetBase;
    } 
    byte b1 = 7;
    int bool1 = 0;
    int bool2 = 0;
    int bool3 = 0;
    int bool4 = 0;
    TbStatement tbStatement = new TbStatement(this.conn);
    TbResultSet tbResultSet = TbResultSetFactory.buildEmptyResultSet(tbStatement, -2, b1, 0);
    DataTypeConverter dataTypeConverter = this.conn.getTypeConverter();
    TbColumnDesc[] arrayOfTbColumnDesc = new TbColumnDesc[b1];
    for (byte b2 = 0; b2 < b1; b2++)
      arrayOfTbColumnDesc[b2] = new TbColumnDesc(); 
    arrayOfTbColumnDesc[0].set("TYPE_CAT", 3, bool1, bool2, bool3, bool4);
    arrayOfTbColumnDesc[1].set("TYPE_SCHEM", 3, bool1, bool2, bool3, bool4);
    arrayOfTbColumnDesc[2].set("TYPE_NAME", 3, bool1, bool2, bool3, bool4);
    arrayOfTbColumnDesc[3].set("CLASS_NAME", 3, bool1, bool2, bool3, bool4);
    arrayOfTbColumnDesc[4].set("DATA_TYPE", 1, bool1, bool2, bool3, bool4);
    arrayOfTbColumnDesc[5].set("REMARKS", 3, bool1, bool2, bool3, bool4);
    arrayOfTbColumnDesc[6].set("BASE_TYPE", 1, bool1, bool2, bool3, bool4);
    dataTypeConverter.buildColumnMetadata(arrayOfTbColumnDesc, tbResultSet, arrayOfTbColumnDesc.length);
    ((TbResultSetBase)tbResultSet).closeStatementOnClose();
    return (ResultSet)tbResultSet;
  }
  
  public ResultSet getUDTs(String paramString, boolean paramBoolean) throws SQLException {
    String str;
    if (paramBoolean) {
      str = "";
    } else {
      str = "";
    } 
    PreparedStatement preparedStatement = this.conn.prepareStatement(str);
    preparedStatement.setString(1, paramString);
    TbResultSetBase tbResultSetBase = (TbResultSetBase)preparedStatement.executeQuery();
    tbResultSetBase.closeStatementOnClose();
    return (ResultSet)tbResultSetBase;
  }
  
  public String getURL() throws SQLException {
    return this.conn.info.getURL();
  }
  
  public String getUserName() throws SQLException {
    return this.conn.info.getUser();
  }
  
  public ResultSet getVersionColumns(String paramString1, String paramString2, String paramString3) throws SQLException {
    PreparedStatement preparedStatement = this.conn.prepareStatement(TbDatabaseMetaQuery.getVersionColumnsQuery(this.conn.getMapDateToTimestamp(), this.conn.getExtFeatureInfo().supports(2)));
    preparedStatement.setString(1, paramString3);
    preparedStatement.setString(2, (paramString2 != null) ? paramString2 : "%");
    TbResultSetBase tbResultSetBase = (TbResultSetBase)preparedStatement.executeQuery();
    tbResultSetBase.closeStatementOnClose();
    return (ResultSet)tbResultSetBase;
  }
  
  public boolean insertsAreDetected(int paramInt) throws SQLException {
    return false;
  }
  
  public boolean isCatalogAtStart() throws SQLException {
    return false;
  }
  
  public boolean isReadOnly() throws SQLException {
    return false;
  }
  
  public boolean isWrapperFor(Class<?> paramClass) throws SQLException {
    return paramClass.isInstance(this);
  }
  
  public boolean locatorsUpdateCopy() throws SQLException {
    return true;
  }
  
  public boolean nullPlusNonNullIsNull() throws SQLException {
    return true;
  }
  
  public boolean nullsAreSortedAtEnd() throws SQLException {
    return false;
  }
  
  public boolean nullsAreSortedAtStart() throws SQLException {
    return false;
  }
  
  public boolean nullsAreSortedHigh() throws SQLException {
    return false;
  }
  
  public boolean nullsAreSortedLow() throws SQLException {
    return true;
  }
  
  public boolean othersDeletesAreVisible(int paramInt) throws SQLException {
    return false;
  }
  
  public boolean othersInsertsAreVisible(int paramInt) throws SQLException {
    return false;
  }
  
  public boolean othersUpdatesAreVisible(int paramInt) throws SQLException {
    return (paramInt == 1005);
  }
  
  public boolean ownDeletesAreVisible(int paramInt) throws SQLException {
    return (paramInt != 1007);
  }
  
  public boolean ownInsertsAreVisible(int paramInt) throws SQLException {
    return false;
  }
  
  public boolean ownUpdatesAreVisible(int paramInt) throws SQLException {
    return (paramInt != 1007);
  }
  
  public boolean storesLowerCaseIdentifiers() throws SQLException {
    return false;
  }
  
  public boolean storesLowerCaseQuotedIdentifiers() throws SQLException {
    return false;
  }
  
  public boolean storesMixedCaseIdentifiers() throws SQLException {
    return false;
  }
  
  public boolean storesMixedCaseQuotedIdentifiers() throws SQLException {
    return false;
  }
  
  public boolean storesUpperCaseIdentifiers() throws SQLException {
    return true;
  }
  
  public boolean storesUpperCaseQuotedIdentifiers() throws SQLException {
    return false;
  }
  
  public boolean supportsAlterTableWithAddColumn() throws SQLException {
    return true;
  }
  
  public boolean supportsAlterTableWithDropColumn() throws SQLException {
    return false;
  }
  
  public boolean supportsANSI92EntryLevelSQL() throws SQLException {
    return true;
  }
  
  public boolean supportsANSI92FullSQL() throws SQLException {
    return false;
  }
  
  public boolean supportsANSI92IntermediateSQL() throws SQLException {
    return false;
  }
  
  public boolean supportsBatchUpdates() throws SQLException {
    return true;
  }
  
  public boolean supportsCatalogsInDataManipulation() throws SQLException {
    return false;
  }
  
  public boolean supportsCatalogsInIndexDefinitions() throws SQLException {
    return false;
  }
  
  public boolean supportsCatalogsInPrivilegeDefinitions() throws SQLException {
    return false;
  }
  
  public boolean supportsCatalogsInProcedureCalls() throws SQLException {
    return false;
  }
  
  public boolean supportsCatalogsInTableDefinitions() throws SQLException {
    return false;
  }
  
  public boolean supportsColumnAliasing() throws SQLException {
    return true;
  }
  
  public boolean supportsConvert() throws SQLException {
    return true;
  }
  
  public boolean supportsConvert(int paramInt1, int paramInt2) throws SQLException {
    switch (paramInt1) {
      case 1:
      case 12:
        switch (paramInt2) {
          case 1:
          case 2:
          case 3:
          case 4:
          case 12:
          case 91:
          case 92:
          case 93:
            return true;
        } 
        break;
      case 2:
      case 3:
      case 4:
        switch (paramInt2) {
          case 1:
          case 2:
          case 3:
          case 4:
          case 12:
            return true;
        } 
        break;
      case 91:
      case 92:
      case 93:
        switch (paramInt2) {
          case 1:
          case 12:
          case 91:
          case 92:
          case 93:
            return true;
        } 
        break;
    } 
    return false;
  }
  
  public boolean supportsCoreSQLGrammar() throws SQLException {
    return true;
  }
  
  public boolean supportsCorrelatedSubqueries() throws SQLException {
    return true;
  }
  
  public boolean supportsDataDefinitionAndDataManipulationTransactions() throws SQLException {
    return true;
  }
  
  public boolean supportsDataManipulationTransactionsOnly() throws SQLException {
    return true;
  }
  
  public boolean supportsDifferentTableCorrelationNames() throws SQLException {
    return true;
  }
  
  public boolean supportsExpressionsInOrderBy() throws SQLException {
    return true;
  }
  
  public boolean supportsExtendedSQLGrammar() throws SQLException {
    return true;
  }
  
  public boolean supportsFullOuterJoins() throws SQLException {
    return true;
  }
  
  public boolean supportsGetGeneratedKeys() throws SQLException {
    return true;
  }
  
  public boolean supportsGroupBy() throws SQLException {
    return true;
  }
  
  public boolean supportsGroupByBeyondSelect() throws SQLException {
    return true;
  }
  
  public boolean supportsGroupByUnrelated() throws SQLException {
    return true;
  }
  
  public boolean supportsIntegrityEnhancementFacility() throws SQLException {
    return true;
  }
  
  public boolean supportsLikeEscapeClause() throws SQLException {
    return true;
  }
  
  public boolean supportsLimitedOuterJoins() throws SQLException {
    return true;
  }
  
  public boolean supportsMinimumSQLGrammar() throws SQLException {
    return true;
  }
  
  public boolean supportsMixedCaseIdentifiers() throws SQLException {
    return false;
  }
  
  public boolean supportsMixedCaseQuotedIdentifiers() throws SQLException {
    return true;
  }
  
  public boolean supportsMultipleOpenResults() throws SQLException {
    return false;
  }
  
  public boolean supportsMultipleResultSets() throws SQLException {
    return false;
  }
  
  public boolean supportsMultipleTransactions() throws SQLException {
    return true;
  }
  
  public boolean supportsNamedParameters() throws SQLException {
    return true;
  }
  
  public boolean supportsNonNullableColumns() throws SQLException {
    return true;
  }
  
  public boolean supportsOpenCursorsAcrossCommit() throws SQLException {
    return true;
  }
  
  public boolean supportsOpenCursorsAcrossRollback() throws SQLException {
    return true;
  }
  
  public boolean supportsOpenStatementsAcrossCommit() throws SQLException {
    return true;
  }
  
  public boolean supportsOpenStatementsAcrossRollback() throws SQLException {
    return true;
  }
  
  public boolean supportsOrderByUnrelated() throws SQLException {
    return true;
  }
  
  public boolean supportsOuterJoins() throws SQLException {
    return true;
  }
  
  public boolean supportsPositionedDelete() throws SQLException {
    return false;
  }
  
  public boolean supportsPositionedUpdate() throws SQLException {
    return false;
  }
  
  public boolean supportsResultSetConcurrency(int paramInt1, int paramInt2) throws SQLException {
    switch (paramInt2) {
      case 1007:
      case 1008:
        break;
      default:
        return false;
    } 
    switch (paramInt1) {
      case 1003:
      case 1004:
      case 1005:
        return true;
    } 
    return false;
  }
  
  public boolean supportsResultSetHoldability(int paramInt) throws SQLException {
    return (paramInt == 1);
  }
  
  public boolean supportsResultSetType(int paramInt) throws SQLException {
    switch (paramInt) {
      case 1003:
      case 1004:
        return true;
      case 1005:
        return false;
    } 
    return false;
  }
  
  public boolean supportsSavepoints() throws SQLException {
    return true;
  }
  
  public boolean supportsSchemasInDataManipulation() throws SQLException {
    return true;
  }
  
  public boolean supportsSchemasInIndexDefinitions() throws SQLException {
    return true;
  }
  
  public boolean supportsSchemasInPrivilegeDefinitions() throws SQLException {
    return true;
  }
  
  public boolean supportsSchemasInProcedureCalls() throws SQLException {
    return true;
  }
  
  public boolean supportsSchemasInTableDefinitions() throws SQLException {
    return true;
  }
  
  public boolean supportsSelectForUpdate() throws SQLException {
    return true;
  }
  
  public boolean supportsStatementPooling() throws SQLException {
    return true;
  }
  
  public boolean supportsStoredFunctionsUsingCallSyntax() throws SQLException {
    return true;
  }
  
  public boolean supportsStoredProcedures() throws SQLException {
    return true;
  }
  
  public boolean supportsSubqueriesInComparisons() throws SQLException {
    return true;
  }
  
  public boolean supportsSubqueriesInExists() throws SQLException {
    return true;
  }
  
  public boolean supportsSubqueriesInIns() throws SQLException {
    return true;
  }
  
  public boolean supportsSubqueriesInQuantifieds() throws SQLException {
    return true;
  }
  
  public boolean supportsTableCorrelationNames() throws SQLException {
    return true;
  }
  
  public boolean supportsTransactionIsolationLevel(int paramInt) throws SQLException {
    switch (paramInt) {
      case 2:
      case 8:
        return true;
    } 
    return false;
  }
  
  public boolean supportsTransactions() throws SQLException {
    return true;
  }
  
  public boolean supportsUnion() throws SQLException {
    return true;
  }
  
  public boolean supportsUnionAll() throws SQLException {
    return true;
  }
  
  public <T> T unwrap(Class<T> paramClass) throws SQLException {
    try {
      return paramClass.cast(this);
    } catch (ClassCastException classCastException) {
      throw TbError.newSQLException(-90657);
    } 
  }
  
  public boolean updatesAreDetected(int paramInt) throws SQLException {
    return false;
  }
  
  public boolean usesLocalFilePerTable() throws SQLException {
    return false;
  }
  
  public boolean usesLocalFiles() throws SQLException {
    return false;
  }
}


/* Location:              C:\TmaxData\tibero6\client\lib\jar\tibero6-jdbc.jar!\com\tmax\tibero\jdbc\TbDatabaseMetaData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */