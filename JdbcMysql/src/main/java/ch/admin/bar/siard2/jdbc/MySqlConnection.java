package ch.admin.bar.siard2.jdbc;

import ch.admin.bar.siard2.mysql.MySqlSqlFactory;
import ch.enterag.sqlparser.DdlStatement;
import ch.enterag.sqlparser.SqlStatement;
import ch.enterag.sqlparser.ddl.DropTableStatement;
import ch.enterag.sqlparser.ddl.enums.DropBehavior;
import ch.enterag.sqlparser.expression.QuerySpecification;
import ch.enterag.sqlparser.expression.TablePrimary;
import ch.enterag.sqlparser.expression.TableReference;
import ch.enterag.sqlparser.identifier.QualifiedId;
import ch.enterag.utils.jdbc.BaseConnection;
import ch.enterag.utils.jdbc.BaseDatabaseMetaData;
import ch.enterag.utils.logging.IndentLogger;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.sql.SQLXML;
import java.sql.Statement;
import java.util.concurrent.Executor;


public class MySqlConnection
        extends BaseConnection
        implements Connection {
    private static IndentLogger _il = IndentLogger.getIndentLogger(MySqlConnection.class.getName());
    private QualifiedId _qiTableDropCascade = null;

    public QualifiedId getTableDropCascade() {
        return this._qiTableDropCascade;
    }

    public void resetTableDropCascade() {
        this._qiTableDropCascade = null;
    }

    private QualifiedId _qiTableWithoutPrimaryKey = null;
    public static final String _sSET_FOREIGN_KEYS = "SET FOREIGN_KEY_CHECKS = {0};";

    public QualifiedId getTableWithoutPrimaryKey() {
        return this._qiTableWithoutPrimaryKey;
    }

    public void resetTableWithoutPrimaryKey() {
        this._qiTableWithoutPrimaryKey = null;
    }


    public MySqlConnection(Connection connNative) throws SQLException {
        super(connNative);
    }


    public String nativeSQL(String sql) {
        _il.enter(new Object[]{sql});
        MySqlSqlFactory mySqlSqlFactory = new MySqlSqlFactory();
        SqlStatement ss = mySqlSqlFactory.newSqlStatement();
        ss.parse(sql);
        resetTableDropCascade();
        DdlStatement ds = ss.getDdlStatement();
        if (ds != null) {

            DropTableStatement dts = ds.getDropTableStatement();
            if (dts != null) {
                if (dts.getDropBehavior() == DropBehavior.CASCADE)
                    this._qiTableDropCascade = dts.getTableName();
            }
        }
        resetTableWithoutPrimaryKey();
        QuerySpecification qs = ss.getQuerySpecification();
        if (qs != null) {
            if (qs.getTableReferences().size() == 1) {

                TableReference tr = qs.getTableReferences().get(0);
                if (tr != null) {

                    TablePrimary tp = tr.getTablePrimary();
                    if (!tp.isOnly() &&
                            !tp.isTable() &&
                            !tp.isUnnest() && tp
                            .getQueryExpression() == null && tp
                            .getTableReference() == null) {

                        QualifiedId qiTable = tp.getTableName();

                        try {
                            BaseDatabaseMetaData bdmd = (BaseDatabaseMetaData) getMetaData();
                            ResultSet rs = getMetaData().getPrimaryKeys(qiTable
                                    .getCatalog(), bdmd
                                    .toPattern(qiTable.getSchema()), bdmd
                                    .toPattern(qiTable.getName()));
                            if (!rs.next())
                                this._qiTableWithoutPrimaryKey = qiTable;
                            rs.close();
                        } catch (SQLException se) {
                            _il.exception(se);
                        }
                    }
                }
            }
        }
        sql = ss.format();
        _il.exit(sql);
        return sql;
    }


    public void setNetworkTimeout(Executor executor, int milliseconds) throws SQLException {
        if (executor == null) {
            throw new SQLFeatureNotSupportedException("MySql does not allow the executor argument to be null");
        }
        super.setNetworkTimeout(executor, milliseconds);
    }


    public DatabaseMetaData getMetaData() throws SQLException {
        return new MySqlDatabaseMetaData(super.getMetaData(), this);
    }


    public Statement createStatement() throws SQLException {
        Statement stmt = super.createStatement();
        return new MySqlStatement(stmt, this);
    }


    public Statement createStatement(int resultSetType, int resultSetConcurrency) throws SQLException {
        Statement stmt = super.createStatement(resultSetType, resultSetConcurrency);
        return new MySqlStatement(stmt, this);
    }


    public Statement createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
        Statement stmt = super.createStatement(resultSetType, resultSetConcurrency, resultSetHoldability);
        return new MySqlStatement(stmt, this);
    }


    public SQLXML createSQLXML() throws SQLException {
        return MySqlSqlXml.getInstance();
    }


    public PreparedStatement prepareStatement(String sql) throws SQLException {
        String sNative = nativeSQL(sql);
        PreparedStatement ps = super.prepareStatement(sNative);
        return ps;
    }


    public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
        String sNative = nativeSQL(sql);
        PreparedStatement ps = super.prepareStatement(sNative, resultSetType, resultSetConcurrency);
        return ps;
    }


    public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
        String sNative = nativeSQL(sql);
        PreparedStatement ps = super.prepareStatement(sNative, resultSetType, resultSetConcurrency, resultSetHoldability);
        return ps;
    }


    public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys) throws SQLException {
        String sNative = nativeSQL(sql);
        PreparedStatement ps = super.prepareStatement(sNative, autoGeneratedKeys);
        return ps;
    }


    public PreparedStatement prepareStatement(String sql, int[] columnIndexes) throws SQLException {
        String sNative = nativeSQL(sql);
        PreparedStatement ps = super.prepareStatement(sNative, columnIndexes);
        return ps;
    }


    public PreparedStatement prepareStatement(String sql, String[] columnNames) throws SQLException {
        String sNative = nativeSQL(sql);
        PreparedStatement ps = super.prepareStatement(sNative, columnNames);
        return ps;
    }


    public CallableStatement prepareCall(String sql) throws SQLException {
        String sNative = nativeSQL(sql);
        CallableStatement cs = super.prepareCall(sNative);
        return cs;
    }


    public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
        String sNative = nativeSQL(sql);
        CallableStatement cs = super.prepareCall(sNative, resultSetType, resultSetConcurrency);
        return cs;
    }


    public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
        String sNative = nativeSQL(sql);
        CallableStatement cs = super.prepareCall(sNative, resultSetType, resultSetConcurrency, resultSetHoldability);
        return cs;
    }


    public Object createDatalinkObject() throws SQLException {
        return createBlob();
    }
}