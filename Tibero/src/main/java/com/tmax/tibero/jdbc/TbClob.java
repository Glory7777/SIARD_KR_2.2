package com.tmax.tibero.jdbc;

import com.tmax.tibero.jdbc.driver.TbConnection;
import com.tmax.tibero.jdbc.err.TbError;
import java.sql.Connection;
import java.sql.SQLException;

public class TbClob extends TbClobBase {
  private static final TbClob EMPTY_CLOB = new TbClob(null, new byte[1], false);
  
  private static final TbClob EMPTY_NCLOB = new TbClob(null, new byte[1], false);
  
  public TbClob(TbConnection paramTbConnection, byte[] paramArrayOfbyte, boolean paramBoolean) {
    super(paramTbConnection, paramArrayOfbyte, paramBoolean);
  }
  
  public static TbClob createEmptyClob() {
    return EMPTY_CLOB;
  }
  
  public static TbClob createEmptyNClob() {
    return EMPTY_NCLOB;
  }
  
  public static TbClob createTemporary(Connection paramConnection) throws SQLException {
    if (!(paramConnection instanceof TbConnection))
      throw TbError.newSQLException(-590713); 
    return ((TbConnection)paramConnection).createTbClob();
  }
  
  public static TbClob createTemporaryNClob(Connection paramConnection) throws SQLException {
    if (!(paramConnection instanceof TbConnection))
      throw TbError.newSQLException(-590713); 
    return new TbClob((TbConnection)paramConnection, ((TbConnection)paramConnection).getClobAccessor().createTemporaryNClob(), false);
  }
  
  public static void freeTemporary(TbClob paramTbClob) throws SQLException {
    if (paramTbClob == null)
      throw TbError.newSQLException(-590713); 
    paramTbClob.free();
  }
  
  public void freeTemporary() throws SQLException {
    free();
  }
}


/* Location:              C:\TmaxData\tibero6\client\lib\jar\tibero6-jdbc.jar!\com\tmax\tibero\jdbc\TbClob.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */