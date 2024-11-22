package com.tmax.tibero.jdbc.msg;

import com.tmax.tibero.jdbc.comm.TbStreamDataReader;
import com.tmax.tibero.jdbc.comm.TbStreamDataWriter;
import com.tmax.tibero.jdbc.msg.common.TbMsg;
import java.sql.SQLException;

public class TbMsgLobReadReply extends TbMsg {
  public byte[] data;
  
  public int dataLen;
  
  public void serialize(TbStreamDataWriter paramTbStreamDataWriter) throws SQLException {}
  
  public void deserialize(TbStreamDataReader paramTbStreamDataReader) throws SQLException {
    this.dataLen = paramTbStreamDataReader.readInt32();
    if (this.dataLen != 0) {
      this.data = new byte[this.dataLen];
      paramTbStreamDataReader.readPadBytes(this.data, 0, this.dataLen);
    } else {
      paramTbStreamDataReader.moveReadOffset(4);
      this.data = new byte[0];
    } 
  }
}


/* Location:              C:\Users\lenovo\Desktop\siard\jdbc\tibero\tibero6\linux\tibero6-jdbc-18.jar!\com\tmax\tibero\jdbc\msg\TbMsgLobReadReply.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */