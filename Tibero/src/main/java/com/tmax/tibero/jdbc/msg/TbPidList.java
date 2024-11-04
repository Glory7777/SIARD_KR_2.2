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


/* Location:              C:\TmaxData\tibero6\client\lib\jar\tibero6-jdbc.jar!\com\tmax\tibero\jdbc\msg\TbPidList.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */