package com.tmax.tibero.jdbc.msg;

import com.tmax.tibero.jdbc.comm.TbStreamDataReader;
import java.sql.SQLException;

public class TbUniqChkIdx {
  public int idxGtid;
  
  public void set(int paramInt) {
    this.idxGtid = paramInt;
  }
  
  public void deserialize(TbStreamDataReader paramTbStreamDataReader) throws SQLException {
    this.idxGtid = paramTbStreamDataReader.readInt32();
  }
}


/* Location:              C:\TmaxData\tibero6\client\lib\jar\tibero6-jdbc.jar!\com\tmax\tibero\jdbc\msg\TbUniqChkIdx.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */