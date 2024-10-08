package ch.enterag.utils.jdbcx;

import ch.enterag.utils.logging.IndentLogger;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;
import javax.sql.DataSource;


public abstract class BaseDataSource
        implements DataSource {
    private static IndentLogger _il = IndentLogger.getIndentLogger(BaseDataSource.class.getName());

    private DataSource _dsWrapped = null;


    public BaseDataSource(DataSource dsWrapped) {
        this._dsWrapped = dsWrapped;
    }


    public Connection getConnection() throws SQLException {
        return this._dsWrapped.getConnection();
    }


    public Connection getConnection(String username, String password) throws SQLException {
        return this._dsWrapped.getConnection(username, password);
    }


    public int getLoginTimeout() throws SQLException {
        return this._dsWrapped.getLoginTimeout();
    }


    public void setLoginTimeout(int seconds) throws SQLException {
        this._dsWrapped.setLoginTimeout(seconds);
    }


    @Deprecated
    public PrintWriter getLogWriter() throws SQLException {
        return this._dsWrapped.getLogWriter();
    }


    @Deprecated
    public void setLogWriter(PrintWriter out) throws SQLException {
        this._dsWrapped.setLogWriter(out);
    }


    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        Logger loggerParent = null;

        try {
            loggerParent = this._dsWrapped.getParentLogger();
        } catch (Exception e) {
            _il.exception(e);
        } catch (Error e) {
            _il.error(e);
        }
        if (loggerParent == null)
            loggerParent = _il.getParent();
        return loggerParent;
    }


    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return (iface == DataSource.class);
    }


    public <T> T unwrap(Class<T> iface) throws SQLException {
        DataSource dataSource = null;
        T wrapped = null;
        if (isWrapperFor(iface))
            dataSource = this._dsWrapped;
        return (T) dataSource;
    }
}