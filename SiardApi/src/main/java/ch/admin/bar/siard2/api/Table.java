package ch.admin.bar.siard2.api;

import ch.admin.bar.siard2.api.ext.SftpConnection;
import ch.admin.bar.siard2.api.primary.LobReader;
import ch.enterag.utils.background.Progress;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public interface Table extends Search {
    Schema getParentSchema();

    MetaTable getMetaTable();

    void exportTableSchema(OutputStream paramOutputStream) throws IOException;

    boolean isValid();

    boolean isEmpty();

    RecordDispenser openRecords() throws IOException;

    RecordDispenser openRecords(LobReader lobReader) throws IOException;

    RecordRetainer createRecords() throws IOException;

    RecordExtract getRecordExtract() throws IOException;

    void sort(boolean paramBoolean, int paramInt, Progress paramProgress) throws IOException;

    boolean getAscending();

    int getSortColumn();

    void exportAsHtml(OutputStream paramOutputStream, File paramFile) throws IOException;

    void setTableSize(long byteCount);

    long getTableSize();

    void setFormattedTableSize(String formattedTableSize);

    String getFormattedTableSize();

    SftpConnection getSftpConnection();

    void setSftpConnection(SftpConnection sftpConnection);

    long getMatchedRows();

    void updateMatchedRows();

    void setSearchAction();

    boolean isSearchAction();

    long getCalculatedRows();

    void setRowsToDefault();
}
