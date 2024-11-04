package com.tmax.tibero.jdbc.ext;

import com.tmax.tibero.jdbc.TbBlob;
import com.tmax.tibero.jdbc.TbClob;
import com.tmax.tibero.jdbc.TbConnection;
import com.tmax.tibero.jdbc.TbNClob;
import com.tmax.tibero.jdbc.TbSQLInfo;
import com.tmax.tibero.jdbc.TbSQLInfo2;
import com.tmax.tibero.jdbc.data.ServerInfo;
import com.tmax.tibero.jdbc.dpl.TbDirPathMetaData;
import com.tmax.tibero.jdbc.dpl.TbDirPathStream;
import com.tmax.tibero.jdbc.driver.TbCallableStatement;
import com.tmax.tibero.jdbc.driver.TbPreparedStatement;
import com.tmax.tibero.jdbc.driver.TbResultSet;
import com.tmax.tibero.jdbc.err.TbError;
import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.DatabaseMetaData;
import java.sql.NClob;
import java.sql.PreparedStatement;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Savepoint;
import java.sql.Statement;
import java.sql.Struct;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import javax.sql.StatementEvent;
import javax.sql.StatementEventListener;
import javax.transaction.xa.XAException;
import javax.transaction.xa.XAResource;
import javax.transaction.xa.Xid;

public class TbLogicalConnection extends TbConnection {
  private TbConnectionEventHandler eventHandler = null;
  
  private TbConnection physicalConn = null;
  
  private boolean closed = true;
  
  private boolean useXA = false;
  
  private boolean reseted = true;
  
  private HashMap<StatementEventListener, StatementEventListener> listenerMap = new HashMap<StatementEventListener, StatementEventListener>();
  
  public TbLogicalConnection(TbConnectionEventHandler paramTbConnectionEventHandler, TbConnection paramTbConnection, boolean paramBoolean) throws SQLException {
    this.eventHandler = paramTbConnectionEventHandler;
    this.physicalConn = paramTbConnection;
    this.useXA = paramBoolean;
    if (paramTbConnection.isClosed()) {
      this.closed = true;
      throw TbError.newSQLException(-90643);
    } 
    this.closed = false;
    if (!this.physicalConn.isSessionClosed()) {
      this.physicalConn.resetSession();
      this.physicalConn.reuse();
      this.eventHandler.notifyClosedEvent();
    } 
    addStatementEventListener(new StatementEventListener() {
          public void statementClosed(StatementEvent param1StatementEvent) {
            try {
              param1StatementEvent.getStatement().close();
            } catch (SQLException sQLException) {}
          }
          
          public void statementErrorOccurred(StatementEvent param1StatementEvent) {}
        });
  }
  
  public void clearWarnings() throws SQLException {
    if (isClosed())
      throw TbError.newSQLException(-90603); 
    try {
      this.physicalConn.clearWarnings();
    } catch (SQLException sQLException) {
      this.eventHandler.notifyExceptionEvent(sQLException);
      throw sQLException;
    } 
  }
  
  public void close() throws SQLException {
    if (isClosed())
      return; 
    this.closed = true;
    this.physicalConn.resetSession();
    this.physicalConn.reuse();
    this.eventHandler.notifyClosedEvent();
  }
  
  public void commit() throws SQLException {
    if (isClosed())
      throw TbError.newSQLException(-90603); 
    if (isUseXA() && getTxnMode() == 2)
      throw TbError.newSQLException(-90602); 
    try {
      this.physicalConn.commit();
    } catch (SQLException sQLException) {
      this.eventHandler.notifyExceptionEvent(sQLException);
      throw sQLException;
    } 
  }
  
  public void commit(Xid paramXid, boolean paramBoolean) throws XAException {
    try {
      if (!isPhysConnClosed() && isUseXA() && this.physicalConn instanceof XAResource)
        ((XAResource)this.physicalConn).commit(paramXid, paramBoolean); 
    } catch (SQLException sQLException) {
      throw new TbXAException(sQLException.getMessage());
    } 
  }
  
  public Array createArrayOf(String paramString, Object[] paramArrayOfObject) throws SQLException {
    return this.physicalConn.createArrayOf(paramString, paramArrayOfObject);
  }
  
  public Blob createBlob() throws SQLException {
    if (isClosed())
      throw TbError.newSQLException(-90603); 
    try {
      return this.physicalConn.createBlob();
    } catch (SQLException sQLException) {
      this.eventHandler.notifyExceptionEvent(sQLException);
      throw sQLException;
    } 
  }
  
  public Clob createClob() throws SQLException {
    if (isClosed())
      throw TbError.newSQLException(-90603); 
    try {
      return this.physicalConn.createClob();
    } catch (SQLException sQLException) {
      this.eventHandler.notifyExceptionEvent(sQLException);
      throw sQLException;
    } 
  }
  
  public NClob createNClob() throws SQLException {
    if (isClosed())
      throw TbError.newSQLException(-90603); 
    try {
      return this.physicalConn.createNClob();
    } catch (SQLException sQLException) {
      this.eventHandler.notifyExceptionEvent(sQLException);
      throw sQLException;
    } 
  }
  
  public SQLXML createSQLXML() throws SQLException {
    if (isClosed())
      throw TbError.newSQLException(-90603); 
    try {
      return this.physicalConn.createSQLXML();
    } catch (SQLException sQLException) {
      this.eventHandler.notifyExceptionEvent(sQLException);
      throw sQLException;
    } 
  }
  
  public Statement createStatement() throws SQLException {
    if (isClosed())
      throw TbError.newSQLException(-90603); 
    try {
      return this.physicalConn.createStatement();
    } catch (SQLException sQLException) {
      this.eventHandler.notifyExceptionEvent(sQLException);
      throw sQLException;
    } 
  }
  
  public Statement createStatement(int paramInt1, int paramInt2) throws SQLException {
    if (isClosed())
      throw TbError.newSQLException(-90603); 
    try {
      return this.physicalConn.createStatement(paramInt1, paramInt2);
    } catch (SQLException sQLException) {
      this.eventHandler.notifyExceptionEvent(sQLException);
      throw sQLException;
    } 
  }
  
  public Statement createStatement(int paramInt1, int paramInt2, int paramInt3) throws SQLException {
    throw TbError.newSQLException(-90201);
  }
  
  public Struct createStruct(String paramString, Object[] paramArrayOfObject) throws SQLException {
    return this.physicalConn.createStruct(paramString, paramArrayOfObject);
  }
  
  public void end(Xid paramXid, int paramInt) throws XAException {
    try {
      if (!isPhysConnClosed() && isUseXA() && this.physicalConn instanceof XAResource)
        ((XAResource)this.physicalConn).end(paramXid, paramInt); 
    } catch (SQLException sQLException) {
      throw new TbXAException(sQLException.getMessage());
    } 
  }
  
  public void forget(Xid paramXid) throws TbXAException {}
  
  public boolean getAutoCommit() throws SQLException {
    if (isClosed())
      throw TbError.newSQLException(-90603); 
    try {
      return this.physicalConn.getAutoCommit();
    } catch (SQLException sQLException) {
      this.eventHandler.notifyExceptionEvent(sQLException);
      throw sQLException;
    } 
  }
  
  public String getCatalog() throws SQLException {
    if (isClosed())
      throw TbError.newSQLException(-90603); 
    try {
      return this.physicalConn.getCatalog();
    } catch (SQLException sQLException) {
      this.eventHandler.notifyExceptionEvent(sQLException);
      throw sQLException;
    } 
  }
  
  public Properties getClientInfo() throws SQLException {
    if (isClosed())
      throw TbError.newSQLException(-90603); 
    try {
      return this.physicalConn.getClientInfo();
    } catch (SQLException sQLException) {
      this.eventHandler.notifyExceptionEvent(sQLException);
      throw sQLException;
    } 
  }
  
  public String getClientInfo(String paramString) throws SQLException {
    if (isClosed())
      throw TbError.newSQLException(-90603); 
    try {
      return this.physicalConn.getClientInfo(paramString);
    } catch (SQLException sQLException) {
      this.eventHandler.notifyExceptionEvent(sQLException);
      throw sQLException;
    } 
  }
  
  public TbConnectionEventHandler getEventHandler() {
    return this.eventHandler;
  }
  
  public int getHoldability() throws SQLException {
    return 1;
  }
  
  public TbSQLInfo getLastExecutedSqlinfo() throws SQLException {
    if (isClosed())
      throw TbError.newSQLException(-90603); 
    try {
      return this.physicalConn.getLastExecutedSqlinfo();
    } catch (SQLException sQLException) {
      this.eventHandler.notifyExceptionEvent(sQLException);
      throw sQLException;
    } 
  }
  
  public TbSQLInfo2 getLastExecutedSqlinfo2() throws SQLException {
    if (isClosed())
      throw TbError.newSQLException(-90603); 
    try {
      return this.physicalConn.getLastExecutedSqlinfo2();
    } catch (SQLException sQLException) {
      this.eventHandler.notifyExceptionEvent(sQLException);
      throw sQLException;
    } 
  }
  
  public DatabaseMetaData getMetaData() throws SQLException {
    if (isClosed())
      throw TbError.newSQLException(-90603); 
    try {
      return this.physicalConn.getMetaData();
    } catch (SQLException sQLException) {
      this.eventHandler.notifyExceptionEvent(sQLException);
      throw sQLException;
    } 
  }
  
  TbConnection getPhysicalConnection() {
    return this.physicalConn;
  }
  
  public int getTransactionIsolation() throws SQLException {
    if (isClosed())
      throw TbError.newSQLException(-90603); 
    try {
      return this.physicalConn.getTransactionIsolation();
    } catch (SQLException sQLException) {
      this.eventHandler.notifyExceptionEvent(sQLException);
      throw sQLException;
    } 
  }
  
  public int getTxnMode() throws SQLException {
    if (isClosed())
      throw TbError.newSQLException(-90603); 
    return this.physicalConn.getTxnMode();
  }
  
  public Map getTypeMap() throws SQLException {
    if (isClosed())
      throw TbError.newSQLException(-90603); 
    try {
      return this.physicalConn.getTypeMap();
    } catch (SQLException sQLException) {
      this.eventHandler.notifyExceptionEvent(sQLException);
      throw sQLException;
    } 
  }
  
  public SQLWarning getWarnings() throws SQLException {
    if (isClosed())
      throw TbError.newSQLException(-90603); 
    try {
      return this.physicalConn.getWarnings();
    } catch (SQLException sQLException) {
      this.eventHandler.notifyExceptionEvent(sQLException);
      throw sQLException;
    } 
  }
  
  public boolean isClosed() {
    return this.closed;
  }
  
  public boolean isPhysConnClosed() throws SQLException {
    return this.physicalConn.isClosed();
  }
  
  public boolean isReadOnly() throws SQLException {
    if (isClosed())
      throw TbError.newSQLException(-90603); 
    try {
      return this.physicalConn.isReadOnly();
    } catch (SQLException sQLException) {
      this.eventHandler.notifyExceptionEvent(sQLException);
      throw sQLException;
    } 
  }
  
  public boolean isUseXA() {
    return this.useXA;
  }
  
  public boolean isValid(int paramInt) throws SQLException {
    if (isClosed())
      throw TbError.newSQLException(-90603); 
    try {
      return this.physicalConn.isValid(paramInt);
    } catch (SQLException sQLException) {
      this.eventHandler.notifyExceptionEvent(sQLException);
      throw sQLException;
    } 
  }
  
  public boolean isWrapperFor(Class<?> paramClass) throws SQLException {
    return paramClass.isInstance(this);
  }
  
  public String nativeSQL(String paramString) throws SQLException {
    if (isClosed())
      throw TbError.newSQLException(-90603); 
    try {
      return this.physicalConn.nativeSQL(paramString);
    } catch (SQLException sQLException) {
      this.eventHandler.notifyExceptionEvent(sQLException);
      throw sQLException;
    } 
  }
  
  public int prepare(Xid paramXid) throws XAException {
    try {
      if (!isPhysConnClosed() && isUseXA() && this.physicalConn instanceof XAResource)
        return ((XAResource)this.physicalConn).prepare(paramXid); 
    } catch (SQLException sQLException) {
      throw new TbXAException("prepare failed " + sQLException.getMessage());
    } 
    throw new TbXAException("prepare failed");
  }
  
  public CallableStatement prepareCall(String paramString) throws SQLException {
    if (isClosed())
      throw TbError.newSQLException(-90603); 
    try {
//      null = null;
      TbCallableStatement tbCallableStatement = (TbCallableStatement)this.physicalConn.prepareCall(paramString);
      return (CallableStatement)new TbLogicalCallableStatement(this, tbCallableStatement);
    } catch (SQLException sQLException) {
      this.eventHandler.notifyExceptionEvent(sQLException);
      throw sQLException;
    } 
  }
  
  public CallableStatement prepareCall(String paramString, int paramInt1, int paramInt2) throws SQLException {
    if (isClosed())
      throw TbError.newSQLException(-90603); 
    try {
//      null = null;
      TbCallableStatement tbCallableStatement = (TbCallableStatement)this.physicalConn.prepareCall(paramString, paramInt1, paramInt2);
      return (CallableStatement)new TbLogicalCallableStatement(this, tbCallableStatement);
    } catch (SQLException sQLException) {
      this.eventHandler.notifyExceptionEvent(sQLException);
      throw sQLException;
    } 
  }
  
  public CallableStatement prepareCall(String paramString, int paramInt1, int paramInt2, int paramInt3) throws SQLException {
    throw TbError.newSQLException(-90201);
  }
  
  public PreparedStatement prepareStatement(String paramString) throws SQLException {
    if (isClosed())
      throw TbError.newSQLException(-90603); 
    try {
//      null = null;
      TbPreparedStatement tbPreparedStatement = (TbPreparedStatement)this.physicalConn.prepareStatement(paramString);
      return (PreparedStatement)new TbLogicalPreparedStatement(this, tbPreparedStatement);
    } catch (SQLException sQLException) {
      this.eventHandler.notifyExceptionEvent(sQLException);
      throw sQLException;
    } 
  }
  
  public PreparedStatement prepareStatement(String paramString, boolean paramBoolean) throws SQLException {
    if (isClosed())
      throw TbError.newSQLException(-90603); 
    try {
//      null = null;
      TbPreparedStatement tbPreparedStatement = (TbPreparedStatement)this.physicalConn.prepareStatement(paramString, paramBoolean);
      return (PreparedStatement)new TbLogicalPreparedStatement(this, tbPreparedStatement);
    } catch (SQLException sQLException) {
      this.eventHandler.notifyExceptionEvent(sQLException);
      throw sQLException;
    } 
  }
  
  public PreparedStatement prepareStatement(String paramString, int paramInt) throws SQLException {
    if (isClosed())
      throw TbError.newSQLException(-90603); 
    try {
//      null = null;
      TbPreparedStatement tbPreparedStatement = (TbPreparedStatement)this.physicalConn.prepareStatement(paramString, paramInt);
      return (PreparedStatement)new TbLogicalPreparedStatement(this, tbPreparedStatement);
    } catch (SQLException sQLException) {
      this.eventHandler.notifyExceptionEvent(sQLException);
      throw sQLException;
    } 
  }
  
  public PreparedStatement prepareStatement(String paramString, int paramInt1, int paramInt2) throws SQLException {
    if (isClosed())
      throw TbError.newSQLException(-90603); 
    try {
//      null = null;
      TbPreparedStatement tbPreparedStatement = (TbPreparedStatement)this.physicalConn.prepareStatement(paramString, paramInt1, paramInt2);
      return (PreparedStatement)new TbLogicalPreparedStatement(this, tbPreparedStatement);
    } catch (SQLException sQLException) {
      this.eventHandler.notifyExceptionEvent(sQLException);
      throw sQLException;
    } 
  }
  
  public PreparedStatement prepareStatement(String paramString, int paramInt1, int paramInt2, int paramInt3) throws SQLException {
    throw TbError.newSQLException(-90201);
  }
  
  public PreparedStatement prepareStatement(String paramString, int[] paramArrayOfint) throws SQLException {
    if (isClosed())
      throw TbError.newSQLException(-90603); 
    try {
//      null = null;
      TbPreparedStatement tbPreparedStatement = (TbPreparedStatement)this.physicalConn.prepareStatement(paramString, paramArrayOfint);
      return (PreparedStatement)new TbLogicalPreparedStatement(this, tbPreparedStatement);
    } catch (SQLException sQLException) {
      this.eventHandler.notifyExceptionEvent(sQLException);
      throw sQLException;
    } 
  }
  
  public PreparedStatement prepareStatement(String paramString, String[] paramArrayOfString) throws SQLException {
    if (isClosed())
      throw TbError.newSQLException(-90603); 
    try {
//      null = null;
      TbPreparedStatement tbPreparedStatement = (TbPreparedStatement)this.physicalConn.prepareStatement(paramString, paramArrayOfString);
      return (PreparedStatement)new TbLogicalPreparedStatement(this, tbPreparedStatement);
    } catch (SQLException sQLException) {
      this.eventHandler.notifyExceptionEvent(sQLException);
      throw sQLException;
    } 
  }
  
  public Xid[] recover(int paramInt) throws XAException {
    try {
      if (!isPhysConnClosed() && isUseXA() && this.physicalConn instanceof XAResource)
        return ((XAResource)this.physicalConn).recover(paramInt); 
    } catch (SQLException sQLException) {
      throw new TbXAException("recover failed " + sQLException.getMessage());
    } 
    throw new TbXAException("recover failed");
  }
  
  public void releaseSavepoint(Savepoint paramSavepoint) throws SQLException {
    throw TbError.newSQLException(-90201);
  }
  
  public void reset() {
    this.eventHandler = null;
    this.physicalConn = null;
  }
  
  public void rollback() throws SQLException {
    if (isClosed())
      throw TbError.newSQLException(-90603); 
    if (isUseXA() && getTxnMode() == 2)
      throw TbError.newSQLException(-90602); 
    try {
      this.physicalConn.rollback();
    } catch (SQLException sQLException) {
      this.eventHandler.notifyExceptionEvent(sQLException);
      throw sQLException;
    } 
  }
  
  public void rollback(Savepoint paramSavepoint) throws SQLException {
    if (isClosed())
      throw TbError.newSQLException(-90603); 
    if (isUseXA() && getTxnMode() == 2)
      throw TbError.newSQLException(-90602); 
    try {
      this.physicalConn.rollback(paramSavepoint);
    } catch (SQLException sQLException) {
      this.eventHandler.notifyExceptionEvent(sQLException);
      throw sQLException;
    } 
  }
  
  public void rollback(Xid paramXid) throws XAException {
    try {
      if (!isPhysConnClosed() && isUseXA() && this.physicalConn instanceof XAResource)
        ((XAResource)this.physicalConn).rollback(paramXid); 
    } catch (SQLException sQLException) {
      throw new TbXAException(sQLException.getMessage());
    } 
  }
  
  public void setAutoCommit(boolean paramBoolean) throws SQLException {
    if (isClosed())
      throw TbError.newSQLException(-90603); 
    if (isUseXA() && getTxnMode() == 2) {
      if (paramBoolean)
        throw TbError.newSQLException(-90602); 
    } else {
      try {
        this.physicalConn.setAutoCommit(paramBoolean);
      } catch (SQLException sQLException) {
        this.eventHandler.notifyExceptionEvent(sQLException);
        throw sQLException;
      } 
    } 
  }
  
  public void setCatalog(String paramString) throws SQLException {
    if (isClosed())
      throw TbError.newSQLException(-90603); 
    try {
      this.physicalConn.setCatalog(paramString);
    } catch (SQLException sQLException) {
      this.eventHandler.notifyExceptionEvent(sQLException);
      throw sQLException;
    } 
  }
  
  public void setClientInfo(Properties paramProperties) throws SQLClientInfoException {
    if (isClosed())
      throw new SQLClientInfoException(TbError.getMsg(-90603), null, -90603, null); 
    try {
      this.physicalConn.setClientInfo(paramProperties);
    } catch (SQLClientInfoException sQLClientInfoException) {
      this.eventHandler.notifyExceptionEvent(sQLClientInfoException);
      throw sQLClientInfoException;
    } 
  }
  
  public void setClientInfo(String paramString1, String paramString2) throws SQLClientInfoException {
    if (isClosed())
      throw new SQLClientInfoException(TbError.getMsg(-90603), null, -90603, null); 
    try {
      this.physicalConn.setClientInfo(paramString1, paramString2);
    } catch (SQLClientInfoException sQLClientInfoException) {
      this.eventHandler.notifyExceptionEvent(sQLClientInfoException);
      throw sQLClientInfoException;
    } 
  }
  
  public void setHoldability(int paramInt) throws SQLException {
    throw TbError.newSQLException(-90201);
  }
  
  public void setReadOnly(boolean paramBoolean) throws SQLException {
    if (isClosed())
      throw TbError.newSQLException(-90603); 
    try {
      this.physicalConn.setReadOnly(paramBoolean);
    } catch (SQLException sQLException) {
      this.eventHandler.notifyExceptionEvent(sQLException);
      throw sQLException;
    } 
  }
  
  public Savepoint setSavepoint() throws SQLException {
    if (isClosed())
      throw TbError.newSQLException(-90603); 
    if (isUseXA() && getTxnMode() == 2)
      throw TbError.newSQLException(-90602); 
    return this.physicalConn.setSavepoint();
  }
  
  public Savepoint setSavepoint(String paramString) throws SQLException {
    if (isClosed())
      throw TbError.newSQLException(-90603); 
    if (isUseXA() && getTxnMode() == 2)
      throw TbError.newSQLException(-90602); 
    return this.physicalConn.setSavepoint(paramString);
  }
  
  public void setTransactionIsolation(int paramInt) throws SQLException {
    if (isClosed())
      throw TbError.newSQLException(-90603); 
    try {
      this.physicalConn.setTransactionIsolation(paramInt);
    } catch (SQLException sQLException) {
      this.eventHandler.notifyExceptionEvent(sQLException);
      throw sQLException;
    } 
  }
  
  public void setTxnMode(int paramInt) {
    this.physicalConn.setTxnMode(paramInt);
  }
  
  public void setTypeMap(Map paramMap) throws SQLException {
    if (isClosed())
      throw TbError.newSQLException(-90603); 
    try {
      this.physicalConn.setTypeMap(paramMap);
    } catch (SQLException sQLException) {
      this.eventHandler.notifyExceptionEvent(sQLException);
      throw sQLException;
    } 
  }
  
  public void start(Xid paramXid, int paramInt) throws XAException {
    try {
      if (!isPhysConnClosed() && isUseXA() && this.physicalConn instanceof XAResource)
        ((XAResource)this.physicalConn).start(paramXid, paramInt); 
    } catch (SQLException sQLException) {
      throw new TbXAException(sQLException.getMessage());
    } 
  }
  
  public <T> T unwrap(Class<T> paramClass) throws SQLException {
    try {
      return paramClass.cast(this);
    } catch (ClassCastException classCastException) {
      throw TbError.newSQLException(-90657);
    } 
  }
  
  public void addStatementEventListener(StatementEventListener paramStatementEventListener) {
    if (this.physicalConn.isPooledConnection())
      this.listenerMap.put(paramStatementEventListener, paramStatementEventListener); 
  }
  
  public void removeaStatementEventListener(StatementEventListener paramStatementEventListener) {
    if (this.physicalConn.isPooledConnection())
      this.listenerMap.remove(paramStatementEventListener); 
  }
  
  public HashMap<StatementEventListener, StatementEventListener> getStatementEventListeners() {
    return this.listenerMap;
  }
  
  public void addWarning(SQLWarning paramSQLWarning) {
    this.physicalConn.addWarning(paramSQLWarning);
  }
  
  public void closeCursor(TbResultSet paramTbResultSet, int paramInt) throws SQLException {
    if (isClosed())
      throw TbError.newSQLException(-90603); 
    this.physicalConn.closeCursor(paramTbResultSet, paramInt);
  }
  
  public TbBlob createTbBlob() throws SQLException {
    if (isClosed())
      throw TbError.newSQLException(-90603); 
    return this.physicalConn.createTbBlob();
  }
  
  public TbClob createTbClob() throws SQLException {
    if (isClosed())
      throw TbError.newSQLException(-90603); 
    return this.physicalConn.createTbClob();
  }
  
  public TbDirPathStream createDirPathStream(TbDirPathMetaData paramTbDirPathMetaData) throws SQLException {
    throw TbError.newSQLException(-90201);
  }
  
  public TbNClob createTbNClob() throws SQLException {
    if (isClosed())
      throw TbError.newSQLException(-90603); 
    return this.physicalConn.createTbNClob();
  }
  
  public String getNlsDate() {
    return this.physicalConn.getNlsDate();
  }
  
  public String getNlsTimestamp() {
    return this.physicalConn.getNlsTimestamp();
  }
  
  public int getSerialNo() {
    return this.physicalConn.getSerialNo();
  }
  
  public int getServerCharSet() {
    return this.physicalConn.getServerCharSet();
  }
  
  public ServerInfo getServerInfo() {
    return this.physicalConn.getServerInfo();
  }
  
  public int getServerNCharSet() {
    return this.physicalConn.getServerNCharSet();
  }
  
  public int getSessionId() {
    return this.physicalConn.getSessionId();
  }
  
  public boolean isPooledConnection() {
    return false;
  }
  
  public boolean isSessionClosed() {
    return this.physicalConn.isSessionClosed();
  }
  
  public void resetSession() throws SQLException {
    if (isClosed())
      throw TbError.newSQLException(-90603); 
    this.physicalConn.resetSession();
  }
}


/* Location:              C:\TmaxData\tibero6\client\lib\jar\tibero6-jdbc.jar!\com\tmax\tibero\jdbc\ext\TbLogicalConnection.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */