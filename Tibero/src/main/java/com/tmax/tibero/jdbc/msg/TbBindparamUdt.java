package com.tmax.tibero.jdbc.msg;

import com.tmax.tibero.jdbc.comm.TbStreamDataReader;
import java.sql.SQLException;

public class TbBindparamUdt {
  public int flag;
  
  public byte[] dataValue;
  
  public int dataValueLen;
  
  public int subBindparamArrayCnt;
  
  public TbBindparamUdt[] subBindparam;
  
  public void set(int paramInt1, byte[] paramArrayOfbyte, int paramInt2, int paramInt3, TbBindparamUdt[] paramArrayOfTbBindparamUdt) {
    this.flag = paramInt1;
    this.dataValue = paramArrayOfbyte;
    this.dataValueLen = paramInt2;
    this.subBindparamArrayCnt = paramInt3;
    this.subBindparam = paramArrayOfTbBindparamUdt;
  }
  
  public void deserialize(TbStreamDataReader paramTbStreamDataReader) throws SQLException {
    this.flag = paramTbStreamDataReader.readInt32();
    this.dataValue = paramTbStreamDataReader.readRpcolData();
    this.dataValueLen = (this.dataValue == null) ? 0 : this.dataValue.length;
    int i = paramTbStreamDataReader.readInt32();
    if (i > 0) {
      this.subBindparam = new TbBindparamUdt[i];
      for (byte b = 0; b < i; b++) {
        this.subBindparam[b] = new TbBindparamUdt();
        this.subBindparam[b].deserialize(paramTbStreamDataReader);
      } 
    } else {
      this.subBindparam = null;
    } 
  }
}


/* Location:              C:\TmaxData\tibero6\client\lib\jar\tibero6-jdbc.jar!\com\tmax\tibero\jdbc\msg\TbBindparamUdt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */