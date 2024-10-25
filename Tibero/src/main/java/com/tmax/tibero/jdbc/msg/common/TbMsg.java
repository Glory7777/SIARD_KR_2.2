package com.tmax.tibero.jdbc.msg.common;

import com.tmax.tibero.jdbc.comm.TbStreamDataReader;
import java.sql.SQLException;

public abstract class TbMsg implements TbMsgSerializable {
  protected int autoCommit;
  
  protected int msgType;
  
  protected int msgBodySize;
  
  protected long tsn;
  
  public int getAutoCommit() {
    return this.autoCommit;
  }
  
  public int getMsgType() {
    return this.msgType;
  }
  
  public int getMsgBodySize() {
    return this.msgBodySize;
  }
  
  public long getTsn() {
    return this.tsn;
  }
  
  public void setAutoCommit(int paramInt) {
    this.autoCommit = paramInt;
  }
  
  public void setMsgType(int paramInt) {
    this.msgType = paramInt;
  }
  
  public void setMsgBodySize(int paramInt) {
    this.msgBodySize = paramInt;
  }
  
  public void setTsn(long paramLong) {
    this.tsn = paramLong;
  }
  
  public void skipDbmsOutput(TbStreamDataReader paramTbStreamDataReader) throws SQLException {
    int i = paramTbStreamDataReader.readInt32();
    if (i == 0)
      i = 4; 
    paramTbStreamDataReader.moveReadOffset(i);
  }
  
  public boolean equals(Object paramObject) {
    return false;
  }
}


/* Location:              C:\Users\Lenovo\Desktop\tibero\tibero6-jdbc.jar!\com\tmax\tibero\jdbc\msg\common\TbMsg.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */