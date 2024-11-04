package com.tmax.tibero.jdbc.ext;

import com.tmax.tibero.jdbc.driver.TbConnection;
import com.tmax.tibero.jdbc.err.TbError;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import javax.sql.ConnectionEvent;
import javax.sql.ConnectionEventListener;
import javax.sql.PooledConnection;
import javax.sql.StatementEventListener;

public class TbPooledConnection implements PooledConnection, TbConnectionEventHandler {
  protected HashMap eventMap = null;
  
  private TbLogicalConnection logicalConn = null;
  
  private TbConnection physicalConn = null;
  
  public TbPooledConnection(TbConnection paramTbConnection) {
    this.eventMap = new HashMap<Object, Object>();
    this.physicalConn = paramTbConnection;
  }
  
  public final void addConnectionEventListener(ConnectionEventListener paramConnectionEventListener) {
    if (paramConnectionEventListener != null && this.physicalConn != null && this.eventMap != null)
      this.eventMap.put(paramConnectionEventListener, paramConnectionEventListener); 
  }
  
  public final void close() throws SQLException {
    try {
      this.physicalConn.close();
    } finally {
      reset();
    } 
  }
  
  public final Connection getConnection() throws SQLException {
    if (this.physicalConn == null) {
      SQLException sQLException = TbError.newSQLException(-590709);
      notifyExceptionEvent(sQLException);
      return null;
    } 
    try {
      if (this.logicalConn != null && !this.logicalConn.isClosed())
        this.logicalConn.close(); 
      this.logicalConn = getNewLogicalConnection();
    } catch (SQLException sQLException) {
      notifyExceptionEvent(sQLException);
      throw sQLException;
    } 
    return (Connection)this.logicalConn;
  }
  
  protected TbLogicalConnection getNewLogicalConnection() throws SQLException {
    return new TbLogicalConnection(this, getPhysicalConnection(), false);
  }
  
  final TbConnection getPhysicalConnection() {
    return this.physicalConn;
  }
  
  public final void notifyClosedEvent() {
    if (this.physicalConn != null && this.eventMap != null) {
      Iterator iterator = this.eventMap.keySet().iterator();
      while (iterator.hasNext()) {
        ConnectionEventListener connectionEventListener = (ConnectionEventListener)this.eventMap.get(iterator.next());
        connectionEventListener.connectionClosed(new ConnectionEvent(this));
      } 
    } 
  }
  
  public final void notifyExceptionEvent(SQLException paramSQLException) {
    if (this.physicalConn != null && this.eventMap != null) {
      Iterator iterator = this.eventMap.keySet().iterator();
      while (iterator.hasNext()) {
        ConnectionEventListener connectionEventListener = (ConnectionEventListener)this.eventMap.get(iterator.next());
        connectionEventListener.connectionErrorOccurred(new ConnectionEvent(this, paramSQLException));
      } 
    } 
  }
  
  public final void removeConnectionEventListener(ConnectionEventListener paramConnectionEventListener) {
    if (paramConnectionEventListener != null && this.physicalConn != null && this.eventMap != null)
      this.eventMap.remove(paramConnectionEventListener); 
  }
  
  public final void reset() {
    this.eventMap = null;
    if (this.logicalConn != null)
      try {
        this.logicalConn.close();
      } catch (Exception exception) {} 
    if (this.physicalConn != null)
      try {
        this.physicalConn.close();
      } catch (Exception exception) {} 
  }
  
  public void addStatementEventListener(StatementEventListener paramStatementEventListener) {
    if (paramStatementEventListener != null && this.logicalConn != null)
      this.logicalConn.addStatementEventListener(paramStatementEventListener); 
  }
  
  public void removeStatementEventListener(StatementEventListener paramStatementEventListener) {
    if (paramStatementEventListener != null && this.logicalConn != null)
      this.logicalConn.removeaStatementEventListener(paramStatementEventListener); 
  }
}


/* Location:              C:\TmaxData\tibero6\client\lib\jar\tibero6-jdbc.jar!\com\tmax\tibero\jdbc\ext\TbPooledConnection.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */