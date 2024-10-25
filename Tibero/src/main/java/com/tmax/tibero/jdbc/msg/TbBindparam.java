package com.tmax.tibero.jdbc.msg;

import com.tmax.tibero.jdbc.comm.TbStreamDataReader;
import java.sql.SQLException;

public class TbBindparam {
  public int flag;
  
  public byte[] dataValue;
  
  public int dataValueLen;
  
  public void set(int paramInt1, byte[] paramArrayOfbyte, int paramInt2) {
    this.flag = paramInt1;
    this.dataValue = paramArrayOfbyte;
    this.dataValueLen = paramInt2;
  }
  
  public void deserialize(TbStreamDataReader paramTbStreamDataReader) throws SQLException {
    this.flag = paramTbStreamDataReader.readInt32();
    this.dataValue = paramTbStreamDataReader.readRpcolData();
    this.dataValueLen = (this.dataValue == null) ? 0 : this.dataValue.length;
  }
}


/* Location:              C:\Users\Lenovo\Desktop\tibero\tibero6-jdbc.jar!\com\tmax\tibero\jdbc\msg\TbBindparam.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */