package com.tmax.tibero.jdbc.msg;

import com.tmax.tibero.jdbc.comm.TbStreamDataReader;
import java.sql.SQLException;

public class TbBindParamDesc {
  public int type;
  
  public String placeHolderName;
  
  public void set(int paramInt, String paramString) {
    this.type = paramInt;
    this.placeHolderName = paramString;
  }
  
  public void deserialize(TbStreamDataReader paramTbStreamDataReader) throws SQLException {
    this.type = paramTbStreamDataReader.readInt32();
    int i = paramTbStreamDataReader.readInt32();
    this.placeHolderName = paramTbStreamDataReader.readDBDecodedPadString(i);
  }
}


/* Location:              C:\Users\Lenovo\Desktop\tibero\tibero6-jdbc.jar!\com\tmax\tibero\jdbc\msg\TbBindParamDesc.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */