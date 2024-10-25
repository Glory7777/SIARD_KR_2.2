package com.tmax.tibero.jdbc.msg;

import com.tmax.tibero.jdbc.comm.TbStreamDataReader;
import java.sql.SQLException;

public class TbExtprocParam {
  public int flag;
  
  public int maxlen;
  
  public byte[] dataValue;
  
  public int dataValueLen;
  
  public void set(int paramInt1, int paramInt2, byte[] paramArrayOfbyte, int paramInt3) {
    this.flag = paramInt1;
    this.maxlen = paramInt2;
    this.dataValue = paramArrayOfbyte;
    this.dataValueLen = paramInt3;
  }
  
  public void deserialize(TbStreamDataReader paramTbStreamDataReader) throws SQLException {
    this.flag = paramTbStreamDataReader.readInt32();
    this.maxlen = paramTbStreamDataReader.readInt32();
    this.dataValueLen = paramTbStreamDataReader.readInt32();
    if (this.dataValueLen != 0) {
      this.dataValue = new byte[this.dataValueLen];
      paramTbStreamDataReader.readPadBytes(this.dataValue, 0, this.dataValueLen);
    } else {
      paramTbStreamDataReader.moveReadOffset(4);
      this.dataValue = new byte[0];
    } 
  }
}


/* Location:              C:\Users\Lenovo\Desktop\tibero\tibero6-jdbc.jar!\com\tmax\tibero\jdbc\msg\TbExtprocParam.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */