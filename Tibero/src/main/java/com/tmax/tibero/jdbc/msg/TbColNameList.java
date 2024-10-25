package com.tmax.tibero.jdbc.msg;

import com.tmax.tibero.jdbc.comm.TbStreamDataReader;
import java.sql.SQLException;

public class TbColNameList {
  public String colName;
  
  public void set(String paramString) {
    this.colName = paramString;
  }
  
  public void deserialize(TbStreamDataReader paramTbStreamDataReader) throws SQLException {
    int i = paramTbStreamDataReader.readInt32();
    this.colName = paramTbStreamDataReader.readDBDecodedPadString(i);
  }
}


/* Location:              C:\Users\Lenovo\Desktop\tibero\tibero6-jdbc.jar!\com\tmax\tibero\jdbc\msg\TbColNameList.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */