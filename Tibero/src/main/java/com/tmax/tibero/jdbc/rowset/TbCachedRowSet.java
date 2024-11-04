package com.tmax.tibero.jdbc.rowset;

import com.tmax.tibero.jdbc.TbBlob;
import com.tmax.tibero.jdbc.TbClobBase;
import com.tmax.tibero.jdbc.TbDriver;
import com.tmax.tibero.jdbc.err.TbError;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.CharArrayReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.Reader;
import java.io.Serializable;
import java.io.StringBufferInputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.NClob;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Savepoint;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;
import java.util.Vector;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import javax.sql.RowSet;
import javax.sql.RowSetEvent;
import javax.sql.RowSetInternal;
import javax.sql.RowSetListener;
import javax.sql.RowSetMetaData;
import javax.sql.RowSetReader;
import javax.sql.RowSetWriter;
import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.Joinable;
import javax.sql.rowset.RowSetWarning;
import javax.sql.rowset.spi.SyncFactory;
import javax.sql.rowset.spi.SyncFactoryException;
import javax.sql.rowset.spi.SyncProvider;
import javax.sql.rowset.spi.SyncProviderException;

public class TbCachedRowSet extends TbRowSet implements RowSetInternal, Serializable, CachedRowSet, Joinable {
  private static final long serialVersionUID = -3966681529200633668L;
  
  private Connection conn;
  
  private ResultSet rs;
  
  private boolean isConnectionOpened = false;
  
  protected RowSetMetaData rowsetMetaData;
  
  protected Vector<TbRow> rows;
  
  protected int currentRowIndex = -1;
  
  protected int columnCount = 0;
  
  protected int rowCount = 0;
  
  private RowSetReader reader;
  
  private RowSetWriter writer;
  
  private String[] columnNames;
  
  private boolean isOnUpdate = false;
  
  private int updateRowIndex;
  
  private boolean isOnInsert = false;
  
  private int insertRowIndex;
  
  private TbRow temporaryRow;
  
  private int pageSize;
  
  private int currentPageIndex;
  
  private boolean prevColumnWasNull = false;
  
  private boolean isInitializedDriver;
  
  private boolean isPopulateCompleted;
  
  private int[] keyColumns;
  
  private RowSetWarning rowsetWarnings;
  
  private SyncProvider syncProvider;
  
  private static final String DEFAULT_SYNC_PROVIDER = "com.sun.rowset.providers.RIOptimisticProvider";
  
  public TbCachedRowSet() throws SQLException {
    this.currentRowIndex = 0;
    this.pageSize = 0;
    this.currentPageIndex = 0;
    this.prevColumnWasNull = false;
    this.isInitializedDriver = false;
    this.isPopulateCompleted = false;
    this.rows = new Vector<TbRow>();
    try {
      this.syncProvider = SyncFactory.getInstance("com.sun.rowset.providers.RIOptimisticProvider");
    } catch (SyncFactoryException syncFactoryException) {
      throw TbError.newSQLException(-90820, syncFactoryException.getMessage());
    } 
    setReader(new TbCachedRowSetReader());
    setWriter(new TbCachedRowSetWriter());
  }
  
  public boolean absolute(int paramInt) throws SQLException {
    if (this.rowsetType == 1003)
      throw TbError.newSQLException(-590758); 
    if (paramInt == 0 || Math.abs(paramInt) > this.rowCount)
      return false; 
    this.currentRowIndex = (paramInt >= 0) ? paramInt : (this.rowCount + paramInt + 1);
    notifyCursorMoved();
    return true;
  }
  
  public void acceptChanges() throws SyncProviderException {
    try {
      getWriter().writeData(this);
    } catch (SQLException sQLException) {
      throw new SyncProviderException(sQLException.getMessage());
    } 
  }
  
  public void acceptChanges(Connection paramConnection) throws SyncProviderException {
    this.conn = paramConnection;
    this.isConnectionOpened = true;
    acceptChanges();
  }
  
  public void afterLast() throws SQLException {
    this.currentRowIndex = this.rowCount + 1;
  }
  
  public void beforeFirst() throws SQLException {
    this.currentRowIndex = 0;
  }
  
  public void cancelRowDelete() throws SQLException {
    if (getCurrentRow().isDeleted()) {
      getCurrentRow().markDeleted(false);
      notifyRowChanged();
    } else {
      throw TbError.newSQLException(-90858);
    } 
  }
  
  public void cancelRowInsert() throws SQLException {
    if (getCurrentRow().isInserted()) {
      this.rows.remove(--this.currentRowIndex);
      this.rowCount--;
      notifyRowChanged();
    } else {
      throw TbError.newSQLException(-90862);
    } 
  }
  
  public void cancelRowUpdates() throws SQLException {
    if (getCurrentRow().isUpdated()) {
      this.isOnUpdate = false;
      getCurrentRow().markUpdated(false);
      notifyRowChanged();
    } else {
      throw TbError.newSQLException(-90863);
    } 
  }
  
  private void checkColumnIndex(int paramInt) throws SQLException {
    if (this.readOnly)
      throw TbError.newSQLException(-90825); 
    if (paramInt < 1 || paramInt > this.columnCount)
      throw TbError.newSQLException(-90834, Integer.toString(paramInt)); 
  }
  
  public void close() throws SQLException {
    release();
    this.isClosed = true;
  }
  
  public boolean columnUpdated(int paramInt) throws SQLException {
    if (this.isOnInsert)
      throw TbError.newSQLException(-90851); 
    return getCurrentRow().isColumnChanged(paramInt);
  }
  
  public boolean columnUpdated(String paramString) throws SQLException {
    return columnUpdated(findColumn(paramString));
  }
  
  public void commit() throws SQLException {
    getConnection().commit();
  }
  
  public CachedRowSet createCopy() throws SQLException {
    TbCachedRowSet tbCachedRowSet = (TbCachedRowSet)createShared();
    int i = this.rows.size();
    tbCachedRowSet.rows = new Vector<TbRow>(i);
    for (byte b = 0; b < i; b++)
      tbCachedRowSet.rows.add(((TbRow)this.rows.elementAt(b)).createCopy()); 
    return tbCachedRowSet;
  }
  
  public CachedRowSet createCopyNoConstraints() throws SQLException {
    TbCachedRowSet tbCachedRowSet = (TbCachedRowSet)createCopy();
    tbCachedRowSet.initialize();
    tbCachedRowSet.listeners = new Vector<RowSetListener>();
    try {
      tbCachedRowSet.unsetMatchColumn(tbCachedRowSet.getMatchColumnIndexes());
    } catch (SQLException sQLException) {}
    try {
      tbCachedRowSet.unsetMatchColumn(tbCachedRowSet.getMatchColumnNames());
    } catch (SQLException sQLException) {}
    return tbCachedRowSet;
  }
  
  public CachedRowSet createCopySchema() throws SQLException {
    TbCachedRowSet tbCachedRowSet = (TbCachedRowSet)createCopy();
    tbCachedRowSet.rows = null;
    tbCachedRowSet.rowCount = 0;
    tbCachedRowSet.currentPageIndex = 0;
    return tbCachedRowSet;
  }
  
  public RowSet createShared() throws SQLException {
    TbCachedRowSet tbCachedRowSet = new TbCachedRowSet();
    tbCachedRowSet.rows = this.rows;
    tbCachedRowSet.setDataSourceName(getDataSourceName());
    tbCachedRowSet.setUsername(getUsername());
    tbCachedRowSet.setPassword(getPassword());
    tbCachedRowSet.setUrl(getUrl());
    tbCachedRowSet.setTypeMap(getTypeMap());
    tbCachedRowSet.setMaxFieldSize(getMaxFieldSize());
    tbCachedRowSet.setMaxRows(getMaxRows());
    tbCachedRowSet.setQueryTimeout(getQueryTimeout());
    tbCachedRowSet.setFetchSize(getFetchSize());
    tbCachedRowSet.setEscapeProcessing(getEscapeProcessing());
    tbCachedRowSet.setConcurrency(getConcurrency());
    tbCachedRowSet.setReadOnly(this.readOnly);
    tbCachedRowSet.rowsetType = getType();
    tbCachedRowSet.setFetchDirection(getFetchDirection());
    tbCachedRowSet.setCommand(getCommand());
    tbCachedRowSet.setTransactionIsolation(getTransactionIsolation());
    tbCachedRowSet.currentRowIndex = this.currentRowIndex;
    tbCachedRowSet.columnCount = this.columnCount;
    tbCachedRowSet.rowCount = this.rowCount;
    tbCachedRowSet.showDeleted = this.showDeleted;
    tbCachedRowSet.syncProvider = this.syncProvider;
    tbCachedRowSet.currentPageIndex = this.currentPageIndex;
    tbCachedRowSet.pageSize = this.pageSize;
    tbCachedRowSet.tableName = (this.tableName != null) ? new String(this.tableName) : null;
    tbCachedRowSet.keyColumns = (this.keyColumns != null) ? (int[])this.keyColumns.clone() : null;
    int i = this.listeners.size();
    byte b;
    for (b = 0; b < i; b++)
      tbCachedRowSet.listeners.add(this.listeners.elementAt(b)); 
    tbCachedRowSet.rowsetMetaData = new TbRowSetMetaData(this.rowsetMetaData);
    i = this.params.size();
    for (b = 0; b < i; b++)
      tbCachedRowSet.params.add(this.params.elementAt(b)); 
    tbCachedRowSet.columnNames = new String[this.columnNames.length];
    System.arraycopy(this.columnNames, 0, tbCachedRowSet.columnNames, 0, this.columnNames.length);
    return tbCachedRowSet;
  }
  
  public void deleteRow() throws SQLException {
    getCurrentRow().markDeleted(true);
    notifyRowChanged();
  }
  
  public synchronized void execute() throws SQLException {
    this.isConnectionOpened = false;
    this.reader.readData(this);
  }
  
  public synchronized void execute(Connection paramConnection) throws SQLException {
    this.conn = paramConnection;
    execute();
  }
  
  public int findColumn(String paramString) throws SQLException {
    byte b = 0;
    paramString = paramString.toUpperCase();
    for (b = 0; b < this.columnCount && !this.columnNames[b].equals(paramString); b++);
    if (b == this.columnCount)
      throw TbError.newSQLException(-90835, paramString); 
    return b + 1;
  }
  
  public boolean first() throws SQLException {
    return absolute(1);
  }
  
  public Array getArray(int paramInt) throws SQLException {
    Object object = getObject(paramInt);
    if (object == null)
      return null; 
    if (object instanceof Array)
      return (Array)object; 
    throw TbError.newSQLException(-90836, object.toString());
  }
  
  public Array getArray(String paramString) throws SQLException {
    return getArray(findColumn(paramString));
  }
  
  public InputStream getAsciiStream(int paramInt) throws SQLException {
    InputStream inputStream = getStream(paramInt);
    return (inputStream != null) ? inputStream : null;
  }
  
  public InputStream getAsciiStream(String paramString) throws SQLException {
    return getAsciiStream(findColumn(paramString));
  }
  
  public BigDecimal getBigDecimal(int paramInt) throws SQLException {
    Number number = getNumeric(paramInt);
    if (number == null || number instanceof BigDecimal)
      return (BigDecimal)number; 
    if (number instanceof Number)
      return new BigDecimal(number.doubleValue()); 
    throw TbError.newSQLException(-90836, number.toString());
  }
  
  @Deprecated
  public BigDecimal getBigDecimal(int paramInt1, int paramInt2) throws SQLException {
    return getBigDecimal(paramInt1);
  }
  
  public BigDecimal getBigDecimal(String paramString) throws SQLException {
    return getBigDecimal(findColumn(paramString));
  }
  
  @Deprecated
  public BigDecimal getBigDecimal(String paramString, int paramInt) throws SQLException {
    return getBigDecimal(findColumn(paramString));
  }
  
  public InputStream getBinaryStream(int paramInt) throws SQLException {
    InputStream inputStream = getStream(paramInt);
    return (inputStream != null) ? inputStream : null;
  }
  
  public InputStream getBinaryStream(String paramString) throws SQLException {
    return getBinaryStream(findColumn(paramString));
  }
  
  public Blob getBlob(int paramInt) throws SQLException {
    Object object = getObject(paramInt);
    if (object == null)
      return null; 
    if (object instanceof TbBlob)
      return (Blob)object; 
    throw TbError.newSQLException(-90836, object.toString());
  }
  
  public Blob getBlob(String paramString) throws SQLException {
    return getBlob(findColumn(paramString));
  }
  
  public boolean getBoolean(int paramInt) throws SQLException {
    Object object = getObject(paramInt);
    if (object == null)
      return false; 
    if (object instanceof Boolean)
      return ((Boolean)object).booleanValue(); 
    if (object instanceof Number)
      return (((Number)object).doubleValue() != 0.0D); 
    throw TbError.newSQLException(-90836, object.toString());
  }
  
  public boolean getBoolean(String paramString) throws SQLException {
    return getBoolean(findColumn(paramString));
  }
  
  public byte getByte(int paramInt) throws SQLException {
    Object object = getObject(paramInt);
    if (object == null)
      return 0; 
    if (object instanceof Number)
      return ((Number)object).byteValue(); 
    if (object instanceof String)
      return ((String)object).getBytes()[0]; 
    if (object instanceof TbBlob) {
      TbBlob tbBlob = (TbBlob)object;
      return tbBlob.getBytes(0L, 1)[0];
    } 
    if (object instanceof TbClobBase) {
      TbClobBase tbClobBase = (TbClobBase)object;
      return tbClobBase.getSubString(0L, 1).getBytes()[0];
    } 
    throw TbError.newSQLException(-90836, object.toString());
  }
  
  public byte getByte(String paramString) throws SQLException {
    return getByte(findColumn(paramString));
  }
  
  public byte[] getBytes(int paramInt) throws SQLException {
    Object object = getObject(paramInt);
    if (object == null)
      return null; 
    if (object instanceof byte[])
      return (byte[])object; 
    if (object instanceof String)
      return ((String)object).getBytes(); 
    if (object instanceof Number)
      return ((Number)object).toString().getBytes(); 
    if (object instanceof BigDecimal)
      return ((BigDecimal)object).toString().getBytes(); 
    if (object instanceof TbBlob) {
      TbBlob tbBlob = (TbBlob)object;
      return tbBlob.getBytes(0L, (int)tbBlob.length());
    } 
    if (object instanceof TbClobBase) {
      TbClobBase tbClobBase = (TbClobBase)object;
      return tbClobBase.getSubString(0L, (int)tbClobBase.length()).getBytes();
    } 
    throw TbError.newSQLException(-90836, object.toString());
  }
  
  public byte[] getBytes(String paramString) throws SQLException {
    return getBytes(findColumn(paramString));
  }
  
  public Reader getCharacterStream(int paramInt) throws SQLException {
    return getCharacterStreamInternal(paramInt);
  }
  
  public Reader getCharacterStream(String paramString) throws SQLException {
    return getCharacterStreamInternal(findColumn(paramString));
  }
  
  private Reader getCharacterStreamInternal(int paramInt) throws SQLException {
    InputStream inputStream = getAsciiStream(paramInt);
    if (inputStream == null)
      return null; 
    try {
      StringBuffer stringBuffer = new StringBuffer();
      int i = 0;
      while ((i = inputStream.read()) != -1)
        stringBuffer.append((char)i); 
      char[] arrayOfChar = new char[stringBuffer.length()];
      stringBuffer.getChars(0, stringBuffer.length(), arrayOfChar, 0);
      return new CharArrayReader(arrayOfChar);
    } catch (IOException iOException) {
      throw TbError.newSQLException(-90829, iOException.getMessage());
    } 
  }
  
  public Clob getClob(int paramInt) throws SQLException {
    Object object = getObject(paramInt);
    if (object == null)
      return null; 
    if (object instanceof TbClobBase)
      return (Clob)object; 
    throw TbError.newSQLException(-90836, object.toString());
  }
  
  public Clob getClob(String paramString) throws SQLException {
    return getClob(findColumn(paramString));
  }
  
  public Connection getConnection() throws SQLException {
    if (this.conn == null || this.conn.isClosed()) {
      String str1 = getUsername();
      String str2 = getPassword();
      String str3 = getUrl();
      String str4 = getDataSourceName();
      if (str4 != null && !str4.equals("")) {
        try {
          InitialContext initialContext = null;
          try {
            Properties properties = System.getProperties();
            initialContext = new InitialContext(properties);
          } catch (SecurityException securityException) {}
          if (initialContext == null)
            initialContext = new InitialContext(); 
          DataSource dataSource = (DataSource)initialContext.lookup(str4);
          if (this.username == null || str2 == null) {
            this.conn = dataSource.getConnection();
          } else {
            this.conn = dataSource.getConnection(this.username, str2);
          } 
        } catch (NamingException namingException) {
          throw TbError.newSQLException(-90828, namingException.getMessage());
        } 
      } else if (str3 != null && !str3.equals("")) {
        if (!this.isInitializedDriver) {
          DriverManager.registerDriver((Driver)new TbDriver());
          this.isInitializedDriver = true;
        } 
        if (str1.equals("") || str2.equals(""))
          throw TbError.newSQLException(-90854, "(" + str1 + "," + str2 + ")"); 
        this.conn = DriverManager.getConnection(str3, str1, str2);
      } else {
        throw TbError.newSQLException(-90852);
      } 
    } 
    return this.conn;
  }
  
  protected TbRow getCurrentRow() throws SQLException {
    if (this.currentRowIndex < 1 || this.currentRowIndex > this.rowCount)
      throw TbError.newSQLException(-90847, "(" + this.currentRowIndex + "," + this.rowCount + ")"); 
    return this.rows.elementAt(this.currentRowIndex - 1);
  }
  
  public String getCursorName() throws SQLException {
    throw TbError.newSQLException(-90201);
  }
  
  public Date getDate(int paramInt) throws SQLException {
    return getDateInternal(paramInt, (Calendar)null);
  }
  
  public Date getDate(int paramInt, Calendar paramCalendar) throws SQLException {
    return getDateInternal(paramInt, paramCalendar);
  }
  
  public Date getDate(String paramString) throws SQLException {
    return getDateInternal(findColumn(paramString), (Calendar)null);
  }
  
  public Date getDate(String paramString, Calendar paramCalendar) throws SQLException {
    return getDateInternal(findColumn(paramString), paramCalendar);
  }
  
  private Date getDateInternal(int paramInt, Calendar paramCalendar) throws SQLException {
    Object object = getObject(paramInt);
    if (object == null)
      return null; 
    if (object instanceof Time)
      return new Date(((Time)object).getTime()); 
    if (object instanceof Date) {
      if (paramCalendar == null)
        paramCalendar = Calendar.getInstance(); 
      paramCalendar.setTime((Date)object);
      return new Date(paramCalendar.getTime().getTime());
    } 
    throw TbError.newSQLException(-90836, object.toString());
  }
  
  public double getDouble(int paramInt) throws SQLException {
    Number number = getNumeric(paramInt);
    return (number != null) ? number.doubleValue() : 0.0D;
  }
  
  public double getDouble(String paramString) throws SQLException {
    return getDouble(findColumn(paramString));
  }
  
  public float getFloat(int paramInt) throws SQLException {
    Number number = getNumeric(paramInt);
    return (number != null) ? number.floatValue() : 0.0F;
  }
  
  public float getFloat(String paramString) throws SQLException {
    return getFloat(findColumn(paramString));
  }
  
  public int getHoldability() throws SQLException {
    return 1;
  }
  
  public int getInt(int paramInt) throws SQLException {
    Number number = getNumeric(paramInt);
    return (number != null) ? number.intValue() : 0;
  }
  
  public int getInt(String paramString) throws SQLException {
    return getInt(findColumn(paramString));
  }
  
  public int[] getKeyColumns() throws SQLException {
    return this.keyColumns;
  }
  
  public long getLong(int paramInt) throws SQLException {
    Number number = getNumeric(paramInt);
    return (number != null) ? number.longValue() : 0L;
  }
  
  public long getLong(String paramString) throws SQLException {
    return getLong(findColumn(paramString));
  }
  
  public ResultSetMetaData getMetaData() throws SQLException {
    return this.rowsetMetaData;
  }
  
  public Reader getNCharacterStream(int paramInt) throws SQLException {
    return getCharacterStreamInternal(paramInt);
  }
  
  public Reader getNCharacterStream(String paramString) throws SQLException {
    return getCharacterStreamInternal(findColumn(paramString));
  }
  
  public NClob getNClob(int paramInt) throws SQLException {
    Object object = getObject(paramInt);
    if (object == null)
      return null; 
    if (object instanceof com.tmax.tibero.jdbc.TbNClob)
      return (NClob)object; 
    throw TbError.newSQLException(-90836, object.toString());
  }
  
  public NClob getNClob(String paramString) throws SQLException {
    return getNClob(findColumn(paramString));
  }
  
  public String getNString(int paramInt) throws SQLException {
    return getStringInternal(paramInt);
  }
  
  public String getNString(String paramString) throws SQLException {
    return getStringInternal(findColumn(paramString));
  }
  
  private synchronized Number getNumeric(int paramInt) throws SQLException {
    Object object = getObject(paramInt);
    if (object == null)
      return null; 
    if (object instanceof BigDecimal || object instanceof Number)
      return (Number)object; 
    if (object instanceof Boolean)
      return ((Boolean)object).booleanValue() ? 1 : 0;
    if (!(object instanceof String))
      throw TbError.newSQLException(-90836, object.toString()); 
    try {
      return new BigDecimal((String)object);
    } catch (NumberFormatException numberFormatException) {
      throw TbError.newSQLException(-90829, ((String)object).toString());
    } 
  }
  
  public Object getObject(int paramInt) throws SQLException {
    Object object = null;
    if (!isUpdated(paramInt)) {
      object = getCurrentRow().getColumn(paramInt);
    } else {
      object = getCurrentRow().getChangedColumn(paramInt);
    } 
    this.prevColumnWasNull = (object == null);
    return object;
  }
  
  public Object getObject(int paramInt, Map<String, Class<?>> paramMap) throws SQLException {
    return getObject(paramInt);
  }
  
  public Object getObject(String paramString) throws SQLException {
    return getObject(findColumn(paramString));
  }
  
  public Object getObject(String paramString, Map<String, Class<?>> paramMap) throws SQLException {
    return getObject(findColumn(paramString), paramMap);
  }
  
  public ResultSet getOriginal() throws SQLException {
    TbCachedRowSet tbCachedRowSet = new TbCachedRowSet();
    tbCachedRowSet.rowsetMetaData = this.rowsetMetaData;
    tbCachedRowSet.columnCount = this.columnCount;
    tbCachedRowSet.rowCount = this.rowCount;
    tbCachedRowSet.currentRowIndex = 0;
    tbCachedRowSet.setType(1004);
    tbCachedRowSet.setConcurrency(1008);
    tbCachedRowSet.setReader((RowSetReader)null);
    tbCachedRowSet.setWriter((RowSetWriter)null);
    TbRow tbRow = null;
    Iterator<TbRow> iterator = this.rows.iterator();
    while (iterator.hasNext()) {
      tbRow = new TbRow(this.columnCount, ((TbRow)iterator.next()).getOriginalRow());
      tbCachedRowSet.rows.add(tbRow);
    } 
    return tbCachedRowSet;
  }
  
  public ResultSet getOriginalRow() throws SQLException {
    TbCachedRowSet tbCachedRowSet = new TbCachedRowSet();
    tbCachedRowSet.rowsetMetaData = this.rowsetMetaData;
    tbCachedRowSet.columnCount = this.columnCount;
    tbCachedRowSet.rowCount = 1;
    tbCachedRowSet.currentRowIndex = 0;
    tbCachedRowSet.setReader((RowSetReader)null);
    tbCachedRowSet.setWriter((RowSetWriter)null);
    TbRow tbRow = new TbRow(this.rowsetMetaData.getColumnCount(), getCurrentRow().getOriginalRow());
    tbCachedRowSet.rows.add(tbRow);
    return tbCachedRowSet;
  }
  
  public int getPageSize() {
    return this.pageSize;
  }
  
  public RowSetReader getReader() throws SQLException {
    return this.reader;
  }
  
  public Ref getRef(int paramInt) throws SQLException {
    Object object = getObject(paramInt);
    if (object == null)
      return null; 
    if (object instanceof Ref)
      return (Ref)object; 
    throw TbError.newSQLException(-90836, object.toString());
  }
  
  public Ref getRef(String paramString) throws SQLException {
    return getRef(findColumn(paramString));
  }
  
  public int getRow() throws SQLException {
    return (this.currentRowIndex > this.rowCount) ? this.rowCount : this.currentRowIndex;
  }
  
  public RowId getRowId(int paramInt) throws SQLException {
    Object object = getObject(paramInt);
    if (object == null)
      return null; 
    if (object instanceof RowId)
      return (RowId)object; 
    throw TbError.newSQLException(-90836, object.toString());
  }
  
  public RowId getRowId(String paramString) throws SQLException {
    return getRowId(findColumn(paramString));
  }
  
  public RowSetWarning getRowSetWarnings() throws SQLException {
    return this.rowsetWarnings;
  }
  
  public short getShort(int paramInt) throws SQLException {
    Number number = getNumeric(paramInt);
    return (number != null) ? number.shortValue() : 0;
  }
  
  public short getShort(String paramString) throws SQLException {
    return getShort(findColumn(paramString));
  }
  
  public SQLXML getSQLXML(int paramInt) throws SQLException {
    Object object = getObject(paramInt);
    if (object == null)
      return null; 
    if (object instanceof SQLXML)
      return (SQLXML)object; 
    throw TbError.newSQLException(-90836, object.toString());
  }
  
  public SQLXML getSQLXML(String paramString) throws SQLException {
    return getSQLXML(findColumn(paramString));
  }
  
  public Statement getStatement() throws SQLException {
    if (this.rs == null)
      throw TbError.newSQLException(-90859); 
    return this.rs.getStatement();
  }
  
  private synchronized InputStream getStream(int paramInt) throws SQLException {
    Object object = getObject(paramInt);
    if (object == null)
      return null; 
    if (object instanceof InputStream)
      return (InputStream)object; 
    if (object instanceof String)
      return new ByteArrayInputStream(((String)object).getBytes()); 
    if (object instanceof byte[])
      return new ByteArrayInputStream((byte[])object); 
    if (object instanceof TbClobBase)
      return ((TbClobBase)object).getAsciiStream(); 
    if (object instanceof TbBlob)
      return ((TbBlob)object).getBinaryStream(); 
    if (!(object instanceof Reader))
      try {
        int i = 0;
        BufferedReader bufferedReader = new BufferedReader((Reader)object);
        PipedInputStream pipedInputStream = new PipedInputStream();
        PipedOutputStream pipedOutputStream = new PipedOutputStream(pipedInputStream);
        while ((i = bufferedReader.read()) != -1)
          pipedOutputStream.write(i); 
        pipedOutputStream.close();
        return pipedInputStream;
      } catch (IOException iOException) {
        throw TbError.newSQLException(-90829, iOException.getMessage());
      }  
    throw TbError.newSQLException(-90836, object.toString());
  }
  
  public String getString(int paramInt) throws SQLException {
    return getStringInternal(paramInt);
  }
  
  public String getString(String paramString) throws SQLException {
    return getStringInternal(findColumn(paramString));
  }
  
  private String getStringInternal(int paramInt) throws SQLException {
    Object object = getObject(paramInt);
    if (object == null)
      return null; 
    if (object instanceof String)
      return (String)object; 
    if (object instanceof Number || object instanceof BigDecimal)
      return object.toString(); 
    if (object instanceof Date)
      return ((Date)object).toString(); 
    if (object instanceof Timestamp)
      return ((Date)object).toString(); 
    if (object instanceof byte[])
      return new String((byte[])object); 
    if (object instanceof TbClobBase) {
      TbClobBase tbClobBase = (TbClobBase)object;
      return tbClobBase.getSubString(0L, (int)tbClobBase.length());
    } 
    if (object instanceof TbBlob) {
      TbBlob tbBlob = (TbBlob)object;
      return new String(tbBlob.getBytes(0L, (int)tbBlob.length()));
    } 
    if (object instanceof URL)
      return ((URL)object).toString(); 
    if (!(object instanceof Reader))
      throw TbError.newSQLException(-90836, object.toString()); 
    try {
      Reader reader = (Reader)object;
      char[] arrayOfChar = new char[1024];
      int i = 0;
      StringBuffer stringBuffer = new StringBuffer(1024);
      while ((i = reader.read(arrayOfChar)) > 0)
        stringBuffer.append(arrayOfChar, 0, i); 
      return stringBuffer.substring(0, stringBuffer.length());
    } catch (IOException iOException) {
      throw TbError.newSQLException(-90829, iOException.getMessage());
    } 
  }
  
  public SyncProvider getSyncProvider() throws SQLException {
    return this.syncProvider;
  }
  
  public Time getTime(int paramInt) throws SQLException {
    return getTimeInternal(paramInt, (Calendar)null);
  }
  
  public Time getTime(int paramInt, Calendar paramCalendar) throws SQLException {
    return getTimeInternal(paramInt, paramCalendar);
  }
  
  public Time getTime(String paramString) throws SQLException {
    return getTimeInternal(findColumn(paramString), (Calendar)null);
  }
  
  public Time getTime(String paramString, Calendar paramCalendar) throws SQLException {
    return getTimeInternal(findColumn(paramString), paramCalendar);
  }
  
  private Time getTimeInternal(int paramInt, Calendar paramCalendar) throws SQLException {
    Object object = getObject(paramInt);
    if (object == null)
      return null; 
    if (object instanceof Time)
      return (Time)object; 
    if (object instanceof Date) {
      if (paramCalendar == null)
        paramCalendar = Calendar.getInstance(); 
      paramCalendar.setTime((Date)object);
      return new Time(paramCalendar.getTime().getTime());
    } 
    throw TbError.newSQLException(-90836, object.toString());
  }
  
  public Timestamp getTimestamp(int paramInt) throws SQLException {
    return getTimestampInternal(paramInt, (Calendar)null);
  }
  
  public Timestamp getTimestamp(int paramInt, Calendar paramCalendar) throws SQLException {
    return getTimestampInternal(paramInt, paramCalendar);
  }
  
  public Timestamp getTimestamp(String paramString) throws SQLException {
    return getTimestampInternal(findColumn(paramString), (Calendar)null);
  }
  
  public Timestamp getTimestamp(String paramString, Calendar paramCalendar) throws SQLException {
    return getTimestampInternal(findColumn(paramString), paramCalendar);
  }
  
  private Timestamp getTimestampInternal(int paramInt, Calendar paramCalendar) throws SQLException {
    Object object = getObject(paramInt);
    if (object == null)
      return null; 
    if (object instanceof Timestamp)
      return (Timestamp)object; 
    if (object instanceof Date) {
      if (paramCalendar == null)
        paramCalendar = Calendar.getInstance(); 
      paramCalendar.setTime((Date)object);
      return new Timestamp(((Date)object).getTime());
    } 
    throw TbError.newSQLException(-90836, object.toString());
  }
  
  @Deprecated
  public InputStream getUnicodeStream(int paramInt) throws SQLException {
    Object object = getObject(paramInt);
    if (object == null)
      return (InputStream)object; 
    if (object instanceof String)
      return new StringBufferInputStream((String)object); 
    throw TbError.newSQLException(-90836, object.toString());
  }
  
  @Deprecated
  public InputStream getUnicodeStream(String paramString) throws SQLException {
    return getUnicodeStream(findColumn(paramString));
  }
  
  public URL getURL(int paramInt) throws SQLException {
    Object object = getObject(paramInt);
    if (object == null)
      return null; 
    if (object instanceof URL)
      return (URL)object; 
    throw TbError.newSQLException(-90836, object.toString());
  }
  
  public URL getURL(String paramString) throws SQLException {
    return getURL(findColumn(paramString));
  }
  
  public RowSetWriter getWriter() throws SQLException {
    return this.writer;
  }
  
  public void insertRow() throws SQLException {
    if (!this.isOnInsert)
      throw TbError.newSQLException(-90822); 
    if (!this.temporaryRow.isPopulationCompleted())
      throw TbError.newSQLException(-90865); 
    this.temporaryRow.insertRow();
    this.rows.insertElementAt(this.temporaryRow, this.insertRowIndex - 1);
    this.isOnInsert = false;
    this.rowCount++;
    notifyRowChanged();
  }
  
  public boolean isAfterLast() throws SQLException {
    return (this.rowCount > 0 && this.currentRowIndex == this.rowCount + 1);
  }
  
  public boolean isBeforeFirst() throws SQLException {
    return (this.rowCount > 0 && this.currentRowIndex == 0);
  }
  
  boolean isConnectionOpened() {
    return this.isConnectionOpened;
  }
  
  public boolean isFirst() throws SQLException {
    return (this.currentRowIndex == 1);
  }
  
  public boolean isLast() throws SQLException {
    return (this.currentRowIndex == this.rowCount);
  }
  
  private final boolean isUpdated(int paramInt) throws SQLException {
    if (paramInt < 1 || paramInt > this.columnCount)
      throw TbError.newSQLException(-90834, Integer.toString(paramInt)); 
    return getCurrentRow().isColumnChanged(paramInt);
  }
  
  public boolean isWrapperFor(Class<?> paramClass) throws SQLException {
    return paramClass.isInstance(this);
  }
  
  public boolean last() throws SQLException {
    return absolute(-1);
  }
  
  public void moveToCurrentRow() throws SQLException {
    this.isOnInsert = false;
    this.isOnUpdate = false;
    absolute(this.currentRowIndex);
  }
  
  public void moveToInsertRow() throws SQLException {
    this.temporaryRow = new TbRow(this.columnCount, true);
    this.isOnInsert = true;
    if (isAfterLast()) {
      this.insertRowIndex = this.currentRowIndex;
    } else {
      this.insertRowIndex = this.currentRowIndex + 1;
    } 
  }
  
  public boolean next() throws SQLException {
    if (this.rowCount < 0)
      return false; 
    if (this.fetchDirection == 1000 || this.fetchDirection == 1002) {
      if (this.currentRowIndex + 1 <= this.rowCount) {
        this.currentRowIndex++;
        if (!this.showDeleted && getCurrentRow().isDeleted())
          return next(); 
        notifyCursorMoved();
        return true;
      } 
      this.currentRowIndex = this.rowCount + 1;
      return false;
    } 
    if (this.fetchDirection == 1001) {
      if (this.currentRowIndex - 1 > 0) {
        this.currentRowIndex--;
        if (!this.showDeleted && getCurrentRow().isDeleted())
          return next(); 
        notifyCursorMoved();
        return true;
      } 
      this.currentRowIndex = 0;
      return false;
    } 
    return false;
  }
  
  public boolean nextPage() throws SQLException {
    if (this.fetchDirection == 1001 && this.rs != null && this.rs.getType() == 1003)
      throw TbError.newSQLException(-90850); 
    if (this.rows.size() == 0 && !this.isPopulateCompleted)
      throw TbError.newSQLException(-90853); 
    populate(this.rs);
    this.currentPageIndex++;
    return !this.isPopulateCompleted;
  }
  
  protected synchronized void notifyCursorMoved() {
    if (this.isOnInsert) {
      this.isOnInsert = false;
      this.temporaryRow.markUpdated(false);
      this.warnings.setNextWarning(new SQLWarning("Insertion is canceled because of the cursor"));
    } else if (this.isOnUpdate) {
      try {
        this.isOnUpdate = false;
        int i = this.currentRowIndex;
        this.currentRowIndex = this.updateRowIndex;
        getCurrentRow().markUpdated(false);
        this.currentRowIndex = i;
        this.warnings.setNextWarning(new SQLWarning("All Updates are canceled because of the cursor"));
      } catch (SQLException sQLException) {}
    } 
    super.notifyCursorMoved();
  }
  
  public void populate(ResultSet paramResultSet) throws SQLException {
    int i;
    if (this.rows == null)
      this.rows = new Vector<TbRow>(100, 10); 
    this.rowsetMetaData = new TbRowSetMetaData(paramResultSet.getMetaData());
    this.columnCount = this.rowsetMetaData.getColumnCount();
    this.columnNames = new String[this.columnCount];
    this.tableName = this.rowsetMetaData.getTableName(1);
    byte b;
    for (b = 0; b < this.columnCount; b++)
      this.columnNames[b] = this.rowsetMetaData.getColumnName(b + 1); 
    b = (byte) ((this.fetchDirection == 1000 || this.fetchDirection == 1002) ? 1 : 0);
    this.rowCount = 0;
    if (this.maxRows == 0 && this.pageSize == 0) {
      i = Integer.MAX_VALUE;
    } else if (this.maxRows == 0 || this.pageSize == 0) {
      i = Math.max(this.maxRows, this.pageSize);
    } else {
      i = Math.min(this.maxRows, this.pageSize);
    } 
    if (paramResultSet.getType() != 1003 && this.rows.size() == 0 && b == 0)
      paramResultSet.afterLast(); 
    while (this.rowCount < i && ((b != 0) ? paramResultSet.next() : paramResultSet.previous())) {
      TbRow tbRow = new TbRow(this.columnCount);
      for (byte b1 = 1; b1 <= this.columnCount; b1++) {
        tbRow.setColumn(b1, paramResultSet.getObject(b1));
        tbRow.markNull(b1, paramResultSet.wasNull());
      } 
      if (b != 0) {
        this.rows.add(tbRow);
      } else {
        this.rows.add(1, tbRow);
      } 
      this.rowCount++;
    } 
    if ((b != 0 && paramResultSet.isAfterLast()) || (b == 0 && paramResultSet.isBeforeFirst()))
      this.isPopulateCompleted = true; 
    this.conn = null;
    notifyRowSetChanged();
  }
  
  public void populate(ResultSet paramResultSet, int paramInt) throws SQLException {
    if (paramInt < 0)
      throw TbError.newSQLException(-90847, Integer.toString(paramInt)); 
    if (paramResultSet == null)
      throw TbError.newSQLException(-90823); 
    if (paramResultSet.getType() == 1003) {
      byte b = 0;
      for (b = 0; paramResultSet.next() && b < paramInt; b++);
      if (b < paramInt)
        throw TbError.newSQLException(-90868); 
    } else {
      paramResultSet.absolute(paramInt);
    } 
    populate(paramResultSet);
  }
  
  public boolean previous() throws SQLException {
    if (this.rowCount < 0)
      return false; 
    if (this.fetchDirection == 1001) {
      if (this.currentRowIndex + 1 <= this.rowCount) {
        this.currentRowIndex++;
        if (!this.showDeleted && getCurrentRow().isDeleted())
          return previous(); 
        notifyCursorMoved();
        return true;
      } 
      this.currentRowIndex = this.rowCount + 1;
      return false;
    } 
    if (this.fetchDirection == 1000 || this.fetchDirection == 1002) {
      if (this.currentRowIndex - 1 > 0) {
        this.currentRowIndex--;
        if (!this.showDeleted && getCurrentRow().isDeleted())
          return previous(); 
        notifyCursorMoved();
        return true;
      } 
      this.currentRowIndex = 0;
      return false;
    } 
    return false;
  }
  
  public boolean previousPage() throws SQLException {
    if (this.rs != null && this.rs.getType() == 1003)
      throw TbError.newSQLException(-90850); 
    if (this.rows.size() == 0 && !this.isPopulateCompleted)
      throw TbError.newSQLException(-90853); 
    if (this.fetchDirection == 1001) {
      this.rs.relative(this.pageSize * 2);
    } else {
      this.rs.relative(-2 * this.pageSize);
    } 
    populate(this.rs);
    if (this.currentPageIndex > 0)
      this.currentPageIndex--; 
    return (this.currentPageIndex != 0);
  }
  
  public void refreshRow() throws SQLException {
    TbRow tbRow = getCurrentRow();
    if (tbRow.isUpdated())
      tbRow.cancelUpdated(); 
  }
  
  public boolean relative(int paramInt) throws SQLException {
    return absolute(this.currentRowIndex + paramInt);
  }
  
  public void release() throws SQLException {
    this.rows = null;
    this.rows = new Vector<TbRow>();
    if (this.conn != null && !this.conn.isClosed())
      this.conn.close(); 
    this.rowCount = 0;
    this.currentRowIndex = 0;
    notifyRowSetChanged();
  }
  
  public void restoreOriginal() throws SQLException {
    boolean bool = false;
    for (byte b = 0; b < this.rowCount; b++) {
      TbRow tbRow = this.rows.elementAt(b);
      if (tbRow.isInserted()) {
        this.rows.remove(b);
        this.rowCount--;
        b--;
        bool = true;
      } else if (tbRow.isUpdated()) {
        tbRow.markUpdated(false);
        bool = true;
      } else if (tbRow.isDeleted()) {
        tbRow.markDeleted(false);
        bool = true;
      } 
    } 
    if (!bool)
      throw TbError.newSQLException(-90857); 
    notifyRowSetChanged();
    this.currentRowIndex = 0;
  }
  
  public void rollback() throws SQLException {
    getConnection().rollback();
  }
  
  public void rollback(Savepoint paramSavepoint) throws SQLException {
    Connection connection = getConnection();
    boolean bool = connection.getAutoCommit();
    connection.setAutoCommit(false);
    try {
      connection.rollback(paramSavepoint);
      connection.setAutoCommit(bool);
    } catch (SQLException sQLException) {
      connection.setAutoCommit(bool);
      throw sQLException;
    } 
  }
  
  public boolean rowDeleted() throws SQLException {
    return getCurrentRow().isDeleted();
  }
  
  public boolean rowInserted() throws SQLException {
    return getCurrentRow().isInserted();
  }
  
  public void rowSetPopulated(RowSetEvent paramRowSetEvent, int paramInt) throws SQLException {
    if (paramInt < 0 || paramInt < getFetchSize())
      throw TbError.newSQLException(-90848, Integer.toString(paramInt)); 
    if (size() % paramInt == 0) {
      this.rowsetEvent = new RowSetEvent(this);
      notifyRowSetChanged();
    } 
  }
  
  public boolean rowUpdated() throws SQLException {
    return getCurrentRow().isUpdated();
  }
  
  protected void setConnection(Connection paramConnection) throws SQLException {
    this.conn = paramConnection;
  }
  
  public void setFetchDirection(int paramInt) throws SQLException {
    if (this.rowsetType == 1005)
      throw TbError.newSQLException(-590759); 
    switch (paramInt) {
      case 1000:
      case 1002:
        this.currentRowIndex = 0;
        break;
      case 1001:
        if (this.rowsetType == 1003)
          throw TbError.newSQLException(-590760); 
        this.currentRowIndex = this.rowCount + 1;
        break;
      default:
        throw TbError.newSQLException(-90838, Integer.toString(paramInt));
    } 
    super.setFetchDirection(paramInt);
  }
  
  public void setKeyColumns(int[] paramArrayOfint) throws SQLException {
    int i = 0;
    if (this.rowsetMetaData != null) {
      i = this.rowsetMetaData.getColumnCount();
      if (paramArrayOfint == null || paramArrayOfint.length > i)
        throw TbError.newSQLException(-90839); 
    } 
    int j = paramArrayOfint.length;
    this.keyColumns = new int[j];
    for (byte b = 0; b < j; b++) {
      if (paramArrayOfint[b] <= 0 || paramArrayOfint[b] > i)
        throw TbError.newSQLException(-90834, Integer.toString(paramArrayOfint[b])); 
      this.keyColumns[b] = paramArrayOfint[b];
    } 
  }
  
  public void setMetaData(RowSetMetaData paramRowSetMetaData) throws SQLException {
    this.rowsetMetaData = paramRowSetMetaData;
    if (paramRowSetMetaData != null)
      this.columnCount = paramRowSetMetaData.getColumnCount(); 
  }
  
  public void setOriginal() throws SQLException {
    byte b = 1;
    while (true) {
      boolean bool = setOriginalInternal(b);
      if (!bool)
        b++; 
      if (b > this.rowCount) {
        notifyRowSetChanged();
        this.currentRowIndex = 0;
        return;
      } 
    } 
  }
  
  private boolean setOriginalInternal(int paramInt) throws SQLException {
    if (paramInt < 1 || paramInt > this.rowCount)
      throw TbError.newSQLException(-90837); 
    boolean bool = false;
    TbRow tbRow = this.rows.elementAt(paramInt - 1);
    if (tbRow.isDeleted()) {
      this.rows.remove(paramInt - 1);
      this.rowCount--;
      bool = true;
    } else {
      if (tbRow.isInserted())
        tbRow.markInserted(false); 
      if (tbRow.isUpdated())
        tbRow.commitChangedColumns(); 
    } 
    return bool;
  }
  
  public void setOriginalRow() throws SQLException {
    if (this.isOnInsert)
      throw TbError.newSQLException(-90851); 
    setOriginalInternal(this.currentRowIndex);
  }
  
  public void setPageSize(int paramInt) throws SQLException {
    if (paramInt < 0 || (this.maxRows > 0 && paramInt > this.maxRows))
      throw TbError.newSQLException(-90843, Integer.toString(paramInt)); 
    this.pageSize = paramInt;
  }
  
  public void setReader(RowSetReader paramRowSetReader) throws SQLException {
    this.reader = paramRowSetReader;
  }
  
  public void setSyncProvider(String paramString) throws SQLException {
    this.syncProvider = SyncFactory.getInstance(paramString);
    this.reader = this.syncProvider.getRowSetReader();
    this.writer = this.syncProvider.getRowSetWriter();
  }
  
  public void setWriter(RowSetWriter paramRowSetWriter) throws SQLException {
    this.writer = paramRowSetWriter;
  }
  
  public int size() {
    return this.rowCount;
  }
  
  public Collection<Collection<Object>> toCollection() throws SQLException {
    Map map = Collections.synchronizedMap(new TreeMap<Object, Object>());
    try {
      for (byte b = 0; b < this.rowCount; b++)
        map.put(b, ((TbRow)this.rows.elementAt(b)).toCollection());
    } catch (Exception exception) {
      map = null;
      throw TbError.newSQLException(-90831);
    } 
    return (Collection)map.values();
  }
  
  public Collection<Object> toCollection(int paramInt) throws SQLException {
    if (paramInt < 1 || paramInt > this.columnCount)
      throw TbError.newSQLException(-90834, Integer.toString(paramInt)); 
    Vector<Object> vector = new Vector(this.rowCount);
    for (byte b = 0; b < this.rowCount; b++) {
      TbRow tbRow = this.rows.elementAt(b);
      Object object = tbRow.isColumnChanged(b) ? tbRow.getChangedColumn(b) : tbRow.getColumn(b);
      vector.add(object);
    } 
    return vector;
  }
  
  public Collection<Object> toCollection(String paramString) throws SQLException {
    return toCollection(findColumn(paramString));
  }
  
  public void undoDelete() throws SQLException {
    cancelRowDelete();
  }
  
  public void undoInsert() throws SQLException {
    cancelRowInsert();
  }
  
  public void undoUpdate() throws SQLException {
    cancelRowUpdates();
  }
  
  public <T> T unwrap(Class<T> paramClass) throws SQLException {
    try {
      return paramClass.cast(this);
    } catch (ClassCastException classCastException) {
      throw TbError.newSQLException(-90657);
    } 
  }
  
  public void updateArray(int paramInt, Array paramArray) throws SQLException {
    updateObject(paramInt, paramArray);
  }
  
  public void updateArray(String paramString, Array paramArray) throws SQLException {
    updateArray(findColumn(paramString), paramArray);
  }
  
  public void updateAsciiStream(int paramInt, InputStream paramInputStream) throws SQLException {
    updateAsciiStream(paramInt, paramInputStream, 2147483647);
  }
  
  public void updateAsciiStream(int paramInt1, InputStream paramInputStream, int paramInt2) throws SQLException {
    try {
      InputStreamReader inputStreamReader = new InputStreamReader(paramInputStream);
      char[] arrayOfChar = new char[paramInt2];
      int i = 0;
      while (true) {
        i += inputStreamReader.read(arrayOfChar, i, paramInt2 - i);
        if (i >= paramInt2) {
          updateObject(paramInt1, new CharArrayReader(arrayOfChar));
          arrayOfChar = null;
          return;
        } 
      } 
    } catch (IOException iOException) {
      throw TbError.newSQLException(-90829, iOException.getMessage());
    } 
  }
  
  public void updateAsciiStream(int paramInt, InputStream paramInputStream, long paramLong) throws SQLException {
    if (paramLong > 2147483647L)
      throw TbError.newSQLException(-90656, Long.toString(paramLong)); 
    updateAsciiStream(paramInt, paramInputStream, (int)paramLong);
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
  
  public void updateBigDecimal(int paramInt, BigDecimal paramBigDecimal) throws SQLException {
    updateObject(paramInt, paramBigDecimal);
  }
  
  public void updateBigDecimal(String paramString, BigDecimal paramBigDecimal) throws SQLException {
    updateBigDecimal(findColumn(paramString), paramBigDecimal);
  }
  
  public void updateBinaryStream(int paramInt, InputStream paramInputStream) throws SQLException {
    updateBinaryStream(paramInt, paramInputStream, 2147483647);
  }
  
  public void updateBinaryStream(int paramInt1, InputStream paramInputStream, int paramInt2) throws SQLException {
    try {
      byte[] arrayOfByte = new byte[paramInt2];
      int i = 0;
      while (true) {
        i += paramInputStream.read(arrayOfByte, i, paramInt2 - i);
        if (i >= paramInt2) {
          updateObject(paramInt1, new ByteArrayInputStream(arrayOfByte));
          arrayOfByte = null;
          return;
        } 
      } 
    } catch (IOException iOException) {
      throw TbError.newSQLException(-90829, iOException.getMessage());
    } 
  }
  
  public void updateBinaryStream(int paramInt, InputStream paramInputStream, long paramLong) throws SQLException {
    if (paramLong > 2147483647L)
      throw TbError.newSQLException(-90656, Long.toString(paramLong)); 
    updateBinaryStream(paramInt, paramInputStream, (int)paramLong);
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
  
  public void updateBlob(int paramInt, Blob paramBlob) throws SQLException {
    updateObject(paramInt, paramBlob);
  }
  
  public void updateBlob(int paramInt, InputStream paramInputStream) throws SQLException {
    updateBinaryStream(paramInt, paramInputStream);
  }
  
  public void updateBlob(int paramInt, InputStream paramInputStream, long paramLong) throws SQLException {
    updateBinaryStream(paramInt, paramInputStream, paramLong);
  }
  
  public void updateBlob(String paramString, Blob paramBlob) throws SQLException {
    updateBlob(findColumn(paramString), paramBlob);
  }
  
  public void updateBlob(String paramString, InputStream paramInputStream) throws SQLException {
    updateBinaryStream(findColumn(paramString), paramInputStream);
  }
  
  public void updateBlob(String paramString, InputStream paramInputStream, long paramLong) throws SQLException {
    updateBinaryStream(findColumn(paramString), paramInputStream, paramLong);
  }
  
  public void updateBoolean(int paramInt, boolean paramBoolean) throws SQLException {
    updateObject(paramInt, paramBoolean);
  }
  
  public void updateBoolean(String paramString, boolean paramBoolean) throws SQLException {
    updateBoolean(findColumn(paramString), paramBoolean);
  }
  
  public void updateByte(int paramInt, byte paramByte) throws SQLException {
    updateObject(paramInt, paramByte);
  }
  
  public void updateByte(String paramString, byte paramByte) throws SQLException {
    updateByte(findColumn(paramString), paramByte);
  }
  
  public void updateBytes(int paramInt, byte[] paramArrayOfbyte) throws SQLException {
    updateObject(paramInt, paramArrayOfbyte);
  }
  
  public void updateBytes(String paramString, byte[] paramArrayOfbyte) throws SQLException {
    updateBytes(findColumn(paramString), paramArrayOfbyte);
  }
  
  public void updateCharacterStream(int paramInt, Reader paramReader) throws SQLException {
    updateCharacterStream(paramInt, paramReader, 2147483647);
  }
  
  public void updateCharacterStream(int paramInt1, Reader paramReader, int paramInt2) throws SQLException {
    try {
      char[] arrayOfChar = new char[paramInt2];
      int i = 0;
      while (true) {
        i += paramReader.read(arrayOfChar, i, paramInt2 - i);
        if (i >= paramInt2) {
          updateObject(paramInt1, new CharArrayReader(arrayOfChar));
          arrayOfChar = null;
          return;
        } 
      } 
    } catch (IOException iOException) {
      throw TbError.newSQLException(-90829, iOException.getMessage());
    } 
  }
  
  public void updateCharacterStream(int paramInt, Reader paramReader, long paramLong) throws SQLException {
    if (paramLong > 2147483647L)
      throw TbError.newSQLException(-90656, Long.toString(paramLong)); 
    updateCharacterStream(paramInt, paramReader, (int)paramLong);
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
  
  public void updateClob(int paramInt, Clob paramClob) throws SQLException {
    updateObject(paramInt, paramClob);
  }
  
  public void updateClob(int paramInt, Reader paramReader) throws SQLException {
    updateCharacterStream(paramInt, paramReader);
  }
  
  public void updateClob(int paramInt, Reader paramReader, long paramLong) throws SQLException {
    updateCharacterStream(paramInt, paramReader, paramLong);
  }
  
  public void updateClob(String paramString, Clob paramClob) throws SQLException {
    updateClob(findColumn(paramString), paramClob);
  }
  
  public void updateClob(String paramString, Reader paramReader) throws SQLException {
    updateCharacterStream(findColumn(paramString), paramReader);
  }
  
  public void updateClob(String paramString, Reader paramReader, long paramLong) throws SQLException {
    updateCharacterStream(findColumn(paramString), paramReader, paramLong);
  }
  
  public void updateDate(int paramInt, Date paramDate) throws SQLException {
    updateObject(paramInt, paramDate);
  }
  
  public void updateDate(String paramString, Date paramDate) throws SQLException {
    updateDate(findColumn(paramString), paramDate);
  }
  
  public void updateDouble(int paramInt, double paramDouble) throws SQLException {
    updateObject(paramInt, paramDouble);
  }
  
  public void updateDouble(String paramString, double paramDouble) throws SQLException {
    updateDouble(findColumn(paramString), paramDouble);
  }
  
  public void updateFloat(int paramInt, float paramFloat) throws SQLException {
    updateObject(paramInt, paramFloat);
  }
  
  public void updateFloat(String paramString, float paramFloat) throws SQLException {
    updateFloat(findColumn(paramString), paramFloat);
  }
  
  public void updateInt(int paramInt1, int paramInt2) throws SQLException {
    updateObject(paramInt1, paramInt2);
  }
  
  public void updateInt(String paramString, int paramInt) throws SQLException {
    updateInt(findColumn(paramString), paramInt);
  }
  
  public void updateLong(int paramInt, long paramLong) throws SQLException {
    updateObject(paramInt, paramLong);
  }
  
  public void updateLong(String paramString, long paramLong) throws SQLException {
    updateLong(findColumn(paramString), paramLong);
  }
  
  public void updateNCharacterStream(int paramInt, Reader paramReader) throws SQLException {
    updateCharacterStream(paramInt, paramReader);
  }
  
  public void updateNCharacterStream(int paramInt, Reader paramReader, long paramLong) throws SQLException {
    updateCharacterStream(paramInt, paramReader, paramLong);
  }
  
  public void updateNCharacterStream(String paramString, Reader paramReader) throws SQLException {
    updateCharacterStream(findColumn(paramString), paramReader);
  }
  
  public void updateNCharacterStream(String paramString, Reader paramReader, long paramLong) throws SQLException {
    updateCharacterStream(findColumn(paramString), paramReader, paramLong);
  }
  
  public void updateNClob(int paramInt, NClob paramNClob) throws SQLException {
    updateObject(paramInt, paramNClob);
  }
  
  public void updateNClob(int paramInt, Reader paramReader) throws SQLException {
    updateCharacterStream(paramInt, paramReader);
  }
  
  public void updateNClob(int paramInt, Reader paramReader, long paramLong) throws SQLException {
    updateCharacterStream(paramInt, paramReader, paramLong);
  }
  
  public void updateNClob(String paramString, NClob paramNClob) throws SQLException {
    updateNClob(findColumn(paramString), paramNClob);
  }
  
  public void updateNClob(String paramString, Reader paramReader) throws SQLException {
    updateCharacterStream(findColumn(paramString), paramReader);
  }
  
  public void updateNClob(String paramString, Reader paramReader, long paramLong) throws SQLException {
    updateCharacterStream(findColumn(paramString), paramReader, paramLong);
  }
  
  public void updateNString(int paramInt, String paramString) throws SQLException {
    updateObject(paramInt, paramString);
  }
  
  public void updateNString(String paramString1, String paramString2) throws SQLException {
    updateNString(findColumn(paramString1), paramString2);
  }
  
  public void updateNull(int paramInt) throws SQLException {
    updateObject(paramInt, (Object)null);
  }
  
  public void updateNull(String paramString) throws SQLException {
    updateNull(findColumn(paramString));
  }
  
  public void updateObject(int paramInt, Object paramObject) throws SQLException {
    checkColumnIndex(paramInt);
    if (this.isOnInsert) {
      this.temporaryRow.updateObject(paramInt, paramObject);
    } else if (!isBeforeFirst() && !isAfterLast()) {
      this.isOnUpdate = true;
      this.updateRowIndex = this.currentRowIndex;
      getCurrentRow().updateObject(paramInt, paramObject);
    } else {
      throw TbError.newSQLException(-90855);
    } 
  }
  
  public void updateObject(int paramInt1, Object paramObject, int paramInt2) throws SQLException {
    if (paramObject instanceof Number) {
      updateObject(paramInt1, new BigDecimal(new BigInteger(((Number)paramObject).toString()), paramInt2));
    } else {
      throw TbError.newSQLException(-90836, paramObject.toString());
    } 
  }
  
  public void updateObject(String paramString, Object paramObject) throws SQLException {
    updateObject(findColumn(paramString), paramObject);
  }
  
  public void updateObject(String paramString, Object paramObject, int paramInt) throws SQLException {
    updateObject(findColumn(paramString), paramObject, paramInt);
  }
  
  public void updateRef(int paramInt, Ref paramRef) throws SQLException {
    updateObject(paramInt, paramRef);
  }
  
  public void updateRef(String paramString, Ref paramRef) throws SQLException {
    updateRef(findColumn(paramString), paramRef);
  }
  
  public void updateRow() throws SQLException {
    if (this.isOnUpdate) {
      this.isOnUpdate = false;
      getCurrentRow().markUpdated(true);
      notifyRowChanged();
    } else {
      throw TbError.newSQLException(-90824);
    } 
  }
  
  public void updateRowId(int paramInt, RowId paramRowId) throws SQLException {
    updateObject(paramInt, paramRowId);
  }
  
  public void updateRowId(String paramString, RowId paramRowId) throws SQLException {
    updateRowId(findColumn(paramString), paramRowId);
  }
  
  public void updateShort(int paramInt, short paramShort) throws SQLException {
    updateObject(paramInt, paramShort);
  }
  
  public void updateShort(String paramString, short paramShort) throws SQLException {
    updateShort(findColumn(paramString), paramShort);
  }
  
  public void updateSQLXML(int paramInt, SQLXML paramSQLXML) throws SQLException {
    updateObject(paramInt, paramSQLXML);
  }
  
  public void updateSQLXML(String paramString, SQLXML paramSQLXML) throws SQLException {
    updateSQLXML(findColumn(paramString), paramSQLXML);
  }
  
  public void updateString(int paramInt, String paramString) throws SQLException {
    updateObject(paramInt, paramString);
  }
  
  public void updateString(String paramString1, String paramString2) throws SQLException {
    updateString(findColumn(paramString1), paramString2);
  }
  
  public void updateTime(int paramInt, Time paramTime) throws SQLException {
    updateObject(paramInt, paramTime);
  }
  
  public void updateTime(String paramString, Time paramTime) throws SQLException {
    updateTime(findColumn(paramString), paramTime);
  }
  
  public void updateTimestamp(int paramInt, Timestamp paramTimestamp) throws SQLException {
    updateObject(paramInt, paramTimestamp);
  }
  
  public void updateTimestamp(String paramString, Timestamp paramTimestamp) throws SQLException {
    updateTimestamp(findColumn(paramString), paramTimestamp);
  }
  
  public boolean wasNull() throws SQLException {
    return this.prevColumnWasNull;
  }
}


/* Location:              C:\TmaxData\tibero6\client\lib\jar\tibero6-jdbc.jar!\com\tmax\tibero\jdbc\rowset\TbCachedRowSet.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */