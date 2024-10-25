package com.tmax.tibero.jdbc;

import java.sql.SQLException;

public class TbXMLInputStream extends TbLobInputStream {
  public TbXMLInputStream(TbClob paramTbClob) throws SQLException {
    super(paramTbClob, 1L, 2147483647L);
  }
}


/* Location:              C:\Users\Lenovo\Desktop\tibero\tibero6-jdbc.jar!\com\tmax\tibero\jdbc\TbXMLInputStream.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */