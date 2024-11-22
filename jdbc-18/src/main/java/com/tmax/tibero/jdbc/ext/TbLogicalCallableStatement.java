package com.tmax.tibero.jdbc.ext;

import com.tmax.tibero.jdbc.TbCallableStatement;
import com.tmax.tibero.jdbc.TbResultSet;
import com.tmax.tibero.jdbc.data.*;
import com.tmax.tibero.jdbc.err.TbError;
import com.tmax.tibero.jdbc.msg.TbPivotInfo;

import javax.sql.PooledConnection;
import javax.sql.StatementEvent;
import javax.sql.StatementEventListener;
import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Date;
import java.sql.*;
import java.util.*;

public class TbLogicalCallableStatement extends TbCallableStatement implements TbStatementEventHandler {
    private TbLogicalConnection logicalConn = null;

    private boolean closed = true;

    protected TbCallableStatement physicalStmt = null;

    protected HashMap<StatementEventListener, StatementEventListener> stmtEventMap = null;

    public TbLogicalCallableStatement(TbLogicalConnection paramTbLogicalConnection, TbCallableStatement paramTbCallableStatement) throws SQLException {
        this.logicalConn = paramTbLogicalConnection;
        this.stmtEventMap = this.logicalConn.getStatementEventListeners();
        this.physicalStmt = paramTbCallableStatement;
        if (paramTbCallableStatement.isClosed()) {
            this.closed = true;
            throw TbError.newSQLException(-90658);
        }
        this.closed = false;
    }

    public void addBatch() throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            this.physicalStmt.addBatch();
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public void addBatch(String paramString) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            this.physicalStmt.addBatch(paramString);
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public void addPivotData(byte[] paramArrayOfbyte) {
        if (this.physicalStmt != null)
            this.physicalStmt.addPivotData(paramArrayOfbyte);
    }

    public void cancel() throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            this.physicalStmt.cancel();
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public void clearBatch() throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        this.physicalStmt.clearBatch();
    }

    public void clearParameters() throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            this.physicalStmt.clearParameters();
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public void clearWarnings() throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            this.physicalStmt.clearWarnings();
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public void close() throws SQLException {
        if (isClosed())
            return;
        this.closed = true;
        this.physicalStmt.resetForCache();
        notifyClosedEvent();
    }

    public void closeInternal() throws SQLException {
        if (this.physicalStmt != null)
            this.physicalStmt.closeInternal();
    }

    public boolean execute() throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            return this.physicalStmt.execute();
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public boolean execute(String paramString) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            return this.physicalStmt.execute(paramString);
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public boolean execute(String paramString, int paramInt) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            return this.physicalStmt.execute(paramString, paramInt);
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public boolean execute(String paramString, int[] paramArrayOfint) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            return this.physicalStmt.execute(paramString, paramArrayOfint);
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public boolean execute(String paramString, String[] paramArrayOfString) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            return this.physicalStmt.execute(paramString, paramArrayOfString);
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public int[] executeBatch() throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            return this.physicalStmt.executeBatch();
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public ResultSet executeQuery() throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            return this.physicalStmt.executeQuery();
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public ResultSet executeQuery(String paramString) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            return this.physicalStmt.executeQuery(paramString);
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public int executeUpdate() throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            return this.physicalStmt.executeUpdate();
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public int executeUpdate(String paramString) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            return this.physicalStmt.executeUpdate(paramString);
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public int executeUpdate(String paramString, int paramInt) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            return this.physicalStmt.executeUpdate(paramString, paramInt);
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public int executeUpdate(String paramString, int[] paramArrayOfint) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            return this.physicalStmt.executeUpdate(paramString, paramArrayOfint);
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public int executeUpdate(String paramString, String[] paramArrayOfString) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            return this.physicalStmt.executeUpdate(paramString, paramArrayOfString);
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public Array getArray(int paramInt) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            return this.physicalStmt.getArray(paramInt);
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public Array getArray(String paramString) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            return this.physicalStmt.getArray(paramString);
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public BigDecimal getBigDecimal(int paramInt) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            return this.physicalStmt.getBigDecimal(paramInt);
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    @Deprecated
    public BigDecimal getBigDecimal(int paramInt1, int paramInt2) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            return this.physicalStmt.getBigDecimal(paramInt1, paramInt2);
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public BigDecimal getBigDecimal(String paramString) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            return this.physicalStmt.getBigDecimal(paramString);
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public Blob getBlob(int paramInt) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            return this.physicalStmt.getBlob(paramInt);
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public Blob getBlob(String paramString) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            return this.physicalStmt.getBlob(paramString);
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public boolean getBoolean(int paramInt) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            return this.physicalStmt.getBoolean(paramInt);
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public boolean getBoolean(String paramString) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            return this.physicalStmt.getBoolean(paramString);
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public byte getByte(int paramInt) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            return this.physicalStmt.getByte(paramInt);
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public byte getByte(String paramString) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            return this.physicalStmt.getByte(paramString);
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public byte[] getBytes(int paramInt) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            return this.physicalStmt.getBytes(paramInt);
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public byte[] getBytes(String paramString) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            return this.physicalStmt.getBytes(paramString);
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public TbRAW getRAW(int paramInt) throws SQLException {
        return new TbRAW(getBytes(paramInt));
    }

    public TbRAW getRAW(String paramString) throws SQLException {
        return new TbRAW(getBytes(paramString));
    }

    public Reader getCharacterStream(int paramInt) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            return this.physicalStmt.getCharacterStream(paramInt);
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public Reader getCharacterStream(String paramString) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            return this.physicalStmt.getCharacterStream(paramString);
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public Clob getClob(int paramInt) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            return this.physicalStmt.getClob(paramInt);
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public Clob getClob(String paramString) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            return this.physicalStmt.getClob(paramString);
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public Connection getConnection() throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        return (Connection) this.logicalConn;
    }

    public Date getDate(int paramInt) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            return this.physicalStmt.getDate(paramInt);
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public Date getDate(int paramInt, Calendar paramCalendar) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            return this.physicalStmt.getDate(paramInt, paramCalendar);
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public Date getDate(String paramString) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            return this.physicalStmt.getDate(paramString);
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public Date getDate(String paramString, Calendar paramCalendar) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            return this.physicalStmt.getDate(paramString, paramCalendar);
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public double getDouble(int paramInt) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            return this.physicalStmt.getDouble(paramInt);
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public double getDouble(String paramString) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            return this.physicalStmt.getDouble(paramString);
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public int getFetchDirection() throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            return this.physicalStmt.getFetchDirection();
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public int getFetchSize() throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            return this.physicalStmt.getFetchDirection();
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public float getFloat(int paramInt) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            return this.physicalStmt.getFloat(paramInt);
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public float getFloat(String paramString) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            return this.physicalStmt.getFloat(paramString);
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public ResultSet getGeneratedKeys() throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            return this.physicalStmt.getGeneratedKeys();
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public int getInt(int paramInt) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            return this.physicalStmt.getInt(paramInt);
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public int getInt(String paramString) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            return this.physicalStmt.getInt(paramString);
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public long getLong(int paramInt) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            return this.physicalStmt.getLong(paramInt);
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public long getLong(String paramString) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            return this.physicalStmt.getLong(paramString);
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public int getMaxFieldSize() throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            return this.physicalStmt.getMaxFieldSize();
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public int getMaxRows() throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            return this.physicalStmt.getMaxRows();
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public ResultSetMetaData getMetaData() throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            return this.physicalStmt.getMetaData();
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public boolean getMoreResults() throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            return this.physicalStmt.getMoreResults();
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public boolean getMoreResults(int paramInt) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            return this.physicalStmt.getMoreResults();
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public Reader getNCharacterStream(int paramInt) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            return this.physicalStmt.getNCharacterStream(paramInt);
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public Reader getNCharacterStream(String paramString) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            return this.physicalStmt.getNCharacterStream(paramString);
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public NClob getNClob(int paramInt) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            return this.physicalStmt.getNClob(paramInt);
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public NClob getNClob(String paramString) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            return this.physicalStmt.getNClob(paramString);
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public String getNString(int paramInt) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            return this.physicalStmt.getNString(paramInt);
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public String getNString(String paramString) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            return this.physicalStmt.getNString(paramString);
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public Object getObject(int paramInt) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            return this.physicalStmt.getObject(paramInt);
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public Object getObject(int paramInt, Map<String, Class<?>> paramMap) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            return this.physicalStmt.getObject(paramInt, paramMap);
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public Object getObject(String paramString) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            return this.physicalStmt.getObject(paramString);
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public Object getObject(String paramString, Map<String, Class<?>> paramMap) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            return this.physicalStmt.getObject(paramString);
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public String getOriginalSql() {
        return (this.physicalStmt != null) ? this.physicalStmt.getOriginalSql() : null;
    }

    public BindItem getOutItems(int paramInt) {
        return (this.physicalStmt != null) ? this.physicalStmt.getOutItems(paramInt) : null;
    }

    public ParamContainer getParamContainer() {
        return (this.physicalStmt != null) ? this.physicalStmt.getParamContainer() : null;
    }

    public ParameterMetaData getParameterMetaData() throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            return this.physicalStmt.getParameterMetaData();
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public Vector<byte[]> getPivotData() {
        return (this.physicalStmt != null) ? this.physicalStmt.getPivotData() : null;
    }

    public Vector<byte[]> getPivotData(int paramInt) throws SQLException {
        return (this.physicalStmt != null) ? this.physicalStmt.getPivotData(paramInt) : null;
    }

    public TbPivotInfo[] getPivotInfo() {
        return (this.physicalStmt != null) ? this.physicalStmt.getPivotInfo() : null;
    }

    public TbPivotInfo[] getPivotInfo(int paramInt) throws SQLException {
        return (this.physicalStmt != null) ? this.physicalStmt.getPivotInfo(paramInt) : null;
    }

    public byte[] getPPID() {
        return (this.physicalStmt != null) ? this.physicalStmt.getPPID() : null;
    }

    public int getQueryTimeout() throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            return this.physicalStmt.getQueryTimeout();
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public Ref getRef(int paramInt) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            return this.physicalStmt.getRef(paramInt);
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public Ref getRef(String paramString) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            return this.physicalStmt.getRef(paramString);
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public ResultSet getResultSet() throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            return this.physicalStmt.getResultSet();
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public int getResultSetConcurrency() throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        return this.physicalStmt.getResultSetConcurrency();
    }

    public int getResultSetHoldability() throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            return this.physicalStmt.getResultSetHoldability();
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public int getResultSetType() throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            return this.physicalStmt.getResultSetType();
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public RowId getRowId(int paramInt) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            return this.physicalStmt.getRowId(paramInt);
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public RowId getRowId(String paramString) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            return this.physicalStmt.getRowId(paramString);
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public short getShort(int paramInt) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            return this.physicalStmt.getShort(paramInt);
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public short getShort(String paramString) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            return this.physicalStmt.getShort(paramString);
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public int getSqlType() {
        return (this.physicalStmt != null) ? this.physicalStmt.getSqlType() : 0;
    }

    public SQLXML getSQLXML(int paramInt) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            return this.physicalStmt.getSQLXML(paramInt);
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public SQLXML getSQLXML(String paramString) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            return this.physicalStmt.getSQLXML(paramString);
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public String getString(int paramInt) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            return this.physicalStmt.getString(paramInt);
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public String getString(String paramString) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            return this.physicalStmt.getString(paramString);
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public Struct getStruct(int paramInt) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            return this.physicalStmt.getStruct(paramInt);
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public TbDate getTbDate(int paramInt) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            return this.physicalStmt.getTbDate(paramInt);
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public TbDate getTbDate(String paramString) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            return this.physicalStmt.getTbDate(paramString);
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public TbTimestamp getTbTimestamp(int paramInt) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            return this.physicalStmt.getTbTimestamp(paramInt);
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public TbTimestamp getTbTimestamp(String paramString) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            return this.physicalStmt.getTbTimestamp(paramString);
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public Time getTime(int paramInt) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            return this.physicalStmt.getTime(paramInt);
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public Time getTime(int paramInt, Calendar paramCalendar) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            return this.physicalStmt.getTime(paramInt, paramCalendar);
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public Time getTime(String paramString) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            return this.physicalStmt.getTime(paramString);
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public Time getTime(String paramString, Calendar paramCalendar) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            return this.physicalStmt.getTime(paramString, paramCalendar);
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public Timestamp getTimestamp(int paramInt) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            return this.physicalStmt.getTimestamp(paramInt);
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public Timestamp getTimestamp(int paramInt, Calendar paramCalendar) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            return this.physicalStmt.getTimestamp(paramInt, paramCalendar);
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public Timestamp getTimestamp(String paramString) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            return this.physicalStmt.getTimestamp(paramString);
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public Timestamp getTimestamp(String paramString, Calendar paramCalendar) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            return this.physicalStmt.getTimestamp(paramString, paramCalendar);
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public int getUpdateCount() throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            return this.physicalStmt.getUpdateCount();
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public URL getURL(int paramInt) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            return this.physicalStmt.getURL(paramInt);
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public URL getURL(String paramString) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            return this.physicalStmt.getURL(paramString);
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public SQLWarning getWarnings() throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            return this.physicalStmt.getWarnings();
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public boolean isClosed() throws SQLException {
        return this.closed;
    }

    public boolean isPoolable() throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            return this.physicalStmt.isPoolable();
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public boolean isWrapperFor(Class<?> paramClass) throws SQLException {
        return paramClass.isInstance(this);
    }

    public void notifyClosedEvent() {
        if (this.physicalStmt != null && this.stmtEventMap != null) {
            Iterator iterator = this.stmtEventMap.keySet().iterator();
            while (iterator.hasNext()) {
                StatementEventListener statementEventListener = this.stmtEventMap.get(iterator.next());
                statementEventListener.statementClosed(new StatementEvent((PooledConnection) this.logicalConn.getEventHandler(), (PreparedStatement) this));
            }
        }
    }

    public void notifyExceptionEvent(PreparedStatement paramPreparedStatement, Exception paramException) {
        if (this.physicalStmt != null && this.stmtEventMap != null) {
            Iterator iterator = this.stmtEventMap.keySet().iterator();
            while (iterator.hasNext()) {
                StatementEventListener statementEventListener = this.stmtEventMap.get(iterator.next());
                statementEventListener.statementErrorOccurred(new StatementEvent((PooledConnection) this.logicalConn.getEventHandler(), (PreparedStatement) this, (SQLException) paramException));
            }
        }
    }

    public void registerOutParameter(int paramInt1, int paramInt2) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            this.physicalStmt.registerOutParameter(paramInt1, paramInt2);
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public void registerOutParameter(int paramInt1, int paramInt2, int paramInt3) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            this.physicalStmt.registerOutParameter(paramInt1, paramInt2, paramInt3);
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public void registerOutParameter(int paramInt1, int paramInt2, String paramString) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            this.physicalStmt.registerOutParameter(paramInt1, paramInt2, paramString);
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public void registerOutParameter(String paramString, int paramInt) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            this.physicalStmt.registerOutParameter(paramString, paramInt);
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public void registerOutParameter(String paramString, int paramInt1, int paramInt2) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            this.physicalStmt.registerOutParameter(paramString, paramInt1, paramInt2);
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public void registerOutParameter(String paramString1, int paramInt, String paramString2) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            this.physicalStmt.registerOutParameter(paramString1, paramInt, paramString2);
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public void resetForCache() {
        if (this.physicalStmt != null)
            this.physicalStmt.resetForCache();
    }

    public void setArray(int paramInt, Array paramArray) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            this.physicalStmt.setArray(paramInt, paramArray);
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public void setAsciiStream(int paramInt, InputStream paramInputStream) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            this.physicalStmt.setAsciiStream(paramInt, paramInputStream);
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public void setAsciiStream(int paramInt1, InputStream paramInputStream, int paramInt2) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            this.physicalStmt.setAsciiStream(paramInt1, paramInputStream, paramInt2);
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public void setAsciiStream(int paramInt, InputStream paramInputStream, long paramLong) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            this.physicalStmt.setAsciiStream(paramInt, paramInputStream, paramLong);
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public void setAsciiStream(String paramString, InputStream paramInputStream) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            this.physicalStmt.setAsciiStream(paramString, paramInputStream);
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public void setAsciiStream(String paramString, InputStream paramInputStream, int paramInt) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            this.physicalStmt.setAsciiStream(paramString, paramInputStream, paramInt);
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public void setAsciiStream(String paramString, InputStream paramInputStream, long paramLong) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            this.physicalStmt.setAsciiStream(paramString, paramInputStream, paramLong);
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public void setBigDecimal(int paramInt, BigDecimal paramBigDecimal) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            this.physicalStmt.setBigDecimal(paramInt, paramBigDecimal);
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public void setBigDecimal(String paramString, BigDecimal paramBigDecimal) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            this.physicalStmt.setBigDecimal(paramString, paramBigDecimal);
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public void setBinaryDouble(int paramInt, double paramDouble) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            this.physicalStmt.setBinaryDouble(paramInt, paramDouble);
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public void setBinaryFloat(int paramInt, float paramFloat) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            this.physicalStmt.setBinaryFloat(paramInt, paramFloat);
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public void setBinaryStream(int paramInt, InputStream paramInputStream) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            this.physicalStmt.setBinaryStream(paramInt, paramInputStream);
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public void setBinaryStream(int paramInt1, InputStream paramInputStream, int paramInt2) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            this.physicalStmt.setBinaryStream(paramInt1, paramInputStream, paramInt2);
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public void setBinaryStream(int paramInt, InputStream paramInputStream, long paramLong) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            this.physicalStmt.setBinaryStream(paramInt, paramInputStream, paramLong);
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public void setBinaryStream(String paramString, InputStream paramInputStream) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            this.physicalStmt.setBinaryStream(paramString, paramInputStream);
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public void setBinaryStream(String paramString, InputStream paramInputStream, int paramInt) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            this.physicalStmt.setBinaryStream(paramString, paramInputStream, paramInt);
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public void setBinaryStream(String paramString, InputStream paramInputStream, long paramLong) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            this.physicalStmt.setBinaryStream(paramString, paramInputStream, paramLong);
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public void setBlob(int paramInt, Blob paramBlob) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            this.physicalStmt.setBlob(paramInt, paramBlob);
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public void setBlob(int paramInt, InputStream paramInputStream) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            this.physicalStmt.setBlob(paramInt, paramInputStream);
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public void setBlob(int paramInt, InputStream paramInputStream, long paramLong) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            this.physicalStmt.setBlob(paramInt, paramInputStream, paramLong);
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public void setBlob(String paramString, Blob paramBlob) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            this.physicalStmt.setBlob(paramString, paramBlob);
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public void setBlob(String paramString, InputStream paramInputStream) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            this.physicalStmt.setBlob(paramString, paramInputStream);
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public void setBlob(String paramString, InputStream paramInputStream, long paramLong) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            this.physicalStmt.setBlob(paramString, paramInputStream, paramLong);
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public void setBoolean(int paramInt, boolean paramBoolean) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            this.physicalStmt.setBoolean(paramInt, paramBoolean);
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public void setBoolean(String paramString, boolean paramBoolean) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            this.physicalStmt.setBoolean(paramString, paramBoolean);
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public void setByte(int paramInt, byte paramByte) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            this.physicalStmt.setByte(paramInt, paramByte);
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public void setByte(String paramString, byte paramByte) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            this.physicalStmt.setByte(paramString, paramByte);
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public void setBytes(int paramInt, byte[] paramArrayOfbyte) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            this.physicalStmt.setBytes(paramInt, paramArrayOfbyte);
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public void setBytes(int paramInt1, int paramInt2, byte[] paramArrayOfbyte) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            this.physicalStmt.setBytes(paramInt1, paramInt2, paramArrayOfbyte);
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public void setBytes(String paramString, byte[] paramArrayOfbyte) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            this.physicalStmt.setBytes(paramString, paramArrayOfbyte);
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public void setRAW(int paramInt, TbRAW paramTbRAW) throws SQLException {
        setBytes(paramInt, paramTbRAW.getBytes());
    }

    public void setRAW(int paramInt1, int paramInt2, TbRAW paramTbRAW) throws SQLException {
        setBytes(paramInt1, paramInt2, paramTbRAW.getBytes());
    }

    public void setRAW(String paramString, TbRAW paramTbRAW) throws SQLException {
        setBytes(paramString, paramTbRAW.getBytes());
    }

    public void setCharacterStream(int paramInt, Reader paramReader) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            this.physicalStmt.setCharacterStream(paramInt, paramReader);
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public void setCharacterStream(int paramInt1, Reader paramReader, int paramInt2) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            this.physicalStmt.setCharacterStream(paramInt1, paramReader, paramInt2);
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public void setCharacterStream(int paramInt, Reader paramReader, long paramLong) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            this.physicalStmt.setCharacterStream(paramInt, paramReader, paramLong);
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public void setCharacterStream(String paramString, Reader paramReader) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            this.physicalStmt.setCharacterStream(paramString, paramReader);
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public void setCharacterStream(String paramString, Reader paramReader, int paramInt) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            this.physicalStmt.setCharacterStream(paramString, paramReader, paramInt);
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public void setCharacterStream(String paramString, Reader paramReader, long paramLong) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            this.physicalStmt.setCharacterStream(paramString, paramReader, paramLong);
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public void setClob(int paramInt, Clob paramClob) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            this.physicalStmt.setClob(paramInt, paramClob);
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public void setClob(int paramInt, Reader paramReader) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            this.physicalStmt.setClob(paramInt, paramReader);
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public void setClob(int paramInt, Reader paramReader, long paramLong) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            this.physicalStmt.setClob(paramInt, paramReader, paramLong);
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public void setClob(String paramString, Clob paramClob) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            this.physicalStmt.setClob(paramString, paramClob);
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public void setClob(String paramString, Reader paramReader) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            this.physicalStmt.setClob(paramString, paramReader);
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public void setClob(String paramString, Reader paramReader, long paramLong) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            this.physicalStmt.setClob(paramString, paramReader, paramLong);
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public void setCursorName(String paramString) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            this.physicalStmt.setCursorName(paramString);
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public void setDate(int paramInt, Date paramDate) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            this.physicalStmt.setDate(paramInt, paramDate);
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public void setDate(int paramInt, Date paramDate, Calendar paramCalendar) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            this.physicalStmt.setDate(paramInt, paramDate, paramCalendar);
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public void setDate(String paramString, Date paramDate) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            this.physicalStmt.setDate(paramString, paramDate);
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public void setDate(String paramString, Date paramDate, Calendar paramCalendar) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            this.physicalStmt.setDate(paramString, paramDate, paramCalendar);
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public void setDouble(int paramInt, double paramDouble) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            this.physicalStmt.setDouble(paramInt, paramDouble);
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public void setDouble(String paramString, double paramDouble) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            this.physicalStmt.setDouble(paramString, paramDouble);
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public void setEscapeProcessing(boolean paramBoolean) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            this.physicalStmt.setEscapeProcessing(paramBoolean);
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public void setFetchDirection(int paramInt) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            this.physicalStmt.setFetchDirection(paramInt);
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public void setFetchSize(int paramInt) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            this.physicalStmt.setFetchSize(paramInt);
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public void setFixedCHAR(int paramInt, String paramString) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            this.physicalStmt.setFixedCHAR(paramInt, paramString);
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public void setFloat(int paramInt, float paramFloat) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            this.physicalStmt.setFloat(paramInt, paramFloat);
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public void setFloat(String paramString, float paramFloat) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            this.physicalStmt.setFloat(paramString, paramFloat);
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public void setInt(int paramInt1, int paramInt2) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            this.physicalStmt.setInt(paramInt1, paramInt2);
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public void setInt(String paramString, int paramInt) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            this.physicalStmt.setInt(paramString, paramInt);
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public void setLong(int paramInt, long paramLong) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            this.physicalStmt.setLong(paramInt, paramLong);
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public void setLong(String paramString, long paramLong) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            this.physicalStmt.setLong(paramString, paramLong);
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public void setMaxFieldSize(int paramInt) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            this.physicalStmt.setMaxFieldSize(paramInt);
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public void setMaxRows(int paramInt) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            this.physicalStmt.setMaxRows(paramInt);
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public void setNCharacterStream(int paramInt, Reader paramReader) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            this.physicalStmt.setNCharacterStream(paramInt, paramReader);
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public void setNCharacterStream(int paramInt, Reader paramReader, long paramLong) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            this.physicalStmt.setNCharacterStream(paramInt, paramReader, paramLong);
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public void setNCharacterStream(String paramString, Reader paramReader) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            this.physicalStmt.setNCharacterStream(paramString, paramReader);
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public void setNCharacterStream(String paramString, Reader paramReader, long paramLong) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            this.physicalStmt.setNCharacterStream(paramString, paramReader, paramLong);
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public void setNClob(int paramInt, Clob paramClob) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            this.physicalStmt.setNClob(paramInt, paramClob);
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public void setNClob(int paramInt, NClob paramNClob) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            this.physicalStmt.setNClob(paramInt, paramNClob);
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public void setNClob(int paramInt, Reader paramReader) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            this.physicalStmt.setNClob(paramInt, paramReader);
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public void setNClob(int paramInt, Reader paramReader, long paramLong) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            this.physicalStmt.setNClob(paramInt, paramReader, paramLong);
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public void setNClob(String paramString, NClob paramNClob) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            this.physicalStmt.setNClob(paramString, paramNClob);
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public void setNClob(String paramString, Reader paramReader) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            this.physicalStmt.setNClob(paramString, paramReader);
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public void setNClob(String paramString, Reader paramReader, long paramLong) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            this.physicalStmt.setNClob(paramString, paramReader, paramLong);
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public void setNString(int paramInt, String paramString) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            this.physicalStmt.setNString(paramInt, paramString);
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public void setNString(String paramString1, String paramString2) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            this.physicalStmt.setNString(paramString1, paramString2);
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public void setNull(int paramInt1, int paramInt2) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            this.physicalStmt.setNull(paramInt1, paramInt2);
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public void setNull(int paramInt1, int paramInt2, String paramString) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            this.physicalStmt.setNull(paramInt1, paramInt2, paramString);
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public void setNull(String paramString, int paramInt) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            this.physicalStmt.setNull(paramString, paramInt);
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public void setNull(String paramString1, int paramInt, String paramString2) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            this.physicalStmt.setNull(paramString1, paramInt, paramString2);
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public void setObject(int paramInt, Object paramObject) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            this.physicalStmt.setObject(paramInt, paramObject);
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public void setObject(int paramInt1, Object paramObject, int paramInt2) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            this.physicalStmt.setObject(paramInt1, paramObject, paramInt2);
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public void setObject(int paramInt1, Object paramObject, int paramInt2, int paramInt3) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            this.physicalStmt.setObject(paramInt1, paramObject, paramInt2, paramInt3);
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public void setObject(String paramString, Object paramObject) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            this.physicalStmt.setObject(paramString, paramObject);
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public void setObject(String paramString, Object paramObject, int paramInt) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            this.physicalStmt.setObject(paramString, paramObject, paramInt);
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public void setObject(String paramString, Object paramObject, int paramInt1, int paramInt2) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            this.physicalStmt.setObject(paramString, paramObject, paramInt1, paramInt2);
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public void setOutParam(int paramInt1, int paramInt2, byte[] paramArrayOfbyte, TbResultSet paramTbResultSet) throws SQLException {
        if (this.physicalStmt != null)
            this.physicalStmt.setOutParam(paramInt1, paramInt2, paramArrayOfbyte, paramTbResultSet);
    }

    public void setPivotInfo(TbPivotInfo[] paramArrayOfTbPivotInfo) {
        if (this.physicalStmt != null)
            this.physicalStmt.setPivotInfo(paramArrayOfTbPivotInfo);
    }

    public void setPoolable(boolean paramBoolean) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            this.physicalStmt.setPoolable(paramBoolean);
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public void setQueryTimeout(int paramInt) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            this.physicalStmt.setQueryTimeout(paramInt);
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public void setRef(int paramInt, Ref paramRef) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            this.physicalStmt.setRef(paramInt, paramRef);
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public void setRowId(int paramInt, RowId paramRowId) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            this.physicalStmt.setRowId(paramInt, paramRowId);
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public void setRowId(String paramString, RowId paramRowId) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            this.physicalStmt.setRowId(paramString, paramRowId);
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public void setShort(int paramInt, short paramShort) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            this.physicalStmt.setShort(paramInt, paramShort);
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public void setShort(String paramString, short paramShort) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            this.physicalStmt.setShort(paramString, paramShort);
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public void setSQLXML(int paramInt, SQLXML paramSQLXML) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            this.physicalStmt.setSQLXML(paramInt, paramSQLXML);
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public void setSQLXML(String paramString, SQLXML paramSQLXML) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            this.physicalStmt.setSQLXML(paramString, paramSQLXML);
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public void setString(int paramInt, String paramString) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            this.physicalStmt.setString(paramInt, paramString);
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public void setString(String paramString1, String paramString2) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            this.physicalStmt.setString(paramString1, paramString2);
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public void setTbDate(int paramInt, TbDate paramTbDate) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            this.physicalStmt.setTbDate(paramInt, paramTbDate);
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public void setTbDate(String paramString, TbDate paramTbDate) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            this.physicalStmt.setTbDate(paramString, paramTbDate);
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public void setTbTimestamp(int paramInt, TbTimestamp paramTbTimestamp) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            this.physicalStmt.setTbTimestamp(paramInt, paramTbTimestamp);
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public void setTbTimestamp(String paramString, TbTimestamp paramTbTimestamp) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            this.physicalStmt.setTbTimestamp(paramString, paramTbTimestamp);
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public void setTime(int paramInt, Time paramTime) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            this.physicalStmt.setTime(paramInt, paramTime);
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public void setTime(int paramInt, Time paramTime, Calendar paramCalendar) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            this.physicalStmt.setTime(paramInt, paramTime, paramCalendar);
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public void setTime(String paramString, Time paramTime) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            this.physicalStmt.setTime(paramString, paramTime);
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public void setTime(String paramString, Time paramTime, Calendar paramCalendar) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            this.physicalStmt.setTime(paramString, paramTime, paramCalendar);
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public void setTimestamp(int paramInt, Timestamp paramTimestamp) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            this.physicalStmt.setTimestamp(paramInt, paramTimestamp);
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public void setTimestamp(int paramInt, Timestamp paramTimestamp, Calendar paramCalendar) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            this.physicalStmt.setTimestamp(paramInt, paramTimestamp, paramCalendar);
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public void setTimestamp(String paramString, Timestamp paramTimestamp) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            this.physicalStmt.setTimestamp(paramString, paramTimestamp);
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public void setTimestamp(String paramString, Timestamp paramTimestamp, Calendar paramCalendar) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            this.physicalStmt.setTimestamp(paramString, paramTimestamp, paramCalendar);
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    @Deprecated
    public void setUnicodeStream(int paramInt1, InputStream paramInputStream, int paramInt2) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            this.physicalStmt.setUnicodeStream(paramInt1, paramInputStream, paramInt2);
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    @Deprecated
    public void setUnicodeStream(String paramString, InputStream paramInputStream, int paramInt) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            this.physicalStmt.setUnicodeStream(paramString, paramInputStream, paramInt);
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public void setURL(int paramInt, URL paramURL) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            this.physicalStmt.setURL(paramInt, paramURL);
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public void setURL(String paramString, URL paramURL) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            this.physicalStmt.setURL(paramString, paramURL);
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public <T> T unwrap(Class<T> paramClass) throws SQLException {
        try {
            return paramClass.cast(this);
        } catch (ClassCastException classCastException) {
            throw TbError.newSQLException(-90657);
        }
    }

    public boolean wasNull() throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            return this.physicalStmt.wasNull();
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public <T> T getObject(int paramInt, Class<T> paramClass) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            return (T) this.physicalStmt.getObject(paramInt, paramClass);
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public <T> T getObject(String paramString, Class<T> paramClass) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            return (T) this.physicalStmt.getObject(paramString, paramClass);
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public void closeOnCompletion() throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            this.physicalStmt.closeOnCompletion();
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public boolean isCloseOnCompletion() throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            return this.physicalStmt.isCloseOnCompletion();
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public void setObject(int paramInt1, Object paramObject, SQLType paramSQLType, int paramInt2) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            this.physicalStmt.setObject(paramInt1, paramObject, paramSQLType, paramInt2);
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }

    public void setObject(int paramInt, Object paramObject, SQLType paramSQLType) throws SQLException {
        if (isClosed())
            throw TbError.newSQLException(-90659);
        try {
            this.physicalStmt.setObject(paramInt, paramObject, paramSQLType);
        } catch (SQLException sQLException) {
            notifyExceptionEvent((PreparedStatement) this.physicalStmt, sQLException);
            throw sQLException;
        }
    }
}