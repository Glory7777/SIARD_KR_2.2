package com.tmax.tibero.jdbc.msg;

import com.tmax.tibero.jdbc.comm.TbStreamDataReader;
import java.sql.SQLException;

public class TbIndoubtTx {
  public int coorNodeId;
  
  public int coorTxid;
  
  public int txType_DO_NOT_USE_DIRECTLY;
  
  public int localNodeId;
  
  public int localTxid;
  
  public int localTxStatus;
  
  public int finalTxStatus;
  
  public void set(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7) {
    this.coorNodeId = paramInt1;
    this.coorTxid = paramInt2;
    this.txType_DO_NOT_USE_DIRECTLY = paramInt3;
    this.localNodeId = paramInt4;
    this.localTxid = paramInt5;
    this.localTxStatus = paramInt6;
    this.finalTxStatus = paramInt7;
  }
  
  public void deserialize(TbStreamDataReader paramTbStreamDataReader) throws SQLException {
    this.coorNodeId = paramTbStreamDataReader.readInt32();
    this.coorTxid = paramTbStreamDataReader.readInt32();
    this.txType_DO_NOT_USE_DIRECTLY = paramTbStreamDataReader.readInt32();
    this.localNodeId = paramTbStreamDataReader.readInt32();
    this.localTxid = paramTbStreamDataReader.readInt32();
    this.localTxStatus = paramTbStreamDataReader.readInt32();
    this.finalTxStatus = paramTbStreamDataReader.readInt32();
  }
}


/* Location:              C:\TmaxData\tibero6\client\lib\jar\tibero6-jdbc.jar!\com\tmax\tibero\jdbc\msg\TbIndoubtTx.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */