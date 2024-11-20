package com.tmax.tibero.jdbc.driver;

import com.tmax.tibero.jdbc.data.*;
import com.tmax.tibero.jdbc.err.TbError;

import java.io.InputStream;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.SQLWarning;

public abstract class TbResultSet implements com.tmax.tibero.jdbc.TbResultSet {
    protected SQLWarning warnings = null;

    protected RsetType rsetType = RsetType.FWRD;

    protected int rowsFetchedCnt = 0;

    protected boolean haveLocator = false;

    protected long tsn = 0L;

    protected TbResultSet(RsetType paramRsetType) {
        if (paramRsetType != null)
            this.rsetType = paramRsetType.getCopy();
    }

    public synchronized void addWarning(SQLWarning paramSQLWarning) {
        if (this.warnings != null) {
            this.warnings.setNextWarning(paramSQLWarning);
        } else {
            this.warnings = paramSQLWarning;
        }
    }

    public abstract void buildRowTable(int paramInt, byte[] paramArrayOfbyte) throws SQLException;

    public synchronized void clearWarnings() throws SQLException {
        this.warnings = null;
    }

    public int getBytes(int paramInt, byte[] paramArrayOfbyte) throws SQLException {
        throw TbError.newSQLException(-90201);
    }

    public abstract TbRAW getRAW(int paramInt) throws SQLException;

    public abstract TbRAW getRAW(String paramString) throws SQLException;

    public abstract Column[] getCols() throws SQLException;

    protected abstract int getColumnDataType(int paramInt) throws SQLException;

    protected abstract int getColumnMaxLength(int paramInt) throws SQLException;

    protected abstract String getColumnName(int paramInt) throws SQLException;

    protected abstract boolean getColumnNullable(int paramInt) throws SQLException;

    protected abstract int getColumnPrecision(int paramInt) throws SQLException;

    protected abstract int getColumnScale(int paramInt) throws SQLException;

    protected abstract int getColumnSqlType(int paramInt) throws SQLException;

    public int getConcurrency() throws SQLException {
        return this.rsetType.getConcurrency();
    }

    public int getHoldability() throws SQLException {
        return this.rsetType.getHoldability();
    }

    public InputStream getLongByteStream(int paramInt) throws SQLException {
        throw TbError.newSQLException(-90201);
    }

    public abstract ResultSetMetaData getMetaData() throws SQLException;

    public abstract byte[] getRowChunk(int paramInt) throws SQLException;

    public RsetType getRsetType() {
        return this.rsetType;
    }

    protected long getTsn() {
        return this.tsn;
    }

    public int getType() throws SQLException {
        return this.rsetType.getType();
    }

    public long getUpdateCount() {
        return this.rowsFetchedCnt;
    }

    public abstract TbDate getTbDate(int paramInt) throws SQLException;

    public abstract TbTimestamp getTbTimestamp(int paramInt) throws SQLException;

    public abstract void updateTbTimestamp(int paramInt, TbTimestamp paramTbTimestamp) throws SQLException;

    public abstract void updateTbTimestamp(String paramString, TbTimestamp paramTbTimestamp) throws SQLException;

    public synchronized SQLWarning getWarnings() throws SQLException {
        return this.warnings;
    }

    public boolean isWrapperFor(Class<?> paramClass) throws SQLException {
        return paramClass.isInstance(this);
    }

    protected void reset() {
        this.warnings = null;
        this.rsetType = null;
        this.rowsFetchedCnt = 0;
        this.haveLocator = true;
    }

    public abstract void setFetchCompleted(int paramInt) throws SQLException;

    public void setHaveLocator(boolean paramBoolean) {
        this.haveLocator = paramBoolean;
    }

    public void setRsetType(RsetType paramRsetType) {
        this.rsetType = paramRsetType;
    }

    public void setTsn(long paramLong) {
        this.tsn = paramLong;
    }

    public <T> T unwrap(Class<T> paramClass) throws SQLException {
        try {
            return paramClass.cast(this);
        } catch (ClassCastException classCastException) {
            throw TbError.newSQLException(-90657);
        }
    }
}
