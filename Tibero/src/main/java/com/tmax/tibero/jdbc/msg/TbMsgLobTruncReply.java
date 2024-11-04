package com.tmax.tibero.jdbc.msg;

import com.tmax.tibero.jdbc.comm.TbStreamDataReader;
import com.tmax.tibero.jdbc.comm.TbStreamDataWriter;
import com.tmax.tibero.jdbc.msg.common.TbMsg;
import java.sql.SQLException;

public class TbMsgLobTruncReply extends TbMsg {
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


/* Location:              C:\TmaxData\tibero6\client\lib\jar\tibero6-jdbc.jar!\com\tmax\tibero\jdbc\msg\TbMsgLobTruncReply.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */