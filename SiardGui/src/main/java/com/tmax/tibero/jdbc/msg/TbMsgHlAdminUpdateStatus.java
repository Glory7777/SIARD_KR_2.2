package com.tmax.tibero.jdbc.msg;

import com.tmax.tibero.jdbc.comm.TbStreamDataReader;
import com.tmax.tibero.jdbc.comm.TbStreamDataWriter;
import com.tmax.tibero.jdbc.msg.common.TbMsg;
import java.sql.SQLException;

public class TbMsgHlAdminUpdateStatus extends TbMsg {
  public int machineUpTime;
  
  public int hlUpTime;
  
  public String cpuUsage;
  
  public int totalPhysMem;
  
  public int usedPhysMem;
  
  public int usedPhysMemHl;
  
  public int totalVirtMem;
  
  public int usedVirtMem;
  
  public int usedVirtMemHl;
  
  public int idleReadBufferCount;
  
  public int idleTransformBufferCount;
  
  public int readQueueSize;
  
  public int transformQueueSize;
  
  public int eventProcessRate;
  
  public int readProcessRate;
  
  public int transformProcessRate;
  
  public int loadProcessRate;
  
  public int handleArrayCnt;
  
  public TbHlHandleStatus[] handle;
  
  public void serialize(TbStreamDataWriter paramTbStreamDataWriter) throws SQLException {}
  
  public void deserialize(TbStreamDataReader paramTbStreamDataReader) throws SQLException {
    this.machineUpTime = paramTbStreamDataReader.readInt32();
    this.hlUpTime = paramTbStreamDataReader.readInt32();
    int i = paramTbStreamDataReader.readInt32();
    this.cpuUsage = paramTbStreamDataReader.readDBDecodedPadString(i);
    this.totalPhysMem = paramTbStreamDataReader.readInt32();
    this.usedPhysMem = paramTbStreamDataReader.readInt32();
    this.usedPhysMemHl = paramTbStreamDataReader.readInt32();
    this.totalVirtMem = paramTbStreamDataReader.readInt32();
    this.usedVirtMem = paramTbStreamDataReader.readInt32();
    this.usedVirtMemHl = paramTbStreamDataReader.readInt32();
    this.idleReadBufferCount = paramTbStreamDataReader.readInt32();
    this.idleTransformBufferCount = paramTbStreamDataReader.readInt32();
    this.readQueueSize = paramTbStreamDataReader.readInt32();
    this.transformQueueSize = paramTbStreamDataReader.readInt32();
    this.eventProcessRate = paramTbStreamDataReader.readInt32();
    this.readProcessRate = paramTbStreamDataReader.readInt32();
    this.transformProcessRate = paramTbStreamDataReader.readInt32();
    this.loadProcessRate = paramTbStreamDataReader.readInt32();
    int j = paramTbStreamDataReader.readInt32();
    if (j > 0) {
      this.handle = new TbHlHandleStatus[j];
      for (byte b = 0; b < j; b++) {
        this.handle[b] = new TbHlHandleStatus();
        this.handle[b].deserialize(paramTbStreamDataReader);
      } 
    } else {
      this.handle = null;
    } 
  }
}


/* Location:              C:\Users\lenovo\Desktop\siard\jdbc\tibero\tibero6\linux\tibero6-jdbc-18.jar!\com\tmax\tibero\jdbc\msg\TbMsgHlAdminUpdateStatus.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */