/*======================================================================
CubridConnection implements a wrapped Cubrid Connection.
Version     : $Id: $
Application : SIARD2
Description : CubridConnection implements a wrapped Cubrid Connection.
Platform    : Java 7
------------------------------------------------------------------------
Copyright  : 2016, Enter AG, Rüti ZH, Switzerland
Created    : 26.10.2016, Simon Jutz
======================================================================*/
package ch.admin.bar.siard2.jdbc;

import java.io.*;
import java.sql.*;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;

import ch.admin.bar.siard2.oracle.OracleSqlFactory;
import ch.admin.bar.siard2.tibero.TiberoSqlFactory;
import ch.enterag.sqlparser.DdlStatement;
import ch.enterag.sqlparser.SqlFactory;
import ch.enterag.sqlparser.SqlStatement;
import ch.enterag.sqlparser.ddl.AlterTableStatement;
import ch.enterag.sqlparser.ddl.CreateTableStatement;
import ch.enterag.sqlparser.ddl.DropTableStatement;
import ch.enterag.sqlparser.ddl.enums.DropBehavior;
import ch.enterag.sqlparser.expression.QuerySpecification;
import ch.enterag.sqlparser.expression.TablePrimary;
import ch.enterag.sqlparser.expression.TableReference;
import ch.enterag.sqlparser.identifier.QualifiedId;
import ch.enterag.utils.jdbc.BaseConnection;
import ch.enterag.utils.jdbc.BaseDatabaseMetaData;
import ch.enterag.utils.logging.IndentLogger;


/* ===============================================================================- */

/**
 * TiberoConnection implements a wrapped Tibero Connection
 *
 * @author
 */
public class TiberoConnection extends BaseConnection implements Connection {

    private static IndentLogger _il = IndentLogger.getIndentLogger(TiberoConnection.class.getName());
    private static final int iBUFFER_SIZE = 8192;

    private void throwSqlException(SQLException tse) throws SQLException {
        throw new SQLException("Tibero exception!", tse);
    }

    private void throwNotSupportedException(SQLException tse) throws SQLFeatureNotSupportedException {
        throw new SQLFeatureNotSupportedException("tse Exception!", tse);
    }

    public TiberoConnection(Connection connWrapped) throws SQLException {
        super(connWrapped);
        if (connWrapped != null) {
            _il.enter(new Object[]{connWrapped});
            Statement stmt = super.createStatement();
            String sSql = "ALTER SESSION SET NLS_LENGTH_SEMANTICS=CHAR";
            _il.event("Unwrapped DML: " + sSql);
            stmt.executeUpdate(sSql);
            stmt.close();
            _il.exit();
        }

    }

    public Statement createStatement() throws SQLException {
        Statement stmt = new OracleStatement(super.createStatement());
        return stmt;
    }

    public PreparedStatement prepareStatement(String sql) throws SQLException {
        PreparedStatement ps = super.prepareStatement(this.nativeSQL(sql));
        return ps;
    }

    public CallableStatement prepareCall(String sql) throws SQLException {
        CallableStatement cs = super.prepareCall(this.nativeSQL(sql));
        return cs;
    }

    public String nativeSQL(String sql) throws SQLException {
        _il.enter(new Object[]{sql});
        TiberoSqlFactory tsf = new TiberoSqlFactory();
        tsf.setConnection(this);
        SqlStatement ss = tsf.newSqlStatement();
        ss.parse(sql);
        sql = ss.format();
        _il.exit(sql);
        return sql;
    }

    public void setAutoCommit(boolean autoCommit) throws SQLException {
        try {
            super.setAutoCommit(autoCommit);
        } catch (SQLException var3) {
            SQLException tse = var3;
            this.throwSqlException(tse);
        }

    }

    public boolean getAutoCommit() throws SQLException {
        boolean bAutoCommit = false;

        try {
            bAutoCommit = super.getAutoCommit();
        } catch (SQLException var3) {
            SQLException tse = var3;
            this.throwSqlException(tse);
        }

        return bAutoCommit;
    }

    public void commit() throws SQLException {
        try {
            super.commit();
        } catch (SQLException var2) {
            SQLException tse = var2;
            this.throwSqlException(tse);
        }

    }

    public void rollback() throws SQLException {
        try {
            super.rollback();
        } catch (SQLException var2) {
            SQLException tse = var2;
            this.throwSqlException(tse);
        }

    }

    public void close() throws SQLException {
        try {
            super.close();
        } catch (SQLException var2) {
            SQLException tse = var2;
            this.throwSqlException(tse);
        }

    }

    public boolean isclosed() throws SQLException {
        boolean bIsclosed = false;

        try {
            bIsclosed = super.isClosed();
        } catch (SQLException var3) {
            SQLException tse = var3;
            this.throwSqlException(tse);
        }

        return bIsclosed;
    }

    public DatabaseMetaData getMetaData() throws SQLException {
        DatabaseMetaData dmd = null;

        try {
            dmd = new OracleDatabaseMetaData(super.getMetaData());
        } catch (SQLException var3) {
            SQLException tse = var3;
            this.throwSqlException(tse);
        }

        return dmd;
    }

    public void setReadOnly(boolean readOnly) throws SQLException {
        try {
            super.setReadOnly(readOnly);
        } catch (SQLException var3) {
            SQLException tse = var3;
            this.throwSqlException(tse);
        }

    }

    public boolean isReadOnly() throws SQLException {
        boolean bIsReadOnly = false;

        try {
            bIsReadOnly = super.isReadOnly();
        } catch (SQLException var3) {
            SQLException tse = var3;
            this.throwSqlException(tse);
        }

        return bIsReadOnly;
    }

    public void setCatalog(String catalog) throws SQLException {
        try {
            super.setCatalog(catalog);
        } catch (SQLException var3) {
            SQLException tse = var3;
            this.throwSqlException(tse);
        }

    }

    public String getCatalog() throws SQLException {
        String sCatalog = null;

        try {
            sCatalog = super.getCatalog();
        } catch (SQLException var3) {
            SQLException tse = var3;
            this.throwSqlException(tse);
        }

        return sCatalog;
    }

    public void setTransactionIsolation(int level) throws SQLException {
        try {
            super.setTransactionIsolation(level);
        } catch (SQLException var3) {
            SQLException tse = var3;
            this.throwSqlException(tse);
        }

    }

    public int getTransactionIsolation() throws SQLException {
        int iTransactionIsolation = -1;

        try {
            iTransactionIsolation = super.getTransactionIsolation();
        } catch (SQLException var3) {
            SQLException tse = var3;
            this.throwSqlException(tse);
        }

        return iTransactionIsolation;
    }

    public SQLWarning getWarnings() throws SQLException {
        SQLWarning sw = null;

        try {
            sw = super.getWarnings();
        } catch (SQLException var3) {
            SQLException tse = var3;
            this.throwSqlException(tse);
        }

        return sw;
    }

    public void clearWarnings() throws SQLException {
        try {
            super.clearWarnings();
        } catch (SQLException var2) {
            SQLException tse = var2;
            this.throwSqlException(tse);
        }

    }

    public Statement createStatement(int resultSetType, int resultSetConcurrency) throws SQLException {
        Statement stmt = null;

        try {
            stmt = new OracleStatement(super.createStatement(resultSetType, resultSetConcurrency));
        } catch (SQLException var5) {
            SQLException tse = var5;
            this.throwSqlException(tse);
        }

        return stmt;
    }

    public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
        PreparedStatement ps = null;

        try {
            ps = super.prepareStatement(this.nativeSQL(sql), resultSetType, resultSetConcurrency);
        } catch (SQLException var6) {
            SQLException tse = var6;
            this.throwSqlException(tse);
        }

        return ps;
    }

    public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
        CallableStatement cs = null;

        try {
            cs = super.prepareCall(this.nativeSQL(sql), resultSetType, resultSetConcurrency);
        } catch (SQLException var6) {
            SQLException tse = var6;
            this.throwSqlException(tse);
        }

        return cs;
    }

    public Map<String, Class<?>> getTypeMap() throws SQLException {
        Map<String, Class<?>> mapTypes = null;

        try {
            mapTypes = super.getTypeMap();
        } catch (SQLException var3) {
            SQLException tse = var3;
            this.throwSqlException(tse);
        }

        return mapTypes;
    }

    public void setTypeMap(Map<String, Class<?>> map) throws SQLException {
        try {
            super.setTypeMap(map);
        } catch (SQLException var3) {
            SQLException tse = var3;
            this.throwSqlException(tse);
        }

    }

    public void setHoldability(int holdability) throws SQLException {
        try {
            super.setHoldability(holdability);
        } catch (SQLException var3) {
            SQLException tse = var3;
            this.throwSqlException(tse);
        }

    }

    public int getHoldability() throws SQLException {
        int iHoldability = -1;

        try {
            iHoldability = super.getHoldability();
        } catch (SQLException var3) {
            SQLException tse = var3;
            this.throwSqlException(tse);
        }

        return iHoldability;
    }

    public Savepoint setSavepoint() throws SQLException {
        Savepoint sp = null;

        try {
            sp = super.setSavepoint();
        } catch (SQLException var3) {
            SQLException tse = var3;
            this.throwSqlException(tse);
        }

        return sp;
    }

    public Savepoint setSavepoint(String name) throws SQLException {
        Savepoint sp = null;

        try {
            sp = super.setSavepoint(name);
        } catch (SQLException var4) {
            SQLException tse = var4;
            this.throwSqlException(tse);
        }

        return sp;
    }

    public void rollback(Savepoint savepoint) throws SQLException {
        try {
            super.rollback(savepoint);
        } catch (SQLException var3) {
            SQLException tse = var3;
            this.throwSqlException(tse);
        }

    }

    public void releaseSavepoint(Savepoint savepoint) throws SQLException {
        try {
            super.releaseSavepoint(savepoint);
        } catch (SQLException var3) {
            SQLException tse = var3;
            this.throwNotSupportedException(tse);
        }

    }

    public Statement createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
        Statement stmt = null;

        try {
            stmt = new OracleStatement(super.createStatement(resultSetType, resultSetConcurrency, resultSetHoldability));
        } catch (SQLException var6) {
            SQLException tse = var6;
            this.throwNotSupportedException(tse);
        }

        return stmt;
    }

    public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
        PreparedStatement ps = null;

        try {
            ps = super.prepareStatement(this.nativeSQL(sql), resultSetType, resultSetConcurrency, resultSetHoldability);
        } catch (SQLException var7) {
            SQLException tse = var7;
            this.throwNotSupportedException(tse);
        }

        return ps;
    }

    public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
        CallableStatement cs = null;

        try {
            cs = super.prepareCall(this.nativeSQL(sql), resultSetType, resultSetConcurrency, resultSetHoldability);
        } catch (SQLException var7) {
            SQLException tse = var7;
            this.throwNotSupportedException(tse);
        }

        return cs;
    }

    public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys) throws SQLException {
        PreparedStatement ps = null;

        try {
            ps = super.prepareStatement(this.nativeSQL(sql), autoGeneratedKeys);
        } catch (SQLException var5) {
            SQLException tse = var5;
            this.throwSqlException(tse);
        }

        return ps;
    }

    public PreparedStatement prepareStatement(String sql, int[] columnIndexes) throws SQLException {
        PreparedStatement ps = null;

        try {
            ps = super.prepareStatement(this.nativeSQL(sql), columnIndexes);
        } catch (SQLException var5) {
            SQLException tse = var5;
            this.throwSqlException(tse);
        }

        return ps;
    }

    public PreparedStatement prepareStatement(String sql, String[] columnNames) throws SQLException {
        PreparedStatement ps = null;

        try {
            ps = super.prepareStatement(this.nativeSQL(sql), columnNames);
        } catch (SQLException var5) {
            SQLException tse = var5;
            this.throwSqlException(tse);
        }

        return ps;
    }

    public Clob createClob() throws SQLException {
        Clob clob = null;

        try {
            clob = super.createClob();
        } catch (SQLException var3) {
            SQLException tse = var3;
            this.throwSqlException(tse);
        }

        return clob;
    }

    public Blob createBlob() throws SQLException {
        Blob blob = null;

        try {
            blob = super.createBlob();
        } catch (SQLException var3) {
            SQLException tse = var3;
            this.throwSqlException(tse);
        }

        return blob;
    }

    public NClob createNClob() throws SQLException {
        NClob nclob = super.createNClob();
        return nclob;
    }

    public SQLXML createSQLXML() throws SQLException {
        SQLXML sqlxml = null;

        try {
            sqlxml = super.createSQLXML();
        } catch (SQLException var3) {
            SQLException tse = var3;
            this.throwSqlException(tse);
        } catch (ClassCastException var4) {
            ClassCastException cce = var4;
            throw new SQLFeatureNotSupportedException(cce);
        }

        return sqlxml;
    }

    public boolean isValid(int timeout) throws SQLException {
        boolean bIsValid = false;

        try {
            bIsValid = super.isValid(timeout);
        } catch (SQLException var4) {
            SQLException tse = var4;
            this.throwSqlException(tse);
        }

        return bIsValid;
    }

    public void setClientInfo(String name, String value) throws SQLClientInfoException {
        super.setClientInfo(name, value);
    }

    public void setClientInfo(Properties properties) throws SQLClientInfoException {
        super.setClientInfo(properties);
    }

    public String getClientInfo(String name) throws SQLException {
        String sClientInfo = null;

        try {
            sClientInfo = super.getClientInfo(name);
        } catch (SQLException var4) {
            SQLException tse = var4;
            this.throwSqlException(tse);
        }

        return sClientInfo;
    }

    public Properties getClientInfo() throws SQLException {
        Properties propClientInfo = null;

        try {
            propClientInfo = super.getClientInfo();
        } catch (SQLException var3) {
            SQLException tse = var3;
            this.throwSqlException(tse);
        }

        return propClientInfo;
    }

    public Array createArrayOf(String typeName, Object[] elements) throws SQLException {
        Array array = new OracleArray(this, typeName, elements);
        return array;
    }

    public Struct createStruct(String typeName, Object[] attributes) throws SQLException {
        Struct struct = null;

        try {
            oracle.jdbc.OracleConnection connOracle = (oracle.jdbc.OracleConnection)this.unwrap(Connection.class);

            for(int i = 0; i < attributes.length; ++i) {
                int iRead;
                Clob clob;
                Writer wr;
                if (attributes[i] instanceof Reader) {
                    Reader rdr = (Reader)attributes[i];
                    clob = connOracle.createClob();
                    wr = clob.setCharacterStream(1L);
                    char[] cbuf = new char[8192];

                    for(iRead = rdr.read(cbuf); iRead != -1; iRead = rdr.read(cbuf)) {
                        wr.write(cbuf, 0, iRead);
                    }

                    rdr.close();
                    wr.close();
                    attributes[i] = clob;
                } else {
                    Blob blob;
                    OutputStream os;
                    if (!(attributes[i] instanceof InputStream)) {
                        if (attributes[i] instanceof byte[]) {
                            byte[] buf = (byte[])((byte[])attributes[i]);
                            if (buf.length > 4000) {
                                blob = connOracle.createBlob();
                                os = blob.setBinaryStream(1L);
                                os.write(buf);
                                os.close();
                                attributes[i] = blob;
                            }
                        } else if (attributes[i] instanceof String) {
                            String s = (String)attributes[i];
                            if (s.length() > 2000) {
                                clob = connOracle.createClob();
                                wr = clob.setCharacterStream(1L);
                                wr.write(s);
                                wr.close();
                                attributes[i] = clob;
                            }
                        }
                    } else {
                        InputStream is = (InputStream)attributes[i];
                        blob = connOracle.createBlob();
                        os = blob.setBinaryStream(1L);
                        byte[] buffer = new byte[8192];

                        for(iRead = is.read(buffer); iRead != -1; iRead = is.read(buffer)) {
                            os.write(buffer, 0, iRead);
                        }

                        is.close();
                        os.close();
                        attributes[i] = blob;
                    }
                }
            }

            struct = connOracle.createStruct(typeName, attributes);
            return struct;
        } catch (IOException var11) {
            IOException ie = var11;
            throw new SQLException("Input stream could not be read!", ie);
        }
    }

    public void setSchema(String schema) throws SQLException {
        try {
            super.setSchema(schema);
        } catch (SQLException var3) {
            SQLException tse = var3;
            this.throwSqlException(tse);
        }

    }

    public String getSchema() throws SQLException {
        String sSchema = null;

        try {
            sSchema = super.getSchema();
        } catch (SQLException var3) {
            SQLException tse = var3;
            this.throwSqlException(tse);
        }

        return sSchema;
    }

    public void abort(Executor executor) throws SQLException {
        try {
            super.abort(executor);
        } catch (SQLException var3) {
            SQLException tse = var3;
            this.throwSqlException(tse);
        }

    }

    public void setNetworkTimeout(Executor executor, int milliseconds) throws SQLException {
        try {
            super.setNetworkTimeout(executor, milliseconds);
        } catch (SQLException var4) {
            SQLException tse = var4;
            this.throwSqlException(tse);
        }

    }

    public int getNetworkTimeout() throws SQLException {
        int iNetWorkTimeout = 0;

        try {
            iNetWorkTimeout = super.getNetworkTimeout();
        } catch (SQLException var3) {
            SQLException tse = var3;
            this.throwSqlException(tse);
        }

        return iNetWorkTimeout;
    }

    public Blob createDatalinkObject() throws SQLException {
        return this.createBlob();
    }
} /* class CubridConnection */