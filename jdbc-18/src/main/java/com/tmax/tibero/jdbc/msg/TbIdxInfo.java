package com.tmax.tibero.jdbc.msg;

import com.tmax.tibero.jdbc.comm.TbStreamDataReader;
import java.sql.SQLException;

public class TbIdxInfo {
  public int blkcnt;
  
  public int leafblkcnt;
  
  public byte[] distinctkey = new byte[8];
  
  public byte[] clufac = new byte[8];
  
  public int blevel;
  
  public void set(int paramInt1, int paramInt2, byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2, int paramInt3) {
    this.blkcnt = paramInt1;
    this.leafblkcnt = paramInt2;
    this.distinctkey = paramArrayOfbyte1;
    this.clufac = paramArrayOfbyte2;
    this.blevel = paramInt3;
  }
  
  public void deserialize(TbStreamDataReader paramTbStreamDataReader) throws SQLException {
    this.blkcnt = paramTbStreamDataReader.readInt32();
    this.leafblkcnt = paramTbStreamDataReader.readInt32();
    paramTbStreamDataReader.readBytes(this.distinctkey, 0, 8);
    paramTbStreamDataReader.readBytes(this.clufac, 0, 8);
    this.blevel = paramTbStreamDataReader.readInt32();
  }
}


/* Location:              C:\Users\lenovo\Desktop\siard\jdbc\tibero\tibero6\linux\tibero6-jdbc-18.jar!\com\tmax\tibero\jdbc\msg\TbIdxInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */