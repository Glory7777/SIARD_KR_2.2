package com.tmax.tibero.jdbc.msg;

import com.tmax.tibero.jdbc.comm.TbStreamDataReader;
import java.sql.SQLException;

public class TbColumnDesc {
  public String name;
  
  public int dataType;
  
  public int precision;
  
  public int scale;
  
  public int etcMeta;
  
  public int maxSize;
  
  public void set(String paramString, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5) {
    this.name = paramString;
    this.dataType = paramInt1;
    this.precision = paramInt2;
    this.scale = paramInt3;
    this.etcMeta = paramInt4;
    this.maxSize = paramInt5;
  }
  
  public void deserialize(TbStreamDataReader paramTbStreamDataReader) throws SQLException {
    int i = paramTbStreamDataReader.readInt32();
    this.name = paramTbStreamDataReader.readDBDecodedPadString(i);
    this.dataType = paramTbStreamDataReader.readInt32();
    this.precision = paramTbStreamDataReader.readInt32();
    this.scale = paramTbStreamDataReader.readInt32();
    this.etcMeta = paramTbStreamDataReader.readInt32();
    this.maxSize = paramTbStreamDataReader.readInt32();
  }
}


/* Location:              C:\Users\Lenovo\Desktop\tibero\tibero6-jdbc.jar!\com\tmax\tibero\jdbc\msg\TbColumnDesc.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */