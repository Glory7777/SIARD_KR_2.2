package com.tmax.tibero.jdbc;

import com.tmax.tibero.DriverConstants;
import com.tmax.tibero.jdbc.data.ConnectionInfo;
import com.tmax.tibero.jdbc.driver.TbConnection;
import com.tmax.tibero.jdbc.err.TbError;
import com.tmax.tibero.jdbc.util.ParseException;
import com.tmax.tibero.jdbc.util.TbUrlParser;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.DriverPropertyInfo;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Properties;
import java.util.logging.Logger;

public class TbDriver implements Driver {
  protected static Connection defaultConn = null;
  
  public boolean acceptsURL(String paramString) throws SQLException {
    try {
      if (TbUrlParser.parseUrl(paramString, null) != null)
        return true; 
    } catch (Exception exception) {}
    return false;
  }
  
  public Connection connect(ConnectionInfo paramConnectionInfo) throws SQLException {
    return connectInternal(paramConnectionInfo);
  }
  
  public Connection connect(String paramString, Properties paramProperties) throws SQLException {
    ConnectionInfo connectionInfo = null;
    if (TbUrlParser.isInternalUrl(paramString)) {
      connectionInfo = new ConnectionInfo();
      connectionInfo.setInternal(true);
    } else {
      if (!TbUrlParser.isTiberoUrl(paramString))
        return null; 
      try {
        connectionInfo = TbUrlParser.parseUrl(paramString, paramProperties);
      } catch (ParseException parseException) {
        throw TbError.newSQLException(-90605, parseException);
      } 
    } 
    return connectInternal(connectionInfo);
  }
  
  private Connection connectInternal(ConnectionInfo paramConnectionInfo) throws SQLException {
    if (paramConnectionInfo == null)
      return null; 
    try {
      TbConnection tbConnection = new TbConnection();
      String str = paramConnectionInfo.getNewPassword();
      if (str != null && str.length() != 0) {
        try {
          tbConnection.openConnection(paramConnectionInfo, false);
        } catch (SQLException sQLException) {
          if (sQLException.getErrorCode() == 17002);
          tbConnection.openConnection(paramConnectionInfo, true);
        } 
      } else {
        tbConnection.openConnection(paramConnectionInfo, false);
      } 
      return (Connection)tbConnection;
    } catch (SQLException sQLException) {
      throw sQLException;
    } catch (Exception exception) {
      return null;
    } 
  }
  
  public Connection defaultConnection() throws SQLException {
    synchronized (TbDriver.class) {
      if (defaultConn == null || defaultConn.isClosed())
        defaultConn = DriverManager.getConnection("jdbc:default:connection"); 
    } 
    return defaultConn;
  }
  
  public int getMajorVersion() {
    return Integer.parseInt(DriverConstants.JDBC_MAJOR);
  }
  
  public int getMinorVersion() {
    return Integer.parseInt(DriverConstants.JDBC_MINOR);
  }
  
  public DriverPropertyInfo[] getPropertyInfo(String paramString, Properties paramProperties) throws SQLException {
    return new DriverPropertyInfo[0];
  }
  
  public int getRevision() {
    return Integer.parseInt(DriverConstants.JDBC_REVISION);
  }
  
  public boolean jdbcCompliant() {
    return true;
  }
  
  public Logger getParentLogger() throws SQLFeatureNotSupportedException {
    throw new SQLFeatureNotSupportedException();
  }
  
  static {
    try {
      DriverManager.registerDriver(new TbDriver());
    } catch (SQLException sQLException) {
      throw new RuntimeException("Failed to register TiberoDriver:" + sQLException.getMessage());
    } 
  }
}


/* Location:              C:\Users\lenovo\Desktop\siard\jdbc\tibero\tibero6\linux\tibero6-jdbc-18.jar!\com\tmax\tibero\jdbc\TbDriver.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */