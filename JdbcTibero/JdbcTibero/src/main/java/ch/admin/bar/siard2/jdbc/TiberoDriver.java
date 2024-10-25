/*======================================================================
TiberoDriver implements a wrapped Tibero Driver.
Version     : $Id: $
Application : SIARD2
Description : TiberoDriver implements a wrapped Tibero Driver.
Platform    : Java 7
------------------------------------------------------------------------
Copyright  : 2016, Enter AG, Rüti ZH, Switzerland
Created    : 26.10.2016, Simon Jutz
======================================================================*/
package ch.admin.bar.siard2.jdbc;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.SQLException;
import java.util.Properties;

import ch.enterag.utils.jdbc.BaseDriver;
import ch.enterag.utils.logging.IndentLogger;

/* =============================================================================== */
/**
 * TiberoDriver implements a wrapped Tibero Driver
 *
 */
public class TiberoDriver extends BaseDriver implements Driver
{
    private static final IndentLogger _il = IndentLogger.getIndentLogger(TiberoDriver.class.getName());

    /** protocol sub scheme for Tibero JDBC URL */
    public static final String sTIBERO_SCHEME = "tibero";

    /** URL prefix for Tibero JDBC URL */
    public static final String sTIBERO_URL_PREFIX = sJDBC_SCHEME + ":" + sTIBERO_SCHEME + ":";

    /** URL for database name.
     * @param sDatabaseName host:port/database, e.g. localhost:8629/tibero
     * @return JDBC URL.
     */
    public static String getUrl(String sDatabaseName, boolean bNoSsl) {
        String sUrl = sDatabaseName;
        if (!sUrl.startsWith(sTIBERO_URL_PREFIX)) {
            sUrl = sTIBERO_URL_PREFIX + "//" + sDatabaseName;
        }
        if (bNoSsl) {
            sUrl = sUrl + "?useSSL=false";
        }
        return sUrl;
    } /* getUrl */

    /** register this driver, replacing original Tibero driver */
    public static void register() {
        System.setProperty("jdbc.drivers", "com.tmax.tibero.tibero.jdbc.TbDriver");

        // tibero.jdbc.driver.TIBERODriver is excluded in acceptsURL
        try {
            BaseDriver.register(new TiberoDriver(), "com.tmax.tibero.tibero.jdbc.TbDriver", "jdbc:tibero://localhost:8629/tibero?user=sys&password=");
        } catch (Exception e) {
            throw new Error(e);
        }
    }

    /** replace Tibero driver by this, when this class is loaded */
    static {
        register();
    }

    /* ------------------------------------------------------------------------ */
    /**
     * {@inheritDoc}
     * @return the appropriately wrapped Connection
     */
    public Connection connect(String url, Properties info) throws SQLException {
        _il.enter(url, info);
        /* add/change properties */
        if (info == null) {
            info = new Properties();
        }
        /* configure properties as needed */
        info.setProperty("useCursorFetch", "true");
        info.setProperty("defaultFetchSize", "1");
        info.setProperty("enableEscapeProcessing", "false");
        info.setProperty("processEscapeCodesForPrepStmts", "false");

        Connection conn = super.connect(url, info);
        if (conn != null) {
            conn = new TiberoConnection(conn);
        }
        _il.exit(conn);
        return conn;
    } /* connect */

    /* ------------------------------------------------------------------------ */
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean acceptsURL(String url) {
        _il.enter(url);
        boolean bAccepts = url.startsWith("jdbc:tibero:");
        _il.exit(bAccepts);
        return bAccepts;
    } /* acceptsURL */

} /* class TIBERODriver */
