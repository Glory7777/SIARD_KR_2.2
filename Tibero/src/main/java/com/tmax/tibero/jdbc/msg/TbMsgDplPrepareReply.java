package com.tmax.tibero.jdbc.msg;

import com.tmax.tibero.jdbc.comm.TbStreamDataReader;
import com.tmax.tibero.jdbc.comm.TbStreamDataWriter;
import com.tmax.tibero.jdbc.msg.common.TbMsg;
import java.sql.SQLException;

public class TbMsgDplPrepareReply extends TbMsg {
  public int colMetaArrayCnt;
  
  public TbColumnDesc[] colMeta;
  
  public void serialize(TbStreamDataWriter paramTbStreamDataWriter) throws SQLException {}
  
  public void deserialize(TbStreamDataReader paramTbStreamDataReader) throws SQLException {
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
  }
}


/* Location:              C:\Users\Lenovo\Desktop\tibero\tibero6-jdbc.jar!\com\tmax\tibero\jdbc\msg\TbMsgDplPrepareReply.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */