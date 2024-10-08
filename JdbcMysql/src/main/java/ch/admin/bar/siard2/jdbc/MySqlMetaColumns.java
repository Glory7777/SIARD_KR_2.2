package ch.admin.bar.siard2.jdbc;

import ch.admin.bar.siard2.mysql.MySqlType;
import ch.enterag.sqlparser.datatype.enums.PreType;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;













public class MySqlMetaColumns
  extends MySqlResultSet
{
  private static Map<MySqlType, PreType> mapNAME_MYSQL_TO_ISO = new HashMap<>();
  private static final int iMAX_VARCHAR_LENGTH = 21845;
  
  static {
    mapNAME_MYSQL_TO_ISO.put(MySqlType.BIGINT, PreType.BIGINT);
    mapNAME_MYSQL_TO_ISO.put(MySqlType.BIGINTU, PreType.BIGINT);
    mapNAME_MYSQL_TO_ISO.put(MySqlType.BINARY, PreType.BINARY);
    mapNAME_MYSQL_TO_ISO.put(MySqlType.BIT, PreType.BOOLEAN);
    mapNAME_MYSQL_TO_ISO.put(MySqlType.BLOB, PreType.BLOB);
    mapNAME_MYSQL_TO_ISO.put(MySqlType.BOOL, PreType.BOOLEAN);
    mapNAME_MYSQL_TO_ISO.put(MySqlType.BOOLEAN, PreType.BOOLEAN);
    mapNAME_MYSQL_TO_ISO.put(MySqlType.CHAR, PreType.CHAR);
    mapNAME_MYSQL_TO_ISO.put(MySqlType.DATE, PreType.DATE);
    mapNAME_MYSQL_TO_ISO.put(MySqlType.DATETIME, PreType.TIMESTAMP);
    mapNAME_MYSQL_TO_ISO.put(MySqlType.DECIMAL, PreType.DECIMAL);
    mapNAME_MYSQL_TO_ISO.put(MySqlType.DECIMALU, PreType.DECIMAL);
    mapNAME_MYSQL_TO_ISO.put(MySqlType.DOUBLE, PreType.DOUBLE);
    mapNAME_MYSQL_TO_ISO.put(MySqlType.DOUBLEU, PreType.DOUBLE);
    mapNAME_MYSQL_TO_ISO.put(MySqlType.ENUM, PreType.VARCHAR);
    mapNAME_MYSQL_TO_ISO.put(MySqlType.FLOAT, PreType.FLOAT);
    mapNAME_MYSQL_TO_ISO.put(MySqlType.FLOATU, PreType.FLOAT);
    mapNAME_MYSQL_TO_ISO.put(MySqlType.INT, PreType.INTEGER);
    mapNAME_MYSQL_TO_ISO.put(MySqlType.INTU, PreType.BIGINT);
    mapNAME_MYSQL_TO_ISO.put(MySqlType.INTEGER, PreType.INTEGER);
    mapNAME_MYSQL_TO_ISO.put(MySqlType.INTEGERU, PreType.BIGINT);
    mapNAME_MYSQL_TO_ISO.put(MySqlType.LONGBLOB, PreType.BLOB);
    mapNAME_MYSQL_TO_ISO.put(MySqlType.LONGTEXT, PreType.CLOB);
    mapNAME_MYSQL_TO_ISO.put(MySqlType.MEDIUMBLOB, PreType.BLOB);
    mapNAME_MYSQL_TO_ISO.put(MySqlType.MEDIUMINT, PreType.INTEGER);
    mapNAME_MYSQL_TO_ISO.put(MySqlType.MEDIUMINTU, PreType.BIGINT);
    mapNAME_MYSQL_TO_ISO.put(MySqlType.MEDIUMTEXT, PreType.CLOB);
    mapNAME_MYSQL_TO_ISO.put(MySqlType.SET, PreType.VARCHAR);
    mapNAME_MYSQL_TO_ISO.put(MySqlType.SMALLINT, PreType.SMALLINT);
    mapNAME_MYSQL_TO_ISO.put(MySqlType.SMALLINTU, PreType.INTEGER);
    mapNAME_MYSQL_TO_ISO.put(MySqlType.TEXT, PreType.CLOB);
    mapNAME_MYSQL_TO_ISO.put(MySqlType.TIME, PreType.TIME);
    mapNAME_MYSQL_TO_ISO.put(MySqlType.TIMESTAMP, PreType.TIMESTAMP);
    mapNAME_MYSQL_TO_ISO.put(MySqlType.TINYBLOB, PreType.VARBINARY);
    mapNAME_MYSQL_TO_ISO.put(MySqlType.TINYINT, PreType.SMALLINT);
    mapNAME_MYSQL_TO_ISO.put(MySqlType.TINYINTU, PreType.SMALLINT);
    mapNAME_MYSQL_TO_ISO.put(MySqlType.TINYTEXT, PreType.VARCHAR);
    mapNAME_MYSQL_TO_ISO.put(MySqlType.VARBINARY, PreType.VARBINARY);
    mapNAME_MYSQL_TO_ISO.put(MySqlType.VARCHAR, PreType.VARCHAR);
    mapNAME_MYSQL_TO_ISO.put(MySqlType.YEAR, PreType.SMALLINT);
    mapNAME_MYSQL_TO_ISO.put(MySqlType.GEOMETRY, PreType.CLOB);
    mapNAME_MYSQL_TO_ISO.put(MySqlType.POINT, PreType.CLOB);
    mapNAME_MYSQL_TO_ISO.put(MySqlType.LINESTRING, PreType.CLOB);
    mapNAME_MYSQL_TO_ISO.put(MySqlType.POLYGON, PreType.CLOB);
    mapNAME_MYSQL_TO_ISO.put(MySqlType.MULTIPOINT, PreType.CLOB);
    mapNAME_MYSQL_TO_ISO.put(MySqlType.MULTILINESTRING, PreType.CLOB);
    mapNAME_MYSQL_TO_ISO.put(MySqlType.MULTIPOLYGON, PreType.CLOB);
    mapNAME_MYSQL_TO_ISO.put(MySqlType.GEOMETRYCOLLECTION, PreType.CLOB);
    mapNAME_MYSQL_TO_ISO.put(MySqlType.JSON, PreType.CLOB);
  }

  
  private int _iDataType = -1;
  private int _iTypeName = -1;
  private int _iPrecision = -1;
  private int _iLength = -1;
  private ResultSet _rsUnwrapped = null;







  
  public MySqlMetaColumns(ResultSet rsWrapped, int iDataType, int iTypeName, int iPrecision, int iLength, MySqlConnection conn) throws SQLException {
    super(rsWrapped, conn);
    this._iDataType = iDataType;
    this._iTypeName = iTypeName;
    this._iPrecision = iPrecision;
    this._iLength = iLength;
    this._rsUnwrapped = unwrap(ResultSet.class);
  }






  
  static int getDataType(String sTypeName) {
    MySqlType mst = MySqlType.getByTypeName(sTypeName);
    PreType pt = mapNAME_MYSQL_TO_ISO.get(mst);
    return pt.getSqlType();
  }







  
  static long getColumnSize(long lColumnSize, String sTypeName) {
    if (lColumnSize <= 0L) {
      
      int iDataType = getDataType(sTypeName);
      if (iDataType == 12 || iDataType == -9 || iDataType == -3)
      {
        
        lColumnSize = 21845L; } 
    } 
    return lColumnSize;
  }



  
  public int getInt(int columnIndex) throws SQLException {
    int iResult = -1;
    if (columnIndex == this._iDataType) {
      iResult = getDataType(this._rsUnwrapped.getString(this._iTypeName));
    } else if (columnIndex == this._iPrecision || columnIndex == this._iLength) {

      
      int iLength = this._rsUnwrapped.getInt(this._iPrecision);
      if (iLength <= 0)
        iLength = this._rsUnwrapped.getInt(this._iLength); 
      iResult = (int)getColumnSize(iLength, this._rsUnwrapped
          
          .getString(this._iTypeName));
    } else {
      
      iResult = super.getInt(columnIndex);
    }  return iResult;
  }








  
  public long getLong(int columnIndex) throws SQLException {
    long lResult = -1L;
    if (columnIndex == this._iPrecision || columnIndex == this._iLength) {

      
      long lLength = this._rsUnwrapped.getLong(this._iPrecision);
      if (lLength <= 0L)
        lLength = this._rsUnwrapped.getLong(this._iLength); 
      lResult = getColumnSize(lLength, this._rsUnwrapped
          
          .getString(this._iTypeName));
    }
    else if (columnIndex == this._iDataType) {
      lResult = getDataType(this._rsUnwrapped.getString(this._iTypeName));
    } else {
      lResult = this._rsUnwrapped.getLong(columnIndex);
    }  return lResult;
  }



  
  public Object getObject(int columnIndex) throws SQLException {
    Object oResult = this._rsUnwrapped.getObject(columnIndex);

    
    if (oResult instanceof Integer) {
      oResult = Integer.valueOf(getInt(columnIndex));
    } else if (oResult instanceof Long) {
      oResult = Long.valueOf(getLong(columnIndex));
    } else if (oResult instanceof String) {
      oResult = getString(columnIndex);
    } 
    if (columnIndex == this._iLength) {
      oResult = Long.valueOf(getLong(columnIndex));
    }
    return oResult;
  }




  
  public boolean next() throws SQLException {
    return this._rsUnwrapped.next();
  }
}


/* Location:              C:\Users\lenovo\IdeaProjects\siard-lib\lib\jdbcmysql-2.2.2.jar!\ch\admin\bar\siard2\jdbc\MySqlMetaColumns.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */