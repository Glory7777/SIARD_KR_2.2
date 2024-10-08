package ch.admin.bar.siard2.jdbcx;

import ch.admin.bar.siard2.jdbc.MySqlConnection;
import ch.enterag.utils.jdbcx.BaseDataSource;
import ch.enterag.utils.logging.IndentLogger;
import com.mysql.cj.jdbc.MysqlDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;


public class MySqlDataSource
        extends BaseDataSource
        implements DataSource {
    private static IndentLogger _il = IndentLogger.getIndentLogger(MySqlDataSource.class.getName());


    public MySqlDataSource() throws SQLException {
        super((DataSource) new MysqlDataSource());
        MysqlDataSource ds = (MysqlDataSource) unwrap(DataSource.class);
        ds.setUseCursorFetch(true);
        ds.setDefaultFetchSize(1);
        ds.setEnableEscapeProcessing(false);
        ds.setProcessEscapeCodesForPrepStmts(false);
        ds.setSessionVariables("sql_mode='ANSI,NO_BACKSLASH_ESCAPES'");
    }


    public MySqlDataSource(String sUrl, String sUser, String sPassword) throws SQLException {
        super((DataSource) new MysqlDataSource());
        setUrl(sUrl);
        setUser(sUser);
        setPassword(sPassword);
        MysqlDataSource ds = (MysqlDataSource) unwrap(DataSource.class);
        ds.setUseCursorFetch(true);
        ds.setDefaultFetchSize(1);
        ds.setEnableEscapeProcessing(false);
        ds.setProcessEscapeCodesForPrepStmts(false);
        ds.setSessionVariables("sqlmode='ANSI,NO_BACKSLASH_ESCAPES'");
    }


    public Connection getConnection() throws SQLException {
        return (Connection) new MySqlConnection(super.getConnection());
    }


    private MysqlDataSource getUnwrapped() {
        MysqlDataSource msds = null;
        try {
            msds = (MysqlDataSource) unwrap(DataSource.class);
        } catch (SQLException se) {
            _il.exception(se);
        }
        return msds;
    }


    public String getDatabaseName() {
        return getUnwrapped().getDatabaseName();
    }


    public String getUrl() {
        return getUnwrapped().getUser();
    }


    public String getUser() {
        return getUnwrapped().getUser();
    }


    public void setDatabaseName(String dbName) {
        getUnwrapped().setDatabaseName(dbName);
    }


    public void setPassword(String password) {
        getUnwrapped().setPassword(password);
    }


    public void setUrl(String url) {
        getUnwrapped().setUrl(url);
    }


    public void setUser(String user) {
        getUnwrapped().setUser(user);
    }
}


/* Location:              C:\Users\lenovo\IdeaProjects\siard-lib\lib\jdbcmysql-2.2.2.jar!\ch\admin\bar\siard2\jdbcx\MySqlDataSource.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */