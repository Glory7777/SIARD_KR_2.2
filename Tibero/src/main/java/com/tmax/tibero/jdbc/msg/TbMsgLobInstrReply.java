package com.tmax.tibero.jdbc.msg;

import com.tmax.tibero.jdbc.comm.TbStreamDataReader;
import com.tmax.tibero.jdbc.comm.TbStreamDataWriter;
import com.tmax.tibero.jdbc.msg.common.TbMsg;
import java.sql.SQLException;

public class TbMsgLobInstrReply extends TbMsg {
  public int offsetHigh;
  
  public int offsetLow;
  
  public void serialize(TbStreamDataWriter paramTbStreamDataWriter) throws SQLException {}
  
  public void deserialize(TbStreamDataReader paramTbStreamDataReader) throws SQLException {
    this.offsetHigh = paramTbStreamDataReader.readInt32();
    this.offsetLow = paramTbStreamDataReader.readInt32();
  }
}


/* Location:              C:\Users\Lenovo\Desktop\tibero\tibero6-jdbc.jar!\com\tmax\tibero\jdbc\msg\TbMsgLobInstrReply.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */