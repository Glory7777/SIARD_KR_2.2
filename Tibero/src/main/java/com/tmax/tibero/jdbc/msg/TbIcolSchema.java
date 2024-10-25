package com.tmax.tibero.jdbc.msg;

import com.tmax.tibero.jdbc.comm.TbStreamDataReader;
import java.sql.SQLException;

public class TbIcolSchema {
  public int colNo;
  
  public void set(int paramInt) {
    this.colNo = paramInt;
  }
  
  public void deserialize(TbStreamDataReader paramTbStreamDataReader) throws SQLException {
    this.colNo = paramTbStreamDataReader.readInt32();
  }
}


/* Location:              C:\Users\Lenovo\Desktop\tibero\tibero6-jdbc.jar!\com\tmax\tibero\jdbc\msg\TbIcolSchema.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */