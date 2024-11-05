package com.tmax.tibero.jdbc.rowset;

import com.tmax.tibero.jdbc.TbDriver;
import com.tmax.tibero.jdbc.err.TbError;
import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.NClob;
import java.sql.PreparedStatement;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLXML;
import java.sql.Savepoint;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Map;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import javax.sql.RowSet;
import javax.sql.rowset.JdbcRowSet;
import javax.sql.rowset.Joinable;
import javax.sql.rowset.RowSetWarning;

public class TbJdbcRowSet extends TbRowSet implements JdbcRowSet, Joinable {
  private Connection conn;
  
  private PreparedStatement pstmt;
  
  private ResultSet rs;
  
  private static boolean isInitializedDriver;
  
  public TbJdbcRowSet() {
    this((Connection)null);
  }
  
  public TbJdbcRowSet(Connection paramConnection) {
    this.conn = paramConnection;
    isInitializedDriver = false;
  }
  
  public boolean absolute(int paramInt) throws SQLException {
    boolean bool = this.rs.absolute(paramInt);
    if (bool)
      notifyCursorMoved(); 
    return bool;
  }
  
  public void afterLast() throws SQLException {
    if (!isAfterLast()) {
      this.rs.afterLast();
      notifyCursorMoved();
    } 
  }
  
  public void beforeFirst() throws SQLException {
    if (!isBeforeFirst()) {
      this.rs.beforeFirst();
      notifyCursorMoved();
    } 
  }
  
  public void cancelRowUpdates() throws SQLException {
    this.rs.cancelRowUpdates();
    notifyRowChanged();
  }
  
  public void clearParameters() throws SQLException {
    this.pstmt.clearParameters();
  }
  
  public void close() throws SQLException {
    if (this.rs != null)
      this.rs.close(); 
    if (this.pstmt != null)
      this.pstmt.close(); 
    if (this.conn != null && !this.conn.isClosed()) {
      this.conn.commit();
      this.conn.close();
    } 
    notifyRowSetChanged();
    this.isClosed = true;
  }
  
  public void commit() throws SQLException {
    if (this.conn != null) {
      this.conn.commit();
    } else {
      throw TbError.newSQLException(-90827);
    } 
  }
  
  public void deleteRow() throws SQLException {
    this.rs.deleteRow();
    notifyRowChanged();
  }
  
  public void execute() throws SQLException {
    this.conn = getConnection(this);
    try {
      this.conn.setTransactionIsolation(getTransactionIsolation());
    } catch (SQLException sQLException) {}
    this.conn.setTypeMap(getTypeMap());
    if (this.pstmt == null)
      this.pstmt = this.conn.prepareStatement(getCommand(), getType(), getConcurrency()); 
    this.pstmt.setFetchSize(getFetchSize());
    this.pstmt.setFetchDirection(getFetchDirection());
    this.pstmt.setMaxFieldSize(getMaxFieldSize());
    this.pstmt.setMaxRows(getMaxRows());
    this.pstmt.setQueryTimeout(getQueryTimeout());
    this.pstmt.setEscapeProcessing(getEscapeProcessing());
    setParams(getParams(), this.pstmt);
    this.rs = this.pstmt.executeQuery();
    notifyRowSetChanged();
  }
  
  private void setParams(Object[] paramArrayOfObject, PreparedStatement paramPreparedStatement) throws SQLException {
    for (byte b = 0; b < paramArrayOfObject.length; b++) {
      if (!(paramArrayOfObject[b] instanceof Object[])) {
        paramPreparedStatement.setObject(b + 1, paramArrayOfObject[b]);
      } else {
        Object[] arrayOfObject = (Object[])paramArrayOfObject[b];
        if (arrayOfObject.length == 2) {
          setParams2Arg(b, arrayOfObject, paramPreparedStatement);
        } else if (arrayOfObject.length == 3) {
          setParams3Arg(b, arrayOfObject, paramPreparedStatement);
        } 
      } 
    } 
  }
  
  private void setParams2Arg(int paramInt, Object[] paramArrayOfObject, PreparedStatement paramPreparedStatement) throws SQLException {
    if (paramArrayOfObject[0] == null) {
      paramPreparedStatement.setNull(paramInt + 1, ((Integer)paramArrayOfObject[1]).intValue());
    } else if (paramArrayOfObject[0] instanceof RowId) {
      paramPreparedStatement.setRowId(paramInt + 1, (RowId)paramArrayOfObject[0]);
    } else if (paramArrayOfObject[0] instanceof NClob) {
      paramPreparedStatement.setNClob(paramInt + 1, (NClob)paramArrayOfObject[0]);
    } else if (paramArrayOfObject[0] instanceof Date) {
      if (paramArrayOfObject[1] instanceof Calendar) {
        paramPreparedStatement.setDate(paramInt + 1, (Date)paramArrayOfObject[0], (Calendar)paramArrayOfObject[1]);
      } else {
        throw TbError.newSQLException(-90846, paramArrayOfObject[1].toString());
      } 
    } else if (paramArrayOfObject[0] instanceof Time) {
      if (paramArrayOfObject[1] instanceof Calendar) {
        paramPreparedStatement.setTime(paramInt + 1, (Time)paramArrayOfObject[0], (Calendar)paramArrayOfObject[1]);
      } else {
        throw TbError.newSQLException(-90846, paramArrayOfObject[1].toString());
      } 
    } else if (paramArrayOfObject[0] instanceof Timestamp) {
      if (paramArrayOfObject[1] instanceof Calendar) {
        paramPreparedStatement.setTimestamp(paramInt + 1, (Timestamp)paramArrayOfObject[0], (Calendar)paramArrayOfObject[1]);
      } else {
        throw TbError.newSQLException(-90846, paramArrayOfObject[1].toString());
      } 
    } else if (paramArrayOfObject[0] instanceof Reader) {
      paramPreparedStatement.setCharacterStream(paramInt + 1, (Reader)paramArrayOfObject[0], ((Integer)paramArrayOfObject[1]).intValue());
    } else if (paramArrayOfObject[1] instanceof Integer) {
      if (((Integer)paramArrayOfObject[1]).intValue() == 0) {
        paramPreparedStatement.setNString(paramInt + 1, (String)paramArrayOfObject[0]);
      } else {
        paramPreparedStatement.setObject(paramInt + 1, paramArrayOfObject[0], ((Integer)paramArrayOfObject[1]).intValue());
      } 
    } 
  }
  
  private void setParams3Arg(int paramInt, Object[] paramArrayOfObject, PreparedStatement paramPreparedStatement) throws SQLException {
    if (paramArrayOfObject[0] == null) {
      paramPreparedStatement.setNull(paramInt + 1, ((Integer)paramArrayOfObject[1]).intValue(), (String)paramArrayOfObject[2]);
    } else {
      if (paramArrayOfObject[0] instanceof Reader) {
        switch (((Integer)paramArrayOfObject[2]).intValue()) {
          case 6:
            if (paramArrayOfObject[1] instanceof Integer) {
              paramPreparedStatement.setClob(paramInt + 1, (Reader)paramArrayOfObject[0], ((Integer)paramArrayOfObject[1]).intValue());
            } else {
              paramPreparedStatement.setClob(paramInt + 1, (Reader)paramArrayOfObject[0], ((Long)paramArrayOfObject[1]).longValue());
            } 
            return;
          case 3:
            if (paramArrayOfObject[1] instanceof Integer) {
              paramPreparedStatement.setCharacterStream(paramInt + 1, (Reader)paramArrayOfObject[0], ((Integer)paramArrayOfObject[1]).intValue());
            } else {
              paramPreparedStatement.setCharacterStream(paramInt + 1, (Reader)paramArrayOfObject[0], ((Long)paramArrayOfObject[1]).longValue());
            } 
            return;
          case 4:
            if (paramArrayOfObject[1] instanceof Integer) {
              paramPreparedStatement.setNCharacterStream(paramInt + 1, (Reader)paramArrayOfObject[0], ((Integer)paramArrayOfObject[1]).intValue());
            } else {
              paramPreparedStatement.setNCharacterStream(paramInt + 1, (Reader)paramArrayOfObject[0], ((Long)paramArrayOfObject[1]).longValue());
            } 
            return;
        } 
        throw TbError.newSQLException(-90846, ((Integer)paramArrayOfObject[2]).toString());
      } 
      if (paramArrayOfObject[0] instanceof InputStream) {
        switch (((Integer)paramArrayOfObject[2]).intValue()) {
          case 2:
            if (paramArrayOfObject[1] instanceof Integer) {
              paramPreparedStatement.setBinaryStream(paramInt + 1, (InputStream)paramArrayOfObject[0], ((Integer)paramArrayOfObject[1]).intValue());
            } else {
              paramPreparedStatement.setBinaryStream(paramInt + 1, (InputStream)paramArrayOfObject[0], ((Long)paramArrayOfObject[1]).longValue());
            } 
            return;
          case 1:
            paramPreparedStatement.setAsciiStream(paramInt + 1, (InputStream)paramArrayOfObject[0], ((Integer)paramArrayOfObject[1]).intValue());
            return;
          case 5:
            if (paramArrayOfObject[1] instanceof Integer) {
              paramPreparedStatement.setBlob(paramInt + 1, (InputStream)paramArrayOfObject[0], ((Integer)paramArrayOfObject[1]).intValue());
            } else {
              paramPreparedStatement.setBlob(paramInt + 1, (InputStream)paramArrayOfObject[0], ((Long)paramArrayOfObject[1]).longValue());
            } 
            return;
        } 
        throw TbError.newSQLException(-90846, ((Integer)paramArrayOfObject[2]).toString());
      } 
      if (paramArrayOfObject[1] instanceof Integer && paramArrayOfObject[2] instanceof Integer) {
        paramPreparedStatement.setObject(paramInt + 1, paramArrayOfObject[0], ((Integer)paramArrayOfObject[1]).intValue(), ((Integer)paramArrayOfObject[2]).intValue());
      } else {
        throw TbError.newSQLException(-90846, paramArrayOfObject[0].toString());
      } 
    } 
  }
  
  public int findColumn(String paramString) throws SQLException {
    return this.rs.findColumn(paramString);
  }
  
  public boolean first() throws SQLException {
    boolean bool = this.rs.first();
    if (bool)
      notifyCursorMoved(); 
    return bool;
  }
  
  public Array getArray(int paramInt) throws SQLException {
    return this.rs.getArray(paramInt);
  }
  
  public Array getArray(String paramString) throws SQLException {
    return this.rs.getArray(paramString);
  }
  
  public InputStream getAsciiStream(int paramInt) throws SQLException {
    return this.rs.getAsciiStream(paramInt);
  }
  
  public InputStream getAsciiStream(String paramString) throws SQLException {
    return this.rs.getAsciiStream(paramString);
  }
  
  public boolean getAutoCommit() throws SQLException {
    if (this.conn != null)
      return this.conn.getAutoCommit(); 
    throw TbError.newSQLException(-90827);
  }
  
  public BigDecimal getBigDecimal(int paramInt) throws SQLException {
    return this.rs.getBigDecimal(paramInt);
  }
  
  @Deprecated
  public BigDecimal getBigDecimal(int paramInt1, int paramInt2) throws SQLException {
    return this.rs.getBigDecimal(paramInt1, paramInt2);
  }
  
  public BigDecimal getBigDecimal(String paramString) throws SQLException {
    return this.rs.getBigDecimal(paramString);
  }
  
  @Deprecated
  public BigDecimal getBigDecimal(String paramString, int paramInt) throws SQLException {
    return this.rs.getBigDecimal(paramString, paramInt);
  }
  
  public InputStream getBinaryStream(int paramInt) throws SQLException {
    return this.rs.getBinaryStream(paramInt);
  }
  
  public InputStream getBinaryStream(String paramString) throws SQLException {
    return this.rs.getBinaryStream(paramString);
  }
  
  public Blob getBlob(int paramInt) throws SQLException {
    return this.rs.getBlob(paramInt);
  }
  
  public Blob getBlob(String paramString) throws SQLException {
    return this.rs.getBlob(paramString);
  }
  
  public boolean getBoolean(int paramInt) throws SQLException {
    return this.rs.getBoolean(paramInt);
  }
  
  public boolean getBoolean(String paramString) throws SQLException {
    return this.rs.getBoolean(paramString);
  }
  
  public byte getByte(int paramInt) throws SQLException {
    return this.rs.getByte(paramInt);
  }
  
  public byte getByte(String paramString) throws SQLException {
    return this.rs.getByte(paramString);
  }
  
  public byte[] getBytes(int paramInt) throws SQLException {
    return this.rs.getBytes(paramInt);
  }
  
  public byte[] getBytes(String paramString) throws SQLException {
    return this.rs.getBytes(paramString);
  }
  
  public Reader getCharacterStream(int paramInt) throws SQLException {
    return this.rs.getCharacterStream(paramInt);
  }
  
  public Reader getCharacterStream(String paramString) throws SQLException {
    return this.rs.getCharacterStream(paramString);
  }
  
  public Clob getClob(int paramInt) throws SQLException {
    return this.rs.getClob(paramInt);
  }
  
  public Clob getClob(String paramString) throws SQLException {
    return this.rs.getClob(paramString);
  }
  
  public int getConcurrency() throws SQLException {
    return super.getConcurrency();
  }
  
  private Connection getConnection(RowSet paramRowSet) throws SQLException {
    String str1 = paramRowSet.getUrl();
    String str2 = paramRowSet.getUsername();
    String str3 = paramRowSet.getPassword();
    String str4 = paramRowSet.getDataSourceName();
    if (this.conn != null && !this.conn.isClosed())
      return this.conn; 
    if (str4 != null && !str4.equals(""))
      try {
        InitialContext initialContext = new InitialContext();
        DataSource dataSource = (DataSource)initialContext.lookup(str4);
        return (str2 == null || str3 == null) ? dataSource.getConnection() : dataSource.getConnection(str2, str3);
      } catch (NamingException namingException) {
        throw TbError.newSQLException(-90828);
      }  
    if (str1 != null && !str1.equals("")) {
      if (!isInitializedDriver) {
        DriverManager.registerDriver((Driver)new TbDriver());
        isInitializedDriver = true;
      } 
      if (str2.equals("") || str3.equals(""))
        throw TbError.newSQLException(-90854); 
      return DriverManager.getConnection(str1, str2, str3);
    } 
    throw TbError.newSQLException(-90852);
  }
  
  public String getCursorName() throws SQLException {
    return this.rs.getCursorName();
  }
  
  public Date getDate(int paramInt) throws SQLException {
    return this.rs.getDate(paramInt);
  }
  
  public Date getDate(int paramInt, Calendar paramCalendar) throws SQLException {
    return this.rs.getDate(paramInt, paramCalendar);
  }
  
  public Date getDate(String paramString) throws SQLException {
    return this.rs.getDate(paramString);
  }
  
  public Date getDate(String paramString, Calendar paramCalendar) throws SQLException {
    return this.rs.getDate(paramString, paramCalendar);
  }
  
  public double getDouble(int paramInt) throws SQLException {
    return this.rs.getDouble(paramInt);
  }
  
  public double getDouble(String paramString) throws SQLException {
    return this.rs.getDouble(paramString);
  }
  
  public float getFloat(int paramInt) throws SQLException {
    return this.rs.getFloat(paramInt);
  }
  
  public float getFloat(String paramString) throws SQLException {
    return this.rs.getFloat(paramString);
  }
  
  public int getHoldability() throws SQLException {
    return this.rs.getHoldability();
  }
  
  public int getInt(int paramInt) throws SQLException {
    return this.rs.getInt(paramInt);
  }
  
  public int getInt(String paramString) throws SQLException {
    return this.rs.getInt(paramString);
  }
  
  public long getLong(int paramInt) throws SQLException {
    return this.rs.getLong(paramInt);
  }
  
  public long getLong(String paramString) throws SQLException {
    return this.rs.getLong(paramString);
  }
  
  public int[] getMatchColumnIndexes() throws SQLException {
    return super.getMatchColumnIndexes();
  }
  
  public String[] getMatchColumnNames() throws SQLException {
    return super.getMatchColumnNames();
  }
  
  public ResultSetMetaData getMetaData() throws SQLException {
    return new TbRowSetMetaData(this.rs.getMetaData());
  }
  
  public Reader getNCharacterStream(int paramInt) throws SQLException {
    return this.rs.getNCharacterStream(paramInt);
  }
  
  public Reader getNCharacterStream(String paramString) throws SQLException {
    return this.rs.getNCharacterStream(paramString);
  }
  
  public NClob getNClob(int paramInt) throws SQLException {
    return this.rs.getNClob(paramInt);
  }
  
  public NClob getNClob(String paramString) throws SQLException {
    return this.rs.getNClob(paramString);
  }
  
  public String getNString(int paramInt) throws SQLException {
    return this.rs.getNString(paramInt);
  }
  
  public String getNString(String paramString) throws SQLException {
    return this.rs.getNString(paramString);
  }
  
  public Object getObject(int paramInt) throws SQLException {
    return this.rs.getObject(paramInt);
  }
  
  public Object getObject(int paramInt, Map<String, Class<?>> paramMap) throws SQLException {
    return this.rs.getObject(paramInt, paramMap);
  }
  
  public Object getObject(String paramString) throws SQLException {
    return this.rs.getObject(paramString);
  }
  
  public Object getObject(String paramString, Map<String, Class<?>> paramMap) throws SQLException {
    return this.rs.getObject(paramString, paramMap);
  }
  
  public Ref getRef(int paramInt) throws SQLException {
    return this.rs.getRef(paramInt);
  }
  
  public Ref getRef(String paramString) throws SQLException {
    return this.rs.getRef(paramString);
  }
  
  public int getRow() throws SQLException {
    return this.rs.getRow();
  }
  
  public RowId getRowId(int paramInt) throws SQLException {
    return this.rs.getRowId(paramInt);
  }
  
  public RowId getRowId(String paramString) throws SQLException {
    return this.rs.getRowId(paramString);
  }
  
  public RowSetWarning getRowSetWarnings() throws SQLException {
    return null;
  }
  
  public short getShort(int paramInt) throws SQLException {
    return this.rs.getShort(paramInt);
  }
  
  public short getShort(String paramString) throws SQLException {
    return this.rs.getShort(paramString);
  }
  
  public SQLXML getSQLXML(int paramInt) throws SQLException {
    return this.rs.getSQLXML(paramInt);
  }
  
  public SQLXML getSQLXML(String paramString) throws SQLException {
    return this.rs.getSQLXML(paramString);
  }
  
  public Statement getStatement() throws SQLException {
    return this.rs.getStatement();
  }
  
  public String getString(int paramInt) throws SQLException {
    return this.rs.getString(paramInt);
  }
  
  public String getString(String paramString) throws SQLException {
    return this.rs.getString(paramString);
  }
  
  public Time getTime(int paramInt) throws SQLException {
    return this.rs.getTime(paramInt);
  }
  
  public Time getTime(int paramInt, Calendar paramCalendar) throws SQLException {
    return this.rs.getTime(paramInt, paramCalendar);
  }
  
  public Time getTime(String paramString) throws SQLException {
    return this.rs.getTime(paramString);
  }
  
  public Time getTime(String paramString, Calendar paramCalendar) throws SQLException {
    return this.rs.getTime(paramString, paramCalendar);
  }
  
  public Timestamp getTimestamp(int paramInt) throws SQLException {
    return this.rs.getTimestamp(paramInt);
  }
  
  public Timestamp getTimestamp(int paramInt, Calendar paramCalendar) throws SQLException {
    return this.rs.getTimestamp(paramInt, paramCalendar);
  }
  
  public Timestamp getTimestamp(String paramString) throws SQLException {
    return this.rs.getTimestamp(paramString);
  }
  
  public Timestamp getTimestamp(String paramString, Calendar paramCalendar) throws SQLException {
    return this.rs.getTimestamp(paramString, paramCalendar);
  }
  
  @Deprecated
  public InputStream getUnicodeStream(int paramInt) throws SQLException {
    return this.rs.getUnicodeStream(paramInt);
  }
  
  @Deprecated
  public InputStream getUnicodeStream(String paramString) throws SQLException {
    return this.rs.getUnicodeStream(paramString);
  }
  
  public URL getURL(int paramInt) throws SQLException {
    return this.rs.getURL(paramInt);
  }
  
  public URL getURL(String paramString) throws SQLException {
    return this.rs.getURL(paramString);
  }
  
  public void insertRow() throws SQLException {
    this.rs.insertRow();
    notifyRowChanged();
  }
  
  public boolean isAfterLast() throws SQLException {
    return this.rs.isAfterLast();
  }
  
  public boolean isBeforeFirst() throws SQLException {
    return this.rs.isBeforeFirst();
  }
  
  public boolean isClosed() throws SQLException {
    return super.isClosed();
  }
  
  public boolean isFirst() throws SQLException {
    return this.rs.isFirst();
  }
  
  public boolean isLast() throws SQLException {
    return this.rs.isLast();
  }
  
  public boolean isWrapperFor(Class<?> paramClass) throws SQLException {
    return paramClass.isInstance(this);
  }
  
  public boolean last() throws SQLException {
    boolean bool = this.rs.last();
    if (bool)
      notifyCursorMoved(); 
    return bool;
  }
  
  public void moveToCurrentRow() throws SQLException {
    this.rs.moveToCurrentRow();
  }
  
  public void moveToInsertRow() throws SQLException {
    this.rs.moveToInsertRow();
  }
  
  public boolean next() throws SQLException {
    boolean bool = this.rs.next();
    if (bool)
      notifyCursorMoved(); 
    return bool;
  }
  
  public boolean previous() throws SQLException {
    boolean bool = this.rs.previous();
    if (bool)
      notifyCursorMoved(); 
    return bool;
  }
  
  public void refreshRow() throws SQLException {
    this.rs.refreshRow();
  }
  
  public boolean relative(int paramInt) throws SQLException {
    boolean bool = this.rs.relative(paramInt);
    if (bool)
      notifyCursorMoved(); 
    return bool;
  }
  
  public void rollback() throws SQLException {
    if (this.conn != null) {
      this.conn.rollback();
    } else {
      throw TbError.newSQLException(-90827);
    } 
  }
  
  public void rollback(Savepoint paramSavepoint) throws SQLException {
    if (this.conn != null) {
      this.conn.rollback(paramSavepoint);
    } else {
      throw TbError.newSQLException(-90827);
    } 
  }
  
  public boolean rowDeleted() throws SQLException {
    return this.rs.rowDeleted();
  }
  
  public boolean rowInserted() throws SQLException {
    return this.rs.rowInserted();
  }
  
  public boolean rowUpdated() throws SQLException {
    return this.rs.rowUpdated();
  }
  
  public void setAutoCommit(boolean paramBoolean) throws SQLException {
    if (this.conn != null) {
      this.conn.setAutoCommit(paramBoolean);
    } else {
      throw TbError.newSQLException(-90827);
    } 
  }
  
  public void setMatchColumn(int paramInt) throws SQLException {
    super.setMatchColumn(paramInt);
  }
  
  public void setMatchColumn(int[] paramArrayOfint) throws SQLException {
    super.setMatchColumn(paramArrayOfint);
  }
  
  public void setMatchColumn(String paramString) throws SQLException {
    super.setMatchColumn(paramString);
  }
  
  public void setMatchColumn(String[] paramArrayOfString) throws SQLException {
    super.setMatchColumn(paramArrayOfString);
  }
  
  public void setShowDeleted(boolean paramBoolean) throws SQLException {
    if (paramBoolean)
      throw TbError.newSQLException(-90856); 
    super.setShowDeleted(paramBoolean);
  }
  
  public void unsetMatchColumn(int paramInt) throws SQLException {
    super.unsetMatchColumn(paramInt);
  }
  
  public void unsetMatchColumn(int[] paramArrayOfint) throws SQLException {
    super.unsetMatchColumn(paramArrayOfint);
  }
  
  public void unsetMatchColumn(String paramString) throws SQLException {
    super.unsetMatchColumn(paramString);
  }
  
  public void unsetMatchColumn(String[] paramArrayOfString) throws SQLException {
    super.unsetMatchColumn(paramArrayOfString);
  }
  
  public <T> T unwrap(Class<T> paramClass) throws SQLException {
    try {
      return paramClass.cast(this);
    } catch (ClassCastException classCastException) {
      throw TbError.newSQLException(-90657);
    } 
  }
  
  public void updateArray(int paramInt, Array paramArray) throws SQLException {
    this.rs.updateArray(paramInt, paramArray);
  }
  
  public void updateArray(String paramString, Array paramArray) throws SQLException {
    this.rs.updateArray(paramString, paramArray);
  }
  
  public void updateAsciiStream(int paramInt, InputStream paramInputStream) throws SQLException {
    this.rs.updateAsciiStream(paramInt, paramInputStream);
  }
  
  public void updateAsciiStream(int paramInt1, InputStream paramInputStream, int paramInt2) throws SQLException {
    this.rs.updateAsciiStream(paramInt1, paramInputStream, paramInt2);
  }
  
  public void updateAsciiStream(int paramInt, InputStream paramInputStream, long paramLong) throws SQLException {
    this.rs.updateAsciiStream(paramInt, paramInputStream, paramLong);
  }
  
  public void updateAsciiStream(String paramString, InputStream paramInputStream) throws SQLException {
    this.rs.updateAsciiStream(paramString, paramInputStream);
  }
  
  public void updateAsciiStream(String paramString, InputStream paramInputStream, int paramInt) throws SQLException {
    this.rs.updateAsciiStream(paramString, paramInputStream, paramInt);
  }
  
  public void updateAsciiStream(String paramString, InputStream paramInputStream, long paramLong) throws SQLException {
    this.rs.updateAsciiStream(paramString, paramInputStream, paramLong);
  }
  
  public void updateBigDecimal(int paramInt, BigDecimal paramBigDecimal) throws SQLException {
    this.rs.updateBigDecimal(paramInt, paramBigDecimal);
  }
  
  public void updateBigDecimal(String paramString, BigDecimal paramBigDecimal) throws SQLException {
    this.rs.updateBigDecimal(paramString, paramBigDecimal);
  }
  
  public void updateBinaryStream(int paramInt, InputStream paramInputStream) throws SQLException {
    this.rs.updateBinaryStream(paramInt, paramInputStream);
  }
  
  public void updateBinaryStream(int paramInt1, InputStream paramInputStream, int paramInt2) throws SQLException {
    this.rs.updateBinaryStream(paramInt1, paramInputStream, paramInt2);
  }
  
  public void updateBinaryStream(int paramInt, InputStream paramInputStream, long paramLong) throws SQLException {
    this.rs.updateBinaryStream(paramInt, paramInputStream, paramLong);
  }
  
  public void updateBinaryStream(String paramString, InputStream paramInputStream) throws SQLException {
    this.rs.updateBinaryStream(paramString, paramInputStream);
  }
  
  public void updateBinaryStream(String paramString, InputStream paramInputStream, int paramInt) throws SQLException {
    this.rs.updateBinaryStream(paramString, paramInputStream, paramInt);
  }
  
  public void updateBinaryStream(String paramString, InputStream paramInputStream, long paramLong) throws SQLException {
    this.rs.updateBinaryStream(paramString, paramInputStream, paramLong);
  }
  
  public void updateBlob(int paramInt, Blob paramBlob) throws SQLException {
    this.rs.updateBlob(paramInt, paramBlob);
  }
  
  public void updateBlob(int paramInt, InputStream paramInputStream) throws SQLException {
    this.rs.updateBlob(paramInt, paramInputStream);
  }
  
  public void updateBlob(int paramInt, InputStream paramInputStream, long paramLong) throws SQLException {
    this.rs.updateBlob(paramInt, paramInputStream, paramLong);
  }
  
  public void updateBlob(String paramString, Blob paramBlob) throws SQLException {
    this.rs.updateBlob(paramString, paramBlob);
  }
  
  public void updateBlob(String paramString, InputStream paramInputStream) throws SQLException {
    this.rs.updateBlob(paramString, paramInputStream);
  }
  
  public void updateBlob(String paramString, InputStream paramInputStream, long paramLong) throws SQLException {
    this.rs.updateBlob(paramString, paramInputStream, paramLong);
  }
  
  public void updateBoolean(int paramInt, boolean paramBoolean) throws SQLException {
    this.rs.updateBoolean(paramInt, paramBoolean);
  }
  
  public void updateBoolean(String paramString, boolean paramBoolean) throws SQLException {
    this.rs.updateBoolean(paramString, paramBoolean);
  }
  
  public void updateByte(int paramInt, byte paramByte) throws SQLException {
    this.rs.updateByte(paramInt, paramByte);
  }
  
  public void updateByte(String paramString, byte paramByte) throws SQLException {
    this.rs.updateByte(paramString, paramByte);
  }
  
  public void updateBytes(int paramInt, byte[] paramArrayOfbyte) throws SQLException {
    this.rs.updateBytes(paramInt, paramArrayOfbyte);
  }
  
  public void updateBytes(String paramString, byte[] paramArrayOfbyte) throws SQLException {
    this.rs.updateBytes(paramString, paramArrayOfbyte);
  }
  
  public void updateCharacterStream(int paramInt, Reader paramReader) throws SQLException {
    this.rs.updateCharacterStream(paramInt, paramReader);
  }
  
  public void updateCharacterStream(int paramInt1, Reader paramReader, int paramInt2) throws SQLException {
    this.rs.updateCharacterStream(paramInt1, paramReader, paramInt2);
  }
  
  public void updateCharacterStream(int paramInt, Reader paramReader, long paramLong) throws SQLException {
    this.rs.updateCharacterStream(paramInt, paramReader, paramLong);
  }
  
  public void updateCharacterStream(String paramString, Reader paramReader) throws SQLException {
    this.rs.updateCharacterStream(paramString, paramReader);
  }
  
  public void updateCharacterStream(String paramString, Reader paramReader, int paramInt) throws SQLException {
    this.rs.updateCharacterStream(paramString, paramReader, paramInt);
  }
  
  public void updateCharacterStream(String paramString, Reader paramReader, long paramLong) throws SQLException {
    this.rs.updateCharacterStream(paramString, paramReader, paramLong);
  }
  
  public void updateClob(int paramInt, Clob paramClob) throws SQLException {
    this.rs.updateClob(paramInt, paramClob);
  }
  
  public void updateClob(int paramInt, Reader paramReader) throws SQLException {
    this.rs.updateClob(paramInt, paramReader);
  }
  
  public void updateClob(int paramInt, Reader paramReader, long paramLong) throws SQLException {
    this.rs.updateClob(paramInt, paramReader, paramLong);
  }
  
  public void updateClob(String paramString, Clob paramClob) throws SQLException {
    this.rs.updateClob(paramString, paramClob);
  }
  
  public void updateClob(String paramString, Reader paramReader) throws SQLException {
    this.rs.updateClob(paramString, paramReader);
  }
  
  public void updateClob(String paramString, Reader paramReader, long paramLong) throws SQLException {
    this.rs.updateClob(paramString, paramReader, paramLong);
  }
  
  public void updateDate(int paramInt, Date paramDate) throws SQLException {
    this.rs.updateDate(paramInt, paramDate);
  }
  
  public void updateDate(String paramString, Date paramDate) throws SQLException {
    this.rs.updateDate(paramString, paramDate);
  }
  
  public void updateDouble(int paramInt, double paramDouble) throws SQLException {
    this.rs.updateDouble(paramInt, paramDouble);
  }
  
  public void updateDouble(String paramString, double paramDouble) throws SQLException {
    this.rs.updateDouble(paramString, paramDouble);
  }
  
  public void updateFloat(int paramInt, float paramFloat) throws SQLException {
    this.rs.updateFloat(paramInt, paramFloat);
  }
  
  public void updateFloat(String paramString, float paramFloat) throws SQLException {
    this.rs.updateFloat(paramString, paramFloat);
  }
  
  public void updateInt(int paramInt1, int paramInt2) throws SQLException {
    this.rs.updateInt(paramInt1, paramInt2);
  }
  
  public void updateInt(String paramString, int paramInt) throws SQLException {
    this.rs.updateInt(paramString, paramInt);
  }
  
  public void updateLong(int paramInt, long paramLong) throws SQLException {
    this.rs.updateLong(paramInt, paramLong);
  }
  
  public void updateLong(String paramString, long paramLong) throws SQLException {
    this.rs.updateLong(paramString, paramLong);
  }
  
  public void updateNCharacterStream(int paramInt, Reader paramReader) throws SQLException {
    this.rs.updateNCharacterStream(paramInt, paramReader);
  }
  
  public void updateNCharacterStream(int paramInt, Reader paramReader, long paramLong) throws SQLException {
    this.rs.updateNCharacterStream(paramInt, paramReader, paramLong);
  }
  
  public void updateNCharacterStream(String paramString, Reader paramReader) throws SQLException {
    this.rs.updateNCharacterStream(paramString, paramReader);
  }
  
  public void updateNCharacterStream(String paramString, Reader paramReader, long paramLong) throws SQLException {
    this.rs.updateNCharacterStream(paramString, paramReader, paramLong);
  }
  
  public void updateNClob(int paramInt, NClob paramNClob) throws SQLException {
    this.rs.updateNClob(paramInt, paramNClob);
  }
  
  public void updateNClob(int paramInt, Reader paramReader) throws SQLException {
    this.rs.updateNClob(paramInt, paramReader);
  }
  
  public void updateNClob(int paramInt, Reader paramReader, long paramLong) throws SQLException {
    this.rs.updateNClob(paramInt, paramReader, paramLong);
  }
  
  public void updateNClob(String paramString, NClob paramNClob) throws SQLException {
    this.rs.updateNClob(paramString, paramNClob);
  }
  
  public void updateNClob(String paramString, Reader paramReader) throws SQLException {
    this.rs.updateNClob(paramString, paramReader);
  }

  @Override
  public <T> T getObject(int columnIndex, Class<T> type) throws SQLException {
    return null;
  }

  @Override
  public <T> T getObject(String columnLabel, Class<T> type) throws SQLException {
    return null;
  }

  public void updateNClob(String paramString, Reader paramReader, long paramLong) throws SQLException {
    this.rs.updateNClob(paramString, paramReader, paramLong);
  }
  
  public void updateNString(int paramInt, String paramString) throws SQLException {
    this.rs.updateNString(paramInt, paramString);
  }
  
  public void updateNString(String paramString1, String paramString2) throws SQLException {
    this.rs.updateNString(paramString1, paramString2);
  }
  
  public void updateNull(int paramInt) throws SQLException {
    this.rs.updateNull(paramInt);
  }
  
  public void updateNull(String paramString) throws SQLException {
    this.rs.updateNull(paramString);
  }
  
  public void updateObject(int paramInt, Object paramObject) throws SQLException {
    this.rs.updateObject(paramInt, paramObject);
  }
  
  public void updateObject(int paramInt1, Object paramObject, int paramInt2) throws SQLException {
    this.rs.updateObject(paramInt1, paramObject, paramInt2);
  }
  
  public void updateObject(String paramString, Object paramObject) throws SQLException {
    this.rs.updateObject(paramString, paramObject);
  }
  
  public void updateObject(String paramString, Object paramObject, int paramInt) throws SQLException {
    this.rs.updateObject(paramString, paramObject, paramInt);
  }
  
  public void updateRef(int paramInt, Ref paramRef) throws SQLException {
    this.rs.updateRef(paramInt, paramRef);
  }
  
  public void updateRef(String paramString, Ref paramRef) throws SQLException {
    this.rs.updateRef(paramString, paramRef);
  }
  
  public void updateRow() throws SQLException {
    this.rs.updateRow();
  }
  
  public void updateRowId(int paramInt, RowId paramRowId) throws SQLException {
    this.rs.updateRowId(paramInt, paramRowId);
  }
  
  public void updateRowId(String paramString, RowId paramRowId) throws SQLException {
    this.rs.updateRowId(paramString, paramRowId);
  }
  
  public void updateShort(int paramInt, short paramShort) throws SQLException {
    this.rs.updateShort(paramInt, paramShort);
  }
  
  public void updateShort(String paramString, short paramShort) throws SQLException {
    this.rs.updateShort(paramString, paramShort);
  }
  
  public void updateSQLXML(int paramInt, SQLXML paramSQLXML) throws SQLException {
    this.rs.updateSQLXML(paramInt, paramSQLXML);
  }
  
  public void updateSQLXML(String paramString, SQLXML paramSQLXML) throws SQLException {
    this.rs.updateSQLXML(paramString, paramSQLXML);
  }
  
  public void updateString(int paramInt, String paramString) throws SQLException {
    this.rs.updateString(paramInt, paramString);
  }
  
  public void updateString(String paramString1, String paramString2) throws SQLException {
    this.rs.updateString(paramString1, paramString2);
  }
  
  public void updateTime(int paramInt, Time paramTime) throws SQLException {
    this.rs.updateTime(paramInt, paramTime);
  }
  
  public void updateTime(String paramString, Time paramTime) throws SQLException {
    this.rs.updateTime(paramString, paramTime);
  }
  
  public void updateTimestamp(int paramInt, Timestamp paramTimestamp) throws SQLException {
    this.rs.updateTimestamp(paramInt, paramTimestamp);
  }
  
  public void updateTimestamp(String paramString, Timestamp paramTimestamp) throws SQLException {
    this.rs.updateTimestamp(paramString, paramTimestamp);
  }
  
  public boolean wasNull() throws SQLException {
    return this.rs.wasNull();
  }
}


/* Location:              C:\TmaxData\tibero6\client\lib\jar\tibero6-jdbc.jar!\com\tmax\tibero\jdbc\rowset\TbJdbcRowSet.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */