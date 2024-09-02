package ch.admin.bar.siard2.cmd;
import ch.admin.bar.siard2.api.Archive;
import ch.admin.bar.siard2.api.Cell;
import ch.admin.bar.siard2.api.Field;
import ch.admin.bar.siard2.api.MetaColumn;
import ch.admin.bar.siard2.api.MetaType;
import ch.admin.bar.siard2.api.Record;
import ch.admin.bar.siard2.api.RecordRetainer;
import ch.admin.bar.siard2.api.Schema;
import ch.admin.bar.siard2.api.Table;
import ch.admin.bar.siard2.api.Value;
import ch.admin.bar.siard2.api.generated.CategoryType;
import ch.enterag.utils.StopWatch;
import ch.enterag.utils.background.Progress;
import java.io.IOException;
import java.math.BigInteger;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLXML;
import java.sql.Struct;
import org.apache.tika.Tika;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PrimaryDataFromDb extends PrimaryDataTransfer {
  private static final Logger LOG = LoggerFactory.getLogger(PrimaryDataFromDb.class);
  
  private static final long REPORT_RECORDS = 1000L;
  
  private Progress progress = null;
  private long recordsDownloaded = -1L;
  private long recordsTotal = -1L;
  private long recordsPercent = -1L;
  private StopWatch getCellStopWatch = null;
  private StopWatch getValueStopWatch = null;
  private StopWatch setValueStopWatch = null;
  private final Tika tika = new Tika();

  
  private PrimaryDataFromDb(Connection connection, Archive archive) {
    super(connection, archive, null, true, true, true);
  }







  
  public static PrimaryDataFromDb newInstance(Connection connection, Archive archive) {
    return new PrimaryDataFromDb(connection, archive);
  }









  
  public void download(Progress progress) throws IOException, SQLException {
    LOG.info("Start primary data download to archive {}", this._archive
        .getFile().getAbsoluteFile());
    
    System.out.println("\r\nPrimary Data");
    this.progress = progress;
    
    this.recordsTotal = 0L; int iSchema;
    for (iSchema = 0; iSchema < this._archive.getSchemas(); iSchema++) {
      Schema schema = this._archive.getSchema(iSchema);
      for (int iTable = 0; iTable < schema.getTables(); iTable++) {
        this.recordsTotal += schema.getTable(iTable).getMetaTable().getRows();
      }
    } 
    this.recordsPercent = (this.recordsTotal + 99L) / 100L;
    this.recordsDownloaded = 0L;
    
    for (iSchema = 0; iSchema < this._archive.getSchemas() && !cancelRequested(); iSchema++) {
      getSchema(this._archive.getSchema(iSchema));
    }
    if (cancelRequested())
      throw new IOException("\r\nDownload of primary data cancelled!"); 
    System.out.println("\r\nDownload terminated successfully.");
    this._conn.rollback();
    
    LOG.info("Primary data download finished");
  }




  
  private void incDownloaded() {
    this.recordsDownloaded++;
    if (this.progress != null && this.recordsTotal > 0L && this.recordsDownloaded % this.recordsPercent == 0L) {
      int iPercent = (int)(100L * this.recordsDownloaded / this.recordsTotal);
      this.progress.notifyProgress(iPercent);
    } 
  }





  
  private boolean cancelRequested() {
    if (this.progress != null && this.progress.cancelRequested()) {
      LOG.info("Cancel downloading of primary data because of request");
      return true;
    } 
    return false;
  }

  
  private void setValue(Value value, Object oValue, MimeTypeHandler mimeTypeHandler) throws IOException, SQLException {
    if (oValue != null) {
      if (oValue instanceof String) {
        value.setString((String)oValue);
      } else if (oValue instanceof byte[]) {
        byte[] bytes = (byte[])oValue;
        mimeTypeHandler.add(value, bytes);
        value.setBytes(bytes);
      } else if (oValue instanceof Boolean) {
        value.setBoolean(((Boolean)oValue).booleanValue());
      } else if (oValue instanceof Short) {
        value.setShort(((Short)oValue).shortValue());
      } else if (oValue instanceof Integer) {
        value.setInt(((Integer)oValue).intValue());
      } else if (oValue instanceof Long) {
        value.setLong(((Long)oValue).longValue());
      } else if (oValue instanceof BigInteger) {
        value.setBigInteger((BigInteger)oValue);
      } else if (oValue instanceof BigDecimal) {
        value.setBigDecimal((BigDecimal)oValue);
      } else if (oValue instanceof Float) {
        value.setFloat(((Float)oValue).floatValue());
      } else if (oValue instanceof Double) {
        value.setDouble(((Double)oValue).doubleValue());
      } else if (oValue instanceof Timestamp) {
        value.setTimestamp((Timestamp)oValue);
      } else if (oValue instanceof Time) {
        value.setTime((Time)oValue);
      } else if (oValue instanceof Date) {
        value.setDate((Date)oValue);
      } else if (oValue instanceof Duration) {
        value.setDuration((Duration)oValue);
      } else if (oValue instanceof Clob) {
        Clob clob = (Clob)oValue;
        mimeTypeHandler.add(value, clob);
        value.setReader(clob.getCharacterStream());
        clob.free();
      } else if (oValue instanceof SQLXML) {
        SQLXML sqlxml = (SQLXML)oValue;
        value.setReader(sqlxml.getCharacterStream());
        sqlxml.free();
      } else if (oValue instanceof Blob) {
        Blob blob = (Blob)oValue;
        mimeTypeHandler.add(value, blob);
        value.setInputStream(blob.getBinaryStream());
        blob.free();
      } else if (oValue instanceof URL) {
        URL url = (URL)oValue;
        value.setInputStream(url.openStream(), url.getPath());
      } else if (oValue instanceof Array) {
        Array array = (Array)oValue;
        Object[] ao = (Object[])array.getArray();
        for (int iElement = 0; iElement < ao.length; iElement++) {
          Field field = value.getElement(iElement);
          setValue((Value)field, ao[iElement], mimeTypeHandler);
          mimeTypeHandler.applyMimeType((Value)field);
        } 
        array.free();
      } else if (oValue instanceof Struct) {
        Struct struct = (Struct)oValue;
        Object[] ao = struct.getAttributes();
        for (int iAttribute = 0; iAttribute < ao.length; iAttribute++) {
          setValue((Value)value.getAttribute(iAttribute), ao[iAttribute], mimeTypeHandler);
        }
      } else {
        throw new SQLException("Invalid value type " + oValue.getClass().getName() + " encountered!");
      } 
    }
  }






  
  private void getRecord(ResultSet rs, Record record, MimeTypeHandler mimeTypeHandler) throws IOException, SQLException {
    if (rs.getMetaData().getColumnCount() != record.getCells())
      throw new IOException("Invalid number of result columns found!"); 
    for (int cellIndex = 0; cellIndex < record.getCells(); cellIndex++) {
      Cell cell = getCell(record, cellIndex);
      Object oValue = getValue(rs, cell, cellIndex);
      setValue(mimeTypeHandler, cell, oValue);
    } 
  }
  
  private Cell getCell(Record record, int cellIndex) throws IOException {
    this.getCellStopWatch.start();
    Cell cell = record.getCell(cellIndex);
    this.getCellStopWatch.stop();
    return cell;
  }
  
  private Object getValue(ResultSet rs, Cell cell, int cellIndex) throws SQLException, IOException {
    this.getValueStopWatch.start();
    Object oValue = (new ObjectValueReader(rs, getDataType(cell.getMetaColumn()), cellIndex + 1)).read();
    if (rs.wasNull()) oValue = null; 
    this.getValueStopWatch.stop();
    return oValue;
  }
  
  private void setValue(MimeTypeHandler mimeTypeHandler, Cell cell, Object oValue) throws IOException, SQLException {
    this.setValueStopWatch.start();
    setValue((Value)cell, oValue, mimeTypeHandler);
    mimeTypeHandler.applyMimeType((Value)cell);
    this.setValueStopWatch.stop();
  }
  
  private int getDataType(MetaColumn mc) throws IOException {
    int iDataType = mc.getPreType();
    if (mc.getCardinality() >= 0) iDataType = 2003; 
    MetaType mt = mc.getMetaType();
    if (mt != null) {
      CategoryType cat = mt.getCategoryType();
      if (cat == CategoryType.DISTINCT) { iDataType = mt.getBasePreType(); }
      else { iDataType = 2002; }
    
    }  return iDataType;
  }























  
  private void getTable(Table table) throws IOException, SQLException {
    // Byte code:
    //   0: aload_0
    //   1: invokestatic getInstance : ()Lch/enterag/utils/StopWatch;
    //   4: putfield getCellStopWatch : Lch/enterag/utils/StopWatch;
    //   7: aload_0
    //   8: invokestatic getInstance : ()Lch/enterag/utils/StopWatch;
    //   11: putfield getValueStopWatch : Lch/enterag/utils/StopWatch;
    //   14: aload_0
    //   15: invokestatic getInstance : ()Lch/enterag/utils/StopWatch;
    //   18: putfield setValueStopWatch : Lch/enterag/utils/StopWatch;
    //   21: new ch/enterag/sqlparser/identifier/QualifiedId
    //   24: dup
    //   25: aconst_null
    //   26: aload_1
    //   27: invokeinterface getParentSchema : ()Lch/admin/bar/siard2/api/Schema;
    //   32: invokeinterface getMetaSchema : ()Lch/admin/bar/siard2/api/MetaSchema;
    //   37: invokeinterface getName : ()Ljava/lang/String;
    //   42: aload_1
    //   43: invokeinterface getMetaTable : ()Lch/admin/bar/siard2/api/MetaTable;
    //   48: invokeinterface getName : ()Ljava/lang/String;
    //   53: invokespecial <init> : (Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V
    //   56: astore_2
    //   57: getstatic java/lang/System.out : Ljava/io/PrintStream;
    //   60: new java/lang/StringBuilder
    //   63: dup
    //   64: invokespecial <init> : ()V
    //   67: ldc '  Table: '
    //   69: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   72: aload_2
    //   73: invokevirtual format : ()Ljava/lang/String;
    //   76: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   79: invokevirtual toString : ()Ljava/lang/String;
    //   82: invokevirtual println : (Ljava/lang/String;)V
    //   85: lconst_0
    //   86: lstore_3
    //   87: aload_1
    //   88: invokeinterface createRecords : ()Lch/admin/bar/siard2/api/RecordRetainer;
    //   93: astore #5
    //   95: aload_0
    //   96: aload_1
    //   97: aconst_null
    //   98: invokevirtual openTable : (Lch/admin/bar/siard2/api/Table;Lch/admin/bar/siard2/cmd/SchemaMapping;)Ljava/sql/ResultSet;
    //   101: astore #6
    //   103: aload #6
    //   105: invokeinterface getStatement : ()Ljava/sql/Statement;
    //   110: astore #7
    //   112: invokestatic getInstance : ()Lch/enterag/utils/StopWatch;
    //   115: astore #8
    //   117: invokestatic getInstance : ()Lch/enterag/utils/StopWatch;
    //   120: astore #9
    //   122: invokestatic getInstance : ()Lch/enterag/utils/StopWatch;
    //   125: astore #10
    //   127: invokestatic getInstance : ()Lch/enterag/utils/StopWatch;
    //   130: astore #11
    //   132: aload #11
    //   134: invokevirtual start : ()V
    //   137: aload #5
    //   139: invokeinterface getByteCount : ()J
    //   144: lstore #12
    //   146: new ch/admin/bar/siard2/cmd/MimeTypeHandler
    //   149: dup
    //   150: aload_0
    //   151: getfield tika : Lorg/apache/tika/Tika;
    //   154: invokespecial <init> : (Lorg/apache/tika/Tika;)V
    //   157: astore #14
    //   159: aload #6
    //   161: invokeinterface next : ()Z
    //   166: ifeq -> 229
    //   169: aload_0
    //   170: invokespecial cancelRequested : ()Z
    //   173: ifne -> 229
    //   176: aload #8
    //   178: aload #5
    //   180: invokestatic createRecord : (Lch/enterag/utils/StopWatch;Lch/admin/bar/siard2/api/RecordRetainer;)Lch/admin/bar/siard2/api/Record;
    //   183: astore #15
    //   185: aload_0
    //   186: aload #9
    //   188: aload #6
    //   190: aload #15
    //   192: aload #14
    //   194: invokespecial readRecord : (Lch/enterag/utils/StopWatch;Ljava/sql/ResultSet;Lch/admin/bar/siard2/api/Record;Lch/admin/bar/siard2/cmd/MimeTypeHandler;)V
    //   197: aload #10
    //   199: aload #5
    //   201: aload #15
    //   203: invokestatic putRecord : (Lch/enterag/utils/StopWatch;Lch/admin/bar/siard2/api/RecordRetainer;Lch/admin/bar/siard2/api/Record;)V
    //   206: lload_3
    //   207: dup2
    //   208: lconst_1
    //   209: ladd
    //   210: lstore_3
    //   211: aload #11
    //   213: aload #5
    //   215: lload #12
    //   217: invokestatic logRecordProgress : (JLch/enterag/utils/StopWatch;Lch/admin/bar/siard2/api/RecordRetainer;J)J
    //   220: lstore #12
    //   222: aload_0
    //   223: invokespecial incDownloaded : ()V
    //   226: goto -> 159
    //   229: getstatic java/lang/System.out : Ljava/io/PrintStream;
    //   232: new java/lang/StringBuilder
    //   235: dup
    //   236: invokespecial <init> : ()V
    //   239: ldc '    Record '
    //   241: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   244: lload_3
    //   245: invokevirtual append : (J)Ljava/lang/StringBuilder;
    //   248: ldc ' ('
    //   250: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   253: aload #11
    //   255: aload #5
    //   257: invokeinterface getByteCount : ()J
    //   262: lload #12
    //   264: lsub
    //   265: aload #11
    //   267: invokevirtual stop : ()J
    //   270: invokevirtual formatRate : (JJ)Ljava/lang/String;
    //   273: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   276: ldc ' kB/s)'
    //   278: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   281: invokevirtual toString : ()Ljava/lang/String;
    //   284: invokevirtual println : (Ljava/lang/String;)V
    //   287: getstatic java/lang/System.out : Ljava/io/PrintStream;
    //   290: new java/lang/StringBuilder
    //   293: dup
    //   294: invokespecial <init> : ()V
    //   297: ldc '    Total: '
    //   299: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   302: lload_3
    //   303: invokestatic formatLong : (J)Ljava/lang/String;
    //   306: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   309: ldc ' records ('
    //   311: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   314: aload #5
    //   316: invokeinterface getByteCount : ()J
    //   321: invokestatic formatLong : (J)Ljava/lang/String;
    //   324: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   327: ldc ' bytes in '
    //   329: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   332: aload #11
    //   334: invokevirtual formatMs : ()Ljava/lang/String;
    //   337: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   340: ldc ' ms)'
    //   342: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   345: invokevirtual toString : ()Ljava/lang/String;
    //   348: invokevirtual println : (Ljava/lang/String;)V
    //   351: aload #6
    //   353: invokeinterface isClosed : ()Z
    //   358: ifne -> 368
    //   361: aload #6
    //   363: invokeinterface close : ()V
    //   368: aload #7
    //   370: invokeinterface isClosed : ()Z
    //   375: ifne -> 385
    //   378: aload #7
    //   380: invokeinterface close : ()V
    //   385: aload #5
    //   387: invokeinterface close : ()V
    //   392: getstatic ch/admin/bar/siard2/cmd/PrimaryDataFromDb.LOG : Lorg/slf4j/Logger;
    //   395: ldc 'All data of table '{}.{}' successfully downloaded'
    //   397: aload_2
    //   398: invokevirtual getSchema : ()Ljava/lang/String;
    //   401: aload_2
    //   402: invokevirtual getName : ()Ljava/lang/String;
    //   405: invokeinterface debug : (Ljava/lang/String;Ljava/lang/Object;Ljava/lang/Object;)V
    //   410: return
    // Line number table:
    //   Java source line number -> byte code offset
    //   #254	-> 0
    //   #255	-> 7
    //   #256	-> 14
    //   #257	-> 21
    //   #258	-> 27
    //   #259	-> 43
    //   #260	-> 57
    //   #261	-> 85
    //   #262	-> 87
    //   #263	-> 95
    //   #264	-> 103
    //   #265	-> 112
    //   #266	-> 117
    //   #267	-> 122
    //   #268	-> 127
    //   #269	-> 132
    //   #270	-> 137
    //   #272	-> 146
    //   #273	-> 159
    //   #274	-> 176
    //   #275	-> 185
    //   #276	-> 197
    //   #277	-> 206
    //   #278	-> 222
    //   #279	-> 226
    //   #280	-> 229
    //   #281	-> 267
    //   #280	-> 270
    //   #282	-> 287
    //   #283	-> 351
    //   #284	-> 361
    //   #285	-> 368
    //   #286	-> 378
    //   #287	-> 385
    //   #289	-> 392
    //   #290	-> 398
    //   #291	-> 402
    //   #289	-> 405
    //   #292	-> 410
    // Local variable table:
    //   start	length	slot	name	descriptor
    //   185	41	15	record	Lch/admin/bar/siard2/api/Record;
    //   0	411	0	this	Lch/admin/bar/siard2/cmd/PrimaryDataFromDb;
    //   0	411	1	table	Lch/admin/bar/siard2/api/Table;
    //   57	354	2	qiTable	Lch/enterag/sqlparser/identifier/QualifiedId;
    //   87	324	3	lRecord	J
    //   95	316	5	rr	Lch/admin/bar/siard2/api/RecordRetainer;
    //   103	308	6	rs	Ljava/sql/ResultSet;
    //   112	299	7	stmt	Ljava/sql/Statement;
    //   117	294	8	swCreate	Lch/enterag/utils/StopWatch;
    //   122	289	9	swGet	Lch/enterag/utils/StopWatch;
    //   127	284	10	swPut	Lch/enterag/utils/StopWatch;
    //   132	279	11	sw	Lch/enterag/utils/StopWatch;
    //   146	265	12	lBytesStart	J
    //   159	252	14	mimeTypeHandler	Lch/admin/bar/siard2/cmd/MimeTypeHandler;
  }























  
  private static long logRecordProgress(long lRecord, StopWatch sw, RecordRetainer rr, long lBytesStart) {
    if (lRecord % 1000L == 0L) {
      System.out.println("    Record " + lRecord + " (" + sw.formatRate(rr.getByteCount() - lBytesStart, sw
            .stop()) + " kB/s)");
      lBytesStart = rr.getByteCount();
      sw.start();
    } 
    return lBytesStart;
  }
  
  private static void putRecord(StopWatch swPut, RecordRetainer rr, Record record) throws IOException {
    swPut.start();
    rr.put(record);
    swPut.stop();
  }
  
  private void readRecord(StopWatch swGet, ResultSet rs, Record record, MimeTypeHandler mimeTypeHandler) throws IOException, SQLException {
    swGet.start();
    getRecord(rs, record, mimeTypeHandler);
    swGet.stop();
  }
  
  private static Record createRecord(StopWatch swCreate, RecordRetainer rr) throws IOException {
    swCreate.start();
    Record record = rr.create();
    swCreate.stop();
    return record;
  }









  
  private void getSchema(Schema schema) throws IOException, SQLException {
    for (int iTable = 0; iTable < schema.getTables() && !cancelRequested(); iTable++) {
      Table table = schema.getTable(iTable);
      getTable(table);
    } 
    
    LOG.debug("All data of schema '{}' successfully downloaded", schema.getMetaSchema().getName());
  }
}


/* Location:              C:\Users\lenovo\IdeaProjects\SIARD_KR_2.2\lib\siardcmd.jar!\ch\admin\bar\siard2\cmd\PrimaryDataFromDb.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */