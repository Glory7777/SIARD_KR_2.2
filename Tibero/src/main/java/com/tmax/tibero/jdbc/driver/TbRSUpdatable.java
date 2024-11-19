package com.tmax.tibero.jdbc.driver;

import com.tmax.tibero.Debug;
import com.tmax.tibero.jdbc.TbBlob;
import com.tmax.tibero.jdbc.TbClob;
import com.tmax.tibero.jdbc.TbClobBase;
import com.tmax.tibero.jdbc.TbNClob;
import com.tmax.tibero.jdbc.TbRef;
import com.tmax.tibero.jdbc.data.Column;
import com.tmax.tibero.jdbc.data.ParamContainer;
import com.tmax.tibero.jdbc.data.Row;
import com.tmax.tibero.jdbc.data.RsetType;
import com.tmax.tibero.jdbc.data.TbDate;
import com.tmax.tibero.jdbc.data.TbRAW;
import com.tmax.tibero.jdbc.data.TbTimestamp;
import com.tmax.tibero.jdbc.err.TbError;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.NClob;
import java.sql.Ref;
import java.sql.ResultSetMetaData;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLXML;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.ZoneOffset;
import java.util.Calendar;
import java.util.Map;
import java.util.TimeZone;

public class TbRSUpdatable extends TbResultSet {
  private TbResultSetBase rset = null;
  
  private boolean onInserting = false;
  
  private boolean onUpdating = false;
  
  private Object[] columnBuffer = null;
  
  private int[] columnLength = null;
  
  private boolean[] hasChanged = null;
  
  private int columnCount = 0;
  
  private int beginColumnIndex = 0;
  
  private boolean lastColumnWasNull = false;
  
  protected TbRSUpdatable(TbResultSetBase paramTbResultSetBase, RsetType paramRsetType) throws SQLException {
    super(paramRsetType);
    if (paramTbResultSetBase == null)
      throw TbError.newSQLException(-90607); 
    this.rset = paramTbResultSetBase;
    this.beginColumnIndex = paramTbResultSetBase.getBeginColumnIndex();
    this.columnCount = paramTbResultSetBase.getColumnCount() + this.beginColumnIndex;
    this.rsetType = paramRsetType;
  }
  
  public synchronized boolean absolute(int paramInt) throws SQLException {
    cancelChanges();
    return this.rset.absolute(paramInt);
  }
  
  public synchronized void afterLast() throws SQLException {
    cancelChanges();
    this.rset.afterLast();
  }
  
  public synchronized void beforeFirst() throws SQLException {
    cancelChanges();
    this.rset.beforeFirst();
  }
  
  private void bindInsertRowChangedData(TbPreparedStatement paramTbPreparedStatement) throws SQLException {
    int i = this.hasChanged.length;
    int j = 1;
    if (this.rset.stmt instanceof ParamContainer)
      j = ((ParamContainer)this.rset.stmt).getParameterCnt() + 1; 
    for (int k = this.beginColumnIndex; k < i; k++) {
      if (this.hasChanged[k]) {
        Object object = this.columnBuffer[k];
        if (object instanceof Clob) {
          paramTbPreparedStatement.setClob(j, (Clob)object);
        } else if (object instanceof Blob) {
          paramTbPreparedStatement.setBlob(j, (Blob)object);
        } else if (object instanceof Reader) {
          paramTbPreparedStatement.setCharacterStream(j, (Reader)object, this.columnLength[k]);
        } else if (object instanceof InputStream) {
          paramTbPreparedStatement.setBinaryStream(j, (InputStream)object, this.columnLength[k]);
        } else if (object instanceof com.tmax.tibero.jdbc.TbArray || object instanceof com.tmax.tibero.jdbc.TbStruct) {
          paramTbPreparedStatement.setObject(j, object);
        } else if (object instanceof byte[]) {
          int m = this.rset.getColumnDataType(k - this.beginColumnIndex + 1);
          if (this.columnLength[k] == 0) {
            paramTbPreparedStatement.setBytes(j, m, null);
          } else {
            paramTbPreparedStatement.setBytes(j, m, (byte[])object);
          } 
        } 
        j++;
      } else {
        paramTbPreparedStatement.setBytes(j, this.rset.getColumnDataType(k - this.beginColumnIndex + 1), null);
        j++;
      } 
    } 
  }
  
  private void bindUpdateRowChangedData(TbPreparedStatement paramTbPreparedStatement) throws SQLException {
    int i = this.hasChanged.length;
    int j = 1;
    if (this.rset.stmt instanceof ParamContainer)
      j = ((ParamContainer)this.rset.stmt).getParameterCnt() + 1; 
    for (int k = this.beginColumnIndex; k < i; k++) {
      if (this.hasChanged[k]) {
        Object object = this.columnBuffer[k];
        if (object instanceof Clob) {
          paramTbPreparedStatement.setClob(j, (Clob)object);
        } else if (object instanceof Blob) {
          paramTbPreparedStatement.setBlob(j, (Blob)object);
        } else if (object instanceof Reader) {
          paramTbPreparedStatement.setCharacterStream(j, (Reader)object, this.columnLength[k]);
        } else if (object instanceof InputStream) {
          paramTbPreparedStatement.setBinaryStream(j, (InputStream)object, this.columnLength[k]);
        } else if (object instanceof com.tmax.tibero.jdbc.TbArray || object instanceof com.tmax.tibero.jdbc.TbStruct) {
          paramTbPreparedStatement.setObject(j, object);
        } else if (object instanceof byte[]) {
          int m = this.rset.getColumnDataType(k - this.beginColumnIndex + 1);
          if (this.columnLength[k] == 0) {
            paramTbPreparedStatement.setBytes(j, m, null);
          } else {
            paramTbPreparedStatement.setBytes(j, m, (byte[])object);
          } 
        } 
        j++;
      } 
    } 
    paramTbPreparedStatement.setBytes(j, 3, this.rset.getColumnRawData(0));
  }
  
  public void buildRowTable(int paramInt, byte[] paramArrayOfbyte) throws SQLException {
    this.rset.buildRowTable(paramInt, paramArrayOfbyte);
  }
  
  protected void cancelChanges() throws SQLException {
    if (this.onInserting)
      cancelRowInserts(); 
    if (this.onUpdating)
      cancelRowUpdates(); 
  }
  
  protected void cancelRowInserts() throws SQLException {
    this.onInserting = false;
    resetBuffer();
  }
  
  public synchronized void cancelRowUpdates() throws SQLException {
    this.onUpdating = false;
    resetBuffer();
  }
  
  void checkColumnIndex(int paramInt) throws SQLException {
    int i = getColumnCount();
    if (i < 0)
      throw TbError.newSQLException(-90607); 
    if (paramInt <= 0 || paramInt > i)
      throw TbError.newSQLException(-90609); 
  }
  
  protected void checkUpdateCursorPosition() throws SQLException {
    if (isBeforeFirst() || isAfterLast())
      throw TbError.newSQLException(-90624); 
    this.onUpdating = true;
  }
  
  public synchronized void close() throws SQLException {
    try {
      if (this.rset != null)
        this.rset.close(); 
    } finally {
      reset();
    } 
  }
  
  public synchronized void deleteRow() throws SQLException {
    if (this.onInserting)
      throw TbError.newSQLException(-590773); 
    TbPreparedStatement tbPreparedStatement = null;
    try {
      tbPreparedStatement = getDeleteRowStatement();
      int i = tbPreparedStatement.executeUpdate();
      if (i <= 0)
        throw TbError.newSQLException(-590777, i); 
      if (i > 1)
        throw TbError.newSQLException(-590778, i); 
      this.rset.removeCurrentRow();
    } finally {
      if (tbPreparedStatement != null)
        try {
          tbPreparedStatement.close();
        } catch (Exception exception) {} 
    } 
  }
  
  public synchronized int findColumn(String paramString) throws SQLException {
    return this.rset.findColumn(paramString);
  }
  
  public synchronized boolean first() throws SQLException {
    cancelChanges();
    return this.rset.first();
  }
  
  private int getRevisedColumnIndex(int paramInt) throws SQLException {
    int i = paramInt + this.beginColumnIndex - 1;
    checkColumnIndex(paramInt);
    return i;
  }
  
  public Array getArray(int paramInt) throws SQLException {
    checkColumnIndex(paramInt);
    if (setLastColumnWasNull(paramInt))
      return null; 
    if (hasBufferChanged(paramInt)) {
      this.rset.checkRsetAndConnClosed();
      Object object = this.columnBuffer[getRevisedColumnIndex(paramInt)];
      return (Array)object;
    } 
    return this.rset.getArray(paramInt);
  }
  
  public Array getArray(String paramString) throws SQLException {
    return getArray(findColumn(paramString));
  }
  
  public synchronized InputStream getAsciiStream(int paramInt) throws SQLException {
    return getBinaryStream(paramInt);
  }
  
  public InputStream getAsciiStream(String paramString) throws SQLException {
    return getBinaryStream(findColumn(paramString));
  }
  
  public BigDecimal getBigDecimal(int paramInt) throws SQLException {
    return getBigDecimal(paramInt, 0);
  }
  
  @Deprecated
  public synchronized BigDecimal getBigDecimal(int paramInt1, int paramInt2) throws SQLException {
    checkColumnIndex(paramInt1);
    return setLastColumnWasNull(paramInt1) ? null : (hasBufferChanged(paramInt1) ? this.rset.typeConverter.toBigDecimal(this.columnBuffer[getRevisedColumnIndex(paramInt1)], 0, this.columnLength[getRevisedColumnIndex(paramInt1)], getColumnDataType(paramInt1)) : this.rset.getBigDecimal(paramInt1));
  }
  
  public BigDecimal getBigDecimal(String paramString) throws SQLException {
    return getBigDecimal(findColumn(paramString));
  }
  
  @Deprecated
  public BigDecimal getBigDecimal(String paramString, int paramInt) throws SQLException {
    return getBigDecimal(findColumn(paramString), paramInt);
  }
  
  public synchronized InputStream getBinaryStream(int paramInt) throws SQLException {
    checkColumnIndex(paramInt);
    if (setLastColumnWasNull(paramInt))
      return null; 
    if (hasBufferChanged(paramInt)) {
      Object object = this.columnBuffer[getRevisedColumnIndex(paramInt)];
      if (object instanceof InputStream)
        return (InputStream)object; 
      if (object instanceof Blob)
        return ((TbBlob)object).getBinaryStream(); 
      throw TbError.newSQLException(-590705, object.toString());
    } 
    return this.rset.getBinaryStream(paramInt);
  }
  
  public InputStream getBinaryStream(String paramString) throws SQLException {
    return getBinaryStream(findColumn(paramString));
  }
  
  public Blob getBlob(int paramInt) throws SQLException {
    checkColumnIndex(paramInt);
    if (setLastColumnWasNull(paramInt))
      return null; 
    if (hasBufferChanged(paramInt)) {
      Object object = this.columnBuffer[getRevisedColumnIndex(paramInt)];
      if (object instanceof Blob)
        return (Blob)object; 
      throw TbError.newSQLException(-590705, object.toString());
    } 
    return this.rset.getBlob(paramInt);
  }
  
  public Blob getBlob(String paramString) throws SQLException {
    return getBlob(findColumn(paramString));
  }
  
  public synchronized boolean getBoolean(int paramInt) throws SQLException {
    checkColumnIndex(paramInt);
    return setLastColumnWasNull(paramInt) ? false : (hasBufferChanged(paramInt) ? this.rset.typeConverter.toBoolean(this.columnBuffer[getRevisedColumnIndex(paramInt)], 0, this.columnLength[getRevisedColumnIndex(paramInt)], getColumnDataType(paramInt)) : this.rset.getBoolean(paramInt));
  }
  
  public boolean getBoolean(String paramString) throws SQLException {
    return getBoolean(findColumn(paramString));
  }
  
  public synchronized byte getByte(int paramInt) throws SQLException {
    checkColumnIndex(paramInt);
    return setLastColumnWasNull(paramInt) ? 0 : (hasBufferChanged(paramInt) ? this.rset.typeConverter.toByte(this.columnBuffer[getRevisedColumnIndex(paramInt)], 0, this.columnLength[getRevisedColumnIndex(paramInt)], getColumnDataType(paramInt)) : this.rset.getByte(paramInt));
  }
  
  public byte getByte(String paramString) throws SQLException {
    return getByte(findColumn(paramString));
  }
  
  public synchronized byte[] getBytes(int paramInt) throws SQLException {
    checkColumnIndex(paramInt);
    return setLastColumnWasNull(paramInt) ? null : (hasBufferChanged(paramInt) ? this.rset.typeConverter.toBytes(this.columnBuffer[getRevisedColumnIndex(paramInt)], 0, this.columnLength[getRevisedColumnIndex(paramInt)], getColumnDataType(paramInt), false) : this.rset.getBytes(paramInt));
  }
  
  public byte[] getBytes(String paramString) throws SQLException {
    return getBytes(findColumn(paramString));
  }
  
  public TbRAW getRAW(int paramInt) throws SQLException {
    return new TbRAW(getBytes(paramInt));
  }
  
  public TbRAW getRAW(String paramString) throws SQLException {
    return new TbRAW(getBytes(paramString));
  }
  
  public synchronized Reader getCharacterStream(int paramInt) throws SQLException {
    checkColumnIndex(paramInt);
    if (setLastColumnWasNull(paramInt))
      return null; 
    if (hasBufferChanged(paramInt)) {
      Object object = this.columnBuffer[getRevisedColumnIndex(paramInt)];
      if (object instanceof Reader)
        return (Reader)object; 
      if (object instanceof Clob)
        return ((TbClob)object).getCharacterStream(); 
      throw TbError.newSQLException(-590705, object.toString());
    } 
    return this.rset.getCharacterStream(paramInt);
  }
  
  public Reader getCharacterStream(String paramString) throws SQLException {
    return getCharacterStream(findColumn(paramString));
  }
  
  public Clob getClob(int paramInt) throws SQLException {
    checkColumnIndex(paramInt);
    if (setLastColumnWasNull(paramInt))
      return null; 
    if (hasBufferChanged(paramInt)) {
      Object object = this.columnBuffer[getRevisedColumnIndex(paramInt)];
      if (object instanceof Clob)
        return (Clob)object; 
      throw TbError.newSQLException(-590705, object.toString());
    } 
    return this.rset.getClob(paramInt);
  }
  
  public Clob getClob(String paramString) throws SQLException {
    return getClob(findColumn(paramString));
  }
  
  public Column[] getCols() {
    return this.rset.getCols();
  }
  
  public int getColumnCount() {
    return this.rset.getColumnCount();
  }
  
  protected int getColumnDataType(int paramInt) throws SQLException {
    return this.rset.getColumnDataType(paramInt);
  }
  
  protected int getColumnMaxLength(int paramInt) throws SQLException {
    return this.rset.getColumnMaxLength(paramInt);
  }
  
  protected String getColumnName(int paramInt) throws SQLException {
    return this.rset.getColumnName(paramInt);
  }
  
  protected boolean getColumnNullable(int paramInt) throws SQLException {
    return this.rset.getColumnNullable(paramInt);
  }
  
  protected int getColumnPrecision(int paramInt) throws SQLException {
    return this.rset.getColumnPrecision(paramInt);
  }
  
  protected int getColumnScale(int paramInt) throws SQLException {
    return this.rset.getColumnScale(paramInt);
  }
  
  protected int getColumnSqlType(int paramInt) throws SQLException {
    return this.rset.getColumnSqlType(paramInt);
  }
  
  protected Row getCurrentRow() throws SQLException {
    return this.rset.getCurrentRow();
  }
  
  private int getColumnOffset(Row paramRow, int paramInt) {
    return paramRow.getColumnOffset(paramInt + this.beginColumnIndex);
  }
  
  private int getColumnLength(Row paramRow, int paramInt) {
    return paramRow.getColumnLength(paramInt + this.beginColumnIndex);
  }
  
  public String getCursorName() throws SQLException {
    throw TbError.newSQLException(-90201);
  }
  
  public synchronized Date getDate(int paramInt) throws SQLException {
    return getDateInternal(paramInt);
  }
  
  public Date getDate(int paramInt, Calendar paramCalendar) throws SQLException {
    Date date = getDateInternal(paramInt);
    if (paramCalendar != null) {
      paramCalendar.setTime(date);
      date = (Date)paramCalendar.getTime();
    } 
    return date;
  }
  
  public Date getDate(String paramString) throws SQLException {
    return getDate(findColumn(paramString));
  }
  
  public Date getDate(String paramString, Calendar paramCalendar) throws SQLException {
    return getDate(findColumn(paramString), paramCalendar);
  }
  
  private Date getDateInternal(int paramInt) throws SQLException {
    checkColumnIndex(paramInt);
    return setLastColumnWasNull(paramInt) ? null : (hasBufferChanged(paramInt) ? this.rset.typeConverter.toDate(this.columnBuffer[getRevisedColumnIndex(paramInt)], 0, this.columnLength[getRevisedColumnIndex(paramInt)], getColumnDataType(paramInt)) : this.rset.getDate(paramInt));
  }
  
  public TbDate getTbDate(int paramInt) throws SQLException {
    return getTbDateInternal(paramInt);
  }
  
  private TbDate getTbDateInternal(int paramInt) throws SQLException {
    checkColumnIndex(paramInt);
    if (setLastColumnWasNull(paramInt))
      return null; 
    if (hasBufferChanged(paramInt)) {
      Row row = getCurrentRow();
      return this.rset.typeConverter.toTbDate(row.getRowChunk(paramInt + this.beginColumnIndex), getColumnOffset(row, paramInt), getColumnLength(row, paramInt), getColumnDataType(paramInt));
    } 
    return this.rset.getTbDate(paramInt);
  }
  
  private TbPreparedStatement getDeleteRowStatement() throws SQLException {
    StringBuffer stringBuffer = new StringBuffer();
    stringBuffer.append("DELETE FROM ( ");
    stringBuffer.append(this.rset.stmt.getOriginalSql());
    stringBuffer.append(" ) WHERE ROWIDTOCHAR(ROWID) = ? ");
    TbPreparedStatement tbPreparedStatement = new TbPreparedStatement(this.rset.stmt.conn, stringBuffer.toString());
    if (this.rset.stmt instanceof ParamContainer) {
      tbPreparedStatement.impl().copyBindParamInfo((ParamContainer)this.rset.stmt);
      tbPreparedStatement.setBytes(((ParamContainer)this.rset.stmt).getParameterCnt() + 1, 3, this.rset.getColumnRawData(0));
    } else {
      tbPreparedStatement.setBytes(1, 3, this.rset.getColumnRawData(0));
    } 
    return tbPreparedStatement;
  }
  
  public synchronized double getDouble(int paramInt) throws SQLException {
    checkColumnIndex(paramInt);
    return setLastColumnWasNull(paramInt) ? 0.0D : (hasBufferChanged(paramInt) ? this.rset.typeConverter.toDouble(this.columnBuffer[getRevisedColumnIndex(paramInt)], 0, this.columnLength[getRevisedColumnIndex(paramInt)], getColumnDataType(paramInt)) : this.rset.getDouble(paramInt));
  }
  
  public double getDouble(String paramString) throws SQLException {
    return getDouble(findColumn(paramString));
  }
  
  public synchronized int getFetchDirection() throws SQLException {
    return this.rset.getFetchDirection();
  }
  
  public synchronized int getFetchSize() throws SQLException {
    return this.rset.getFetchSize();
  }
  
  public synchronized float getFloat(int paramInt) throws SQLException {
    checkColumnIndex(paramInt);
    return setLastColumnWasNull(paramInt) ? 0.0F : (hasBufferChanged(paramInt) ? this.rset.typeConverter.toFloat(this.columnBuffer[getRevisedColumnIndex(paramInt)], 0, this.columnLength[getRevisedColumnIndex(paramInt)], getColumnDataType(paramInt)) : this.rset.getFloat(paramInt));
  }
  
  public float getFloat(String paramString) throws SQLException {
    return getFloat(findColumn(paramString));
  }
  
  private TbPreparedStatement getInsertRowStatement() throws SQLException {
    StringBuffer stringBuffer = new StringBuffer();
    stringBuffer.append("INSERT INTO ( ");
    stringBuffer.append(this.rset.stmt.getOriginalSql());
    stringBuffer.append(" ) VALUES ( ");
    int i = this.hasChanged.length;
    for (int j = this.beginColumnIndex; j < i; j++) {
      if (j != this.beginColumnIndex)
        stringBuffer.append(" , "); 
      stringBuffer.append("?");
    } 
    stringBuffer.append(" ) ");
    TbPreparedStatement tbPreparedStatement = new TbPreparedStatement(this.rset.stmt.conn, stringBuffer.toString());
    if (this.rset.stmt instanceof ParamContainer)
      tbPreparedStatement.impl().copyBindParamInfo((ParamContainer)this.rset.stmt); 
    return tbPreparedStatement;
  }
  
  public synchronized int getInt(int paramInt) throws SQLException {
    checkColumnIndex(paramInt);
    return setLastColumnWasNull(paramInt) ? 0 : (hasBufferChanged(paramInt) ? this.rset.typeConverter.toInt(this.columnBuffer[getRevisedColumnIndex(paramInt)], 0, this.columnLength[getRevisedColumnIndex(paramInt)], getColumnDataType(paramInt)) : this.rset.getInt(paramInt));
  }
  
  public int getInt(String paramString) throws SQLException {
    return getInt(findColumn(paramString));
  }
  
  public synchronized long getLong(int paramInt) throws SQLException {
    checkColumnIndex(paramInt);
    return setLastColumnWasNull(paramInt) ? 0L : (hasBufferChanged(paramInt) ? this.rset.typeConverter.toLong(this.columnBuffer[getRevisedColumnIndex(paramInt)], 0, this.columnLength[getRevisedColumnIndex(paramInt)], getColumnDataType(paramInt)) : this.rset.getLong(paramInt));
  }
  
  public long getLong(String paramString) throws SQLException {
    return getLong(findColumn(paramString));
  }
  
  public ResultSetMetaData getMetaData() throws SQLException {
    return (ResultSetMetaData)new TbResultSetMetaData(this.rset.cols, this.rset.beginColumnIndex);
  }
  
  public Reader getNCharacterStream(int paramInt) throws SQLException {
    checkColumnIndex(paramInt);
    if (setLastColumnWasNull(paramInt))
      return null; 
    if (hasBufferChanged(paramInt)) {
      Object object = this.columnBuffer[getRevisedColumnIndex(paramInt)];
      if (object instanceof Reader)
        return (Reader)object; 
      if (object instanceof NClob)
        return ((TbNClob)object).getCharacterStream(); 
      throw TbError.newSQLException(-590705, object.toString());
    } 
    return this.rset.getNCharacterStream(paramInt);
  }
  
  public Reader getNCharacterStream(String paramString) throws SQLException {
    return getNCharacterStream(findColumn(paramString));
  }
  
  public NClob getNClob(int paramInt) throws SQLException {
    checkColumnIndex(paramInt);
    if (setLastColumnWasNull(paramInt))
      return null; 
    if (hasBufferChanged(paramInt)) {
      Object object = this.columnBuffer[getRevisedColumnIndex(paramInt)];
      if (object instanceof NClob)
        return (NClob)object; 
      throw TbError.newSQLException(-590705, object.toString());
    } 
    return this.rset.getNClob(paramInt);
  }
  
  public NClob getNClob(String paramString) throws SQLException {
    return getNClob(findColumn(paramString));
  }
  
  public String getNString(int paramInt) throws SQLException {
    checkColumnIndex(paramInt);
    if (setLastColumnWasNull(paramInt))
      return null; 
    if (hasBufferChanged(paramInt)) {
      Object object = this.columnBuffer[getRevisedColumnIndex(paramInt)];
      if (object instanceof TbNClob) {
        long l = ((TbNClob)object).length();
        return ((TbNClob)object).getSubString(1L, (int)l);
      } 
      return this.rset.typeConverter.toString(object, 0, this.columnLength[getRevisedColumnIndex(paramInt)], getColumnDataType(paramInt), getColumnPrecision(paramInt), getColumnScale(paramInt), !this.rset.getRsetType().isScrollable());
    } 
    return this.rset.getNString(paramInt);
  }
  
  public String getNString(String paramString) throws SQLException {
    return getNString(findColumn(paramString));
  }
  
  public synchronized Object getObject(int paramInt) throws SQLException {
    checkColumnIndex(paramInt);
    if (setLastColumnWasNull(paramInt))
      return null; 
    if (hasBufferChanged(paramInt)) {
      this.rset.checkRsetAndConnClosed();
      Object object = this.columnBuffer[getRevisedColumnIndex(paramInt)];
      return (object instanceof byte[]) ? this.rset.typeConverter.toObject(object, 0, this.columnLength[getRevisedColumnIndex(paramInt)], getColumnDataType(paramInt), getColumnSqlType(paramInt), getColumnPrecision(paramInt), getColumnScale(paramInt), !this.rset.getRsetType().isScrollable(), null, null, this.rset.stmt.conn.typeMap, this.rset.getStatement()) : object;
    } 
    return this.rset.getObject(paramInt);
  }
  
  public Object getObject(int paramInt, Map<String, Class<?>> paramMap) throws SQLException {
    throw TbError.newSQLException(-90201);
  }
  
  public Object getObject(String paramString) throws SQLException {
    return getObject(findColumn(paramString));
  }
  
  public Object getObject(String paramString, Map<String, Class<?>> paramMap) throws SQLException {
    return getObject(findColumn(paramString), paramMap);
  }
  
  public Ref getRef(int paramInt) throws SQLException {
    checkColumnIndex(paramInt);
    return setLastColumnWasNull(paramInt) ? null : (hasBufferChanged(paramInt) ? this.rset.typeConverter.toRef(this.columnBuffer[getRevisedColumnIndex(paramInt)], 0, this.columnLength[getRevisedColumnIndex(paramInt)], getColumnDataType(paramInt)) : this.rset.getRef(paramInt));
  }
  
  public Ref getRef(String paramString) throws SQLException {
    return getRef(findColumn(paramString));
  }
  
  public synchronized int getRow() throws SQLException {
    return this.rset.getRow();
  }
  
  public byte[] getRowChunk(int paramInt) {
    return this.rset.getRowChunk(paramInt);
  }
  
  public RowId getRowId(int paramInt) throws SQLException {
    checkColumnIndex(paramInt);
    return (RowId)(setLastColumnWasNull(paramInt) ? null : (hasBufferChanged(paramInt) ? this.rset.typeConverter.toRowId(this.columnBuffer[getRevisedColumnIndex(paramInt)], 0, this.columnLength[getRevisedColumnIndex(paramInt)], getColumnDataType(paramInt)) : this.rset.getRowId(paramInt)));
  }
  
  public RowId getRowId(String paramString) throws SQLException {
    return getRowId(findColumn(paramString));
  }
  
  public synchronized short getShort(int paramInt) throws SQLException {
    checkColumnIndex(paramInt);
    return setLastColumnWasNull(paramInt) ? 0 : (hasBufferChanged(paramInt) ? this.rset.typeConverter.toShort(this.columnBuffer[getRevisedColumnIndex(paramInt)], 0, this.columnLength[getRevisedColumnIndex(paramInt)], getColumnDataType(paramInt)) : this.rset.getShort(paramInt));
  }
  
  public short getShort(String paramString) throws SQLException {
    return getShort(findColumn(paramString));
  }
  
  public SQLXML getSQLXML(int paramInt) throws SQLException {
    checkColumnIndex(paramInt);
    return setLastColumnWasNull(paramInt) ? null : (hasBufferChanged(paramInt) ? this.rset.typeConverter.toSQLXML(this.columnBuffer[getRevisedColumnIndex(paramInt)], 0, this.columnLength[getRevisedColumnIndex(paramInt)], getColumnDataType(paramInt), false) : this.rset.getSQLXML(paramInt));
  }
  
  public SQLXML getSQLXML(String paramString) throws SQLException {
    return getSQLXML(findColumn(paramString));
  }
  
  public Statement getStatement() throws SQLException {
    return this.rset.getStatement();
  }
  
  public synchronized String getString(int paramInt) throws SQLException {
    checkColumnIndex(paramInt);
    if (setLastColumnWasNull(paramInt))
      return null; 
    if (hasBufferChanged(paramInt)) {
      Object object = this.columnBuffer[getRevisedColumnIndex(paramInt)];
      if (object instanceof TbClobBase) {
        long l = ((TbClobBase)object).length();
        return ((TbClobBase)object).getSubString(1L, (int)l);
      } 
      return this.rset.typeConverter.toString(this.columnBuffer[getRevisedColumnIndex(paramInt)], 0, this.columnLength[getRevisedColumnIndex(paramInt)], getColumnDataType(paramInt), getColumnPrecision(paramInt), getColumnScale(paramInt), !this.rset.getRsetType().isScrollable());
    } 
    return this.rset.getString(paramInt);
  }
  
  public String getString(String paramString) throws SQLException {
    return getString(findColumn(paramString));
  }
  
  public synchronized Time getTime(int paramInt) throws SQLException {
    return getTimeInternal(paramInt);
  }
  
  public Time getTime(int paramInt, Calendar paramCalendar) throws SQLException {
    Time time = getTimeInternal(paramInt);
    if (paramCalendar != null) {
      paramCalendar.setTime(time);
      time = (Time)paramCalendar.getTime();
    } 
    return time;
  }
  
  public Time getTime(String paramString) throws SQLException {
    return getTime(findColumn(paramString));
  }
  
  public Time getTime(String paramString, Calendar paramCalendar) throws SQLException {
    return getTime(findColumn(paramString), paramCalendar);
  }
  
  private Time getTimeInternal(int paramInt) throws SQLException {
    checkColumnIndex(paramInt);
    return setLastColumnWasNull(paramInt) ? null : (hasBufferChanged(paramInt) ? this.rset.typeConverter.toTime(this.columnBuffer[getRevisedColumnIndex(paramInt)], 0, this.columnLength[getRevisedColumnIndex(paramInt)], getColumnDataType(paramInt)) : this.rset.getTime(paramInt));
  }
  
  public synchronized Timestamp getTimestamp(int paramInt) throws SQLException {
    return getTimestampInternal(paramInt);
  }
  
  public Timestamp getTimestamp(int paramInt, Calendar paramCalendar) throws SQLException {
    Timestamp timestamp = getTimestampInternal(paramInt);
    if (paramCalendar != null) {
      paramCalendar.setTime(timestamp);
      timestamp = (Timestamp)paramCalendar.getTime();
    } 
    return timestamp;
  }
  
  public Timestamp getTimestamp(String paramString) throws SQLException {
    return getTimestamp(findColumn(paramString));
  }
  
  public Timestamp getTimestamp(String paramString, Calendar paramCalendar) throws SQLException {
    return getTimestamp(findColumn(paramString), paramCalendar);
  }
  
  private Timestamp getTimestampInternal(int paramInt) throws SQLException {
    checkColumnIndex(paramInt);
    return setLastColumnWasNull(paramInt) ? null : (hasBufferChanged(paramInt) ? this.rset.typeConverter.toTimestamp(this.columnBuffer[getRevisedColumnIndex(paramInt)], 0, this.columnLength[getRevisedColumnIndex(paramInt)], getColumnDataType(paramInt)) : this.rset.getTimestamp(paramInt));
  }
  
  public TbTimestamp getTbTimestamp(int paramInt) throws SQLException {
    return getTbTimestampInternal(paramInt);
  }
  
  private TbTimestamp getTbTimestampInternal(int paramInt) throws SQLException {
    checkColumnIndex(paramInt);
    if (setLastColumnWasNull(paramInt))
      return null; 
    if (hasBufferChanged(paramInt)) {
      Row row = getCurrentRow();
      return this.rset.typeConverter.toTbTimestamp(row.getRowChunk(paramInt + this.beginColumnIndex), getColumnOffset(row, paramInt), getColumnLength(row, paramInt), getColumnDataType(paramInt));
    } 
    return this.rset.getTbTimestamp(paramInt);
  }
  
  @Deprecated
  public synchronized InputStream getUnicodeStream(int paramInt) throws SQLException {
    checkColumnIndex(paramInt);
    if (setLastColumnWasNull(paramInt))
      return null; 
    if (hasBufferChanged(paramInt)) {
      Object object = this.columnBuffer[getRevisedColumnIndex(paramInt)];
      if (object instanceof InputStream)
        return (InputStream)object; 
      if (object instanceof byte[])
        return new ByteArrayInputStream((byte[])object); 
      throw TbError.newSQLException(-590705);
    } 
    return this.rset.getAsciiStream(paramInt);
  }
  
  @Deprecated
  public InputStream getUnicodeStream(String paramString) throws SQLException {
    return getUnicodeStream(findColumn(paramString));
  }
  
  private TbPreparedStatement getUpdateRowStatement() throws SQLException {
    StringBuffer stringBuffer = new StringBuffer();
    String str = this.rset.stmt.getOriginalSql().toUpperCase();
    int i = str.lastIndexOf("FOR");
    int j = str.lastIndexOf("UPDATE");
    if (str.lastIndexOf("\"") < i && i < j) {
      stringBuffer.append(this.rset.stmt.getOriginalSql()).append(" ) SET ");
      stringBuffer.delete(i, i + 3);
      stringBuffer.delete(j - 3, j + 3);
      stringBuffer.insert(0, "UPDATE ( ");
    } else {
      stringBuffer.append("UPDATE ( ");
      stringBuffer.append(this.rset.stmt.getOriginalSql());
      stringBuffer.append(" ) SET ");
    } 
    int k = this.hasChanged.length;
    for (byte b = 0; b < k; b++) {
      if (b != 0 && this.hasChanged[b])
        stringBuffer.append(this.rset.getColumnName(b - this.beginColumnIndex + 1)).append(" = ?, "); 
    } 
    stringBuffer.delete(stringBuffer.length() - 2, stringBuffer.length());
    stringBuffer.append(" WHERE ROWIDTOCHAR(ROWID) = ? ");
    TbPreparedStatement tbPreparedStatement = new TbPreparedStatement(this.rset.stmt.conn, stringBuffer.toString());
    if (this.rset.stmt instanceof ParamContainer)
      tbPreparedStatement.impl().copyBindParamInfo((ParamContainer)this.rset.stmt); 
    return tbPreparedStatement;
  }
  
  public URL getURL(int paramInt) throws SQLException {
    throw TbError.newSQLException(-90201);
  }
  
  public URL getURL(String paramString) throws SQLException {
    return getURL(findColumn(paramString));
  }
  
  protected boolean hasBufferChanged(int paramInt) throws SQLException {
    int i = paramInt + this.beginColumnIndex - 1;
    checkColumnIndex(paramInt);
    return ((this.onInserting || this.onUpdating) && this.hasChanged[i]);
  }
  
  public synchronized void insertRow() throws SQLException {
    if (!this.onInserting)
      throw TbError.newSQLException(-590774); 
    TbPreparedStatement tbPreparedStatement = null;
    try {
      tbPreparedStatement = getInsertRowStatement();
      bindInsertRowChangedData(tbPreparedStatement);
      int i = tbPreparedStatement.executeUpdate();
      if (i <= 0)
        throw TbError.newSQLException(-590779, i); 
      if (i > 1)
        throw TbError.newSQLException(-590780, i); 
    } finally {
      if (tbPreparedStatement != null)
        try {
          tbPreparedStatement.close();
        } catch (Exception exception) {} 
    } 
  }
  
  public synchronized boolean isAfterLast() throws SQLException {
    return this.onInserting ? false : this.rset.isAfterLast();
  }
  
  public synchronized boolean isBeforeFirst() throws SQLException {
    return this.onInserting ? false : this.rset.isBeforeFirst();
  }
  
  public boolean isClosed() throws SQLException {
    throw TbError.newSQLException(-90201);
  }
  
  public synchronized boolean isFirst() throws SQLException {
    return this.onInserting ? false : this.rset.isFirst();
  }
  
  public synchronized boolean isLast() throws SQLException {
    return this.onInserting ? false : this.rset.isLast();
  }
  
  public synchronized boolean last() throws SQLException {
    cancelChanges();
    return this.rset.last();
  }
  
  public synchronized void moveToCurrentRow() throws SQLException {
    cancelRowInserts();
  }
  
  public synchronized void moveToInsertRow() throws SQLException {
    if (this.onInserting)
      return; 
    this.onInserting = true;
    if (this.columnBuffer == null) {
      this.columnBuffer = new Object[this.columnCount];
      this.columnLength = new int[this.columnCount];
      this.hasChanged = new boolean[this.columnCount];
    } else {
      resetBuffer();
    } 
  }
  
  public synchronized boolean next() throws SQLException {
    cancelChanges();
    return this.rset.next();
  }
  
  public synchronized boolean previous() throws SQLException {
    cancelChanges();
    return this.rset.previous();
  }
  
  public synchronized void refreshRow() throws SQLException {
    if (this.onInserting)
      throw TbError.newSQLException(-590775); 
    this.rset.refreshRow();
  }
  
  public synchronized boolean relative(int paramInt) throws SQLException {
    cancelChanges();
    return this.rset.relative(paramInt);
  }
  
  public void reset() {
    super.reset();
    this.rset = null;
    this.columnBuffer = null;
    this.columnLength = null;
    this.hasChanged = null;
    this.lastColumnWasNull = false;
  }
  
  protected void resetBuffer() {
    if (this.columnBuffer != null)
      for (byte b = 0; b < this.columnCount - this.beginColumnIndex; b++) {
        this.columnBuffer[b] = null;
        this.columnLength[b] = 0;
        this.hasChanged[b] = false;
      }  
  }
  
  public boolean rowDeleted() throws SQLException {
    return false;
  }
  
  public boolean rowInserted() throws SQLException {
    return false;
  }
  
  public boolean rowUpdated() throws SQLException {
    return false;
  }
  
  protected void setColumnBuffer(int paramInt1, int paramInt2, Object paramObject) throws SQLException {
    checkColumnIndex(paramInt1);
    if (this.columnBuffer == null)
      this.columnBuffer = new Object[this.columnCount]; 
    if (this.columnLength == null)
      this.columnLength = new int[this.columnCount]; 
    if (this.hasChanged == null)
      this.hasChanged = new boolean[this.columnCount]; 
    int i = getRevisedColumnIndex(paramInt1);
    this.hasChanged[i] = true;
    this.columnLength[i] = paramInt2;
    this.columnBuffer[i] = paramObject;
  }
  
  public synchronized void setFetchCompleted(int paramInt) {
    this.rset.setFetchCompleted(paramInt);
  }
  
  public synchronized void setFetchDirection(int paramInt) throws SQLException {
    this.rset.setFetchDirection(paramInt);
  }
  
  public synchronized void setFetchSize(int paramInt) throws SQLException {
    this.rset.setFetchSize(paramInt);
  }
  
  private boolean setLastColumnWasNull(int paramInt) throws SQLException {
    return this.lastColumnWasNull = getCurrentRow().isNull(paramInt + this.beginColumnIndex);
  }
  
  private void storeUpdatedRowChunk() throws SQLException {
    int i = this.hasChanged.length;
    Row row = this.rset.getCurrentRow();
    for (byte b = 0; b < i; b++) {
      if (this.hasChanged[b])
        row.setUpdatedColumn(b + 1, this.columnLength[b], this.columnBuffer[b]); 
    } 
  }
  
  public synchronized void updateArray(int paramInt, Array paramArray) throws SQLException {
    if (paramArray == null) {
      updateNull(paramInt);
    } else {
      if (!(paramArray instanceof com.tmax.tibero.jdbc.TbArray))
        throw TbError.newSQLException(-590702, paramArray.toString()); 
      checkUpdateCursorPosition();
      setColumnBuffer(paramInt, 2147483647, paramArray);
    } 
  }
  
  public void updateArray(String paramString, Array paramArray) throws SQLException {
    updateArray(findColumn(paramString), paramArray);
  }
  
  public synchronized void updateAsciiStream(int paramInt, InputStream paramInputStream) throws SQLException {
    updateBinaryStream(paramInt, paramInputStream, 2147483647);
  }
  
  public synchronized void updateAsciiStream(int paramInt1, InputStream paramInputStream, int paramInt2) throws SQLException {
    updateBinaryStream(paramInt1, paramInputStream, paramInt2);
  }
  
  public synchronized void updateAsciiStream(int paramInt, InputStream paramInputStream, long paramLong) throws SQLException {
    if (paramLong > 2147483647L)
      throw TbError.newSQLException(-90656, paramLong); 
    updateBinaryStream(paramInt, paramInputStream, (int)paramLong);
  }
  
  public void updateAsciiStream(String paramString, InputStream paramInputStream) throws SQLException {
    updateAsciiStream(findColumn(paramString), paramInputStream);
  }
  
  public void updateAsciiStream(String paramString, InputStream paramInputStream, int paramInt) throws SQLException {
    updateAsciiStream(findColumn(paramString), paramInputStream, paramInt);
  }
  
  public void updateAsciiStream(String paramString, InputStream paramInputStream, long paramLong) throws SQLException {
    updateAsciiStream(findColumn(paramString), paramInputStream, paramLong);
  }
  
  public synchronized void updateBigDecimal(int paramInt, BigDecimal paramBigDecimal) throws SQLException {
    checkUpdateCursorPosition();
    if (paramBigDecimal == null) {
      updateNull(paramInt);
      return;
    } 
    byte[] arrayOfByte = this.rset.typeConverter.castFromBigDecimal(paramBigDecimal, this.rset.getColumnDataType(paramInt));
    setColumnBuffer(paramInt, arrayOfByte.length, arrayOfByte);
  }
  
  public void updateBigDecimal(String paramString, BigDecimal paramBigDecimal) throws SQLException {
    updateBigDecimal(findColumn(paramString), paramBigDecimal);
  }
  
  public synchronized void updateBinaryStream(int paramInt, InputStream paramInputStream) throws SQLException {
    if (paramInputStream == null) {
      updateNull(paramInt);
    } else {
      checkUpdateCursorPosition();
      setColumnBuffer(paramInt, 2147483647, paramInputStream);
    } 
  }
  
  public synchronized void updateBinaryStream(int paramInt1, InputStream paramInputStream, int paramInt2) throws SQLException {
    if (paramInputStream == null || paramInt2 <= 0) {
      updateNull(paramInt1);
    } else {
      checkUpdateCursorPosition();
      setColumnBuffer(paramInt1, paramInt2, paramInputStream);
    } 
  }
  
  public synchronized void updateBinaryStream(int paramInt, InputStream paramInputStream, long paramLong) throws SQLException {
    if (paramInputStream == null || paramLong <= 0L) {
      updateNull(paramInt);
    } else {
      if (paramLong > 2147483647L)
        throw TbError.newSQLException(-90656, Long.toString(paramLong)); 
      checkUpdateCursorPosition();
      setColumnBuffer(paramInt, (int)paramLong, paramInputStream);
    } 
  }
  
  public void updateBinaryStream(String paramString, InputStream paramInputStream) throws SQLException {
    updateBinaryStream(findColumn(paramString), paramInputStream);
  }
  
  public void updateBinaryStream(String paramString, InputStream paramInputStream, int paramInt) throws SQLException {
    updateBinaryStream(findColumn(paramString), paramInputStream, paramInt);
  }
  
  public void updateBinaryStream(String paramString, InputStream paramInputStream, long paramLong) throws SQLException {
    updateBinaryStream(findColumn(paramString), paramInputStream, paramLong);
  }
  
  public synchronized void updateBlob(int paramInt, Blob paramBlob) throws SQLException {
    if (paramBlob == null) {
      updateNull(paramInt);
    } else {
      if (!(paramBlob instanceof TbBlob))
        throw TbError.newSQLException(-590702, paramBlob.toString()); 
      checkUpdateCursorPosition();
      setColumnBuffer(paramInt, 2147483647, paramBlob);
    } 
  }
  
  public synchronized void updateBlob(int paramInt, InputStream paramInputStream) throws SQLException {
    if (paramInputStream == null) {
      updateNull(paramInt);
    } else {
      checkUpdateCursorPosition();
      setColumnBuffer(paramInt, 2147483647, paramInputStream);
    } 
  }
  
  public synchronized void updateBlob(int paramInt, InputStream paramInputStream, long paramLong) throws SQLException {
    if (paramInputStream == null || paramLong <= 0L) {
      updateNull(paramInt);
    } else {
      if (paramLong > 2147483647L)
        throw TbError.newSQLException(-90656, Long.toString(paramLong)); 
      checkUpdateCursorPosition();
      setColumnBuffer(paramInt, (int)paramLong, paramInputStream);
    } 
  }
  
  public void updateBlob(String paramString, Blob paramBlob) throws SQLException {
    updateBlob(findColumn(paramString), paramBlob);
  }
  
  public void updateBlob(String paramString, InputStream paramInputStream) throws SQLException {
    updateBlob(findColumn(paramString), paramInputStream);
  }
  
  public void updateBlob(String paramString, InputStream paramInputStream, long paramLong) throws SQLException {
    updateBlob(findColumn(paramString), paramInputStream, paramLong);
  }
  
  public synchronized void updateBoolean(int paramInt, boolean paramBoolean) throws SQLException {
    checkUpdateCursorPosition();
    byte[] arrayOfByte = this.rset.typeConverter.castFromBoolean(paramBoolean, this.rset.getColumnDataType(paramInt));
    setColumnBuffer(paramInt, arrayOfByte.length, arrayOfByte);
  }
  
  public void updateBoolean(String paramString, boolean paramBoolean) throws SQLException {
    updateBoolean(findColumn(paramString), paramBoolean);
  }
  
  public synchronized void updateByte(int paramInt, byte paramByte) throws SQLException {
    checkUpdateCursorPosition();
    byte[] arrayOfByte = this.rset.typeConverter.castFromByte(paramByte, this.rset.getColumnDataType(paramInt));
    setColumnBuffer(paramInt, arrayOfByte.length, arrayOfByte);
  }
  
  public void updateByte(String paramString, byte paramByte) throws SQLException {
    updateByte(findColumn(paramString), paramByte);
  }
  
  public synchronized void updateBytes(int paramInt, byte[] paramArrayOfbyte) throws SQLException {
    checkUpdateCursorPosition();
    if (paramArrayOfbyte == null) {
      updateNull(paramInt);
      return;
    } 
    byte[] arrayOfByte = this.rset.typeConverter.castFromBytes(paramArrayOfbyte, this.rset.getColumnDataType(paramInt));
    setColumnBuffer(paramInt, arrayOfByte.length, arrayOfByte);
  }
  
  public void updateBytes(String paramString, byte[] paramArrayOfbyte) throws SQLException {
    updateBytes(findColumn(paramString), paramArrayOfbyte);
  }
  
  public void updateRAW(int paramInt, TbRAW paramTbRAW) throws SQLException {
    updateBytes(paramInt, paramTbRAW.getBytes());
  }
  
  public void updateRAW(String paramString, TbRAW paramTbRAW) throws SQLException {
    updateBytes(paramString, paramTbRAW.getBytes());
  }
  
  public synchronized void updateCharacterStream(int paramInt, Reader paramReader) throws SQLException {
    if (paramReader == null) {
      updateNull(paramInt);
    } else {
      checkUpdateCursorPosition();
      setColumnBuffer(paramInt, 2147483647, paramReader);
    } 
  }
  
  public synchronized void updateCharacterStream(int paramInt1, Reader paramReader, int paramInt2) throws SQLException {
    if (paramReader == null || paramInt2 <= 0) {
      updateNull(paramInt1);
    } else {
      checkUpdateCursorPosition();
      setColumnBuffer(paramInt1, 2147483647, paramReader);
    } 
  }
  
  public synchronized void updateCharacterStream(int paramInt, Reader paramReader, long paramLong) throws SQLException {
    if (paramReader == null || paramLong <= 0L) {
      updateNull(paramInt);
    } else {
      if (paramLong > 2147483647L)
        throw TbError.newSQLException(-90656, Long.toString(paramLong)); 
      checkUpdateCursorPosition();
      setColumnBuffer(paramInt, (int)paramLong, paramReader);
    } 
  }
  
  public void updateCharacterStream(String paramString, Reader paramReader) throws SQLException {
    updateCharacterStream(findColumn(paramString), paramReader);
  }
  
  public void updateCharacterStream(String paramString, Reader paramReader, int paramInt) throws SQLException {
    updateCharacterStream(findColumn(paramString), paramReader, paramInt);
  }
  
  public void updateCharacterStream(String paramString, Reader paramReader, long paramLong) throws SQLException {
    updateCharacterStream(findColumn(paramString), paramReader, paramLong);
  }
  
  public synchronized void updateClob(int paramInt, Clob paramClob) throws SQLException {
    if (paramClob == null) {
      updateNull(paramInt);
    } else {
      if (!(paramClob instanceof TbClobBase))
        throw TbError.newSQLException(-590702, paramClob.toString()); 
      checkUpdateCursorPosition();
      setColumnBuffer(paramInt, 2147483647, paramClob);
    } 
  }
  
  public synchronized void updateClob(int paramInt, Reader paramReader) throws SQLException {
    if (paramReader == null) {
      updateNull(paramInt);
    } else {
      checkUpdateCursorPosition();
      setColumnBuffer(paramInt, 2147483647, paramReader);
    } 
  }
  
  public synchronized void updateClob(int paramInt, Reader paramReader, long paramLong) throws SQLException {
    if (paramReader == null || paramLong <= 0L) {
      updateNull(paramInt);
    } else {
      if (paramLong > 2147483647L)
        throw TbError.newSQLException(-90656, Long.toString(paramLong)); 
      checkUpdateCursorPosition();
      setColumnBuffer(paramInt, (int)paramLong, paramReader);
    } 
  }
  
  public void updateClob(String paramString, Clob paramClob) throws SQLException {
    updateClob(findColumn(paramString), paramClob);
  }
  
  public void updateClob(String paramString, Reader paramReader) throws SQLException {
    updateClob(findColumn(paramString), paramReader);
  }
  
  public void updateClob(String paramString, Reader paramReader, long paramLong) throws SQLException {
    updateClob(findColumn(paramString), paramReader, paramLong);
  }
  
  public synchronized void updateDate(int paramInt, Date paramDate) throws SQLException {
    checkUpdateCursorPosition();
    if (paramDate == null) {
      updateNull(paramInt);
      return;
    } 
    byte[] arrayOfByte = this.rset.typeConverter.castFromDate(paramDate, this.rset.getColumnDataType(paramInt));
    setColumnBuffer(paramInt, arrayOfByte.length, arrayOfByte);
  }
  
  public void updateDate(String paramString, Date paramDate) throws SQLException {
    updateDate(findColumn(paramString), paramDate);
  }
  
  public synchronized void updateDouble(int paramInt, double paramDouble) throws SQLException {
    checkUpdateCursorPosition();
    byte[] arrayOfByte = this.rset.typeConverter.castFromDouble(paramDouble, this.rset.getColumnDataType(paramInt));
    setColumnBuffer(paramInt, arrayOfByte.length, arrayOfByte);
  }
  
  public void updateDouble(String paramString, double paramDouble) throws SQLException {
    updateDouble(findColumn(paramString), paramDouble);
  }
  
  public synchronized void updateFloat(int paramInt, float paramFloat) throws SQLException {
    checkUpdateCursorPosition();
    byte[] arrayOfByte = this.rset.typeConverter.castFromFloat(paramFloat, this.rset.getColumnDataType(paramInt));
    setColumnBuffer(paramInt, arrayOfByte.length, arrayOfByte);
  }
  
  public void updateFloat(String paramString, float paramFloat) throws SQLException {
    updateFloat(findColumn(paramString), paramFloat);
  }
  
  public synchronized void updateInt(int paramInt1, int paramInt2) throws SQLException {
    checkUpdateCursorPosition();
    byte[] arrayOfByte = this.rset.typeConverter.castFromInt(paramInt2, this.rset.getColumnDataType(paramInt1));
    setColumnBuffer(paramInt1, arrayOfByte.length, arrayOfByte);
  }
  
  public void updateInt(String paramString, int paramInt) throws SQLException {
    updateInt(findColumn(paramString), paramInt);
  }
  
  public synchronized void updateLong(int paramInt, long paramLong) throws SQLException {
    checkUpdateCursorPosition();
    byte[] arrayOfByte = this.rset.typeConverter.castFromLong(paramLong, this.rset.getColumnDataType(paramInt));
    setColumnBuffer(paramInt, arrayOfByte.length, arrayOfByte);
  }
  
  public void updateLong(String paramString, long paramLong) throws SQLException {
    updateLong(findColumn(paramString), paramLong);
  }
  
  public synchronized void updateNCharacterStream(int paramInt, Reader paramReader) throws SQLException {
    if (paramReader == null) {
      updateNull(paramInt);
    } else {
      checkUpdateCursorPosition();
      setColumnBuffer(paramInt, 2147483647, paramReader);
    } 
  }
  
  public synchronized void updateNCharacterStream(int paramInt, Reader paramReader, long paramLong) throws SQLException {
    if (paramReader == null || paramLong <= 0L) {
      updateNull(paramInt);
    } else {
      if (paramLong > 2147483647L)
        throw TbError.newSQLException(-90656, Long.toString(paramLong)); 
      checkUpdateCursorPosition();
      setColumnBuffer(paramInt, (int)paramLong, paramReader);
    } 
  }
  
  public void updateNCharacterStream(String paramString, Reader paramReader) throws SQLException {
    updateNCharacterStream(findColumn(paramString), paramReader);
  }
  
  public void updateNCharacterStream(String paramString, Reader paramReader, long paramLong) throws SQLException {
    updateNCharacterStream(findColumn(paramString), paramReader, paramLong);
  }
  
  public synchronized void updateNClob(int paramInt, NClob paramNClob) throws SQLException {
    if (paramNClob == null) {
      updateNull(paramInt);
    } else {
      if (!(paramNClob instanceof TbNClob))
        throw TbError.newSQLException(-590702, paramNClob.toString()); 
      checkUpdateCursorPosition();
      setColumnBuffer(paramInt, 2147483647, paramNClob);
    } 
  }
  
  public synchronized void updateNClob(int paramInt, Reader paramReader) throws SQLException {
    updateNClob(paramInt, paramReader, 2147483647L);
  }
  
  public synchronized void updateNClob(int paramInt, Reader paramReader, long paramLong) throws SQLException {
    if (paramLong > 2147483647L)
      throw TbError.newSQLException(-90656, Long.toString(paramLong)); 
    if (paramReader == null || paramLong <= 0L) {
      updateNull(paramInt);
    } else {
      checkUpdateCursorPosition();
      setColumnBuffer(paramInt, 2147483647, paramReader);
    } 
  }
  
  public void updateNClob(String paramString, NClob paramNClob) throws SQLException {
    updateNClob(findColumn(paramString), paramNClob);
  }
  
  public void updateNClob(String paramString, Reader paramReader) throws SQLException {
    updateNClob(findColumn(paramString), paramReader);
  }

  public <T> T getObject(int i, Class<T> clazz) throws SQLException {
    Debug.logMethod("TbRSUpdatable.getObject", new Object[]{this, Integer.toString(i), clazz});
    String className = clazz.getName();
    byte var5 = -1;
    switch(className.hashCode()) {
      case -2056817302:
        if (className.equals("java.lang.Integer")) {
          var5 = 12;
        }
        break;
      case -1917484011:
        if (className.equals("java.time.OffsetTime")) {
          var5 = 25;
        }
        break;
      case -1766012793:
        if (className.equals("java.sql.Ref")) {
          var5 = 15;
        }
        break;
      case -1405464277:
        if (className.equals("java.math.BigDecimal")) {
          var5 = 2;
        }
        break;
      case -1374008726:
        if (className.equals("byte[]")) {
          var5 = 6;
        }
        break;
      case -1359732257:
        if (className.equals("java.io.Reader")) {
          var5 = 7;
        }
        break;
      case -1246518012:
        if (className.equals("java.time.LocalDate")) {
          var5 = 22;
        }
        break;
      case -1246033885:
        if (className.equals("java.time.LocalTime")) {
          var5 = 23;
        }
        break;
      case -1179039247:
        if (className.equals("java.time.LocalDateTime")) {
          var5 = 24;
        }
        break;
      case -989675752:
        if (className.equals("java.math.BigInteger")) {
          var5 = 3;
        }
        break;
      case -682591005:
        if (className.equals("java.time.OffsetDateTime")) {
          var5 = 26;
        }
        break;
      case -641510067:
        if (className.equals("java.sql.Array")) {
          var5 = 0;
        }
        break;
      case -630909826:
        if (className.equals("java.sql.NClob")) {
          var5 = 14;
        }
        break;
      case -625895543:
        if (className.equals("java.sql.RowId")) {
          var5 = 16;
        }
        break;
      case -527879800:
        if (className.equals("java.lang.Float")) {
          var5 = 11;
        }
        break;
      case -515992664:
        if (className.equals("java.lang.Short")) {
          var5 = 17;
        }
        break;
      case 398507100:
        if (className.equals("java.lang.Byte")) {
          var5 = 5;
        }
        break;
      case 398795216:
        if (className.equals("java.lang.Long")) {
          var5 = 13;
        }
        break;
      case 761287205:
        if (className.equals("java.lang.Double")) {
          var5 = 10;
        }
        break;
      case 833723470:
        if (className.equals("java.io.InputStream")) {
          var5 = 1;
        }
        break;
      case 1087708713:
        if (className.equals("java.sql.Blob")) {
          var5 = 4;
        }
        break;
      case 1087738504:
        if (className.equals("java.sql.Clob")) {
          var5 = 8;
        }
        break;
      case 1087757882:
        if (className.equals("java.sql.Date")) {
          var5 = 9;
        }
        break;
      case 1088242009:
        if (className.equals("java.sql.Time")) {
          var5 = 20;
        }
        break;
      case 1195259493:
        if (className.equals("java.lang.String")) {
          var5 = 19;
        }
        break;
      case 1252880906:
        if (className.equals("java.sql.Timestamp")) {
          var5 = 21;
        }
        break;
      case 2050244018:
        if (className.equals("java.net.URL")) {
          var5 = 27;
        }
        break;
      case 2071730933:
        if (className.equals("java.sql.SQLXML")) {
          var5 = 18;
        }
    }

    switch (var5) {
      case 0 -> {
        return (T) this.getObject(i);
      }
      case 1 -> {
        return (T) this.getBinaryStream(i);
      }
      case 2 -> {
        return (T) this.getBigDecimal(i);
      }
      case 3 -> {
        BigDecimal bigDecimal = this.getBigDecimal(i);
        return (T) (bigDecimal != null ? bigDecimal.toBigInteger() : null);
      }
      case 4 -> {
        return (T) this.getBlob(i);
      }
      case 5 -> {
        return (T) Byte.valueOf(this.getByte(i));
      }
      case 6 -> {
        return (T) this.getBytes(i);
      }
      case 7 -> {
        return (T) this.getCharacterStream(i);
      }
      case 8 -> {
        return (T) this.getClob(i);
      }
      case 9 -> {
        return (T) this.getDate(i);
      }
      case 10 -> {
        return (T) Double.valueOf(this.getDouble(i));
      }
      case 11 -> {
        return (T) Float.valueOf(this.getFloat(i));
      }
      case 12 -> {
        return (T) Integer.valueOf(this.getInt(i));
      }
      case 13 -> {
        return (T) Long.valueOf(this.getLong(i));
      }
      case 14 -> {
        return (T) this.getNClob(i);
      }
      case 15 -> {
        return (T) this.getRef(i);
      }
      case 16 -> {
        return (T) this.getRowId(i);
      }
      case 17 -> {
        return (T) Short.valueOf(this.getShort(i));
      }
      case 18 -> {
        return (T) this.getSQLXML(i);
      }
      case 19 -> {
        return (T) this.getString(i);
      }
      case 20 -> {
        return (T) this.getTime(i);
      }
      case 21 -> {
        return (T) this.getTimestamp(i);
      }
      case 22 -> {
        java.sql.Date date = this.getDate(i);
        return (T) (date != null ? date.toLocalDate() : null);
      }
      case 23 -> {
        java.sql.Time time = this.getTime(i);
        return (T) (time != null ? time.toLocalTime() : null);
      }
      case 24 -> {
        java.sql.Timestamp timestamp = this.getTimestamp(i);
        return (T) (timestamp != null ? timestamp.toLocalDateTime() : null);
      }
      case 25 -> {
        return (T) this.getOffsetTime(i);
      }
      case 26 -> {
        return (T) this.getOffsetDateTime(i);
      }
      case 27 -> {
        return (T) this.getURL(i);
      }
      default -> {
        return (T) this.getObject(i);
      }
    }

  }

  public <T> T getObject(String columnLabel, Class<T> type) throws SQLException {
    return this.getObject(this.findColumn(columnLabel), type);
  }

  public void updateNClob(String paramString, Reader paramReader, long paramLong) throws SQLException {
    updateNClob(findColumn(paramString), paramReader, paramLong);
  }
  
  public synchronized void updateNString(int paramInt, String paramString) throws SQLException {
    if (paramString == null) {
      updateNull(paramInt);
      return;
    } 
    checkUpdateCursorPosition();
    byte[] arrayOfByte = this.rset.typeConverter.castFromString(paramString, this.rset.getColumnDataType(paramInt));
    setColumnBuffer(paramInt, arrayOfByte.length, arrayOfByte);
  }
  
  public void updateNString(String paramString1, String paramString2) throws SQLException {
    updateNString(findColumn(paramString1), paramString2);
  }
  
  public synchronized void updateNull(int paramInt) throws SQLException {
    checkUpdateCursorPosition();
    setColumnBuffer(paramInt, 0, new byte[0]);
  }
  
  public void updateNull(String paramString) throws SQLException {
    updateNull(findColumn(paramString));
  }
  
  public synchronized void updateObject(int paramInt, Object paramObject) throws SQLException {
    updateObject(paramInt, paramObject, 0);
  }
  
  public synchronized void updateObject(int paramInt1, Object paramObject, int paramInt2) throws SQLException {
    if (paramObject == null) {
      updateNull(paramInt1);
      return;
    } 
    checkUpdateCursorPosition();
    if (paramObject instanceof Clob || paramObject instanceof Blob || paramObject instanceof Reader || paramObject instanceof InputStream || paramObject instanceof com.tmax.tibero.jdbc.TbArray || paramObject instanceof com.tmax.tibero.jdbc.TbStruct) {
      setColumnBuffer(paramInt1, 2147483647, paramObject);
    } else {
      byte[] arrayOfByte = this.rset.typeConverter.castFromObject(paramObject, this.rset.getColumnDataType(paramInt1));
      setColumnBuffer(paramInt1, arrayOfByte.length, arrayOfByte);
    } 
  }
  
  public void updateObject(String paramString, Object paramObject) throws SQLException {
    updateObject(findColumn(paramString), paramObject);
  }
  
  public void updateObject(String paramString, Object paramObject, int paramInt) throws SQLException {
    updateObject(findColumn(paramString), paramObject);
  }
  
  public synchronized void updateRef(int paramInt, Ref paramRef) throws SQLException {
    if (paramRef == null) {
      updateNull(paramInt);
    } else {
      if (!(paramRef instanceof TbRef))
        throw TbError.newSQLException(-590702, paramRef.toString()); 
      checkUpdateCursorPosition();
      byte[] arrayOfByte = ((TbRef)paramRef).getRawData();
      setColumnBuffer(paramInt, arrayOfByte.length, arrayOfByte);
    } 
  }
  
  public void updateRef(String paramString, Ref paramRef) throws SQLException {
    updateRef(findColumn(paramString), paramRef);
  }
  
  public synchronized void updateRow() throws SQLException {
    if (this.onInserting)
      throw TbError.newSQLException(-590776); 
    TbPreparedStatement tbPreparedStatement = null;
    try {
      if (this.hasChanged == null)
        return; 
      tbPreparedStatement = getUpdateRowStatement();
      bindUpdateRowChangedData(tbPreparedStatement);
      int i = tbPreparedStatement.executeUpdate();
      if (i <= 0)
        throw TbError.newSQLException(-590781, i); 
      if (i > 1)
        throw TbError.newSQLException(-590782, i); 
      if (this.rset instanceof TbRSSensitive)
        ((TbRSSensitive)this.rset).refreshRowForced(1); 
      storeUpdatedRowChunk();
    } finally {
      if (tbPreparedStatement != null)
        try {
          tbPreparedStatement.close();
        } catch (Exception exception) {} 
    } 
  }
  
  public synchronized void updateRowId(int paramInt, RowId paramRowId) throws SQLException {
    checkUpdateCursorPosition();
    if (!(paramRowId instanceof com.tmax.tibero.jdbc.TbRowId))
      throw TbError.newSQLException(-590771, paramRowId.toString()); 
    byte[] arrayOfByte = paramRowId.getBytes();
    setColumnBuffer(paramInt, arrayOfByte.length, arrayOfByte);
  }
  
  public void updateRowId(String paramString, RowId paramRowId) throws SQLException {
    updateRowId(findColumn(paramString), paramRowId);
  }
  
  public synchronized void updateShort(int paramInt, short paramShort) throws SQLException {
    checkUpdateCursorPosition();
    byte[] arrayOfByte = this.rset.typeConverter.castFromShort(paramShort, this.rset.getColumnDataType(paramInt));
    setColumnBuffer(paramInt, arrayOfByte.length, arrayOfByte);
  }
  
  public void updateShort(String paramString, short paramShort) throws SQLException {
    updateShort(findColumn(paramString), paramShort);
  }
  
  public synchronized void updateSQLXML(int paramInt, SQLXML paramSQLXML) throws SQLException {
    if (paramSQLXML == null) {
      updateNull(paramInt);
    } else {
      if (!(paramSQLXML instanceof com.tmax.tibero.jdbc.TbSQLXML))
        throw TbError.newSQLException(-590702, paramSQLXML.toString()); 
      checkUpdateCursorPosition();
      setColumnBuffer(paramInt, 2147483647, paramSQLXML);
    } 
  }
  
  public void updateSQLXML(String paramString, SQLXML paramSQLXML) throws SQLException {
    updateSQLXML(findColumn(paramString), paramSQLXML);
  }
  
  public synchronized void updateString(int paramInt, String paramString) throws SQLException {
    if (paramString == null) {
      updateNull(paramInt);
      return;
    } 
    checkUpdateCursorPosition();
    byte[] arrayOfByte = this.rset.typeConverter.castFromString(paramString, this.rset.getColumnDataType(paramInt));
    setColumnBuffer(paramInt, arrayOfByte.length, arrayOfByte);
  }
  
  public void updateString(String paramString1, String paramString2) throws SQLException {
    updateString(findColumn(paramString1), paramString2);
  }
  
  public synchronized void updateTime(int paramInt, Time paramTime) throws SQLException {
    if (paramTime == null) {
      updateNull(paramInt);
      return;
    } 
    checkUpdateCursorPosition();
    byte[] arrayOfByte = this.rset.typeConverter.castFromTime(paramTime, this.rset.getColumnDataType(paramInt));
    setColumnBuffer(paramInt, arrayOfByte.length, arrayOfByte);
  }
  
  public void updateTime(String paramString, Time paramTime) throws SQLException {
    updateTime(findColumn(paramString), paramTime);
  }
  
  public synchronized void updateTimestamp(int paramInt, Timestamp paramTimestamp) throws SQLException {
    if (paramTimestamp == null) {
      updateNull(paramInt);
      return;
    } 
    checkUpdateCursorPosition();
    byte[] arrayOfByte = this.rset.typeConverter.castFromTimestamp(paramTimestamp, this.rset.getColumnDataType(paramInt));
    setColumnBuffer(paramInt, arrayOfByte.length, arrayOfByte);
  }
  
  public void updateTimestamp(String paramString, Timestamp paramTimestamp) throws SQLException {
    updateTimestamp(findColumn(paramString), paramTimestamp);
  }
  
  public synchronized void updateTbTimestamp(int paramInt, TbTimestamp paramTbTimestamp) throws SQLException {
    if (paramTbTimestamp == null) {
      updateNull(paramInt);
      return;
    } 
    checkUpdateCursorPosition();
    TbTimestamp tbTimestamp = new TbTimestamp(paramTbTimestamp.getBytes());
    byte[] arrayOfByte = tbTimestamp.getBytes();
    setColumnBuffer(paramInt, arrayOfByte.length, arrayOfByte);
  }
  
  public void updateTbTimestamp(String paramString, TbTimestamp paramTbTimestamp) throws SQLException {
    updateTbTimestamp(findColumn(paramString), paramTbTimestamp);
  }
  
  public synchronized boolean wasNull() throws SQLException {
    return this.lastColumnWasNull;
  }
  private OffsetTime getOffsetTime(int i) throws SQLException {
    this.checkColumnIndex(i);
    if (this.setLastColumnWasNull(i)) {
      return null;
    } else if (this.hasBufferChanged(i)) {
      if (this.columnLength[this.getRevisedColumnIndex(i)] == 17) {
        TimeZone timezone = this.rset.typeConverter.toTimeZoneFromBytes((byte[])((byte[])this.columnBuffer[this.getRevisedColumnIndex(i)]), 0, this.columnLength[this.getRevisedColumnIndex(i)]);
        return OffsetTime.of(this.getTimestamp(i).toLocalDateTime().toLocalTime(), ZoneOffset.ofTotalSeconds(timezone.getRawOffset() / 1000));
      } else {
        return OffsetTime.of(this.getTime(i).toLocalTime(), ZoneOffset.UTC);
      }
    } else {
      return (OffsetTime)this.rset.getObject(i, OffsetTime.class);
    }
  }

  private OffsetDateTime getOffsetDateTime(int i) throws SQLException {
    this.checkColumnIndex(i);
    if (this.setLastColumnWasNull(i)) {
      return null;
    } else if (this.hasBufferChanged(i)) {
      if (this.columnLength[this.getRevisedColumnIndex(i)] == 17) {
        TimeZone timezone = this.rset.typeConverter.toTimeZoneFromBytes((byte[])((byte[])this.columnBuffer[this.getRevisedColumnIndex(i)]), 0, this.columnLength[this.getRevisedColumnIndex(i)]);
        return OffsetDateTime.of(this.getTimestamp(i).toLocalDateTime(), ZoneOffset.ofTotalSeconds(timezone.getRawOffset() / 1000));
      } else {
        return OffsetDateTime.of(this.getTimestamp(i).toLocalDateTime(), ZoneOffset.UTC);
      }
    } else {
      return (OffsetDateTime)this.rset.getObject(i, OffsetDateTime.class);
    }
  }
}


/* Location:              C:\TmaxData\tibero6\client\lib\jar\tibero6-jdbc.jar!\com\tmax\tibero\jdbc\driver\TbRSUpdatable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */