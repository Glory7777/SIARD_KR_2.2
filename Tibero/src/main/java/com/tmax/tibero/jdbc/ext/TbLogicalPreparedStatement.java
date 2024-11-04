package com.tmax.tibero.jdbc.ext;

import com.tmax.tibero.jdbc.TbPreparedStatement;
import com.tmax.tibero.jdbc.data.ParamContainer;
import com.tmax.tibero.jdbc.data.TbDate;
import com.tmax.tibero.jdbc.data.TbRAW;
import com.tmax.tibero.jdbc.data.TbTimestamp;
import com.tmax.tibero.jdbc.err.TbError;
import com.tmax.tibero.jdbc.msg.TbPivotInfo;
import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.NClob;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;
import javax.sql.PooledConnection;
import javax.sql.StatementEvent;
import javax.sql.StatementEventListener;

public class TbLogicalPreparedStatement extends TbPreparedStatement implements TbStatementEventHandler {
  private TbLogicalConnection logicalConn = null;
  
  private boolean closed = true;
  
  protected TbPreparedStatement physicalStmt = null;
  
  protected HashMap<StatementEventListener, StatementEventListener> stmtEventMap = null;
  
  public TbLogicalPreparedStatement(TbLogicalConnection paramTbLogicalConnection, TbPreparedStatement paramTbPreparedStatement) throws SQLException {
    this.logicalConn = paramTbLogicalConnection;
    this.stmtEventMap = this.logicalConn.getStatementEventListeners();
    this.physicalStmt = paramTbPreparedStatement;
    if (paramTbPreparedStatement.isClosed()) {
      this.closed = true;
      throw TbError.newSQLException(-90658);
    } 
    this.closed = false;
  }
  
  public void addBatch() throws SQLException {
    if (isClosed())
      throw TbError.newSQLException(-90659); 
    try {
      this.physicalStmt.addBatch();
    } catch (SQLException sQLException) {
      notifyExceptionEvent((PreparedStatement)this.physicalStmt, sQLException);
      throw sQLException;
    } 
  }
  
  public void addBatch(String paramString) throws SQLException {
    if (isClosed())
      throw TbError.newSQLException(-90659); 
    try {
      this.physicalStmt.addBatch(paramString);
    } catch (SQLException sQLException) {
      notifyExceptionEvent((PreparedStatement)this.physicalStmt, sQLException);
      throw sQLException;
    } 
  }
  
  public void addPivotData(byte[] paramArrayOfbyte) {
    if (this.physicalStmt != null)
      this.physicalStmt.addPivotData(paramArrayOfbyte); 
  }
  
  public void cancel() throws SQLException {
    if (isClosed())
      throw TbError.newSQLException(-90659); 
    try {
      this.physicalStmt.cancel();
    } catch (SQLException sQLException) {
      notifyExceptionEvent((PreparedStatement)this.physicalStmt, sQLException);
      throw sQLException;
    } 
  }
  
  public void clearBatch() throws SQLException {
    if (isClosed())
      throw TbError.newSQLException(-90659); 
    this.physicalStmt.clearBatch();
  }
  
  public void clearParameters() throws SQLException {
    if (isClosed())
      throw TbError.newSQLException(-90659); 
    try {
      this.physicalStmt.clearParameters();
    } catch (SQLException sQLException) {
      notifyExceptionEvent((PreparedStatement)this.physicalStmt, sQLException);
      throw sQLException;
    } 
  }
  
  public void clearWarnings() throws SQLException {
    if (isClosed())
      throw TbError.newSQLException(-90659); 
    try {
      this.physicalStmt.clearWarnings();
    } catch (SQLException sQLException) {
      notifyExceptionEvent((PreparedStatement)this.physicalStmt, sQLException);
      throw sQLException;
    } 
  }
  
  public void close() throws SQLException {
    if (isClosed())
      return; 
    this.closed = true;
    this.physicalStmt.resetForCache();
    notifyClosedEvent();
  }
  
  public void closeInternal() throws SQLException {
    if (this.physicalStmt != null)
      this.physicalStmt.closeInternal(); 
  }
  
  public boolean execute() throws SQLException {
    if (isClosed())
      throw TbError.newSQLException(-90659); 
    try {
      return this.physicalStmt.execute();
    } catch (SQLException sQLException) {
      notifyExceptionEvent((PreparedStatement)this.physicalStmt, sQLException);
      throw sQLException;
    } 
  }
  
  public boolean execute(String paramString) throws SQLException {
    if (isClosed())
      throw TbError.newSQLException(-90659); 
    try {
      return this.physicalStmt.execute(paramString);
    } catch (SQLException sQLException) {
      notifyExceptionEvent((PreparedStatement)this.physicalStmt, sQLException);
      throw sQLException;
    } 
  }
  
  public boolean execute(String paramString, int paramInt) throws SQLException {
    if (isClosed())
      throw TbError.newSQLException(-90659); 
    try {
      return this.physicalStmt.execute(paramString, paramInt);
    } catch (SQLException sQLException) {
      notifyExceptionEvent((PreparedStatement)this.physicalStmt, sQLException);
      throw sQLException;
    } 
  }
  
  public boolean execute(String paramString, int[] paramArrayOfint) throws SQLException {
    if (isClosed())
      throw TbError.newSQLException(-90659); 
    try {
      return this.physicalStmt.execute(paramString, paramArrayOfint);
    } catch (SQLException sQLException) {
      notifyExceptionEvent((PreparedStatement)this.physicalStmt, sQLException);
      throw sQLException;
    } 
  }
  
  public boolean execute(String paramString, String[] paramArrayOfString) throws SQLException {
    if (isClosed())
      throw TbError.newSQLException(-90659); 
    try {
      return this.physicalStmt.execute(paramString, paramArrayOfString);
    } catch (SQLException sQLException) {
      notifyExceptionEvent((PreparedStatement)this.physicalStmt, sQLException);
      throw sQLException;
    } 
  }
  
  public int[] executeBatch() throws SQLException {
    if (isClosed())
      throw TbError.newSQLException(-90659); 
    try {
      return this.physicalStmt.executeBatch();
    } catch (SQLException sQLException) {
      notifyExceptionEvent((PreparedStatement)this.physicalStmt, sQLException);
      throw sQLException;
    } 
  }
  
  public ResultSet executeQuery() throws SQLException {
    if (isClosed())
      throw TbError.newSQLException(-90659); 
    try {
      return this.physicalStmt.executeQuery();
    } catch (SQLException sQLException) {
      notifyExceptionEvent((PreparedStatement)this.physicalStmt, sQLException);
      throw sQLException;
    } 
  }
  
  public ResultSet executeQuery(String paramString) throws SQLException {
    if (isClosed())
      throw TbError.newSQLException(-90659); 
    try {
      return this.physicalStmt.executeQuery(paramString);
    } catch (SQLException sQLException) {
      notifyExceptionEvent((PreparedStatement)this.physicalStmt, sQLException);
      throw sQLException;
    } 
  }
  
  public int executeUpdate() throws SQLException {
    if (isClosed())
      throw TbError.newSQLException(-90659); 
    try {
      return this.physicalStmt.executeUpdate();
    } catch (SQLException sQLException) {
      notifyExceptionEvent((PreparedStatement)this.physicalStmt, sQLException);
      throw sQLException;
    } 
  }
  
  public int executeUpdate(String paramString) throws SQLException {
    if (isClosed())
      throw TbError.newSQLException(-90659); 
    try {
      return this.physicalStmt.executeUpdate(paramString);
    } catch (SQLException sQLException) {
      notifyExceptionEvent((PreparedStatement)this.physicalStmt, sQLException);
      throw sQLException;
    } 
  }
  
  public int executeUpdate(String paramString, int paramInt) throws SQLException {
    if (isClosed())
      throw TbError.newSQLException(-90659); 
    try {
      return this.physicalStmt.executeUpdate(paramString, paramInt);
    } catch (SQLException sQLException) {
      notifyExceptionEvent((PreparedStatement)this.physicalStmt, sQLException);
      throw sQLException;
    } 
  }
  
  public int executeUpdate(String paramString, int[] paramArrayOfint) throws SQLException {
    if (isClosed())
      throw TbError.newSQLException(-90659); 
    try {
      return this.physicalStmt.executeUpdate(paramString, paramArrayOfint);
    } catch (SQLException sQLException) {
      notifyExceptionEvent((PreparedStatement)this.physicalStmt, sQLException);
      throw sQLException;
    } 
  }
  
  public int executeUpdate(String paramString, String[] paramArrayOfString) throws SQLException {
    if (isClosed())
      throw TbError.newSQLException(-90659); 
    try {
      return this.physicalStmt.executeUpdate(paramString, paramArrayOfString);
    } catch (SQLException sQLException) {
      notifyExceptionEvent((PreparedStatement)this.physicalStmt, sQLException);
      throw sQLException;
    } 
  }
  
  public Connection getConnection() throws SQLException {
    if (isClosed())
      throw TbError.newSQLException(-90659); 
    return (Connection)this.logicalConn;
  }
  
  public int getFetchDirection() throws SQLException {
    if (isClosed())
      throw TbError.newSQLException(-90659); 
    try {
      return this.physicalStmt.getFetchDirection();
    } catch (SQLException sQLException) {
      notifyExceptionEvent((PreparedStatement)this.physicalStmt, sQLException);
      throw sQLException;
    } 
  }
  
  public int getFetchSize() throws SQLException {
    if (isClosed())
      throw TbError.newSQLException(-90659); 
    try {
      return this.physicalStmt.getFetchDirection();
    } catch (SQLException sQLException) {
      notifyExceptionEvent((PreparedStatement)this.physicalStmt, sQLException);
      throw sQLException;
    } 
  }
  
  public ResultSet getGeneratedKeys() throws SQLException {
    if (isClosed())
      throw TbError.newSQLException(-90659); 
    try {
      return this.physicalStmt.getGeneratedKeys();
    } catch (SQLException sQLException) {
      notifyExceptionEvent((PreparedStatement)this.physicalStmt, sQLException);
      throw sQLException;
    } 
  }
  
  public int getMaxFieldSize() throws SQLException {
    if (isClosed())
      throw TbError.newSQLException(-90659); 
    try {
      return this.physicalStmt.getMaxFieldSize();
    } catch (SQLException sQLException) {
      notifyExceptionEvent((PreparedStatement)this.physicalStmt, sQLException);
      throw sQLException;
    } 
  }
  
  public int getMaxRows() throws SQLException {
    if (isClosed())
      throw TbError.newSQLException(-90659); 
    try {
      return this.physicalStmt.getMaxRows();
    } catch (SQLException sQLException) {
      notifyExceptionEvent((PreparedStatement)this.physicalStmt, sQLException);
      throw sQLException;
    } 
  }
  
  public ResultSetMetaData getMetaData() throws SQLException {
    if (isClosed())
      throw TbError.newSQLException(-90659); 
    try {
      return this.physicalStmt.getMetaData();
    } catch (SQLException sQLException) {
      notifyExceptionEvent((PreparedStatement)this.physicalStmt, sQLException);
      throw sQLException;
    } 
  }
  
  public boolean getMoreResults() throws SQLException {
    if (isClosed())
      throw TbError.newSQLException(-90659); 
    try {
      return this.physicalStmt.getMoreResults();
    } catch (SQLException sQLException) {
      notifyExceptionEvent((PreparedStatement)this.physicalStmt, sQLException);
      throw sQLException;
    } 
  }
  
  public boolean getMoreResults(int paramInt) throws SQLException {
    if (isClosed())
      throw TbError.newSQLException(-90659); 
    try {
      return this.physicalStmt.getMoreResults();
    } catch (SQLException sQLException) {
      notifyExceptionEvent((PreparedStatement)this.physicalStmt, sQLException);
      throw sQLException;
    } 
  }
  
  public String getOriginalSql() {
    return (this.physicalStmt != null) ? this.physicalStmt.getOriginalSql() : null;
  }
  
  public ParamContainer getParamContainer() {
    return (this.physicalStmt != null) ? this.physicalStmt.getParamContainer() : null;
  }
  
  public ParameterMetaData getParameterMetaData() throws SQLException {
    if (isClosed())
      throw TbError.newSQLException(-90659); 
    try {
      return this.physicalStmt.getParameterMetaData();
    } catch (SQLException sQLException) {
      notifyExceptionEvent((PreparedStatement)this.physicalStmt, sQLException);
      throw sQLException;
    } 
  }
  
  public Vector<byte[]> getPivotData() {
    return (this.physicalStmt != null) ? this.physicalStmt.getPivotData() : null;
  }
  
  public TbPivotInfo[] getPivotInfo() {
    return (this.physicalStmt != null) ? this.physicalStmt.getPivotInfo() : null;
  }
  
  public byte[] getPPID() {
    return (this.physicalStmt != null) ? this.physicalStmt.getPPID() : null;
  }
  
  public int getQueryTimeout() throws SQLException {
    if (isClosed())
      throw TbError.newSQLException(-90659); 
    try {
      return this.physicalStmt.getQueryTimeout();
    } catch (SQLException sQLException) {
      notifyExceptionEvent((PreparedStatement)this.physicalStmt, sQLException);
      throw sQLException;
    } 
  }
  
  public ResultSet getResultSet() throws SQLException {
    if (isClosed())
      throw TbError.newSQLException(-90659); 
    try {
      return this.physicalStmt.getResultSet();
    } catch (SQLException sQLException) {
      notifyExceptionEvent((PreparedStatement)this.physicalStmt, sQLException);
      throw sQLException;
    } 
  }
  
  public int getResultSetConcurrency() throws SQLException {
    if (isClosed())
      throw TbError.newSQLException(-90659); 
    return this.physicalStmt.getResultSetConcurrency();
  }
  
  public int getResultSetHoldability() throws SQLException {
    if (isClosed())
      throw TbError.newSQLException(-90659); 
    try {
      return this.physicalStmt.getResultSetHoldability();
    } catch (SQLException sQLException) {
      notifyExceptionEvent((PreparedStatement)this.physicalStmt, sQLException);
      throw sQLException;
    } 
  }
  
  public int getResultSetType() throws SQLException {
    if (isClosed())
      throw TbError.newSQLException(-90659); 
    try {
      return this.physicalStmt.getResultSetType();
    } catch (SQLException sQLException) {
      notifyExceptionEvent((PreparedStatement)this.physicalStmt, sQLException);
      throw sQLException;
    } 
  }
  
  public int getSqlType() {
    return (this.physicalStmt != null) ? this.physicalStmt.getSqlType() : 0;
  }
  
  public int getUpdateCount() throws SQLException {
    if (isClosed())
      throw TbError.newSQLException(-90659); 
    try {
      return this.physicalStmt.getUpdateCount();
    } catch (SQLException sQLException) {
      notifyExceptionEvent((PreparedStatement)this.physicalStmt, sQLException);
      throw sQLException;
    } 
  }
  
  public SQLWarning getWarnings() throws SQLException {
    if (isClosed())
      throw TbError.newSQLException(-90659); 
    try {
      return this.physicalStmt.getWarnings();
    } catch (SQLException sQLException) {
      notifyExceptionEvent((PreparedStatement)this.physicalStmt, sQLException);
      throw sQLException;
    } 
  }
  
  public boolean isClosed() throws SQLException {
    return this.closed;
  }
  
  public boolean isPoolable() throws SQLException {
    if (isClosed())
      throw TbError.newSQLException(-90659); 
    try {
      return this.physicalStmt.isPoolable();
    } catch (SQLException sQLException) {
      notifyExceptionEvent((PreparedStatement)this.physicalStmt, sQLException);
      throw sQLException;
    } 
  }
  
  public boolean isWrapperFor(Class<?> paramClass) throws SQLException {
    return paramClass.isInstance(this);
  }
  
  public void notifyClosedEvent() {
    if (this.physicalStmt != null && this.stmtEventMap != null) {
      Iterator iterator = this.stmtEventMap.keySet().iterator();
      while (iterator.hasNext()) {
        StatementEventListener statementEventListener = this.stmtEventMap.get(iterator.next());
        statementEventListener.statementClosed(new StatementEvent((PooledConnection)this.logicalConn.getEventHandler(), (PreparedStatement)this));
      } 
    } 
  }
  
  public void notifyExceptionEvent(PreparedStatement paramPreparedStatement, Exception paramException) {
    if (this.physicalStmt != null && this.stmtEventMap != null) {
      Iterator iterator = this.stmtEventMap.keySet().iterator();
      while (iterator.hasNext()) {
        StatementEventListener statementEventListener = this.stmtEventMap.get(iterator.next());
        statementEventListener.statementErrorOccurred(new StatementEvent((PooledConnection)this.logicalConn.getEventHandler(), (PreparedStatement)this, (SQLException)paramException));
      } 
    } 
  }
  
  public void resetForCache() {
    if (this.physicalStmt != null)
      this.physicalStmt.resetForCache(); 
  }
  
  public void setArray(int paramInt, Array paramArray) throws SQLException {
    if (isClosed())
      throw TbError.newSQLException(-90659); 
    try {
      this.physicalStmt.setArray(paramInt, paramArray);
    } catch (SQLException sQLException) {
      notifyExceptionEvent((PreparedStatement)this.physicalStmt, sQLException);
      throw sQLException;
    } 
  }
  
  public void setAsciiStream(int paramInt, InputStream paramInputStream) throws SQLException {
    if (isClosed())
      throw TbError.newSQLException(-90659); 
    try {
      this.physicalStmt.setAsciiStream(paramInt, paramInputStream);
    } catch (SQLException sQLException) {
      notifyExceptionEvent((PreparedStatement)this.physicalStmt, sQLException);
      throw sQLException;
    } 
  }
  
  public void setAsciiStream(int paramInt1, InputStream paramInputStream, int paramInt2) throws SQLException {
    if (isClosed())
      throw TbError.newSQLException(-90659); 
    try {
      this.physicalStmt.setAsciiStream(paramInt1, paramInputStream, paramInt2);
    } catch (SQLException sQLException) {
      notifyExceptionEvent((PreparedStatement)this.physicalStmt, sQLException);
      throw sQLException;
    } 
  }
  
  public void setAsciiStream(int paramInt, InputStream paramInputStream, long paramLong) throws SQLException {
    if (isClosed())
      throw TbError.newSQLException(-90659); 
    try {
      this.physicalStmt.setAsciiStream(paramInt, paramInputStream, paramLong);
    } catch (SQLException sQLException) {
      notifyExceptionEvent((PreparedStatement)this.physicalStmt, sQLException);
      throw sQLException;
    } 
  }
  
  public void setBigDecimal(int paramInt, BigDecimal paramBigDecimal) throws SQLException {
    if (isClosed())
      throw TbError.newSQLException(-90659); 
    try {
      this.physicalStmt.setBigDecimal(paramInt, paramBigDecimal);
    } catch (SQLException sQLException) {
      notifyExceptionEvent((PreparedStatement)this.physicalStmt, sQLException);
      throw sQLException;
    } 
  }
  
  public void setBinaryDouble(int paramInt, double paramDouble) throws SQLException {
    if (isClosed())
      throw TbError.newSQLException(-90659); 
    try {
      this.physicalStmt.setBinaryDouble(paramInt, paramDouble);
    } catch (SQLException sQLException) {
      notifyExceptionEvent((PreparedStatement)this.physicalStmt, sQLException);
      throw sQLException;
    } 
  }
  
  public void setBinaryFloat(int paramInt, float paramFloat) throws SQLException {
    if (isClosed())
      throw TbError.newSQLException(-90659); 
    try {
      this.physicalStmt.setBinaryFloat(paramInt, paramFloat);
    } catch (SQLException sQLException) {
      notifyExceptionEvent((PreparedStatement)this.physicalStmt, sQLException);
      throw sQLException;
    } 
  }
  
  public void setBinaryStream(int paramInt, InputStream paramInputStream) throws SQLException {
    if (isClosed())
      throw TbError.newSQLException(-90659); 
    try {
      this.physicalStmt.setBinaryStream(paramInt, paramInputStream);
    } catch (SQLException sQLException) {
      notifyExceptionEvent((PreparedStatement)this.physicalStmt, sQLException);
      throw sQLException;
    } 
  }
  
  public void setBinaryStream(int paramInt1, InputStream paramInputStream, int paramInt2) throws SQLException {
    if (isClosed())
      throw TbError.newSQLException(-90659); 
    try {
      this.physicalStmt.setBinaryStream(paramInt1, paramInputStream, paramInt2);
    } catch (SQLException sQLException) {
      notifyExceptionEvent((PreparedStatement)this.physicalStmt, sQLException);
      throw sQLException;
    } 
  }
  
  public void setBinaryStream(int paramInt, InputStream paramInputStream, long paramLong) throws SQLException {
    if (isClosed())
      throw TbError.newSQLException(-90659); 
    try {
      this.physicalStmt.setBinaryStream(paramInt, paramInputStream, paramLong);
    } catch (SQLException sQLException) {
      notifyExceptionEvent((PreparedStatement)this.physicalStmt, sQLException);
      throw sQLException;
    } 
  }
  
  public void setBlob(int paramInt, Blob paramBlob) throws SQLException {
    if (isClosed())
      throw TbError.newSQLException(-90659); 
    try {
      this.physicalStmt.setBlob(paramInt, paramBlob);
    } catch (SQLException sQLException) {
      notifyExceptionEvent((PreparedStatement)this.physicalStmt, sQLException);
      throw sQLException;
    } 
  }
  
  public void setBlob(int paramInt, InputStream paramInputStream) throws SQLException {
    if (isClosed())
      throw TbError.newSQLException(-90659); 
    try {
      this.physicalStmt.setBlob(paramInt, paramInputStream);
    } catch (SQLException sQLException) {
      notifyExceptionEvent((PreparedStatement)this.physicalStmt, sQLException);
      throw sQLException;
    } 
  }
  
  public void setBlob(int paramInt, InputStream paramInputStream, long paramLong) throws SQLException {
    if (isClosed())
      throw TbError.newSQLException(-90659); 
    try {
      this.physicalStmt.setBlob(paramInt, paramInputStream, paramLong);
    } catch (SQLException sQLException) {
      notifyExceptionEvent((PreparedStatement)this.physicalStmt, sQLException);
      throw sQLException;
    } 
  }
  
  public void setBoolean(int paramInt, boolean paramBoolean) throws SQLException {
    if (isClosed())
      throw TbError.newSQLException(-90659); 
    try {
      this.physicalStmt.setBoolean(paramInt, paramBoolean);
    } catch (SQLException sQLException) {
      notifyExceptionEvent((PreparedStatement)this.physicalStmt, sQLException);
      throw sQLException;
    } 
  }
  
  public void setByte(int paramInt, byte paramByte) throws SQLException {
    if (isClosed())
      throw TbError.newSQLException(-90659); 
    try {
      this.physicalStmt.setByte(paramInt, paramByte);
    } catch (SQLException sQLException) {
      notifyExceptionEvent((PreparedStatement)this.physicalStmt, sQLException);
      throw sQLException;
    } 
  }
  
  public void setBytes(int paramInt, byte[] paramArrayOfbyte) throws SQLException {
    if (isClosed())
      throw TbError.newSQLException(-90659); 
    try {
      this.physicalStmt.setBytes(paramInt, paramArrayOfbyte);
    } catch (SQLException sQLException) {
      notifyExceptionEvent((PreparedStatement)this.physicalStmt, sQLException);
      throw sQLException;
    } 
  }
  
  public void setBytes(int paramInt1, int paramInt2, byte[] paramArrayOfbyte) throws SQLException {
    if (isClosed())
      throw TbError.newSQLException(-90659); 
    try {
      this.physicalStmt.setBytes(paramInt1, paramInt2, paramArrayOfbyte);
    } catch (SQLException sQLException) {
      notifyExceptionEvent((PreparedStatement)this.physicalStmt, sQLException);
      throw sQLException;
    } 
  }
  
  public void setRAW(int paramInt, TbRAW paramTbRAW) throws SQLException {
    setBytes(paramInt, paramTbRAW.getBytes());
  }
  
  public void setRAW(int paramInt1, int paramInt2, TbRAW paramTbRAW) throws SQLException {
    setBytes(paramInt1, paramInt2, paramTbRAW.getBytes());
  }
  
  public void setCharacterStream(int paramInt, Reader paramReader) throws SQLException {
    if (isClosed())
      throw TbError.newSQLException(-90659); 
    try {
      this.physicalStmt.setCharacterStream(paramInt, paramReader);
    } catch (SQLException sQLException) {
      notifyExceptionEvent((PreparedStatement)this.physicalStmt, sQLException);
      throw sQLException;
    } 
  }
  
  public void setCharacterStream(int paramInt1, Reader paramReader, int paramInt2) throws SQLException {
    if (isClosed())
      throw TbError.newSQLException(-90659); 
    try {
      this.physicalStmt.setCharacterStream(paramInt1, paramReader, paramInt2);
    } catch (SQLException sQLException) {
      notifyExceptionEvent((PreparedStatement)this.physicalStmt, sQLException);
      throw sQLException;
    } 
  }
  
  public void setCharacterStream(int paramInt, Reader paramReader, long paramLong) throws SQLException {
    if (isClosed())
      throw TbError.newSQLException(-90659); 
    try {
      this.physicalStmt.setCharacterStream(paramInt, paramReader, paramLong);
    } catch (SQLException sQLException) {
      notifyExceptionEvent((PreparedStatement)this.physicalStmt, sQLException);
      throw sQLException;
    } 
  }
  
  public void setClob(int paramInt, Clob paramClob) throws SQLException {
    if (isClosed())
      throw TbError.newSQLException(-90659); 
    try {
      this.physicalStmt.setClob(paramInt, paramClob);
    } catch (SQLException sQLException) {
      notifyExceptionEvent((PreparedStatement)this.physicalStmt, sQLException);
      throw sQLException;
    } 
  }
  
  public void setClob(int paramInt, Reader paramReader) throws SQLException {
    if (isClosed())
      throw TbError.newSQLException(-90659); 
    try {
      this.physicalStmt.setClob(paramInt, paramReader);
    } catch (SQLException sQLException) {
      notifyExceptionEvent((PreparedStatement)this.physicalStmt, sQLException);
      throw sQLException;
    } 
  }
  
  public void setClob(int paramInt, Reader paramReader, long paramLong) throws SQLException {
    if (isClosed())
      throw TbError.newSQLException(-90659); 
    try {
      this.physicalStmt.setClob(paramInt, paramReader, paramLong);
    } catch (SQLException sQLException) {
      notifyExceptionEvent((PreparedStatement)this.physicalStmt, sQLException);
      throw sQLException;
    } 
  }
  
  public void setCursorName(String paramString) throws SQLException {
    if (isClosed())
      throw TbError.newSQLException(-90659); 
    try {
      this.physicalStmt.setCursorName(paramString);
    } catch (SQLException sQLException) {
      notifyExceptionEvent((PreparedStatement)this.physicalStmt, sQLException);
      throw sQLException;
    } 
  }
  
  public void setDate(int paramInt, Date paramDate) throws SQLException {
    if (isClosed())
      throw TbError.newSQLException(-90659); 
    try {
      this.physicalStmt.setDate(paramInt, paramDate);
    } catch (SQLException sQLException) {
      notifyExceptionEvent((PreparedStatement)this.physicalStmt, sQLException);
      throw sQLException;
    } 
  }
  
  public void setDate(int paramInt, Date paramDate, Calendar paramCalendar) throws SQLException {
    if (isClosed())
      throw TbError.newSQLException(-90659); 
    try {
      this.physicalStmt.setDate(paramInt, paramDate, paramCalendar);
    } catch (SQLException sQLException) {
      notifyExceptionEvent((PreparedStatement)this.physicalStmt, sQLException);
      throw sQLException;
    } 
  }
  
  public void setDouble(int paramInt, double paramDouble) throws SQLException {
    if (isClosed())
      throw TbError.newSQLException(-90659); 
    try {
      this.physicalStmt.setDouble(paramInt, paramDouble);
    } catch (SQLException sQLException) {
      notifyExceptionEvent((PreparedStatement)this.physicalStmt, sQLException);
      throw sQLException;
    } 
  }
  
  public void setEscapeProcessing(boolean paramBoolean) throws SQLException {
    if (isClosed())
      throw TbError.newSQLException(-90659); 
    try {
      this.physicalStmt.setEscapeProcessing(paramBoolean);
    } catch (SQLException sQLException) {
      notifyExceptionEvent((PreparedStatement)this.physicalStmt, sQLException);
      throw sQLException;
    } 
  }
  
  public void setFetchDirection(int paramInt) throws SQLException {
    if (isClosed())
      throw TbError.newSQLException(-90659); 
    try {
      this.physicalStmt.setFetchDirection(paramInt);
    } catch (SQLException sQLException) {
      notifyExceptionEvent((PreparedStatement)this.physicalStmt, sQLException);
      throw sQLException;
    } 
  }
  
  public void setFetchSize(int paramInt) throws SQLException {
    if (isClosed())
      throw TbError.newSQLException(-90659); 
    try {
      this.physicalStmt.setFetchSize(paramInt);
    } catch (SQLException sQLException) {
      notifyExceptionEvent((PreparedStatement)this.physicalStmt, sQLException);
      throw sQLException;
    } 
  }
  
  public void setFixedCHAR(int paramInt, String paramString) throws SQLException {
    if (isClosed())
      throw TbError.newSQLException(-90659); 
    try {
      this.physicalStmt.setFixedCHAR(paramInt, paramString);
    } catch (SQLException sQLException) {
      notifyExceptionEvent((PreparedStatement)this.physicalStmt, sQLException);
      throw sQLException;
    } 
  }
  
  public void setFloat(int paramInt, float paramFloat) throws SQLException {
    if (isClosed())
      throw TbError.newSQLException(-90659); 
    try {
      this.physicalStmt.setFloat(paramInt, paramFloat);
    } catch (SQLException sQLException) {
      notifyExceptionEvent((PreparedStatement)this.physicalStmt, sQLException);
      throw sQLException;
    } 
  }
  
  public void setInt(int paramInt1, int paramInt2) throws SQLException {
    if (isClosed())
      throw TbError.newSQLException(-90659); 
    try {
      this.physicalStmt.setInt(paramInt1, paramInt2);
    } catch (SQLException sQLException) {
      notifyExceptionEvent((PreparedStatement)this.physicalStmt, sQLException);
      throw sQLException;
    } 
  }
  
  public void setLong(int paramInt, long paramLong) throws SQLException {
    if (isClosed())
      throw TbError.newSQLException(-90659); 
    try {
      this.physicalStmt.setLong(paramInt, paramLong);
    } catch (SQLException sQLException) {
      notifyExceptionEvent((PreparedStatement)this.physicalStmt, sQLException);
      throw sQLException;
    } 
  }
  
  public void setMaxFieldSize(int paramInt) throws SQLException {
    if (isClosed())
      throw TbError.newSQLException(-90659); 
    try {
      this.physicalStmt.setMaxFieldSize(paramInt);
    } catch (SQLException sQLException) {
      notifyExceptionEvent((PreparedStatement)this.physicalStmt, sQLException);
      throw sQLException;
    } 
  }
  
  public void setMaxRows(int paramInt) throws SQLException {
    if (isClosed())
      throw TbError.newSQLException(-90659); 
    try {
      this.physicalStmt.setMaxRows(paramInt);
    } catch (SQLException sQLException) {
      notifyExceptionEvent((PreparedStatement)this.physicalStmt, sQLException);
      throw sQLException;
    } 
  }
  
  public void setNCharacterStream(int paramInt, Reader paramReader) throws SQLException {
    if (isClosed())
      throw TbError.newSQLException(-90659); 
    try {
      this.physicalStmt.setNCharacterStream(paramInt, paramReader);
    } catch (SQLException sQLException) {
      notifyExceptionEvent((PreparedStatement)this.physicalStmt, sQLException);
      throw sQLException;
    } 
  }
  
  public void setNCharacterStream(int paramInt, Reader paramReader, long paramLong) throws SQLException {
    if (isClosed())
      throw TbError.newSQLException(-90659); 
    try {
      this.physicalStmt.setNCharacterStream(paramInt, paramReader, paramLong);
    } catch (SQLException sQLException) {
      notifyExceptionEvent((PreparedStatement)this.physicalStmt, sQLException);
      throw sQLException;
    } 
  }
  
  public void setNClob(int paramInt, Clob paramClob) throws SQLException {
    if (isClosed())
      throw TbError.newSQLException(-90659); 
    try {
      this.physicalStmt.setNClob(paramInt, paramClob);
    } catch (SQLException sQLException) {
      notifyExceptionEvent((PreparedStatement)this.physicalStmt, sQLException);
      throw sQLException;
    } 
  }
  
  public void setNClob(int paramInt, NClob paramNClob) throws SQLException {
    if (isClosed())
      throw TbError.newSQLException(-90659); 
    try {
      this.physicalStmt.setNClob(paramInt, paramNClob);
    } catch (SQLException sQLException) {
      notifyExceptionEvent((PreparedStatement)this.physicalStmt, sQLException);
      throw sQLException;
    } 
  }
  
  public void setNClob(int paramInt, Reader paramReader) throws SQLException {
    if (isClosed())
      throw TbError.newSQLException(-90659); 
    try {
      this.physicalStmt.setNClob(paramInt, paramReader);
    } catch (SQLException sQLException) {
      notifyExceptionEvent((PreparedStatement)this.physicalStmt, sQLException);
      throw sQLException;
    } 
  }
  
  public void setNClob(int paramInt, Reader paramReader, long paramLong) throws SQLException {
    if (isClosed())
      throw TbError.newSQLException(-90659); 
    try {
      this.physicalStmt.setNClob(paramInt, paramReader, paramLong);
    } catch (SQLException sQLException) {
      notifyExceptionEvent((PreparedStatement)this.physicalStmt, sQLException);
      throw sQLException;
    } 
  }
  
  public void setNString(int paramInt, String paramString) throws SQLException {
    if (isClosed())
      throw TbError.newSQLException(-90659); 
    try {
      this.physicalStmt.setNString(paramInt, paramString);
    } catch (SQLException sQLException) {
      notifyExceptionEvent((PreparedStatement)this.physicalStmt, sQLException);
      throw sQLException;
    } 
  }
  
  public void setNull(int paramInt1, int paramInt2) throws SQLException {
    if (isClosed())
      throw TbError.newSQLException(-90659); 
    try {
      this.physicalStmt.setNull(paramInt1, paramInt2);
    } catch (SQLException sQLException) {
      notifyExceptionEvent((PreparedStatement)this.physicalStmt, sQLException);
      throw sQLException;
    } 
  }
  
  public void setNull(int paramInt1, int paramInt2, String paramString) throws SQLException {
    if (isClosed())
      throw TbError.newSQLException(-90659); 
    try {
      this.physicalStmt.setNull(paramInt1, paramInt2, paramString);
    } catch (SQLException sQLException) {
      notifyExceptionEvent((PreparedStatement)this.physicalStmt, sQLException);
      throw sQLException;
    } 
  }
  
  public void setObject(int paramInt, Object paramObject) throws SQLException {
    if (isClosed())
      throw TbError.newSQLException(-90659); 
    try {
      this.physicalStmt.setObject(paramInt, paramObject);
    } catch (SQLException sQLException) {
      notifyExceptionEvent((PreparedStatement)this.physicalStmt, sQLException);
      throw sQLException;
    } 
  }
  
  public void setObject(int paramInt1, Object paramObject, int paramInt2) throws SQLException {
    if (isClosed())
      throw TbError.newSQLException(-90659); 
    try {
      this.physicalStmt.setObject(paramInt1, paramObject, paramInt2);
    } catch (SQLException sQLException) {
      notifyExceptionEvent((PreparedStatement)this.physicalStmt, sQLException);
      throw sQLException;
    } 
  }
  
  public void setObject(int paramInt1, Object paramObject, int paramInt2, int paramInt3) throws SQLException {
    if (isClosed())
      throw TbError.newSQLException(-90659); 
    try {
      this.physicalStmt.setObject(paramInt1, paramObject, paramInt2, paramInt3);
    } catch (SQLException sQLException) {
      notifyExceptionEvent((PreparedStatement)this.physicalStmt, sQLException);
      throw sQLException;
    } 
  }
  
  public void setPivotInfo(TbPivotInfo[] paramArrayOfTbPivotInfo) {
    if (this.physicalStmt != null)
      this.physicalStmt.setPivotInfo(paramArrayOfTbPivotInfo); 
  }
  
  public void setPoolable(boolean paramBoolean) throws SQLException {
    if (isClosed())
      throw TbError.newSQLException(-90659); 
    try {
      this.physicalStmt.setPoolable(paramBoolean);
    } catch (SQLException sQLException) {
      notifyExceptionEvent((PreparedStatement)this.physicalStmt, sQLException);
      throw sQLException;
    } 
  }
  
  public void setQueryTimeout(int paramInt) throws SQLException {
    if (isClosed())
      throw TbError.newSQLException(-90659); 
    try {
      this.physicalStmt.setQueryTimeout(paramInt);
    } catch (SQLException sQLException) {
      notifyExceptionEvent((PreparedStatement)this.physicalStmt, sQLException);
      throw sQLException;
    } 
  }
  
  public void setRef(int paramInt, Ref paramRef) throws SQLException {
    if (isClosed())
      throw TbError.newSQLException(-90659); 
    try {
      this.physicalStmt.setRef(paramInt, paramRef);
    } catch (SQLException sQLException) {
      notifyExceptionEvent((PreparedStatement)this.physicalStmt, sQLException);
      throw sQLException;
    } 
  }
  
  public void setRowId(int paramInt, RowId paramRowId) throws SQLException {
    if (isClosed())
      throw TbError.newSQLException(-90659); 
    try {
      this.physicalStmt.setRowId(paramInt, paramRowId);
    } catch (SQLException sQLException) {
      notifyExceptionEvent((PreparedStatement)this.physicalStmt, sQLException);
      throw sQLException;
    } 
  }
  
  public void setShort(int paramInt, short paramShort) throws SQLException {
    if (isClosed())
      throw TbError.newSQLException(-90659); 
    try {
      this.physicalStmt.setShort(paramInt, paramShort);
    } catch (SQLException sQLException) {
      notifyExceptionEvent((PreparedStatement)this.physicalStmt, sQLException);
      throw sQLException;
    } 
  }
  
  public void setSQLXML(int paramInt, SQLXML paramSQLXML) throws SQLException {
    if (isClosed())
      throw TbError.newSQLException(-90659); 
    try {
      this.physicalStmt.setSQLXML(paramInt, paramSQLXML);
    } catch (SQLException sQLException) {
      notifyExceptionEvent((PreparedStatement)this.physicalStmt, sQLException);
      throw sQLException;
    } 
  }
  
  public void setString(int paramInt, String paramString) throws SQLException {
    if (isClosed())
      throw TbError.newSQLException(-90659); 
    try {
      this.physicalStmt.setString(paramInt, paramString);
    } catch (SQLException sQLException) {
      notifyExceptionEvent((PreparedStatement)this.physicalStmt, sQLException);
      throw sQLException;
    } 
  }
  
  public void setTbDate(int paramInt, TbDate paramTbDate) throws SQLException {
    if (isClosed())
      throw TbError.newSQLException(-90659); 
    try {
      this.physicalStmt.setTbDate(paramInt, paramTbDate);
    } catch (SQLException sQLException) {
      notifyExceptionEvent((PreparedStatement)this.physicalStmt, sQLException);
      throw sQLException;
    } 
  }
  
  public void setTbTimestamp(int paramInt, TbTimestamp paramTbTimestamp) throws SQLException {
    if (isClosed())
      throw TbError.newSQLException(-90659); 
    try {
      this.physicalStmt.setTbTimestamp(paramInt, paramTbTimestamp);
    } catch (SQLException sQLException) {
      notifyExceptionEvent((PreparedStatement)this.physicalStmt, sQLException);
      throw sQLException;
    } 
  }
  
  public void setTime(int paramInt, Time paramTime) throws SQLException {
    if (isClosed())
      throw TbError.newSQLException(-90659); 
    try {
      this.physicalStmt.setTime(paramInt, paramTime);
    } catch (SQLException sQLException) {
      notifyExceptionEvent((PreparedStatement)this.physicalStmt, sQLException);
      throw sQLException;
    } 
  }
  
  public void setTime(int paramInt, Time paramTime, Calendar paramCalendar) throws SQLException {
    if (isClosed())
      throw TbError.newSQLException(-90659); 
    try {
      this.physicalStmt.setTime(paramInt, paramTime, paramCalendar);
    } catch (SQLException sQLException) {
      notifyExceptionEvent((PreparedStatement)this.physicalStmt, sQLException);
      throw sQLException;
    } 
  }
  
  public void setTimestamp(int paramInt, Timestamp paramTimestamp) throws SQLException {
    if (isClosed())
      throw TbError.newSQLException(-90659); 
    try {
      this.physicalStmt.setTimestamp(paramInt, paramTimestamp);
    } catch (SQLException sQLException) {
      notifyExceptionEvent((PreparedStatement)this.physicalStmt, sQLException);
      throw sQLException;
    } 
  }
  
  public void setTimestamp(int paramInt, Timestamp paramTimestamp, Calendar paramCalendar) throws SQLException {
    if (isClosed())
      throw TbError.newSQLException(-90659); 
    try {
      this.physicalStmt.setTimestamp(paramInt, paramTimestamp, paramCalendar);
    } catch (SQLException sQLException) {
      notifyExceptionEvent((PreparedStatement)this.physicalStmt, sQLException);
      throw sQLException;
    } 
  }
  
  @Deprecated
  public void setUnicodeStream(int paramInt1, InputStream paramInputStream, int paramInt2) throws SQLException {
    if (isClosed())
      throw TbError.newSQLException(-90659); 
    try {
      this.physicalStmt.setUnicodeStream(paramInt1, paramInputStream, paramInt2);
    } catch (SQLException sQLException) {
      notifyExceptionEvent((PreparedStatement)this.physicalStmt, sQLException);
      throw sQLException;
    } 
  }
  
  public void setURL(int paramInt, URL paramURL) throws SQLException {
    if (isClosed())
      throw TbError.newSQLException(-90659); 
    try {
      this.physicalStmt.setURL(paramInt, paramURL);
    } catch (SQLException sQLException) {
      notifyExceptionEvent((PreparedStatement)this.physicalStmt, sQLException);
      throw sQLException;
    } 
  }
  
  public <T> T unwrap(Class<T> paramClass) throws SQLException {
    try {
      return paramClass.cast(this);
    } catch (ClassCastException classCastException) {
      throw TbError.newSQLException(-90657);
    } 
  }
}


/* Location:              C:\TmaxData\tibero6\client\lib\jar\tibero6-jdbc.jar!\com\tmax\tibero\jdbc\ext\TbLogicalPreparedStatement.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */