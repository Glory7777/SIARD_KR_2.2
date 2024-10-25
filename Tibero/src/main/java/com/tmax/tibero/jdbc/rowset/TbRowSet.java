package com.tmax.tibero.jdbc.rowset;

import com.tmax.tibero.jdbc.err.TbError;
import com.tmax.tibero.jdbc.util.TbSQLParser;
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
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;
import javax.sql.RowSet;
import javax.sql.RowSetEvent;
import javax.sql.RowSetListener;

public abstract class TbRowSet implements RowSet {
  protected String url;
  
  protected String dataSourceName;
  
  protected String username;
  
  protected String password;
  
  protected int isolation;
  
  protected String command;
  
  protected boolean isClosed;
  
  protected boolean readOnly;
  
  protected boolean showDeleted;
  
  protected int maxFieldSize;
  
  protected int maxRows;
  
  protected boolean escapeProcessing;
  
  protected int queryTimeout;
  
  protected int rowsetType;
  
  protected int concurrency;
  
  protected int fetchDirection;
  
  protected int fetchSize;
  
  protected String tableName;
  
  protected Vector<RowSetListener> listeners;
  
  protected Map<String, Class<?>> typeMap;
  
  private Vector<Integer> matchColumnIndexes;
  
  private Vector<String> matchColumnNames;
  
  protected Vector<Object> params;
  
  protected SQLWarning warnings;
  
  protected String[] paramNames;
  
  protected RowSetEvent rowsetEvent;
  
  static final int PARAM_NSTRING = 0;
  
  static final int PARAM_ASCII = 1;
  
  static final int PARAM_BINARY = 2;
  
  static final int PARAM_CHARACTER = 3;
  
  static final int PARAM_NCHARACTER = 4;
  
  static final int PARAM_BLOB = 5;
  
  static final int PARAM_CLOB = 6;
  
  static final int PARAM_NCLOB = 7;
  
  public TbRowSet() {
    initialize();
    this.listeners = new Vector<RowSetListener>();
    this.matchColumnIndexes = new Vector<Integer>(10);
    this.matchColumnNames = new Vector<String>(10);
    this.rowsetEvent = new RowSetEvent(this);
    this.isClosed = false;
  }
  
  public void addRowSetListener(RowSetListener paramRowSetListener) {
    this.listeners.add(paramRowSetListener);
  }
  
  public void addWarning(SQLWarning paramSQLWarning) {
    if (this.warnings != null) {
      this.warnings.setNextWarning(paramSQLWarning);
    } else {
      this.warnings = paramSQLWarning;
    } 
  }
  
  protected void checkMatchColumnIndexesExist() throws SQLException {
    if (this.matchColumnIndexes.size() == 0)
      throw TbError.newSQLException(-90860); 
  }
  
  protected void checkMatchColumnNamesExist() throws SQLException {
    if (this.matchColumnNames.size() == 0)
      throw TbError.newSQLException(-90861); 
  }
  
  private void checkParameterIndex(int paramInt) throws SQLException {
    if (paramInt < 1)
      throw TbError.newSQLException(-90844, Integer.toString(paramInt)); 
  }
  
  public synchronized void clearParameters() throws SQLException {
    this.params = null;
    this.params = new Vector();
  }
  
  public synchronized void clearWarnings() {
    this.warnings = null;
  }
  
  public abstract void execute() throws SQLException;
  
  int findParam(String paramString) throws SQLException {
    byte b = 0;
    for (b = 0; b < this.paramNames.length && !this.paramNames[b].equals(paramString); b++);
    if (b == this.paramNames.length)
      throw TbError.newSQLException(-90845, paramString); 
    return b + 1;
  }
  
  public String getCommand() {
    return this.command;
  }
  
  public int getConcurrency() throws SQLException {
    return this.concurrency;
  }
  
  public String getDataSourceName() {
    return this.dataSourceName;
  }
  
  public boolean getEscapeProcessing() {
    return this.escapeProcessing;
  }
  
  public int getFetchDirection() {
    return this.fetchDirection;
  }
  
  public int getFetchSize() {
    return this.fetchSize;
  }
  
  public int[] getMatchColumnIndexes() throws SQLException {
    int[] arrayOfInt;
    if (this.matchColumnIndexes.size() == 0 && this.matchColumnNames.size() == 0)
      throw TbError.newSQLException(-90860); 
    if (this.matchColumnNames.size() > 0) {
      String[] arrayOfString = getMatchColumnNames();
      int i = arrayOfString.length;
      arrayOfInt = new int[i];
      for (byte b = 0; b < i; b++)
        arrayOfInt[b] = findColumn(arrayOfString[b]); 
    } else {
      int i = this.matchColumnIndexes.size();
      arrayOfInt = new int[i];
      int j = -1;
      for (byte b = 0; b < i; b++) {
        try {
          j = ((Integer)this.matchColumnIndexes.get(b)).intValue();
        } catch (Exception exception) {
          throw TbError.newSQLException(-90840, exception.getMessage());
        } 
        if (j <= 0)
          throw TbError.newSQLException(-90840); 
        arrayOfInt[b] = j;
      } 
    } 
    return arrayOfInt;
  }
  
  public String[] getMatchColumnNames() throws SQLException {
    checkMatchColumnNamesExist();
    int i = this.matchColumnNames.size();
    String[] arrayOfString = new String[i];
    String str = null;
    for (byte b = 0; b < i; b++) {
      try {
        str = this.matchColumnNames.get(b);
      } catch (Exception exception) {
        throw TbError.newSQLException(-90841, exception.getMessage());
      } 
      if (str == null || str.equals(""))
        throw TbError.newSQLException(-90841); 
      arrayOfString[b] = str;
    } 
    return arrayOfString;
  }
  
  public int getMaxFieldSize() {
    return this.maxFieldSize;
  }
  
  public int getMaxRows() {
    return this.maxRows;
  }
  
  public Object[] getParams() {
    return (this.params == null) ? new Object[0] : this.params.toArray();
  }
  
  public String getPassword() {
    return this.password;
  }
  
  public int getQueryTimeout() {
    return this.queryTimeout;
  }
  
  public boolean getShowDeleted() throws SQLException {
    return this.showDeleted;
  }
  
  public String getTableName() throws SQLException {
    return this.tableName;
  }
  
  public void setTableName(String paramString) throws SQLException {
    this.tableName = paramString;
  }
  
  public int getTransactionIsolation() {
    return this.isolation;
  }
  
  public int getType() {
    return this.rowsetType;
  }
  
  public Map<String, Class<?>> getTypeMap() {
    return this.typeMap;
  }
  
  public String getUrl() {
    return this.url;
  }
  
  public String getUsername() {
    return this.username;
  }
  
  public SQLWarning getWarnings() {
    return this.warnings;
  }
  
  protected void initialize() {
    this.command = null;
    this.dataSourceName = null;
    this.dataSourceName = null;
    this.escapeProcessing = true;
    this.fetchSize = 0;
    this.maxFieldSize = 0;
    this.maxRows = 0;
    this.queryTimeout = 0;
    this.readOnly = true;
    this.showDeleted = false;
    this.username = null;
    this.password = null;
    this.url = null;
    this.fetchDirection = 1002;
    this.rowsetType = 1005;
    this.concurrency = 1007;
    this.isolation = 2;
    this.params = new Vector();
    this.typeMap = new HashMap<String, Class<?>>();
  }
  
  public boolean isClosed() throws SQLException {
    return this.isClosed;
  }
  
  public boolean isReadOnly() {
    return this.readOnly;
  }
  
  public boolean isShowDeleted() {
    return this.showDeleted;
  }
  
  protected void notifyCursorMoved() {
    Iterator<RowSetListener> iterator = this.listeners.iterator();
    while (iterator.hasNext())
      ((RowSetListener)iterator.next()).cursorMoved(this.rowsetEvent); 
  }
  
  protected void notifyRowChanged() {
    Iterator<RowSetListener> iterator = this.listeners.iterator();
    while (iterator.hasNext())
      ((RowSetListener)iterator.next()).rowChanged(this.rowsetEvent); 
  }
  
  protected void notifyRowSetChanged() {
    Iterator<RowSetListener> iterator = this.listeners.iterator();
    while (iterator.hasNext())
      ((RowSetListener)iterator.next()).rowSetChanged(this.rowsetEvent); 
  }
  
  public void removeRowSetListener(RowSetListener paramRowSetListener) {
    this.listeners.remove(paramRowSetListener);
  }
  
  public void setArray(int paramInt, Array paramArray) throws SQLException {
    checkParameterIndex(paramInt);
    this.params.add(paramInt - 1, paramArray);
  }
  
  public void setArray(String paramString, Array paramArray) throws SQLException {
    setArray(findParam(paramString), paramArray);
  }
  
  public void setAsciiStream(int paramInt, InputStream paramInputStream) throws SQLException {
    checkParameterIndex(paramInt);
    Object[] arrayOfObject = new Object[3];
    arrayOfObject[0] = paramInputStream;
    arrayOfObject[1] = new Integer(2147483647);
    arrayOfObject[2] = new Integer(1);
    this.params.add(paramInt - 1, arrayOfObject);
  }
  
  public void setAsciiStream(int paramInt1, InputStream paramInputStream, int paramInt2) throws SQLException {
    checkParameterIndex(paramInt1);
    Object[] arrayOfObject = new Object[3];
    arrayOfObject[0] = paramInputStream;
    arrayOfObject[1] = new Integer(paramInt2);
    arrayOfObject[2] = new Integer(1);
    this.params.add(paramInt1 - 1, arrayOfObject);
  }
  
  public void setAsciiStream(String paramString, InputStream paramInputStream) throws SQLException {
    setAsciiStream(findParam(paramString), paramInputStream);
  }
  
  public void setAsciiStream(String paramString, InputStream paramInputStream, int paramInt) throws SQLException {
    setAsciiStream(findParam(paramString), paramInputStream, paramInt);
  }
  
  public void setBigDecimal(int paramInt, BigDecimal paramBigDecimal) throws SQLException {
    checkParameterIndex(paramInt);
    this.params.add(paramInt - 1, paramBigDecimal);
  }
  
  public void setBigDecimal(String paramString, BigDecimal paramBigDecimal) throws SQLException {
    setBigDecimal(findParam(paramString), paramBigDecimal);
  }
  
  public void setBinaryStream(int paramInt, InputStream paramInputStream) throws SQLException {
    checkParameterIndex(paramInt);
    Object[] arrayOfObject = new Object[3];
    arrayOfObject[0] = paramInputStream;
    arrayOfObject[1] = new Integer(2147483647);
    arrayOfObject[2] = new Integer(2);
    this.params.add(paramInt - 1, arrayOfObject);
  }
  
  public void setBinaryStream(int paramInt1, InputStream paramInputStream, int paramInt2) throws SQLException {
    checkParameterIndex(paramInt1);
    Object[] arrayOfObject = new Object[3];
    arrayOfObject[0] = paramInputStream;
    arrayOfObject[1] = new Integer(paramInt2);
    arrayOfObject[2] = new Integer(2);
    this.params.add(paramInt1 - 1, arrayOfObject);
  }
  
  public void setBinaryStream(String paramString, InputStream paramInputStream) throws SQLException {
    setBinaryStream(findParam(paramString), paramInputStream);
  }
  
  public void setBinaryStream(String paramString, InputStream paramInputStream, int paramInt) throws SQLException {
    setBinaryStream(findParam(paramString), paramInputStream, paramInt);
  }
  
  public void setBlob(int paramInt, Blob paramBlob) throws SQLException {
    checkParameterIndex(paramInt);
    this.params.add(paramInt - 1, paramBlob);
  }
  
  public void setBlob(int paramInt, InputStream paramInputStream) throws SQLException {
    checkParameterIndex(paramInt);
    Object[] arrayOfObject = new Object[3];
    arrayOfObject[0] = paramInputStream;
    arrayOfObject[1] = new Integer(2147483647);
    arrayOfObject[2] = new Integer(5);
    this.params.add(paramInt - 1, arrayOfObject);
  }
  
  public void setBlob(int paramInt, InputStream paramInputStream, long paramLong) throws SQLException {
    checkParameterIndex(paramInt);
    Object[] arrayOfObject = new Object[3];
    arrayOfObject[0] = paramInputStream;
    arrayOfObject[1] = new Long(paramLong);
    arrayOfObject[2] = new Integer(5);
    this.params.add(paramInt - 1, arrayOfObject);
  }
  
  public void setBlob(String paramString, Blob paramBlob) throws SQLException {
    setBlob(findParam(paramString), paramBlob);
  }
  
  public void setBlob(String paramString, InputStream paramInputStream) throws SQLException {
    setBlob(findParam(paramString), paramInputStream);
  }
  
  public void setBlob(String paramString, InputStream paramInputStream, long paramLong) throws SQLException {
    setBlob(findParam(paramString), paramInputStream, paramLong);
  }
  
  public void setBoolean(int paramInt, boolean paramBoolean) throws SQLException {
    checkParameterIndex(paramInt);
    this.params.add(paramInt - 1, paramBoolean ? Boolean.TRUE : Boolean.FALSE);
  }
  
  public void setBoolean(String paramString, boolean paramBoolean) throws SQLException {
    setBoolean(findParam(paramString), paramBoolean);
  }
  
  public void setByte(int paramInt, byte paramByte) throws SQLException {
    checkParameterIndex(paramInt);
    this.params.add(paramInt - 1, new Byte(paramByte));
  }
  
  public void setByte(String paramString, byte paramByte) throws SQLException {
    setByte(findParam(paramString), paramByte);
  }
  
  public void setBytes(int paramInt, byte[] paramArrayOfbyte) throws SQLException {
    checkParameterIndex(paramInt);
    this.params.add(paramInt - 1, paramArrayOfbyte);
  }
  
  public void setBytes(String paramString, byte[] paramArrayOfbyte) throws SQLException {
    setBytes(findParam(paramString), paramArrayOfbyte);
  }
  
  public void setCharacterStream(int paramInt, Reader paramReader) throws SQLException {
    checkParameterIndex(paramInt);
    Object[] arrayOfObject = new Object[3];
    arrayOfObject[0] = paramReader;
    arrayOfObject[1] = new Integer(2147483647);
    arrayOfObject[2] = new Integer(3);
    this.params.add(paramInt - 1, arrayOfObject);
  }
  
  public void setCharacterStream(int paramInt1, Reader paramReader, int paramInt2) throws SQLException {
    checkParameterIndex(paramInt1);
    Object[] arrayOfObject = new Object[3];
    arrayOfObject[0] = paramReader;
    arrayOfObject[1] = new Integer(paramInt2);
    arrayOfObject[2] = new Integer(3);
    this.params.add(paramInt1 - 1, arrayOfObject);
  }
  
  public void setCharacterStream(String paramString, Reader paramReader) throws SQLException {
    setCharacterStream(findParam(paramString), paramReader);
  }
  
  public void setCharacterStream(String paramString, Reader paramReader, int paramInt) throws SQLException {
    setCharacterStream(findParam(paramString), paramReader, paramInt);
  }
  
  public void setClob(int paramInt, Clob paramClob) throws SQLException {
    checkParameterIndex(paramInt);
    this.params.add(paramInt - 1, paramClob);
  }
  
  public void setClob(int paramInt, Reader paramReader) throws SQLException {
    checkParameterIndex(paramInt);
    Object[] arrayOfObject = new Object[3];
    arrayOfObject[0] = paramReader;
    arrayOfObject[1] = new Integer(2147483647);
    arrayOfObject[2] = new Integer(6);
    this.params.add(paramInt - 1, arrayOfObject);
  }
  
  public void setClob(int paramInt, Reader paramReader, long paramLong) throws SQLException {
    checkParameterIndex(paramInt);
    Object[] arrayOfObject = new Object[3];
    arrayOfObject[0] = paramReader;
    arrayOfObject[1] = new Long(paramLong);
    arrayOfObject[2] = new Integer(6);
    this.params.add(paramInt - 1, arrayOfObject);
  }
  
  public void setClob(String paramString, Clob paramClob) throws SQLException {
    setClob(findParam(paramString), paramClob);
  }
  
  public void setClob(String paramString, Reader paramReader) throws SQLException {
    setClob(findParam(paramString), paramReader);
  }
  
  public void setClob(String paramString, Reader paramReader, long paramLong) throws SQLException {
    setClob(findParam(paramString), paramReader, paramLong);
  }
  
  public void setCommand(String paramString) throws SQLException {
    this.command = paramString;
    this.paramNames = TbSQLParser.getParamNames(this.command);
  }
  
  public void setConcurrency(int paramInt) throws SQLException {
    switch (paramInt) {
      case 1007:
      case 1008:
        this.concurrency = paramInt;
        return;
    } 
    throw TbError.newSQLException(-90608);
  }
  
  public void setDataSourceName(String paramString) throws SQLException {
    this.dataSourceName = paramString;
  }
  
  public void setDate(int paramInt, Date paramDate) throws SQLException {
    checkParameterIndex(paramInt);
    this.params.add(paramInt - 1, paramDate);
  }
  
  public void setDate(int paramInt, Date paramDate, Calendar paramCalendar) throws SQLException {
    checkParameterIndex(paramInt);
    Object[] arrayOfObject = new Object[2];
    arrayOfObject[0] = paramDate;
    arrayOfObject[1] = paramCalendar;
    this.params.add(paramInt - 1, arrayOfObject);
  }
  
  public void setDate(String paramString, Date paramDate) throws SQLException {
    setDate(findParam(paramString), paramDate);
  }
  
  public void setDate(String paramString, Date paramDate, Calendar paramCalendar) throws SQLException {
    setDate(findParam(paramString), paramDate, paramCalendar);
  }
  
  public void setDouble(int paramInt, double paramDouble) throws SQLException {
    checkParameterIndex(paramInt);
    this.params.add(paramInt - 1, new Double(paramDouble));
  }
  
  public void setDouble(String paramString, double paramDouble) throws SQLException {
    setDouble(findParam(paramString), paramDouble);
  }
  
  public void setEscapeProcessing(boolean paramBoolean) {
    this.escapeProcessing = paramBoolean;
  }
  
  public void setFetchDirection(int paramInt) throws SQLException {
    this.fetchDirection = paramInt;
  }
  
  public void setFetchSize(int paramInt) {
    this.fetchSize = paramInt;
  }
  
  public void setFloat(int paramInt, float paramFloat) throws SQLException {
    checkParameterIndex(paramInt);
    this.params.add(paramInt - 1, new Float(paramFloat));
  }
  
  public void setFloat(String paramString, float paramFloat) throws SQLException {
    setFloat(findParam(paramString), paramFloat);
  }
  
  public void setInt(int paramInt1, int paramInt2) throws SQLException {
    checkParameterIndex(paramInt1);
    this.params.add(paramInt1 - 1, new Integer(paramInt2));
  }
  
  public void setInt(String paramString, int paramInt) throws SQLException {
    setInt(findParam(paramString), paramInt);
  }
  
  public void setLong(int paramInt, long paramLong) throws SQLException {
    checkParameterIndex(paramInt);
    this.params.add(paramInt - 1, new Long(paramLong));
  }
  
  public void setLong(String paramString, long paramLong) throws SQLException {
    setLong(findParam(paramString), paramLong);
  }
  
  public void setMatchColumn(int paramInt) throws SQLException {
    if (paramInt <= 0)
      throw TbError.newSQLException(-90840); 
    try {
      this.matchColumnIndexes.clear();
      this.matchColumnNames.clear();
      this.matchColumnIndexes.add(0, new Integer(paramInt));
    } catch (Exception exception) {
      throw TbError.newSQLException(-90832);
    } 
  }
  
  public void setMatchColumn(int[] paramArrayOfint) throws SQLException {
    this.matchColumnIndexes.clear();
    this.matchColumnNames.clear();
    if (paramArrayOfint == null)
      throw TbError.newSQLException(-590762); 
    for (byte b = 0; b < paramArrayOfint.length; b++) {
      if (paramArrayOfint[b] <= 0)
        throw TbError.newSQLException(-90840); 
      try {
        this.matchColumnIndexes.add(b, new Integer(paramArrayOfint[b]));
      } catch (Exception exception) {
        throw TbError.newSQLException(-90832);
      } 
    } 
  }
  
  public void setMatchColumn(String paramString) throws SQLException {
    if (paramString == null || paramString.equals(""))
      throw TbError.newSQLException(-90841); 
    try {
      this.matchColumnIndexes.clear();
      this.matchColumnNames.clear();
      this.matchColumnNames.add(0, paramString.trim());
    } catch (Exception exception) {
      throw TbError.newSQLException(-90833);
    } 
  }
  
  public void setMatchColumn(String[] paramArrayOfString) throws SQLException {
    this.matchColumnIndexes.clear();
    this.matchColumnNames.clear();
    if (paramArrayOfString == null)
      throw TbError.newSQLException(-590763); 
    for (byte b = 0; b < paramArrayOfString.length; b++) {
      if (paramArrayOfString[b] == null || paramArrayOfString[b].equals(""))
        throw TbError.newSQLException(-90841); 
      try {
        this.matchColumnNames.add(b, paramArrayOfString[b].trim());
      } catch (Exception exception) {
        throw TbError.newSQLException(-90833);
      } 
    } 
  }
  
  public void setMaxFieldSize(int paramInt) {
    this.maxFieldSize = paramInt;
  }
  
  public void setMaxRows(int paramInt) {
    this.maxRows = paramInt;
  }
  
  public void setNCharacterStream(int paramInt, Reader paramReader) throws SQLException {
    checkParameterIndex(paramInt);
    Object[] arrayOfObject = new Object[3];
    arrayOfObject[0] = paramReader;
    arrayOfObject[1] = new Integer(2147483647);
    arrayOfObject[2] = new Integer(4);
    this.params.add(paramInt - 1, arrayOfObject);
  }
  
  public void setNCharacterStream(int paramInt, Reader paramReader, long paramLong) throws SQLException {
    checkParameterIndex(paramInt);
    Object[] arrayOfObject = new Object[3];
    arrayOfObject[0] = paramReader;
    arrayOfObject[1] = new Long(paramLong);
    arrayOfObject[2] = new Integer(4);
    this.params.add(paramInt - 1, arrayOfObject);
  }
  
  public void setNCharacterStream(String paramString, Reader paramReader) throws SQLException {
    setNCharacterStream(findParam(paramString), paramReader);
  }
  
  public void setNCharacterStream(String paramString, Reader paramReader, long paramLong) throws SQLException {
    setNCharacterStream(findParam(paramString), paramReader, paramLong);
  }
  
  public void setNClob(int paramInt, NClob paramNClob) throws SQLException {
    checkParameterIndex(paramInt);
    this.params.add(paramInt - 1, paramNClob);
  }
  
  public void setNClob(int paramInt, Reader paramReader) throws SQLException {
    checkParameterIndex(paramInt);
    Object[] arrayOfObject = new Object[3];
    arrayOfObject[0] = paramReader;
    arrayOfObject[1] = new Integer(2147483647);
    arrayOfObject[2] = new Integer(7);
    this.params.add(paramInt - 1, arrayOfObject);
  }
  
  public void setNClob(int paramInt, Reader paramReader, long paramLong) throws SQLException {
    checkParameterIndex(paramInt);
    Object[] arrayOfObject = new Object[3];
    arrayOfObject[0] = paramReader;
    arrayOfObject[1] = new Long(paramLong);
    arrayOfObject[2] = new Integer(7);
    this.params.add(paramInt - 1, arrayOfObject);
  }
  
  public void setNClob(String paramString, NClob paramNClob) throws SQLException {
    setNClob(findParam(paramString), paramNClob);
  }
  
  public void setNClob(String paramString, Reader paramReader) throws SQLException {
    setNClob(findParam(paramString), paramReader);
  }
  
  public void setNClob(String paramString, Reader paramReader, long paramLong) throws SQLException {
    setNClob(findParam(paramString), paramReader, paramLong);
  }
  
  public void setNString(int paramInt, String paramString) throws SQLException {
    checkParameterIndex(paramInt);
    Object[] arrayOfObject = new Object[2];
    arrayOfObject[0] = paramString;
    arrayOfObject[1] = new Integer(0);
    this.params.add(paramInt - 1, arrayOfObject);
  }
  
  public void setNString(String paramString1, String paramString2) throws SQLException {
    setNString(findParam(paramString1), paramString2);
  }
  
  public void setNull(int paramInt1, int paramInt2) throws SQLException {
    checkParameterIndex(paramInt1);
    this.params.add(paramInt1 - 1, null);
  }
  
  public void setNull(int paramInt1, int paramInt2, String paramString) throws SQLException {
    checkParameterIndex(paramInt1);
    Object[] arrayOfObject = new Object[2];
    arrayOfObject[0] = new Integer(paramInt2);
    arrayOfObject[1] = paramString;
    this.params.add(paramInt1 - 1, arrayOfObject);
  }
  
  public void setNull(String paramString, int paramInt) throws SQLException {
    setNull(findParam(paramString), paramInt);
  }
  
  public void setNull(String paramString1, int paramInt, String paramString2) throws SQLException {
    setNull(findParam(paramString1), paramInt, paramString2);
  }
  
  public void setObject(int paramInt, Object paramObject) throws SQLException {
    checkParameterIndex(paramInt);
    this.params.add(paramInt - 1, paramObject);
  }
  
  public void setObject(int paramInt1, Object paramObject, int paramInt2) throws SQLException {
    checkParameterIndex(paramInt1);
    Object[] arrayOfObject = new Object[2];
    arrayOfObject[0] = paramObject;
    arrayOfObject[1] = new Integer(paramInt2);
    this.params.add(paramInt1 - 1, arrayOfObject);
  }
  
  public void setObject(int paramInt1, Object paramObject, int paramInt2, int paramInt3) throws SQLException {
    checkParameterIndex(paramInt1);
    Object[] arrayOfObject = new Object[3];
    arrayOfObject[0] = paramObject;
    arrayOfObject[1] = new Integer(paramInt2);
    arrayOfObject[2] = new Integer(paramInt3);
    this.params.add(paramInt1 - 1, arrayOfObject);
  }
  
  public void setObject(String paramString, Object paramObject) throws SQLException {
    setObject(findParam(paramString), paramObject);
  }
  
  public void setObject(String paramString, Object paramObject, int paramInt) throws SQLException {
    setObject(findParam(paramString), paramObject, paramInt);
  }
  
  public void setObject(String paramString, Object paramObject, int paramInt1, int paramInt2) throws SQLException {
    setObject(findParam(paramString), paramObject, paramInt1, paramInt2);
  }
  
  public void setPassword(String paramString) {
    this.password = paramString;
  }
  
  public void setQueryTimeout(int paramInt) {
    this.queryTimeout = paramInt;
  }
  
  public void setReadOnly(boolean paramBoolean) {
    this.readOnly = paramBoolean;
  }
  
  public void setRef(int paramInt, Ref paramRef) throws SQLException {
    checkParameterIndex(paramInt);
    this.params.add(paramInt - 1, paramRef);
  }
  
  public void setRef(String paramString, Ref paramRef) throws SQLException {
    setRef(findParam(paramString), paramRef);
  }
  
  public void setRowId(int paramInt, RowId paramRowId) throws SQLException {
    checkParameterIndex(paramInt);
    this.params.add(paramInt - 1, paramRowId);
  }
  
  public void setRowId(String paramString, RowId paramRowId) throws SQLException {
    setRowId(findParam(paramString), paramRowId);
  }
  
  public void setShort(int paramInt, short paramShort) throws SQLException {
    checkParameterIndex(paramInt);
    this.params.add(paramInt - 1, new Short(paramShort));
  }
  
  public void setShort(String paramString, short paramShort) throws SQLException {
    setShort(findParam(paramString), paramShort);
  }
  
  public void setShowDeleted(boolean paramBoolean) throws SQLException {
    this.showDeleted = paramBoolean;
  }
  
  public void setSQLXML(int paramInt, SQLXML paramSQLXML) throws SQLException {
    checkParameterIndex(paramInt);
    this.params.add(paramInt - 1, paramSQLXML);
  }
  
  public void setSQLXML(String paramString, SQLXML paramSQLXML) throws SQLException {
    setSQLXML(findParam(paramString), paramSQLXML);
  }
  
  public void setString(int paramInt, String paramString) throws SQLException {
    checkParameterIndex(paramInt);
    this.params.add(paramInt - 1, paramString);
  }
  
  public void setString(String paramString1, String paramString2) throws SQLException {
    setString(findParam(paramString1), paramString2);
  }
  
  public void setTime(int paramInt, Time paramTime) throws SQLException {
    checkParameterIndex(paramInt);
    this.params.add(paramInt - 1, paramTime);
  }
  
  public void setTime(int paramInt, Time paramTime, Calendar paramCalendar) throws SQLException {
    checkParameterIndex(paramInt);
    Object[] arrayOfObject = new Object[2];
    arrayOfObject[0] = paramTime;
    arrayOfObject[1] = paramCalendar;
    this.params.add(paramInt - 1, arrayOfObject);
  }
  
  public void setTime(String paramString, Time paramTime) throws SQLException {
    setTime(findParam(paramString), paramTime);
  }
  
  public void setTime(String paramString, Time paramTime, Calendar paramCalendar) throws SQLException {
    setTime(findParam(paramString), paramTime);
  }
  
  public void setTimestamp(int paramInt, Timestamp paramTimestamp) throws SQLException {
    checkParameterIndex(paramInt);
    this.params.add(paramInt - 1, paramTimestamp);
  }
  
  public void setTimestamp(int paramInt, Timestamp paramTimestamp, Calendar paramCalendar) throws SQLException {
    checkParameterIndex(paramInt);
    Object[] arrayOfObject = new Object[2];
    arrayOfObject[0] = paramTimestamp;
    arrayOfObject[1] = paramCalendar;
    this.params.add(paramInt - 1, arrayOfObject);
  }
  
  public void setTimestamp(String paramString, Timestamp paramTimestamp) throws SQLException {
    setTimestamp(findParam(paramString), paramTimestamp);
  }
  
  public void setTimestamp(String paramString, Timestamp paramTimestamp, Calendar paramCalendar) throws SQLException {
    setTimestamp(findParam(paramString), paramTimestamp, paramCalendar);
  }
  
  public void setTransactionIsolation(int paramInt) throws SQLException {
    switch (paramInt) {
      case 2:
      case 8:
        this.isolation = paramInt;
        return;
    } 
    throw TbError.newSQLException(-90608);
  }
  
  public void setType(int paramInt) throws SQLException {
    switch (paramInt) {
      case 1003:
      case 1004:
      case 1005:
        this.rowsetType = paramInt;
        return;
    } 
    throw TbError.newSQLException(-90608);
  }
  
  public void setTypeMap(Map<String, Class<?>> paramMap) {
    this.typeMap = paramMap;
  }
  
  public void setUrl(String paramString) {
    this.url = paramString;
  }
  
  public void setURL(int paramInt, URL paramURL) throws SQLException {
    throw TbError.newSQLException(-90201);
  }
  
  public void setURL(String paramString, URL paramURL) throws SQLException {
    setURL(findParam(paramString), paramURL);
  }
  
  public void setUsername(String paramString) {
    this.username = paramString;
  }
  
  public void unsetMatchColumn(int paramInt) throws SQLException {
    checkMatchColumnIndexesExist();
    if (paramInt <= 0)
      throw TbError.newSQLException(-90840); 
    int i = -1;
    try {
      i = ((Integer)this.matchColumnIndexes.get(0)).intValue();
    } catch (Exception exception) {
      throw TbError.newSQLException(-90860);
    } 
    if (i != paramInt)
      throw TbError.newSQLException(-90866); 
    this.matchColumnIndexes.clear();
    this.matchColumnNames.clear();
  }
  
  public void unsetMatchColumn(int[] paramArrayOfint) throws SQLException {
    checkMatchColumnIndexesExist();
    if (paramArrayOfint == null)
      throw TbError.newSQLException(-590762); 
    int i = -1;
    for (byte b = 0; b < paramArrayOfint.length; b++) {
      if (paramArrayOfint[b] <= 0)
        throw TbError.newSQLException(-90840); 
      try {
        i = ((Integer)this.matchColumnIndexes.get(b)).intValue();
      } catch (Exception exception) {
        throw TbError.newSQLException(-90860);
      } 
      if (i != paramArrayOfint[b])
        throw TbError.newSQLException(-90866); 
    } 
    this.matchColumnIndexes.clear();
    this.matchColumnNames.clear();
  }
  
  public void unsetMatchColumn(String paramString) throws SQLException {
    checkMatchColumnNamesExist();
    if (paramString == null || paramString.equals(""))
      throw TbError.newSQLException(-90841); 
    String str = null;
    try {
      str = this.matchColumnNames.get(0);
    } catch (Exception exception) {
      throw TbError.newSQLException(-90861);
    } 
    if (!str.equals(paramString.trim()))
      throw TbError.newSQLException(-90867); 
    this.matchColumnIndexes.clear();
    this.matchColumnNames.clear();
  }
  
  public void unsetMatchColumn(String[] paramArrayOfString) throws SQLException {
    checkMatchColumnNamesExist();
    if (paramArrayOfString == null)
      throw TbError.newSQLException(-590763); 
    String str = null;
    for (byte b = 0; b < paramArrayOfString.length; b++) {
      if (paramArrayOfString[b] == null || paramArrayOfString[b].equals(""))
        throw TbError.newSQLException(-90841); 
      try {
        str = this.matchColumnNames.get(b);
      } catch (Exception exception) {
        throw TbError.newSQLException(-90861);
      } 
      if (!str.equals(paramArrayOfString[b]))
        throw TbError.newSQLException(-90867); 
    } 
    this.matchColumnIndexes.clear();
    this.matchColumnNames.clear();
  }
}


/* Location:              C:\Users\Lenovo\Desktop\tibero\tibero6-jdbc.jar!\com\tmax\tibero\jdbc\rowset\TbRowSet.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */