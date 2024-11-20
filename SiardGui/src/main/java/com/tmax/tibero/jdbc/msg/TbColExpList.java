package com.tmax.tibero.jdbc.msg;

import com.tmax.tibero.jdbc.comm.TbStreamDataReader;
import java.sql.SQLException;

public class TbColExpList {
  public String colExp;
  
  public void set(String paramString) {
    this.colExp = paramString;
  }
  
  public void deserialize(TbStreamDataReader paramTbStreamDataReader) throws SQLException {
    int i = paramTbStreamDataReader.readInt32();
    this.colExp = paramTbStreamDataReader.readDBDecodedPadString(i);
  }
}


/* Location:              C:\Users\lenovo\Desktop\siard\jdbc\tibero\tibero6\linux\tibero6-jdbc-18.jar!\com\tmax\tibero\jdbc\msg\TbColExpList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */