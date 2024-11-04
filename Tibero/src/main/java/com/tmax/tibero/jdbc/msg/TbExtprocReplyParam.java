package com.tmax.tibero.jdbc.msg;

import com.tmax.tibero.jdbc.comm.TbStreamDataReader;
import java.sql.SQLException;

public class TbExtprocReplyParam {
  public byte[] dataValue;
  
  public int dataValueLen;
  
  public void set(byte[] paramArrayOfbyte, int paramInt) {
    this.dataValue = paramArrayOfbyte;
    this.dataValueLen = paramInt;
  }
  
  public void deserialize(TbStreamDataReader paramTbStreamDataReader) throws SQLException {
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


/* Location:              C:\TmaxData\tibero6\client\lib\jar\tibero6-jdbc.jar!\com\tmax\tibero\jdbc\msg\TbExtprocReplyParam.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */