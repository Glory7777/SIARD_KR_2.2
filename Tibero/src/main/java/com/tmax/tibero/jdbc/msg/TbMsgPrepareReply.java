package com.tmax.tibero.jdbc.msg;

import com.tmax.tibero.jdbc.comm.TbStreamDataReader;
import com.tmax.tibero.jdbc.comm.TbStreamDataWriter;
import com.tmax.tibero.jdbc.msg.common.TbMsg;
import java.sql.SQLException;

public class TbMsgPrepareReply extends TbMsg {
  public byte[] ppid = new byte[8];
  
  public int outColCnt;
  
  public int bindParamCnt;
  
  public int isPreparedDdl;
  
  public int hiddenColCnt;
  
  public String csrName;
  
  public int colDescArrayCnt;
  
  public TbColumnDesc[] colDesc;
  
  public int bindParamMetaArrayCnt;
  
  public TbBindParamDesc[] bindParamMeta;
  
  public void serialize(TbStreamDataWriter paramTbStreamDataWriter) throws SQLException {}
  
  public void deserialize(TbStreamDataReader paramTbStreamDataReader) throws SQLException {
    paramTbStreamDataReader.readBytes(this.ppid, 0, 8);
    this.outColCnt = paramTbStreamDataReader.readInt32();
    this.bindParamCnt = paramTbStreamDataReader.readInt32();
    this.isPreparedDdl = paramTbStreamDataReader.readInt32();
    this.hiddenColCnt = paramTbStreamDataReader.readInt32();
    int i = paramTbStreamDataReader.readInt32();
    this.csrName = paramTbStreamDataReader.readDBDecodedPadString(i);
    int j = paramTbStreamDataReader.readInt32();
    if (j > 0) {
      this.colDesc = new TbColumnDesc[j];
      for (byte b = 0; b < j; b++) {
        this.colDesc[b] = new TbColumnDesc();
        this.colDesc[b].deserialize(paramTbStreamDataReader);
      } 
    } else {
      this.colDesc = null;
    } 
    int k = paramTbStreamDataReader.readInt32();
    if (k > 0) {
      this.bindParamMeta = new TbBindParamDesc[k];
      for (byte b = 0; b < k; b++) {
        this.bindParamMeta[b] = new TbBindParamDesc();
        this.bindParamMeta[b].deserialize(paramTbStreamDataReader);
      } 
    } else {
      this.bindParamMeta = null;
    } 
  }
}


/* Location:              C:\TmaxData\tibero6\client\lib\jar\tibero6-jdbc.jar!\com\tmax\tibero\jdbc\msg\TbMsgPrepareReply.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */