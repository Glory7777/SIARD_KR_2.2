package com.tmax.tibero.jdbc.msg;

import com.tmax.tibero.jdbc.comm.TbStreamDataReader;
import com.tmax.tibero.jdbc.comm.TbStreamDataWriter;
import com.tmax.tibero.jdbc.msg.common.TbMsg;
import java.sql.SQLException;

public class TbMsgHlDbConnectRequest extends TbMsg {
  public int dbType;
  
  public String dbName;
  
  public String dbIp;
  
  public int dbPort;
  
  public String userId;
  
  public String password;
  
  public void serialize(TbStreamDataWriter paramTbStreamDataWriter) throws SQLException {}
  
  public void deserialize(TbStreamDataReader paramTbStreamDataReader) throws SQLException {
    this.dbType = paramTbStreamDataReader.readInt32();
    int i = paramTbStreamDataReader.readInt32();
    this.dbName = paramTbStreamDataReader.readDBDecodedPadString(i);
    int j = paramTbStreamDataReader.readInt32();
    this.dbIp = paramTbStreamDataReader.readDBDecodedPadString(j);
    this.dbPort = paramTbStreamDataReader.readInt32();
    int k = paramTbStreamDataReader.readInt32();
    this.userId = paramTbStreamDataReader.readDBDecodedPadString(k);
    int m = paramTbStreamDataReader.readInt32();
    this.password = paramTbStreamDataReader.readDBDecodedPadString(m);
  }
}


/* Location:              C:\Users\lenovo\Desktop\siard\jdbc\tibero\tibero6\linux\tibero6-jdbc-18.jar!\com\tmax\tibero\jdbc\msg\TbMsgHlDbConnectRequest.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */