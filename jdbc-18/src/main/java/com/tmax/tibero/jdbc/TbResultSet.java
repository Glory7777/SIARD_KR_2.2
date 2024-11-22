package com.tmax.tibero.jdbc;

import com.tmax.tibero.jdbc.data.Column;
import com.tmax.tibero.jdbc.data.RsetType;
import com.tmax.tibero.jdbc.data.TbDate;
import com.tmax.tibero.jdbc.data.TbRAW;
import com.tmax.tibero.jdbc.data.TbTimestamp;

import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLWarning;

public interface TbResultSet extends ResultSet {
    void addWarning(SQLWarning paramSQLWarning);

    void buildRowTable(int paramInt, byte[] paramArrayOfbyte) throws SQLException;

    int getBytes(int paramInt, byte[] paramArrayOfbyte) throws SQLException;

    TbRAW getRAW(int paramInt) throws SQLException;

    TbRAW getRAW(String paramString) throws SQLException;

    Column[] getCols() throws SQLException;

    InputStream getLongByteStream(int paramInt) throws SQLException;

    byte[] getRowChunk(int paramInt) throws SQLException;

    RsetType getRsetType();

    long getUpdateCount();

    TbDate getTbDate(int paramInt) throws SQLException;

    TbTimestamp getTbTimestamp(int paramInt) throws SQLException;

    void updateRAW(int paramInt, TbRAW paramTbRAW) throws SQLException;

    void updateRAW(String paramString, TbRAW paramTbRAW) throws SQLException;

    void updateTbTimestamp(int paramInt, TbTimestamp paramTbTimestamp) throws SQLException;

    void updateTbTimestamp(String paramString, TbTimestamp paramTbTimestamp) throws SQLException;

    SQLWarning getWarnings() throws SQLException;

    boolean isWrapperFor(Class<?> paramClass) throws SQLException;

    void setFetchCompleted(int paramInt) throws SQLException;

    void setHaveLocator(boolean paramBoolean);

    void setRsetType(RsetType paramRsetType);

    <T> T unwrap(Class<T> paramClass) throws SQLException;
}
