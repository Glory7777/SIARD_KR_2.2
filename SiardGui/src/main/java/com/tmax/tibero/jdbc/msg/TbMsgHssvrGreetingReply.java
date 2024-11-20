package com.tmax.tibero.jdbc.msg;

import com.tmax.tibero.jdbc.comm.TbStreamDataReader;
import com.tmax.tibero.jdbc.comm.TbStreamDataWriter;
import com.tmax.tibero.jdbc.msg.common.TbMsg;
import java.sql.SQLException;

public class TbMsgHssvrGreetingReply extends TbMsg {
  public int verMajor;
  
  public int verMinor;
  
  public String revision;
  
  public int rc;
  
  public String msg;
  
  public byte[] aux;
  
  public int auxLen;
  
  public void serialize(TbStreamDataWriter paramTbStreamDataWriter) throws SQLException {}
  
  public void deserialize(TbStreamDataReader paramTbStreamDataReader) throws SQLException {
    this.verMajor = paramTbStreamDataReader.readInt32();
    this.verMinor = paramTbStreamDataReader.readInt32();
    int i = paramTbStreamDataReader.readInt32();
    this.revision = paramTbStreamDataReader.readDBDecodedPadString(i);
    this.rc = paramTbStreamDataReader.readInt32();
    int j = paramTbStreamDataReader.readInt32();
    this.msg = paramTbStreamDataReader.readDBDecodedPadString(j);
    this.auxLen = paramTbStreamDataReader.readInt32();
    if (this.auxLen != 0) {
      this.aux = new byte[this.auxLen];
      paramTbStreamDataReader.readPadBytes(this.aux, 0, this.auxLen);
    } else {
      paramTbStreamDataReader.moveReadOffset(4);
      this.aux = new byte[0];
    } 
  }
}


/* Location:              C:\Users\lenovo\Desktop\siard\jdbc\tibero\tibero6\linux\tibero6-jdbc-18.jar!\com\tmax\tibero\jdbc\msg\TbMsgHssvrGreetingReply.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */