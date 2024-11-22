package com.tmax.tibero.jdbc.msg;

import com.tmax.tibero.jdbc.comm.TbStreamDataReader;
import java.sql.SQLException;

public class TbHlLogInfo {
  public int errorCode;
  
  public int failedRecordNumber;
  
  public void set(int paramInt1, int paramInt2) {
    this.errorCode = paramInt1;
    this.failedRecordNumber = paramInt2;
  }
  
  public void deserialize(TbStreamDataReader paramTbStreamDataReader) throws SQLException {
    this.errorCode = paramTbStreamDataReader.readInt32();
    this.failedRecordNumber = paramTbStreamDataReader.readInt32();
  }
}


/* Location:              C:\Users\lenovo\Desktop\siard\jdbc\tibero\tibero6\linux\tibero6-jdbc-18.jar!\com\tmax\tibero\jdbc\msg\TbHlLogInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */