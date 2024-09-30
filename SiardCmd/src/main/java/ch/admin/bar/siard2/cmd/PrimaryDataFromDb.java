package ch.admin.bar.siard2.cmd;

import ch.admin.bar.siard2.api.Archive;
import ch.admin.bar.siard2.api.Cell;
import ch.admin.bar.siard2.api.MetaColumn;
import ch.admin.bar.siard2.api.MetaType;
import ch.admin.bar.siard2.api.Record;
import ch.admin.bar.siard2.api.RecordRetainer;
import ch.admin.bar.siard2.api.Schema;
import ch.admin.bar.siard2.api.Table;
import ch.admin.bar.siard2.api.Value;
import ch.admin.bar.siard2.api.generated.CategoryType;
import ch.enterag.sqlparser.identifier.QualifiedId;
import ch.enterag.utils.StopWatch;
import ch.enterag.utils.background.Progress;

import java.io.IOException;
import java.math.BigDecimal;
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
import java.sql.Statement;
import java.sql.Struct;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import javax.xml.datatype.Duration;

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
        System.out.println("instantiate");
        return new PrimaryDataFromDb(connection, archive);
    }

    public void download(Progress progress) throws IOException, SQLException {
        LOG.info("Start primary data download to archive {}", this._archive.getFile().getAbsoluteFile());
        System.out.println("\r\nPrimary Data");
        this.progress = progress;
        this.recordsTotal = 0L;

        countRecords();

        this.recordsPercent = (this.recordsTotal + 99L) / 100L;
        this.recordsDownloaded = 0L;

        processData();

        if (this.cancelRequested()) {
            throw new IOException("\r\nDownload of primary data cancelled!");
        } else {
            System.out.println("\r\nDownload terminated successfully.");
            this._conn.rollback();
            LOG.info("Primary data download finished");
        }
    }

    private void countRecords() {
        _archive.getSelectedSchemaMap()
                .forEach((s, schema) -> this.recordsTotal += schema.getRecordCount());
    }

    private void processData() {
        Map<String, Schema> map = _archive.getSelectedSchemaMap().isEmpty() ? _archive.getSchemaMap() : _archive.getSelectedSchemaMap();
        map.forEach((s, schema) -> this.tryGetSchema(schema));
    }

    private void tryGetSchema(Schema schema) {
        try {
            this.getSchema(schema);
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void tryGetTable(Table table) {
        try {
            this.getTable(table);
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void incDownloaded() {
        ++this.recordsDownloaded;
        if (this.progress != null && this.recordsTotal > 0L && this.recordsDownloaded % this.recordsPercent == 0L) {
            int iPercent = (int) (100L * this.recordsDownloaded / this.recordsTotal);
            this.progress.notifyProgress(iPercent);
        }

    }

    private boolean cancelRequested() {
        if (this.progress != null && this.progress.cancelRequested()) {
            LOG.info("Cancel downloading of primary data because of request");
            return true;
        } else {
            return false;
        }
    }

    private void setValue(Value value, Object oValue, MimeTypeHandler mimeTypeHandler) throws IOException, SQLException {
        if (oValue != null) {
            if (oValue instanceof String) {
                value.setString((String) oValue);
            } else if (oValue instanceof byte[]) {
                byte[] bytes = (byte[]) oValue;
                mimeTypeHandler.add(value, bytes);
                value.setBytes(bytes);
            } else if (oValue instanceof Boolean) {
                value.setBoolean((Boolean) oValue);
            } else if (oValue instanceof Short) {
                value.setShort((Short) oValue);
            } else if (oValue instanceof Integer) {
                value.setInt((Integer) oValue);
            } else if (oValue instanceof Long) {
                value.setLong((Long) oValue);
            } else if (oValue instanceof BigInteger) {
                value.setBigInteger((BigInteger) oValue);
            } else if (oValue instanceof BigDecimal) {
                value.setBigDecimal((BigDecimal) oValue);
            } else if (oValue instanceof Float) {
                value.setFloat((Float) oValue);
            } else if (oValue instanceof Double) {
                value.setDouble((Double) oValue);
            } else if (oValue instanceof Timestamp) {
                value.setTimestamp((Timestamp) oValue);
            } else if (oValue instanceof Time) {
                value.setTime((Time) oValue);
            } else if (oValue instanceof Date) {
                value.setDate((Date) oValue);
            } else if (oValue instanceof Duration) {
                value.setDuration((Duration) oValue);
            } else if (oValue instanceof Clob clob) {
                mimeTypeHandler.add(value, clob);
                value.setReader(clob.getCharacterStream());
                clob.free();
            } else if (oValue instanceof SQLXML sqlxml) {
                value.setReader(sqlxml.getCharacterStream());
                sqlxml.free();
            } else if (oValue instanceof Blob blob) {
                mimeTypeHandler.add(value, blob);
                value.setInputStream(blob.getBinaryStream());
                blob.free();
            } else if (oValue instanceof URL url) {
                value.setInputStream(url.openStream(), url.getPath());
            } else {
                Object[] ao;
                int iAttribute;
                if (oValue instanceof Array array) {
                    ao = (Object[]) array.getArray();

                    for (iAttribute = 0; iAttribute < ao.length; ++iAttribute) {
                        Value valueElement = value.getElement(iAttribute);
                        this.setValue(valueElement, ao[iAttribute], mimeTypeHandler);
                        mimeTypeHandler.applyMimeType(valueElement);
                    }

                    array.free();
                } else {
                    if (!(oValue instanceof Struct struct)) {
                        throw new SQLException("Invalid value type " + oValue.getClass().getName() + " encountered!");
                    }

                    ao = struct.getAttributes();

                    for (iAttribute = 0; iAttribute < ao.length; ++iAttribute) {
                        this.setValue(value.getAttribute(iAttribute), ao[iAttribute], mimeTypeHandler);
                    }
                }
            }
        }

    }

    private void getRecord(ResultSet rs, Record record, MimeTypeHandler mimeTypeHandler) throws IOException, SQLException {
        if (rs.getMetaData().getColumnCount() != record.getCells()) {
            throw new IOException("Invalid number of result columns found!");
        } else {
            for (int cellIndex = 0; cellIndex < record.getCells(); ++cellIndex) {
                Cell cell = this.getCell(record, cellIndex);
                Object oValue = this.getValue(rs, cell, cellIndex);
                this.setValue(mimeTypeHandler, cell, oValue);
            }

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
        Object oValue = (new ObjectValueReader(rs, this.getDataType(cell.getMetaColumn()), cellIndex + 1)).read();
        if (rs.wasNull()) {
            oValue = null;
        }

        this.getValueStopWatch.stop();
        return oValue;
    }

    private void setValue(MimeTypeHandler mimeTypeHandler, Cell cell, Object oValue) throws IOException, SQLException {
        this.setValueStopWatch.start();
        this.setValue(cell, oValue, mimeTypeHandler);
        mimeTypeHandler.applyMimeType(cell);
        this.setValueStopWatch.stop();
    }

    private int getDataType(MetaColumn mc) throws IOException {
        int iDataType = mc.getPreType();
        if (mc.getCardinality() >= 0) {
            iDataType = 2003;
        }

        MetaType mt = mc.getMetaType();
        if (mt != null) {
            CategoryType cat = mt.getCategoryType();
            if (cat == CategoryType.DISTINCT) {
                iDataType = mt.getBasePreType();
            } else {
                iDataType = 2002;
            }
        }

        return iDataType;
    }

    private void getTable(Table table) throws IOException, SQLException {
        this.getCellStopWatch = StopWatch.getInstance();
        this.getValueStopWatch = StopWatch.getInstance();
        this.setValueStopWatch = StopWatch.getInstance();
        QualifiedId qiTable = new QualifiedId(null, table.getParentSchema().getMetaSchema().getName(), table.getMetaTable().getName());
        System.out.println("  Table: " + qiTable.format());
        long lRecord = 0L;
        RecordRetainer rr = table.createRecords();
        ResultSet rs = this.openTable(table, null);
        Statement stmt = rs.getStatement();
        StopWatch swCreate = StopWatch.getInstance();
        StopWatch swGet = StopWatch.getInstance();
        StopWatch swPut = StopWatch.getInstance();
        StopWatch sw = StopWatch.getInstance();
        sw.start();
        long lBytesStart = rr.getByteCount();
        MimeTypeHandler mimeTypeHandler = new MimeTypeHandler(this.tika);

        while (rs.next() && !this.cancelRequested()) {
            Record record = createRecord(swCreate, rr);
            this.readRecord(swGet, rs, record, mimeTypeHandler);
            putRecord(swPut, rr, record);
            lBytesStart = logRecordProgress(lRecord++, sw, rr, lBytesStart);
            this.incDownloaded();
        }

        System.out.println("    Record " + lRecord + " (" + sw.formatRate(rr.getByteCount() - lBytesStart, sw.stop()) + " kB/s)");
        System.out.println("    Total: " + StopWatch.formatLong(lRecord) + " records (" + StopWatch.formatLong(rr.getByteCount()) + " bytes in " + sw.formatMs() + " ms)");
        if (!rs.isClosed()) {
            rs.close();
        }

        if (!stmt.isClosed()) {
            stmt.close();
        }

        rr.close();
        LOG.debug("All data of table '{}.{}' successfully downloaded", qiTable.getSchema(), qiTable.getName());
    }

    private static long logRecordProgress(long lRecord, StopWatch sw, RecordRetainer rr, long lBytesStart) {
        if (lRecord % 1000L == 0L) {
            System.out.println("    Record " + lRecord + " (" + sw.formatRate(rr.getByteCount() - lBytesStart, sw.stop()) + " kB/s)");
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
        this.getRecord(rs, record, mimeTypeHandler);
        swGet.stop();
    }

    private static Record createRecord(StopWatch swCreate, RecordRetainer rr) throws IOException {
        swCreate.start();
        Record record = rr.create();
        swCreate.stop();
        return record;
    }

    private void getSchema(Schema schema) throws IOException, SQLException {
        schema.getSelectedTables().forEach(this::tryGetTable);
        LOG.debug("All data of schema '{}' successfully downloaded", schema.getMetaSchema().getName());
    }
}
