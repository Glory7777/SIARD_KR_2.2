package com.tmax.tibero.jdbc.driver;

import com.tmax.tibero.jdbc.TbResultSet;
import com.tmax.tibero.jdbc.data.BindItem;
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
import java.sql.Struct;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Map;
import java.util.Vector;

public class TbCallableStatement extends com.tmax.tibero.jdbc.TbCallableStatement {
  private final TbCallableStatementImpl impl;

  public TbCallableStatement(TbConnection paramTbConnection, String paramString) throws SQLException {
    this.impl = new TbCallableStatementImpl(paramTbConnection, paramString, 1003, 1007, 64000);
  }

  public TbCallableStatement(TbConnection paramTbConnection, String paramString, int paramInt1, int paramInt2, int paramInt3) throws SQLException {
    this.impl = new TbCallableStatementImpl(paramTbConnection, paramString, paramInt1, paramInt2, paramInt3);
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

  public Array getArray(int paramInt) throws SQLException {
    return this.impl.getArray(paramInt);
  }

  public Array getArray(String paramString) throws SQLException {
    return this.impl.getArray(paramString);
  }

  public BigDecimal getBigDecimal(int paramInt) throws SQLException {
    return this.impl.getBigDecimal(paramInt);
  }

  @Deprecated
  public BigDecimal getBigDecimal(int paramInt1, int paramInt2) throws SQLException {
    return this.impl.getBigDecimal(paramInt1, paramInt2);
  }

  public BigDecimal getBigDecimal(String paramString) throws SQLException {
    return this.impl.getBigDecimal(paramString);
  }

  public Blob getBlob(int paramInt) throws SQLException {
    return this.impl.getBlob(paramInt);
  }

  public Blob getBlob(String paramString) throws SQLException {
    return this.impl.getBlob(paramString);
  }

  public boolean getBoolean(int paramInt) throws SQLException {
    return this.impl.getBoolean(paramInt);
  }

  public boolean getBoolean(String paramString) throws SQLException {
    return this.impl.getBoolean(paramString);
  }

  public byte getByte(int paramInt) throws SQLException {
    return this.impl.getByte(paramInt);
  }

  public byte getByte(String paramString) throws SQLException {
    return this.impl.getByte(paramString);
  }

  public byte[] getBytes(int paramInt) throws SQLException {
    return this.impl.getBytes(paramInt);
  }

  public byte[] getBytes(String paramString) throws SQLException {
    return this.impl.getBytes(paramString);
  }

  public TbRAW getRAW(int paramInt) throws SQLException {
    return this.impl.getRAW(paramInt);
  }

  public TbRAW getRAW(String paramString) throws SQLException {
    return this.impl.getRAW(paramString);
  }

  public Reader getCharacterStream(int paramInt) throws SQLException {
    return this.impl.getCharacterStream(paramInt);
  }

  public Reader getCharacterStream(String paramString) throws SQLException {
    return this.impl.getCharacterStream(paramString);
  }

  public Clob getClob(int paramInt) throws SQLException {
    return this.impl.getClob(paramInt);
  }

  public Clob getClob(String paramString) throws SQLException {
    return this.impl.getClob(paramString);
  }

  public Connection getConnection() throws SQLException {
    return this.impl.getConnection();
  }

  public Date getDate(int paramInt) throws SQLException {
    return this.impl.getDate(paramInt);
  }

  public Date getDate(int paramInt, Calendar paramCalendar) throws SQLException {
    return this.impl.getDate(paramInt, paramCalendar);
  }

  public Date getDate(String paramString) throws SQLException {
    return this.impl.getDate(paramString);
  }

  public Date getDate(String paramString, Calendar paramCalendar) throws SQLException {
    return this.impl.getDate(paramString, paramCalendar);
  }

  public double getDouble(int paramInt) throws SQLException {
    return this.impl.getDouble(paramInt);
  }

  public double getDouble(String paramString) throws SQLException {
    return this.impl.getDouble(paramString);
  }

  public int getFetchDirection() throws SQLException {
    return this.impl.getFetchDirection();
  }

  public int getFetchSize() throws SQLException {
    return this.impl.getFetchSize();
  }

  public float getFloat(int paramInt) throws SQLException {
    return this.impl.getFloat(paramInt);
  }

  public float getFloat(String paramString) throws SQLException {
    return this.impl.getFloat(paramString);
  }

  public ResultSet getGeneratedKeys() throws SQLException {
    return this.impl.getGeneratedKeys();
  }

  public int getInt(int paramInt) throws SQLException {
    return this.impl.getInt(paramInt);
  }

  public int getInt(String paramString) throws SQLException {
    return this.impl.getInt(paramString);
  }

  public long getLong(int paramInt) throws SQLException {
    return this.impl.getLong(paramInt);
  }

  public long getLong(String paramString) throws SQLException {
    return this.impl.getLong(paramString);
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

  public Reader getNCharacterStream(int paramInt) throws SQLException {
    return this.impl.getNCharacterStream(paramInt);
  }

  public Reader getNCharacterStream(String paramString) throws SQLException {
    return this.impl.getNCharacterStream(paramString);
  }

  public NClob getNClob(int paramInt) throws SQLException {
    return this.impl.getNClob(paramInt);
  }

  public NClob getNClob(String paramString) throws SQLException {
    return this.impl.getNClob(paramString);
  }

  public String getNString(int paramInt) throws SQLException {
    return this.impl.getNString(paramInt);
  }

  public String getNString(String paramString) throws SQLException {
    return this.impl.getNString(paramString);
  }

  public Object getObject(int paramInt) throws SQLException {
    return this.impl.getObject(paramInt);
  }

  public Object getObject(int paramInt, Map<String, Class<?>> paramMap) throws SQLException {
    return this.impl.getObject(paramInt, paramMap);
  }

  public Object getObject(String paramString) throws SQLException {
    return this.impl.getObject(paramString);
  }

  public Object getObject(String paramString, Map<String, Class<?>> paramMap) throws SQLException {
    return this.impl.getObject(paramString, paramMap);
  }

  public String getOriginalSql() {
    return this.impl.getOriginalSql();
  }

  public BindItem getOutItems(int paramInt) {
    return this.impl.getOutItems(paramInt);
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

  public Vector<byte[]> getPivotData(int paramInt) throws SQLException {
    return this.impl.getPivotData(paramInt);
  }

  public TbPivotInfo[] getPivotInfo() {
    return this.impl.getPivotInfo();
  }

  public TbPivotInfo[] getPivotInfo(int paramInt) throws SQLException {
    return this.impl.getPivotInfo(paramInt);
  }

  public byte[] getPPID() {
    return this.impl.getPPID();
  }

  public int getQueryTimeout() throws SQLException {
    return this.impl.getQueryTimeout();
  }

  public Ref getRef(int paramInt) throws SQLException {
    return this.impl.getRef(paramInt);
  }

  public Ref getRef(String paramString) throws SQLException {
    return this.impl.getRef(paramString);
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

  public RowId getRowId(int paramInt) throws SQLException {
    return this.impl.getRowId(paramInt);
  }

  public RowId getRowId(String paramString) throws SQLException {
    return this.impl.getRowId(paramString);
  }

  public short getShort(int paramInt) throws SQLException {
    return this.impl.getShort(paramInt);
  }

  public short getShort(String paramString) throws SQLException {
    return this.impl.getShort(paramString);
  }

  public int getSqlType() {
    return this.impl.getSqlType();
  }

  public SQLXML getSQLXML(int paramInt) throws SQLException {
    return this.impl.getSQLXML(paramInt);
  }

  public SQLXML getSQLXML(String paramString) throws SQLException {
    return this.impl.getSQLXML(paramString);
  }

  public String getString(int paramInt) throws SQLException {
    return this.impl.getString(paramInt);
  }

  public String getString(String paramString) throws SQLException {
    return this.impl.getString(paramString);
  }

  public Struct getStruct(int paramInt) throws SQLException {
    return this.impl.getStruct(paramInt);
  }

  public TbDate getTbDate(int paramInt) throws SQLException {
    return this.impl.getTbDate(paramInt);
  }

  public TbDate getTbDate(String paramString) throws SQLException {
    return this.impl.getTbDate(paramString);
  }

  public TbTimestamp getTbTimestamp(int paramInt) throws SQLException {
    return this.impl.getTbTimestamp(paramInt);
  }

  public TbTimestamp getTbTimestamp(String paramString) throws SQLException {
    return this.impl.getTbTimestamp(paramString);
  }

  public Time getTime(int paramInt) throws SQLException {
    return this.impl.getTime(paramInt);
  }

  public Time getTime(int paramInt, Calendar paramCalendar) throws SQLException {
    return this.impl.getTime(paramInt, paramCalendar);
  }

  public Time getTime(String paramString) throws SQLException {
    return this.impl.getTime(paramString);
  }

  public Time getTime(String paramString, Calendar paramCalendar) throws SQLException {
    return this.impl.getTime(paramString, paramCalendar);
  }

  public Timestamp getTimestamp(int paramInt) throws SQLException {
    return this.impl.getTimestamp(paramInt);
  }

  public Timestamp getTimestamp(int paramInt, Calendar paramCalendar) throws SQLException {
    return this.impl.getTimestamp(paramInt, paramCalendar);
  }

  public Timestamp getTimestamp(String paramString) throws SQLException {
    return this.impl.getTimestamp(paramString);
  }

  public Timestamp getTimestamp(String paramString, Calendar paramCalendar) throws SQLException {
    return this.impl.getTimestamp(paramString, paramCalendar);
  }

  public int getUpdateCount() throws SQLException {
    return this.impl.getUpdateCount();
  }

  public URL getURL(int paramInt) throws SQLException {
    return this.impl.getURL(paramInt);
  }

  public URL getURL(String paramString) throws SQLException {
    return this.impl.getURL(paramString);
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

  public void registerOutParameter(int paramInt1, int paramInt2) throws SQLException {
    this.impl.registerOutParameter(paramInt1, paramInt2);
  }

  public void registerOutParameter(int paramInt1, int paramInt2, int paramInt3) throws SQLException {
    this.impl.registerOutParameter(paramInt1, paramInt2, paramInt3);
  }

  public void registerOutParameter(int paramInt1, int paramInt2, String paramString) throws SQLException {
    this.impl.registerOutParameter(paramInt1, paramInt2, paramString);
  }

  public void registerOutParameter(String paramString, int paramInt) throws SQLException {
    this.impl.registerOutParameter(paramString, paramInt);
  }

  public void registerOutParameter(String paramString, int paramInt1, int paramInt2) throws SQLException {
    this.impl.registerOutParameter(paramString, paramInt1, paramInt2);
  }

  public void registerOutParameter(String paramString1, int paramInt, String paramString2) throws SQLException {
    this.impl.registerOutParameter(paramString1, paramInt, paramString2);
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

  public void setAsciiStream(String paramString, InputStream paramInputStream) throws SQLException {
    this.impl.setAsciiStream(paramString, paramInputStream);
  }

  public void setAsciiStream(String paramString, InputStream paramInputStream, int paramInt) throws SQLException {
    this.impl.setAsciiStream(paramString, paramInputStream, paramInt);
  }

  public void setAsciiStream(String paramString, InputStream paramInputStream, long paramLong) throws SQLException {
    this.impl.setAsciiStream(paramString, paramInputStream, paramLong);
  }

  public void setBigDecimal(int paramInt, BigDecimal paramBigDecimal) throws SQLException {
    this.impl.setBigDecimal(paramInt, paramBigDecimal);
  }

  public void setBigDecimal(String paramString, BigDecimal paramBigDecimal) throws SQLException {
    this.impl.setBigDecimal(paramString, paramBigDecimal);
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

  public void setBinaryStream(String paramString, InputStream paramInputStream) throws SQLException {
    this.impl.setBinaryStream(paramString, paramInputStream);
  }

  public void setBinaryStream(String paramString, InputStream paramInputStream, int paramInt) throws SQLException {
    this.impl.setBinaryStream(paramString, paramInputStream, paramInt);
  }

  public void setBinaryStream(String paramString, InputStream paramInputStream, long paramLong) throws SQLException {
    this.impl.setBinaryStream(paramString, paramInputStream, paramLong);
  }

  public void setBlob(int paramInt, Blob paramBlob) throws SQLException {
    this.impl.setBlob(paramInt, paramBlob);
  }

  public void setBlob(int paramInt, InputStream paramInputStream) throws SQLException {
    this.impl.setBlob(paramInt, paramInputStream);
  }

  public void setBlob(int paramInt, InputStream paramInputStream, long paramLong) throws SQLException {
    this.impl.setBlob(paramInt, paramInputStream, paramInt);
  }

  public void setBlob(String paramString, Blob paramBlob) throws SQLException {
    this.impl.setBlob(paramString, paramBlob);
  }

  public void setBlob(String paramString, InputStream paramInputStream) throws SQLException {
    this.impl.setBlob(paramString, paramInputStream);
  }

  public void setBlob(String paramString, InputStream paramInputStream, long paramLong) throws SQLException {
    this.impl.setBlob(paramString, paramInputStream, paramLong);
  }

  public void setBoolean(int paramInt, boolean paramBoolean) throws SQLException {
    this.impl.setBoolean(paramInt, paramBoolean);
  }

  public void setBoolean(String paramString, boolean paramBoolean) throws SQLException {
    this.impl.setBoolean(paramString, paramBoolean);
  }

  public void setByte(int paramInt, byte paramByte) throws SQLException {
    this.impl.setByte(paramInt, paramByte);
  }

  public void setByte(String paramString, byte paramByte) throws SQLException {
    this.impl.setByte(paramString, paramByte);
  }

  public void setBytes(int paramInt, byte[] paramArrayOfbyte) throws SQLException {
    this.impl.setBytes(paramInt, paramArrayOfbyte);
  }

  public void setBytes(int paramInt1, int paramInt2, byte[] paramArrayOfbyte) throws SQLException {
    this.impl.setBytes(paramInt1, paramInt2, paramArrayOfbyte);
  }

  public void setBytes(String paramString, byte[] paramArrayOfbyte) throws SQLException {
    this.impl.setBytes(paramString, paramArrayOfbyte);
  }

  public void setRAW(int paramInt, TbRAW paramTbRAW) throws SQLException {
    this.impl.setRAW(paramInt, paramTbRAW);
  }

  public void setRAW(int paramInt1, int paramInt2, TbRAW paramTbRAW) throws SQLException {
    this.impl.setRAW(paramInt1, paramInt2, paramTbRAW);
  }

  public void setRAW(String paramString, TbRAW paramTbRAW) throws SQLException {
    this.impl.setRAW(paramString, paramTbRAW);
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

  public void setCharacterStream(String paramString, Reader paramReader) throws SQLException {
    this.impl.setCharacterStream(paramString, paramReader);
  }

  public void setCharacterStream(String paramString, Reader paramReader, int paramInt) throws SQLException {
    this.impl.setCharacterStream(paramString, paramReader, paramInt);
  }

  public void setCharacterStream(String paramString, Reader paramReader, long paramLong) throws SQLException {
    this.impl.setCharacterStream(paramString, paramReader, paramLong);
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

  public void setClob(String paramString, Clob paramClob) throws SQLException {
    this.impl.setClob(paramString, paramClob);
  }

  public void setClob(String paramString, Reader paramReader) throws SQLException {
    this.impl.setClob(paramString, paramReader);
  }

  public void setClob(String paramString, Reader paramReader, long paramLong) throws SQLException {
    this.impl.setClob(paramString, paramReader, paramLong);
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

  public void setDate(String paramString, Date paramDate) throws SQLException {
    this.impl.setDate(paramString, paramDate);
  }

  public void setDate(String paramString, Date paramDate, Calendar paramCalendar) throws SQLException {
    this.impl.setDate(paramString, paramDate, paramCalendar);
  }

  public void setDouble(int paramInt, double paramDouble) throws SQLException {
    this.impl.setDouble(paramInt, paramDouble);
  }

  public void setDouble(String paramString, double paramDouble) throws SQLException {
    this.impl.setDouble(paramString, paramDouble);
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

  public void setFloat(String paramString, float paramFloat) throws SQLException {
    this.impl.setFloat(paramString, paramFloat);
  }

  public void setInt(int paramInt1, int paramInt2) throws SQLException {
    this.impl.setInt(paramInt1, paramInt2);
  }

  public void setInt(String paramString, int paramInt) throws SQLException {
    this.impl.setInt(paramString, paramInt);
  }

  public void setLong(int paramInt, long paramLong) throws SQLException {
    this.impl.setLong(paramInt, paramLong);
  }

  public void setLong(String paramString, long paramLong) throws SQLException {
    this.impl.setLong(paramString, paramLong);
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

  public void setNCharacterStream(String paramString, Reader paramReader) throws SQLException {
    this.impl.setNCharacterStream(paramString, paramReader);
  }

  public void setNCharacterStream(String paramString, Reader paramReader, long paramLong) throws SQLException {
    this.impl.setNCharacterStream(paramString, paramReader, paramLong);
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

  public void setNClob(String paramString, NClob paramNClob) throws SQLException {
    this.impl.setNClob(paramString, paramNClob);
  }

  public void setNClob(String paramString, Reader paramReader) throws SQLException {
    this.impl.setNClob(paramString, paramReader);
  }

  @Override
  public <T> T getObject(int parameterIndex, Class<T> type) throws SQLException {
    return null;
  }

  @Override
  public <T> T getObject(String parameterName, Class<T> type) throws SQLException {
    return null;
  }

  public void setNClob(String paramString, Reader paramReader, long paramLong) throws SQLException {
    this.impl.setNClob(paramString, paramReader, paramLong);
  }

  public void setNString(int paramInt, String paramString) throws SQLException {
    this.impl.setNString(paramInt, paramString);
  }

  public void setNString(String paramString1, String paramString2) throws SQLException {
    this.impl.setNString(paramString1, paramString2);
  }

  public void setNull(int paramInt1, int paramInt2) throws SQLException {
    this.impl.setNull(paramInt1, paramInt2);
  }

  public void setNull(int paramInt1, int paramInt2, String paramString) throws SQLException {
    this.impl.setNull(paramInt1, paramInt2, paramString);
  }

  public void setNull(String paramString, int paramInt) throws SQLException {
    this.impl.setNull(paramString, paramInt);
  }

  public void setNull(String paramString1, int paramInt, String paramString2) throws SQLException {
    this.impl.setNull(paramString1, paramInt, paramString2);
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

  public void setObject(String paramString, Object paramObject) throws SQLException {
    this.impl.setObject(paramString, paramObject);
  }

  public void setObject(String paramString, Object paramObject, int paramInt) throws SQLException {
    this.impl.setObject(paramString, paramObject, paramInt);
  }

  public void setObject(String paramString, Object paramObject, int paramInt1, int paramInt2) throws SQLException {
    this.impl.setObject(paramString, paramObject, paramInt1, paramInt2);
  }

  public void setOutParam(int paramInt1, int paramInt2, byte[] paramArrayOfbyte, TbResultSet paramTbResultSet) throws SQLException {
    this.impl.setOutParam(paramInt1, paramInt2, paramArrayOfbyte, paramTbResultSet);
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

  public void setRowId(String paramString, RowId paramRowId) throws SQLException {
    this.impl.setRowId(paramString, paramRowId);
  }

  public void setShort(int paramInt, short paramShort) throws SQLException {
    this.impl.setShort(paramInt, paramShort);
  }

  public void setShort(String paramString, short paramShort) throws SQLException {
    this.impl.setShort(paramString, paramShort);
  }

  public void setSQLXML(int paramInt, SQLXML paramSQLXML) throws SQLException {
    this.impl.setSQLXML(paramInt, paramSQLXML);
  }

  public void setSQLXML(String paramString, SQLXML paramSQLXML) throws SQLException {
    this.impl.setSQLXML(paramString, paramSQLXML);
  }

  public void setString(int paramInt, String paramString) throws SQLException {
    this.impl.setString(paramInt, paramString);
  }

  public void setString(String paramString1, String paramString2) throws SQLException {
    this.impl.setString(paramString1, paramString2);
  }

  public void setTbDate(int paramInt, TbDate paramTbDate) throws SQLException {
    this.impl.setTbDate(paramInt, paramTbDate);
  }

  public void setTbDate(String paramString, TbDate paramTbDate) throws SQLException {
    this.impl.setTbDate(paramString, paramTbDate);
  }

  public void setTbTimestamp(int paramInt, TbTimestamp paramTbTimestamp) throws SQLException {
    this.impl.setTbTimestamp(paramInt, paramTbTimestamp);
  }

  public void setTbTimestamp(String paramString, TbTimestamp paramTbTimestamp) throws SQLException {
    this.impl.setTbTimestamp(paramString, paramTbTimestamp);
  }

  public void setTime(int paramInt, Time paramTime) throws SQLException {
    this.impl.setTime(paramInt, paramTime);
  }

  public void setTime(int paramInt, Time paramTime, Calendar paramCalendar) throws SQLException {
    this.impl.setTime(paramInt, paramTime, paramCalendar);
  }

  public void setTime(String paramString, Time paramTime) throws SQLException {
    this.impl.setTime(paramString, paramTime);
  }

  public void setTime(String paramString, Time paramTime, Calendar paramCalendar) throws SQLException {
    this.impl.setTime(paramString, paramTime, paramCalendar);
  }

  public void setTimestamp(int paramInt, Timestamp paramTimestamp) throws SQLException {
    this.impl.setTimestamp(paramInt, paramTimestamp);
  }

  public void setTimestamp(int paramInt, Timestamp paramTimestamp, Calendar paramCalendar) throws SQLException {
    this.impl.setTimestamp(paramInt, paramTimestamp, paramCalendar);
  }

  public void setTimestamp(String paramString, Timestamp paramTimestamp) throws SQLException {
    this.impl.setTimestamp(paramString, paramTimestamp);
  }

  public void setTimestamp(String paramString, Timestamp paramTimestamp, Calendar paramCalendar) throws SQLException {
    this.impl.setTimestamp(paramString, paramTimestamp, paramCalendar);
  }

  @Deprecated
  public void setUnicodeStream(int paramInt1, InputStream paramInputStream, int paramInt2) throws SQLException {
    this.impl.setUnicodeStream(paramInt1, paramInputStream, paramInt2);
  }

  @Deprecated
  public void setUnicodeStream(String paramString, InputStream paramInputStream, int paramInt) throws SQLException {
    this.impl.setUnicodeStream(paramString, paramInputStream, paramInt);
  }

  public void setURL(int paramInt, URL paramURL) throws SQLException {
    this.impl.setURL(paramInt, paramURL);
  }

  public void setURL(String paramString, URL paramURL) throws SQLException {
    this.impl.setURL(paramString, paramURL);
  }

  public <T> T unwrap(Class<T> paramClass) throws SQLException {
    try {
      return paramClass.cast(this);
    } catch (ClassCastException classCastException) {
      throw TbError.newSQLException(-90657);
    }
  }

  public boolean wasNull() throws SQLException {
    return this.impl.wasNull();
  }
}


/* Location:              C:\TmaxData\tibero6\client\lib\jar\tibero6-jdbc.jar!\com\tmax\tibero\jdbc\driver\TbCallableStatement.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */