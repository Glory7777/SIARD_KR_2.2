package ch.admin.bar.siard2.jdbc;

import ch.enterag.utils.jdbc.BaseDriver;
import ch.enterag.utils.logging.IndentLogger;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.SQLException;
import java.util.Properties;


public class MySqlDriver extends BaseDriver implements Driver {
    private static IndentLogger _il = IndentLogger.getIndentLogger(MySqlDriver.class.getName());


    public static final String sMYSQL_SCHEME = "mysql";


    public static final String sMYSQL_URL_PREFIX = "jdbc:mysql:";


    public static String getUrl(String sDatabaseName, boolean bNoSsl) {
        String sUrl = sDatabaseName;
        if (!sUrl.startsWith("jdbc:mysql:"))
            sUrl = "jdbc:mysql://" + sDatabaseName;
        if (bNoSsl)
            sUrl = sUrl + "?verifyServerCertificate=false&useSSL=false&requireSSL=false&allowPublicKeyRetrieval=true";
        return sUrl;
    }

    public static void register() {

        try {
            BaseDriver.register(new MySqlDriver(), "com.mysql.cj.jdbc.Driver", "jdbc:mysql://localhost:3306/testschema");
        } catch (Exception e) {
            throw new Error(e);
        }

    }

    static {
        register();
    }


    public Connection connect(String url, Properties info) throws SQLException {
        _il.enter(new Object[]{url, info});

        if (info == null) {
            info = new Properties();
        }

        info.setProperty("useCursorFetch", "true");
        info.setProperty("defaultFetchSize", "1");
        info.setProperty("enableEscapeProcessing", "false");
        info.setProperty("processEscapeCodesForPrepStmts", "false");
        info.setProperty("sessionVariables", "sql_mode='ANSI'"); // NO_BACKSLASH_ESCAPSES 설정 제거
        Connection conn = super.connect(url, info);
        if (conn != null)
            conn = new MySqlConnection(conn);
        _il.exit(conn);
        return conn;
    }


    public boolean acceptsURL(String url) {
        _il.enter(new Object[]{url});
        boolean bAccepts = url.startsWith("jdbc:mysql:");
        _il.exit(Boolean.valueOf(bAccepts));
        return bAccepts;
    }
}
