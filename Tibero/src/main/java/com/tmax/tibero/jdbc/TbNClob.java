package com.tmax.tibero.jdbc;

import com.tmax.tibero.jdbc.driver.TbConnection;
import java.sql.NClob;

public class TbNClob extends TbClobBase implements NClob {
  private static final TbNClob EMPTY_NCLOB = new TbNClob(null, new byte[1], false);
  
  public TbNClob(TbConnection paramTbConnection, byte[] paramArrayOfbyte, boolean paramBoolean) {
    super(paramTbConnection, paramArrayOfbyte, paramBoolean);
  }
  
  public static TbNClob createEmptyNClob() {
    return EMPTY_NCLOB;
  }
}


/* Location:              C:\Users\Lenovo\Desktop\tibero\tibero6-jdbc.jar!\com\tmax\tibero\jdbc\TbNClob.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */