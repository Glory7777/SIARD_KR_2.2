package com.tmax.tibero.jdbc;

import com.tmax.tibero.jdbc.data.Column;
import com.tmax.tibero.jdbc.data.DataType;
import com.tmax.tibero.jdbc.data.DataTypeConverter;
import com.tmax.tibero.jdbc.data.RsetType;
import com.tmax.tibero.jdbc.data.TbDate;
import com.tmax.tibero.jdbc.data.TbRAW;
import com.tmax.tibero.jdbc.data.TbTimestamp;
import com.tmax.tibero.jdbc.driver.TbConnection;
import com.tmax.tibero.jdbc.err.TbError;
import java.io.ByteArrayInputStream;
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
import java.sql.Ref;
import java.sql.ResultSetMetaData;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Map;

public class TbArrayDataResultSet implements TbResultSet {
  Object[] data;
  
  private int currentIndex;
  
  private int lastIndex;
  
  private int fetchSize;
  
  private boolean isRsetClosed;
  
  private boolean isNullData = false;
  
  private boolean lastColumnWasNull = false;
  
  DataTypeConverter typeConverter;
  
  private int dataType;
  
  private byte[] index;
  
  private byte[] value;
  
  private boolean fromBytes = true;
  
  private TbConnection conn;
  
  protected TbArrayDataResultSet(TbConnection paramTbConnection, TbArray paramTbArray, long paramLong, int paramInt, Map<String, Class<?>> paramMap) throws SQLException {
    this.conn = paramTbConnection;
    this.dataType = paramTbArray.getDescriptor().getElementType();
    if (paramMap == null) {
      this.data = (Object[])paramTbArray.getArray(paramLong, paramInt);
    } else {
      this.data = (Object[])paramTbArray.getArray(paramLong, paramInt, paramMap);
    } 
    this.typeConverter = this.conn.getTypeConverter();
    this.currentIndex = -1;
    this.lastIndex = (this.data == null) ? -1 : (this.data.length - 1);
    this.isRsetClosed = false;
    this.fetchSize = 50;
    if ((this.lastIndex >= 0 && this.data[0] instanceof Array) || this.data[0] instanceof java.sql.Struct || this.data[0] instanceof Reader || this.data[0] instanceof InputStream || this.data[0] instanceof Clob || this.data[0] instanceof Blob || this.data[0] instanceof java.sql.SQLData || this.data[0] instanceof Ref)
      this.fromBytes = false; 
  }
  
  public boolean next() throws SQLException {
    if (this.isRsetClosed)
      throw TbError.newSQLException(-90646); 
    this.currentIndex++;
    if (this.currentIndex <= this.lastIndex) {
      this.index = this.typeConverter.castFromObject(new Integer(this.currentIndex + 1), 1);
      if (this.data[this.currentIndex] == null) {
        this.isNullData = true;
      } else if (this.fromBytes) {
        this.value = this.typeConverter.castFromObject(this.data[this.currentIndex], this.dataType);
      } 
      return true;
    } 
    return false;
  }
  
  public void close() throws SQLException {
    this.isRsetClosed = true;
  }
  
  public boolean wasNull() throws SQLException {
    return this.lastColumnWasNull;
  }
  
  public String getString(int paramInt) throws SQLException {
    checkColumnIndex(paramInt);
    if (setLastColumnIsNull(paramInt))
      return null; 
    if (this.data[this.currentIndex] instanceof TbClobBase && paramInt == 2) {
      long l = ((TbClobBase)this.data[this.currentIndex]).length();
      return ((TbClobBase)this.data[this.currentIndex]).getSubString(1L, (int)l);
    } 
    int i = getType(paramInt);
    byte[] arrayOfByte = getData(paramInt);
    return this.typeConverter.toString(arrayOfByte, 0, arrayOfByte.length, i, -1, -1, false);
  }
  
  public boolean getBoolean(int paramInt) throws SQLException {
    checkColumnIndex(paramInt);
    if (setLastColumnIsNull(paramInt))
      return false; 
    int i = getType(paramInt);
    byte[] arrayOfByte = getData(paramInt);
    return this.typeConverter.toBoolean(arrayOfByte, 0, arrayOfByte.length, i);
  }
  
  public byte getByte(int paramInt) throws SQLException {
    checkColumnIndex(paramInt);
    if (setLastColumnIsNull(paramInt))
      return 0; 
    int i = getType(paramInt);
    byte[] arrayOfByte = getData(paramInt);
    return this.typeConverter.toByte(arrayOfByte, 0, arrayOfByte.length, i);
  }
  
  public short getShort(int paramInt) throws SQLException {
    checkColumnIndex(paramInt);
    if (setLastColumnIsNull(paramInt))
      return 0; 
    int i = getType(paramInt);
    byte[] arrayOfByte = getData(paramInt);
    return this.typeConverter.toShort(arrayOfByte, 0, arrayOfByte.length, i);
  }
  
  public int getInt(int paramInt) throws SQLException {
    checkColumnIndex(paramInt);
    if (setLastColumnIsNull(paramInt))
      return 0; 
    int i = getType(paramInt);
    byte[] arrayOfByte = getData(paramInt);
    return this.typeConverter.toInt(arrayOfByte, 0, arrayOfByte.length, i);
  }
  
  public long getLong(int paramInt) throws SQLException {
    checkColumnIndex(paramInt);
    if (setLastColumnIsNull(paramInt))
      return 0L; 
    int i = getType(paramInt);
    byte[] arrayOfByte = getData(paramInt);
    return this.typeConverter.toLong(arrayOfByte, 0, arrayOfByte.length, i);
  }
  
  public float getFloat(int paramInt) throws SQLException {
    checkColumnIndex(paramInt);
    if (setLastColumnIsNull(paramInt))
      return 0.0F; 
    int i = getType(paramInt);
    byte[] arrayOfByte = getData(paramInt);
    return this.typeConverter.toFloat(arrayOfByte, 0, arrayOfByte.length, i);
  }
  
  public double getDouble(int paramInt) throws SQLException {
    checkColumnIndex(paramInt);
    if (setLastColumnIsNull(paramInt))
      return 0.0D; 
    int i = getType(paramInt);
    byte[] arrayOfByte = getData(paramInt);
    return this.typeConverter.toDouble(arrayOfByte, 0, arrayOfByte.length, i);
  }
  
  @Deprecated
  public BigDecimal getBigDecimal(int paramInt1, int paramInt2) throws SQLException {
    checkColumnIndex(paramInt1);
    if (setLastColumnIsNull(paramInt1))
      return null; 
    int i = getType(paramInt1);
    byte[] arrayOfByte = getData(paramInt1);
    return this.typeConverter.toBigDecimal(arrayOfByte, 0, arrayOfByte.length, i);
  }
  
  public byte[] getBytes(int paramInt) throws SQLException {
    checkColumnIndex(paramInt);
    if (setLastColumnIsNull(paramInt))
      return null; 
    int i = getType(paramInt);
    byte[] arrayOfByte = getData(paramInt);
    return this.typeConverter.toBytes(arrayOfByte, 0, arrayOfByte.length, i, false);
  }
  
  public Date getDate(int paramInt) throws SQLException {
    checkColumnIndex(paramInt);
    if (setLastColumnIsNull(paramInt))
      return null; 
    int i = getType(paramInt);
    byte[] arrayOfByte = getData(paramInt);
    return this.typeConverter.toDate(arrayOfByte, 0, arrayOfByte.length, i);
  }
  
  public Time getTime(int paramInt) throws SQLException {
    checkColumnIndex(paramInt);
    if (setLastColumnIsNull(paramInt))
      return null; 
    int i = getType(paramInt);
    byte[] arrayOfByte = getData(paramInt);
    return this.typeConverter.toTime(arrayOfByte, 0, arrayOfByte.length, i);
  }
  
  public Timestamp getTimestamp(int paramInt) throws SQLException {
    checkColumnIndex(paramInt);
    if (setLastColumnIsNull(paramInt))
      return null; 
    int i = getType(paramInt);
    byte[] arrayOfByte = getData(paramInt);
    return this.typeConverter.toTimestamp(arrayOfByte, 0, arrayOfByte.length, i);
  }
  
  public InputStream getAsciiStream(int paramInt) throws SQLException {
    return getBinaryStream(paramInt);
  }
  
  @Deprecated
  public InputStream getUnicodeStream(int paramInt) throws SQLException {
    checkColumnIndex(paramInt);
    if (setLastColumnIsNull(paramInt))
      return null; 
    if (paramInt == 2) {
      if (this.data[this.currentIndex] instanceof InputStream)
        return (InputStream)this.data[this.currentIndex]; 
      if (this.data[this.currentIndex] instanceof byte[])
        return new ByteArrayInputStream((byte[])this.data[this.currentIndex]); 
    } 
    int i = getType(paramInt);
    byte[] arrayOfByte = getData(paramInt);
    return this.typeConverter.toUnicodeStream(arrayOfByte, 0, arrayOfByte.length, i, false);
  }
  
  public InputStream getBinaryStream(int paramInt) throws SQLException {
    checkColumnIndex(paramInt);
    if (setLastColumnIsNull(paramInt))
      return null; 
    if (paramInt == 2) {
      if (this.data[this.currentIndex] instanceof InputStream)
        return (InputStream)this.data[this.currentIndex]; 
      if (this.data[this.currentIndex] instanceof Blob)
        return ((TbBlob)this.data[this.currentIndex]).getBinaryStream(); 
    } 
    int i = getType(paramInt);
    byte[] arrayOfByte = getData(paramInt);
    return this.typeConverter.toBinaryStream(arrayOfByte, 0, arrayOfByte.length, i);
  }
  
  public String getString(String paramString) throws SQLException {
    return getString(findColumn(paramString));
  }
  
  public boolean getBoolean(String paramString) throws SQLException {
    return getBoolean(findColumn(paramString));
  }
  
  public byte getByte(String paramString) throws SQLException {
    return getByte(findColumn(paramString));
  }
  
  public short getShort(String paramString) throws SQLException {
    return getShort(findColumn(paramString));
  }
  
  public int getInt(String paramString) throws SQLException {
    return getInt(findColumn(paramString));
  }
  
  public long getLong(String paramString) throws SQLException {
    return getLong(findColumn(paramString));
  }
  
  public float getFloat(String paramString) throws SQLException {
    return getFloat(findColumn(paramString));
  }
  
  public double getDouble(String paramString) throws SQLException {
    return getDouble(findColumn(paramString));
  }
  
  @Deprecated
  public BigDecimal getBigDecimal(String paramString, int paramInt) throws SQLException {
    return getBigDecimal(findColumn(paramString));
  }
  
  public byte[] getBytes(String paramString) throws SQLException {
    return getBytes(findColumn(paramString));
  }
  
  public Date getDate(String paramString) throws SQLException {
    return getDate(findColumn(paramString));
  }
  
  public Time getTime(String paramString) throws SQLException {
    return getTime(findColumn(paramString));
  }
  
  public Timestamp getTimestamp(String paramString) throws SQLException {
    return getTimestamp(findColumn(paramString));
  }
  
  public InputStream getAsciiStream(String paramString) throws SQLException {
    return getAsciiStream(findColumn(paramString));
  }
  
  @Deprecated
  public InputStream getUnicodeStream(String paramString) throws SQLException {
    return getUnicodeStream(findColumn(paramString));
  }
  
  public InputStream getBinaryStream(String paramString) throws SQLException {
    return getBinaryStream(findColumn(paramString));
  }
  
  public void clearWarnings() throws SQLException {}
  
  public String getCursorName() throws SQLException {
    throw TbError.newSQLException(-90201);
  }
  
  public ResultSetMetaData getMetaData() throws SQLException {
    throw TbError.newSQLException(-90201);
  }
  
  public Object getObject(int paramInt) throws SQLException {
    int j;
    checkColumnIndex(paramInt);
    if (setLastColumnIsNull(paramInt))
      return null; 
    if (!this.fromBytes && paramInt == 2)
      return this.data[this.currentIndex]; 
    int i = getType(paramInt);
    byte[] arrayOfByte = getData(paramInt);
    if (i == 16) {
      j = -1;
    } else {
      j = DataType.getSqlType(i);
    } 
    return this.typeConverter.toObject(arrayOfByte, 0, arrayOfByte.length, i, j, false);
  }
  
  public Object getObject(String paramString) throws SQLException {
    return getObject(findColumn(paramString));
  }
  
  public int findColumn(String paramString) throws SQLException {
    if (paramString.equalsIgnoreCase("index"))
      return 1; 
    if (paramString.equalsIgnoreCase("value"))
      return 2; 
    throw TbError.newSQLException(-90611, paramString);
  }
  
  public Reader getCharacterStream(int paramInt) throws SQLException {
    checkColumnIndex(paramInt);
    if (setLastColumnIsNull(paramInt))
      return null; 
    if (paramInt == 2) {
      if (this.data[this.currentIndex] instanceof Reader)
        return (Reader)this.data[this.currentIndex]; 
      if (this.data[this.currentIndex] instanceof Clob)
        return ((TbClob)this.data[this.currentIndex]).getCharacterStream(); 
    } 
    int i = getType(paramInt);
    byte[] arrayOfByte = getData(paramInt);
    return this.typeConverter.toCharacterStream(arrayOfByte, 0, arrayOfByte.length, i);
  }
  
  public Reader getCharacterStream(String paramString) throws SQLException {
    return getCharacterStream(findColumn(paramString));
  }
  
  public BigDecimal getBigDecimal(int paramInt) throws SQLException {
    checkColumnIndex(paramInt);
    if (setLastColumnIsNull(paramInt))
      return null; 
    int i = getType(paramInt);
    byte[] arrayOfByte = getData(paramInt);
    return this.typeConverter.toBigDecimal(arrayOfByte, 0, arrayOfByte.length, i);
  }
  
  public BigDecimal getBigDecimal(String paramString) throws SQLException {
    return getBigDecimal(findColumn(paramString));
  }
  
  public boolean isBeforeFirst() throws SQLException {
    return (this.currentIndex < 0);
  }
  
  public boolean isAfterLast() throws SQLException {
    return (this.currentIndex > this.lastIndex);
  }
  
  public boolean isFirst() throws SQLException {
    return (this.currentIndex == 0);
  }
  
  public boolean isLast() throws SQLException {
    return (this.currentIndex == this.lastIndex);
  }
  
  public void beforeFirst() throws SQLException {
    throw TbError.newSQLException(-90201);
  }
  
  public void afterLast() throws SQLException {
    throw TbError.newSQLException(-90201);
  }
  
  public boolean first() throws SQLException {
    throw TbError.newSQLException(-90201);
  }
  
  public boolean last() throws SQLException {
    throw TbError.newSQLException(-90201);
  }
  
  public int getRow() throws SQLException {
    return (this.lastIndex == 0 || this.currentIndex < 0 || this.currentIndex > this.lastIndex) ? 0 : (this.currentIndex + 1);
  }
  
  public boolean absolute(int paramInt) throws SQLException {
    throw TbError.newSQLException(-90201);
  }
  
  public boolean relative(int paramInt) throws SQLException {
    throw TbError.newSQLException(-90201);
  }
  
  public boolean previous() throws SQLException {
    throw TbError.newSQLException(-90201);
  }
  
  public void setFetchDirection(int paramInt) throws SQLException {
    throw TbError.newSQLException(-90201);
  }
  
  public int getFetchDirection() throws SQLException {
    return 1000;
  }
  
  public void setFetchSize(int paramInt) throws SQLException {
    if (paramInt == 0) {
      this.fetchSize = 50;
    } else if (paramInt > 0) {
      this.fetchSize = paramInt;
    } else {
      throw TbError.newSQLException(-590735);
    } 
  }
  
  public int getFetchSize() throws SQLException {
    return this.fetchSize;
  }
  
  public int getType() throws SQLException {
    return 1003;
  }
  
  public int getConcurrency() throws SQLException {
    return 1008;
  }
  
  public boolean rowUpdated() throws SQLException {
    return false;
  }
  
  public boolean rowInserted() throws SQLException {
    return false;
  }
  
  public boolean rowDeleted() throws SQLException {
    return false;
  }
  
  public void updateNull(int paramInt) throws SQLException {
    throw TbError.newSQLException(-90621);
  }
  
  public void updateBoolean(int paramInt, boolean paramBoolean) throws SQLException {
    throw TbError.newSQLException(-90621);
  }
  
  public void updateByte(int paramInt, byte paramByte) throws SQLException {
    throw TbError.newSQLException(-90621);
  }
  
  public void updateShort(int paramInt, short paramShort) throws SQLException {
    throw TbError.newSQLException(-90621);
  }
  
  public void updateInt(int paramInt1, int paramInt2) throws SQLException {
    throw TbError.newSQLException(-90621);
  }
  
  public void updateLong(int paramInt, long paramLong) throws SQLException {
    throw TbError.newSQLException(-90621);
  }
  
  public void updateFloat(int paramInt, float paramFloat) throws SQLException {
    throw TbError.newSQLException(-90621);
  }
  
  public void updateDouble(int paramInt, double paramDouble) throws SQLException {
    throw TbError.newSQLException(-90621);
  }
  
  public void updateBigDecimal(int paramInt, BigDecimal paramBigDecimal) throws SQLException {
    throw TbError.newSQLException(-90621);
  }
  
  public void updateString(int paramInt, String paramString) throws SQLException {
    throw TbError.newSQLException(-90621);
  }
  
  public void updateBytes(int paramInt, byte[] paramArrayOfbyte) throws SQLException {
    throw TbError.newSQLException(-90621);
  }
  
  public void updateDate(int paramInt, Date paramDate) throws SQLException {
    throw TbError.newSQLException(-90621);
  }
  
  public void updateTime(int paramInt, Time paramTime) throws SQLException {
    throw TbError.newSQLException(-90621);
  }
  
  public void updateTimestamp(int paramInt, Timestamp paramTimestamp) throws SQLException {
    throw TbError.newSQLException(-90621);
  }
  
  public void updateAsciiStream(int paramInt1, InputStream paramInputStream, int paramInt2) throws SQLException {
    throw TbError.newSQLException(-90621);
  }
  
  public void updateBinaryStream(int paramInt1, InputStream paramInputStream, int paramInt2) throws SQLException {
    throw TbError.newSQLException(-90621);
  }
  
  public void updateCharacterStream(int paramInt1, Reader paramReader, int paramInt2) throws SQLException {
    throw TbError.newSQLException(-90621);
  }
  
  public void updateObject(int paramInt1, Object paramObject, int paramInt2) throws SQLException {
    throw TbError.newSQLException(-90621);
  }
  
  public void updateObject(int paramInt, Object paramObject) throws SQLException {
    throw TbError.newSQLException(-90621);
  }
  
  public void updateNull(String paramString) throws SQLException {
    throw TbError.newSQLException(-90621);
  }
  
  public void updateBoolean(String paramString, boolean paramBoolean) throws SQLException {
    throw TbError.newSQLException(-90621);
  }
  
  public void updateByte(String paramString, byte paramByte) throws SQLException {
    throw TbError.newSQLException(-90621);
  }
  
  public void updateShort(String paramString, short paramShort) throws SQLException {
    throw TbError.newSQLException(-90621);
  }
  
  public void updateInt(String paramString, int paramInt) throws SQLException {
    throw TbError.newSQLException(-90621);
  }
  
  public void updateLong(String paramString, long paramLong) throws SQLException {
    throw TbError.newSQLException(-90621);
  }
  
  public void updateFloat(String paramString, float paramFloat) throws SQLException {
    throw TbError.newSQLException(-90621);
  }
  
  public void updateDouble(String paramString, double paramDouble) throws SQLException {
    throw TbError.newSQLException(-90621);
  }
  
  public void updateBigDecimal(String paramString, BigDecimal paramBigDecimal) throws SQLException {
    throw TbError.newSQLException(-90621);
  }
  
  public void updateString(String paramString1, String paramString2) throws SQLException {
    throw TbError.newSQLException(-90621);
  }
  
  public void updateBytes(String paramString, byte[] paramArrayOfbyte) throws SQLException {
    throw TbError.newSQLException(-90621);
  }
  
  public void updateDate(String paramString, Date paramDate) throws SQLException {
    throw TbError.newSQLException(-90621);
  }
  
  public void updateTime(String paramString, Time paramTime) throws SQLException {
    throw TbError.newSQLException(-90621);
  }
  
  public void updateTimestamp(String paramString, Timestamp paramTimestamp) throws SQLException {
    throw TbError.newSQLException(-90621);
  }
  
  public void updateAsciiStream(String paramString, InputStream paramInputStream, int paramInt) throws SQLException {
    throw TbError.newSQLException(-90621);
  }
  
  public void updateBinaryStream(String paramString, InputStream paramInputStream, int paramInt) throws SQLException {
    throw TbError.newSQLException(-90621);
  }
  
  public void updateCharacterStream(String paramString, Reader paramReader, int paramInt) throws SQLException {
    throw TbError.newSQLException(-90621);
  }
  
  public void updateObject(String paramString, Object paramObject, int paramInt) throws SQLException {
    throw TbError.newSQLException(-90621);
  }
  
  public void updateObject(String paramString, Object paramObject) throws SQLException {
    throw TbError.newSQLException(-90621);
  }
  
  public void insertRow() throws SQLException {
    throw TbError.newSQLException(-90621);
  }
  
  public void updateRow() throws SQLException {
    throw TbError.newSQLException(-90621);
  }
  
  public void deleteRow() throws SQLException {
    throw TbError.newSQLException(-90621);
  }
  
  public void refreshRow() throws SQLException {
    throw TbError.newSQLException(-90201);
  }
  
  public void cancelRowUpdates() throws SQLException {
    throw TbError.newSQLException(-90621);
  }
  
  public void moveToInsertRow() throws SQLException {
    throw TbError.newSQLException(-90621);
  }
  
  public void moveToCurrentRow() throws SQLException {
    throw TbError.newSQLException(-90621);
  }
  
  public Statement getStatement() throws SQLException {
    return null;
  }
  
  public Object getObject(int paramInt, Map<String, Class<?>> paramMap) throws SQLException {
    int j;
    Map<String, Class<?>> map = paramMap;
    checkColumnIndex(paramInt);
    if (setLastColumnIsNull(paramInt))
      return null; 
    Object object = new Object();
    if (!this.fromBytes && paramInt == 2) {
      object = this.data[this.currentIndex];
      if (this.conn != null && this.data[this.currentIndex] instanceof java.sql.SQLData)
        object = TbStruct.toStruct(this.data[this.currentIndex], (Connection)this.conn); 
      if (map != null && object instanceof java.sql.Struct) {
        Class<?> clazz = ((TbStruct)object).getDescriptor().getClassWithExplicitMap(map);
        if (clazz != null)
          object = ((TbStruct)object).toClass(clazz, map); 
      } 
      return object;
    } 
    int i = getType(paramInt);
    byte[] arrayOfByte = getData(paramInt);
    if (i == 16) {
      j = -1;
    } else {
      j = DataType.getSqlType(i);
    } 
    return this.typeConverter.toObject(arrayOfByte, 0, arrayOfByte.length, i, j, false);
  }
  
  public Ref getRef(int paramInt) throws SQLException {
    checkColumnIndex(paramInt);
    if (setLastColumnIsNull(paramInt))
      return null; 
    if (paramInt == 2 && this.data[this.currentIndex] instanceof Ref)
      return (Ref)this.data[this.currentIndex]; 
    int i = getType(paramInt);
    byte[] arrayOfByte = getData(paramInt);
    return this.typeConverter.toRef(arrayOfByte, 0, arrayOfByte.length, i);
  }
  
  public Blob getBlob(int paramInt) throws SQLException {
    checkColumnIndex(paramInt);
    if (setLastColumnIsNull(paramInt))
      return null; 
    if (paramInt == 2 && this.data[this.currentIndex] instanceof Blob)
      return (Blob)this.data[this.currentIndex]; 
    int i = getType(paramInt);
    byte[] arrayOfByte = getData(paramInt);
    return this.typeConverter.toBlob(arrayOfByte, 0, arrayOfByte.length, i, false);
  }
  
  public Clob getClob(int paramInt) throws SQLException {
    checkColumnIndex(paramInt);
    if (setLastColumnIsNull(paramInt))
      return null; 
    if (paramInt == 2 && this.data[this.currentIndex] instanceof Clob)
      return (Clob)this.data[this.currentIndex]; 
    int i = getType(paramInt);
    byte[] arrayOfByte = getData(paramInt);
    return this.typeConverter.toClob(arrayOfByte, 0, arrayOfByte.length, i, false);
  }
  
  public Array getArray(int paramInt) throws SQLException {
    checkColumnIndex(paramInt);
    if (setLastColumnIsNull(paramInt))
      return null; 
    if (paramInt == 2 && this.data[this.currentIndex] instanceof Array)
      return (Array)this.data[this.currentIndex]; 
    int i = getType(paramInt);
    byte[] arrayOfByte = getData(paramInt);
    return this.typeConverter.toArray(arrayOfByte, 0, arrayOfByte.length, i, false, null, null);
  }
  
  public Object getObject(String paramString, Map<String, Class<?>> paramMap) throws SQLException {
    return getObject(findColumn(paramString), paramMap);
  }
  
  public Ref getRef(String paramString) throws SQLException {
    return getRef(findColumn(paramString));
  }
  
  public Blob getBlob(String paramString) throws SQLException {
    return getBlob(findColumn(paramString));
  }
  
  public Clob getClob(String paramString) throws SQLException {
    return getClob(findColumn(paramString));
  }
  
  public Array getArray(String paramString) throws SQLException {
    return getArray(findColumn(paramString));
  }
  
  public Date getDate(int paramInt, Calendar paramCalendar) throws SQLException {
    Date date = getDate(paramInt);
    if (paramCalendar != null) {
      paramCalendar.setTime(date);
      date = (Date)paramCalendar.getTime();
    } 
    return date;
  }
  
  public Date getDate(String paramString, Calendar paramCalendar) throws SQLException {
    return getDate(findColumn(paramString), paramCalendar);
  }
  
  public Time getTime(int paramInt, Calendar paramCalendar) throws SQLException {
    Time time = getTime(paramInt);
    if (paramCalendar != null) {
      paramCalendar.setTime(time);
      time = (Time)paramCalendar.getTime();
    } 
    return time;
  }
  
  public Time getTime(String paramString, Calendar paramCalendar) throws SQLException {
    return getTime(findColumn(paramString), paramCalendar);
  }
  
  public Timestamp getTimestamp(int paramInt, Calendar paramCalendar) throws SQLException {
    Timestamp timestamp = getTimestamp(paramInt);
    if (paramCalendar != null) {
      paramCalendar.setTime(timestamp);
      timestamp = (Timestamp)paramCalendar.getTime();
    } 
    return timestamp;
  }
  
  public Timestamp getTimestamp(String paramString, Calendar paramCalendar) throws SQLException {
    return getTimestamp(findColumn(paramString), paramCalendar);
  }
  
  public URL getURL(int paramInt) throws SQLException {
    throw TbError.newSQLException(-90201);
  }
  
  public URL getURL(String paramString) throws SQLException {
    return getURL(findColumn(paramString));
  }
  
  public void updateRef(int paramInt, Ref paramRef) throws SQLException {
    throw TbError.newSQLException(-90621);
  }
  
  public void updateRef(String paramString, Ref paramRef) throws SQLException {
    throw TbError.newSQLException(-90621);
  }
  
  public void updateBlob(int paramInt, Blob paramBlob) throws SQLException {
    throw TbError.newSQLException(-90621);
  }
  
  public void updateBlob(String paramString, Blob paramBlob) throws SQLException {
    throw TbError.newSQLException(-90621);
  }
  
  public void updateClob(int paramInt, Clob paramClob) throws SQLException {
    throw TbError.newSQLException(-90621);
  }
  
  public void updateClob(String paramString, Clob paramClob) throws SQLException {
    throw TbError.newSQLException(-90621);
  }
  
  public void updateArray(int paramInt, Array paramArray) throws SQLException {
    throw TbError.newSQLException(-90621);
  }
  
  public void updateArray(String paramString, Array paramArray) throws SQLException {
    throw TbError.newSQLException(-90621);
  }
  
  public RowId getRowId(int paramInt) throws SQLException {
    checkColumnIndex(paramInt);
    if (setLastColumnIsNull(paramInt))
      return null; 
    int i = getType(paramInt);
    byte[] arrayOfByte = getData(paramInt);
    return this.typeConverter.toRowId(arrayOfByte, 0, arrayOfByte.length, i);
  }
  
  public RowId getRowId(String paramString) throws SQLException {
    return getRowId(findColumn(paramString));
  }
  
  public void updateRowId(int paramInt, RowId paramRowId) throws SQLException {
    throw TbError.newSQLException(-90621);
  }
  
  public void updateRowId(String paramString, RowId paramRowId) throws SQLException {
    throw TbError.newSQLException(-90621);
  }
  
  public int getHoldability() throws SQLException {
    return 0;
  }
  
  public boolean isClosed() throws SQLException {
    return this.isRsetClosed;
  }
  
  public void updateNString(int paramInt, String paramString) throws SQLException {
    throw TbError.newSQLException(-90621);
  }
  
  public void updateNString(String paramString1, String paramString2) throws SQLException {
    throw TbError.newSQLException(-90621);
  }
  
  public void updateNClob(int paramInt, NClob paramNClob) throws SQLException {
    throw TbError.newSQLException(-90621);
  }
  
  public void updateNClob(String paramString, NClob paramNClob) throws SQLException {
    throw TbError.newSQLException(-90621);
  }
  
  public NClob getNClob(int paramInt) throws SQLException {
    checkColumnIndex(paramInt);
    if (setLastColumnIsNull(paramInt))
      return null; 
    if (paramInt == 2 && this.data[this.currentIndex] instanceof NClob)
      return (NClob)this.data[this.currentIndex]; 
    int i = getType(paramInt);
    byte[] arrayOfByte = getData(paramInt);
    return this.typeConverter.toNClob(arrayOfByte, 0, arrayOfByte.length, i);
  }
  
  public NClob getNClob(String paramString) throws SQLException {
    return getNClob(findColumn(paramString));
  }
  
  public SQLXML getSQLXML(int paramInt) throws SQLException {
    checkColumnIndex(paramInt);
    if (setLastColumnIsNull(paramInt))
      return null; 
    if (paramInt == 2) {
      if (this.data[this.currentIndex] instanceof SQLXML)
        return (SQLXML)this.data[this.currentIndex]; 
      if (this.data[this.currentIndex] instanceof TbStruct) {
        TbStructDescriptor tbStructDescriptor = ((TbStruct)this.data[this.currentIndex]).getDescriptor();
        if (tbStructDescriptor.getOID().compareTo("00000000000000000000000000000001") == 0) {
          Object object = ((TbStruct)this.data[this.currentIndex]).getAttributes()[0];
          if (object instanceof TbClob) {
            TbXMLInputStream tbXMLInputStream = new TbXMLInputStream((TbClob)object);
            return new TbSQLXML(this.conn, tbXMLInputStream);
          } 
        } 
      } 
      if (this.data[this.currentIndex] instanceof TbClob) {
        TbXMLInputStream tbXMLInputStream = new TbXMLInputStream((TbClob)this.data[this.currentIndex]);
        return new TbSQLXML(this.conn, tbXMLInputStream);
      } 
    } 
    int i = getType(paramInt);
    byte[] arrayOfByte = getData(paramInt);
    return this.typeConverter.toSQLXML(arrayOfByte, 0, arrayOfByte.length, i, false);
  }
  
  public SQLXML getSQLXML(String paramString) throws SQLException {
    return getSQLXML(findColumn(paramString));
  }
  
  public void updateSQLXML(int paramInt, SQLXML paramSQLXML) throws SQLException {
    throw TbError.newSQLException(-90621);
  }
  
  public void updateSQLXML(String paramString, SQLXML paramSQLXML) throws SQLException {
    throw TbError.newSQLException(-90621);
  }
  
  public String getNString(int paramInt) throws SQLException {
    checkColumnIndex(paramInt);
    if (setLastColumnIsNull(paramInt))
      return null; 
    if (this.data[this.currentIndex] instanceof TbNClob && paramInt == 2) {
      long l = ((TbNClob)this.data[this.currentIndex]).length();
      return ((TbNClob)this.data[this.currentIndex]).getSubString(1L, (int)l);
    } 
    int i = getType(paramInt);
    byte[] arrayOfByte = getData(paramInt);
    return this.typeConverter.toString(arrayOfByte, 0, arrayOfByte.length, i, -1, -1, false);
  }
  
  public String getNString(String paramString) throws SQLException {
    return getNString(findColumn(paramString));
  }
  
  public Reader getNCharacterStream(int paramInt) throws SQLException {
    checkColumnIndex(paramInt);
    if (setLastColumnIsNull(paramInt))
      return null; 
    if (paramInt == 2) {
      if (this.data[this.currentIndex] instanceof Reader)
        return (Reader)this.data[this.currentIndex]; 
      if (this.data[this.currentIndex] instanceof NClob)
        return ((TbNClob)this.data[this.currentIndex]).getCharacterStream(); 
    } 
    int i = getType(paramInt);
    byte[] arrayOfByte = getData(paramInt);
    return this.typeConverter.toCharacterStream(arrayOfByte, 0, arrayOfByte.length, i);
  }
  
  public Reader getNCharacterStream(String paramString) throws SQLException {
    return getNCharacterStream(findColumn(paramString));
  }
  
  public void updateNCharacterStream(int paramInt, Reader paramReader, long paramLong) throws SQLException {
    throw TbError.newSQLException(-90621);
  }
  
  public void updateNCharacterStream(String paramString, Reader paramReader, long paramLong) throws SQLException {
    throw TbError.newSQLException(-90621);
  }
  
  public void updateAsciiStream(int paramInt, InputStream paramInputStream, long paramLong) throws SQLException {
    throw TbError.newSQLException(-90621);
  }
  
  public void updateBinaryStream(int paramInt, InputStream paramInputStream, long paramLong) throws SQLException {
    throw TbError.newSQLException(-90621);
  }
  
  public void updateCharacterStream(int paramInt, Reader paramReader, long paramLong) throws SQLException {
    throw TbError.newSQLException(-90621);
  }
  
  public void updateAsciiStream(String paramString, InputStream paramInputStream, long paramLong) throws SQLException {
    throw TbError.newSQLException(-90621);
  }
  
  public void updateBinaryStream(String paramString, InputStream paramInputStream, long paramLong) throws SQLException {
    throw TbError.newSQLException(-90621);
  }
  
  public void updateCharacterStream(String paramString, Reader paramReader, long paramLong) throws SQLException {
    throw TbError.newSQLException(-90621);
  }
  
  public void updateBlob(int paramInt, InputStream paramInputStream, long paramLong) throws SQLException {
    throw TbError.newSQLException(-90621);
  }
  
  public void updateBlob(String paramString, InputStream paramInputStream, long paramLong) throws SQLException {
    throw TbError.newSQLException(-90621);
  }
  
  public void updateClob(int paramInt, Reader paramReader, long paramLong) throws SQLException {
    throw TbError.newSQLException(-90621);
  }
  
  public void updateClob(String paramString, Reader paramReader, long paramLong) throws SQLException {
    throw TbError.newSQLException(-90621);
  }
  
  public void updateNClob(int paramInt, Reader paramReader, long paramLong) throws SQLException {
    throw TbError.newSQLException(-90621);
  }
  
  public void updateNClob(String paramString, Reader paramReader, long paramLong) throws SQLException {
    throw TbError.newSQLException(-90621);
  }
  
  public void updateNCharacterStream(int paramInt, Reader paramReader) throws SQLException {
    throw TbError.newSQLException(-90621);
  }
  
  public void updateNCharacterStream(String paramString, Reader paramReader) throws SQLException {
    throw TbError.newSQLException(-90621);
  }
  
  public void updateAsciiStream(int paramInt, InputStream paramInputStream) throws SQLException {
    throw TbError.newSQLException(-90621);
  }
  
  public void updateBinaryStream(int paramInt, InputStream paramInputStream) throws SQLException {
    throw TbError.newSQLException(-90621);
  }
  
  public void updateCharacterStream(int paramInt, Reader paramReader) throws SQLException {
    throw TbError.newSQLException(-90621);
  }
  
  public void updateAsciiStream(String paramString, InputStream paramInputStream) throws SQLException {
    throw TbError.newSQLException(-90621);
  }
  
  public void updateBinaryStream(String paramString, InputStream paramInputStream) throws SQLException {
    throw TbError.newSQLException(-90621);
  }
  
  public void updateCharacterStream(String paramString, Reader paramReader) throws SQLException {
    throw TbError.newSQLException(-90621);
  }
  
  public void updateBlob(int paramInt, InputStream paramInputStream) throws SQLException {
    throw TbError.newSQLException(-90621);
  }
  
  public void updateBlob(String paramString, InputStream paramInputStream) throws SQLException {
    throw TbError.newSQLException(-90621);
  }
  
  public void updateClob(int paramInt, Reader paramReader) throws SQLException {
    throw TbError.newSQLException(-90621);
  }
  
  public void updateClob(String paramString, Reader paramReader) throws SQLException {
    throw TbError.newSQLException(-90621);
  }
  
  public void updateNClob(int paramInt, Reader paramReader) throws SQLException {
    throw TbError.newSQLException(-90621);
  }
  
  public void updateNClob(String paramString, Reader paramReader) throws SQLException {
    throw TbError.newSQLException(-90621);
  }
  
  public void addWarning(SQLWarning paramSQLWarning) {}
  
  public void buildRowTable(int paramInt, byte[] paramArrayOfbyte) throws SQLException {
    throw TbError.newSQLException(-90201);
  }
  
  public int getBytes(int paramInt, byte[] paramArrayOfbyte) throws SQLException {
    throw TbError.newSQLException(-90201);
  }
  
  public TbRAW getRAW(int paramInt) throws SQLException {
    return new TbRAW(getBytes(paramInt));
  }
  
  public TbRAW getRAW(String paramString) throws SQLException {
    return new TbRAW(getBytes(paramString));
  }
  
  public Column[] getCols() throws SQLException {
    return null;
  }
  
  public InputStream getLongByteStream(int paramInt) throws SQLException {
    throw TbError.newSQLException(-90201);
  }
  
  public byte[] getRowChunk(int paramInt) throws SQLException {
    return null;
  }
  
  public RsetType getRsetType() {
    return null;
  }
  
  public long getUpdateCount() {
    return 0L;
  }
  
  public TbDate getTbDate(int paramInt) throws SQLException {
    checkColumnIndex(paramInt);
    if (setLastColumnIsNull(paramInt))
      return null; 
    int i = getType(paramInt);
    byte[] arrayOfByte = getData(paramInt);
    return this.typeConverter.toTbDate(arrayOfByte, 0, arrayOfByte.length, i);
  }
  
  public TbTimestamp getTbTimestamp(int paramInt) throws SQLException {
    checkColumnIndex(paramInt);
    if (setLastColumnIsNull(paramInt))
      return null; 
    int i = getType(paramInt);
    byte[] arrayOfByte = getData(paramInt);
    return this.typeConverter.toTbTimestamp(arrayOfByte, 0, arrayOfByte.length, i);
  }
  
  public void updateRAW(int paramInt, TbRAW paramTbRAW) throws SQLException {
    throw TbError.newSQLException(-90621);
  }
  
  public void updateRAW(String paramString, TbRAW paramTbRAW) throws SQLException {
    throw TbError.newSQLException(-90621);
  }
  
  public void updateTbTimestamp(int paramInt, TbTimestamp paramTbTimestamp) throws SQLException {
    throw TbError.newSQLException(-90621);
  }
  
  public void updateTbTimestamp(String paramString, TbTimestamp paramTbTimestamp) throws SQLException {
    throw TbError.newSQLException(-90621);
  }
  
  public SQLWarning getWarnings() throws SQLException {
    return null;
  }
  
  public boolean isWrapperFor(Class<?> paramClass) throws SQLException {
    return paramClass.isInstance(this);
  }
  
  public void setFetchCompleted(int paramInt) throws SQLException {}
  
  public void setHaveLocator(boolean paramBoolean) {}
  
  public void setRsetType(RsetType paramRsetType) {}
  
  public <T> T unwrap(Class<T> paramClass) throws SQLException {
    try {
      return paramClass.cast(this);
    } catch (ClassCastException classCastException) {
      throw TbError.newSQLException(-90657);
    } 
  }
  
  private byte[] getData(int paramInt) {
    return (paramInt == 1) ? this.index : (this.fromBytes ? this.value : new byte[] { 0 });
  }
  
  private int getType(int paramInt) {
    return (paramInt == 1) ? 1 : this.dataType;
  }
  
  private void checkColumnIndex(int paramInt) throws SQLException {
    if (paramInt <= 0 || paramInt > 2)
      throw TbError.newSQLException(-90609); 
  }
  
  private boolean setLastColumnIsNull(int paramInt) {
    return (paramInt == 1) ? (this.lastColumnWasNull = false) : (this.lastColumnWasNull = this.isNullData);
  }
}


/* Location:              C:\TmaxData\tibero6\client\lib\jar\tibero6-jdbc.jar!\com\tmax\tibero\jdbc\TbArrayDataResultSet.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */