package com.tmax.tibero.jdbc.msg;

import com.tmax.tibero.jdbc.comm.TbStreamDataReader;
import com.tmax.tibero.jdbc.comm.TbStreamDataWriter;
import com.tmax.tibero.jdbc.msg.common.TbMsgError;
import java.sql.SQLException;

public class TbMsgBatchUpdateReply extends TbMsgError {
  public int executedCnt;
  
  public int affectedCntArrayCnt;
  
  public TbAffectedCnt[] affectedCnt;
  
  public byte[] errorStack;
  
  public int errorStackLen;
  
  public void serialize(TbStreamDataWriter paramTbStreamDataWriter) throws SQLException {}
  
  public void deserialize(TbStreamDataReader paramTbStreamDataReader) throws SQLException {
    this.executedCnt = paramTbStreamDataReader.readInt32();
    int i = paramTbStreamDataReader.readInt32();
    if (i > 0) {
      this.affectedCnt = new TbAffectedCnt[i];
      for (byte b = 0; b < i; b++) {
        this.affectedCnt[b] = new TbAffectedCnt();
        this.affectedCnt[b].deserialize(paramTbStreamDataReader);
      } 
    } else {
      this.affectedCnt = null;
    } 
    readErrorStackInfo(paramTbStreamDataReader);
  }
}


/* Location:              C:\TmaxData\tibero6\client\lib\jar\tibero6-jdbc.jar!\com\tmax\tibero\jdbc\msg\TbMsgBatchUpdateReply.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */