package com.tmax.tibero.jdbc.msg;

import com.tmax.tibero.jdbc.comm.TbStreamDataReader;
import com.tmax.tibero.jdbc.comm.TbStreamDataWriter;
import com.tmax.tibero.jdbc.msg.common.TbMsg;
import java.sql.SQLException;

public class TbMsgFetchReply extends TbMsg {
  public int rowCnt;
  
  public int isFetchCompleted;
  
  public int rowChunkSize;
  
  public void serialize(TbStreamDataWriter paramTbStreamDataWriter) throws SQLException {}
  
  public void deserialize(TbStreamDataReader paramTbStreamDataReader) throws SQLException {
    this.rowCnt = paramTbStreamDataReader.readInt32();
    this.isFetchCompleted = paramTbStreamDataReader.readInt32();
    this.rowChunkSize = paramTbStreamDataReader.readInt32();
  }
}


/* Location:              C:\TmaxData\tibero6\client\lib\jar\tibero6-jdbc.jar!\com\tmax\tibero\jdbc\msg\TbMsgFetchReply.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */