package com.tmax.tibero.jdbc.msg;

import com.tmax.tibero.jdbc.comm.TbStreamDataReader;
import com.tmax.tibero.jdbc.comm.TbStreamDataWriter;
import com.tmax.tibero.jdbc.msg.common.TbMsg;
import java.sql.SQLException;

public class TbMsgHlLoadDataReply extends TbMsg {
  public int hitCnt;
  
  public int failedCnt;
  
  public void serialize(TbStreamDataWriter paramTbStreamDataWriter) throws SQLException {}
  
  public void deserialize(TbStreamDataReader paramTbStreamDataReader) throws SQLException {
    this.hitCnt = paramTbStreamDataReader.readInt32();
    this.failedCnt = paramTbStreamDataReader.readInt32();
  }
}


/* Location:              C:\Users\Lenovo\Desktop\tibero\tibero6-jdbc.jar!\com\tmax\tibero\jdbc\msg\TbMsgHlLoadDataReply.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */