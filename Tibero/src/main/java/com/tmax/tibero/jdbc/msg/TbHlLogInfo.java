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


/* Location:              C:\Users\Lenovo\Desktop\tibero\tibero6-jdbc.jar!\com\tmax\tibero\jdbc\msg\TbHlLogInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */