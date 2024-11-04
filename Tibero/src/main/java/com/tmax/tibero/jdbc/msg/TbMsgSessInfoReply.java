package com.tmax.tibero.jdbc.msg;

import com.tmax.tibero.jdbc.comm.TbStreamDataReader;
import com.tmax.tibero.jdbc.comm.TbStreamDataWriter;
import com.tmax.tibero.jdbc.msg.common.TbMsg;
import java.sql.SQLException;

public class TbMsgSessInfoReply extends TbMsg {
  public int sessionId;
  
  public int serialNo;
  
  public int nlsDataArrayCnt;
  
  public TbNlsParam[] nlsData;
  
  public void serialize(TbStreamDataWriter paramTbStreamDataWriter) throws SQLException {}
  
  public void deserialize(TbStreamDataReader paramTbStreamDataReader) throws SQLException {
    this.sessionId = paramTbStreamDataReader.readInt32();
    this.serialNo = paramTbStreamDataReader.readInt32();
    int i = paramTbStreamDataReader.readInt32();
    if (i > 0) {
      this.nlsData = new TbNlsParam[i];
      for (byte b = 0; b < i; b++) {
        this.nlsData[b] = new TbNlsParam();
        this.nlsData[b].deserialize(paramTbStreamDataReader);
      } 
    } else {
      this.nlsData = null;
    } 
  }
}


/* Location:              C:\TmaxData\tibero6\client\lib\jar\tibero6-jdbc.jar!\com\tmax\tibero\jdbc\msg\TbMsgSessInfoReply.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */