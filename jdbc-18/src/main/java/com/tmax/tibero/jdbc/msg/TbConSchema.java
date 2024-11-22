package com.tmax.tibero.jdbc.msg;

import com.tmax.tibero.jdbc.comm.TbStreamDataReader;
import java.sql.SQLException;

public class TbConSchema {
  public String conName;
  
  public int typeNo;
  
  public int colCnt;
  
  public void set(String paramString, int paramInt1, int paramInt2) {
    this.conName = paramString;
    this.typeNo = paramInt1;
    this.colCnt = paramInt2;
  }
  
  public void deserialize(TbStreamDataReader paramTbStreamDataReader) throws SQLException {
    int i = paramTbStreamDataReader.readInt32();
    this.conName = paramTbStreamDataReader.readDBDecodedPadString(i);
    this.typeNo = paramTbStreamDataReader.readInt32();
    this.colCnt = paramTbStreamDataReader.readInt32();
  }
}


/* Location:              C:\Users\lenovo\Desktop\siard\jdbc\tibero\tibero6\linux\tibero6-jdbc-18.jar!\com\tmax\tibero\jdbc\msg\TbConSchema.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */