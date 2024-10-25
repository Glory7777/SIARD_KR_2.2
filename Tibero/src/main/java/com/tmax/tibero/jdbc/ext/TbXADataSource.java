package com.tmax.tibero.jdbc.ext;

import com.tmax.tibero.jdbc.driver.TbConnection;
import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.XAConnection;
import javax.sql.XADataSource;

public class TbXADataSource extends TbConnectionPoolDataSource implements XADataSource {
  private static final long serialVersionUID = 7001142423129336182L;
  
  public XAConnection getXAConnection() throws SQLException {
    return getXAConnection((String)null, (String)null);
  }
  
  public XAConnection getXAConnection(String paramString1, String paramString2) throws SQLException {
    this.info.setXA(true);
    Connection connection = getConnection(paramString1, paramString2);
    ((TbConnection)connection).getTbXAComm().xaOpen();
    return new TbXAConnection((TbConnection)connection);
  }
}


/* Location:              C:\Users\Lenovo\Desktop\tibero\tibero6-jdbc.jar!\com\tmax\tibero\jdbc\ext\TbXADataSource.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */