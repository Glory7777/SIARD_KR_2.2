package com.tmax.tibero.jdbc.msg;

import com.tmax.tibero.jdbc.comm.TbStreamDataReader;
import java.sql.SQLException;

public class TbOutParam {
  public int dataType;
  
  public byte[] value;
  
  public int valueLen;
  
  public int colMetaArrayCnt;
  
  public TbColumnDesc[] colMeta;
  
  public void set(int paramInt1, byte[] paramArrayOfbyte, int paramInt2, int paramInt3, TbColumnDesc[] paramArrayOfTbColumnDesc) {
    this.dataType = paramInt1;
    this.value = paramArrayOfbyte;
    this.valueLen = paramInt2;
    this.colMetaArrayCnt = paramInt3;
    this.colMeta = paramArrayOfTbColumnDesc;
  }
  
  public void deserialize(TbStreamDataReader paramTbStreamDataReader) throws SQLException {
    this.dataType = paramTbStreamDataReader.readInt32();
    this.valueLen = paramTbStreamDataReader.readInt32();
    if (this.valueLen != 0) {
      this.value = new byte[this.valueLen];
      paramTbStreamDataReader.readPadBytes(this.value, 0, this.valueLen);
    } else {
      paramTbStreamDataReader.moveReadOffset(4);
      this.value = new byte[0];
    } 
    int i = paramTbStreamDataReader.readInt32();
    if (i > 0) {
      this.colMeta = new TbColumnDesc[i];
      for (byte b = 0; b < i; b++) {
        this.colMeta[b] = new TbColumnDesc();
        this.colMeta[b].deserialize(paramTbStreamDataReader);
      } 
    } else {
      this.colMeta = null;
    } 
  }
}


/* Location:              C:\Users\lenovo\Desktop\siard\jdbc\tibero\tibero6\linux\tibero6-jdbc-18.jar!\com\tmax\tibero\jdbc\msg\TbOutParam.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */