package com.tmax.tibero.jdbc.msg;

import com.tmax.tibero.jdbc.comm.TbStreamDataReader;
import com.tmax.tibero.jdbc.comm.TbStreamDataWriter;
import com.tmax.tibero.jdbc.msg.common.TbMsg;
import java.sql.SQLException;

public class TbMsgHlActive extends TbMsg {
  public String adminIp;
  
  public int adminPort;
  
  public int mainPort;
  
  public int transformBufferSize;
  
  public int transformBufferCount;
  
  public int readBufferSize;
  
  public int readBufferCount;
  
  public int workerCount;
  
  public int bindSize;
  
  public int adapterListArrayCnt;
  
  public TbHlAdapter[] adapterList;
  
  public void serialize(TbStreamDataWriter paramTbStreamDataWriter) throws SQLException {}
  
  public void deserialize(TbStreamDataReader paramTbStreamDataReader) throws SQLException {
    int i = paramTbStreamDataReader.readInt32();
    this.adminIp = paramTbStreamDataReader.readDBDecodedPadString(i);
    this.adminPort = paramTbStreamDataReader.readInt32();
    this.mainPort = paramTbStreamDataReader.readInt32();
    this.transformBufferSize = paramTbStreamDataReader.readInt32();
    this.transformBufferCount = paramTbStreamDataReader.readInt32();
    this.readBufferSize = paramTbStreamDataReader.readInt32();
    this.readBufferCount = paramTbStreamDataReader.readInt32();
    this.workerCount = paramTbStreamDataReader.readInt32();
    this.bindSize = paramTbStreamDataReader.readInt32();
    int j = paramTbStreamDataReader.readInt32();
    if (j > 0) {
      this.adapterList = new TbHlAdapter[j];
      for (byte b = 0; b < j; b++) {
        this.adapterList[b] = new TbHlAdapter();
        this.adapterList[b].deserialize(paramTbStreamDataReader);
      } 
    } else {
      this.adapterList = null;
    } 
  }
}


/* Location:              C:\Users\lenovo\Desktop\siard\jdbc\tibero\tibero6\linux\tibero6-jdbc-18.jar!\com\tmax\tibero\jdbc\msg\TbMsgHlActive.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */