package com.tmax.tibero.jdbc.msg;

import com.tmax.tibero.jdbc.comm.TbStreamDataReader;
import com.tmax.tibero.jdbc.comm.TbStreamDataWriter;
import com.tmax.tibero.jdbc.msg.common.TbMsg;
import java.sql.SQLException;

public class TbMsgHlInitiateLoadReply extends TbMsg {
  public int handleId;
  
  public int returnCode;
  
  public int overlapSize;
  
  public int bufferSize;
  
  public int colMetaArrayArrayCnt;
  
  public TbHlColMeta[] colMetaArray;
  
  public void serialize(TbStreamDataWriter paramTbStreamDataWriter) throws SQLException {}
  
  public void deserialize(TbStreamDataReader paramTbStreamDataReader) throws SQLException {
    this.handleId = paramTbStreamDataReader.readInt32();
    this.returnCode = paramTbStreamDataReader.readInt32();
    this.overlapSize = paramTbStreamDataReader.readInt32();
    this.bufferSize = paramTbStreamDataReader.readInt32();
    int i = paramTbStreamDataReader.readInt32();
    if (i > 0) {
      this.colMetaArray = new TbHlColMeta[i];
      for (byte b = 0; b < i; b++) {
        this.colMetaArray[b] = new TbHlColMeta();
        this.colMetaArray[b].deserialize(paramTbStreamDataReader);
      } 
    } else {
      this.colMetaArray = null;
    } 
  }
}


/* Location:              C:\Users\lenovo\Desktop\siard\jdbc\tibero\tibero6\linux\tibero6-jdbc-18.jar!\com\tmax\tibero\jdbc\msg\TbMsgHlInitiateLoadReply.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */