package com.tmax.tibero.jdbc.comm;

import com.tmax.tibero.jdbc.TbLob;
import java.sql.SQLException;

public interface TbLobAccessor {
  boolean close(TbLob paramTbLob) throws SQLException;
  
  void freeTemporary(TbLob paramTbLob) throws SQLException;
  
  long length(TbLob paramTbLob) throws SQLException;
  
  boolean open(TbLob paramTbLob, int paramInt) throws SQLException;
  
  long position(TbLob paramTbLob1, TbLob paramTbLob2, long paramLong) throws SQLException;
  
  void truncate(TbLob paramTbLob, long paramLong) throws SQLException;
}


/* Location:              C:\Users\lenovo\Desktop\siard\jdbc\tibero\tibero6\linux\tibero6-jdbc-18.jar!\com\tmax\tibero\jdbc\comm\TbLobAccessor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */