package ch.admin.bar.siard2.cmd;

import ch.admin.bar.siard2.api.Record;
import ch.admin.bar.siard2.api.*;
import ch.admin.bar.siard2.api.ext.FileDownloadPathHolder;
import ch.admin.bar.siard2.api.ext.SchemaTableKey;
import ch.admin.bar.siard2.api.ext.SftpConnection;
import ch.admin.bar.siard2.api.ext.SftpSender;
import ch.admin.bar.siard2.api.ext.form.FormData;
import ch.admin.bar.siard2.api.ext.form.FormDataHelper;
import ch.admin.bar.siard2.api.generated.CategoryType;
import ch.enterag.sqlparser.identifier.QualifiedId;
import ch.enterag.utils.StopWatch;
import ch.enterag.utils.background.Progress;
import javafx.application.Platform;
import org.apache.tika.Tika;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.datatype.Duration;
import java.io.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import static ch.admin.bar.siard2.api.ext.SftpSender.buildSftpSender;

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

    private PrimaryDataFromDb(Connection connection, Archive archive) throws SQLException {
        super(connection, archive, null, true, true, true);
    }

    public static PrimaryDataFromDb newInstance(Connection connection, Archive archive) throws SQLException {
        return new PrimaryDataFromDb(connection, archive);
    }

    public void download(Progress progress) throws IOException, SQLException {
        LOG.info("Start primary data download to archive {}", this._archive.getFile().getAbsoluteFile());
        System.out.println("\r\nPrimary Data");
        this.progress = progress;
        this.recordsTotal = 0L;

        countRecords(); //전체 레코드 수 계산
        this.recordsPercent = (this.recordsTotal + 99L) / 100L;
        this.recordsDownloaded = 0L;

        processData(); //데이터 다운로드

        if (this.cancelRequested()) {
            throw new IOException("\r\nDownload of primary data cancelled!");
        } else {
            System.out.println("\r\nDownload terminated successfully.");
            this._conn.rollback();
            LOG.info("Primary data download finished");
        }
    }

//    private void countRecords() {
//        _archive.getSelectedSchemaMap()
//                .forEach((s, schema) -> this.recordsTotal += schema.getRecordCount());
//    }

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
        if (this.progress != null && this.recordsTotal > 0L) {
            int iPercent = (int) (100L * this.recordsDownloaded / this.recordsTotal);
            Platform.runLater(() -> this.progress.notifyProgress(iPercent)); // UI 스레드에서 실행
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
                try {
                    Reader reader;
                    if (isTiberoDb()) {  // 데이터베이스가 TIBERO 일 때만 아래 방식으로 clob 처리
                        // 스트림 상태 확인 및 처리
                        reader = clob.getCharacterStream();
                        if (reader == null || !reader.ready()) {
                            // CLOB 데이터를 문자열로 가져와 새로운 Reader 생성
                            String clobData = clob.getSubString(1, (int) clob.length());
                            reader = new StringReader(clobData);
                        }
                    } else {   // TIBERO 외에 다른 데이터베이스 clob 처리(기존 방식)
                       reader = clob.getCharacterStream();
                    }
                    mimeTypeHandler.add(value, clob);
                    value.setReader(reader);
                    clob.free();
                } catch (IOException e) {
                    throw new IOException("Error handling clob data : ", e);
                } catch (SQLException e) {
                    throw new SQLException("Error accessing clob data : ", e);
                }

            } else if (oValue instanceof SQLXML sqlxml) {
                value.setReader(sqlxml.getCharacterStream());
                sqlxml.free();
            } else if (oValue instanceof Blob blob) {
                try {
                    InputStream inputStream;
                    if (isTiberoDb()) { // 데이터베이스가 TIBERO 일 때만 아래 방식으로 Blob 처리
                        // InputStream 가져오기
                        InputStream originalInputStream = blob.getBinaryStream();
                        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
                        // 바이너리 데이터를 읽어서 복사
                        byte[] temp = new byte[1024];
                        int bytesRead;
                        while ((bytesRead = originalInputStream.read(temp)) != -1) {
                            buffer.write(temp, 0, bytesRead);
                        }
                        // 복사된 데이터를 기반으로 새로운 InputStream 생성
                        byte[] blobBytes = buffer.toByteArray();
                        inputStream = new ByteArrayInputStream(blobBytes);
                        originalInputStream.close(); // 원래 InputStream 닫기
                    } else {  // TIBERO 외에 다른 데이터베이스 Blob 처리(기존 방식)
                        inputStream = blob.getBinaryStream();
                    }
                    mimeTypeHandler.add(value, blob);
                    value.setInputStream(inputStream);
                    blob.free();
                } catch (IOException e) {
                    throw new IOException("Error handling blob data : ", e);
                } catch (SQLException e) {
                    throw new SQLException("Error accessing blob data : ", e);
                }
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

        Set<FormData> formDataSet = _archive.getFormDataSet();
        Table parentTable = cell.getParentRecord().getParentTable();
        String schema = parentTable.getParentSchema().getMetaSchema().getName();
        String table = parentTable.getMetaTable().getName();
        SchemaTableKey schemaTableKey = SchemaTableKey.of(schema, table);

        for (FormData formData : formDataSet) {
            FormDataHelper formDataHelper = FormDataHelper.builder()
                    .formData(formData)
                    .defaultTargetDirectory(_archive.getFile().getParent())
                    .schemaTableKey(schemaTableKey)
                    .cell(cell)
                    .oValue(oValue)
                    .build();
            formDataHelper.send();
        }
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

    private int tablesProcessed = 0; // 현재까지 처리된 테이블 수
    private int totalTables = 0;     // 전체 테이블 수

    private void countRecords() {
        _archive.getSelectedSchemaMap().forEach((schemaName, schema) -> {
            this.recordsTotal += schema.getRecordCount(); // 총 레코드 수 계산
            this.totalTables += schema.getSelectedTables().size(); // 전체 테이블 수 계산
        });
        LOG.info("Total records: {}, Total tables: {}", recordsTotal, totalTables);
    }

//    public void download(Progress progress) throws IOException, SQLException {
//        LOG.info("Starting data download...");
//        this.progress = progress;
//
//        // 전체 테이블 및 레코드 수 계산
//        countRecords();
//
//        // 데이터 처리 시작
//        processData();
//
//        if (this.cancelRequested()) {
//            throw new IOException("Download cancelled by user.");
//        }
//        LOG.info("Data download completed successfully.");
//    }

    private void getTable(Table table) throws IOException, SQLException {
        long lRecord = 0L;
        RecordRetainer rr = table.createRecords();
        ResultSet rs = null;
        Statement stmt = null;

        try {
            rs = this.openTable(table, null);
            stmt = rs.getStatement();

            while (rs.next() && !this.cancelRequested()) {
                try {
                    Record record = rr.create();
                    lRecord++;
                } catch (Exception e) {
                    LOG.error("Error processing record {} in table '{}': {}", lRecord, table.getMetaTable().getName(), e.getMessage());
                    continue;
                }
            }

            LOG.info("Finished processing table '{}': {} records downloaded", table.getMetaTable().getName(), lRecord);
        } finally {
            // ResultSet과 Statement를 각각 닫아야함
            if (rs != null && !rs.isClosed()) {
                rs.close();
            }
            if (stmt != null && !stmt.isClosed()) {
                stmt.close();
            }
            rr.close(); // RecordRetainer는 테이블 처리 완료 후 닫음
        }

        // 테이블 단위로 프로그레스 업데이트
        Platform.runLater(() -> {
            if (this.progress != null && this.totalTables > 0) {
                int progressValue = (int) ((++this.tablesProcessed / (double) this.totalTables) * 100);
                this.progress.notifyProgress(progressValue);
            }
        });
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
