package com.tmax.tibero.jdbc.msg;

import com.tmax.tibero.jdbc.comm.TbStreamDataReader;
import com.tmax.tibero.jdbc.comm.TbStreamDataWriter;
import com.tmax.tibero.jdbc.msg.common.TbMsg;
import java.sql.SQLException;

public class TbMsgExecutePivotReply extends TbMsg {
  public int pivotInfoArrayCnt;
  
  public TbPivotInfo[] pivotInfo;
  
  public byte[] chunk;
  
  public int chunkLen;
  
  public void serialize(TbStreamDataWriter paramTbStreamDataWriter) throws SQLException {}
  
  public void deserialize(TbStreamDataReader paramTbStreamDataReader) throws SQLException {
    int i = paramTbStreamDataReader.readInt32();
    if (i > 0) {
      this.pivotInfo = new TbPivotInfo[i];
      for (byte b = 0; b < i; b++) {
        this.pivotInfo[b] = new TbPivotInfo();
        this.pivotInfo[b].deserialize(paramTbStreamDataReader);
      } 
    } else {
      this.pivotInfo = null;
    } 
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


/* Location:              C:\TmaxData\tibero6\client\lib\jar\tibero6-jdbc.jar!\com\tmax\tibero\jdbc\msg\TbMsgExecutePivotReply.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */