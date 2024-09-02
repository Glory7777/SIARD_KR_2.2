package ch.admin.bar.siard2.cmd;

import ch.enterag.utils.database.SqlTypes;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;




class ObjectValueReader
{
  private final ResultSet resultSet;
  private final int dataType;
  private final int position;
  
  public ObjectValueReader(ResultSet resultSet, int dataType, int position) {
    this.resultSet = resultSet; this.dataType = dataType; this.position = position;
  }



  
  public Object read() throws SQLException {
    BigDecimal bdInt;
    Object oValue = null;
    switch (this.dataType) {
      case 1:
      case 12:
        oValue = this.resultSet.getString(this.position);


































































        
        return oValue;case 2005: oValue = this.resultSet.getClob(this.position); return oValue;case 2009: oValue = this.resultSet.getSQLXML(this.position); return oValue;case -15: case -9: oValue = this.resultSet.getNString(this.position); return oValue;case 2011: oValue = this.resultSet.getNClob(this.position); return oValue;case -3: case -2: oValue = this.resultSet.getBytes(this.position); return oValue;case 2004: oValue = this.resultSet.getBlob(this.position); return oValue;case 70: oValue = this.resultSet.getURL(this.position); return oValue;case 16: oValue = Boolean.valueOf(this.resultSet.getBoolean(this.position)); return oValue;case 5: oValue = Integer.valueOf(this.resultSet.getInt(this.position)); return oValue;case 4: oValue = Long.valueOf(this.resultSet.getLong(this.position)); return oValue;case -5: bdInt = this.resultSet.getBigDecimal(this.position); if (bdInt != null) oValue = bdInt.toBigIntegerExact();  return oValue;case 2: case 3: oValue = this.resultSet.getBigDecimal(this.position); return oValue;case 7: oValue = Float.valueOf(this.resultSet.getFloat(this.position)); return oValue;case 6: case 8: oValue = Double.valueOf(this.resultSet.getDouble(this.position)); return oValue;case 91: oValue = this.resultSet.getDate(this.position); return oValue;case 92: oValue = this.resultSet.getTime(this.position); return oValue;case 93: oValue = this.resultSet.getTimestamp(this.position); return oValue;case 1111: case 2002: oValue = this.resultSet.getObject(this.position); return oValue;case 2003: oValue = this.resultSet.getArray(this.position); return oValue;
    } 
    throw new SQLException("Invalid data type " + this.dataType + " (" + SqlTypes.getTypeName(this.dataType) + ") encountered!");
  }
}


/* Location:              C:\Users\lenovo\IdeaProjects\SIARD_KR_2.2\lib\siardcmd.jar!\ch\admin\bar\siard2\cmd\ObjectValueReader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */