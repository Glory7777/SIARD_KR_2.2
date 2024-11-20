package com.tmax.tibero.jdbc.msg;

import com.tmax.tibero.jdbc.comm.TbStreamDataReader;
import java.sql.SQLException;

public class TbUniqChkIdx {
  public int idxGtid;
  
  public void set(int paramInt) {
    this.idxGtid = paramInt;
  }
  
  public void deserialize(TbStreamDataReader paramTbStreamDataReader) throws SQLException {
    this.idxGtid = paramTbStreamDataReader.readInt32();
  }
}


/* Location:              C:\Users\lenovo\Desktop\siard\jdbc\tibero\tibero6\linux\tibero6-jdbc-18.jar!\com\tmax\tibero\jdbc\msg\TbUniqChkIdx.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */