package com.tmax.tibero.jdbc.msg;

import com.tmax.tibero.jdbc.comm.TbStreamDataReader;
import com.tmax.tibero.jdbc.comm.TbStreamDataWriter;
import com.tmax.tibero.jdbc.msg.common.TbMsg;
import java.sql.SQLException;

public class TbMsgExecutePrefetchReply extends TbMsg {
  public byte[] ppid = new byte[8];
  
  public int affectedCnt;
  
  public int csrId;
  
  public int colCnt;
  
  public int hiddenColCnt;
  
  public int colMetaArrayCnt;
  
  public TbColumnDesc[] colMeta;
  
  public int rowCnt;
  
  public int isFetchCompleted;
  
  public int rowChunkSize;
  
  public void serialize(TbStreamDataWriter paramTbStreamDataWriter) throws SQLException {}
  
  public void deserialize(TbStreamDataReader paramTbStreamDataReader) throws SQLException {
    paramTbStreamDataReader.readBytes(this.ppid, 0, 8);
    this.affectedCnt = paramTbStreamDataReader.readInt32();
    this.csrId = paramTbStreamDataReader.readInt32();
    this.colCnt = paramTbStreamDataReader.readInt32();
    this.hiddenColCnt = paramTbStreamDataReader.readInt32();
    int i = paramTbStreamDataReader.readInt32();
    if (i > 0) {
      this.colMeta = new TbColumnDesc[i];
      for (byte b = 0; b < i; b++) {
        this.colMeta[b] = new TbColumnDesc();
        this.colMeta[b].deserialize(paramTbStreamDataReader);
      } 
    } else {
      this.colMeta = null;
    } 
    this.rowCnt = paramTbStreamDataReader.readInt32();
    this.isFetchCompleted = paramTbStreamDataReader.readInt32();
    this.rowChunkSize = paramTbStreamDataReader.readInt32();
  }
}


/* Location:              C:\Users\lenovo\Desktop\siard\jdbc\tibero\tibero6\linux\tibero6-jdbc-18.jar!\com\tmax\tibero\jdbc\msg\TbMsgExecutePrefetchReply.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */