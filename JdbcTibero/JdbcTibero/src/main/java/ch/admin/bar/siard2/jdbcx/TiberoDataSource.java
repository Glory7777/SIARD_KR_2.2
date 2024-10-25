/*======================================================================
CubridDataSource implements a wrapped Cubrid DataSource
Version     : $Id: $
Application : SIARD2
Description : TiberoDataSource implements a wrapped Cubrid DataSource.
Platform    : Java 7
------------------------------------------------------------------------
Copyright  : 2016, Enter AG, Rüti ZH, Switzerland
Created    : 26.10.2016, Simon Jutz
======================================================================*/

package ch.admin.bar.siard2.jdbcx;

import ch.admin.bar.siard2.jdbc.TiberoConnection;
import ch.enterag.utils.jdbcx.BaseDataSource;
import ch.enterag.utils.logging.IndentLogger;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

import com.tmax.tibero.jdbc.ext.TbDataSource;

/* ===============================================================================- */

/**
 * TiberoDataSource implements a wrapped Tibero DataSource
 *
 * @author Simon Jutz
 */
public class TiberoDataSource extends BaseDataSource implements DataSource{
    private static IndentLogger _il = IndentLogger.getIndentLogger(TiberoDataSource.class.getName());

    public TiberoDataSource() throws SQLException {
        super(new TbDataSource());
    }

    public TiberoDataSource(String sUrl, String sUser, String sPassword) throws SQLException {
        super(new TbDataSource());
        this.setUrl(sUrl);
        this.setUser(sUser);
        this.setPassword(sPassword);

    }

    public Connection getConnection() throws SQLException {
        return new TiberoConnection(super.getConnection());
    }

    private com.tmax.tibero.jdbc.ext.TbDataSource getUnwrapped() {
        com.tmax.tibero.jdbc.ext.TbDataSource ssds = null;

        try {
            ssds = (com.tmax.tibero.jdbc.ext.TbDataSource)this.unwrap(DataSource.class);
        } catch (SQLException var3) {
            SQLException se = var3;
            _il.exception(se);
        }

        return ssds;
    }

    public void setUrl(String url) throws SQLException {
        this.getUnwrapped().setURL(url);
    }

    public String getUrl() throws SQLException {
        return this.getUnwrapped().getURL();
    }

    public void setUser(String user) {
        this.getUnwrapped().setUser(user);
    }

    public String getUser() {
        return this.getUnwrapped().getUser();
    }

    public void setPassword(String password) {
        this.getUnwrapped().setPassword(password);
    }

    public void setDescription(String description) {
        this.getUnwrapped().setDescription(description);
    }

    public String getDescription() {
        return this.getUnwrapped().getDescription();
    }

    static {
        System.setProperty("tibero.jdbc.getObjectReturnsXMLType", "false");
    }

}

