package com.tmax.tibero.jdbc.msg;

import com.tmax.tibero.jdbc.comm.TbStreamDataReader;
import java.sql.SQLException;

public class TbAffectedCnt {
  public int cnt;
  
  public void set(int paramInt) {
    this.cnt = paramInt;
  }
  
  public void deserialize(TbStreamDataReader paramTbStreamDataReader) throws SQLException {
    this.cnt = paramTbStreamDataReader.readInt32();
  }
}


/* Location:              C:\Users\Lenovo\Desktop\tibero\tibero6-jdbc.jar!\com\tmax\tibero\jdbc\msg\TbAffectedCnt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */