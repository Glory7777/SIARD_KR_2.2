package com.tmax.tibero.jdbc.msg;

import com.tmax.tibero.jdbc.comm.TbStreamDataReader;
import java.sql.SQLException;

public class TbSessAttrDesc {
  public int key;
  
  public String value;
  
  public void set(int paramInt, String paramString) {
    this.key = paramInt;
    this.value = paramString;
  }
  
  public void deserialize(TbStreamDataReader paramTbStreamDataReader) throws SQLException {
    this.key = paramTbStreamDataReader.readInt32();
    int i = paramTbStreamDataReader.readInt32();
    this.value = paramTbStreamDataReader.readDBDecodedPadString(i);
  }
}


/* Location:              C:\Users\Lenovo\Desktop\tibero\tibero6-jdbc.jar!\com\tmax\tibero\jdbc\msg\TbSessAttrDesc.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */