package com.tmax.tibero.jdbc.msg;

import com.tmax.tibero.jdbc.comm.TbStreamDataReader;
import java.sql.SQLException;

public class TbThreadLog {
  public int rthId;
  
  public int rbaSeqno;
  
  public int rbaBlkno;
  
  public byte[] logblks;
  
  public int logblksLen;
  
  public void set(int paramInt1, int paramInt2, int paramInt3, byte[] paramArrayOfbyte, int paramInt4) {
    this.rthId = paramInt1;
    this.rbaSeqno = paramInt2;
    this.rbaBlkno = paramInt3;
    this.logblks = paramArrayOfbyte;
    this.logblksLen = paramInt4;
  }
  
  public void deserialize(TbStreamDataReader paramTbStreamDataReader) throws SQLException {
    this.rthId = paramTbStreamDataReader.readInt32();
    this.rbaSeqno = paramTbStreamDataReader.readInt32();
    this.rbaBlkno = paramTbStreamDataReader.readInt32();
    this.logblksLen = paramTbStreamDataReader.readInt32();
    if (this.logblksLen != 0) {
      this.logblks = new byte[this.logblksLen];
      paramTbStreamDataReader.readPadBytes(this.logblks, 0, this.logblksLen);
    } else {
      paramTbStreamDataReader.moveReadOffset(4);
      this.logblks = new byte[0];
    } 
  }
}


/* Location:              C:\Users\Lenovo\Desktop\tibero\tibero6-jdbc.jar!\com\tmax\tibero\jdbc\msg\TbThreadLog.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */