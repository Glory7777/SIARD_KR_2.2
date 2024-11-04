package com.tmax.tibero.jdbc.msg;

import com.tmax.tibero.jdbc.comm.TbStreamDataReader;
import java.sql.SQLException;

public class TbHlHandleStatus {
  public int handleId;
  
  public int loadType;
  
  public int requestCount;
  
  public int doneCount;
  
  public int loadQueueSizeArrayCnt;
  
  public TbHlLoadQueue[] loadQueueSize;
  
  public int bufferCountInLoadBuf;
  
  public void set(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, TbHlLoadQueue[] paramArrayOfTbHlLoadQueue, int paramInt6) {
    this.handleId = paramInt1;
    this.loadType = paramInt2;
    this.requestCount = paramInt3;
    this.doneCount = paramInt4;
    this.loadQueueSizeArrayCnt = paramInt5;
    this.loadQueueSize = paramArrayOfTbHlLoadQueue;
    this.bufferCountInLoadBuf = paramInt6;
  }
  
  public void deserialize(TbStreamDataReader paramTbStreamDataReader) throws SQLException {
    this.handleId = paramTbStreamDataReader.readInt32();
    this.loadType = paramTbStreamDataReader.readInt32();
    this.requestCount = paramTbStreamDataReader.readInt32();
    this.doneCount = paramTbStreamDataReader.readInt32();
    int i = paramTbStreamDataReader.readInt32();
    if (i > 0) {
      this.loadQueueSize = new TbHlLoadQueue[i];
      for (byte b = 0; b < i; b++) {
        this.loadQueueSize[b] = new TbHlLoadQueue();
        this.loadQueueSize[b].deserialize(paramTbStreamDataReader);
      } 
    } else {
      this.loadQueueSize = null;
    } 
    this.bufferCountInLoadBuf = paramTbStreamDataReader.readInt32();
  }
}


/* Location:              C:\TmaxData\tibero6\client\lib\jar\tibero6-jdbc.jar!\com\tmax\tibero\jdbc\msg\TbHlHandleStatus.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */