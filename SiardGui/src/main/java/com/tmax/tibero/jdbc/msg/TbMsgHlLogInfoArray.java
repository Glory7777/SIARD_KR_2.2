package com.tmax.tibero.jdbc.msg;

import com.tmax.tibero.jdbc.comm.TbStreamDataReader;
import com.tmax.tibero.jdbc.comm.TbStreamDataWriter;
import com.tmax.tibero.jdbc.msg.common.TbMsg;
import java.sql.SQLException;

public class TbMsgHlLogInfoArray extends TbMsg {
  public int logInfoArrayArrayCnt;
  
  public TbHlLogInfo[] logInfoArray;
  
  public void serialize(TbStreamDataWriter paramTbStreamDataWriter) throws SQLException {}
  
  public void deserialize(TbStreamDataReader paramTbStreamDataReader) throws SQLException {
    int i = paramTbStreamDataReader.readInt32();
    if (i > 0) {
      this.logInfoArray = new TbHlLogInfo[i];
      for (byte b = 0; b < i; b++) {
        this.logInfoArray[b] = new TbHlLogInfo();
        this.logInfoArray[b].deserialize(paramTbStreamDataReader);
      } 
    } else {
      this.logInfoArray = null;
    } 
  }
}


/* Location:              C:\Users\lenovo\Desktop\siard\jdbc\tibero\tibero6\linux\tibero6-jdbc-18.jar!\com\tmax\tibero\jdbc\msg\TbMsgHlLogInfoArray.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */