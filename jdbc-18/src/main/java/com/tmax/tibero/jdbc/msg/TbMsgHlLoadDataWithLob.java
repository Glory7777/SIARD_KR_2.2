package com.tmax.tibero.jdbc.msg;

import com.tmax.tibero.jdbc.comm.TbStreamDataReader;
import com.tmax.tibero.jdbc.comm.TbStreamDataWriter;
import com.tmax.tibero.jdbc.msg.common.TbMsg;
import java.sql.SQLException;

public class TbMsgHlLoadDataWithLob extends TbMsg {
  public int rowNum;
  
  public int partNo;
  
  public int totalPartNo;
  
  public int lobNo;
  
  public int totalLobNo;
  
  public int partialFlag;
  
  public byte[] data;
  
  public int dataLen;
  
  public void serialize(TbStreamDataWriter paramTbStreamDataWriter) throws SQLException {}
  
  public void deserialize(TbStreamDataReader paramTbStreamDataReader) throws SQLException {
    this.rowNum = paramTbStreamDataReader.readInt32();
    this.partNo = paramTbStreamDataReader.readInt32();
    this.totalPartNo = paramTbStreamDataReader.readInt32();
    this.lobNo = paramTbStreamDataReader.readInt32();
    this.totalLobNo = paramTbStreamDataReader.readInt32();
    this.partialFlag = paramTbStreamDataReader.readInt32();
    this.dataLen = paramTbStreamDataReader.readInt32();
    if (this.dataLen != 0) {
      this.data = new byte[this.dataLen];
      paramTbStreamDataReader.readPadBytes(this.data, 0, this.dataLen);
    } else {
      paramTbStreamDataReader.moveReadOffset(4);
      this.data = new byte[0];
    } 
  }
}


/* Location:              C:\Users\lenovo\Desktop\siard\jdbc\tibero\tibero6\linux\tibero6-jdbc-18.jar!\com\tmax\tibero\jdbc\msg\TbMsgHlLoadDataWithLob.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */