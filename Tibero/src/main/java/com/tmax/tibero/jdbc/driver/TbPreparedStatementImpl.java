package com.tmax.tibero.jdbc.driver;

import com.tmax.tibero.jdbc.*;
import tibero.jdbc.TbArray;
import com.tmax.tibero.jdbc.TbLob;
import com.tmax.tibero.jdbc.data.BatchInfo;
import com.tmax.tibero.jdbc.data.BatchUpdateInfo;
import com.tmax.tibero.jdbc.data.BigLiteral;
import com.tmax.tibero.jdbc.data.BindData;
import com.tmax.tibero.jdbc.data.BindItem;
import com.tmax.tibero.jdbc.data.BytesStreamWrapper;
import com.tmax.tibero.jdbc.data.Column;
import com.tmax.tibero.jdbc.data.DataType;
import com.tmax.tibero.jdbc.data.ParamContainer;
import com.tmax.tibero.jdbc.data.ReaderWrapper;
import com.tmax.tibero.jdbc.data.RsetType;
import com.tmax.tibero.jdbc.data.ServerInfo;
import com.tmax.tibero.jdbc.data.TbDate;
import com.tmax.tibero.jdbc.data.TbRAW;
import com.tmax.tibero.jdbc.data.TbTimestamp;
import com.tmax.tibero.jdbc.data.TbTimestampTZ;
import com.tmax.tibero.jdbc.data.binder.Binder;
import com.tmax.tibero.jdbc.data.binder.StaticBinder;
import com.tmax.tibero.jdbc.err.TbError;
import com.tmax.tibero.jdbc.msg.TbBindParamDesc;
import com.tmax.tibero.jdbc.msg.TbColumnDesc;
import com.tmax.tibero.jdbc.util.TbSQLParser;
import com.tmax.tibero.jdbc.util.TbSQLTypeScanner;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URL;
import java.sql.Array;
import java.sql.BatchUpdateException;
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
import java.sql.SQLXML;
import java.sql.Struct;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Vector;

public class TbPreparedStatementImpl extends TbStatement implements PreparedStatement, ParamContainer {
  protected byte[] ppid;
  
  private static byte[] PPID_NULL = new byte[] { 0, 0, 0, 0, 0, 0, 0, 0 };
  
  protected int bindParamCnt = 0;
  
  private int hiddenColCnt = 0;
  
  private int outColCnt = 0;
  
  private TbColumnDesc[] colMeta;
  
  private TbBindParamDesc[] paramMeta;
  
  private ResultSetMetaData resultSetMetaData;
  
  private ParameterMetaData parameterMetaData;
  
  private String processedBigLiteralSql;
  
  private ArrayList<BigLiteral> bigLiterals;
  
  protected BindData bindData;
  
  private BatchUpdateInfo batchUpdateInfo;
  
  private int batchFlag = 256;
  
  protected int allocatedBatchRowCount = 1;
  
  protected int currentRowIndex = 0;
  
  private int[][] paramInt;
  
  private long[][] paramLong;
  
  private float[][] paramFloat;
  
  private double[][] paramDouble;
  
  private BigDecimal[][] paramBigDecimal;
  
  private String[][] paramString;
  
  private Timestamp[][] paramTimestamp;
  
  private TbTimestampTZ[][] paramTbTimestampTZ;
  
  private TbTimestamp[][] paramTbTimestamp;
  
  private TbDate[][] paramTbDate;
  
  private Calendar[][] paramCalendar;
  
  private byte[][][] paramBytes;
  
  private InputStream[][] paramStream;
  
  private Reader[][] paramReader;
  
  private byte[][] paramTypes;
  
  private Struct[][] paramStruct;
  
  private Array[][] paramArray;
  
  protected Binder[][] binder;
  
  protected Binder staticNullBinder;
  
  protected Binder staticStringBinder;
  
  protected Binder staticReaderBinder;
  
  protected Binder staticIntBinder;
  
  protected Binder staticLongBinder;
  
  protected Binder staticFloatBinder;
  
  protected Binder staticDoubleBinder;
  
  protected Binder staticBinaryFloatBinder;
  
  protected Binder staticBinaryDoubleBinder;
  
  protected Binder staticBigDecimalBinder;
  
  protected Binder staticDateBinder;
  
  protected Binder staticTimeBinder;
  
  protected Binder staticTimestampBinder;
  
  protected Binder staticTimestampTZBinder;
  
  protected Binder staticTbDateBinder;
  
  protected Binder staticTbTimestampBinder;
  
  protected Binder staticBytesBinder;
  
  protected Binder staticStreamBinder;
  
  protected Binder staticNStringBinder;
  
  protected Binder staticNReaderBinder;
  
  protected Binder staticStructInBinder;
  
  protected Binder staticStructOutBinder;
  
  protected Binder staticArrayInBinder;
  
  protected Binder staticArrayOutBinder;
  
  private int varcharMax;
  
  private int deferrableStrLen;
  
  private int deferrableNStrLen;
  
  protected Object autoGenKeyArr;
  
  private boolean supportBinaryDoubleFloatType;
  
  private void initializeBinder(ServerInfo paramServerInfo) {
    this.supportBinaryDoubleFloatType = isSupportBinaryDoubleFloatType(paramServerInfo);
    this.staticNullBinder = StaticBinder.getNullBinder();
    this.staticStringBinder = StaticBinder.getStringBinder();
    this.staticReaderBinder = StaticBinder.getReaderBinder();
    this.staticIntBinder = StaticBinder.getIntBinder();
    this.staticLongBinder = StaticBinder.getLongBinder();
    this.staticFloatBinder = StaticBinder.getFloatBinder();
    this.staticDoubleBinder = StaticBinder.getDoubleBinder();
    this.staticBinaryFloatBinder = StaticBinder.getBinaryFloatBinder(this.supportBinaryDoubleFloatType);
    this.staticBinaryDoubleBinder = StaticBinder.getBinaryDoubleBinder(this.supportBinaryDoubleFloatType);
    this.staticBigDecimalBinder = StaticBinder.getBigDecimalBinder();
    this.staticDateBinder = StaticBinder.getDateBinder();
    this.staticTimeBinder = StaticBinder.getTimeBinder();
    this.staticTimestampBinder = StaticBinder.getTimestampBinder();
    this.staticTimestampTZBinder = StaticBinder.getTbTimestampTZBinder();
    this.staticTbDateBinder = StaticBinder.getTbDateBinder();
    this.staticTbTimestampBinder = StaticBinder.getTbTimestampBinder();
    this.staticBytesBinder = StaticBinder.getBytesBinder();
    this.staticStreamBinder = StaticBinder.getStreamBinder();
    this.staticNStringBinder = StaticBinder.getNStringBinder();
    this.staticNReaderBinder = StaticBinder.getNReaderBinder();
    this.staticStructInBinder = StaticBinder.getStructInBinder();
    this.staticStructOutBinder = StaticBinder.getStructOutBinder();
    this.staticArrayInBinder = StaticBinder.getArrayInBinder();
    this.staticArrayOutBinder = StaticBinder.getArrayOutBinder();
  }
  
  public TbPreparedStatementImpl(TbConnection paramTbConnection, String paramString) throws SQLException {
    this(paramTbConnection, paramString, 1003, 1007, 64000, false);
    initializeBinder(paramTbConnection.getServerInfo());
    if (paramTbConnection.getExtFeatureInfo().supports(0)) {
      this.varcharMax = 65532;
    } else {
      this.varcharMax = 4000;
    } 
    this.deferrableStrLen = this.varcharMax / Math.max(2, this.typeConverter.getMaxBytesPerChar()) + 1;
    this.deferrableNStrLen = this.varcharMax / Math.max(2, this.typeConverter.getMaxBytesPerNChar()) + 1;
  }
  
  public TbPreparedStatementImpl(TbConnection paramTbConnection, String paramString, int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean) throws SQLException {
    super(paramTbConnection, paramInt1, paramInt2, paramInt3);
    this.poolable = paramTbConnection.info.isStmtCache();
    this.sqlTypeScanner = new TbSQLTypeScanner();
    initSql(paramString);
    if (paramBoolean) {
      forcePrepare();
    } else {
      initParameter();
    } 
    initializeBinder(paramTbConnection.getServerInfo());
    if (paramTbConnection.getExtFeatureInfo().supports(0)) {
      this.varcharMax = 65532;
    } else {
      this.varcharMax = 4000;
    } 
    this.deferrableStrLen = this.varcharMax / Math.max(2, this.typeConverter.getMaxBytesPerChar()) + 1;
    this.deferrableNStrLen = this.varcharMax / Math.max(2, this.typeConverter.getMaxBytesPerNChar()) + 1;
  }
  
  public void addBatch() throws SQLException {
    int i = this.bindData.getDFRParameterCnt();
    for (byte b = 0; b < this.bindParamCnt; b++) {
      if (this.binder[this.currentRowIndex][b] == null)
        setCachedBindParameter(b); 
      BindItem bindItem = this.bindData.getBindItem(b);
      if (bindItem.isOUTParameter())
        throw TbError.newSQLException(-90631); 
      if (i <= 0 && this.paramTypes[0][b] != this.paramTypes[this.currentRowIndex][b])
        this.batchFlag = 0; 
    } 
    if (this.batchUpdateInfo == null)
      this.batchUpdateInfo = new BatchUpdateInfo(); 
    BindData bindData = new BindData();
    this.bindData.clone(bindData);
    this.bindData.clearDFRParameter();
    this.batchUpdateInfo.add(new BatchInfo(bindData, this.currentRowIndex));
    if (this.currentRowIndex == this.allocatedBatchRowCount - 1)
      growBatchArray(this.allocatedBatchRowCount, 0); 
    this.currentRowIndex++;
  }
  
  private void setCachedBindParameter(int paramInt) throws SQLException {
    if (this.currentRowIndex > 0) {
      byte b;
      BindItem bindItem = this.bindData.getBindItem(paramInt);
      int i = bindItem.getSQLType();
      switch (i) {
        case 4:
          this.paramInt[this.currentRowIndex][paramInt] = this.paramInt[this.currentRowIndex - 1][paramInt];
          break;
        case -5:
          this.paramBigDecimal[this.currentRowIndex][paramInt] = this.paramBigDecimal[this.currentRowIndex - 1][paramInt];
          break;
        case 6:
          this.paramFloat[this.currentRowIndex][paramInt] = this.paramFloat[this.currentRowIndex - 1][paramInt];
          break;
        case 8:
          this.paramDouble[this.currentRowIndex][paramInt] = this.paramDouble[this.currentRowIndex - 1][paramInt];
          break;
        case 2:
          this.paramLong[this.currentRowIndex][paramInt] = this.paramLong[this.currentRowIndex - 1][paramInt];
          break;
        case 12:
          this.paramString[this.currentRowIndex][paramInt] = this.paramString[this.currentRowIndex - 1][paramInt];
          break;
        case -1:
          this.paramReader[this.currentRowIndex][paramInt] = this.paramReader[this.currentRowIndex - 1][paramInt];
          break;
        case 91:
        case 92:
          b = this.paramTypes[this.currentRowIndex - 1][paramInt];
          switch (b) {
            case 5:
              if (this.paramCalendar != null && this.paramCalendar[this.currentRowIndex - 1][paramInt] != null) {
                this.paramCalendar[this.currentRowIndex][paramInt] = this.paramCalendar[this.currentRowIndex - 1][paramInt];
                break;
              } 
              if (this.paramTbDate != null && this.paramTbDate[this.currentRowIndex - 1][paramInt] != null) {
                this.paramTbDate[this.currentRowIndex][paramInt] = this.paramTbDate[this.currentRowIndex - 1][paramInt];
                break;
              } 
              throw TbError.newSQLException(-590704, Integer.toString(i));
            case 7:
              if (this.paramTimestamp != null && this.paramTimestamp[this.currentRowIndex - 1][paramInt] != null) {
                this.paramTimestamp[this.currentRowIndex][paramInt] = this.paramTimestamp[this.currentRowIndex - 1][paramInt];
                break;
              } 
              if (this.paramTbTimestamp != null && this.paramTbTimestamp[this.currentRowIndex - 1][paramInt] != null) {
                this.paramTbTimestamp[this.currentRowIndex][paramInt] = this.paramTbTimestamp[this.currentRowIndex - 1][paramInt];
                break;
              } 
              throw TbError.newSQLException(-590704, Integer.toString(i));
          } 
          throw TbError.newSQLException(-590704, Integer.toString(i));
        case 93:
          b = this.paramTypes[this.currentRowIndex - 1][paramInt];
          switch (b) {
            case 5:
              if (this.paramCalendar != null && this.paramCalendar[this.currentRowIndex - 1][paramInt] != null) {
                this.paramCalendar[this.currentRowIndex][paramInt] = this.paramCalendar[this.currentRowIndex - 1][paramInt];
                break;
              } 
              if (this.paramTbDate != null && this.paramTbDate[this.currentRowIndex - 1][paramInt] != null) {
                this.paramTbDate[this.currentRowIndex][paramInt] = this.paramTbDate[this.currentRowIndex - 1][paramInt];
                break;
              } 
              if (this.paramBytes != null && this.paramBytes[this.currentRowIndex - 1][paramInt] != null) {
                this.paramBytes[this.currentRowIndex][paramInt] = this.paramBytes[this.currentRowIndex - 1][paramInt];
                break;
              } 
              throw TbError.newSQLException(-590704, Integer.toString(i));
            case 7:
              if (this.paramTimestamp != null && this.paramTimestamp[this.currentRowIndex - 1][paramInt] != null) {
                this.paramTimestamp[this.currentRowIndex][paramInt] = this.paramTimestamp[this.currentRowIndex - 1][paramInt];
                break;
              } 
              if (this.paramTbTimestamp != null && this.paramTbTimestamp[this.currentRowIndex - 1][paramInt] != null) {
                this.paramTbTimestamp[this.currentRowIndex][paramInt] = this.paramTbTimestamp[this.currentRowIndex - 1][paramInt];
                break;
              } 
              throw TbError.newSQLException(-590704, Integer.toString(i));
            case 21:
              if (this.paramTbTimestampTZ != null && this.paramTbTimestampTZ[this.currentRowIndex - 1][paramInt] != null) {
                this.paramTbTimestampTZ[this.currentRowIndex][paramInt] = this.paramTbTimestampTZ[this.currentRowIndex - 1][paramInt];
                break;
              } 
              throw TbError.newSQLException(-590704, Integer.toString(i));
          } 
          throw TbError.newSQLException(-590704, Integer.toString(i));
        case -2:
          this.paramBytes[this.currentRowIndex][paramInt] = this.paramBytes[this.currentRowIndex - 1][paramInt];
          break;
        case -4:
          this.paramStream[this.currentRowIndex][paramInt] = this.paramStream[this.currentRowIndex - 1][paramInt];
          break;
        case 0:
          break;
        default:
          throw TbError.newSQLException(-590704, Integer.toString(i));
      } 
      this.binder[this.currentRowIndex][paramInt] = this.binder[this.currentRowIndex - 1][paramInt];
      this.paramTypes[this.currentRowIndex][paramInt] = this.paramTypes[this.currentRowIndex - 1][paramInt];
    } else {
      throw TbError.newSQLException(-90627);
    } 
  }
  
  private void addBigLiteral(ArrayList<BigLiteral> paramArrayList) throws SQLException {
    int i = paramArrayList.size();
    if (this.bindParamCnt == 0) {
      this.binder = new Binder[this.allocatedBatchRowCount][i];
      this.paramTypes = new byte[this.allocatedBatchRowCount][i];
      this.bindData = new BindData(i);
      for (byte b1 = 0; b1 < i; b1++) {
        BigLiteral bigLiteral = paramArrayList.get(b1);
        String str = bigLiteral.getLiteralValue();
        int j = bigLiteral.getLiteralIndex();
        if (this.paramReader == null)
          this.paramReader = new Reader[this.allocatedBatchRowCount][i]; 
        this.bindData.setDFRParam(j, -1, str.length());
        this.paramReader[this.currentRowIndex][j] = new StringReader(str);
        this.paramTypes[this.currentRowIndex][j] = 13;
        this.binder[this.currentRowIndex][j] = this.staticReaderBinder;
      } 
      return;
    } 
    growBatchArray(0, i);
    this.bindData.resize(this.bindParamCnt + i);
    for (byte b = 0; b < i; b++) {
      BigLiteral bigLiteral = paramArrayList.get(b);
      int j = bigLiteral.getLiteralIndex();
      String str = bigLiteral.getLiteralValue();
      if (this.paramReader == null)
        this.paramReader = new Reader[this.allocatedBatchRowCount][this.bindParamCnt + i]; 
      if (j < this.bindParamCnt)
        pushParamData(i, j); 
      this.bindData.insertDFRLiteral(j, -1, str.length());
      this.paramReader[this.currentRowIndex][j] = new StringReader(str);
      this.paramTypes[this.currentRowIndex][j] = 13;
      this.binder[this.currentRowIndex][j] = this.staticReaderBinder;
    } 
  }
  
  public void buildColMetaArray(int paramInt1, int paramInt2, TbColumnDesc[] paramArrayOfTbColumnDesc) {
    this.outColCnt = paramInt1;
    this.hiddenColCnt = paramInt2;
    this.colMeta = paramArrayOfTbColumnDesc;
  }
  
  public void buildParamMetaArray(TbBindParamDesc[] paramArrayOfTbBindParamDesc) {
    this.paramMeta = paramArrayOfTbBindParamDesc;
  }
  
  protected void checkParameterIndex(int paramInt) throws SQLException {
    if (paramInt < 0 || paramInt > this.bindParamCnt)
      throw TbError.newSQLException(-90609); 
  }
  
  public synchronized void clearBatch() throws SQLException {
    if (this.batchUpdateInfo != null)
      this.batchUpdateInfo.clear(); 
    for (int i = this.currentRowIndex; i >= 0; i--) {
      for (byte b = 0; b < this.bindParamCnt; b++)
        this.binder[i][b] = null; 
    } 
    this.currentRowIndex = 0;
  }
  
  private void clearBigLiteral(ArrayList<BigLiteral> paramArrayList) throws SQLException {
    int i = paramArrayList.size();
    int[] arrayOfInt = new int[i];
    for (int j = i - 1; j >= 0; j--) {
      BigLiteral bigLiteral = paramArrayList.get(j);
      arrayOfInt[j] = bigLiteral.getLiteralIndex();
      BindItem bindItem = this.bindData.getBindItem(j);
      int k = bindItem.getSQLType();
      int m;
      for (m = arrayOfInt[j]; m < this.bindParamCnt + i - 1; m++) {
        this.binder[this.currentRowIndex][m] = this.binder[this.currentRowIndex][m + 1];
        this.binder[this.currentRowIndex][m + 1] = null;
      } 
      for (m = arrayOfInt[j]; m < this.bindParamCnt + i - 1; m++)
        this.paramTypes[this.currentRowIndex][m] = this.paramTypes[this.currentRowIndex][m + 1]; 
      switch (k) {
        case 4:
          for (m = arrayOfInt[j]; m < this.bindParamCnt + i - 1; m++)
            this.paramInt[this.currentRowIndex][m] = this.paramInt[this.currentRowIndex][m + 1]; 
          break;
        case -5:
          for (m = arrayOfInt[j]; m < this.bindParamCnt + i - 1; m++)
            this.paramBigDecimal[this.currentRowIndex][m] = this.paramBigDecimal[this.currentRowIndex][m + 1]; 
          break;
        case 6:
          for (m = arrayOfInt[j]; m < this.bindParamCnt + i - 1; m++)
            this.paramFloat[this.currentRowIndex][m] = this.paramFloat[this.currentRowIndex][m + 1]; 
          break;
        case 8:
          for (m = arrayOfInt[j]; m < this.bindParamCnt + i - 1; m++)
            this.paramDouble[this.currentRowIndex][m] = this.paramDouble[this.currentRowIndex][m + 1]; 
          break;
        case 2:
          for (m = arrayOfInt[j]; m < this.bindParamCnt + i - 1; m++)
            this.paramLong[this.currentRowIndex][m] = this.paramLong[this.currentRowIndex][m + 1]; 
          break;
        case 12:
          for (m = arrayOfInt[j]; m < this.bindParamCnt + i - 1; m++)
            this.paramString[this.currentRowIndex][m] = this.paramString[this.currentRowIndex][m + 1]; 
          break;
        case -1:
          for (m = arrayOfInt[j]; m < this.bindParamCnt + i - 1; m++)
            this.paramReader[this.currentRowIndex][m] = this.paramReader[this.currentRowIndex][m + 1]; 
          break;
        case 91:
          for (m = arrayOfInt[j]; m < this.bindParamCnt + i - 1; m++) {
            if (this.paramTbDate != null)
              this.paramTbDate[this.currentRowIndex][m] = this.paramTbDate[this.currentRowIndex][m + 1]; 
          } 
        case 92:
          for (m = arrayOfInt[j]; m < this.bindParamCnt + i - 1; m++) {
            if (this.paramCalendar != null)
              this.paramCalendar[this.currentRowIndex][m] = this.paramCalendar[this.currentRowIndex][m + 1]; 
          } 
          break;
        case 93:
          for (m = arrayOfInt[j]; m < this.bindParamCnt + i - 1; m++) {
            if (this.paramTimestamp != null)
              this.paramTimestamp[this.currentRowIndex][m] = this.paramTimestamp[this.currentRowIndex][m + 1]; 
            if (this.paramTbTimestamp != null)
              this.paramTbTimestamp[this.currentRowIndex][m] = this.paramTbTimestamp[this.currentRowIndex][m + 1]; 
            if (this.paramTbTimestampTZ != null)
              this.paramTbTimestampTZ[this.currentRowIndex][m] = this.paramTbTimestampTZ[this.currentRowIndex][m + 1]; 
          } 
          break;
        case -2:
          for (m = arrayOfInt[j]; m < this.bindParamCnt + i - 1; m++)
            this.paramBytes[this.currentRowIndex][m] = this.paramBytes[this.currentRowIndex][m + 1]; 
          break;
        case -4:
          for (m = arrayOfInt[j]; m < this.bindParamCnt + i - 1; m++)
            this.paramStream[this.currentRowIndex][m] = this.paramStream[this.currentRowIndex][m + 1]; 
          break;
        case 0:
          break;
        default:
          throw TbError.newSQLException(-590704, Integer.toString(k));
      } 
    } 
    this.bindData.removeDFRLiteral(arrayOfInt);
  }
  
  public void clearParameters() throws SQLException {
    if (this.bindData != null)
      this.bindData.reuse(); 
    for (byte b = 0; b < this.bindParamCnt; b++)
      this.binder[this.currentRowIndex][b] = null; 
  }
  
  protected void copyBindParamInfo(ParamContainer paramParamContainer) throws SQLException {
    int i = paramParamContainer.getParameterCnt();
    getBindData().set(paramParamContainer.getBindData());
    Binder[][] arrayOfBinder = paramParamContainer.getBinder();
    this.binder = new Binder[this.allocatedBatchRowCount][this.bindParamCnt];
    if (arrayOfBinder != null)
      for (byte b = 0; b < this.allocatedBatchRowCount; b++)
        System.arraycopy(arrayOfBinder[0], 0, this.binder[b], 0, i);  
    byte[] arrayOfByte = paramParamContainer.getParamTypesOfRow(this.currentRowIndex);
    this.paramTypes = new byte[this.allocatedBatchRowCount][this.bindParamCnt];
    if (arrayOfByte != null)
      System.arraycopy(arrayOfByte, 0, this.paramTypes[this.currentRowIndex], 0, i); 
    int[] arrayOfInt = paramParamContainer.getParamIntOfRow(this.currentRowIndex);
    if (arrayOfInt != null) {
      this.paramInt = new int[this.allocatedBatchRowCount][this.bindParamCnt];
      System.arraycopy(arrayOfInt, 0, this.paramInt[this.currentRowIndex], 0, i);
    } 
    long[] arrayOfLong = paramParamContainer.getParamLongOfRow(this.currentRowIndex);
    if (arrayOfLong != null) {
      this.paramLong = new long[this.allocatedBatchRowCount][this.bindParamCnt];
      System.arraycopy(arrayOfLong, 0, this.paramLong[this.currentRowIndex], 0, i);
    } 
    float[] arrayOfFloat = paramParamContainer.getParamFloatOfRow(this.currentRowIndex);
    if (arrayOfFloat != null) {
      this.paramFloat = new float[this.allocatedBatchRowCount][this.bindParamCnt];
      System.arraycopy(arrayOfFloat, 0, this.paramFloat[this.currentRowIndex], 0, i);
    } 
    double[] arrayOfDouble = paramParamContainer.getParamDoubleOfRow(this.currentRowIndex);
    if (arrayOfDouble != null) {
      this.paramDouble = new double[this.allocatedBatchRowCount][this.bindParamCnt];
      System.arraycopy(arrayOfLong, 0, this.paramDouble[this.currentRowIndex], 0, i);
    } 
    BigDecimal[] arrayOfBigDecimal = paramParamContainer.getParamBigDecimalOfRow(this.currentRowIndex);
    if (arrayOfBigDecimal != null) {
      this.paramBigDecimal = new BigDecimal[this.allocatedBatchRowCount][this.bindParamCnt];
      System.arraycopy(arrayOfBigDecimal, 0, this.paramBigDecimal[this.currentRowIndex], 0, i);
    } 
    String[] arrayOfString = paramParamContainer.getParamStringOfRow(this.currentRowIndex);
    if (arrayOfString != null) {
      this.paramString = new String[this.allocatedBatchRowCount][this.bindParamCnt];
      System.arraycopy(arrayOfString, 0, this.paramString[this.currentRowIndex], 0, i);
    } 
    Calendar[] arrayOfCalendar = paramParamContainer.getParamCalendarOfRow(this.currentRowIndex);
    if (arrayOfCalendar != null) {
      this.paramCalendar = new Calendar[this.allocatedBatchRowCount][this.bindParamCnt];
      System.arraycopy(arrayOfCalendar, 0, this.paramCalendar[this.currentRowIndex], 0, i);
    } 
    Timestamp[] arrayOfTimestamp = paramParamContainer.getParamTimestampOfRow(this.currentRowIndex);
    if (arrayOfTimestamp != null) {
      this.paramTimestamp = new Timestamp[this.allocatedBatchRowCount][this.bindParamCnt];
      System.arraycopy(arrayOfTimestamp, 0, this.paramTimestamp[this.currentRowIndex], 0, i);
    } 
    TbDate[] arrayOfTbDate = paramParamContainer.getParamTbDateOfRow(this.currentRowIndex);
    if (arrayOfTbDate != null) {
      this.paramTbDate = new TbDate[this.allocatedBatchRowCount][this.bindParamCnt];
      System.arraycopy(arrayOfTbDate, 0, this.paramTbDate[this.currentRowIndex], 0, i);
    } 
    TbTimestamp[] arrayOfTbTimestamp = paramParamContainer.getParamTbTimestampOfRow(this.currentRowIndex);
    if (arrayOfTbTimestamp != null) {
      this.paramTbTimestamp = new TbTimestamp[this.allocatedBatchRowCount][this.bindParamCnt];
      System.arraycopy(arrayOfTbTimestamp, 0, this.paramTbTimestamp[this.currentRowIndex], 0, i);
    } 
    TbTimestampTZ[] arrayOfTbTimestampTZ = paramParamContainer.getParamTbTimestampTZOfRow(this.currentRowIndex);
    if (arrayOfTbTimestampTZ != null) {
      this.paramTbTimestampTZ = new TbTimestampTZ[this.allocatedBatchRowCount][this.bindParamCnt];
      System.arraycopy(arrayOfTbTimestampTZ, 0, this.paramTbTimestampTZ[this.currentRowIndex], 0, i);
    } 
    byte[][] arrayOfByte1 = paramParamContainer.getParamBytesOfRow(this.currentRowIndex);
    if (arrayOfByte1 != null) {
      this.paramBytes = new byte[this.allocatedBatchRowCount][this.bindParamCnt][];
      System.arraycopy(arrayOfByte1, 0, this.paramBytes[this.currentRowIndex], 0, i);
    } 
    InputStream[] arrayOfInputStream = paramParamContainer.getParamStreamOfRow(this.currentRowIndex);
    if (arrayOfInputStream != null) {
      this.paramStream = new InputStream[this.allocatedBatchRowCount][this.bindParamCnt];
      System.arraycopy(arrayOfInputStream, 0, this.paramStream[this.currentRowIndex], 0, i);
    } 
    Reader[] arrayOfReader = paramParamContainer.getParamReaderOfRow(this.currentRowIndex);
    if (arrayOfReader != null) {
      this.paramReader = new Reader[this.allocatedBatchRowCount][this.bindParamCnt];
      System.arraycopy(arrayOfReader, 0, this.paramReader[this.currentRowIndex], 0, i);
    } 
  }
  
  public boolean execute() throws SQLException {
    checkConnectionOpen();
    if (this.batchStmts != null) {
      checkBatchStmtRemained();
      initBatchStmts();
    } 
    initBeforeExecute();
    if (this.queryTimeout != 0)
      this.conn.getTimeout().setTimeout((this.queryTimeout * 1000), this); 
    try {
      this.isExecuting = true;
      executeInternal(this.originalSql);
    } finally {
      this.isExecuting = false;
      if (this.queryTimeout != 0)
        this.conn.getTimeout().cancelTimeout(); 
    } 
    return (this.currentRs != null);
  }
  
  public boolean execute(String paramString) throws SQLException {
    checkConnectionOpen();
    initSQLInfo(paramString);
    if (this.batchStmts != null) {
      checkBatchStmtRemained();
      initBatchStmts();
    } 
    initBeforeExecute();
    this.returnAutoGeneratedKeys = false;
    if (this.queryTimeout != 0)
      this.conn.getTimeout().setTimeout((this.queryTimeout * 1000), this); 
    try {
      this.isExecuting = true;
      executeInternal(this.originalSql);
    } finally {
      this.isExecuting = false;
      if (this.queryTimeout != 0)
        this.conn.getTimeout().cancelTimeout(); 
    } 
    return (this.currentRs != null);
  }
  
  public boolean execute(String paramString, int paramInt) throws SQLException {
    if (paramInt == 2)
      return execute(paramString); 
    if (paramInt != 1)
      TbError.newSQLException(-590733); 
    checkConnectionOpen();
    initSQLInfo(paramString);
    if (this.batchStmts != null) {
      checkBatchStmtRemained();
      initBatchStmts();
    } 
    initBeforeExecute();
    this.returnAutoGeneratedKeys = true;
    setAutoGenKeyArr((Object)null);
    if (this.queryTimeout != 0)
      this.conn.getTimeout().setTimeout((this.queryTimeout * 1000), this); 
    try {
      this.isExecuting = true;
      executeInternal(this.originalSql);
    } finally {
      this.isExecuting = false;
      if (this.queryTimeout != 0)
        this.conn.getTimeout().cancelTimeout(); 
    } 
    return (this.currentRs != null);
  }
  
  public boolean execute(String paramString, int[] paramArrayOfint) throws SQLException {
    if (paramArrayOfint == null || paramArrayOfint.length == 0)
      TbError.newSQLException(-590732); 
    checkConnectionOpen();
    initSQLInfo(paramString);
    if (this.batchStmts != null) {
      checkBatchStmtRemained();
      initBatchStmts();
    } 
    initBeforeExecute();
    this.returnAutoGeneratedKeys = true;
    setAutoGenKeyArr(paramArrayOfint);
    if (this.queryTimeout != 0)
      this.conn.getTimeout().setTimeout((this.queryTimeout * 1000), this); 
    try {
      this.isExecuting = true;
      executeInternal(this.originalSql);
    } finally {
      this.isExecuting = false;
      if (this.queryTimeout != 0)
        this.conn.getTimeout().cancelTimeout(); 
    } 
    return (this.currentRs != null);
  }
  
  public boolean execute(String paramString, String[] paramArrayOfString) throws SQLException {
    if (paramArrayOfString == null || paramArrayOfString.length == 0)
      TbError.newSQLException(-590734); 
    checkConnectionOpen();
    initSQLInfo(paramString);
    if (this.batchStmts != null) {
      checkBatchStmtRemained();
      initBatchStmts();
    } 
    initBeforeExecute();
    this.returnAutoGeneratedKeys = true;
    setAutoGenKeyArr(paramArrayOfString);
    if (this.queryTimeout != 0)
      this.conn.getTimeout().setTimeout((this.queryTimeout * 1000), this); 
    try {
      this.isExecuting = true;
      executeInternal(this.originalSql);
    } finally {
      this.isExecuting = false;
      if (this.queryTimeout != 0)
        this.conn.getTimeout().cancelTimeout(); 
    } 
    return (this.currentRs != null);
  }
  
  public synchronized int[] executeBatch() throws SQLException, BatchUpdateException {
    int i = this.conn.typeConverter.getMaxBytesPerChar();
    checkConnectionOpen();
    initBeforeExecute();
    if (this.currentRowIndex == 0) {
      this.batchCounts = new int[0];
      return this.batchCounts;
    } 
    if (this.originalSql.length() > 65535 / i) {
      ArrayList arrayList = new ArrayList();
      TbSQLParser.getBigLiteral(this.originalSql, i, arrayList);
      if (arrayList.size() > 0)
        throw TbError.newSQLException(-90652); 
    } 
    try {
      if (this.queryTimeout != 0)
        this.conn.getTimeout().setTimeout((this.queryTimeout * 1000), this); 
      this.isExecuting = true;
      synchronized (this.conn) {
        this.batchCounts = this.conn.getTbComm().batchUpdateLoop(this, this.batchUpdateInfo);
      } 
      return this.batchCounts;
    } finally {
      this.isExecuting = false;
      if (this.queryTimeout != 0)
        this.conn.getTimeout().cancelTimeout(); 
      clearBatch();
    } 
  }
  
  private int executeBigLiteral(String paramString, ArrayList<BigLiteral> paramArrayList) throws SQLException {
    addBigLiteral(paramArrayList);
    try {
      return executeCompleteSQL(paramString);
    } catch (SQLException sQLException) {
      this.ppid = null;
      throw sQLException;
    } finally {
      clearBigLiteral(paramArrayList);
    } 
  }
  
  private void prepareBigLiteralInternal(String paramString, ArrayList<BigLiteral> paramArrayList) throws SQLException {
    addBigLiteral(paramArrayList);
    try {
      prepareSQLInternal(paramString);
    } catch (SQLException sQLException) {
      this.ppid = null;
      throw sQLException;
    } finally {
      clearBigLiteral(paramArrayList);
    } 
  }
  
  private int executeCompleteSQL(String paramString) throws SQLException {
    if (!isInsertStmt(paramString))
      this.returnAutoGeneratedKeys = false; 
    if (TbSQLTypeScanner.isQueryStmt(this.sqlType)) {
      if (this.userRsetType.getType() == 1005 || this.userRsetType.getConcurrency() == 1008) {
        if (this.rsetTypeDowngraded) {
          this.realRsetType = RsetType.getDownGradedRsetType(this.userRsetType.getRank());
          synchronized (this.conn) {
            int i = this.conn.getTbComm().prepareExecute(this, paramString, this.currentRowIndex);
            this.rsetTypeDowngraded = true;
            return i;
          } 
        } 
        this.sqlWithRowId = getQueryWithRowId(paramString);
        try {
          this.realRsetType = this.userRsetType;
          synchronized (this.conn) {
            return this.conn.getTbComm().prepareExecute(this, this.sqlWithRowId, this.currentRowIndex);
          } 
        } catch (SQLException sQLException) {
          this.realRsetType = RsetType.getDownGradedRsetType(this.userRsetType.getRank());
          synchronized (this.conn) {
            int i = this.conn.getTbComm().prepareExecute(this, paramString, this.currentRowIndex);
            this.rsetTypeDowngraded = true;
            return i;
          } 
        } 
      } 
      try {
        this.realRsetType = this.userRsetType;
        synchronized (this.conn) {
          return this.conn.getTbComm().prepareExecute(this, paramString, this.currentRowIndex);
        } 
      } catch (SQLException sQLException) {
        this.ppid = null;
        if (this.conn.isClosed() || !this.conn.info.isFailoverCursorEnabled() || sQLException.getErrorCode() != -90700)
          throw sQLException; 
        int i = this.conn.getTbComm().prepareExecute(this, paramString, this.currentRowIndex);
        addWarning(TbError.newSQLWarning(-90700, sQLException));
        return i;
      } 
    } 
    if (TbSQLTypeScanner.isDMLStmt(this.sqlType) || (!TbSQLTypeScanner.isQueryStmt(this.sqlType) && !TbSQLTypeScanner.isPSMStmt(this.sqlType))) {
      this.realRsetType = this.userRsetType;
      String str = paramString;
      if (this.returnAutoGeneratedKeys) {
        if (this.realRsetType.isSensitive() || this.realRsetType.isUpdatable())
          this.realRsetType = RsetType.SIRD; 
        if (this.autoGenKeyArr == null) {
          str = getAutoGenSql(this.originalSql);
        } else if (this.autoGenKeyArr instanceof int[]) {
          str = getAutoGenSql(this.originalSql, (int[])this.autoGenKeyArr);
        } else if (this.autoGenKeyArr instanceof String[]) {
          str = getAutoGenSql(this.originalSql, (String[])this.autoGenKeyArr);
        } 
      } 
      synchronized (this.conn) {
        this.rowsUpdated = this.conn.getTbComm().prepareExecute(this, str, this.currentRowIndex);
      } 
      return (int)this.rowsUpdated;
    } 
    if ((TbSQLTypeScanner.isPSMStmt(this.sqlType) && this.userRsetType.isScrollable()) || this.userRsetType.isUpdatable()) {
      this.realRsetType = RsetType.getDownGradedRsetType(this.userRsetType.getRank());
    } else {
      this.realRsetType = this.userRsetType;
    } 
    synchronized (this.conn) {
      return this.conn.getTbComm().prepareExecute(this, paramString, this.currentRowIndex);
    } 
  }
  
  private void prepareSQLInternal(String paramString) throws SQLException {
    if (!isInsertStmt(paramString))
      this.returnAutoGeneratedKeys = false; 
    if (TbSQLTypeScanner.isQueryStmt(this.sqlType)) {
      this.realRsetType = this.userRsetType;
      Vector<Integer> vector = new Vector();
      synchronized (this.conn) {
        this.conn.getTbComm().prepare(this, paramString, vector);
      } 
      this.allocatedBatchRowCount = 1;
      this.currentRowIndex = 0;
      this.bindData = new BindData(this.bindParamCnt);
      this.binder = new Binder[this.allocatedBatchRowCount][this.bindParamCnt];
      this.paramTypes = new byte[this.allocatedBatchRowCount][this.bindParamCnt];
      for (byte b = 0; b < this.bindParamCnt; b++) {
        int i = ((Integer)vector.get(b)).intValue();
        DataType.checkValidDataType(i);
        this.paramTypes[this.currentRowIndex][b] = (byte)i;
      } 
      vector.clear();
      vector = null;
    } 
  }
  
  protected synchronized int executeInternal(String paramString) throws SQLException {
    int i = this.conn.typeConverter.getMaxBytesPerChar();
    if (paramString.length() > 65535 / i && this.sqlType != 128) {
      if (this.processedBigLiteralSql == null) {
        if (this.bigLiterals == null)
          this.bigLiterals = new ArrayList<BigLiteral>(); 
        this.processedBigLiteralSql = TbSQLParser.getBigLiteral(paramString, i, this.bigLiterals);
      } 
      if (this.bigLiterals.size() > 0)
        return executeBigLiteral(this.processedBigLiteralSql, this.bigLiterals); 
    } 
    try {
      return executeCompleteSQL(paramString);
    } catch (SQLException sQLException) {
      this.ppid = null;
      throw sQLException;
    } 
  }
  
  protected synchronized void prepareInternal(String paramString) throws SQLException {
    int i = this.conn.typeConverter.getMaxBytesPerChar();
    if (paramString.length() > 65535 / i) {
      if (this.processedBigLiteralSql == null) {
        if (this.bigLiterals == null)
          this.bigLiterals = new ArrayList<BigLiteral>(); 
        this.processedBigLiteralSql = TbSQLParser.getBigLiteral(paramString, i, this.bigLiterals);
      } 
      if (this.bigLiterals.size() > 0)
        prepareBigLiteralInternal(this.processedBigLiteralSql, this.bigLiterals); 
    } 
    try {
      prepareSQLInternal(paramString);
    } catch (SQLException sQLException) {
      this.ppid = null;
      throw sQLException;
    } 
  }
  
  public ResultSet executeQuery() throws SQLException {
    checkConnectionOpen();
    if (this.batchStmts != null) {
      checkBatchStmtRemained();
      initBatchStmts();
    } 
    initBeforeExecute();
    if (this.queryTimeout > 0)
      this.conn.getTimeout().setTimeout((this.queryTimeout * 1000), this); 
    try {
      this.isExecuting = true;
      executeInternal(this.originalSql);
    } finally {
      this.isExecuting = false;
      if (this.queryTimeout > 0)
        this.conn.getTimeout().cancelTimeout(); 
    } 
    if (this.currentRs == null)
      this.currentRs = TbResultSetFactory.buildResultSet(this, -1, 0, 0, null); 
    return (ResultSet)this.currentRs;
  }
  
  public ResultSet executeQuery(String paramString) throws SQLException {
    checkConnectionOpen();
    initSQLInfo(paramString);
    if (this.batchStmts != null) {
      checkBatchStmtRemained();
      initBatchStmts();
    } 
    initBeforeExecute();
    if (this.queryTimeout > 0)
      this.conn.getTimeout().setTimeout((this.queryTimeout * 1000), this); 
    try {
      this.isExecuting = true;
      executeInternal(this.originalSql);
    } finally {
      this.isExecuting = false;
      if (this.queryTimeout > 0)
        this.conn.getTimeout().cancelTimeout(); 
    } 
    if (this.currentRs == null)
      this.currentRs = TbResultSetFactory.buildResultSet(this, -1, 0, 0, null); 
    return (ResultSet)this.currentRs;
  }
  
  public int executeUpdate() throws SQLException {
    checkConnectionOpen();
    if (this.batchStmts != null) {
      checkBatchStmtRemained();
      initBatchStmts();
    } 
    initBeforeExecute();
    if (this.queryTimeout != 0)
      this.conn.getTimeout().setTimeout((this.queryTimeout * 1000), this); 
    try {
      this.isExecuting = true;
      return executeInternal(this.originalSql);
    } finally {
      this.isExecuting = false;
      if (this.queryTimeout != 0)
        this.conn.getTimeout().cancelTimeout(); 
    } 
  }
  
  public int executeUpdate(String paramString) throws SQLException {
    checkConnectionOpen();
    initSQLInfo(paramString);
    if (this.batchStmts != null) {
      checkBatchStmtRemained();
      initBatchStmts();
    } 
    initBeforeExecute();
    this.returnAutoGeneratedKeys = false;
    if (this.queryTimeout != 0)
      this.conn.getTimeout().setTimeout((this.queryTimeout * 1000), this); 
    try {
      this.isExecuting = true;
      return executeInternal(this.originalSql);
    } finally {
      this.isExecuting = false;
      if (this.queryTimeout != 0)
        this.conn.getTimeout().cancelTimeout(); 
    } 
  }
  
  public int executeUpdate(String paramString, int paramInt) throws SQLException {
    if (paramInt == 2)
      return executeUpdate(paramString); 
    if (paramInt != 1)
      TbError.newSQLException(-590733); 
    checkConnectionOpen();
    initSQLInfo(paramString);
    if (this.batchStmts != null) {
      checkBatchStmtRemained();
      initBatchStmts();
    } 
    initBeforeExecute();
    this.returnAutoGeneratedKeys = true;
    setAutoGenKeyArr((Object)null);
    if (this.queryTimeout != 0)
      this.conn.getTimeout().setTimeout((this.queryTimeout * 1000), this); 
    try {
      this.isExecuting = true;
      return executeInternal(this.originalSql);
    } finally {
      this.isExecuting = false;
      if (this.queryTimeout != 0)
        this.conn.getTimeout().cancelTimeout(); 
    } 
  }
  
  public int executeUpdate(String paramString, int[] paramArrayOfint) throws SQLException {
    if (paramArrayOfint == null || paramArrayOfint.length == 0)
      TbError.newSQLException(-590732); 
    checkConnectionOpen();
    initSQLInfo(paramString);
    if (this.batchStmts != null) {
      checkBatchStmtRemained();
      initBatchStmts();
    } 
    initBeforeExecute();
    this.returnAutoGeneratedKeys = true;
    setAutoGenKeyArr(paramArrayOfint);
    if (this.queryTimeout != 0)
      this.conn.getTimeout().setTimeout((this.queryTimeout * 1000), this); 
    try {
      this.isExecuting = true;
      return executeInternal(this.originalSql);
    } finally {
      this.isExecuting = false;
      if (this.queryTimeout != 0)
        this.conn.getTimeout().cancelTimeout(); 
    } 
  }
  
  public int executeUpdate(String paramString, String[] paramArrayOfString) throws SQLException {
    if (paramArrayOfString == null || paramArrayOfString.length == 0)
      TbError.newSQLException(-590732); 
    checkConnectionOpen();
    initSQLInfo(paramString);
    if (this.batchStmts != null) {
      checkBatchStmtRemained();
      initBatchStmts();
    } 
    initBeforeExecute();
    this.returnAutoGeneratedKeys = true;
    setAutoGenKeyArr(paramArrayOfString);
    if (this.queryTimeout != 0)
      this.conn.getTimeout().setTimeout((this.queryTimeout * 1000), this); 
    try {
      this.isExecuting = true;
      return executeInternal(this.originalSql);
    } finally {
      this.isExecuting = false;
      if (this.queryTimeout != 0)
        this.conn.getTimeout().cancelTimeout(); 
    } 
  }
  
  private void forcePrepare() throws SQLException {
    Vector<Integer> vector = new Vector();
    synchronized (this.conn) {
      this.conn.getTbComm().prepare(this, this.originalSql, vector);
    } 
    this.allocatedBatchRowCount = 1;
    this.currentRowIndex = 0;
    this.bindData = new BindData(this.bindParamCnt);
    this.binder = new Binder[this.allocatedBatchRowCount][this.bindParamCnt];
    this.paramTypes = new byte[this.allocatedBatchRowCount][this.bindParamCnt];
    for (byte b = 0; b < this.bindParamCnt; b++) {
      int i = ((Integer)vector.get(b)).intValue();
      DataType.checkValidDataType(i);
      this.paramTypes[this.currentRowIndex][b] = (byte)i;
    } 
    vector.clear();
    vector = null;
  }
  
  public int getBatchFlag() {
    return this.batchFlag;
  }
  
  public int getBatchRowCount() {
    return this.currentRowIndex;
  }
  
  public BindData getBindData() {
    return this.bindData;
  }
  
  public Binder[][] getBinder() {
    return this.binder;
  }
  
  public Binder getBinder(int paramInt1, int paramInt2) {
    return this.binder[paramInt1][paramInt2];
  }
  
  public TbColumnDesc[] getColMetaArray() {
    return this.colMeta;
  }
  
  public int getHiddenColCnt() {
    return this.hiddenColCnt;
  }
  
  public ResultSetMetaData getMetaData() throws SQLException {
    ResultSet resultSet = getResultSet();
    if (resultSet != null)
      return resultSet.getMetaData(); 
    if (resultSet == null) {
      prepareInternal();
      int i = getColumnCount();
      Column[] arrayOfColumn = new Column[i];
      for (byte b = 0; b < i; b++)
        arrayOfColumn[b] = new Column(this.conn.getMapDateToTimestamp()); 
      this.typeConverter.buildColumnMetaData(this.colMeta, this.hiddenColCnt, i, arrayOfColumn);
      this.resultSetMetaData = (ResultSetMetaData)new TbResultSetMetaData(arrayOfColumn, 0);
    } 
    return this.resultSetMetaData;
  }
  
  private void prepareInternal() throws SQLException {
    checkConnectionOpen();
    if (this.batchStmts != null) {
      checkBatchStmtRemained();
      initBatchStmts();
    } 
    initBeforeExecute();
    if (this.queryTimeout > 0)
      this.conn.getTimeout().setTimeout((this.queryTimeout * 1000), this); 
    try {
      this.isExecuting = true;
      prepareInternal(this.originalSql);
    } finally {
      this.isExecuting = false;
      if (this.queryTimeout > 0)
        this.conn.getTimeout().cancelTimeout(); 
    } 
  }
  
  int getColumnCount() {
    int i = getHiddenColCnt();
    int j = 0;
    if (getRealRsetType() != null)
      j = i + ((getRealRsetType().useRowId() == true) ? 1 : 0); 
    return getOutColCnt() - j;
  }
  
  public int getOutColCnt() {
    return this.outColCnt;
  }
  
  public BigDecimal getParamBigDecimal(int paramInt1, int paramInt2) {
    return this.paramBigDecimal[paramInt1][paramInt2];
  }
  
  public BigDecimal[] getParamBigDecimalOfRow(int paramInt) {
    return (this.paramBigDecimal == null) ? null : this.paramBigDecimal[paramInt];
  }
  
  public byte[] getParamBytes(int paramInt1, int paramInt2) {
    return this.paramBytes[paramInt1][paramInt2];
  }
  
  public byte[][] getParamBytesOfRow(int paramInt) {
    return (this.paramBytes == null) ? (byte[][])null : this.paramBytes[paramInt];
  }
  
  public Calendar getParamCalendar(int paramInt1, int paramInt2) {
    return this.paramCalendar[paramInt1][paramInt2];
  }
  
  public Calendar[] getParamCalendarOfRow(int paramInt) {
    return (this.paramCalendar == null) ? null : this.paramCalendar[paramInt];
  }
  
  public double getParamDouble(int paramInt1, int paramInt2) {
    return this.paramDouble[paramInt1][paramInt2];
  }
  
  public double[] getParamDoubleOfRow(int paramInt) {
    return (this.paramDouble == null) ? null : this.paramDouble[paramInt];
  }
  
  public int getParameterCnt() {
    return this.bindParamCnt;
  }
  
  public ParameterMetaData getParameterMetaData() throws SQLException {
    this.paramMeta = null;
    if (!TbSQLTypeScanner.isAnonBlockStmt(this.sqlType) && !TbSQLTypeScanner.isDDLStmt(this.sqlType))
      prepareForParamMeta(); 
    this.parameterMetaData = new TbParameterMetaData(this.bindParamCnt, this.paramMeta, this.conn.getMapDateToTimestamp());
    return this.parameterMetaData;
  }
  
  private void prepareForParamMeta() throws SQLException {
    Vector vector = new Vector();
    int i = this.conn.typeConverter.getMaxBytesPerChar();
    if (this.originalSql.length() <= 65535 / i) {
      checkConnectionOpen();
      initBeforeExecute();
      try {
        synchronized (this.conn) {
          this.conn.getTbComm().prepare(this, this.originalSql, vector);
        } 
      } catch (SQLException sQLException) {
        this.ppid = null;
        throw sQLException;
      } 
    } 
    vector.clear();
    vector = null;
  }
  
  public float getParamFloat(int paramInt1, int paramInt2) {
    return this.paramFloat[paramInt1][paramInt2];
  }
  
  public float[] getParamFloatOfRow(int paramInt) {
    return (this.paramFloat == null) ? null : this.paramFloat[paramInt];
  }
  
  public int getParamInt(int paramInt1, int paramInt2) {
    return this.paramInt[paramInt1][paramInt2];
  }
  
  public int[] getParamIntOfRow(int paramInt) {
    return (this.paramInt == null) ? null : this.paramInt[paramInt];
  }
  
  public long getParamLong(int paramInt1, int paramInt2) {
    return this.paramLong[paramInt1][paramInt2];
  }
  
  public long[] getParamLongOfRow(int paramInt) {
    return (this.paramLong == null) ? null : this.paramLong[paramInt];
  }
  
  public Reader getParamReader(int paramInt1, int paramInt2) {
    return this.paramReader[paramInt1][paramInt2];
  }
  
  public Reader[] getParamReaderOfRow(int paramInt) {
    return (this.paramReader == null) ? null : this.paramReader[paramInt];
  }
  
  public InputStream getParamStream(int paramInt1, int paramInt2) {
    return this.paramStream[paramInt1][paramInt2];
  }
  
  public InputStream[] getParamStreamOfRow(int paramInt) {
    return (this.paramStream == null) ? null : this.paramStream[paramInt];
  }
  
  public String getParamString(int paramInt1, int paramInt2) {
    return this.paramString[paramInt1][paramInt2];
  }
  
  public String[] getParamStringOfRow(int paramInt) {
    return (this.paramString == null) ? null : this.paramString[paramInt];
  }
  
  public Timestamp getParamTimestamp(int paramInt1, int paramInt2) {
    return this.paramTimestamp[paramInt1][paramInt2];
  }
  
  public TbDate getParamTbDate(int paramInt1, int paramInt2) {
    return this.paramTbDate[paramInt1][paramInt2];
  }
  
  public TbDate[] getParamTbDateOfRow(int paramInt) {
    return (this.paramTbDate == null) ? null : this.paramTbDate[paramInt];
  }
  
  public TbTimestamp getParamTbTimestamp(int paramInt1, int paramInt2) {
    return this.paramTbTimestamp[paramInt1][paramInt2];
  }
  
  public TbTimestamp[] getParamTbTimestampOfRow(int paramInt) {
    return (this.paramTbTimestamp == null) ? null : this.paramTbTimestamp[paramInt];
  }
  
  public TbTimestampTZ getParamTbTimestampTZ(int paramInt1, int paramInt2) {
    return this.paramTbTimestampTZ[paramInt1][paramInt2];
  }
  
  public TbTimestampTZ[] getParamTbTimestampTZOfRow(int paramInt) {
    return (this.paramTbTimestampTZ == null) ? null : this.paramTbTimestampTZ[paramInt];
  }
  
  public Timestamp[] getParamTimestampOfRow(int paramInt) {
    return (this.paramTimestamp == null) ? null : this.paramTimestamp[paramInt];
  }
  
  public Array getParamArray(int paramInt1, int paramInt2) {
    return this.paramArray[paramInt1][paramInt2];
  }
  
  public Array[] getParamArrayOfRow(int paramInt) {
    return (this.paramArray == null) ? null : this.paramArray[paramInt];
  }
  
  public Struct getParamStruct(int paramInt1, int paramInt2) {
    return this.paramStruct[paramInt1][paramInt2];
  }
  
  public Struct[] getParamStructOfRow(int paramInt) {
    return (this.paramStruct == null) ? null : this.paramStruct[paramInt];
  }
  
  public byte getParamType(int paramInt1, int paramInt2) {
    return this.paramTypes[paramInt1][paramInt2];
  }
  
  public byte[][] getParamTypes() {
    return this.paramTypes;
  }
  
  public byte[] getParamTypesOfRow(int paramInt) {
    return (this.paramTypes == null) ? null : this.paramTypes[paramInt];
  }
  
  public byte[] getPPID() {
    return this.ppid;
  }
  
  private void growBatchArray(int paramInt1, int paramInt2) {
    int i = this.bindParamCnt + paramInt2;
    int j = this.allocatedBatchRowCount + paramInt1;
    Binder[][] arrayOfBinder = this.binder;
    this.binder = new Binder[j][i];
    if (arrayOfBinder != null)
      for (byte b = 0; b < this.allocatedBatchRowCount; b++)
        System.arraycopy(arrayOfBinder[b], 0, this.binder[b], 0, this.bindParamCnt);  
    byte[][] arrayOfByte = this.paramTypes;
    this.paramTypes = new byte[j][i];
    if (arrayOfByte != null)
      for (byte b = 0; b < this.allocatedBatchRowCount; b++)
        System.arraycopy(arrayOfByte[b], 0, this.paramTypes[b], 0, this.bindParamCnt);  
    if (this.paramInt != null) {
      int[][] arrayOfInt = this.paramInt;
      this.paramInt = new int[j][i];
      for (byte b = 0; b < this.allocatedBatchRowCount; b++)
        System.arraycopy(arrayOfInt[b], 0, this.paramInt[b], 0, this.bindParamCnt); 
    } 
    if (this.paramLong != null) {
      long[][] arrayOfLong = this.paramLong;
      this.paramLong = new long[j][i];
      for (byte b = 0; b < this.allocatedBatchRowCount; b++)
        System.arraycopy(arrayOfLong[b], 0, this.paramLong[b], 0, this.bindParamCnt); 
    } 
    if (this.paramFloat != null) {
      float[][] arrayOfFloat = this.paramFloat;
      this.paramFloat = new float[j][i];
      for (byte b = 0; b < this.allocatedBatchRowCount; b++)
        System.arraycopy(arrayOfFloat[b], 0, this.paramFloat[b], 0, this.bindParamCnt); 
    } 
    if (this.paramDouble != null) {
      double[][] arrayOfDouble = this.paramDouble;
      this.paramDouble = new double[j][i];
      for (byte b = 0; b < this.allocatedBatchRowCount; b++)
        System.arraycopy(arrayOfDouble[b], 0, this.paramDouble[b], 0, this.bindParamCnt); 
    } 
    if (this.paramBigDecimal != null) {
      BigDecimal[][] arrayOfBigDecimal = this.paramBigDecimal;
      this.paramBigDecimal = new BigDecimal[j][i];
      for (byte b = 0; b < this.allocatedBatchRowCount; b++)
        System.arraycopy(arrayOfBigDecimal[b], 0, this.paramBigDecimal[b], 0, this.bindParamCnt); 
    } 
    if (this.paramString != null) {
      String[][] arrayOfString = this.paramString;
      this.paramString = new String[j][i];
      for (byte b = 0; b < this.allocatedBatchRowCount; b++)
        System.arraycopy(arrayOfString[b], 0, this.paramString[b], 0, this.bindParamCnt); 
    } 
    if (this.paramCalendar != null) {
      Calendar[][] arrayOfCalendar = this.paramCalendar;
      this.paramCalendar = new Calendar[j][i];
      for (byte b = 0; b < this.allocatedBatchRowCount; b++)
        System.arraycopy(arrayOfCalendar[b], 0, this.paramCalendar[b], 0, this.bindParamCnt); 
    } 
    if (this.paramTimestamp != null) {
      Timestamp[][] arrayOfTimestamp = this.paramTimestamp;
      this.paramTimestamp = new Timestamp[j][i];
      for (byte b = 0; b < this.allocatedBatchRowCount; b++)
        System.arraycopy(arrayOfTimestamp[b], 0, this.paramTimestamp[b], 0, this.bindParamCnt); 
    } 
    if (this.paramTbDate != null) {
      TbDate[][] arrayOfTbDate = this.paramTbDate;
      this.paramTbDate = new TbDate[j][i];
      for (byte b = 0; b < this.allocatedBatchRowCount; b++)
        System.arraycopy(arrayOfTbDate[b], 0, this.paramTbDate[b], 0, this.bindParamCnt); 
    } 
    if (this.paramTbTimestamp != null) {
      TbTimestamp[][] arrayOfTbTimestamp = this.paramTbTimestamp;
      this.paramTbTimestamp = new TbTimestamp[j][i];
      for (byte b = 0; b < this.allocatedBatchRowCount; b++)
        System.arraycopy(arrayOfTbTimestamp[b], 0, this.paramTbTimestamp[b], 0, this.bindParamCnt); 
    } 
    if (this.paramTbTimestampTZ != null) {
      TbTimestampTZ[][] arrayOfTbTimestampTZ = this.paramTbTimestampTZ;
      this.paramTbTimestampTZ = new TbTimestampTZ[j][i];
      for (byte b = 0; b < this.allocatedBatchRowCount; b++)
        System.arraycopy(arrayOfTbTimestampTZ[b], 0, this.paramTbTimestampTZ[b], 0, this.bindParamCnt); 
    } 
    if (this.paramBytes != null) {
      byte[][][] arrayOfByte1 = this.paramBytes;
      this.paramBytes = new byte[j][i][];
      for (byte b = 0; b < this.allocatedBatchRowCount; b++)
        System.arraycopy(arrayOfByte1[b], 0, this.paramBytes[b], 0, this.bindParamCnt); 
    } 
    if (this.paramStream != null) {
      InputStream[][] arrayOfInputStream = this.paramStream;
      this.paramStream = new InputStream[j][i];
      for (byte b = 0; b < this.allocatedBatchRowCount; b++)
        System.arraycopy(arrayOfInputStream[b], 0, this.paramStream[b], 0, this.bindParamCnt); 
    } 
    if (this.paramReader != null) {
      Reader[][] arrayOfReader = this.paramReader;
      this.paramReader = new Reader[j][i];
      for (byte b = 0; b < this.allocatedBatchRowCount; b++)
        System.arraycopy(arrayOfReader[b], 0, this.paramReader[b], 0, this.bindParamCnt); 
    } 
    this.allocatedBatchRowCount = j;
  }
  
  public void initParameter() throws SQLException {
    this.bindParamCnt = TbSQLParser.getParamCount(this.originalSql, this.sqlType);
    this.allocatedBatchRowCount = 1;
    this.currentRowIndex = 0;
    if (this.bindData == null) {
      this.bindData = new BindData(this.bindParamCnt);
    } else {
      this.bindData.reuse();
      this.bindData.resize(this.bindParamCnt);
    } 
    this.paramTypes = new byte[this.allocatedBatchRowCount][this.bindParamCnt];
    this.binder = new Binder[this.allocatedBatchRowCount][this.bindParamCnt];
  }
  
  private void initSQLInfo(String paramString) throws SQLException {
    if (this.originalSql.equals(paramString))
      return; 
    if (this.ppid != null)
      this.ppid = null; 
    initSql(paramString);
    initParameter();
  }
  
  public boolean isPoolable() throws SQLException {
    if (isClosed())
      throw TbError.newSQLException(-90659); 
    return this.poolable;
  }
  
  private void pushParamData(int paramInt1, int paramInt2) throws SQLException {
    BindItem bindItem = this.bindData.getBindItem(paramInt2);
    int i = bindItem.getSQLType();
    int j;
    for (j = this.bindParamCnt + paramInt1 - 1; j > paramInt2; j--)
      this.binder[this.currentRowIndex][j] = this.binder[this.currentRowIndex][j - 1]; 
    for (j = this.bindParamCnt + paramInt1 - 1; j > paramInt2; j--)
      this.paramTypes[this.currentRowIndex][j] = this.paramTypes[this.currentRowIndex][j - 1]; 
    switch (i) {
      case 4:
        for (j = this.bindParamCnt + paramInt1 - 1; j > paramInt2; j--)
          this.paramInt[this.currentRowIndex][j] = this.paramInt[this.currentRowIndex][j - 1]; 
      case -5:
        for (j = this.bindParamCnt + paramInt1 - 1; j > paramInt2; j--)
          this.paramBigDecimal[this.currentRowIndex][j] = this.paramBigDecimal[this.currentRowIndex][j - 1]; 
      case 6:
        for (j = this.bindParamCnt + paramInt1 - 1; j > paramInt2; j--)
          this.paramFloat[this.currentRowIndex][j] = this.paramFloat[this.currentRowIndex][j - 1]; 
      case 8:
        for (j = this.bindParamCnt + paramInt1 - 1; j > paramInt2; j--)
          this.paramDouble[this.currentRowIndex][j] = this.paramDouble[this.currentRowIndex][j - 1]; 
      case 2:
        for (j = this.bindParamCnt + paramInt1 - 1; j > paramInt2; j--)
          this.paramLong[this.currentRowIndex][j] = this.paramLong[this.currentRowIndex][j - 1]; 
      case 12:
        for (j = this.bindParamCnt + paramInt1 - 1; j > paramInt2; j--)
          this.paramString[this.currentRowIndex][j] = this.paramString[this.currentRowIndex][j - 1]; 
      case -1:
        for (j = this.bindParamCnt + paramInt1 - 1; j > paramInt2; j--)
          this.paramReader[this.currentRowIndex][j] = this.paramReader[this.currentRowIndex][j - 1]; 
      case 91:
        for (j = this.bindParamCnt + paramInt1 - 1; j > paramInt2; j--) {
          if (this.paramTbDate != null)
            this.paramTbDate[this.currentRowIndex][j] = this.paramTbDate[this.currentRowIndex][j - 1]; 
        } 
      case 92:
        for (j = this.bindParamCnt + paramInt1 - 1; j > paramInt2; j--) {
          if (this.paramCalendar != null)
            this.paramCalendar[this.currentRowIndex][j] = this.paramCalendar[this.currentRowIndex][j - 1]; 
        } 
      case 93:
        for (j = this.bindParamCnt + paramInt1 - 1; j > paramInt2; j--) {
          if (this.paramTimestamp != null)
            this.paramTimestamp[this.currentRowIndex][j] = this.paramTimestamp[this.currentRowIndex][j - 1]; 
          if (this.paramTbTimestamp != null)
            this.paramTbTimestamp[this.currentRowIndex][j] = this.paramTbTimestamp[this.currentRowIndex][j - 1]; 
          if (this.paramTbTimestampTZ != null)
            this.paramTbTimestampTZ[this.currentRowIndex][j] = this.paramTbTimestampTZ[this.currentRowIndex][j - 1]; 
        } 
      case -2:
        for (j = this.bindParamCnt + paramInt1 - 1; j > paramInt2; j--)
          this.paramBytes[this.currentRowIndex][j] = this.paramBytes[this.currentRowIndex][j - 1]; 
      case -4:
        for (j = this.bindParamCnt + paramInt1 - 1; j > paramInt2; j--)
          this.paramStream[this.currentRowIndex][j] = this.paramStream[this.currentRowIndex][j - 1]; 
      case 0:
        return;
    } 
    throw TbError.newSQLException(-590704, Integer.toString(i));
  }
  
  protected void reset() {
    super.reset();
    this.ppid = null;
    this.colMeta = null;
    this.paramMeta = null;
    this.paramInt = (int[][])null;
    this.paramLong = (long[][])null;
    this.paramFloat = (float[][])null;
    this.paramDouble = (double[][])null;
    this.paramBigDecimal = (BigDecimal[][])null;
    this.paramString = (String[][])null;
    this.paramCalendar = (Calendar[][])null;
    this.paramTimestamp = (Timestamp[][])null;
    this.paramTbDate = (TbDate[][])null;
    this.paramTbTimestamp = (TbTimestamp[][])null;
    this.paramTbTimestampTZ = (TbTimestampTZ[][])null;
    this.paramBytes = (byte[][][])null;
    this.paramStream = (InputStream[][])null;
    this.paramReader = (Reader[][])null;
    this.paramTypes = (byte[][])null;
    this.binder = (Binder[][])null;
    this.processedBigLiteralSql = null;
    this.resultSetMetaData = null;
    this.parameterMetaData = null;
    if (this.bigLiterals != null) {
      this.bigLiterals.clear();
      this.bigLiterals = null;
    } 
    if (this.bindData != null) {
      this.bindData.reset();
      this.bindData = null;
    } 
    if (this.batchUpdateInfo != null) {
      this.batchUpdateInfo.clear();
      this.batchUpdateInfo = null;
    } 
    this.autoGenKeyArr = null;
  }
  
  public synchronized void resetForCache() {
    super.resetForCache();
    this.paramInt = (int[][])null;
    this.paramLong = (long[][])null;
    this.paramFloat = (float[][])null;
    this.paramDouble = (double[][])null;
    this.paramBigDecimal = (BigDecimal[][])null;
    this.paramString = (String[][])null;
    this.paramCalendar = (Calendar[][])null;
    this.paramTimestamp = (Timestamp[][])null;
    this.paramTbDate = (TbDate[][])null;
    this.paramTbTimestamp = (TbTimestamp[][])null;
    this.paramTbTimestampTZ = (TbTimestampTZ[][])null;
    this.paramBytes = (byte[][][])null;
    this.paramStream = (InputStream[][])null;
    this.paramReader = (Reader[][])null;
    this.paramTypes = (byte[][])null;
    this.binder = (Binder[][])null;
    this.allocatedBatchRowCount = 1;
    this.currentRowIndex = 0;
    this.processedBigLiteralSql = null;
    this.resultSetMetaData = null;
    if (this.bigLiterals != null) {
      this.bigLiterals.clear();
      this.bigLiterals = null;
    } 
    if (this.bindData != null) {
      this.bindData.reset();
      this.bindData = null;
    } 
    if (this.batchUpdateInfo != null) {
      this.batchUpdateInfo.clear();
      this.batchUpdateInfo = null;
    } 
  }
  
  public void setArray(int paramInt, Array paramArray) throws SQLException {
    setArrayInternal(paramInt, paramArray);
  }
  
  public void setAsciiStream(int paramInt, InputStream paramInputStream) throws SQLException {
    setAsciiStreamInternal(paramInt, paramInputStream, 2147483647);
  }
  
  public void setAsciiStream(int paramInt1, InputStream paramInputStream, int paramInt2) throws SQLException {
    setAsciiStreamInternal(paramInt1, paramInputStream, paramInt2);
  }
  
  public void setAsciiStream(int paramInt, InputStream paramInputStream, long paramLong) throws SQLException {
    if (paramLong > 2147483647L)
      throw TbError.newSQLException(-90656, Long.toString(paramLong)); 
    setAsciiStreamInternal(paramInt, paramInputStream, (int)paramLong);
  }
  
  void setAsciiStreamInternal(int paramInt1, InputStream paramInputStream, int paramInt2) throws SQLException {
    if (paramInputStream == null || paramInt2 <= 0) {
      setNullInternal(paramInt1, 3);
      return;
    } 
    boolean bool = (65532 < paramInt2) ? true : paramInt2;
    byte[] arrayOfByte = new byte[bool];
    int i = 0;
    try {
      i = paramInputStream.read(arrayOfByte, 0, bool);
    } catch (IOException iOException) {
      throw TbError.newSQLException(-90202, iOException.getMessage());
    } 
    if (i < 0) {
      setNullInternal(paramInt1, 3);
    } else if (i > 65532) {
      if (this.paramStream == null)
        this.paramStream = new InputStream[this.allocatedBatchRowCount][this.bindParamCnt]; 
      this.bindData.setDFRParam(paramInt1 - 1, -1, paramInt2);
      this.paramStream[this.currentRowIndex][paramInt1 - 1] = (InputStream)new BytesStreamWrapper(paramInputStream, arrayOfByte, i);
      this.binder[this.currentRowIndex][paramInt1 - 1] = this.staticStreamBinder;
      this.paramTypes[this.currentRowIndex][paramInt1 - 1] = 13;
    } else {
      if (this.paramBytes == null)
        this.paramBytes = new byte[this.allocatedBatchRowCount][this.bindParamCnt][]; 
      this.bindData.setINParam(paramInt1 - 1, 12, i);
      this.paramBytes[this.currentRowIndex][paramInt1 - 1] = arrayOfByte;
      this.binder[this.currentRowIndex][paramInt1 - 1] = this.staticBytesBinder;
      this.paramTypes[this.currentRowIndex][paramInt1 - 1] = 3;
    } 
  }
  
  public void setBigDecimal(int paramInt, BigDecimal paramBigDecimal) throws SQLException {
    setBigDecimalInternal(paramInt, paramBigDecimal);
  }
  
  void setBigDecimalInternal(int paramInt, BigDecimal paramBigDecimal) throws SQLException {
    if (paramBigDecimal == null) {
      setNullInternal(paramInt, 1);
      return;
    } 
    if (this.paramBigDecimal == null)
      this.paramBigDecimal = new BigDecimal[this.allocatedBatchRowCount][this.bindParamCnt]; 
    this.bindData.setINParam(paramInt - 1, -5, -1);
    this.paramBigDecimal[this.currentRowIndex][paramInt - 1] = paramBigDecimal;
    this.paramTypes[this.currentRowIndex][paramInt - 1] = 1;
    this.binder[this.currentRowIndex][paramInt - 1] = this.staticBigDecimalBinder;
  }
  
  public void setBinaryStream(int paramInt, InputStream paramInputStream) throws SQLException {
    setBinaryStreamInternal(paramInt, paramInputStream, 2147483647);
  }
  
  public void setBinaryStream(int paramInt1, InputStream paramInputStream, int paramInt2) throws SQLException {
    setBinaryStreamInternal(paramInt1, paramInputStream, paramInt2);
  }
  
  public void setBinaryStream(int paramInt, InputStream paramInputStream, long paramLong) throws SQLException {
    if (paramLong > 2147483647L)
      throw TbError.newSQLException(-90656, Long.toString(paramLong)); 
    setBinaryStreamInternal(paramInt, paramInputStream, (int)paramLong);
  }
  
  void setBinaryStreamInternal(int paramInt1, InputStream paramInputStream, int paramInt2) throws SQLException {
    if (paramInputStream == null || paramInt2 <= 0) {
      setNullInternal(paramInt1, 4);
      return;
    } 
    boolean bool = (65532 < paramInt2) ? true : paramInt2;
    byte[] arrayOfByte = new byte[bool];
    int i = 0;
    try {
      i = paramInputStream.read(arrayOfByte, 0, bool);
    } catch (IOException iOException) {
      throw TbError.newSQLException(-90202, iOException.getMessage());
    } 
    if (i < 0) {
      setNullInternal(paramInt1, 4);
    } else if (i > 65532) {
      if (this.paramStream == null)
        this.paramStream = new InputStream[this.allocatedBatchRowCount][this.bindParamCnt]; 
      this.bindData.setDFRParam(paramInt1 - 1, -4, paramInt2);
      this.paramStream[this.currentRowIndex][paramInt1 - 1] = (InputStream)new BytesStreamWrapper(paramInputStream, arrayOfByte, i);
      this.binder[this.currentRowIndex][paramInt1 - 1] = this.staticStreamBinder;
      this.paramTypes[this.currentRowIndex][paramInt1 - 1] = 12;
    } else {
      if (this.paramBytes == null)
        this.paramBytes = new byte[this.allocatedBatchRowCount][this.bindParamCnt][]; 
      this.bindData.setINParam(paramInt1 - 1, -2, i);
      this.paramBytes[this.currentRowIndex][paramInt1 - 1] = arrayOfByte;
      this.binder[this.currentRowIndex][paramInt1 - 1] = this.staticBytesBinder;
      this.paramTypes[this.currentRowIndex][paramInt1 - 1] = 4;
    } 
  }
  
  public void setBindData(BindData paramBindData) {
    this.bindData = paramBindData;
  }
  
  public void setBlob(int paramInt, Blob paramBlob) throws SQLException {
    setBlobInternal(paramInt, paramBlob);
  }
  
  public void setBlob(int paramInt, InputStream paramInputStream) throws SQLException {
    setBlobInternal(paramInt, paramInputStream, 2147483647L);
  }
  
  public void setBlob(int paramInt, InputStream paramInputStream, long paramLong) throws SQLException {
    setBlobInternal(paramInt, paramInputStream, paramLong);
  }
  
  void setBlobInternal(int paramInt, Blob paramBlob) throws SQLException {
    if (paramBlob == null) {
      setNullInternal(paramInt, 12);
      return;
    } 
    if (this.paramBytes == null)
      this.paramBytes = new byte[this.allocatedBatchRowCount][this.bindParamCnt][]; 
    this.bindData.setINParam(paramInt - 1, -2, ((TbLob)paramBlob).getLocatorLength());
    this.paramBytes[this.currentRowIndex][paramInt - 1] = ((TbLob)paramBlob).getLocator();
    this.paramTypes[this.currentRowIndex][paramInt - 1] = 12;
    this.binder[this.currentRowIndex][paramInt - 1] = this.staticBytesBinder;
  }
  
  void setBlobInternal(int paramInt, InputStream paramInputStream, long paramLong) throws SQLException {
    if (paramLong > 2147483647L)
      throw TbError.newSQLException(-90656, Long.toString(paramLong)); 
    setBinaryStreamInternal(paramInt, paramInputStream, (int)paramLong);
  }
  
  public void setBoolean(int paramInt, boolean paramBoolean) throws SQLException {
    setBooleanInternal(paramInt, paramBoolean);
  }
  
  void setBooleanInternal(int paramInt, boolean paramBoolean) throws SQLException {
    if (this.paramString == null)
      this.paramString = new String[this.allocatedBatchRowCount][this.bindParamCnt]; 
    this.bindData.setINParam(paramInt - 1, 1, 1);
    this.paramString[this.currentRowIndex][paramInt - 1] = paramBoolean ? "1" : "0";
    this.paramTypes[this.currentRowIndex][paramInt - 1] = 2;
    this.binder[this.currentRowIndex][paramInt - 1] = this.staticStringBinder;
  }
  
  public void setByte(int paramInt, byte paramByte) throws SQLException {
    setByteInternal(paramInt, paramByte);
  }
  
  void setByteInternal(int paramInt, byte paramByte) throws SQLException {
    if (this.paramInt == null)
      this.paramInt = new int[this.allocatedBatchRowCount][this.bindParamCnt]; 
    this.bindData.setINParam(paramInt - 1, 4, -1);
    this.paramInt[this.currentRowIndex][paramInt - 1] = paramByte;
    this.paramTypes[this.currentRowIndex][paramInt - 1] = 1;
    this.binder[this.currentRowIndex][paramInt - 1] = this.staticIntBinder;
  }
  
  public void setBytes(int paramInt, byte[] paramArrayOfbyte) throws SQLException {
    setBytesInternal(paramInt, 4, paramArrayOfbyte);
  }
  
  public void setBytes(int paramInt1, int paramInt2, byte[] paramArrayOfbyte) throws SQLException {
    DataType.checkValidDataType(paramInt2);
    setBytesInternal(paramInt1, paramInt2, paramArrayOfbyte);
  }
  
  public void setRAW(int paramInt, TbRAW paramTbRAW) throws SQLException {
    setBytes(paramInt, paramTbRAW.getBytes());
  }
  
  public void setRAW(int paramInt1, int paramInt2, TbRAW paramTbRAW) throws SQLException {
    setBytes(paramInt1, paramInt2, paramTbRAW.getBytes());
  }
  
  void setBytesInternal(int paramInt1, int paramInt2, byte[] paramArrayOfbyte) throws SQLException {
    if (paramArrayOfbyte == null) {
      setNullInternal(paramInt1, paramInt2);
      return;
    } 
    if (paramArrayOfbyte.length >= 65532) {
      if (this.paramStream == null)
        this.paramStream = new InputStream[this.allocatedBatchRowCount][this.bindParamCnt]; 
      this.bindData.setDFRParam(paramInt1 - 1, -4, paramArrayOfbyte.length);
      this.paramStream[this.currentRowIndex][paramInt1 - 1] = new ByteArrayInputStream(paramArrayOfbyte);
      this.binder[this.currentRowIndex][paramInt1 - 1] = this.staticStreamBinder;
      this.paramTypes[this.currentRowIndex][paramInt1 - 1] = 12;
    } else {
      if (this.paramBytes == null)
        this.paramBytes = new byte[this.allocatedBatchRowCount][this.bindParamCnt][]; 
      if (!this.supportBinaryDoubleFloatType && (paramInt2 == 24 || paramInt2 == 23))
        paramInt2 = 1; 
      this.bindData.setINParam(paramInt1 - 1, -2, paramArrayOfbyte.length);
      this.paramBytes[this.currentRowIndex][paramInt1 - 1] = paramArrayOfbyte;
      this.binder[this.currentRowIndex][paramInt1 - 1] = this.staticBytesBinder;
      this.paramTypes[this.currentRowIndex][paramInt1 - 1] = (byte)paramInt2;
    } 
  }
  
  public void setCharacterStream(int paramInt, Reader paramReader) throws SQLException {
    setCharacterStreamInternal(paramInt, paramReader, 2147483647);
  }
  
  public void setCharacterStream(int paramInt1, Reader paramReader, int paramInt2) throws SQLException {
    setCharacterStreamInternal(paramInt1, paramReader, paramInt2);
  }
  
  public void setCharacterStream(int paramInt, Reader paramReader, long paramLong) throws SQLException {
    if (paramLong > 2147483647L)
      throw TbError.newSQLException(-90656, Long.toString(paramLong)); 
    setCharacterStreamInternal(paramInt, paramReader, (int)paramLong);
  }
  
  void setCharacterStreamInternal(int paramInt1, Reader paramReader, int paramInt2) throws SQLException {
    if (paramReader == null || paramInt2 <= 0) {
      setNullInternal(paramInt1, 3);
      return;
    } 
    int i = (paramInt2 < this.varcharMax + 1) ? paramInt2 : (this.varcharMax + 1);
    char[] arrayOfChar = new char[i];
    int j = 0;
    try {
      j = paramReader.read(arrayOfChar, 0, i);
    } catch (IOException iOException) {
      throw TbError.newSQLException(-90202, iOException.getMessage());
    } 
    if (j < 0) {
      setNullInternal(paramInt1, 3);
    } else if (j < this.deferrableStrLen) {
      if (this.paramString == null)
        this.paramString = new String[this.allocatedBatchRowCount][this.bindParamCnt]; 
      this.bindData.setINParam(paramInt1 - 1, 12, paramInt2);
      this.paramString[this.currentRowIndex][paramInt1 - 1] = new String(arrayOfChar, 0, j);
      this.binder[this.currentRowIndex][paramInt1 - 1] = this.staticStringBinder;
      this.paramTypes[this.currentRowIndex][paramInt1 - 1] = 3;
    } else if (j > this.varcharMax) {
      if (this.paramReader == null)
        this.paramReader = new Reader[this.allocatedBatchRowCount][this.bindParamCnt]; 
      this.bindData.setDFRParam(paramInt1 - 1, -1, paramInt2);
      this.paramReader[this.currentRowIndex][paramInt1 - 1] = (Reader)ReaderWrapper.getInstance(paramReader, arrayOfChar, j);
      this.binder[this.currentRowIndex][paramInt1 - 1] = this.staticReaderBinder;
      this.paramTypes[this.currentRowIndex][paramInt1 - 1] = 13;
    } else {
      byte[] arrayOfByte = new byte[j * this.typeConverter.getMaxBytesPerChar()];
      int k = this.typeConverter.charsToBytes(arrayOfChar, 0, j, arrayOfByte, 0, arrayOfByte.length);
      if (k < this.varcharMax) {
        if (this.paramBytes == null)
          this.paramBytes = new byte[this.allocatedBatchRowCount][this.bindParamCnt][]; 
        this.bindData.setINParam(paramInt1 - 1, 12, k);
        this.paramBytes[this.currentRowIndex][paramInt1 - 1] = arrayOfByte;
        this.binder[this.currentRowIndex][paramInt1 - 1] = this.staticBytesBinder;
        this.paramTypes[this.currentRowIndex][paramInt1 - 1] = 3;
      } else {
        if (this.paramStream == null)
          this.paramStream = new InputStream[this.allocatedBatchRowCount][this.bindParamCnt]; 
        this.bindData.setDFRParam(paramInt1 - 1, -1, k);
        this.paramStream[this.currentRowIndex][paramInt1 - 1] = new ByteArrayInputStream(arrayOfByte, 0, k);
        this.binder[this.currentRowIndex][paramInt1 - 1] = this.staticStreamBinder;
        this.paramTypes[this.currentRowIndex][paramInt1 - 1] = 13;
      } 
    } 
  }
  
  public void setClob(int paramInt, Clob paramClob) throws SQLException {
    setClobInternal(paramInt, 13, paramClob);
  }
  
  public void setClob(int paramInt, Reader paramReader) throws SQLException {
    setClobInternal(paramInt, paramReader, 2147483647L);
  }
  
  public void setClob(int paramInt, Reader paramReader, long paramLong) throws SQLException {
    setClobInternal(paramInt, paramReader, paramLong);
  }
  
  public void setClobInternal(int paramInt1, int paramInt2, Clob paramClob) throws SQLException {
    if (paramClob == null) {
      setNullInternal(paramInt1, paramInt2);
      return;
    } 
    if (!(paramClob instanceof TbClob) && !(paramClob instanceof TbNClob))
      throw TbError.newSQLException(-590770, paramClob.toString()); 
    if (this.paramBytes == null)
      this.paramBytes = new byte[this.allocatedBatchRowCount][this.bindParamCnt][]; 
    this.bindData.setINParam(paramInt1 - 1, -2, (((TbLob)paramClob).getLocator()).length);
    this.paramBytes[this.currentRowIndex][paramInt1 - 1] = ((TbLob)paramClob).getLocator();
    this.paramTypes[this.currentRowIndex][paramInt1 - 1] = (byte)paramInt2;
    this.binder[this.currentRowIndex][paramInt1 - 1] = this.staticBytesBinder;
  }
  
  void setClobInternal(int paramInt, Reader paramReader, long paramLong) throws SQLException {
    if (paramLong > 2147483647L)
      throw TbError.newSQLException(-90656, Long.toString(paramLong)); 
    setCharacterStreamInternal(paramInt, paramReader, (int)paramLong);
  }
  
  public void setDate(int paramInt, Date paramDate) throws SQLException {
    setDateInternal(paramInt, paramDate);
  }
  
  public void setDate(int paramInt, Date paramDate, Calendar paramCalendar) throws SQLException {
    if (paramDate == null) {
      setNullInternal(paramInt, 5);
      return;
    } 
    if (this.paramBytes == null)
      this.paramBytes = new byte[this.allocatedBatchRowCount][this.bindParamCnt][]; 
    if (paramCalendar == null)
      paramCalendar = Calendar.getInstance(); 
    paramCalendar.setTime(paramDate);
    paramCalendar.set(11, 0);
    paramCalendar.set(12, 0);
    paramCalendar.set(13, 0);
    paramCalendar.set(14, 0);
    byte[] arrayOfByte = new byte[8];
    this.typeConverter.fromDate(arrayOfByte, 0, paramCalendar);
    this.bindData.setINParam(paramInt - 1, 91, arrayOfByte.length);
    this.paramBytes[this.currentRowIndex][paramInt - 1] = arrayOfByte;
    this.paramTypes[this.currentRowIndex][paramInt - 1] = 5;
    this.binder[this.currentRowIndex][paramInt - 1] = this.staticBytesBinder;
  }
  
  void setDateInternal(int paramInt, Date paramDate) throws SQLException {
    if (paramDate == null) {
      setNullInternal(paramInt, 5);
      return;
    } 
    if (this.paramCalendar == null)
      this.paramCalendar = new Calendar[this.allocatedBatchRowCount][this.bindParamCnt]; 
    Calendar calendar = Calendar.getInstance();
    if (this.conn.getNlsCalendar().equals("THAI BUDDHA")) {
      int i = calendar.get(1);
      calendar.set(1, i - 543);
    } else {
      calendar.setTime(paramDate);
    } 
    calendar.set(11, 0);
    calendar.set(12, 0);
    calendar.set(13, 0);
    calendar.set(14, 0);
    this.bindData.setINParam(paramInt - 1, 91, -1);
    this.paramCalendar[this.currentRowIndex][paramInt - 1] = calendar;
    this.paramTypes[this.currentRowIndex][paramInt - 1] = 5;
    this.binder[this.currentRowIndex][paramInt - 1] = this.staticDateBinder;
  }
  
  public void setTbDate(int paramInt, TbDate paramTbDate) throws SQLException {
    setTbDateInternal(paramInt, paramTbDate);
  }
  
  void setTbDateInternal(int paramInt, TbDate paramTbDate) throws SQLException {
    if (paramTbDate == null) {
      setNullInternal(paramInt, 5);
      return;
    } 
    if (this.paramTbDate == null)
      this.paramTbDate = new TbDate[this.allocatedBatchRowCount][this.bindParamCnt]; 
    this.bindData.setINParam(paramInt - 1, 91, -1);
    this.paramTbDate[this.currentRowIndex][paramInt - 1] = paramTbDate;
    this.paramTypes[this.currentRowIndex][paramInt - 1] = 5;
    this.binder[this.currentRowIndex][paramInt - 1] = this.staticTbDateBinder;
  }
  
  public void setDouble(int paramInt, double paramDouble) throws SQLException {
    setDoubleInternal(paramInt, paramDouble);
  }
  
  void setDoubleInternal(int paramInt, double paramDouble) throws SQLException {
    if (this.paramDouble == null)
      this.paramDouble = new double[this.allocatedBatchRowCount][this.bindParamCnt]; 
    if (this.supportBinaryDoubleFloatType && (Double.compare(paramDouble, Double.NaN) == 0 || Double.compare(paramDouble, Double.NEGATIVE_INFINITY) == 0 || Double.compare(paramDouble, Double.POSITIVE_INFINITY) == 0)) {
      setBinaryDoubleInternal(paramInt, paramDouble);
    } else {
      this.bindData.setINParam(paramInt - 1, 8, -1);
      this.paramDouble[this.currentRowIndex][paramInt - 1] = paramDouble;
      this.paramTypes[this.currentRowIndex][paramInt - 1] = 1;
      this.binder[this.currentRowIndex][paramInt - 1] = this.staticDoubleBinder;
    } 
  }
  
  public void setBinaryDouble(int paramInt, double paramDouble) throws SQLException {
    setBinaryDoubleInternal(paramInt, paramDouble);
  }
  
  void setBinaryDoubleInternal(int paramInt, double paramDouble) throws SQLException {
    if (this.paramDouble == null)
      this.paramDouble = new double[this.allocatedBatchRowCount][this.bindParamCnt]; 
    if (this.supportBinaryDoubleFloatType) {
      this.bindData.setINParam(paramInt - 1, 101, -1);
      this.paramDouble[this.currentRowIndex][paramInt - 1] = paramDouble;
      this.paramTypes[this.currentRowIndex][paramInt - 1] = 24;
      this.binder[this.currentRowIndex][paramInt - 1] = this.staticBinaryDoubleBinder;
    } else {
      setDoubleInternal(paramInt, paramDouble);
    } 
  }
  
  public void setFixedCHAR(int paramInt, String paramString) throws SQLException {
    setFixedCHARInternal(paramInt, paramString);
  }
  
  void setFixedCHARInternal(int paramInt, String paramString) throws SQLException {
    if (paramString == null || paramString.length() == 0) {
      setNullInternal(paramInt, 3);
      return;
    } 
    if (this.paramString == null)
      this.paramString = new String[this.allocatedBatchRowCount][this.bindParamCnt]; 
    this.bindData.setINParam(paramInt - 1, 1, paramString.length());
    this.paramString[this.currentRowIndex][paramInt - 1] = paramString;
    this.binder[this.currentRowIndex][paramInt - 1] = this.staticStringBinder;
    this.paramTypes[this.currentRowIndex][paramInt - 1] = 2;
  }
  
  public void setFloat(int paramInt, float paramFloat) throws SQLException {
    setFloatInternal(paramInt, paramFloat);
  }
  
  void setFloatInternal(int paramInt, float paramFloat) throws SQLException {
    if (this.paramFloat == null)
      this.paramFloat = new float[this.allocatedBatchRowCount][this.bindParamCnt]; 
    if (this.supportBinaryDoubleFloatType && (Float.compare(paramFloat, Float.NaN) == 0 || Float.compare(paramFloat, Float.NEGATIVE_INFINITY) == 0 || Float.compare(paramFloat, Float.POSITIVE_INFINITY) == 0)) {
      setBinaryFloatInternal(paramInt, paramFloat);
    } else {
      this.bindData.setINParam(paramInt - 1, 6, -1);
      this.paramFloat[this.currentRowIndex][paramInt - 1] = paramFloat;
      this.paramTypes[this.currentRowIndex][paramInt - 1] = 1;
      this.binder[this.currentRowIndex][paramInt - 1] = this.staticFloatBinder;
    } 
  }
  
  public void setBinaryFloat(int paramInt, float paramFloat) throws SQLException {
    setBinaryFloatInternal(paramInt, paramFloat);
  }
  
  void setBinaryFloatInternal(int paramInt, float paramFloat) throws SQLException {
    if (this.paramFloat == null)
      this.paramFloat = new float[this.allocatedBatchRowCount][this.bindParamCnt]; 
    if (this.supportBinaryDoubleFloatType) {
      this.bindData.setINParam(paramInt - 1, 100, -1);
      this.paramFloat[this.currentRowIndex][paramInt - 1] = paramFloat;
      this.paramTypes[this.currentRowIndex][paramInt - 1] = 23;
      this.binder[this.currentRowIndex][paramInt - 1] = this.staticBinaryFloatBinder;
    } else {
      setFloatInternal(paramInt, paramFloat);
    } 
  }
  
  public void setInt(int paramInt1, int paramInt2) throws SQLException {
    setIntInternal(paramInt1, paramInt2);
  }
  
  void setIntInternal(int paramInt1, int paramInt2) throws SQLException {
    if (this.paramInt == null)
      this.paramInt = new int[this.allocatedBatchRowCount][this.bindParamCnt]; 
    this.bindData.setINParam(paramInt1 - 1, 4, -1);
    this.paramInt[this.currentRowIndex][paramInt1 - 1] = paramInt2;
    this.paramTypes[this.currentRowIndex][paramInt1 - 1] = 1;
    this.binder[this.currentRowIndex][paramInt1 - 1] = this.staticIntBinder;
  }
  
  public void setLong(int paramInt, long paramLong) throws SQLException {
    setLongInternal(paramInt, paramLong);
  }
  
  void setLongInternal(int paramInt, long paramLong) throws SQLException {
    if (this.paramLong == null)
      this.paramLong = new long[this.allocatedBatchRowCount][this.bindParamCnt]; 
    this.bindData.setINParam(paramInt - 1, 2, -1);
    this.paramLong[this.currentRowIndex][paramInt - 1] = paramLong;
    this.paramTypes[this.currentRowIndex][paramInt - 1] = 1;
    this.binder[this.currentRowIndex][paramInt - 1] = this.staticLongBinder;
  }
  
  public void setNCharacterStream(int paramInt, Reader paramReader) throws SQLException {
    setNCharacterStreamInternal(paramInt, paramReader, 2147483647);
  }
  
  public void setNCharacterStream(int paramInt, Reader paramReader, long paramLong) throws SQLException {
    if (paramLong > 2147483647L)
      throw TbError.newSQLException(-90656, Long.toString(paramLong)); 
    setNCharacterStreamInternal(paramInt, paramReader, (int)paramLong);
  }
  
  void setNCharacterStreamInternal(int paramInt1, Reader paramReader, int paramInt2) throws SQLException {
    if (paramReader == null || paramInt2 <= 0) {
      setNullInternal(paramInt1, 19);
      return;
    } 
    int i = (paramInt2 < this.varcharMax + 1) ? paramInt2 : (this.varcharMax + 1);
    char[] arrayOfChar = new char[i];
    int j = 0;
    try {
      j = paramReader.read(arrayOfChar, 0, i);
    } catch (IOException iOException) {
      throw TbError.newSQLException(-90202, iOException.getMessage());
    } 
    if (j < 0) {
      setNullInternal(paramInt1, 19);
    } else if (j < this.deferrableNStrLen) {
      if (this.paramString == null)
        this.paramString = new String[this.allocatedBatchRowCount][this.bindParamCnt]; 
      this.bindData.setINParam(paramInt1 - 1, -9, paramInt2);
      this.paramString[this.currentRowIndex][paramInt1 - 1] = new String(arrayOfChar, 0, j);
      this.binder[this.currentRowIndex][paramInt1 - 1] = this.staticNStringBinder;
      this.paramTypes[this.currentRowIndex][paramInt1 - 1] = 19;
    } else if (j > this.varcharMax) {
      if (this.paramReader == null)
        this.paramReader = new Reader[this.allocatedBatchRowCount][this.bindParamCnt]; 
      this.bindData.setDFRParam(paramInt1 - 1, -16, paramInt2);
      this.paramReader[this.currentRowIndex][paramInt1 - 1] = (Reader)ReaderWrapper.getInstance(paramReader, arrayOfChar, j);
      this.binder[this.currentRowIndex][paramInt1 - 1] = this.staticNReaderBinder;
      this.paramTypes[this.currentRowIndex][paramInt1 - 1] = 20;
    } else {
      byte[] arrayOfByte = new byte[j * this.typeConverter.getMaxBytesPerNChar()];
      int k = this.typeConverter.nCharsToBytes(arrayOfChar, 0, j, arrayOfByte, 0, arrayOfByte.length);
      if (k < this.varcharMax) {
        if (this.paramBytes == null)
          this.paramBytes = new byte[this.allocatedBatchRowCount][this.bindParamCnt][]; 
        this.bindData.setINParam(paramInt1 - 1, -9, k);
        this.paramBytes[this.currentRowIndex][paramInt1 - 1] = arrayOfByte;
        this.binder[this.currentRowIndex][paramInt1 - 1] = this.staticBytesBinder;
        this.paramTypes[this.currentRowIndex][paramInt1 - 1] = 19;
      } else {
        if (this.paramStream == null)
          this.paramStream = new InputStream[this.allocatedBatchRowCount][this.bindParamCnt]; 
        this.bindData.setDFRParam(paramInt1 - 1, -16, k);
        this.paramStream[this.currentRowIndex][paramInt1 - 1] = new ByteArrayInputStream(arrayOfByte, 0, k);
        this.binder[this.currentRowIndex][paramInt1 - 1] = this.staticStreamBinder;
        this.paramTypes[this.currentRowIndex][paramInt1 - 1] = 20;
      } 
    } 
  }
  
  public void setNClob(int paramInt, Clob paramClob) throws SQLException {
    setClobInternal(paramInt, 20, paramClob);
  }
  
  public void setNClob(int paramInt, NClob paramNClob) throws SQLException {
    setClobInternal(paramInt, 20, paramNClob);
  }
  
  public void setNClob(int paramInt, Reader paramReader) throws SQLException {
    setNClobInternal(paramInt, paramReader, 2147483647);
  }
  
  public void setNClob(int paramInt, Reader paramReader, long paramLong) throws SQLException {
    if (paramLong > 2147483647L)
      throw TbError.newSQLException(-90656, Long.toString(paramLong)); 
    setNClobInternal(paramInt, paramReader, (int)paramLong);
  }
  
  void setNClobInternal(int paramInt1, Reader paramReader, int paramInt2) throws SQLException {
    if (paramReader == null || paramInt2 <= 0) {
      setNullInternal(paramInt1, 19);
      return;
    } 
    if (paramInt2 >= this.conn.getMaxDFRNCharCount()) {
      if (this.paramReader == null)
        this.paramReader = new Reader[this.allocatedBatchRowCount][this.bindParamCnt]; 
      this.bindData.setDFRParam(paramInt1 - 1, -16, paramInt2);
      this.paramReader[this.currentRowIndex][paramInt1 - 1] = paramReader;
      this.binder[this.currentRowIndex][paramInt1 - 1] = this.staticNReaderBinder;
      this.paramTypes[this.currentRowIndex][paramInt1 - 1] = 20;
    } else {
      int i = 0;
      char[] arrayOfChar = new char[paramInt2];
      try {
        i = paramReader.read(arrayOfChar, 0, paramInt2);
      } catch (IOException iOException) {
        throw TbError.newSQLException(-90202, iOException.getMessage());
      } 
      if (i != paramInt2) {
        char[] arrayOfChar1 = arrayOfChar;
        arrayOfChar = new char[i];
        System.arraycopy(arrayOfChar1, 0, arrayOfChar, 0, i);
      } 
      if (this.paramString == null)
        this.paramString = new String[this.allocatedBatchRowCount][this.bindParamCnt]; 
      this.bindData.setINParam(paramInt1 - 1, -9, paramInt2);
      this.paramString[this.currentRowIndex][paramInt1 - 1] = new String(arrayOfChar);
      this.binder[this.currentRowIndex][paramInt1 - 1] = this.staticStringBinder;
      this.paramTypes[this.currentRowIndex][paramInt1 - 1] = 19;
    } 
  }
  
  public void setNString(int paramInt, String paramString) throws SQLException {
    setNStringInternal(paramInt, paramString);
  }
  
  void setNStringInternal(int paramInt, String paramString) throws SQLException {
    if (paramString == null || paramString.length() == 0) {
      setNullInternal(paramInt, 19);
      return;
    } 
    if (paramString.length() < this.deferrableNStrLen) {
      if (this.paramString == null)
        this.paramString = new String[this.allocatedBatchRowCount][this.bindParamCnt]; 
      this.bindData.setINParam(paramInt - 1, -9, paramString.length());
      this.paramString[this.currentRowIndex][paramInt - 1] = paramString;
      this.binder[this.currentRowIndex][paramInt - 1] = this.staticNStringBinder;
      this.paramTypes[this.currentRowIndex][paramInt - 1] = 19;
    } else {
      byte[] arrayOfByte = new byte[paramString.length() * this.typeConverter.getMaxBytesPerNChar()];
      int i = this.typeConverter.fromNString(arrayOfByte, 0, paramString);
      if (i > this.varcharMax) {
        if (this.paramStream == null)
          this.paramStream = new InputStream[this.allocatedBatchRowCount][this.bindParamCnt]; 
        this.bindData.setDFRParam(paramInt - 1, -16, i);
        this.paramStream[this.currentRowIndex][paramInt - 1] = new ByteArrayInputStream(arrayOfByte, 0, i);
        this.binder[this.currentRowIndex][paramInt - 1] = this.staticStreamBinder;
        this.paramTypes[this.currentRowIndex][paramInt - 1] = 20;
      } else {
        if (this.paramBytes == null)
          this.paramBytes = new byte[this.allocatedBatchRowCount][this.bindParamCnt][]; 
        this.bindData.setINParam(paramInt - 1, -9, i);
        this.paramBytes[this.currentRowIndex][paramInt - 1] = arrayOfByte;
        this.binder[this.currentRowIndex][paramInt - 1] = this.staticBytesBinder;
        this.paramTypes[this.currentRowIndex][paramInt - 1] = 19;
      } 
    } 
  }
  
  public void setNull(int paramInt1, int paramInt2) throws SQLException {
    setNullInternal(paramInt1, DataType.getDataType(paramInt2));
  }
  
  public void setNull(int paramInt1, int paramInt2, String paramString) throws SQLException {
    setNullInternal(paramInt1, DataType.getDataType(paramInt2));
  }
  
  void setNullInternal(int paramInt1, int paramInt2) throws SQLException {
    if (paramInt2 == 29)
      throw TbError.newSQLException(-590703, DataType.getDBTypeName(paramInt2)); 
    this.bindData.setINParam(paramInt1 - 1, 0, 0);
    this.paramTypes[this.currentRowIndex][paramInt1 - 1] = (byte)paramInt2;
    this.binder[this.currentRowIndex][paramInt1 - 1] = this.staticNullBinder;
  }
  
  public void setObject(int paramInt, Object paramObject) throws SQLException {
    setObject(paramInt, paramObject, DataType.getSqlType(paramObject), 0);
  }
  
  public void setObject(int paramInt1, Object paramObject, int paramInt2) throws SQLException {
    setObject(paramInt1, paramObject, paramInt2, 0);
  }
  
  public void setObject(int paramInt1, Object paramObject, int paramInt2, int paramInt3) throws SQLException {
    setObjectInternal(paramInt1, paramObject, paramInt2, paramInt3);
  }
  
  void setObjectInternal(int paramInt1, Object paramObject, int paramInt2, int paramInt3) throws SQLException {
    if (paramObject == null) {
      setNullInternal(paramInt1, 2);
      return;
    } 
    switch (paramInt2) {
      case -7:
        setBooleanInternal(paramInt1, (new Boolean(paramObject.toString())).booleanValue());
        return;
      case -15:
      case -9:
        setNStringInternal(paramInt1, paramObject.toString());
        return;
      case -1:
      case 1:
      case 12:
        if (paramObject instanceof Boolean) {
          setStringInternal(paramInt1, ((Boolean)paramObject).booleanValue() ? "1" : "0");
        } else {
          setStringInternal(paramInt1, paramObject.toString());
        } 
        return;
      case -4:
      case -3:
      case -2:
        setBytesInternal(paramInt1, 4, (byte[])paramObject);
        return;
      case -6:
      case 5:
        if (paramObject instanceof Number) {
          setShortInternal(paramInt1, ((Number)paramObject).shortValue());
        } else if (paramObject instanceof String) {
          setShortInternal(paramInt1, Short.parseShort((String)paramObject));
        } else if (paramObject instanceof Boolean) {
          setShortInternal(paramInt1, (short)(((Boolean)paramObject).booleanValue() ? 1 : 0));
        } else {
          throw TbError.newSQLException(-590704, paramObject.toString() + "," + paramInt2);
        } 
        return;
      case 4:
        if (paramObject instanceof Number) {
          setIntInternal(paramInt1, ((Number)paramObject).intValue());
        } else if (paramObject instanceof BigInteger) {
          setIntInternal(paramInt1, ((BigInteger)paramObject).intValue());
        } else if (paramObject instanceof String) {
          setIntInternal(paramInt1, Integer.parseInt((String)paramObject));
        } else if (paramObject instanceof Boolean) {
          setIntInternal(paramInt1, ((Boolean)paramObject).booleanValue() ? 1 : 0);
        } else {
          throw TbError.newSQLException(-590704, paramObject.toString() + "," + paramInt2);
        } 
        return;
      case -5:
        if (paramObject instanceof Number) {
          setLongInternal(paramInt1, ((Number)paramObject).longValue());
        } else if (paramObject instanceof BigInteger) {
          setLongInternal(paramInt1, ((BigInteger)paramObject).longValue());
        } else if (paramObject instanceof String) {
          setLongInternal(paramInt1, Long.parseLong((String)paramObject));
        } else if (paramObject instanceof Boolean) {
          setLongInternal(paramInt1, ((Boolean)paramObject).booleanValue() ? 1L : 0L);
        } else {
          throw TbError.newSQLException(-590704, paramObject.toString() + "," + paramInt2);
        } 
        return;
      case 3:
        if (paramObject instanceof BigDecimal) {
          setBigDecimalInternal(paramInt1, (BigDecimal)paramObject);
        } else if (paramObject instanceof BigInteger) {
          setBigDecimalInternal(paramInt1, new BigDecimal((BigInteger)paramObject));
        } else if (paramObject instanceof Number) {
          setBigDecimalInternal(paramInt1, new BigDecimal(((Number)paramObject).doubleValue()));
        } else if (paramObject instanceof String) {
          setBigDecimalInternal(paramInt1, new BigDecimal((String)paramObject));
        } else if (paramObject instanceof Boolean) {
          setBigDecimalInternal(paramInt1, new BigDecimal(((Boolean)paramObject).booleanValue() ? 1.0D : 0.0D));
        } else {
          throw TbError.newSQLException(-590704, paramObject.toString() + "," + paramInt2);
        } 
        return;
      case 7:
        if (paramObject instanceof Number) {
          setFloatInternal(paramInt1, ((Number)paramObject).floatValue());
        } else if (paramObject instanceof String) {
          setFloatInternal(paramInt1, Float.valueOf((String)paramObject).floatValue());
        } else if (paramObject instanceof Boolean) {
          setFloatInternal(paramInt1, ((Boolean)paramObject).booleanValue() ? 1.0F : 0.0F);
        } else {
          throw TbError.newSQLException(-590704, paramObject.toString() + "," + paramInt2);
        } 
        return;
      case 6:
      case 8:
        if (paramObject instanceof Number) {
          setDoubleInternal(paramInt1, ((Number)paramObject).doubleValue());
        } else if (paramObject instanceof String) {
          setDoubleInternal(paramInt1, Double.valueOf((String)paramObject).doubleValue());
        } else if (paramObject instanceof Boolean) {
          setDoubleInternal(paramInt1, ((Boolean)paramObject).booleanValue() ? 1.0D : 0.0D);
        } else {
          throw TbError.newSQLException(-590704, paramObject.toString() + "," + paramInt2);
        } 
        return;
      case 2:
        if (paramObject instanceof Short) {
          setShortInternal(paramInt1, ((Short)paramObject).shortValue());
        } else if (paramObject instanceof Integer) {
          setIntInternal(paramInt1, ((Integer)paramObject).intValue());
        } else if (paramObject instanceof Long) {
          setLongInternal(paramInt1, ((Long)paramObject).longValue());
        } else if (paramObject instanceof Float) {
          setFloatInternal(paramInt1, ((Float)paramObject).floatValue());
        } else if (paramObject instanceof Double) {
          setDoubleInternal(paramInt1, ((Double)paramObject).doubleValue());
        } else if (paramObject instanceof BigDecimal) {
          setBigDecimalInternal(paramInt1, (BigDecimal)paramObject);
        } else if (paramObject instanceof BigInteger) {
          setBigDecimalInternal(paramInt1, new BigDecimal((BigInteger)paramObject));
        } else if (paramObject instanceof Boolean) {
          setBooleanInternal(paramInt1, ((Boolean)paramObject).booleanValue());
        } else if (paramObject instanceof String) {
          setBigDecimalInternal(paramInt1, new BigDecimal((String)paramObject));
        } else {
          throw TbError.newSQLException(-590704, paramObject.toString() + "," + paramInt2);
        } 
        return;
      case 100:
        if (paramObject instanceof Number) {
          setBinaryFloatInternal(paramInt1, ((Number)paramObject).floatValue());
        } else if (paramObject instanceof String) {
          setBinaryFloatInternal(paramInt1, Float.valueOf((String)paramObject).floatValue());
        } else if (paramObject instanceof Boolean) {
          setBinaryFloatInternal(paramInt1, ((Boolean)paramObject).booleanValue() ? 1.0F : 0.0F);
        } else {
          throw TbError.newSQLException(-590704, paramObject.toString() + "," + paramInt2);
        } 
        return;
      case 101:
        if (paramObject instanceof Number) {
          setBinaryDoubleInternal(paramInt1, ((Number)paramObject).doubleValue());
        } else if (paramObject instanceof String) {
          setBinaryDoubleInternal(paramInt1, Double.valueOf((String)paramObject).doubleValue());
        } else if (paramObject instanceof Boolean) {
          setBinaryDoubleInternal(paramInt1, ((Boolean)paramObject).booleanValue() ? 1.0D : 0.0D);
        } else {
          throw TbError.newSQLException(-590704, paramObject.toString() + "," + paramInt2);
        } 
        return;
      case 91:
        if (paramObject instanceof Date) {
          setDateInternal(paramInt1, (Date)paramObject);
        } else if (paramObject instanceof Timestamp) {
          setDateInternal(paramInt1, (Date)paramObject);
        } else if (paramObject instanceof String) {
          setDateInternal(paramInt1, Date.valueOf((String)paramObject));
        } else if (paramObject instanceof TbDate) {
          setTbDate(paramInt1, (TbDate)paramObject);
        } else {
          throw TbError.newSQLException(-590704, paramObject.toString() + "," + paramInt2);
        } 
        return;
      case 92:
        if (paramObject instanceof Time) {
          setTimeInternal(paramInt1, (Time)paramObject);
        } else if (paramObject instanceof Timestamp) {
          setTimeInternal(paramInt1, new Time(((Timestamp)paramObject).getTime()));
        } else if (paramObject instanceof Date) {
          setTimeInternal(paramInt1, new Time(((Date)paramObject).getTime()));
        } else if (paramObject instanceof String) {
          setTimeInternal(paramInt1, Time.valueOf((String)paramObject));
        } else {
          throw TbError.newSQLException(-590704, paramObject.toString() + "," + paramInt2);
        } 
        return;
      case 93:
        if (paramObject instanceof Timestamp) {
          setTimestampInternal(paramInt1, (Timestamp)paramObject);
        } else if (paramObject instanceof Date) {
          setDateInternal(paramInt1, (Date)paramObject);
        } else if (paramObject instanceof String) {
          setTimestampInternal(paramInt1, Timestamp.valueOf((String)paramObject));
        } else if (paramObject instanceof TbTimestamp) {
          setTbTimestamp(paramInt1, (TbTimestamp)paramObject);
        } else {
          throw TbError.newSQLException(-590704, paramObject.toString() + "," + paramInt2);
        } 
        return;
      case 2004:
        if (paramObject instanceof Blob) {
          setBlobInternal(paramInt1, (Blob)paramObject);
        } else {
          throw TbError.newSQLException(-590704, paramObject.toString() + "," + paramInt2);
        } 
        return;
      case 2011:
        if (paramObject instanceof NClob) {
          setClobInternal(paramInt1, 20, (NClob)paramObject);
        } else {
          throw TbError.newSQLException(-590704, paramObject.toString() + "," + paramInt2);
        } 
        return;
      case 2005:
        if (paramObject instanceof Clob) {
          setClobInternal(paramInt1, 13, (Clob)paramObject);
        } else {
          throw TbError.newSQLException(-590704, paramObject.toString() + "," + paramInt2);
        } 
        return;
      case -8:
        if (paramObject instanceof RowId) {
          setRowIdInternal(paramInt1, (RowId)paramObject);
        } else {
          throw TbError.newSQLException(-590704, paramObject.toString() + "," + paramInt2);
        } 
        return;
      case 2009:
        if (paramObject instanceof SQLXML) {
          setSQLXMLInternal(paramInt1, (SQLXML)paramObject);
        } else {
          throw TbError.newSQLException(-590704, paramObject.toString() + "," + paramInt2);
        } 
        return;
      case 2002:
        if (paramObject instanceof Struct) {
          setStructInternal(paramInt1, (Struct)paramObject);
        } else if (paramObject instanceof java.sql.SQLData) {
          setStructInternal(paramInt1, (Struct)TbStruct.toStruct(paramObject, (Connection)this.conn));
        } else {
          throw TbError.newSQLException(-590704, paramObject.toString() + "," + paramInt2);
        } 
        return;
      case -2003:
      case 2003:
        if (paramObject instanceof Array) {
          setArrayInternal(paramInt1, (Array)paramObject);
        } else {
          throw TbError.newSQLException(-590704, paramObject.toString() + "," + paramInt2);
        } 
        return;
      case 2006:
        if (paramObject instanceof Ref) {
          setRefInternal(paramInt1, 33, (Ref)paramObject);
        } else {
          throw TbError.newSQLException(-590704, paramObject.toString() + "," + paramInt2);
        } 
        return;
    } 
    throw TbError.newSQLException(-590704, paramObject.toString() + "," + paramInt2);
  }
  
  private void setStructInternal(int paramInt, Struct paramStruct) throws SQLException {
    if (paramStruct == null) {
      setNullInternal(paramInt, 28);
      return;
    } 
    if (this.paramStruct == null)
      this.paramStruct = (Struct[][])new TbStruct[this.allocatedBatchRowCount][this.bindParamCnt]; 
    TbStruct tbStruct = (TbStruct)paramStruct;
    int i = tbStruct.getDescriptor().getDataType();
    this.bindData.setINParam(paramInt - 1, i, 0);
    this.paramStruct[this.currentRowIndex][paramInt - 1] = (Struct)tbStruct;
    this.paramTypes[this.currentRowIndex][paramInt - 1] = (byte)i;
    this.binder[this.currentRowIndex][paramInt - 1] = this.staticStructInBinder;
    TbStructDescriptor tbStructDescriptor = tbStruct.getDescriptor();
    this.bindData.getBindItem(paramInt - 1).setTypeDescriptor((TbTypeDescriptor)tbStructDescriptor);
  }
  
  private void setArrayInternal(int paramInt, Array paramArray) throws SQLException {
    if (paramArray == null) {
      setNullInternal(paramInt, 29);
      return;
    } 
    if (this.paramArray == null)
      this.paramArray = (Array[][])new TbArray[this.allocatedBatchRowCount][this.bindParamCnt]; 
    TbArray tbArray = (TbArray)paramArray;
    int i = tbArray.getDescriptor().getDataType();
    this.bindData.setINParam(paramInt - 1, i, 0);
    this.paramArray[this.currentRowIndex][paramInt - 1] = (Array)tbArray;
    this.paramTypes[this.currentRowIndex][paramInt - 1] = (byte)i;
    this.binder[this.currentRowIndex][paramInt - 1] = this.staticArrayInBinder;
    TbArrayDescriptor tbArrayDescriptor = tbArray.getDescriptor();
    this.bindData.getBindItem(paramInt - 1).setTypeDescriptor((TbTypeDescriptor)tbArrayDescriptor);
  }
  
  private void setTableInternal(int paramInt, Array paramArray) throws SQLException {
    throw TbError.newSQLException(-90201);
  }
  
  public void setParameterCnt(int paramInt) {
    this.bindParamCnt = paramInt;
  }
  
  protected void setParamTypes(int paramInt, byte paramByte) {
    this.paramTypes[this.currentRowIndex][paramInt] = paramByte;
  }
  
  public void setPoolable(boolean paramBoolean) throws SQLException {
    if (isClosed())
      throw TbError.newSQLException(-90659); 
    this.poolable = paramBoolean;
  }
  
  public void setPPID(byte[] paramArrayOfbyte) {
    if (paramArrayOfbyte == null || Arrays.equals(paramArrayOfbyte, PPID_NULL)) {
      this.ppid = null;
    } else {
      this.ppid = paramArrayOfbyte;
    } 
  }
  
  public void setRef(int paramInt, Ref paramRef) throws SQLException {
    setRefInternal(paramInt, 33, paramRef);
  }
  
  public void setRefInternal(int paramInt1, int paramInt2, Ref paramRef) throws SQLException {
    if (paramRef == null) {
      setNullInternal(paramInt1, paramInt2);
      return;
    } 
    if (!(paramRef instanceof TbRef))
      throw TbError.newSQLException(-590770, paramRef.toString()); 
    if (this.paramBytes == null)
      this.paramBytes = new byte[this.allocatedBatchRowCount][this.bindParamCnt][]; 
    this.bindData.setINParam(paramInt1 - 1, 2006, (((TbRef)paramRef).getRawData()).length);
    this.paramBytes[this.currentRowIndex][paramInt1 - 1] = ((TbRef)paramRef).getRawData();
    this.binder[this.currentRowIndex][paramInt1 - 1] = this.staticBytesBinder;
    this.paramTypes[this.currentRowIndex][paramInt1 - 1] = (byte)paramInt2;
  }
  
  public void setRowId(int paramInt, RowId paramRowId) throws SQLException {
    if (!(paramRowId instanceof TbRowId))
      throw TbError.newSQLException(-590771, String.valueOf(paramRowId)); 
    setRowIdInternal(paramInt, paramRowId);
  }
  
  void setRowIdInternal(int paramInt, RowId paramRowId) throws SQLException {
    if (this.paramBytes == null)
      this.paramBytes = new byte[this.allocatedBatchRowCount][this.bindParamCnt][]; 
    this.bindData.setINParam(paramInt - 1, -8, (paramRowId.getBytes()).length);
    this.paramBytes[this.currentRowIndex][paramInt - 1] = paramRowId.getBytes();
    this.paramTypes[this.currentRowIndex][paramInt - 1] = 15;
    this.binder[this.currentRowIndex][paramInt - 1] = this.staticBytesBinder;
  }
  
  public void setShort(int paramInt, short paramShort) throws SQLException {
    setShortInternal(paramInt, paramShort);
  }
  
  void setShortInternal(int paramInt, short paramShort) throws SQLException {
    if (this.paramInt == null)
      this.paramInt = new int[this.allocatedBatchRowCount][this.bindParamCnt]; 
    this.bindData.setINParam(paramInt - 1, 4, -1);
    this.paramInt[this.currentRowIndex][paramInt - 1] = paramShort;
    this.paramTypes[this.currentRowIndex][paramInt - 1] = 1;
    this.binder[this.currentRowIndex][paramInt - 1] = this.staticIntBinder;
  }
  
  public void setSQLXML(int paramInt, SQLXML paramSQLXML) throws SQLException {
    setSQLXMLInternal(paramInt, paramSQLXML);
  }
  
  void setSQLXMLInternal(int paramInt, SQLXML paramSQLXML) throws SQLException {
    InputStream inputStream = ((TbSQLXML)paramSQLXML).getValue();
    if (this.paramStream == null)
      this.paramStream = new InputStream[this.allocatedBatchRowCount][this.bindParamCnt]; 
    this.bindData.setDFRParam(paramInt - 1, 2009, 2147483647);
    this.paramStream[this.currentRowIndex][paramInt - 1] = inputStream;
    this.paramTypes[this.currentRowIndex][paramInt - 1] = 13;
    this.binder[this.currentRowIndex][paramInt - 1] = this.staticStreamBinder;
  }
  
  public void setString(int paramInt, String paramString) throws SQLException {
    setStringInternal(paramInt, paramString);
  }
  
  void setStringInternal(int paramInt, String paramString) throws SQLException {
    if (this.defaultNChar) {
      setNStringInternal(paramInt, paramString);
      return;
    } 
    if (paramString == null || paramString.length() == 0) {
      setNullInternal(paramInt, 3);
      return;
    } 
    if (paramString.length() < this.deferrableStrLen) {
      if (this.paramString == null)
        this.paramString = new String[this.allocatedBatchRowCount][this.bindParamCnt]; 
      this.bindData.setINParam(paramInt - 1, 12, paramString.length());
      this.paramString[this.currentRowIndex][paramInt - 1] = paramString;
      this.binder[this.currentRowIndex][paramInt - 1] = this.staticStringBinder;
      this.paramTypes[this.currentRowIndex][paramInt - 1] = 3;
    } else if (paramString.length() > this.varcharMax) {
      if (this.paramReader == null)
        this.paramReader = new Reader[this.allocatedBatchRowCount][this.bindParamCnt]; 
      this.bindData.setDFRParam(paramInt - 1, -1, paramString.length());
      this.paramReader[this.currentRowIndex][paramInt - 1] = new StringReader(paramString);
      this.binder[this.currentRowIndex][paramInt - 1] = this.staticReaderBinder;
      this.paramTypes[this.currentRowIndex][paramInt - 1] = 13;
    } else {
      byte[] arrayOfByte = new byte[paramString.length() * this.typeConverter.getMaxBytesPerChar()];
      int i = this.typeConverter.fromString(arrayOfByte, 0, paramString);
      if (i > this.varcharMax) {
        if (this.paramStream == null)
          this.paramStream = new InputStream[this.allocatedBatchRowCount][this.bindParamCnt]; 
        this.bindData.setDFRParam(paramInt - 1, -1, i);
        this.paramStream[this.currentRowIndex][paramInt - 1] = new ByteArrayInputStream(arrayOfByte, 0, i);
        this.binder[this.currentRowIndex][paramInt - 1] = this.staticStreamBinder;
        this.paramTypes[this.currentRowIndex][paramInt - 1] = 13;
      } else {
        if (this.paramBytes == null)
          this.paramBytes = new byte[this.allocatedBatchRowCount][this.bindParamCnt][]; 
        this.bindData.setINParam(paramInt - 1, 12, i);
        this.paramBytes[this.currentRowIndex][paramInt - 1] = arrayOfByte;
        this.binder[this.currentRowIndex][paramInt - 1] = this.staticBytesBinder;
        this.paramTypes[this.currentRowIndex][paramInt - 1] = 3;
      } 
    } 
  }
  
  public void setTime(int paramInt, Time paramTime) throws SQLException {
    setTimeInternal(paramInt, paramTime);
  }
  
  public void setTime(int paramInt1, Time paramTime, int paramInt2) throws SQLException {
    if (paramTime == null) {
      setNullInternal(paramInt1, 7);
      return;
    } 
    Timestamp timestamp = new Timestamp(paramTime.getTime() + paramInt2);
    setTimestamp(paramInt1, timestamp);
  }
  
  public void setTime(int paramInt, Time paramTime, Calendar paramCalendar) throws SQLException {
    if (paramTime == null) {
      setNullInternal(paramInt, 5);
      return;
    } 
    if (this.paramBytes == null)
      this.paramBytes = new byte[this.allocatedBatchRowCount][this.bindParamCnt][]; 
    if (paramCalendar == null)
      paramCalendar = Calendar.getInstance(); 
    paramCalendar.setTime(paramTime);
    byte[] arrayOfByte = new byte[8];
    this.typeConverter.fromTime(arrayOfByte, 0, paramCalendar, 0);
    this.bindData.setINParam(paramInt - 1, 92, arrayOfByte.length);
    this.paramBytes[this.currentRowIndex][paramInt - 1] = arrayOfByte;
    this.paramTypes[this.currentRowIndex][paramInt - 1] = 5;
    this.binder[this.currentRowIndex][paramInt - 1] = this.staticBytesBinder;
  }
  
  void setTimeInternal(int paramInt, Time paramTime) throws SQLException {
    if (paramTime == null) {
      setNullInternal(paramInt, 7);
      return;
    } 
    Timestamp timestamp = new Timestamp(paramTime.getTime());
    setTimestamp(paramInt, timestamp);
  }
  
  public void setTimestamp(int paramInt, Timestamp paramTimestamp) throws SQLException {
    setTimestampInternal(paramInt, paramTimestamp);
  }
  
  public void setTimestamp(int paramInt, Timestamp paramTimestamp, Calendar paramCalendar) throws SQLException {
    if (paramTimestamp == null) {
      setNullInternal(paramInt, 7);
      return;
    } 
    if (paramCalendar == null)
      paramCalendar = Calendar.getInstance(); 
    TbDatabaseMetaData tbDatabaseMetaData = new TbDatabaseMetaData(this.conn);
    if (tbDatabaseMetaData.getDatabaseMajorVersion() < 5) {
      paramCalendar.setTimeInMillis(paramTimestamp.getTime());
      TbTimestamp tbTimestamp = new TbTimestamp(paramCalendar.get(1), paramCalendar.get(2) + 1, paramCalendar.get(5), paramCalendar.get(11), paramCalendar.get(12), paramCalendar.get(13), paramTimestamp.getNanos());
      if (this.paramTbTimestamp == null)
        this.paramTbTimestamp = new TbTimestamp[this.allocatedBatchRowCount][this.bindParamCnt]; 
      this.bindData.setINParam(paramInt - 1, 93, -1);
      this.paramTbTimestamp[this.currentRowIndex][paramInt - 1] = tbTimestamp;
      this.paramTypes[this.currentRowIndex][paramInt - 1] = 7;
      this.binder[this.currentRowIndex][paramInt - 1] = this.staticTbTimestampBinder;
    } else {
      if (this.paramTbTimestampTZ == null)
        this.paramTbTimestampTZ = new TbTimestampTZ[this.allocatedBatchRowCount][this.bindParamCnt]; 
      TbTimestampTZ tbTimestampTZ = new TbTimestampTZ(paramTimestamp, paramCalendar.getTimeZone());
      this.bindData.setINParam(paramInt - 1, 93, -1);
      this.paramTbTimestampTZ[this.currentRowIndex][paramInt - 1] = tbTimestampTZ;
      this.paramTypes[this.currentRowIndex][paramInt - 1] = 21;
      this.binder[this.currentRowIndex][paramInt - 1] = this.staticTimestampTZBinder;
    } 
  }
  
  void setTimestampInternal(int paramInt, Timestamp paramTimestamp) throws SQLException {
    if (paramTimestamp == null) {
      setNullInternal(paramInt, 7);
      return;
    } 
    if (this.paramTimestamp == null)
      this.paramTimestamp = new Timestamp[this.allocatedBatchRowCount][this.bindParamCnt]; 
    this.bindData.setINParam(paramInt - 1, 93, -1);
    this.paramTimestamp[this.currentRowIndex][paramInt - 1] = paramTimestamp;
    this.paramTypes[this.currentRowIndex][paramInt - 1] = 7;
    this.binder[this.currentRowIndex][paramInt - 1] = this.staticTimestampBinder;
  }
  
  public void setTbTimestamp(int paramInt, TbTimestamp paramTbTimestamp) throws SQLException {
    setTbTimestampInternal(paramInt, paramTbTimestamp);
  }
  
  void setTbTimestampInternal(int paramInt, TbTimestamp paramTbTimestamp) throws SQLException {
    if (paramTbTimestamp == null) {
      setNullInternal(paramInt, 7);
      return;
    } 
    if (this.paramTbTimestamp == null)
      this.paramTbTimestamp = new TbTimestamp[this.allocatedBatchRowCount][this.bindParamCnt]; 
    this.bindData.setINParam(paramInt - 1, 93, -1);
    this.paramTbTimestamp[this.currentRowIndex][paramInt - 1] = paramTbTimestamp;
    this.paramTypes[this.currentRowIndex][paramInt - 1] = 7;
    this.binder[this.currentRowIndex][paramInt - 1] = this.staticTbTimestampBinder;
  }
  
  @Deprecated
  public void setUnicodeStream(int paramInt1, InputStream paramInputStream, int paramInt2) throws SQLException {
    setUnicodeStreamInternal(paramInt1, paramInputStream, paramInt2);
  }
  
  void setUnicodeStreamInternal(int paramInt1, InputStream paramInputStream, int paramInt2) throws SQLException {
    if (paramInputStream == null || paramInt2 <= 0) {
      setNullInternal(paramInt1, 3);
      return;
    } 
    InputStreamReader inputStreamReader = new InputStreamReader(paramInputStream);
    setCharacterStreamInternal(paramInt1, inputStreamReader, paramInt2);
  }
  
  public void setURL(int paramInt, URL paramURL) throws SQLException {
    throw TbError.newSQLException(-90201);
  }
  
  public void addBatch(String paramString) throws SQLException {
    initSQLInfo(paramString);
    int i = this.bindData.getDFRParameterCnt();
    for (byte b = 0; b < this.bindParamCnt; b++) {
      if (this.binder[this.currentRowIndex][b] == null)
        setCachedBindParameter(b); 
      BindItem bindItem = this.bindData.getBindItem(b);
      if (bindItem.isOUTParameter())
        throw TbError.newSQLException(-90631); 
      if (i <= 0 && this.paramTypes[0][b] != this.paramTypes[this.currentRowIndex][b])
        this.batchFlag = 0; 
    } 
    if (this.batchUpdateInfo == null)
      this.batchUpdateInfo = new BatchUpdateInfo(); 
    BindData bindData = new BindData();
    this.bindData.clone(bindData);
    this.bindData.clearDFRParameter();
    this.batchUpdateInfo.add(new BatchInfo(bindData, this.currentRowIndex));
    if (this.currentRowIndex == this.allocatedBatchRowCount - 1)
      growBatchArray(this.allocatedBatchRowCount, 0); 
    this.currentRowIndex++;
  }
  
  public void setAutoGenKeyArr(Object paramObject) {
    this.autoGenKeyArr = paramObject;
  }
  
  private static boolean isSupportBinaryDoubleFloatType(ServerInfo paramServerInfo) {
    return (paramServerInfo.getProtocolMajorVersion() * 100 + paramServerInfo.getProtocolMinorVersion() >= 206);
  }
}


/* Location:              C:\Users\Lenovo\Desktop\tibero\tibero6-jdbc.jar!\com\tmax\tibero\jdbc\driver\TbPreparedStatementImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */