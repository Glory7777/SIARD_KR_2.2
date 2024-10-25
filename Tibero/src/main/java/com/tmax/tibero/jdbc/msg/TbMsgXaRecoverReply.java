package com.tmax.tibero.jdbc.msg;

import com.tmax.tibero.jdbc.comm.TbStreamDataReader;
import com.tmax.tibero.jdbc.comm.TbStreamDataWriter;
import com.tmax.tibero.jdbc.msg.common.TbMsg;
import java.sql.SQLException;

public class TbMsgXaRecoverReply extends TbMsg {
  public int xidCount;
  
  public byte[] xids;
  
  public int xidsLen;
  
  public void serialize(TbStreamDataWriter paramTbStreamDataWriter) throws SQLException {}
  
  public void deserialize(TbStreamDataReader paramTbStreamDataReader) throws SQLException {
    this.xidCount = paramTbStreamDataReader.readInt32();
    this.xidsLen = paramTbStreamDataReader.readInt32();
    if (this.xidsLen != 0) {
      this.xids = new byte[this.xidsLen];
      paramTbStreamDataReader.readPadBytes(this.xids, 0, this.xidsLen);
    } else {
      paramTbStreamDataReader.moveReadOffset(4);
      this.xids = new byte[0];
    } 
  }
}


/* Location:              C:\Users\Lenovo\Desktop\tibero\tibero6-jdbc.jar!\com\tmax\tibero\jdbc\msg\TbMsgXaRecoverReply.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */