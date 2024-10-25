package com.tmax.tibero.jdbc.msg;

import com.tmax.tibero.jdbc.comm.TbStreamDataReader;
import com.tmax.tibero.jdbc.comm.TbStreamDataWriter;
import com.tmax.tibero.jdbc.msg.common.TbMsg;
import java.sql.SQLException;

public class TbMsgFetchPivotReply extends TbMsg {
  public byte[] chunk;
  
  public int chunkLen;
  
  public void serialize(TbStreamDataWriter paramTbStreamDataWriter) throws SQLException {}
  
  public void deserialize(TbStreamDataReader paramTbStreamDataReader) throws SQLException {
    this.chunkLen = paramTbStreamDataReader.readInt32();
    if (this.chunkLen != 0) {
      this.chunk = new byte[this.chunkLen];
      paramTbStreamDataReader.readPadBytes(this.chunk, 0, this.chunkLen);
    } else {
      paramTbStreamDataReader.moveReadOffset(4);
      this.chunk = new byte[0];
    } 
  }
}


/* Location:              C:\Users\Lenovo\Desktop\tibero\tibero6-jdbc.jar!\com\tmax\tibero\jdbc\msg\TbMsgFetchPivotReply.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */