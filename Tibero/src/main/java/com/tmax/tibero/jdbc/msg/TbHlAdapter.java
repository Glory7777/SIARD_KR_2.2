package com.tmax.tibero.jdbc.msg;

import com.tmax.tibero.jdbc.comm.TbStreamDataReader;
import java.sql.SQLException;

public class TbHlAdapter {
  public String loadType;
  
  public int portListArrayCnt;
  
  public TbHlAdapterPort[] portList;
  
  public void set(String paramString, int paramInt, TbHlAdapterPort[] paramArrayOfTbHlAdapterPort) {
    this.loadType = paramString;
    this.portListArrayCnt = paramInt;
    this.portList = paramArrayOfTbHlAdapterPort;
  }
  
  public void deserialize(TbStreamDataReader paramTbStreamDataReader) throws SQLException {
    int i = paramTbStreamDataReader.readInt32();
    this.loadType = paramTbStreamDataReader.readDBDecodedPadString(i);
    int j = paramTbStreamDataReader.readInt32();
    if (j > 0) {
      this.portList = new TbHlAdapterPort[j];
      for (byte b = 0; b < j; b++) {
        this.portList[b] = new TbHlAdapterPort();
        this.portList[b].deserialize(paramTbStreamDataReader);
      } 
    } else {
      this.portList = null;
    } 
  }
}


/* Location:              C:\TmaxData\tibero6\client\lib\jar\tibero6-jdbc.jar!\com\tmax\tibero\jdbc\msg\TbHlAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */