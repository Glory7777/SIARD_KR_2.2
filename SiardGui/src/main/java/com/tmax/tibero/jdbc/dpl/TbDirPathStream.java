package com.tmax.tibero.jdbc.dpl;

import com.tmax.tibero.jdbc.TbDatabaseMetaData;
import com.tmax.tibero.jdbc.TbIntervalDts;
import com.tmax.tibero.jdbc.TbIntervalYtm;
import com.tmax.tibero.jdbc.comm.TbStream;
import com.tmax.tibero.jdbc.comm.TbStreamDataWriter;
import com.tmax.tibero.jdbc.data.DataType;
import com.tmax.tibero.jdbc.data.ServerInfo;
import com.tmax.tibero.jdbc.data.StreamBuffer;
import com.tmax.tibero.jdbc.data.TbDate;
import com.tmax.tibero.jdbc.data.TbTimestamp;
import com.tmax.tibero.jdbc.data.TbTimestampTZ;
import com.tmax.tibero.jdbc.dpl.binder.DPLBinder;
import com.tmax.tibero.jdbc.dpl.binder.DPLLongStreamBinder;
import com.tmax.tibero.jdbc.dpl.binder.DPLReaderBinder;
import com.tmax.tibero.jdbc.dpl.binder.DPLStreamBinder;
import com.tmax.tibero.jdbc.dpl.binder.StaticDPLBinder;
import com.tmax.tibero.jdbc.driver.TbConnection;
import com.tmax.tibero.jdbc.err.TbError;
import com.tmax.tibero.jdbc.msg.TbColumnDesc;
import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.TimeZone;

public class TbDirPathStream {
  public static final int DPL_BIND_STREAM = -1;
  
  public static final int DPL_LOAD_STREAM_SUCCESS = 0;
  
  public static final int DPL_LOAD_STREAM_NEED_DATA = 1;
  
  public static final int DPL_LOAD_STREAM_NO_DATA = 2;
  
  public static final int DPL_LOAD_STREAM_FAIL = 3;
  
  public static final int SQL_DPL_DATASAVE_SAVE_ONLY = 0;
  
  public static final int SQL_DPL_DATASAVE_FINISH = 1;
  
  public static final int DPL_PARAM_INFO_OFFSET = 24;
  
  private int returnCode;
  
  private int rowCnt;
  
  private int totalRowCnt;
  
  private TbConnection conn = null;
  
  private TbDirPathMetaData metaData = null;
  
  private int bindParamCnt;
  
  private int[] paramLength = null;
  
  private TbStreamDataWriter writer = null;
  
  private int[] paramInt;
  
  private long[] paramLong;
  
  private float[] paramFloat;
  
  private double[] paramDouble;
  
  private BigDecimal[] paramBigDecimal;
  
  private String[] paramString;
  
  private Date[] paramDate;
  
  private TbDate[] paramTbDate;
  
  private Time[] paramTime;
  
  private Timestamp[] paramTimestamp;
  
  private TbTimestamp[] paramTbTimestamp;
  
  private TbTimestampTZ[] paramTbTimestampTZ;
  
  private byte[][] paramBytes;
  
  private Reader[] paramReader;
  
  private InputStream[] paramStream;
  
  private Object[] paramObject;
  
  private TbIntervalYtm[] paramTbIntervalYtm;
  
  private TbIntervalDts[] paramTbIntervalDts;
  
  private DPLBinder[] binder;
  
  private DPLBinder staticNullDPLBinder = StaticDPLBinder.nullDPLBinder;
  
  private DPLBinder staticStringDPLBinder = StaticDPLBinder.stringDPLBinder;
  
  private DPLBinder staticReaderDPLBinder = (DPLBinder)new DPLReaderBinder();
  
  private DPLBinder staticIntDPLBinder = StaticDPLBinder.intDPLBinder;
  
  private DPLBinder staticLongDPLBinder = StaticDPLBinder.longDPLBinder;
  
  private DPLBinder staticFloatDPLBinder = StaticDPLBinder.floatDPLBinder;
  
  private DPLBinder staticDoubleDPLBinder = StaticDPLBinder.doubleDPLBinder;
  
  private DPLBinder staticBigDecimalDPLBinder = StaticDPLBinder.bigDecimalDPLBinder;
  
  private DPLBinder staticDateDPLBinder = StaticDPLBinder.dateDPLBinder;
  
  private DPLBinder staticTbDateDPLBinder = StaticDPLBinder.tbDateDPLBinder;
  
  private DPLBinder staticTimeDPLBinder = StaticDPLBinder.timeDPLBinder;
  
  private DPLBinder staticTimestampDPLBinder = StaticDPLBinder.timestampDPLBinder;
  
  private DPLBinder staticTbTimestampDPLBinder = StaticDPLBinder.tbTimestampDPLBinder;
  
  private DPLBinder staticTbTimestampTZDPLBinder = StaticDPLBinder.tbTimestampTZDPLBinder;
  
  private DPLBinder staticTimestampLTZDPLBinder = StaticDPLBinder.timestampLTZDPLBinder;
  
  private DPLBinder staticRawDPLBinder = StaticDPLBinder.rawDPLBinder;
  
  private DPLBinder staticBytesDPLBinder = StaticDPLBinder.bytesDPLBinder;
  
  private DPLBinder staticRowIdDPLBinder = StaticDPLBinder.rowIdDPLBinder;
  
  private DPLBinder staticLongStreamDPLBinder = (DPLBinder)new DPLLongStreamBinder();
  
  private DPLBinder staticStreamDPLBinder = (DPLBinder)new DPLStreamBinder();
  
  private DPLBinder staticEmptyLOBDPLBinder = StaticDPLBinder.emptyLOBDPLBinder;
  
  private DPLBinder staticTbIntervalYtmBinder = StaticDPLBinder.tbIntervalYtmDPLBinder;
  
  private DPLBinder staticTbIntervalDtsBinder = StaticDPLBinder.tbIntervalDtsDPLBinder;
  
  private TbStreamDataWriter tempWriter = null;
  
  private boolean[] isWritten = null;
  
  private int tempColumnIndex = 0;
  
  public TbDirPathStream(TbConnection paramTbConnection, TbDirPathMetaData paramTbDirPathMetaData) throws SQLException {
    this.conn = paramTbConnection;
    this.metaData = paramTbDirPathMetaData;
    this.bindParamCnt = paramTbDirPathMetaData.getColumnCnt();
    this.binder = new DPLBinder[this.bindParamCnt];
    TbStream tbStream = this.conn.getTbComm().getStream();
    this.writer = tbStream.createDirPathWriter(paramTbDirPathMetaData.getBufferSize());
    this.writer.clearDPLBuffer();
    this.totalRowCnt = 0;
    initReturnInfo();
    if (this.metaData.getParallelFlag() || (this.metaData.getPartition() != null && this.metaData.getPartition().length() > 0)) {
      ServerInfo serverInfo = paramTbConnection.getServerInfo();
      if (serverInfo.getProtocolMajorVersion() * 100 + serverInfo.getProtocolMinorVersion() < 205)
        throw TbError.newSQLException(-90662); 
      prepareParallel();
    } else {
      prepare();
    } 
  }
  
  public TbDirPathStream(TbConnection paramTbConnection, TbDirPathMetaData paramTbDirPathMetaData, boolean paramBoolean) throws SQLException {
    this.conn = paramTbConnection;
    this.metaData = paramTbDirPathMetaData;
    this.bindParamCnt = paramTbDirPathMetaData.getColumnCnt();
    this.binder = new DPLBinder[this.bindParamCnt];
    this.isWritten = new boolean[this.bindParamCnt];
    this.totalRowCnt = 0;
    initReturnInfo();
    if (this.conn.isClosed())
      throw TbError.newSQLException(-90603); 
    this.metaData.checkDirPathMetaData();
    this.paramLength = new int[this.bindParamCnt];
    if (this.conn.isClosed())
      throw TbError.newSQLException(-90603); 
    TbDirPathMetaData tbDirPathMetaData = paramTbDirPathMetaData;
    int i = tbDirPathMetaData.getColumnCnt();
    TbColumnDesc[] arrayOfTbColumnDesc = new TbColumnDesc[i];
    PreparedStatement preparedStatement = this.conn.prepareStatement("select COLUMN_NAME, DATA_TYPE from all_tab_columns where OWNER=? and TABLE_NAME=?");
    preparedStatement.setString(1, paramTbDirPathMetaData.getSchema());
    preparedStatement.setString(2, paramTbDirPathMetaData.getTable());
    ResultSet resultSet = preparedStatement.executeQuery();
    while (resultSet.next()) {
      String str = resultSet.getString(1);
      for (byte b1 = 0; b1 < i; b1++) {
        if (str.equals(paramTbDirPathMetaData.getColumn(b1 + 1))) {
          arrayOfTbColumnDesc[b1] = new TbColumnDesc();
          arrayOfTbColumnDesc[b1].set(str, DataType.toIntDataType(resultSet.getString(2)), 0, 0, 0, 0);
          break;
        } 
      } 
    } 
    for (byte b = 0; b < i; b++) {
      if (arrayOfTbColumnDesc[b] == null)
        throw TbError.newSQLException(-90544); 
    } 
    paramTbDirPathMetaData.setColumnMetas(arrayOfTbColumnDesc);
  }
  
  public void abort() throws SQLException {
    if (this.conn.isClosed())
      throw TbError.newSQLException(-90603); 
    synchronized (this.conn) {
      this.conn.getTbComm().dirPathAbort();
    } 
  }
  
  public void addTotalRowCnt(int paramInt) {
    this.totalRowCnt += paramInt;
  }
  
  public int getTotalRowCnt() {
    return this.totalRowCnt;
  }
  
  protected void checkParameterIndex(int paramInt) throws SQLException {
    if (paramInt < 1 || paramInt > this.metaData.getColumnCnt())
      throw TbError.newSQLException(-90609); 
  }
  
  public void clearBindData() {
    clearArray((Object[])this.paramBigDecimal);
    clearArray((Object[])this.paramString);
    clearArray((Object[])this.paramDate);
    clearArray((Object[])this.paramTime);
    clearArray((Object[])this.paramTimestamp);
    clearArray((Object[])this.paramTbTimestamp);
    clearArray((Object[])this.paramTbTimestampTZ);
    clearArray((Object[])this.paramBytes);
    clearArray((Object[])this.paramReader);
    clearArray((Object[])this.paramStream);
    clearArray(this.paramObject);
    clearArray((Object[])this.binder);
  }
  
  private void clearArray(Object[] paramArrayOfObject) {
    if (paramArrayOfObject == null)
      return; 
    for (byte b = 0; b < paramArrayOfObject.length; b++)
      paramArrayOfObject[b] = null; 
  }
  
  public void close() {
    reset();
  }
  
  private void copyStream() throws SQLException {
    try {
      for (byte b = 0; b < this.bindParamCnt; b++) {
        if (this.binder[b] == null)
          throw TbError.newSQLException(-90627); 
        this.binder[b].bind(this.conn, this, this.writer, b, this.paramLength[b]);
      } 
    } catch (SQLException sQLException) {
      this.writer.clearDPLBuffer();
      throw sQLException;
    } 
  }
  
  public void dataSave(int paramInt) throws SQLException {
    if (this.conn.isClosed())
      throw TbError.newSQLException(-90603); 
    synchronized (this.conn) {
      this.conn.getTbComm().dirPathDataSave(paramInt);
    } 
  }
  
  public void dirPathLoadStream(TbStreamDataWriter paramTbStreamDataWriter, int paramInt) throws SQLException {
    if (this.conn.isClosed())
      throw TbError.newSQLException(-90603); 
    synchronized (this.conn) {
      this.conn.getTbComm().dirPathLoadStream(this, paramTbStreamDataWriter, paramInt);
    } 
  }
  
  public void finish() throws SQLException {
    if (this.conn.isClosed())
      throw TbError.newSQLException(-90603); 
    synchronized (this.conn) {
      this.conn.getTbComm().dirPathFinish();
    } 
  }
  
  public void flushRow() throws SQLException {
    if (this.conn.isClosed())
      throw TbError.newSQLException(-90603); 
    synchronized (this.conn) {
      this.conn.getTbComm().dirPathFlushRow();
    } 
  }
  
  public int getBindDataSize() {
    return this.writer.getBufferedDataSize();
  }
  
  public DPLBinder[] getBinder() {
    return this.binder;
  }
  
  public TbDirPathMetaData getDPLMetaData() {
    return this.metaData;
  }
  
  public TbStreamDataWriter getDPLStreamDataWriter() {
    return this.writer;
  }
  
  public BigDecimal getParamBigDecimal(int paramInt) {
    return this.paramBigDecimal[paramInt];
  }
  
  public byte[] getParamBytes(int paramInt) {
    return this.paramBytes[paramInt];
  }
  
  public Date getParamDate(int paramInt) {
    return this.paramDate[paramInt];
  }
  
  public TbDate getParamTbDate(int paramInt) {
    return this.paramTbDate[paramInt];
  }
  
  public double getParamDouble(int paramInt) {
    return this.paramDouble[paramInt];
  }
  
  public float getParamFloat(int paramInt) {
    return this.paramFloat[paramInt];
  }
  
  public int getParamInt(int paramInt) {
    return this.paramInt[paramInt];
  }
  
  public int getParamLength(int paramInt) {
    return this.paramLength[paramInt];
  }
  
  public long getParamLong(int paramInt) {
    return this.paramLong[paramInt];
  }
  
  public Object getParamObject(int paramInt) {
    return this.paramObject[paramInt];
  }
  
  public Reader getParamReader(int paramInt) {
    return this.paramReader[paramInt];
  }
  
  public InputStream getParamStream(int paramInt) {
    return this.paramStream[paramInt];
  }
  
  public String getParamString(int paramInt) {
    return this.paramString[paramInt];
  }
  
  public Time getParamTime(int paramInt) {
    return this.paramTime[paramInt];
  }
  
  public Timestamp getParamTimestamp(int paramInt) {
    return this.paramTimestamp[paramInt];
  }
  
  public TbTimestamp getParamTbTimestamp(int paramInt) {
    return this.paramTbTimestamp[paramInt];
  }
  
  public TbTimestampTZ getParamTbTimestampTZ(int paramInt) {
    return this.paramTbTimestampTZ[paramInt];
  }
  
  public TbIntervalYtm getParamTbIntervalYtm(int paramInt) {
    return this.paramTbIntervalYtm[paramInt];
  }
  
  public TbIntervalDts getParamTbIntervalDts(int paramInt) {
    return this.paramTbIntervalDts[paramInt];
  }
  
  public int getReturnCode() {
    return this.returnCode;
  }
  
  public int getRowCnt() {
    return this.rowCnt;
  }
  
  public boolean hasBindData() {
    return (this.writer.getBufferedDataSize() > 24);
  }
  
  private void initReturnInfo() {
    this.returnCode = -1;
    this.rowCnt = 0;
  }
  
  public int loadStream() throws SQLException {
    initReturnInfo();
    copyStream();
    dirPathLoadStream(this.writer, 0);
    return this.returnCode;
  }
  
  public int loadStreamBatch(int paramInt) throws SQLException {
    initReturnInfo();
    if ((paramInt & 0x100) != 0)
      copyStream(); 
    if ((paramInt & 0x10) != 0) {
      if (this.writer.getBufferedDataSize() > this.metaData.getBufferSize())
        dirPathLoadStream(this.writer, 0); 
    } else {
      dirPathLoadStream(this.writer, 0);
    } 
    return this.returnCode;
  }
  
  public void setBuffer(TbConnection paramTbConnection, byte[] paramArrayOfbyte) throws SQLException {
    StreamBuffer streamBuffer = new StreamBuffer(paramArrayOfbyte);
    this.tempWriter = new TbStreamDataWriter(streamBuffer, paramTbConnection.getTypeConverter());
  }
  
  public int getBufferedDataSize() throws SQLException {
    if (this.tempWriter == null)
      return 0; 
    int i = this.tempWriter.getBufferedDataSize();
    this.tempWriter = null;
    return i;
  }
  
  public void initBinder() throws SQLException {
    this.tempColumnIndex = 0;
    for (byte b = 0; b < this.bindParamCnt; b++) {
      if (this.binder[b] == null)
        throw TbError.newSQLException(-90627); 
      this.binder[b].init();
      this.isWritten[b] = false;
    } 
  }
  
  public int getPartialColumnIndex() {
    return this.tempColumnIndex + 1;
  }
  
  public int writeRowChunk(TbConnection paramTbConnection) throws SQLException {
    int i = 0;
    try {
      for (int j = this.tempColumnIndex; j < this.bindParamCnt; j++) {
        i = this.binder[j].bind(paramTbConnection, this, this.tempWriter, j, this.paramLength[j], this.isWritten[j]);
        switch (i) {
          case 0:
            this.isWritten[j] = true;
            this.tempColumnIndex++;
            break;
          case 1:
            this.isWritten[j] = true;
            return 1;
          case 2:
            this.isWritten[j] = true;
            this.tempColumnIndex++;
            return 0;
          default:
            return 0;
        } 
      } 
      return 0;
    } catch (SQLException sQLException) {
      throw sQLException;
    } 
  }
  
  public void prepare() throws SQLException {
    if (this.conn.isClosed())
      throw TbError.newSQLException(-90603); 
    this.metaData.checkDirPathMetaData();
    this.paramLength = new int[this.bindParamCnt];
    if (this.conn.isClosed())
      throw TbError.newSQLException(-90603); 
    synchronized (this.conn) {
      this.conn.getTbComm().dirPathPrepare(this);
    } 
  }
  
  public void prepareParallel() throws SQLException {
    if (this.conn.isClosed())
      throw TbError.newSQLException(-90603); 
    this.metaData.checkDirPathMetaData();
    if (this.conn.isClosed())
      throw TbError.newSQLException(-90603); 
    this.paramLength = new int[this.bindParamCnt];
    synchronized (this.conn) {
      this.conn.getTbComm().dirPathPrepareParallel(this);
    } 
  }
  
  private void reset() {
    this.conn = null;
    this.writer = null;
    this.paramLength = null;
    if (this.metaData != null) {
      this.metaData.reset();
      this.metaData = null;
    } 
  }
  
  public void setAsciiStream(int paramInt1, InputStream paramInputStream, int paramInt2) throws SQLException {
    setBinaryStream(paramInt1, paramInputStream, paramInt2);
  }
  
  public void setBigDecimal(int paramInt, BigDecimal paramBigDecimal) throws SQLException {
    if (paramBigDecimal == null) {
      setNull(paramInt);
      return;
    } 
    checkParameterIndex(paramInt);
    if (this.paramBigDecimal == null)
      this.paramBigDecimal = new BigDecimal[this.bindParamCnt]; 
    this.paramLength[paramInt - 1] = 0;
    this.paramBigDecimal[paramInt - 1] = paramBigDecimal;
    this.binder[paramInt - 1] = this.staticBigDecimalDPLBinder;
  }
  
  public void setBinaryStream(int paramInt1, InputStream paramInputStream, int paramInt2) throws SQLException {
    if (paramInputStream == null || paramInt2 <= 0) {
      setNull(paramInt1);
      return;
    } 
    checkParameterIndex(paramInt1);
    if (this.paramStream == null)
      this.paramStream = new InputStream[this.bindParamCnt]; 
    this.paramLength[paramInt1 - 1] = paramInt2;
    this.paramStream[paramInt1 - 1] = paramInputStream;
    this.binder[paramInt1 - 1] = this.staticStreamDPLBinder;
  }
  
  public void setBoolean(int paramInt, boolean paramBoolean) throws SQLException {
    checkParameterIndex(paramInt);
    if (this.paramString == null)
      this.paramString = new String[this.bindParamCnt]; 
    this.paramLength[paramInt - 1] = 20;
    this.paramString[paramInt - 1] = paramBoolean ? "1" : "0";
    this.binder[paramInt - 1] = this.staticStringDPLBinder;
  }
  
  public void setByte(int paramInt, byte paramByte) throws SQLException {
    checkParameterIndex(paramInt);
    if (this.paramInt == null)
      this.paramInt = new int[this.bindParamCnt]; 
    this.paramLength[paramInt - 1] = 1;
    this.paramInt[paramInt - 1] = paramByte;
    this.binder[paramInt - 1] = this.staticIntDPLBinder;
  }
  
  public void setBytes(int paramInt, byte[] paramArrayOfbyte) throws SQLException {
    if (paramArrayOfbyte == null) {
      setNull(paramInt);
      return;
    } 
    checkParameterIndex(paramInt);
    if (this.paramBytes == null)
      this.paramBytes = new byte[this.bindParamCnt][]; 
    this.paramLength[paramInt - 1] = paramArrayOfbyte.length;
    this.paramBytes[paramInt - 1] = paramArrayOfbyte;
    this.binder[paramInt - 1] = this.staticBytesDPLBinder;
  }
  
  public void setBytes(int paramInt1, int paramInt2, byte[] paramArrayOfbyte) throws SQLException {
    if (paramArrayOfbyte == null) {
      setNull(paramInt1);
      return;
    } 
    checkParameterIndex(paramInt1);
    if (this.paramBytes == null)
      this.paramBytes = new byte[this.bindParamCnt][]; 
    this.paramLength[paramInt1 - 1] = paramArrayOfbyte.length;
    this.paramBytes[paramInt1 - 1] = paramArrayOfbyte;
    this.binder[paramInt1 - 1] = this.staticRawDPLBinder;
  }
  
  public void setCharacterStream(int paramInt1, Reader paramReader, int paramInt2) throws SQLException {
    if (paramReader == null || paramInt2 <= 0) {
      setNull(paramInt1);
      return;
    } 
    checkParameterIndex(paramInt1);
    if (this.paramReader == null)
      this.paramReader = new Reader[this.bindParamCnt]; 
    this.paramLength[paramInt1 - 1] = paramInt2;
    this.paramReader[paramInt1 - 1] = paramReader;
    this.binder[paramInt1 - 1] = this.staticReaderDPLBinder;
  }
  
  public void setDate(int paramInt, Date paramDate) throws SQLException {
    if (paramDate == null) {
      setNull(paramInt);
      return;
    } 
    checkParameterIndex(paramInt);
    if (this.paramDate == null)
      this.paramDate = new Date[this.bindParamCnt]; 
    this.paramLength[paramInt - 1] = 0;
    this.paramDate[paramInt - 1] = paramDate;
    this.binder[paramInt - 1] = this.staticDateDPLBinder;
  }
  
  public void setDate(int paramInt, Date paramDate, Calendar paramCalendar) throws SQLException {
    setDate(paramInt, paramDate);
  }
  
  public void setTbDate(int paramInt, TbDate paramTbDate) throws SQLException {
    if (paramTbDate == null) {
      setNull(paramInt);
      return;
    } 
    checkParameterIndex(paramInt);
    if (this.paramTbDate == null)
      this.paramTbDate = new TbDate[this.bindParamCnt]; 
    this.paramLength[paramInt - 1] = 0;
    this.paramTbDate[paramInt - 1] = paramTbDate;
    this.binder[paramInt - 1] = this.staticTbDateDPLBinder;
  }
  
  public void setDouble(int paramInt, double paramDouble) throws SQLException {
    checkParameterIndex(paramInt);
    if (this.paramDouble == null)
      this.paramDouble = new double[this.bindParamCnt]; 
    this.paramLength[paramInt - 1] = 0;
    this.paramDouble[paramInt - 1] = paramDouble;
    this.binder[paramInt - 1] = this.staticDoubleDPLBinder;
  }
  
  public void setEmptyLob(int paramInt) throws SQLException {
    checkParameterIndex(paramInt);
    int i = this.metaData.getDataType(paramInt);
    if (i != 12 && i != 13 && i != 20)
      throw TbError.newSQLException(-590703); 
    this.paramLength[paramInt - 1] = -1;
    this.binder[paramInt - 1] = this.staticEmptyLOBDPLBinder;
  }
  
  public void setFloat(int paramInt, float paramFloat) throws SQLException {
    checkParameterIndex(paramInt);
    if (this.paramFloat == null)
      this.paramFloat = new float[this.bindParamCnt]; 
    this.paramLength[paramInt - 1] = 0;
    this.paramFloat[paramInt - 1] = paramFloat;
    this.binder[paramInt - 1] = this.staticFloatDPLBinder;
  }
  
  public void setInt(int paramInt1, int paramInt2) throws SQLException {
    checkParameterIndex(paramInt1);
    if (this.paramInt == null)
      this.paramInt = new int[this.bindParamCnt]; 
    this.paramLength[paramInt1 - 1] = 0;
    this.paramInt[paramInt1 - 1] = paramInt2;
    this.binder[paramInt1 - 1] = this.staticIntDPLBinder;
  }
  
  public void setLong(int paramInt, long paramLong) throws SQLException {
    checkParameterIndex(paramInt);
    if (this.paramLong == null)
      this.paramLong = new long[this.bindParamCnt]; 
    this.paramLength[paramInt - 1] = 0;
    this.paramLong[paramInt - 1] = paramLong;
    this.binder[paramInt - 1] = this.staticLongDPLBinder;
  }
  
  public void setLongBinaryStream(int paramInt1, InputStream paramInputStream, int paramInt2) throws SQLException {
    if (paramInputStream == null || paramInt2 <= 0) {
      setNull(paramInt1);
      return;
    } 
    checkParameterIndex(paramInt1);
    if (this.paramStream == null)
      this.paramStream = new InputStream[this.bindParamCnt]; 
    this.paramLength[paramInt1 - 1] = paramInt2;
    this.paramStream[paramInt1 - 1] = paramInputStream;
    String str1 = this.metaData.getClientCharSet();
    String str2 = ((TbDatabaseMetaData)this.conn.getMetaData()).getServerCharSet();
    if (str1 == null || str1.equals(str2)) {
      this.binder[paramInt1 - 1] = this.staticStreamDPLBinder;
    } else {
      this.binder[paramInt1 - 1] = this.staticLongStreamDPLBinder;
    } 
  }
  
  public void setNull(int paramInt) throws SQLException {
    checkParameterIndex(paramInt);
    this.paramLength[paramInt - 1] = 0;
    this.binder[paramInt - 1] = this.staticNullDPLBinder;
  }
  
  public void setObject(int paramInt, Object paramObject) throws SQLException {
    setObject(paramInt, paramObject, DataType.getSqlType(paramObject), 0);
  }
  
  public void setObject(int paramInt1, Object paramObject, int paramInt2) throws SQLException {
    setObject(paramInt1, paramObject, paramInt2, 0);
  }
  
  public void setObject(int paramInt1, Object paramObject, int paramInt2, int paramInt3) throws SQLException {
    if (paramObject == null) {
      setNull(paramInt1);
      return;
    } 
    switch (paramInt2) {
      case -7:
        setBoolean(paramInt1, (new Boolean(paramObject.toString())).booleanValue());
        return;
      case -1:
      case 1:
      case 12:
        setString(paramInt1, paramObject.toString());
        return;
      case -4:
      case -3:
      case -2:
        setBytes(paramInt1, (byte[])paramObject);
        return;
      case -6:
      case 5:
        setShort(paramInt1, (new Short(paramObject.toString())).shortValue());
        return;
      case 4:
        setInt(paramInt1, (new Integer(paramObject.toString())).intValue());
        return;
      case -5:
        setLong(paramInt1, (new Long(paramObject.toString())).longValue());
        return;
      case 3:
        setBigDecimal(paramInt1, new BigDecimal(paramObject.toString()));
        return;
      case 6:
        setFloat(paramInt1, (new Float(paramObject.toString())).floatValue());
        return;
      case 7:
        setFloat(paramInt1, (new Float(paramObject.toString())).floatValue());
        return;
      case 8:
        setDouble(paramInt1, (new Double(paramObject.toString())).doubleValue());
        return;
      case 2:
        if (paramObject instanceof Short) {
          setShort(paramInt1, ((Short)paramObject).shortValue());
        } else if (paramObject instanceof Integer) {
          setInt(paramInt1, ((Integer)paramObject).intValue());
        } else if (paramObject instanceof Long) {
          setLong(paramInt1, ((Long)paramObject).longValue());
        } else if (paramObject instanceof Float) {
          setFloat(paramInt1, ((Float)paramObject).floatValue());
        } else if (paramObject instanceof Double) {
          setDouble(paramInt1, ((Double)paramObject).doubleValue());
        } else if (paramObject instanceof BigDecimal) {
          setBigDecimal(paramInt1, (BigDecimal)paramObject);
        } else if (paramObject instanceof Boolean) {
          setBoolean(paramInt1, ((Boolean)paramObject).booleanValue());
        } else {
          throw TbError.newSQLException(-590704, paramObject.toString() + "," + paramInt2);
        } 
        return;
      case 91:
        if (paramObject instanceof Date) {
          setDate(paramInt1, (Date)paramObject);
        } else if (paramObject instanceof TbDate) {
          setTbDate(paramInt1, (TbDate)paramObject);
        } else {
          throw TbError.newSQLException(-590704, paramObject.toString() + "," + paramInt2);
        } 
        return;
      case 92:
        setTime(paramInt1, (Time)paramObject);
        return;
      case 93:
        if (paramObject instanceof Timestamp) {
          setTimestamp(paramInt1, (Timestamp)paramObject);
        } else if (paramObject instanceof TbTimestamp) {
          setTbTimestamp(paramInt1, (TbTimestamp)paramObject);
        } else {
          throw TbError.newSQLException(-590704, paramObject.toString() + "," + paramInt2);
        } 
        return;
    } 
    throw TbError.newSQLException(-590704, paramObject.toString() + "," + paramInt2);
  }
  
  public void setReturnCode(int paramInt) {
    this.returnCode = paramInt;
  }
  
  public void addRowCnt(int paramInt) {
    this.rowCnt += paramInt;
  }
  
  public void setRowid(int paramInt, String paramString) throws SQLException {
    if (paramString == null || paramString.length() == 0) {
      setNull(paramInt);
      return;
    } 
    checkParameterIndex(paramInt);
    if (this.paramString == null)
      this.paramString = new String[this.bindParamCnt]; 
    this.paramLength[paramInt - 1] = paramString.length();
    this.paramString[paramInt - 1] = paramString;
    this.binder[paramInt - 1] = this.staticRowIdDPLBinder;
  }
  
  public void setShort(int paramInt, short paramShort) throws SQLException {
    checkParameterIndex(paramInt);
    if (this.paramInt == null)
      this.paramInt = new int[this.bindParamCnt]; 
    this.paramLength[paramInt - 1] = 0;
    this.paramInt[paramInt - 1] = paramShort;
    this.binder[paramInt - 1] = this.staticIntDPLBinder;
  }
  
  public void setString(int paramInt, String paramString) throws SQLException {
    if (paramString == null || paramString.length() == 0) {
      setNull(paramInt);
      return;
    } 
    checkParameterIndex(paramInt);
    if (this.paramString == null)
      this.paramString = new String[this.bindParamCnt]; 
    this.paramLength[paramInt - 1] = paramString.length();
    this.paramString[paramInt - 1] = paramString;
    this.binder[paramInt - 1] = this.staticStringDPLBinder;
  }
  
  public void setTime(int paramInt, Time paramTime) throws SQLException {
    if (paramTime == null) {
      setNull(paramInt);
      return;
    } 
    checkParameterIndex(paramInt);
    if (this.paramTime == null)
      this.paramTime = new Time[this.bindParamCnt]; 
    this.paramLength[paramInt - 1] = 0;
    this.paramTime[paramInt - 1] = paramTime;
    this.binder[paramInt - 1] = this.staticTimeDPLBinder;
  }
  
  public void setTime(int paramInt, Time paramTime, Calendar paramCalendar) throws SQLException {
    setTime(paramInt, paramTime);
  }
  
  public void setTimestamp(int paramInt, Timestamp paramTimestamp) throws SQLException {
    if (paramTimestamp == null) {
      setNull(paramInt);
      return;
    } 
    checkParameterIndex(paramInt);
    if (this.paramTimestamp == null)
      this.paramTimestamp = new Timestamp[this.bindParamCnt]; 
    this.paramLength[paramInt - 1] = 0;
    this.paramTimestamp[paramInt - 1] = paramTimestamp;
    this.binder[paramInt - 1] = this.staticTimestampDPLBinder;
  }
  
  public void setTbTimestamp(int paramInt, TbTimestamp paramTbTimestamp) throws SQLException {
    if (paramTbTimestamp == null) {
      setNull(paramInt);
      return;
    } 
    checkParameterIndex(paramInt);
    if (this.paramTbTimestamp == null)
      this.paramTbTimestamp = new TbTimestamp[this.bindParamCnt]; 
    this.paramLength[paramInt - 1] = 0;
    this.paramTbTimestamp[paramInt - 1] = paramTbTimestamp;
    this.binder[paramInt - 1] = this.staticTbTimestampDPLBinder;
  }
  
  public void setTimestamp(int paramInt, Timestamp paramTimestamp, Calendar paramCalendar) throws SQLException {
    setTimestampTZ(paramInt, paramTimestamp, paramCalendar.getTimeZone());
  }
  
  public void setTimestampTZ(int paramInt, Timestamp paramTimestamp, TimeZone paramTimeZone) throws SQLException {
    if (paramTimestamp == null) {
      setNull(paramInt);
      return;
    } 
    checkParameterIndex(paramInt);
    if (this.paramTbTimestampTZ == null)
      this.paramTbTimestampTZ = new TbTimestampTZ[this.bindParamCnt]; 
    this.paramLength[paramInt - 1] = 0;
    this.paramTbTimestampTZ[paramInt - 1] = new TbTimestampTZ(paramTimestamp, paramTimeZone);
    this.binder[paramInt - 1] = this.staticTbTimestampTZDPLBinder;
  }
  
  public void setTimestampLTZ(int paramInt, Timestamp paramTimestamp) throws SQLException {
    if (paramTimestamp == null) {
      setNull(paramInt);
      return;
    } 
    checkParameterIndex(paramInt);
    if (this.paramTimestamp == null)
      this.paramTimestamp = new Timestamp[this.bindParamCnt]; 
    this.paramLength[paramInt - 1] = 0;
    this.paramTimestamp[paramInt - 1] = paramTimestamp;
    this.binder[paramInt - 1] = this.staticTimestampLTZDPLBinder;
  }
  
  public void setTbIntervalYtm(int paramInt, TbIntervalYtm paramTbIntervalYtm) throws SQLException {
    if (paramTbIntervalYtm == null) {
      setNull(paramInt);
      return;
    } 
    checkParameterIndex(paramInt);
    if (this.paramTbIntervalYtm == null)
      this.paramTbIntervalYtm = new TbIntervalYtm[this.bindParamCnt]; 
    this.paramLength[paramInt - 1] = 0;
    this.paramTbIntervalYtm[paramInt - 1] = paramTbIntervalYtm;
    this.binder[paramInt - 1] = this.staticTbIntervalYtmBinder;
  }
  
  public void setTbIntervalDts(int paramInt, TbIntervalDts paramTbIntervalDts) throws SQLException {
    if (paramTbIntervalDts == null) {
      setNull(paramInt);
      return;
    } 
    checkParameterIndex(paramInt);
    if (this.paramTbIntervalDts == null)
      this.paramTbIntervalDts = new TbIntervalDts[this.bindParamCnt]; 
    this.paramLength[paramInt - 1] = 0;
    this.paramTbIntervalDts[paramInt - 1] = paramTbIntervalDts;
    this.binder[paramInt - 1] = this.staticTbIntervalDtsBinder;
  }
}


/* Location:              C:\Users\lenovo\Desktop\siard\jdbc\tibero\tibero6\linux\tibero6-jdbc-18.jar!\com\tmax\tibero\jdbc\dpl\TbDirPathStream.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */