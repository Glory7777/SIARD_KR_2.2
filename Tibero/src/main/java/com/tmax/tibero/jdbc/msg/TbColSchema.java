package com.tmax.tibero.jdbc.msg;

import com.tmax.tibero.jdbc.comm.TbStreamDataReader;
import java.sql.SQLException;

public class TbColSchema {
  public String colName;
  
  public int typeNo;
  
  public int len;
  
  public int precision;
  
  public int scale;
  
  public int notNull;
  
  public int property;
  
  public void set(String paramString, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6) {
    this.colName = paramString;
    this.typeNo = paramInt1;
    this.len = paramInt2;
    this.precision = paramInt3;
    this.scale = paramInt4;
    this.notNull = paramInt5;
    this.property = paramInt6;
  }
  
  public void deserialize(TbStreamDataReader paramTbStreamDataReader) throws SQLException {
    int i = paramTbStreamDataReader.readInt32();
    this.colName = paramTbStreamDataReader.readDBDecodedPadString(i);
    this.typeNo = paramTbStreamDataReader.readInt32();
    this.len = paramTbStreamDataReader.readInt32();
    this.precision = paramTbStreamDataReader.readInt32();
    this.scale = paramTbStreamDataReader.readInt32();
    this.notNull = paramTbStreamDataReader.readInt32();
    this.property = paramTbStreamDataReader.readInt32();
  }
}


/* Location:              C:\Users\Lenovo\Desktop\tibero\tibero6-jdbc.jar!\com\tmax\tibero\jdbc\msg\TbColSchema.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */