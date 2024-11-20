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


/* Location:              C:\Users\lenovo\Desktop\siard\jdbc\tibero\tibero6\linux\tibero6-jdbc-18.jar!\com\tmax\tibero\jdbc\msg\TbColNameList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */