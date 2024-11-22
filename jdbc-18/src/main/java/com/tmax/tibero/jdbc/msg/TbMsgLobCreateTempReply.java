package com.tmax.tibero.jdbc.msg;

import com.tmax.tibero.jdbc.comm.TbStreamDataReader;
import com.tmax.tibero.jdbc.comm.TbStreamDataWriter;
import com.tmax.tibero.jdbc.msg.common.TbMsg;
import java.sql.SQLException;

public class TbMsgLobCreateTempReply extends TbMsg {
  public int lobType;
  
  public byte[] slobLoc;
  
  public int slobLocLen;
  
  public void serialize(TbStreamDataWriter paramTbStreamDataWriter) throws SQLException {}
  
  public void deserialize(TbStreamDataReader paramTbStreamDataReader) throws SQLException {
    this.lobType = paramTbStreamDataReader.readInt32();
    this.slobLocLen = paramTbStreamDataReader.readInt32();
    if (this.slobLocLen != 0) {
      this.slobLoc = new byte[this.slobLocLen];
      paramTbStreamDataReader.readPadBytes(this.slobLoc, 0, this.slobLocLen);
    } else {
      paramTbStreamDataReader.moveReadOffset(4);
      this.slobLoc = new byte[0];
    } 
  }
}


/* Location:              C:\Users\lenovo\Desktop\siard\jdbc\tibero\tibero6\linux\tibero6-jdbc-18.jar!\com\tmax\tibero\jdbc\msg\TbMsgLobCreateTempReply.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */