package ch.admin.bar.siard2.mysql.datatype;

import ch.enterag.sqlparser.SqlFactory;
import ch.enterag.sqlparser.datatype.PredefinedType;
import ch.enterag.sqlparser.datatype.enums.PreType;
import java.util.HashMap;
import java.util.Map;
















public class MySqlPredefinedType
  extends PredefinedType
{
  private static Map<PreType, String> mapISO_TO_MYSQL = new HashMap<>();
  
  static {
    mapISO_TO_MYSQL.put(PreType.CHAR, "CHAR");
    mapISO_TO_MYSQL.put(PreType.VARCHAR, "VARCHAR");
    mapISO_TO_MYSQL.put(PreType.CLOB, "LONGTEXT");
    mapISO_TO_MYSQL.put(PreType.NCHAR, "NCHAR");
    mapISO_TO_MYSQL.put(PreType.NVARCHAR, "NVARCHAR");
    mapISO_TO_MYSQL.put(PreType.NCLOB, "LONGTEXT");
    mapISO_TO_MYSQL.put(PreType.XML, "LONGTEXT");
    mapISO_TO_MYSQL.put(PreType.BINARY, "BINARY");
    mapISO_TO_MYSQL.put(PreType.VARBINARY, "VARBINARY");
    mapISO_TO_MYSQL.put(PreType.BLOB, "LONGBLOB");
    mapISO_TO_MYSQL.put(PreType.NUMERIC, "NUMERIC");
    mapISO_TO_MYSQL.put(PreType.DECIMAL, "DECIMAL");
    mapISO_TO_MYSQL.put(PreType.SMALLINT, "SMALLINT");
    mapISO_TO_MYSQL.put(PreType.INTEGER, "INTEGER");
    mapISO_TO_MYSQL.put(PreType.BIGINT, "BIGINT");
    mapISO_TO_MYSQL.put(PreType.FLOAT, "FLOAT");
    mapISO_TO_MYSQL.put(PreType.REAL, "REAL");
    mapISO_TO_MYSQL.put(PreType.DOUBLE, "DOUBLE");
    mapISO_TO_MYSQL.put(PreType.BOOLEAN, "BOOLEAN");
    mapISO_TO_MYSQL.put(PreType.DATE, "DATE");
    mapISO_TO_MYSQL.put(PreType.TIME, "TIME");
    mapISO_TO_MYSQL.put(PreType.TIMESTAMP, "DATETIME");
    mapISO_TO_MYSQL.put(PreType.INTERVAL, "VARBINARY");
    mapISO_TO_MYSQL.put(PreType.DATALINK, "LONGBLOB");
  }



  
  public MySqlPredefinedType(SqlFactory sf) {
    super(sf);
  }






  
  protected String formatSecondsDecimals(int iDefaultDecimals) {
    String sSecondsDecimals = "";
    int iSecondsDecimals = getSecondsDecimals();
    if (iSecondsDecimals != -1) {
      
      if (iSecondsDecimals > 6)
        iSecondsDecimals = 6; 
      if (iSecondsDecimals != iDefaultDecimals)
        sSecondsDecimals = sSecondsDecimals + "(" + String.valueOf(iSecondsDecimals) + ")"; 
    } 
    return sSecondsDecimals;
  }





  
  public String format() {
    String sType = null;
    sType = mapISO_TO_MYSQL.get(getType());
    if (getType() == PreType.NUMERIC || 
      getType() == PreType.DECIMAL) {
      sType = sType + formatPrecisionScale();
    } else if (getType() == PreType.FLOAT || 
      getType() == PreType.REAL || 
      getType() == PreType.DOUBLE || 
      getType() == PreType.FLOAT) {
      sType = sType + formatPrecisionScale();
    } else if (getType() == PreType.SMALLINT || 
      getType() == PreType.INTEGER || 
      getType() == PreType.BIGINT) {
      sType = sType + formatPrecisionScale();
    } else if (getType() == PreType.CHAR || 
      getType() == PreType.VARCHAR || 
      getType() == PreType.NCHAR || 
      getType() == PreType.NVARCHAR) {
      
      sType = sType + formatLength();
      long l = getLength();
      if (l == -1L && (
        getType() == PreType.CHAR || getType() == PreType.NCHAR))
        l = 1L; 
      if (l != -1L) {
        
        if (l >= 16777216L) {
          sType = "LONGTEXT";
        } else if (l >= 65536L) {
          sType = "MEDIUMTEXT";
        } else if (l >= 256L) {
          sType = "TEXT";
        } 
      } else {
        sType = "TEXT";
      } 
    } else if (getType() == PreType.BINARY || 
      getType() == PreType.VARBINARY) {
      
      sType = sType + formatLength();
      long l = getLength();
      if (l == -1L && 
        getType() == PreType.BINARY)
        l = 1L; 
      if (getLength() != -1L) {
        
        if (l >= 16777216L) {
          sType = "LONGBLOB";
        } else if (l >= 65536L) {
          sType = "MEDIUMBLOB";
        } else if (l >= 256L) {
          sType = "BLOB";
        } 
      } else {
        sType = "BLOB";
      } 
    } else if (getType() == PreType.TIME || 
      getType() == PreType.TIMESTAMP || 
      getType() == PreType.DATE) {
      sType = sType + formatSecondsDecimals(0);
    } else if (getType() == PreType.INTERVAL) {
      sType = sType + "(255)";
    } else if (getType() == PreType.CLOB || 
      getType() == PreType.NCLOB) {
      
      long l = getLength();
      if (getLength() != -1L) {
        
        if (getMultiplier() != null)
          l *= getMultiplier().getValue(); 
        if (l < 65536L) {
          sType = "TEXT";
        } else if (l < 16777216L) {
          sType = "MEDIUMTEXT";
        } 
      } 
    } else if (getType() == PreType.BLOB || getType() == PreType.DATALINK) {
      
      if (getLength() != -1) {
        
        long l = getLength();
        if (getMultiplier() != null)
          l *= getMultiplier().getValue(); 
        if (l < 65536L) {
          sType = "BLOB";
        } else if (l < 16777216L) {
          sType = "MEDIUMBLOB";
        } 
      } 
    }  if (sType.startsWith("CHAR") || sType
      .startsWith("VARCHAR") || sType
      .startsWith("TEXT") || sType
      .startsWith("MEDIUMTEXT") || sType
      .startsWith("LONGTEXT"))
      sType = sType + " BINARY"; 
    return sType;
  }
}


/* Location:              C:\Users\lenovo\IdeaProjects\siard-lib\lib\jdbcmysql-2.2.2.jar!\ch\admin\bar\siard2\mysql\datatype\MySqlPredefinedType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */