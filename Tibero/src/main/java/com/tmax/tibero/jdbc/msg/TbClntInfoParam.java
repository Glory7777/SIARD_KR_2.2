package com.tmax.tibero.jdbc.msg;

import com.tmax.tibero.jdbc.comm.TbStreamDataReader;
import java.sql.SQLException;

public class TbClntInfoParam {
  public int clntParamId;
  
  public String clntParamVal;
  
  public void set(int paramInt, String paramString) {
    this.clntParamId = paramInt;
    this.clntParamVal = paramString;
  }
  
  public void deserialize(TbStreamDataReader paramTbStreamDataReader) throws SQLException {
    this.clntParamId = paramTbStreamDataReader.readInt32();
    int i = paramTbStreamDataReader.readInt32();
    this.clntParamVal = paramTbStreamDataReader.readDBDecodedPadString(i);
  }
}


/* Location:              C:\TmaxData\tibero6\client\lib\jar\tibero6-jdbc.jar!\com\tmax\tibero\jdbc\msg\TbClntInfoParam.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */