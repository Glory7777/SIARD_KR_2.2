package com.tmax.tibero.jdbc;

import com.tmax.tibero.jdbc.data.ServerInfo;
import com.tmax.tibero.jdbc.dpl.TbDirPathMetaData;
import com.tmax.tibero.jdbc.dpl.TbDirPathStream;
import com.tmax.tibero.jdbc.driver.TbResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLWarning;

public abstract class TbConnection implements Connection {
  public static final int NO_TXN = 0;
  
  public static final int LOCAL_TXN = 1;
  
  public static final int GLOBAL_TXN = 2;
  
  public abstract void addWarning(SQLWarning paramSQLWarning);
  
  public abstract void closeCursor(TbResultSet paramTbResultSet, int paramInt) throws SQLException;
  
  public abstract TbBlob createTbBlob() throws SQLException;
  
  public abstract TbClob createTbClob() throws SQLException;
  
  public abstract TbDirPathStream createDirPathStream(TbDirPathMetaData paramTbDirPathMetaData) throws SQLException;
  
  public abstract TbNClob createTbNClob() throws SQLException;
  
  public abstract TbSQLInfo getLastExecutedSqlinfo() throws SQLException;
  
  public abstract String getNlsDate();
  
  public abstract String getNlsTimestamp();
  
  public abstract int getSerialNo();
  
  public abstract int getServerCharSet();
  
  public abstract ServerInfo getServerInfo();
  
  public abstract int getServerNCharSet();
  
  public abstract int getSessionId();
  
  public abstract boolean isPooledConnection();
  
  public abstract boolean isSessionClosed();
  
  public abstract PreparedStatement prepareStatement(String paramString, boolean paramBoolean) throws SQLException;
  
  public abstract void resetSession() throws SQLException;
}


/* Location:              C:\Users\Lenovo\Desktop\tibero\tibero6-jdbc.jar!\com\tmax\tibero\jdbc\TbConnection.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */