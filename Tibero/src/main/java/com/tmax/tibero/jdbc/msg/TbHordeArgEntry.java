package com.tmax.tibero.jdbc.msg;

import com.tmax.tibero.jdbc.comm.TbStreamDataReader;
import java.sql.SQLException;

public class TbHordeArgEntry {
  public String key;
  
  public String value;
  
  public void set(String paramString1, String paramString2) {
    this.key = paramString1;
    this.value = paramString2;
  }
  
  public void deserialize(TbStreamDataReader paramTbStreamDataReader) throws SQLException {
    int i = paramTbStreamDataReader.readInt32();
    this.key = paramTbStreamDataReader.readDBDecodedPadString(i);
    int j = paramTbStreamDataReader.readInt32();
    this.value = paramTbStreamDataReader.readDBDecodedPadString(j);
  }
}


/* Location:              C:\Users\Lenovo\Desktop\tibero\tibero6-jdbc.jar!\com\tmax\tibero\jdbc\msg\TbHordeArgEntry.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */