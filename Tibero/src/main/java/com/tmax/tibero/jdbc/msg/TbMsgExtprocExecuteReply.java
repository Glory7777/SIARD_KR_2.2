package com.tmax.tibero.jdbc.msg;

import com.tmax.tibero.jdbc.comm.TbStreamDataReader;
import com.tmax.tibero.jdbc.comm.TbStreamDataWriter;
import com.tmax.tibero.jdbc.msg.common.TbMsgError;
import java.sql.SQLException;

public class TbMsgExtprocExecuteReply extends TbMsgError {
  public String dbmsOutput;
  
  public byte[] errstack;
  
  public int errstackLen;
  
  public int replyParamArrayCnt;
  
  public TbExtprocReplyParam[] replyParam;
  
  public void serialize(TbStreamDataWriter paramTbStreamDataWriter) throws SQLException {}
  
  public void deserialize(TbStreamDataReader paramTbStreamDataReader) throws SQLException {
    skipDbmsOutput(paramTbStreamDataReader);
    this.errstackLen = paramTbStreamDataReader.readInt32();
    if (this.errstackLen != 0) {
      this.errstack = new byte[this.errstackLen];
      paramTbStreamDataReader.readPadBytes(this.errstack, 0, this.errstackLen);
    } else {
      paramTbStreamDataReader.moveReadOffset(4);
      this.errstack = new byte[0];
    } 
    int i = paramTbStreamDataReader.readInt32();
    if (i > 0) {
      this.replyParam = new TbExtprocReplyParam[i];
      for (byte b = 0; b < i; b++) {
        this.replyParam[b] = new TbExtprocReplyParam();
        this.replyParam[b].deserialize(paramTbStreamDataReader);
      } 
    } else {
      this.replyParam = null;
    } 
  }
}


/* Location:              C:\TmaxData\tibero6\client\lib\jar\tibero6-jdbc.jar!\com\tmax\tibero\jdbc\msg\TbMsgExtprocExecuteReply.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */