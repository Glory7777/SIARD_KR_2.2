package com.tmax.tibero.jdbc.msg;

import com.tmax.tibero.jdbc.comm.TbStreamDataReader;
import com.tmax.tibero.jdbc.comm.TbStreamDataWriter;
import com.tmax.tibero.jdbc.msg.common.TbMsg;
import java.sql.SQLException;

public class TbMsgGetLastExecutedSqlinfo2Reply extends TbMsg {
  public String sqlid;
  
  public String childNum;
  
  public void serialize(TbStreamDataWriter paramTbStreamDataWriter) throws SQLException {}
  
  public void deserialize(TbStreamDataReader paramTbStreamDataReader) throws SQLException {
    int i = paramTbStreamDataReader.readInt32();
    this.sqlid = paramTbStreamDataReader.readDBDecodedPadString(i);
    int j = paramTbStreamDataReader.readInt32();
    this.childNum = paramTbStreamDataReader.readDBDecodedPadString(j);
  }
}


/* Location:              C:\Users\Lenovo\Desktop\tibero\tibero6-jdbc.jar!\com\tmax\tibero\jdbc\msg\TbMsgGetLastExecutedSqlinfo2Reply.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */