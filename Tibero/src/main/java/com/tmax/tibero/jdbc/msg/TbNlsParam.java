package com.tmax.tibero.jdbc.msg;

import com.tmax.tibero.jdbc.comm.TbStreamDataReader;
import java.sql.SQLException;

public class TbNlsParam {
  public int nlsParamId;
  
  public String nlsParamVal;
  
  public void set(int paramInt, String paramString) {
    this.nlsParamId = paramInt;
    this.nlsParamVal = paramString;
  }
  
  public void deserialize(TbStreamDataReader paramTbStreamDataReader) throws SQLException {
    this.nlsParamId = paramTbStreamDataReader.readInt32();
    int i = paramTbStreamDataReader.readInt32();
    this.nlsParamVal = paramTbStreamDataReader.readDBDecodedPadString(i);
  }
}


/* Location:              C:\Users\Lenovo\Desktop\tibero\tibero6-jdbc.jar!\com\tmax\tibero\jdbc\msg\TbNlsParam.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */