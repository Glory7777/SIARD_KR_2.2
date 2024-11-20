package com.tmax.tibero.jdbc.msg;

import com.tmax.tibero.jdbc.comm.TbStreamDataReader;
import java.sql.SQLException;

public class TbHlColMeta {
  public int colIdx;
  
  public int isLob;
  
  public void set(int paramInt1, int paramInt2) {
    this.colIdx = paramInt1;
    this.isLob = paramInt2;
  }
  
  public void deserialize(TbStreamDataReader paramTbStreamDataReader) throws SQLException {
    this.colIdx = paramTbStreamDataReader.readInt32();
    this.isLob = paramTbStreamDataReader.readInt32();
  }
}


/* Location:              C:\Users\lenovo\Desktop\siard\jdbc\tibero\tibero6\linux\tibero6-jdbc-18.jar!\com\tmax\tibero\jdbc\msg\TbHlColMeta.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */