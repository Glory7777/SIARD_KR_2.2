package com.tmax.tibero.jdbc.msg;

import com.tmax.tibero.jdbc.comm.TbStreamDataReader;
import java.sql.SQLException;

public class TbColumnData {
  public byte[] value;
  
  public int valueLen;
  
  public void set(byte[] paramArrayOfbyte, int paramInt) {
    this.value = paramArrayOfbyte;
    this.valueLen = paramInt;
  }
  
  public void deserialize(TbStreamDataReader paramTbStreamDataReader) throws SQLException {
    this.value = paramTbStreamDataReader.readRpcolData();
    this.valueLen = (this.value == null) ? 0 : this.value.length;
  }
}


/* Location:              C:\Users\lenovo\Desktop\siard\jdbc\tibero\tibero6\linux\tibero6-jdbc-18.jar!\com\tmax\tibero\jdbc\msg\TbColumnData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */