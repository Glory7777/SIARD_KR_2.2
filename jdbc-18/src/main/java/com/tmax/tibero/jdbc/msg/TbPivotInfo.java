package com.tmax.tibero.jdbc.msg;

import com.tmax.tibero.jdbc.comm.TbStreamDataReader;
import java.sql.SQLException;

public class TbPivotInfo {
  public int valTypeArrayCnt;
  
  public TbPvValType[] valType;
  
  public int colIdx;
  
  public int chunkCnt;
  
  public void set(int paramInt1, TbPvValType[] paramArrayOfTbPvValType, int paramInt2, int paramInt3) {
    this.valTypeArrayCnt = paramInt1;
    this.valType = paramArrayOfTbPvValType;
    this.colIdx = paramInt2;
    this.chunkCnt = paramInt3;
  }
  
  public void deserialize(TbStreamDataReader paramTbStreamDataReader) throws SQLException {
    int i = paramTbStreamDataReader.readInt32();
    if (i > 0) {
      this.valType = new TbPvValType[i];
      for (byte b = 0; b < i; b++) {
        this.valType[b] = new TbPvValType();
        this.valType[b].deserialize(paramTbStreamDataReader);
      } 
    } else {
      this.valType = null;
    } 
    this.colIdx = paramTbStreamDataReader.readInt32();
    this.chunkCnt = paramTbStreamDataReader.readInt32();
  }
}


/* Location:              C:\Users\lenovo\Desktop\siard\jdbc\tibero\tibero6\linux\tibero6-jdbc-18.jar!\com\tmax\tibero\jdbc\msg\TbPivotInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */