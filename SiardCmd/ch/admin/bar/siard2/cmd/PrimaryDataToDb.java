package ch.admin.bar.siard2.cmd;
import ch.admin.bar.siard2.api.Archive;
import ch.admin.bar.siard2.api.Cell;
import ch.admin.bar.siard2.api.Field;
import ch.admin.bar.siard2.api.MetaColumn;
import ch.admin.bar.siard2.api.MetaData;
import ch.admin.bar.siard2.api.MetaField;
import ch.admin.bar.siard2.api.MetaForeignKey;
import ch.admin.bar.siard2.api.MetaSchema;
import ch.admin.bar.siard2.api.MetaTable;
import ch.admin.bar.siard2.api.MetaType;
import ch.admin.bar.siard2.api.MetaUniqueKey;
import ch.admin.bar.siard2.api.Record;
import ch.admin.bar.siard2.api.RecordDispenser;
import ch.admin.bar.siard2.api.Schema;
import ch.admin.bar.siard2.api.Table;
import ch.admin.bar.siard2.api.Value;
import ch.admin.bar.siard2.api.generated.CategoryType;
import ch.enterag.sqlparser.identifier.QualifiedId;
import ch.enterag.utils.EU;
import ch.enterag.utils.StopWatch;
import ch.enterag.utils.background.Progress;
import ch.enterag.utils.jdbc.BaseConnection;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.NClob;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.SQLXML;
import java.sql.Statement;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.slf4j.Logger;

public class PrimaryDataToDb extends PrimaryDataTransfer {
  private static final Logger LOG = LoggerFactory.getLogger(PrimaryDataToDb.class);
  
  private static final int iBUFFER_SIZE = 8192;
  
  private static final long lCOMMIT_RECORDS = 1000L;
  private Progress _progress = null;
  private long _lRecordsUploaded = -1L;
  private long _lRecordsTotal = -1L;
  private long _lRecordsPercent = -1L;





  
  private void incUploaded() {
    this._lRecordsUploaded++;
    if (this._progress != null && this._lRecordsTotal > 0L && this._lRecordsUploaded % this._lRecordsPercent == 0L) {
      
      int iPercent = (int)(100L * this._lRecordsUploaded / this._lRecordsTotal);
      this._progress.notifyProgress(iPercent);
    } 
  }



  
  private boolean cancelRequested() {
    if (this._progress != null && this._progress.cancelRequested()) {
      LOG.info("Cancel uploading of primary data because of request");
      return true;
    } 
    return false;
  }


  
  private void copyFromReaderToWriter(Reader rdr, Writer wr) throws IOException {
    char[] cbuf = new char[8192]; int iRead;
    for (iRead = rdr.read(cbuf); iRead != -1; iRead = rdr.read(cbuf))
      wr.write(cbuf, 0, iRead); 
    rdr.close();
    wr.close();
  }


  
  private void copyFromInputToOutput(InputStream is, OutputStream os) throws IOException {
    byte[] buf = new byte[8192]; int iRead;
    for (iRead = is.read(buf); iRead != -1; iRead = is.read(buf))
      os.write(buf, 0, iRead); 
    is.close();
    os.close();
  }


  
  private void freeResources(Set<Object> setResources) throws SQLException {
    for (Iterator<Object> iterResource = setResources.iterator(); iterResource.hasNext(); ) {
      
      Object oResource = iterResource.next();
      if (oResource instanceof Clob) {
        ((Clob)oResource).free(); continue;
      }  if (oResource instanceof NClob) {
        ((NClob)oResource).free(); continue;
      }  if (oResource instanceof SQLXML) {
        ((SQLXML)oResource).free(); continue;
      }  if (oResource instanceof Blob) {
        ((Blob)oResource).free(); continue;
      }  if (oResource instanceof Array) {
        ((Array)oResource).free();
      }
    } 
  }

  
  public void addCandidateKeys(Connection conn, MetaTable mt) throws SQLException {
    if (mt.getMetaCandidateKeys() > 0) {
      
      SchemaMapping sm = this._am.getSchemaMapping(mt.getParentMetaSchema().getName());
      TableMapping tm = sm.getTableMapping(mt.getName());
      QualifiedId qiTable = new QualifiedId(null, sm.getMappedSchemaName(), tm.getMappedTableName());
      String sSql = "ALTER TABLE " + qiTable.format();
      for (int iCandidateKey = 0; iCandidateKey < mt.getMetaCandidateKeys(); iCandidateKey++) {
        
        MetaUniqueKey mck = mt.getMetaCandidateKey(iCandidateKey);
        StringBuilder sbSql = new StringBuilder(sSql + " ADD CONSTRAINT " + mck.getName() + " UNIQUE(");
        for (int iColumn = 0; iColumn < mck.getColumns(); iColumn++) {
          
          if (iColumn > 0)
            sbSql.append(","); 
          String sMappedColumnName = tm.getMappedColumnName(mck.getColumn(iColumn));
          sbSql.append(SqlLiterals.formatId(sMappedColumnName));
        } 
        sbSql.append(")");
        
        String sqlStatement = sbSql.toString();
        LOG.trace("SQL statement: '{}'", sqlStatement);
        
        Statement stmt = conn.createStatement();
        stmt.setQueryTimeout(this._iQueryTimeoutSeconds);
        stmt.execute(sbSql.toString());
        stmt.close();
        
        LOG.debug("Candidate key '{}' (table '{}.{}') successfully created", new Object[] { mck
              .getName(), sm
              .getMappedSchemaName(), tm
              .getMappedTableName() });
      } 
    } 
  }


  
  public void addForeignKeys(Connection conn, MetaTable mt) throws SQLException {
    if (mt.getMetaForeignKeys() > 0) {
      
      SchemaMapping sm = this._am.getSchemaMapping(mt.getParentMetaSchema().getName());
      TableMapping tm = sm.getTableMapping(mt.getName());
      QualifiedId qiTable = new QualifiedId(null, sm.getMappedSchemaName(), tm.getMappedTableName());
      String sSql = "ALTER TABLE " + qiTable.format();
      for (int iForeignKey = 0; iForeignKey < mt.getMetaForeignKeys(); iForeignKey++) {
        
        MetaForeignKey mfk = mt.getMetaForeignKey(iForeignKey);
        StringBuilder sbSql = new StringBuilder(sSql + " ADD CONSTRAINT " + mfk.getName() + " FOREIGN KEY(");
        SchemaMapping smReferenced = sm;
        if (mfk.getReferencedSchema() != null)
          smReferenced = this._am.getSchemaMapping(mfk.getReferencedSchema()); 
        TableMapping tmReferenced = smReferenced.getTableMapping(mfk.getReferencedTable());
        
        QualifiedId qiReferenced = new QualifiedId(null, smReferenced.getMappedSchemaName(), tmReferenced.getMappedTableName());
        StringBuilder sbReferences = new StringBuilder(" REFERENCES " + qiReferenced.format() + "(");
        for (int iReference = 0; iReference < mfk.getReferences(); iReference++) {
          
          if (iReference > 0) {
            
            sbSql.append(", ");
            sbReferences.append(", ");
          } 
          sbSql.append(tm.getMappedColumnName(mfk.getColumn(iReference)));
          sbReferences.append(tmReferenced.getMappedColumnName(mfk.getReferenced(iReference)));
        } 
        sbSql.append(")");
        sbReferences.append(")");
        sbSql.append(sbReferences.toString());
        
        String sqlStatement = sbSql.toString();
        LOG.trace("SQL statement: '{}'", sqlStatement);
        
        Statement stmt = conn.createStatement();
        stmt.setQueryTimeout(this._iQueryTimeoutSeconds);
        stmt.execute(sqlStatement);
        stmt.close();
        
        LOG.debug("Foreign key '{}' (table '{}.{}') successfully created", new Object[] { mfk
              .getName(), sm
              .getMappedSchemaName(), tm
              .getMappedTableName() });
      } 
    } 
  }

  
  private void enableConstraints(MetaSchema ms) {
    for (int iTable = 0; iTable < ms.getMetaTables() && !cancelRequested(); iTable++) {
      
      MetaTable mt = ms.getMetaTable(iTable); 
      try { addCandidateKeys(this._conn, mt); }
      catch (SQLException se) { System.err.println(EU.getExceptionMessage(se)); }
       try { addForeignKeys(this._conn, mt); }
      catch (SQLException se) { System.err.println(EU.getExceptionMessage(se)); }
    
    } 
  }





  
  private void enableConstraints() {
    MetaData md = this._archive.getMetaData();
    for (int iSchema = 0; iSchema < md.getMetaSchemas(); iSchema++) {
      
      MetaSchema ms = md.getMetaSchema(iSchema);
      enableConstraints(ms);
    } 
  }











  
  private Object getValue(Value value, BaseConnection conn, Set<Object> setResources) throws IOException, SQLException {
    Object o = null;
    if (!value.isNull()) {
      
      String sType = null;
      int iDataType = 0;
      int iCardinality = -1;
      MetaType mt = null;
      if (value instanceof Cell) {
        
        Cell cell = (Cell)value;
        MetaColumn mc = cell.getMetaColumn();
        sType = mc.getType();
        iDataType = mc.getPreType();
        iCardinality = mc.getCardinality();
        mt = mc.getMetaType();
      }
      else if (value instanceof Field) {
        
        Field field = (Field)value;
        MetaField mf = field.getMetaField();
        sType = mf.getType();
        iDataType = mf.getPreType();
        iCardinality = mf.getCardinality();
        mt = mf.getMetaType();
      } 
      CategoryType cat = null;
      if (mt != null)
        cat = mt.getCategoryType(); 
      if ((cat == null || cat == CategoryType.DISTINCT) && iCardinality < 0) {
        Clob clob; SQLXML sqlxml; NClob nclob; Blob blob; Object obj;
        switch (iDataType) {
          
          case -15:
          case -9:
          case 1:
          case 12:
            o = value.getString();
            break;
          case -3:
          case -2:
            o = value.getBytes();
            break;
          case 16:
            o = value.getBoolean();
            break;
          case 4:
          case 5:
            o = value.getLong();
            break;
          case -5:
            o = new BigDecimal(value.getBigInteger());
            break;
          case 2:
          case 3:
            o = value.getBigDecimal();
            break;
          case 7:
            o = value.getFloat();
            break;
          case 6:
          case 8:
            o = value.getDouble();
            break;
          case 91:
            o = value.getDate();
            break;
          case 92:
            o = value.getTime();
            break;
          case 93:
            o = value.getTimestamp();
            break;
          case 1111:
            o = value.getDuration();
            break;
          case 2005:
            clob = conn.createClob();
            copyFromReaderToWriter(value.getReader(), clob.setCharacterStream(1L));
            o = clob;
            setResources.add(o);
            break;
          case 2009:
            sqlxml = conn.createSQLXML();
            copyFromReaderToWriter(value.getReader(), sqlxml.setCharacterStream());
            o = sqlxml;
            setResources.add(o);
            break;
          case 2011:
            nclob = conn.createNClob();
            copyFromReaderToWriter(value.getReader(), nclob.setCharacterStream(1L));
            o = nclob;
            setResources.add(o);
            break;
          case 2004:
            blob = conn.createBlob();
            copyFromInputToOutput(value.getInputStream(), blob.setBinaryStream(1L));
            o = blob;
            setResources.add(o);
            break;
          
          case 70:
            obj = conn.createDatalinkObject();
            if (obj instanceof Blob) {
              copyFromInputToOutput(value.getInputStream(), ((Blob)obj).setBinaryStream(1L));
              setResources.add(obj);
            } 
            o = obj;
            break;
        } 

      
      } else if (iCardinality >= 0) {
        
        Object[] aoValues = new Object[value.getElements()];
        
        for (int iElement = 0; iElement < value.getElements(); iElement++) {
          
          Field field = value.getElement(iElement);
          aoValues[iElement] = getValue((Value)field, conn, setResources);
        } 
        o = conn.createArrayOf(sType, aoValues);
        setResources.add(o);
      }
      else if (cat == CategoryType.UDT) {
        
        SchemaMapping sm = this._am.getSchemaMapping(mt.getParentMetaSchema().getName());

        
        QualifiedId qiType = new QualifiedId(null, sm.getMappedSchemaName(), sm.getMappedTypeName(mt.getName()));
        Object[] aoAttributes = new Object[value.getAttributes()];
        
        for (int iAttribute = 0; iAttribute < value.getAttributes(); iAttribute++) {
          
          Field field = value.getAttribute(iAttribute);
          aoAttributes[iAttribute] = getValue((Value)field, conn, setResources);
        } 
        o = conn.createStruct(qiType.format(), aoAttributes);
        setResources.add(o);
      } 
    } 
    return o;
  }










  
  private void putRecord(Record record, ResultSet rs, Set<Object> setResources) throws IOException, SQLException {
    List<Value> listValues = record.getValues(supportsArrays(), supportsUdts());
    ResultSetMetaData rsmd = rs.getMetaData();
    if (rsmd.getColumnCount() != listValues.size())
      throw new IOException("Invalid number of result columns found!"); 
    for (int iValue = 0; iValue < listValues.size(); iValue++) {
      
      Value value = listValues.get(iValue);
      if (!value.isNull()) {
        
        Object oValue = getValue(value, (BaseConnection)rs.getStatement().getConnection(), setResources);
        rs.updateObject(iValue + 1, oValue);
      } 
    } 
  }









  
  private void putTable(Table table, SchemaMapping sm) throws IOException, SQLException {
    MetaTable mt = table.getMetaTable();

    
    QualifiedId qiTable = new QualifiedId(null, mt.getParentMetaSchema().getName(), mt.getName());
    System.out.println("  Table: " + qiTable.format());
    RecordDispenser rd = table.openRecords();
    ResultSet rs = openTable(table, sm);
    Statement stmt = rs.getStatement();
    Set<Object> setResources = new HashSet();
    long lRecord = 0L;
    Record record = null;
    StopWatch sw = StopWatch.getInstance();
    sw.start();
    long lBytesStart = rd.getByteCount();
    while (lRecord < mt.getRows() && !cancelRequested()) {
      
      record = rd.get();
      setResources.clear();
      rs.moveToInsertRow();
      putRecord(record, rs, setResources);
      rs.insertRow();
      freeResources(setResources);
      rs.moveToCurrentRow();
      lRecord++;
      if (lRecord % 1000L == 0L) {
        
        System.out.println("    Record " + String.valueOf(lRecord) + " (" + sw.formatRate(rd.getByteCount() - lBytesStart, sw.stop()) + " kB/s)");
        lBytesStart = rd.getByteCount();
        sw.start();
      } 
      incUploaded();
    } 
    System.out.println("    Record " + String.valueOf(lRecord) + " (" + sw.formatRate(rd.getByteCount() - lBytesStart, sw.stop()) + " kB/s)");
    System.out.println("    Total: " + StopWatch.formatLong(lRecord) + " records (" + StopWatch.formatLong(rd.getByteCount()) + " bytes in " + sw.formatMs() + " ms)");
    if (!rs.isClosed())
      rs.close(); 
    if (!stmt.isClosed())
      stmt.close(); 
    rd.close();
    this._conn.commit();
    LOG.debug("Records of table '{}.{}' successfully uploaded", qiTable.getSchema(), qiTable.getName());
  }








  
  private void putSchema(Schema schema) throws IOException, SQLException {
    MetaSchema ms = schema.getMetaSchema();
    SchemaMapping sm = this._am.getSchemaMapping(ms.getName());
    for (int iTable = 0; iTable < schema.getTables() && !cancelRequested(); iTable++) {
      
      Table table = schema.getTable(iTable);
      putTable(table, sm);
    } 
    this._conn.commit();
    
    LOG.debug("Records of schema '{}' successfully uploaded", ms.getName());
  }







  
  public void upload(Progress progress) throws IOException, SQLException {
    LOG.info("Start primary data upload of archive {}", this._archive
        .getFile().getAbsoluteFile());
    
    System.out.println("\r\nPrimary Data");
    this._progress = progress;
    
    this._lRecordsTotal = 0L; int iSchema;
    for (iSchema = 0; iSchema < this._archive.getSchemas(); iSchema++) {
      
      Schema schema = this._archive.getSchema(iSchema);
      for (int iTable = 0; iTable < schema.getTables(); iTable++) {
        
        Table table = schema.getTable(iTable);
        this._lRecordsTotal += table.getMetaTable().getRows();
      } 
    } 
    this._lRecordsPercent = (this._lRecordsTotal + 99L) / 100L;
    this._lRecordsUploaded = 0L;
    
    for (iSchema = 0; iSchema < this._archive.getSchemas() && !cancelRequested(); iSchema++) {
      
      Schema schema = this._archive.getSchema(iSchema);
      putSchema(schema);
    } 
    if (!cancelRequested())
      enableConstraints(); 
    if (cancelRequested())
      throw new IOException("\r\nUpload of primary data cancelled!"); 
    System.out.println("\r\nUpload terminated successfully.");
    this._conn.commit();
    
    LOG.info("Primary data upload finished");
  }













  
  private PrimaryDataToDb(Connection conn, Archive archive, ArchiveMapping am, boolean bSupportsArrays, boolean bSupportsDistincts, boolean bSupportsUdts) throws SQLException {
    super(conn, archive, am, bSupportsArrays, bSupportsDistincts, bSupportsUdts);
    conn.setAutoCommit(false);
  }














  
  public static PrimaryDataToDb newInstance(Connection conn, Archive archive, ArchiveMapping am, boolean bSupportsArrays, boolean bSupportsDistincts, boolean bSupportsUdts) throws SQLException {
    return new PrimaryDataToDb(conn, archive, am, bSupportsArrays, bSupportsDistincts, bSupportsUdts);
  }
}


/* Location:              C:\Users\lenovo\IdeaProjects\SIARD_KR_2.2\lib\siardcmd.jar!\ch\admin\bar\siard2\cmd\PrimaryDataToDb.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */