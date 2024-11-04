package com.tmax.tibero.jdbc.comm;

import com.tmax.tibero.jdbc.TbBlob;
import java.sql.SQLException;

public interface TbBlobAccessor extends TbLobAccessor {
  byte[] createTemporaryBlob() throws SQLException;
  
  long position(TbBlob paramTbBlob, byte[] paramArrayOfbyte, long paramLong) throws SQLException;
  
  long read(TbBlob paramTbBlob, long paramLong1, byte[] paramArrayOfbyte, long paramLong2, long paramLong3) throws SQLException;
  
  long write(TbBlob paramTbBlob, long paramLong1, byte[] paramArrayOfbyte, long paramLong2, long paramLong3) throws SQLException;
}


/* Location:              C:\TmaxData\tibero6\client\lib\jar\tibero6-jdbc.jar!\com\tmax\tibero\jdbc\comm\TbBlobAccessor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */