package com.tmax.tibero.jdbc.msg;

import com.tmax.tibero.jdbc.comm.TbStreamDataReader;
import java.sql.SQLException;

public class TbOutParamUdt {
  public int dataType;
  
  public byte[] value;
  
  public int valueLen;
  
  public int colMetaArrayCnt;
  
  public TbColumnDesc[] colMeta;
  
  public int subOutParamArrayCnt;
  
  public TbOutParamUdt[] subOutParam;
  
  public void set(int paramInt1, byte[] paramArrayOfbyte, int paramInt2, int paramInt3, TbColumnDesc[] paramArrayOfTbColumnDesc, int paramInt4, TbOutParamUdt[] paramArrayOfTbOutParamUdt) {
    this.dataType = paramInt1;
    this.value = paramArrayOfbyte;
    this.valueLen = paramInt2;
    this.colMetaArrayCnt = paramInt3;
    this.colMeta = paramArrayOfTbColumnDesc;
    this.subOutParamArrayCnt = paramInt4;
    this.subOutParam = paramArrayOfTbOutParamUdt;
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
    int j = paramTbStreamDataReader.readInt32();
    if (j > 0) {
      this.subOutParam = new TbOutParamUdt[j];
      for (byte b = 0; b < j; b++) {
        this.subOutParam[b] = new TbOutParamUdt();
        this.subOutParam[b].deserialize(paramTbStreamDataReader);
      } 
    } else {
      this.subOutParam = null;
    } 
  }
}


/* Location:              C:\Users\Lenovo\Desktop\tibero\tibero6-jdbc.jar!\com\tmax\tibero\jdbc\msg\TbOutParamUdt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */