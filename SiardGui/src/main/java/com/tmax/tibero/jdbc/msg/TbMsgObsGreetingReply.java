package com.tmax.tibero.jdbc.msg;

import com.tmax.tibero.jdbc.comm.TbStreamDataReader;
import com.tmax.tibero.jdbc.comm.TbStreamDataWriter;
import com.tmax.tibero.jdbc.msg.common.TbMsg;
import java.sql.SQLException;

public class TbMsgObsGreetingReply extends TbMsg {
  public int verMajor;
  
  public int verMinor;
  
  public String revision;
  
  public int clsId;
  
  public int rc;
  
  public void serialize(TbStreamDataWriter paramTbStreamDataWriter) throws SQLException {}
  
  public void deserialize(TbStreamDataReader paramTbStreamDataReader) throws SQLException {
    this.verMajor = paramTbStreamDataReader.readInt32();
    this.verMinor = paramTbStreamDataReader.readInt32();
    int i = paramTbStreamDataReader.readInt32();
    this.revision = paramTbStreamDataReader.readDBDecodedPadString(i);
    this.clsId = paramTbStreamDataReader.readInt32();
    this.rc = paramTbStreamDataReader.readInt32();
  }
}


/* Location:              C:\Users\lenovo\Desktop\siard\jdbc\tibero\tibero6\linux\tibero6-jdbc-18.jar!\com\tmax\tibero\jdbc\msg\TbMsgObsGreetingReply.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */