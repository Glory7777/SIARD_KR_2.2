//package ch.admin.bar.siardsuite.service.database;
//
//import ch.admin.bar.siard2.api.*;
//import ch.admin.bar.siard2.api.Record;
//import ch.admin.bar.siard2.api.generated.CategoryType;
//import ch.admin.bar.siard2.cmd.*;
//import ch.admin.bar.siardsuite.ui.presenter.archive.model.CustomArchiveProxy;
//import ch.enterag.sqlparser.identifier.QualifiedId;
//import ch.enterag.utils.StopWatch;
//import ch.enterag.utils.background.Progress;
//import org.apache.tika.Tika;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import javax.xml.datatype.Duration;
//import java.io.IOException;
//import java.io.PrintStream;
//import java.math.BigDecimal;
//import java.math.BigInteger;
//import java.net.URL;
//import java.sql.*;
//import java.util.List;
//import java.util.Map;
//
//// TODO:: 멀티 모듈로 라이브러리 수정 또는 바이트 조작으로 접근해야 함
//public class TempPrimaryDataFromDb extends PrimaryDataTransfer{
//
//    private static final Logger LOG = LoggerFactory.getLogger(TempPrimaryDataFromDb.class);
//    private static final long REPORT_RECORDS = 1000L;
//    private Progress progress = null;
//    private long recordsDownloaded = -1L;
//    private long recordsTotal = -1L;
//    private long recordsPercent = -1L;
//    private StopWatch getCellStopWatch = null;
//    private StopWatch getValueStopWatch = null;
//    private StopWatch setValueStopWatch = null;
//    private final Tika tika = new Tika();
//
//    private TempPrimaryDataFromDb(Connection connection, Archive archive) {
//        super(connection, archive, (ArchiveMapping)null, true, true, true);
//    }
//
//    public static TempPrimaryDataFromDb newInstance(Connection connection, Archive archive) {
//        System.out.println("instantiate");
//        return new TempPrimaryDataFromDb(connection, archive);
//    }
//
//    public void download(Progress progress) throws IOException, SQLException {
//        LOG.info("Start primary data download to archive {}", this._archive.getFile().getAbsoluteFile());
//        System.out.println("\r\nPrimary Data");
//        this.progress = progress;
//        this.recordsTotal = 0L;
//
//        Archive archive = this._archive;
////                instanceof CustomArchiveProxy ? (CustomArchiveProxy) this._archive : this._archive;
//
////        for(iSchema = 0; iSchema < this._archive.getSchemas(); ++iSchema) {
////            Schema schema = this._archive.getSchema(iSchema);
////
////            for(int iTable = 0; iTable < schema.getTables(); ++iTable) {
////                this.recordsTotal += schema.getTable(iTable).getMetaTable().getRows();
////            }
////        }
//
//
//        if (this._archive instanceof CustomArchiveProxy) {
//            CustomArchiveProxy proxy = (CustomArchiveProxy) this._archive;
//
//            int iSchema;
//
//            proxy.getSchemaTableMap().forEach(
//                    (schema, tables) -> tables.forEach(
//                            t -> this.recordsTotal += t.getMetaTable().getRows()
//                    )
//            );
//
//            this.recordsPercent = (this.recordsTotal + 99L) / 100L;
//            this.recordsDownloaded = 0L;
//
////            for(iSchema = 0; iSchema < this._archive.getSchemas() && !this.cancelRequested(); ++iSchema) {
////                this.getSchema(this._archive.getSchema(iSchema));
////            }
//
//            int schemas = proxy.getSchemas();
//
//            // 테이블 조회
//            for (Map.Entry<Schema, List<Table>> entry : proxy.getSchemaTableMap().entrySet()) {
//                if (this.cancelRequested()) break;
//                Schema schema = entry.getKey();
//                List<Table> table = entry.getValue();
//                for (Table table1 : table) {
//                    Schema tempSchema = TempSchemaImpl.newInstance(proxy.getDelegate(), schema.getMetaSchema().getName());
//                    MetaTable metaTable = table1.getMetaTable();
//                    String name = metaTable.getName();
//                    Table ti = CustomTableImpl.newInstance(tempSchema, name);
//                    tryGetTable(ti);
//                }
//            }
//        } else {
//
//            int iSchema;
//
//            for(iSchema = 0; iSchema < archive.getSchemas(); ++iSchema) {
//                Schema schema = archive.getSchema(iSchema);
//
//                for(int iTable = 0; iTable < schema.getTables(); ++iTable) {
//                    this.recordsTotal += schema.getTable(iTable).getMetaTable().getRows();
//                }
//            }
//
//            this.recordsPercent = (this.recordsTotal + 99L) / 100L;
//            this.recordsDownloaded = 0L;
//
//            for(iSchema = 0; iSchema < archive.getSchemas() && !this.cancelRequested(); ++iSchema) {
//                this.getSchema(archive.getSchema(iSchema));
//            }
//        }
//
//        if (this.cancelRequested()) {
//            throw new IOException("\r\nDownload of primary data cancelled!");
//        } else {
//            System.out.println("\r\nDownload terminated successfully.");
//            this._conn.rollback();
//            LOG.info("Primary data download finished");
//        }
//    }
//
//    private void tryGetTable(Table table) {
//        try {
//            this.getTable(table);
//        } catch (IOException | SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    private void incDownloaded() {
//        ++this.recordsDownloaded;
//        if (this.progress != null && this.recordsTotal > 0L && this.recordsDownloaded % this.recordsPercent == 0L) {
//            int iPercent = (int)(100L * this.recordsDownloaded / this.recordsTotal);
//            this.progress.notifyProgress(iPercent);
//        }
//
//    }
//
//    private boolean cancelRequested() {
//        if (this.progress != null && this.progress.cancelRequested()) {
//            LOG.info("Cancel downloading of primary data because of request");
//            return true;
//        } else {
//            return false;
//        }
//    }
//
//    private void setValue(Value value, Object oValue, MimeTypeHandler mimeTypeHandler) throws IOException, SQLException {
//        if (oValue != null) {
//            if (oValue instanceof String) {
//                value.setString((String)oValue);
//            } else if (oValue instanceof byte[]) {
//                byte[] bytes = (byte[])oValue;
//                mimeTypeHandler.add(value, bytes);
//                value.setBytes(bytes);
//            } else if (oValue instanceof Boolean) {
//                value.setBoolean((Boolean)oValue);
//            } else if (oValue instanceof Short) {
//                value.setShort((Short)oValue);
//            } else if (oValue instanceof Integer) {
//                value.setInt((Integer)oValue);
//            } else if (oValue instanceof Long) {
//                value.setLong((Long)oValue);
//            } else if (oValue instanceof BigInteger) {
//                value.setBigInteger((BigInteger)oValue);
//            } else if (oValue instanceof BigDecimal) {
//                value.setBigDecimal((BigDecimal)oValue);
//            } else if (oValue instanceof Float) {
//                value.setFloat((Float)oValue);
//            } else if (oValue instanceof Double) {
//                value.setDouble((Double)oValue);
//            } else if (oValue instanceof Timestamp) {
//                value.setTimestamp((Timestamp)oValue);
//            } else if (oValue instanceof Time) {
//                value.setTime((Time)oValue);
//            } else if (oValue instanceof Date) {
//                value.setDate((Date)oValue);
//            } else if (oValue instanceof Duration) {
//                value.setDuration((Duration)oValue);
//            } else if (oValue instanceof Clob) {
//                Clob clob = (Clob)oValue;
//                mimeTypeHandler.add(value, clob);
//                value.setReader(clob.getCharacterStream());
//                clob.free();
//            } else if (oValue instanceof SQLXML) {
//                SQLXML sqlxml = (SQLXML)oValue;
//                value.setReader(sqlxml.getCharacterStream());
//                sqlxml.free();
//            } else if (oValue instanceof Blob) {
//                Blob blob = (Blob)oValue;
//                mimeTypeHandler.add(value, blob);
//                value.setInputStream(blob.getBinaryStream());
//                blob.free();
//            } else if (oValue instanceof URL) {
//                URL url = (URL)oValue;
//                value.setInputStream(url.openStream(), url.getPath());
//            } else {
//                int iAttribute;
//                Object[] ao;
//                if (oValue instanceof Array) {
//                    Array array = (Array)oValue;
//                    ao = (Object[])array.getArray();
//
//                    for(iAttribute = 0; iAttribute < ao.length; ++iAttribute) {
//                        Value valueElement = value.getElement(iAttribute);
//                        this.setValue((Value)valueElement, (Object)ao[iAttribute], (MimeTypeHandler)mimeTypeHandler);
//                        mimeTypeHandler.applyMimeType(valueElement);
//                    }
//
//                    array.free();
//                } else {
//                    if (!(oValue instanceof Struct)) {
//                        throw new SQLException("Invalid value type " + oValue.getClass().getName() + " encountered!");
//                    }
//
//                    Struct struct = (Struct)oValue;
//                    ao = struct.getAttributes();
//
//                    for(iAttribute = 0; iAttribute < ao.length; ++iAttribute) {
//                        this.setValue((Value)value.getAttribute(iAttribute), (Object)ao[iAttribute], (MimeTypeHandler)mimeTypeHandler);
//                    }
//                }
//            }
//        }
//
//    }
//
//    private void getRecord(ResultSet rs, Record record, MimeTypeHandler mimeTypeHandler) throws IOException, SQLException {
//        if (rs.getMetaData().getColumnCount() != record.getCells()) {
//            throw new IOException("Invalid number of result columns found!");
//        } else {
//            for(int cellIndex = 0; cellIndex < record.getCells(); ++cellIndex) {
//                Cell cell = this.getCell(record, cellIndex);
//                Object oValue = this.getValue(rs, cell, cellIndex);
//                this.setValue(mimeTypeHandler, cell, oValue);
//            }
//
//        }
//    }
//
//    private Cell getCell(Record record, int cellIndex) throws IOException {
//        this.getCellStopWatch.start();
//        Cell cell = record.getCell(cellIndex);
//        this.getCellStopWatch.stop();
//        return cell;
//    }
//
//    private Object getValue(ResultSet rs, Cell cell, int cellIndex) throws SQLException, IOException {
//        this.getValueStopWatch.start();
//        Object oValue = (new ObjectValueReader(rs, this.getDataType(cell.getMetaColumn()), cellIndex + 1)).read();
//        if (rs.wasNull()) {
//            oValue = null;
//        }
//
//        this.getValueStopWatch.stop();
//        return oValue;
//    }
//
//    private void setValue(MimeTypeHandler mimeTypeHandler, Cell cell, Object oValue) throws IOException, SQLException {
//        this.setValueStopWatch.start();
//        this.setValue((Value)cell, (Object)oValue, (MimeTypeHandler)mimeTypeHandler);
//        mimeTypeHandler.applyMimeType(cell);
//        this.setValueStopWatch.stop();
//    }
//
//    private int getDataType(MetaColumn mc) throws IOException {
//        int iDataType = mc.getPreType();
//        if (mc.getCardinality() >= 0) {
//            iDataType = 2003;
//        }
//
//        MetaType mt = mc.getMetaType();
//        if (mt != null) {
//            CategoryType cat = mt.getCategoryType();
//            if (cat == CategoryType.DISTINCT) {
//                iDataType = mt.getBasePreType();
//            } else {
//                iDataType = 2002;
//            }
//        }
//
//        return iDataType;
//    }
//
//    private void getTable(Table table) throws IOException, SQLException {
//        this.getCellStopWatch = StopWatch.getInstance();
//        this.getValueStopWatch = StopWatch.getInstance();
//        this.setValueStopWatch = StopWatch.getInstance();
//        QualifiedId qiTable = new QualifiedId((String)null, table.getParentSchema().getMetaSchema().getName(), table.getMetaTable().getName());
//        System.out.println("  Table: " + qiTable.format());
//        long lRecord = 0L;
//        RecordRetainer rr = table.createRecords();
//        ResultSet rs = this.openTable(table, (SchemaMapping)null);
//        Statement stmt = rs.getStatement();
//        StopWatch swCreate = StopWatch.getInstance();
//        StopWatch swGet = StopWatch.getInstance();
//        StopWatch swPut = StopWatch.getInstance();
//        StopWatch sw = StopWatch.getInstance();
//        sw.start();
//        long lBytesStart = rr.getByteCount();
//        MimeTypeHandler mimeTypeHandler = new MimeTypeHandler(this.tika);
//
//        while(rs.next() && !this.cancelRequested()) {
//            Record record = createRecord(swCreate, rr);
//            this.readRecord(swGet, rs, record, mimeTypeHandler);
//            putRecord(swPut, rr, record);
//            lBytesStart = logRecordProgress(lRecord++, sw, rr, lBytesStart);
//            this.incDownloaded();
//        }
//
//        System.out.println("    Record " + lRecord + " (" + sw.formatRate(rr.getByteCount() - lBytesStart, sw.stop()) + " kB/s)");
//        PrintStream var16 = System.out;
//        String var10001 = StopWatch.formatLong(lRecord);
//        var16.println("    Total: " + var10001 + " records (" + StopWatch.formatLong(rr.getByteCount()) + " bytes in " + sw.formatMs() + " ms)");
//        if (!rs.isClosed()) {
//            rs.close();
//        }
//
//        if (!stmt.isClosed()) {
//            stmt.close();
//        }
//
//        rr.close();
//        LOG.debug("All data of table '{}.{}' successfully downloaded", qiTable.getSchema(), qiTable.getName());
//    }
//
//    private static long logRecordProgress(long lRecord, StopWatch sw, RecordRetainer rr, long lBytesStart) {
//        if (lRecord % 1000L == 0L) {
//            System.out.println("    Record " + lRecord + " (" + sw.formatRate(rr.getByteCount() - lBytesStart, sw.stop()) + " kB/s)");
//            lBytesStart = rr.getByteCount();
//            sw.start();
//        }
//
//        return lBytesStart;
//    }
//
//    private static void putRecord(StopWatch swPut, RecordRetainer rr, Record record) throws IOException {
//        swPut.start();
//        rr.put(record);
//        swPut.stop();
//    }
//
//    private void readRecord(StopWatch swGet, ResultSet rs, Record record, MimeTypeHandler mimeTypeHandler) throws IOException, SQLException {
//        swGet.start();
//        this.getRecord(rs, record, mimeTypeHandler);
//        swGet.stop();
//    }
//
//    private static Record createRecord(StopWatch swCreate, RecordRetainer rr) throws IOException {
//        swCreate.start();
//        Record record = rr.create();
//        swCreate.stop();
//        return record;
//    }
//
//    private void getSchema(Schema schema) throws IOException, SQLException {
//        for(int iTable = 0; iTable < schema.getTables() && !this.cancelRequested(); ++iTable) {
//            Table table = schema.getTable(iTable);
//            this.getTable(table);
//        }
//
//        LOG.debug("All data of schema '{}' successfully downloaded", schema.getMetaSchema().getName());
//    }
//}
