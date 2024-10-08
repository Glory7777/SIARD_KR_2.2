package ch.enterag.utils.jdbc;

import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.NClob;
import java.sql.PreparedStatement;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Savepoint;
import java.sql.Statement;
import java.sql.Struct;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;


public abstract class BaseConnection
        implements Connection {
    private Connection _connWrapped = null;


    private void throwUndefinedMethod(AbstractMethodError ame) throws SQLFeatureNotSupportedException {
        throw new SQLFeatureNotSupportedException("Undefined JDBC method!", ame);
    }


    public BaseConnection(Connection connWrapped) {
        this._connWrapped = connWrapped;
    }


    public Statement createStatement() throws SQLException {
        return this._connWrapped.createStatement();
    }


    public PreparedStatement prepareStatement(String sql) throws SQLException {
        return this._connWrapped.prepareStatement(sql);
    }


    public CallableStatement prepareCall(String sql) throws SQLException {
        return this._connWrapped.prepareCall(sql);
    }


    public String nativeSQL(String sql) throws SQLException {
        return this._connWrapped.nativeSQL(sql);
    }


    public void setAutoCommit(boolean autoCommit) throws SQLException {
        this._connWrapped.setAutoCommit(autoCommit);
    }


    public boolean getAutoCommit() throws SQLException {
        return this._connWrapped.getAutoCommit();
    }


    public void commit() throws SQLException {
        this._connWrapped.commit();
    }


    public void rollback() throws SQLException {
        this._connWrapped.rollback();
    }


    public void close() throws SQLException {
        this._connWrapped.close();
    }


    public boolean isClosed() throws SQLException {
        boolean bClosed = true;
        if (this._connWrapped != null)
            bClosed = this._connWrapped.isClosed();
        return bClosed;
    }


    public DatabaseMetaData getMetaData() throws SQLException {
        return this._connWrapped.getMetaData();
    }


    public void setReadOnly(boolean readOnly) throws SQLException {
        this._connWrapped.setReadOnly(readOnly);
    }


    public boolean isReadOnly() throws SQLException {
        return this._connWrapped.isReadOnly();
    }


    public void setCatalog(String catalog) throws SQLException {
        this._connWrapped.setCatalog(catalog);
    }


    public String getCatalog() throws SQLException {
        return this._connWrapped.getCatalog();
    }


    public void setTransactionIsolation(int level) throws SQLException {
        this._connWrapped.setTransactionIsolation(level);
    }


    public int getTransactionIsolation() throws SQLException {
        return this._connWrapped.getTransactionIsolation();
    }


    public SQLWarning getWarnings() throws SQLException {
        return this._connWrapped.getWarnings();
    }


    public void clearWarnings() throws SQLException {
        this._connWrapped.clearWarnings();
    }


    public Statement createStatement(int resultSetType, int resultSetConcurrency) throws SQLException {
        return this._connWrapped.createStatement(resultSetType, resultSetConcurrency);
    }


    public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
        return this._connWrapped.prepareStatement(sql, resultSetType, resultSetConcurrency);
    }


    public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
        return this._connWrapped.prepareCall(sql, resultSetType, resultSetConcurrency);
    }


    public Map<String, Class<?>> getTypeMap() throws SQLException {
        return this._connWrapped.getTypeMap();
    }


    public void setTypeMap(Map<String, Class<?>> map) throws SQLException {
        this._connWrapped.setTypeMap(map);
    }


    public void setHoldability(int holdability) throws SQLException {
        this._connWrapped.setHoldability(holdability);
    }


    public int getHoldability() throws SQLException {
        return this._connWrapped.getHoldability();
    }


    public Savepoint setSavepoint() throws SQLException {
        return this._connWrapped.setSavepoint();
    }


    public Savepoint setSavepoint(String name) throws SQLException {
        return this._connWrapped.setSavepoint(name);
    }


    public void rollback(Savepoint savepoint) throws SQLException {
        this._connWrapped.rollback(savepoint);
    }


    public void releaseSavepoint(Savepoint savepoint) throws SQLException {
        this._connWrapped.releaseSavepoint(savepoint);
    }


    public Statement createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
        return this._connWrapped.createStatement(resultSetType, resultSetConcurrency, resultSetHoldability);
    }


    public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
        return this._connWrapped.prepareStatement(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
    }


    public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
        return this._connWrapped.prepareCall(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
    }


    public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys) throws SQLException {
        return this._connWrapped.prepareStatement(sql, autoGeneratedKeys);
    }


    public PreparedStatement prepareStatement(String sql, int[] columnIndexes) throws SQLException {
        return this._connWrapped.prepareStatement(sql, columnIndexes);
    }


    public PreparedStatement prepareStatement(String sql, String[] columnNames) throws SQLException {
        return this._connWrapped.prepareStatement(sql, columnNames);
    }


    public Clob createClob() throws SQLException {
        return this._connWrapped.createClob();
    }


    public Blob createBlob() throws SQLException {
        return this._connWrapped.createBlob();
    }


    public NClob createNClob() throws SQLException {
        return this._connWrapped.createNClob();
    }


    public SQLXML createSQLXML() throws SQLException {
        return this._connWrapped.createSQLXML();
    }

    public Object createDatalinkObject() throws SQLException {
        return null;
    }


    public boolean isValid(int timeout) throws SQLException {
        return this._connWrapped.isValid(timeout);
    }


    public void setClientInfo(String name, String value) throws SQLClientInfoException {
        this._connWrapped.setClientInfo(name, value);
    }


    public void setClientInfo(Properties properties) throws SQLClientInfoException {
        this._connWrapped.setClientInfo(properties);
    }


    public String getClientInfo(String name) throws SQLException {
        return this._connWrapped.getClientInfo(name);
    }


    public Properties getClientInfo() throws SQLException {
        return this._connWrapped.getClientInfo();
    }


    public Array createArrayOf(String typeName, Object[] elements) throws SQLException {
        return this._connWrapped.createArrayOf(typeName, elements);
    }


    public Struct createStruct(String typeName, Object[] attributes) throws SQLException {
        return this._connWrapped.createStruct(typeName, attributes);
    }


    public void setSchema(String schema) throws SQLException {

        try {
            this._connWrapped.setSchema(schema);
        } catch (AbstractMethodError ame) {
            throwUndefinedMethod(ame);
        }

    }


    public String getSchema() throws SQLException {
        String sSchema = null;
        try {
            sSchema = this._connWrapped.getSchema();
        } catch (AbstractMethodError ame) {
            throwUndefinedMethod(ame);
        }
        return sSchema;
    }


    public void abort(Executor executor) throws SQLException {

        try {
            this._connWrapped.abort(executor);
        } catch (AbstractMethodError ame) {
            throwUndefinedMethod(ame);
        }

    }


    public void setNetworkTimeout(Executor executor, int milliseconds) throws SQLException {

        try {
            this._connWrapped.setNetworkTimeout(executor, milliseconds);
        } catch (AbstractMethodError ame) {
            throwUndefinedMethod(ame);
        }

    }


    public int getNetworkTimeout() throws SQLException {
        int iNetWorkTimeout = 0;
        try {
            iNetWorkTimeout = this._connWrapped.getNetworkTimeout();
        } catch (AbstractMethodError ame) {
            throwUndefinedMethod(ame);
        }
        return iNetWorkTimeout;
    }


    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return (iface == Connection.class);
    }


    public <T> T unwrap(Class<T> iface) throws SQLException {
        Connection connection = null;
        T wrapped = null;
        if (isWrapperFor(iface))
            connection = this._connWrapped;
        return (T) connection;
    }
}
