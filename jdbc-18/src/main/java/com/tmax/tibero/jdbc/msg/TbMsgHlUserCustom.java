package com.tmax.tibero.jdbc.msg;

import com.tmax.tibero.jdbc.comm.TbStreamDataReader;
import com.tmax.tibero.jdbc.comm.TbStreamDataWriter;
import com.tmax.tibero.jdbc.msg.common.TbMsg;
import java.sql.SQLException;

public class TbMsgHlUserCustom extends TbMsg {
  public String ipAddress;
  
  public int portNumber;
  
  public int messageNumber;
  
  public byte[] messageContent;
  
  public int messageContentLen;
  
  public int type;
  
  public void serialize(TbStreamDataWriter paramTbStreamDataWriter) throws SQLException {}
  
  public void deserialize(TbStreamDataReader paramTbStreamDataReader) throws SQLException {
    int i = paramTbStreamDataReader.readInt32();
    this.ipAddress = paramTbStreamDataReader.readDBDecodedPadString(i);
    this.portNumber = paramTbStreamDataReader.readInt32();
    this.messageNumber = paramTbStreamDataReader.readInt32();
    this.messageContentLen = paramTbStreamDataReader.readInt32();
    if (this.messageContentLen != 0) {
      this.messageContent = new byte[this.messageContentLen];
      paramTbStreamDataReader.readPadBytes(this.messageContent, 0, this.messageContentLen);
    } else {
      paramTbStreamDataReader.moveReadOffset(4);
      this.messageContent = new byte[0];
    } 
    this.type = paramTbStreamDataReader.readInt32();
  }
}


/* Location:              C:\Users\lenovo\Desktop\siard\jdbc\tibero\tibero6\linux\tibero6-jdbc-18.jar!\com\tmax\tibero\jdbc\msg\TbMsgHlUserCustom.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */