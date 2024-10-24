package ch.admin.bar.siard2.jdbc;

import ch.enterag.utils.jdbc.BaseResultSetMetaData;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Date;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;









public class MySqlResultSetMetaData
  extends BaseResultSetMetaData
  implements ResultSetMetaData
{
  private String _sPrimaryColumn = null;
  private static Map<String, Class<?>> mapCLASS_MYSQL_TO_ISO = new HashMap<>();
  
  static {
    mapCLASS_MYSQL_TO_ISO.put(byte[].class.getName(), byte[].class);
    mapCLASS_MYSQL_TO_ISO.put(String.class.getName(), String.class);
    mapCLASS_MYSQL_TO_ISO.put(Boolean.class.getName(), Boolean.class);
    mapCLASS_MYSQL_TO_ISO.put(Short.class.getName(), Short.class);
    mapCLASS_MYSQL_TO_ISO.put(Integer.class.getName(), Integer.class);
    mapCLASS_MYSQL_TO_ISO.put(BigInteger.class.getName(), BigInteger.class);
    mapCLASS_MYSQL_TO_ISO.put(Long.class.getName(), Long.class);
    mapCLASS_MYSQL_TO_ISO.put(Float.class.getName(), Float.class);
    mapCLASS_MYSQL_TO_ISO.put(Double.class.getName(), Double.class);
    mapCLASS_MYSQL_TO_ISO.put(BigDecimal.class.getName(), BigDecimal.class);
    mapCLASS_MYSQL_TO_ISO.put(Date.class.getName(), Date.class);
    mapCLASS_MYSQL_TO_ISO.put(Time.class.getName(), Time.class);
    mapCLASS_MYSQL_TO_ISO.put(Timestamp.class.getName(), Timestamp.class);
    
    mapCLASS_MYSQL_TO_ISO.put(LocalDateTime.class.getName(), Timestamp.class);
  }






  
  public MySqlResultSetMetaData(ResultSetMetaData rsmdWrapped, String sPrimaryColumn) {
    super(rsmdWrapped);
    this._sPrimaryColumn = sPrimaryColumn;
  }




  
  public int getColumnCount() throws SQLException {
    int iColumnCount = super.getColumnCount();
    if (this._sPrimaryColumn != null)
      iColumnCount--; 
    return iColumnCount;
  }





  
  public int getColumnType(int column) throws SQLException {
    return MySqlMetaColumns.getDataType(getColumnTypeName(column).toLowerCase());
  }





  
  public String getColumnClassName(int column) throws SQLException {
    String sClassName = super.getColumnClassName(column);
    String sTypeName = getColumnTypeName(column);
    Class<?> cls = mapCLASS_MYSQL_TO_ISO.get(sClassName);
    if (sTypeName.equals("GEOMETRY") || sTypeName
      .equals("POINT") || sTypeName
      .equals("LINESTRING") || sTypeName
      .equals("POLYGON") || sTypeName
      .equals("MULTIPOINT") || sTypeName
      .equals("MULTILINESTRING") || sTypeName
      .equals("MULTIPOLYGON") || sTypeName
      .equals("GEOMETRYCOLLECTION")) {
      cls = String.class;
    }
    
    if (cls == null) {
      throw new IllegalStateException(String.format("No MySql to ISO mapping found for class name %s (column %d)", new Object[] { sClassName, 

              
              Integer.valueOf(column) }));
    }

    
    return cls.getName();
  }






  
  public boolean isCaseSensitive(int column) throws SQLException {
    return super.isCaseSensitive(column);
  }
}


/* Location:              C:\Users\lenovo\IdeaProjects\siard-lib\lib\jdbcmysql-2.2.2.jar!\ch\admin\bar\siard2\jdbc\MySqlResultSetMetaData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */