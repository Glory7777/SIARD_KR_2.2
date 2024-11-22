package com.tmax.tibero.jdbc.msg;

import com.tmax.tibero.jdbc.comm.TbStreamDataReader;
import com.tmax.tibero.jdbc.comm.TbStreamDataWriter;
import com.tmax.tibero.jdbc.msg.common.TbMsg;
import java.sql.SQLException;

public class TbMsgHlInitiateLoad extends TbMsg {
  public String userId;
  
  public String password;
  
  public String dbIp;
  
  public int dbPort;
  
  public String dbName;
  
  public byte[] ctrlInfo;
  
  public int ctrlInfoLen;
  
  public int largeData;
  
  public int parallelFlag;
  
  public int errors;
  
  public int disableIdx;
  
  public int isRemote;
  
  public int isDpl;
  
  public void serialize(TbStreamDataWriter paramTbStreamDataWriter) throws SQLException {}
  
  public void deserialize(TbStreamDataReader paramTbStreamDataReader) throws SQLException {
    int i = paramTbStreamDataReader.readInt32();
    this.userId = paramTbStreamDataReader.readDBDecodedPadString(i);
    int j = paramTbStreamDataReader.readInt32();
    this.password = paramTbStreamDataReader.readDBDecodedPadString(j);
    int k = paramTbStreamDataReader.readInt32();
    this.dbIp = paramTbStreamDataReader.readDBDecodedPadString(k);
    this.dbPort = paramTbStreamDataReader.readInt32();
    int m = paramTbStreamDataReader.readInt32();
    this.dbName = paramTbStreamDataReader.readDBDecodedPadString(m);
    this.ctrlInfoLen = paramTbStreamDataReader.readInt32();
    if (this.ctrlInfoLen != 0) {
      this.ctrlInfo = new byte[this.ctrlInfoLen];
      paramTbStreamDataReader.readPadBytes(this.ctrlInfo, 0, this.ctrlInfoLen);
    } else {
      paramTbStreamDataReader.moveReadOffset(4);
      this.ctrlInfo = new byte[0];
    } 
    this.largeData = paramTbStreamDataReader.readInt32();
    this.parallelFlag = paramTbStreamDataReader.readInt32();
    this.errors = paramTbStreamDataReader.readInt32();
    this.disableIdx = paramTbStreamDataReader.readInt32();
    this.isRemote = paramTbStreamDataReader.readInt32();
    this.isDpl = paramTbStreamDataReader.readInt32();
  }
}


/* Location:              C:\Users\lenovo\Desktop\siard\jdbc\tibero\tibero6\linux\tibero6-jdbc-18.jar!\com\tmax\tibero\jdbc\msg\TbMsgHlInitiateLoad.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */