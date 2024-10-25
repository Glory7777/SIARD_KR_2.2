package com.tmax.tibero.jdbc.msg;

import com.tmax.tibero.jdbc.comm.TbStreamDataReader;
import com.tmax.tibero.jdbc.comm.TbStreamDataWriter;
import com.tmax.tibero.jdbc.msg.common.TbMsg;
import java.sql.SQLException;

public class TbMsgExecutePrefetchNoDescReply extends TbMsg {
  public byte[] ppid = new byte[8];
  
  public int csrId;
  
  public int rowCnt;
  
  public int isFetchCompleted;
  
  public int rowChunkSize;
  
  public void serialize(TbStreamDataWriter paramTbStreamDataWriter) throws SQLException {}
  
  public void deserialize(TbStreamDataReader paramTbStreamDataReader) throws SQLException {
    paramTbStreamDataReader.readBytes(this.ppid, 0, 8);
    this.csrId = paramTbStreamDataReader.readInt32();
    this.rowCnt = paramTbStreamDataReader.readInt32();
    this.isFetchCompleted = paramTbStreamDataReader.readInt32();
    this.rowChunkSize = paramTbStreamDataReader.readInt32();
  }
}


/* Location:              C:\Users\Lenovo\Desktop\tibero\tibero6-jdbc.jar!\com\tmax\tibero\jdbc\msg\TbMsgExecutePrefetchNoDescReply.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */