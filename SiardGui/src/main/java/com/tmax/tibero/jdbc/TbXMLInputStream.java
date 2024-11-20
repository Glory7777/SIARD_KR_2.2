package com.tmax.tibero.jdbc;

import java.sql.SQLException;

public class TbXMLInputStream extends TbLobInputStream {
  public TbXMLInputStream(TbClob paramTbClob) throws SQLException {
    super(paramTbClob, 1L, 2147483647L);
  }
}


/* Location:              C:\Users\lenovo\Desktop\siard\jdbc\tibero\tibero6\linux\tibero6-jdbc-18.jar!\com\tmax\tibero\jdbc\TbXMLInputStream.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */