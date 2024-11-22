package com.tmax.tibero.jdbc.msg;

import com.tmax.tibero.jdbc.comm.TbStreamDataReader;
import com.tmax.tibero.jdbc.comm.TbStreamDataWriter;
import com.tmax.tibero.jdbc.msg.common.TbMsg;
import java.sql.SQLException;

public class TbMsgHlAdminToHyperData extends TbMsg {
  public byte[] messageContent;
  
  public int messageContentLen;
  
  public int version;
  
  public void serialize(TbStreamDataWriter paramTbStreamDataWriter) throws SQLException {}
  
  public void deserialize(TbStreamDataReader paramTbStreamDataReader) throws SQLException {
    this.messageContentLen = paramTbStreamDataReader.readInt32();
    if (this.messageContentLen != 0) {
      this.messageContent = new byte[this.messageContentLen];
      paramTbStreamDataReader.readPadBytes(this.messageContent, 0, this.messageContentLen);
    } else {
      paramTbStreamDataReader.moveReadOffset(4);
      this.messageContent = new byte[0];
    } 
    this.version = paramTbStreamDataReader.readInt32();
  }
}


/* Location:              C:\Users\lenovo\Desktop\siard\jdbc\tibero\tibero6\linux\tibero6-jdbc-18.jar!\com\tmax\tibero\jdbc\msg\TbMsgHlAdminToHyperData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */