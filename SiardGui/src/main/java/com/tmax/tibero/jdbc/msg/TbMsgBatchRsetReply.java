package com.tmax.tibero.jdbc.msg;

import com.tmax.tibero.jdbc.comm.TbStreamDataReader;
import com.tmax.tibero.jdbc.comm.TbStreamDataWriter;
import com.tmax.tibero.jdbc.msg.common.TbMsgError;
import java.sql.SQLException;

public class TbMsgBatchRsetReply extends TbMsgError {
  public byte[] ppid = new byte[8];
  
  public int csrId;
  
  public int colCnt;
  
  public int hiddenColCnt;
  
  public int colMetaArrayCnt;
  
  public TbColumnDesc[] colMeta;
  
  public int executedCnt;
  
  public int affectedCntArrayCnt;
  
  public TbAffectedCnt[] affectedCnt;
  
  public byte[] errorStack;
  
  public int errorStackLen;
  
  public void serialize(TbStreamDataWriter paramTbStreamDataWriter) throws SQLException {}
  
  public void deserialize(TbStreamDataReader paramTbStreamDataReader) throws SQLException {
    paramTbStreamDataReader.readBytes(this.ppid, 0, 8);
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
    this.executedCnt = paramTbStreamDataReader.readInt32();
    int j = paramTbStreamDataReader.readInt32();
    if (j > 0) {
      this.affectedCnt = new TbAffectedCnt[j];
      for (byte b = 0; b < j; b++) {
        this.affectedCnt[b] = new TbAffectedCnt();
        this.affectedCnt[b].deserialize(paramTbStreamDataReader);
      } 
    } else {
      this.affectedCnt = null;
    } 
    readErrorStackInfo(paramTbStreamDataReader);
  }
}


/* Location:              C:\Users\lenovo\Desktop\siard\jdbc\tibero\tibero6\linux\tibero6-jdbc-18.jar!\com\tmax\tibero\jdbc\msg\TbMsgBatchRsetReply.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */