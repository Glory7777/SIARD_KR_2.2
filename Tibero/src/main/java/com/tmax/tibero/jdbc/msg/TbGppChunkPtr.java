package com.tmax.tibero.jdbc.msg;

import com.tmax.tibero.jdbc.comm.TbStreamDataReader;
import java.sql.SQLException;

public class TbGppChunkPtr {
  public byte[] ptr = new byte[8];
  
  public void set(byte[] paramArrayOfbyte) {
    this.ptr = paramArrayOfbyte;
  }
  
  public void deserialize(TbStreamDataReader paramTbStreamDataReader) throws SQLException {
    paramTbStreamDataReader.readBytes(this.ptr, 0, 8);
  }
}


/* Location:              C:\TmaxData\tibero6\client\lib\jar\tibero6-jdbc.jar!\com\tmax\tibero\jdbc\msg\TbGppChunkPtr.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */