package com.tmax.tibero.jdbc.msg;

import com.tmax.tibero.jdbc.comm.TbStreamDataReader;
import java.sql.SQLException;

public class TbSysBindVar {
  public int type;
  
  public byte[] dtv;
  
  public int dtvLen;
  
  public String literal;
  
  public void set(int paramInt1, byte[] paramArrayOfbyte, int paramInt2, String paramString) {
    this.type = paramInt1;
    this.dtv = paramArrayOfbyte;
    this.dtvLen = paramInt2;
    this.literal = paramString;
  }
  
  public void deserialize(TbStreamDataReader paramTbStreamDataReader) throws SQLException {
    this.type = paramTbStreamDataReader.readInt32();
    this.dtvLen = paramTbStreamDataReader.readInt32();
    if (this.dtvLen != 0) {
      this.dtv = new byte[this.dtvLen];
      paramTbStreamDataReader.readPadBytes(this.dtv, 0, this.dtvLen);
    } else {
      paramTbStreamDataReader.moveReadOffset(4);
      this.dtv = new byte[0];
    } 
    int i = paramTbStreamDataReader.readInt32();
    this.literal = paramTbStreamDataReader.readDBDecodedPadString(i);
  }
}


/* Location:              C:\Users\lenovo\Desktop\siard\jdbc\tibero\tibero6\linux\tibero6-jdbc-18.jar!\com\tmax\tibero\jdbc\msg\TbSysBindVar.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */