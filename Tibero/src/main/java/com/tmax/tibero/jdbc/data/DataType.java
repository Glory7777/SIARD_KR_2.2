package com.tmax.tibero.jdbc.data;

import com.tmax.tibero.jdbc.err.TbError;
import java.sql.SQLException;

public class DataType {
  public static final int NONE = 0;
  
  public static final int NUMBER = 1;
  
  public static final int CHAR = 2;
  
  public static final int VARCHAR = 3;
  
  public static final int RAW = 4;
  
  public static final int DATE = 5;
  
  public static final int TIME = 6;
  
  public static final int TIMESTAMP = 7;
  
  public static final int TIMESTAMP_TZ = 21;
  
  public static final int TIMESTAMP_LTZ = 22;
  
  public static final int ITV_YTM = 8;
  
  public static final int ITV_DTS = 9;
  
  public static final int LONG = 10;
  
  public static final int LONGRAW = 11;
  
  public static final int BLOB = 12;
  
  public static final int CLOB = 13;
  
  public static final int BFILE = 14;
  
  public static final int ROWID = 15;
  
  public static final int CURSOR = 16;
  
  public static final int UNKNOWN = 17;
  
  public static final int NCHAR = 18;
  
  public static final int NVARCHAR = 19;
  
  public static final int NCLOB = 20;
  
  public static final int BIN_FLOAT = 23;
  
  public static final int BIN_DOUBLE = 24;
  
  public static final int BOOLEAN = 25;
  
  public static final int PLS_INT = 26;
  
  public static final int BIN_INT = 27;
  
  public static final int RECORD = 28;
  
  public static final int VARRAY = 29;
  
  public static final int TABLE = 30;
  
  public static final int IDX_BY_TBL = 31;
  
  public static final int OBJECT = 32;
  
  public static final int REF = 33;
  
  public static final int LGEOMETRY = 1;
  
  public static final int LXML = 2;
  
  public static final int LPIVOT = 3;
  
  public static void checkValidDataType(int paramInt) throws SQLException {
    switch (paramInt) {
      case 1:
      case 2:
      case 3:
      case 4:
      case 5:
      case 6:
      case 7:
      case 8:
      case 9:
      case 10:
      case 11:
      case 12:
      case 13:
      case 15:
      case 16:
      case 17:
      case 18:
      case 19:
      case 20:
      case 21:
      case 22:
      case 23:
      case 24:
      case 28:
      case 29:
      case 30:
      case 32:
      case 33:
        return;
    } 
    throw TbError.newSQLException(-590703, Integer.toString(paramInt));
  }
  
  public static int getDataType(int paramInt) throws SQLException {
    switch (paramInt) {
      case -7:
      case 1:
        return 2;
      case -4:
      case -3:
      case -2:
        return 4;
      case -1:
      case 12:
        return 3;
      case -6:
      case -5:
      case 2:
      case 3:
      case 4:
      case 5:
      case 6:
      case 7:
      case 8:
        return 1;
      case 91:
      case 92:
        return 5;
      case 93:
        return 7;
      case 2004:
        return 12;
      case 2005:
        return 13;
      case -17:
      case -10:
        return 16;
      case 101:
        return 24;
      case 100:
        return 23;
      case -15:
        return 18;
      case -9:
        return 19;
      case 2011:
        return 13;
      case 2009:
        return 13;
      case 0:
        return 3;
      case 2003:
        return 29;
      case 2002:
        return 32;
      case -2003:
        return 30;
      case 2006:
        return 33;
    } 
    throw TbError.newSQLException(-590704, Integer.toString(paramInt));
  }
  
  public static int toIntDataType(String paramString) throws SQLException {
    return paramString.equals("CHAR") ? 2 : ((paramString.equals("VARCHAR") || paramString.equals("VARCHAR2")) ? 3 : (paramString.equals("NUMBER") ? 1 : (paramString.equals("DATE") ? 5 : (paramString.equals("TIME") ? 6 : (paramString.equals("TIMESTAMP") ? 7 : (paramString.equals("INTERVAL YEAR TO MONTH") ? 8 : (paramString.equals("INTERVAL DAY TO SECOND") ? 9 : (paramString.equals("RAW") ? 4 : (paramString.equals("GEOMETRY") ? 12 : (paramString.equals("BLOB") ? 12 : (paramString.equals("XMLTYPE") ? 13 : (paramString.equals("CLOB") ? 13 : (paramString.equals("BINARY_DOUBLE") ? 24 : (paramString.equals("BINARY_FLOAT") ? 23 : (paramString.equals("BFILE") ? 14 : (paramString.equals("LONG") ? 10 : (paramString.equals("LONG RAW") ? 11 : (paramString.equals("ROWID") ? 15 : (paramString.equals("NCHAR") ? 18 : (paramString.equals("NVARCHAR") ? 19 : (paramString.equals("NCLOB") ? 20 : (paramString.equals("CURSOR") ? 16 : (paramString.equals("VARRAY") ? 29 : (paramString.equals("STRUCT") ? 28 : (paramString.equals("TABLE") ? 30 : 32)))))))))))))))))))))))));
  }
  
  public static String getDataTypeClassName(int paramInt) throws SQLException {
    switch (paramInt) {
      case 1:
        return "java.math.BigDecimal";
      case 24:
        return "java.lang.Double";
      case 23:
        return "java.lang.Float";
      case 2:
      case 3:
      case 10:
      case 18:
      case 19:
        return "java.lang.String";
      case 5:
        return "java.sql.Date";
      case 6:
        return "java.sql.Time";
      case 7:
      case 21:
      case 22:
        return "java.sql.Timestamp";
      case 4:
      case 11:
        return "byte[]";
      case 15:
        return "com.tmax.tibero.jdbc.TbRowId";
      case 12:
        return "java.sql.Blob";
      case 20:
        return "java.sql.NClob";
      case 13:
        return "java.sql.Clob";
      case 16:
        return "java.sql.ResultSet";
      case 9:
        return "com.tmax.tibero.jdbc.TbIntervalDts";
      case 8:
        return "com.tmax.tibero.jdbc.TbIntervalYtm";
      case 29:
        return "java.sql.Array";
      case 28:
      case 32:
        return "java.lang.Object";
      case 30:
        return "java.sql.Array";
      case 33:
        return "java.sql.Ref";
    } 
    throw TbError.newSQLException(-590703, Integer.toString(paramInt));
  }
  
  public static String getDBTypeName(int paramInt) throws SQLException {
    return getDBTypeName(paramInt, 0);
  }
  
  public static String getDBTypeName(int paramInt1, int paramInt2) throws SQLException {
    switch (paramInt1) {
      case 2:
        return "CHAR";
      case 3:
        return "VARCHAR2";
      case 1:
        return "NUMBER";
      case 5:
        return "DATE";
      case 6:
        return "TIME";
      case 21:
        return "TIMESTAMP WITH TIME ZONE";
      case 22:
        return "TIMESTAMP WITH LOCAL TIME ZONE";
      case 7:
        return "TIMESTAMP";
      case 8:
        return "INTERVAL YEAR TO MONTH";
      case 9:
        return "INTERVAL DAY TO SECOND";
      case 4:
        return "RAW";
      case 12:
        return (1 == paramInt2) ? "GEOMETRY" : ((3 == paramInt2) ? "PIVOT" : "BLOB");
      case 13:
        return (2 == paramInt2) ? "XMLTYPE" : "CLOB";
      case 24:
        return "BINARY_DOUBLE";
      case 23:
        return "BINARY_FLOAT";
      case 14:
        return "BFILE";
      case 10:
        return "LONG";
      case 11:
        return "LONG RAW";
      case 15:
        return "ROWID";
      case 18:
        return "NCHAR";
      case 19:
        return "NVARCHAR";
      case 20:
        return "NCLOB";
      case 16:
        return "CURSOR";
      case 29:
        return "VARRAY";
      case 28:
        return "STRUCT";
      case 32:
        return "OBJECT";
      case 30:
        return "TABLE";
      case 33:
        return "REF";
    } 
    throw TbError.newSQLException(-590703, Integer.toString(paramInt1));
  }
  
  public static int getSqlType(int paramInt) throws SQLException {
    return getSqlType(paramInt, 0);
  }
  
  public static int getSqlType(int paramInt1, int paramInt2) throws SQLException {
    return getSqlType(paramInt1, 0, true);
  }
  
  public static int getSqlType(int paramInt1, int paramInt2, boolean paramBoolean) throws SQLException {
    switch (paramInt1) {
      case 0:
        return 0;
      case 18:
        return -15;
      case 2:
        return 1;
      case 19:
        return -9;
      case 3:
        return 12;
      case 1:
        return 2;
      case 24:
        return 101;
      case 23:
        return 100;
      case 5:
        return paramBoolean ? 93 : 91;
      case 6:
        return 92;
      case 7:
      case 21:
      case 22:
        return 93;
      case 8:
      case 9:
        return 1111;
      case 15:
        return 1;
      case 4:
        return -3;
      case 12:
        return (1 == paramInt2) ? 26 : ((3 == paramInt2) ? 27 : 2004);
      case 20:
        return 2011;
      case 13:
        return (2 == paramInt2) ? 2009 : 2005;
      case 10:
        return -1;
      case 11:
        return -4;
      case 16:
        return -17;
      case 29:
        return 2003;
      case 30:
        return -2003;
      case 28:
      case 32:
        return 2002;
      case 33:
        return 2006;
    } 
    throw TbError.newSQLException(-590703, Integer.toString(paramInt1));
  }
  
  public static int getSqlType(Object paramObject) throws SQLException {
    if (paramObject == null)
      return 1; 
    if (paramObject instanceof String)
      return 12; 
    if (paramObject instanceof java.math.BigDecimal)
      return 2; 
    if (paramObject instanceof java.math.BigInteger)
      return 2; 
    if (paramObject instanceof Integer)
      return 4; 
    if (paramObject instanceof Short)
      return 5; 
    if (paramObject instanceof Long)
      return -5; 
    if (paramObject instanceof Float)
      return 7; 
    if (paramObject instanceof Double)
      return 8; 
    if (paramObject instanceof byte[])
      return -3; 
    if (paramObject instanceof java.sql.Date || paramObject instanceof TbDate)
      return 91; 
    if (paramObject instanceof java.sql.Time)
      return 92; 
    if (paramObject instanceof java.sql.Timestamp || paramObject instanceof TbTimestamp)
      return 93; 
    if (paramObject instanceof Boolean)
      return -7; 
    if (paramObject instanceof java.sql.NClob)
      return 2011; 
    if (paramObject instanceof java.sql.Clob)
      return 2005; 
    if (paramObject instanceof java.sql.Blob)
      return 2004; 
    if (paramObject instanceof java.sql.Array)
      return 2003; 
    if (paramObject instanceof java.sql.Struct || paramObject instanceof java.sql.SQLData)
      return 2002; 
    if (paramObject instanceof java.sql.Ref)
      return 2006; 
    if (paramObject instanceof java.net.URL)
      return 70; 
    if (paramObject instanceof java.sql.SQLXML)
      return 2009; 
    if (paramObject instanceof java.sql.RowId)
      return -8; 
    throw TbError.newSQLException(-590703, paramObject.toString());
  }
  
  public static boolean isCharacterCategory(int paramInt) {
    return (paramInt == 2 || paramInt == 3);
  }
  
  public static boolean isDateCategory(int paramInt) {
    return (paramInt == 5 || paramInt == 6 || paramInt == 7);
  }
  
  public static boolean isLobCategory(int paramInt) {
    return (paramInt == 13 || paramInt == 12 || paramInt == 20);
  }
  
  public static boolean isLocatorCategory(int paramInt) {
    return (paramInt == 13 || paramInt == 12 || paramInt == 20 || paramInt == 10 || paramInt == 11);
  }
  
  public static boolean isLongCategory(int paramInt) {
    return (paramInt == 10 || paramInt == 11);
  }
  
  public static boolean isNationalCategory(int paramInt) {
    return (paramInt == 18 || paramInt == 19);
  }
  
  public static boolean isNumberCategory(int paramInt) {
    return (paramInt == 1);
  }
  
  public static boolean isBinaryDoubleCategory(int paramInt) {
    return (paramInt == 24);
  }
  
  public static boolean isBinaryFloatCategory(int paramInt) {
    return (paramInt == 23);
  }
  
  public static boolean isUDTCategory(int paramInt) {
    return (paramInt == 32 || paramInt == 28 || paramInt == 29 || paramInt == 30 || paramInt == 31);
  }
}


/* Location:              C:\TmaxData\tibero6\client\lib\jar\tibero6-jdbc.jar!\com\tmax\tibero\jdbc\data\DataType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */