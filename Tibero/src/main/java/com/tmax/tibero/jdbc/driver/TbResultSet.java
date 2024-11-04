package com.tmax.tibero.jdbc.driver;

import com.tmax.tibero.jdbc.data.Column;
import com.tmax.tibero.jdbc.data.RsetType;
import com.tmax.tibero.jdbc.data.TbDate;
import com.tmax.tibero.jdbc.data.TbRAW;
import com.tmax.tibero.jdbc.data.TbTimestamp;
import com.tmax.tibero.jdbc.err.TbError;
import java.io.InputStream;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.SQLWarning;

public abstract class TbResultSet implements com.tmax.tibero.jdbc.TbResultSet {
  protected SQLWarning warnings = null;
  protected RsetType rsetType;
  protected int rowsFetchedCnt;
  protected boolean haveLocator;
  protected long tsn;

  protected TbResultSet(RsetType var1) {
    this.rsetType = RsetType.FWRD;
    this.rowsFetchedCnt = 0;
    this.haveLocator = false;
    this.tsn = 0L;
    if (var1 != null) {
      this.rsetType = var1.getCopy();
    }

  }

  public synchronized void addWarning(SQLWarning var1) {
    if (this.warnings != null) {
      this.warnings.setNextWarning(var1);
    } else {
      this.warnings = var1;
    }

  }

  public abstract void buildRowTable(int var1, byte[] var2) throws SQLException;

  public synchronized void clearWarnings() throws SQLException {
    this.warnings = null;
  }

  public int getBytes(int var1, byte[] var2) throws SQLException {
    throw TbError.newSQLException(-90201);
  }

  public abstract TbRAW getRAW(int var1) throws SQLException;

  public abstract TbRAW getRAW(String var1) throws SQLException;

  public abstract Column[] getCols() throws SQLException;

  protected abstract int getColumnDataType(int var1) throws SQLException;

  protected abstract int getColumnMaxLength(int var1) throws SQLException;

  protected abstract String getColumnName(int var1) throws SQLException;

  protected abstract boolean getColumnNullable(int var1) throws SQLException;

  protected abstract int getColumnPrecision(int var1) throws SQLException;

  protected abstract int getColumnScale(int var1) throws SQLException;

  protected abstract int getColumnSqlType(int var1) throws SQLException;

  public int getConcurrency() throws SQLException {
    return this.rsetType.getConcurrency();
  }

  public int getHoldability() throws SQLException {
    return this.rsetType.getHoldability();
  }

  public InputStream getLongByteStream(int var1) throws SQLException {
    throw TbError.newSQLException(-90201);
  }

  public abstract ResultSetMetaData getMetaData() throws SQLException;

  public abstract byte[] getRowChunk(int var1) throws SQLException;

  public RsetType getRsetType() {
    return this.rsetType;
  }

  protected long getTsn() {
    return this.tsn;
  }

  public int getType() throws SQLException {
    return this.rsetType.getType();
  }

  public long getUpdateCount() {
    return (long)this.rowsFetchedCnt;
  }

  public abstract TbDate getTbDate(int var1) throws SQLException;

  public abstract TbTimestamp getTbTimestamp(int var1) throws SQLException;

  public abstract void updateTbTimestamp(int var1, TbTimestamp var2) throws SQLException;

  public abstract void updateTbTimestamp(String var1, TbTimestamp var2) throws SQLException;

  public synchronized SQLWarning getWarnings() throws SQLException {
    return this.warnings;
  }

  public boolean isWrapperFor(Class<?> var1) throws SQLException {
    return var1.isInstance(this);
  }

  protected void reset() {
    this.warnings = null;
    this.rsetType = null;
    this.rowsFetchedCnt = 0;
    this.haveLocator = true;
  }

  public abstract void setFetchCompleted(int var1) throws SQLException;

  public void setHaveLocator(boolean var1) {
    this.haveLocator = var1;
  }

  public void setRsetType(RsetType var1) {
    this.rsetType = var1;
  }

  public void setTsn(long var1) {
    this.tsn = var1;
  }

  public <T> T unwrap(Class<T> var1) throws SQLException {
    try {
      return var1.cast(this);
    } catch (ClassCastException var3) {
      throw TbError.newSQLException(-90657);
    }
  }
}