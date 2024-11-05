package com.tmax.tibero.jdbc.driver;

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
import java.util.Vector;

public class TbPreparedStatement extends com.tmax.tibero.jdbc.TbPreparedStatement {
  private final TbPreparedStatementImpl impl;
  
  public TbPreparedStatement(TbConnection paramTbConnection, String paramString) throws SQLException {
    this.impl = new TbPreparedStatementImpl(paramTbConnection, paramString, 1003, 1007, 64000, false);
  }
  
  public TbPreparedStatement(TbConnection paramTbConnection, String paramString, int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean) throws SQLException {
    this.impl = new TbPreparedStatementImpl(paramTbConnection, paramString, paramInt1, paramInt2, paramInt3, paramBoolean);
  }
  
  public void addBatch() throws SQLException {
    this.impl.addBatch();
  }
  
  public void addBatch(String paramString) throws SQLException {
    this.impl.addBatch(paramString);
  }
  
  public void addPivotData(byte[] paramArrayOfbyte) {
    this.impl.addPivotData(paramArrayOfbyte);
  }
  
  public void cancel() throws SQLException {
    this.impl.cancel();
  }
  
  public void clearBatch() throws SQLException {
    this.impl.clearBatch();
  }
  
  public void clearParameters() throws SQLException {
    this.impl.clearParameters();
  }
  
  public void clearWarnings() throws SQLException {
    this.impl.clearWarnings();
  }
  
  public void close() throws SQLException {
    this.impl.close();
  }
  
  public void closeInternal() throws SQLException {
    this.impl.closeInternal();
  }
  
  public boolean execute() throws SQLException {
    return this.impl.execute();
  }
  
  public boolean execute(String paramString) throws SQLException {
    return this.impl.execute(paramString);
  }
  
  public boolean execute(String paramString, int paramInt) throws SQLException {
    return this.impl.execute(paramString, paramInt);
  }
  
  public boolean execute(String paramString, int[] paramArrayOfint) throws SQLException {
    return this.impl.execute(paramString, paramArrayOfint);
  }
  
  public boolean execute(String paramString, String[] paramArrayOfString) throws SQLException {
    return this.impl.execute(paramString, paramArrayOfString);
  }
  
  public int[] executeBatch() throws SQLException {
    return this.impl.executeBatch();
  }
  
  public ResultSet executeQuery() throws SQLException {
    return this.impl.executeQuery();
  }
  
  public ResultSet executeQuery(String paramString) throws SQLException {
    return this.impl.executeQuery(paramString);
  }
  
  public int executeUpdate() throws SQLException {
    return this.impl.executeUpdate();
  }
  
  public int executeUpdate(String paramString) throws SQLException {
    return this.impl.executeUpdate(paramString);
  }
  
  public int executeUpdate(String paramString, int paramInt) throws SQLException {
    return this.impl.executeUpdate(paramString, paramInt);
  }
  
  public int executeUpdate(String paramString, int[] paramArrayOfint) throws SQLException {
    return this.impl.executeUpdate(paramString, paramArrayOfint);
  }
  
  public int executeUpdate(String paramString, String[] paramArrayOfString) throws SQLException {
    return this.impl.executeUpdate(paramString, paramArrayOfString);
  }
  
  public Connection getConnection() throws SQLException {
    return this.impl.getConnection();
  }
  
  public int getFetchDirection() throws SQLException {
    return this.impl.getFetchDirection();
  }
  
  public int getFetchSize() throws SQLException {
    return this.impl.getFetchSize();
  }
  
  public ResultSet getGeneratedKeys() throws SQLException {
    return this.impl.getGeneratedKeys();
  }
  
  public int getMaxFieldSize() throws SQLException {
    return this.impl.getMaxFieldSize();
  }
  
  public int getMaxRows() throws SQLException {
    return this.impl.getMaxRows();
  }
  
  public ResultSetMetaData getMetaData() throws SQLException {
    return this.impl.getMetaData();
  }
  
  public boolean getMoreResults() throws SQLException {
    return this.impl.getMoreResults();
  }
  
  public boolean getMoreResults(int paramInt) throws SQLException {
    return this.impl.getMoreResults(paramInt);
  }
  
  public String getOriginalSql() {
    return this.impl.getOriginalSql();
  }
  
  public ParamContainer getParamContainer() {
    return this.impl;
  }
  
  public ParameterMetaData getParameterMetaData() throws SQLException {
    return this.impl.getParameterMetaData();
  }
  
  public Vector<byte[]> getPivotData() {
    return this.impl.getPivotData();
  }
  
  public TbPivotInfo[] getPivotInfo() {
    return this.impl.getPivotInfo();
  }
  
  public byte[] getPPID() {
    return this.impl.getPPID();
  }
  
  public int getQueryTimeout() throws SQLException {
    return this.impl.getQueryTimeout();
  }
  
  public ResultSet getResultSet() throws SQLException {
    return this.impl.getResultSet();
  }
  
  public int getResultSetConcurrency() throws SQLException {
    return this.impl.getResultSetConcurrency();
  }
  
  public int getResultSetHoldability() throws SQLException {
    return this.impl.getResultSetHoldability();
  }
  
  public int getResultSetType() throws SQLException {
    return this.impl.getResultSetType();
  }
  
  public int getSqlType() {
    return this.impl.getSqlType();
  }
  
  public int getUpdateCount() throws SQLException {
    return this.impl.getUpdateCount();
  }
  
  public SQLWarning getWarnings() throws SQLException {
    return this.impl.getWarnings();
  }
  
  public boolean isClosed() throws SQLException {
    return this.impl.isClosed();
  }
  
  public boolean isPoolable() throws SQLException {
    return this.impl.isPoolable();
  }

  @Override
  public void closeOnCompletion() throws SQLException {

  }

  @Override
  public boolean isCloseOnCompletion() throws SQLException {
    return false;
  }

  public boolean isWrapperFor(Class<?> paramClass) throws SQLException {
    return paramClass.isInstance(this);
  }
  
  public void resetForCache() {
    this.impl.resetForCache();
  }
  
  public void setArray(int paramInt, Array paramArray) throws SQLException {
    this.impl.setArray(paramInt, paramArray);
  }
  
  public void setAsciiStream(int paramInt, InputStream paramInputStream) throws SQLException {
    this.impl.setAsciiStream(paramInt, paramInputStream);
  }
  
  public void setAsciiStream(int paramInt1, InputStream paramInputStream, int paramInt2) throws SQLException {
    this.impl.setAsciiStream(paramInt1, paramInputStream, paramInt2);
  }
  
  public void setAsciiStream(int paramInt, InputStream paramInputStream, long paramLong) throws SQLException {
    this.impl.setAsciiStream(paramInt, paramInputStream, paramLong);
  }
  
  public void setBigDecimal(int paramInt, BigDecimal paramBigDecimal) throws SQLException {
    this.impl.setBigDecimal(paramInt, paramBigDecimal);
  }
  
  public void setBinaryDouble(int paramInt, double paramDouble) throws SQLException {
    this.impl.setBinaryDouble(paramInt, paramDouble);
  }
  
  public void setBinaryFloat(int paramInt, float paramFloat) throws SQLException {
    this.impl.setBinaryFloat(paramInt, paramFloat);
  }
  
  public void setBinaryStream(int paramInt, InputStream paramInputStream) throws SQLException {
    this.impl.setBinaryStream(paramInt, paramInputStream);
  }
  
  public void setBinaryStream(int paramInt1, InputStream paramInputStream, int paramInt2) throws SQLException {
    this.impl.setBinaryStream(paramInt1, paramInputStream, paramInt2);
  }
  
  public void setBinaryStream(int paramInt, InputStream paramInputStream, long paramLong) throws SQLException {
    this.impl.setBinaryStream(paramInt, paramInputStream, paramLong);
  }
  
  public void setBlob(int paramInt, Blob paramBlob) throws SQLException {
    this.impl.setBlob(paramInt, paramBlob);
  }
  
  public void setBlob(int paramInt, InputStream paramInputStream) throws SQLException {
    this.impl.setBlob(paramInt, paramInputStream);
  }
  
  public void setBlob(int paramInt, InputStream paramInputStream, long paramLong) throws SQLException {
    this.impl.setBlob(paramInt, paramInputStream, paramLong);
  }
  
  public void setBoolean(int paramInt, boolean paramBoolean) throws SQLException {
    this.impl.setBoolean(paramInt, paramBoolean);
  }
  
  public void setByte(int paramInt, byte paramByte) throws SQLException {
    this.impl.setByte(paramInt, paramByte);
  }
  
  public void setBytes(int paramInt, byte[] paramArrayOfbyte) throws SQLException {
    this.impl.setBytes(paramInt, paramArrayOfbyte);
  }
  
  public void setBytes(int paramInt1, int paramInt2, byte[] paramArrayOfbyte) throws SQLException {
    this.impl.setBytes(paramInt1, paramInt2, paramArrayOfbyte);
  }
  
  public void setRAW(int paramInt, TbRAW paramTbRAW) throws SQLException {
    this.impl.setRAW(paramInt, paramTbRAW);
  }
  
  public void setRAW(int paramInt1, int paramInt2, TbRAW paramTbRAW) throws SQLException {
    this.impl.setRAW(paramInt1, paramInt2, paramTbRAW);
  }
  
  public void setCharacterStream(int paramInt, Reader paramReader) throws SQLException {
    this.impl.setCharacterStream(paramInt, paramReader);
  }
  
  public void setCharacterStream(int paramInt1, Reader paramReader, int paramInt2) throws SQLException {
    this.impl.setCharacterStream(paramInt1, paramReader, paramInt2);
  }
  
  public void setCharacterStream(int paramInt, Reader paramReader, long paramLong) throws SQLException {
    this.impl.setCharacterStream(paramInt, paramReader, paramLong);
  }
  
  public void setClob(int paramInt, Clob paramClob) throws SQLException {
    this.impl.setClob(paramInt, paramClob);
  }
  
  public void setClob(int paramInt, Reader paramReader) throws SQLException {
    this.impl.setClob(paramInt, paramReader);
  }
  
  public void setClob(int paramInt, Reader paramReader, long paramLong) throws SQLException {
    this.impl.setClob(paramInt, paramReader, paramLong);
  }
  
  public void setCursorName(String paramString) throws SQLException {
    this.impl.setCursorName(paramString);
  }
  
  public void setDate(int paramInt, Date paramDate) throws SQLException {
    this.impl.setDate(paramInt, paramDate);
  }
  
  public void setDate(int paramInt, Date paramDate, Calendar paramCalendar) throws SQLException {
    this.impl.setDate(paramInt, paramDate, paramCalendar);
  }
  
  public void setDouble(int paramInt, double paramDouble) throws SQLException {
    this.impl.setDouble(paramInt, paramDouble);
  }
  
  public void setEscapeProcessing(boolean paramBoolean) throws SQLException {
    this.impl.setEscapeProcessing(paramBoolean);
  }
  
  public void setFetchDirection(int paramInt) throws SQLException {
    this.impl.setFetchDirection(paramInt);
  }
  
  public void setFetchSize(int paramInt) throws SQLException {
    this.impl.setFetchSize(paramInt);
  }
  
  public void setFixedCHAR(int paramInt, String paramString) throws SQLException {
    this.impl.setFixedCHAR(paramInt, paramString);
  }
  
  public void setFloat(int paramInt, float paramFloat) throws SQLException {
    this.impl.setFloat(paramInt, paramFloat);
  }
  
  public void setInt(int paramInt1, int paramInt2) throws SQLException {
    this.impl.setInt(paramInt1, paramInt2);
  }
  
  public void setLong(int paramInt, long paramLong) throws SQLException {
    this.impl.setLong(paramInt, paramLong);
  }
  
  public void setMaxFieldSize(int paramInt) throws SQLException {
    this.impl.setMaxFieldSize(paramInt);
  }
  
  public void setMaxRows(int paramInt) throws SQLException {
    this.impl.setMaxRows(paramInt);
  }
  
  public void setNCharacterStream(int paramInt, Reader paramReader) throws SQLException {
    this.impl.setNCharacterStream(paramInt, paramReader);
  }
  
  public void setNCharacterStream(int paramInt, Reader paramReader, long paramLong) throws SQLException {
    this.impl.setNCharacterStream(paramInt, paramReader, paramLong);
  }
  
  public void setNClob(int paramInt, Clob paramClob) throws SQLException {
    this.impl.setNClob(paramInt, paramClob);
  }
  
  public void setNClob(int paramInt, NClob paramNClob) throws SQLException {
    this.impl.setNClob(paramInt, paramNClob);
  }
  
  public void setNClob(int paramInt, Reader paramReader) throws SQLException {
    this.impl.setNClob(paramInt, paramReader);
  }
  
  public void setNClob(int paramInt, Reader paramReader, long paramLong) throws SQLException {
    this.impl.setNClob(paramInt, paramReader, paramLong);
  }
  
  public void setNString(int paramInt, String paramString) throws SQLException {
    this.impl.setNString(paramInt, paramString);
  }
  
  public void setNull(int paramInt1, int paramInt2) throws SQLException {
    this.impl.setNull(paramInt1, paramInt2);
  }
  
  public void setNull(int paramInt1, int paramInt2, String paramString) throws SQLException {
    this.impl.setNull(paramInt1, paramInt2, paramString);
  }
  
  public void setObject(int paramInt, Object paramObject) throws SQLException {
    this.impl.setObject(paramInt, paramObject);
  }
  
  public void setObject(int paramInt1, Object paramObject, int paramInt2) throws SQLException {
    this.impl.setObject(paramInt1, paramObject, paramInt2);
  }
  
  public void setObject(int paramInt1, Object paramObject, int paramInt2, int paramInt3) throws SQLException {
    this.impl.setObject(paramInt1, paramObject, paramInt2, paramInt3);
  }
  
  public void setPivotInfo(TbPivotInfo[] paramArrayOfTbPivotInfo) {
    this.impl.setPivotInfo(paramArrayOfTbPivotInfo);
  }
  
  public void setPoolable(boolean paramBoolean) throws SQLException {
    this.impl.setPoolable(paramBoolean);
  }
  
  public void setQueryTimeout(int paramInt) throws SQLException {
    this.impl.setQueryTimeout(paramInt);
  }
  
  public void setRef(int paramInt, Ref paramRef) throws SQLException {
    this.impl.setRef(paramInt, paramRef);
  }
  
  public void setRowId(int paramInt, RowId paramRowId) throws SQLException {
    this.impl.setRowId(paramInt, paramRowId);
  }
  
  public void setShort(int paramInt, short paramShort) throws SQLException {
    this.impl.setShort(paramInt, paramShort);
  }
  
  public void setSQLXML(int paramInt, SQLXML paramSQLXML) throws SQLException {
    this.impl.setSQLXML(paramInt, paramSQLXML);
  }
  
  public void setString(int paramInt, String paramString) throws SQLException {
    this.impl.setString(paramInt, paramString);
  }
  
  public void setTbDate(int paramInt, TbDate paramTbDate) throws SQLException {
    this.impl.setTbDate(paramInt, paramTbDate);
  }
  
  public void setTbTimestamp(int paramInt, TbTimestamp paramTbTimestamp) throws SQLException {
    this.impl.setTbTimestamp(paramInt, paramTbTimestamp);
  }
  
  public void setTime(int paramInt, Time paramTime) throws SQLException {
    this.impl.setTime(paramInt, paramTime);
  }
  
  public void setTime(int paramInt, Time paramTime, Calendar paramCalendar) throws SQLException {
    this.impl.setTime(paramInt, paramTime, paramCalendar);
  }
  
  public void setTimestamp(int paramInt, Timestamp paramTimestamp) throws SQLException {
    this.impl.setTimestamp(paramInt, paramTimestamp);
  }
  
  public void setTimestamp(int paramInt, Timestamp paramTimestamp, Calendar paramCalendar) throws SQLException {
    this.impl.setTimestamp(paramInt, paramTimestamp, paramCalendar);
  }
  
  public void setUnicodeStream(int paramInt1, InputStream paramInputStream, int paramInt2) throws SQLException {
    this.impl.setUnicodeStream(paramInt1, paramInputStream, paramInt2);
  }
  
  public void setURL(int paramInt, URL paramURL) throws SQLException {
    this.impl.setURL(paramInt, paramURL);
  }
  
  public <T> T unwrap(Class<T> paramClass) throws SQLException {
    try {
      return paramClass.cast(this);
    } catch (ClassCastException classCastException) {
      throw TbError.newSQLException(-90657);
    } 
  }
  
  protected TbPreparedStatementImpl impl() {
    return this.impl;
  }
  
  protected void setAutoGenKeyArr(Object paramObject) {
    this.impl.setAutoGenKeyArr(paramObject);
  }
  
  protected void setReturnAutoGeneratedKeys(boolean paramBoolean) {
    this.impl.setReturnAutoGeneratedKeys(paramBoolean);
  }
}


/* Location:              C:\TmaxData\tibero6\client\lib\jar\tibero6-jdbc.jar!\com\tmax\tibero\jdbc\driver\TbPreparedStatement.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */