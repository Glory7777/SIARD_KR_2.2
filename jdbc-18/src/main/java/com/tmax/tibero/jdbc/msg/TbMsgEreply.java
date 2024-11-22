package com.tmax.tibero.jdbc.msg;

import com.tmax.tibero.jdbc.comm.TbStreamDataReader;
import com.tmax.tibero.jdbc.comm.TbStreamDataWriter;
import com.tmax.tibero.jdbc.msg.common.TbMsgError;
import java.sql.SQLException;

public class TbMsgEreply extends TbMsgError {
  public int flag;
  
  public byte[] errorStack;
  
  public int errorStackLen;
  
  public void serialize(TbStreamDataWriter paramTbStreamDataWriter) throws SQLException {}
  
  public void deserialize(TbStreamDataReader paramTbStreamDataReader) throws SQLException {
    this.flag = paramTbStreamDataReader.readInt32();
    readErrorStackInfo(paramTbStreamDataReader);
  }
}


/* Location:              C:\Users\lenovo\Desktop\siard\jdbc\tibero\tibero6\linux\tibero6-jdbc-18.jar!\com\tmax\tibero\jdbc\msg\TbMsgEreply.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */