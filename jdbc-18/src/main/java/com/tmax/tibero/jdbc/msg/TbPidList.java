package com.tmax.tibero.jdbc.msg;

import com.tmax.tibero.jdbc.comm.TbStreamDataReader;
import java.sql.SQLException;

public class TbPidList {
  public int pid;
  
  public void set(int paramInt) {
    this.pid = paramInt;
  }
  
  public void deserialize(TbStreamDataReader paramTbStreamDataReader) throws SQLException {
    this.pid = paramTbStreamDataReader.readInt32();
  }
}


/* Location:              C:\Users\lenovo\Desktop\siard\jdbc\tibero\tibero6\linux\tibero6-jdbc-18.jar!\com\tmax\tibero\jdbc\msg\TbPidList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */