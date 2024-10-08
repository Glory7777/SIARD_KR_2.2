package ch.enterag.utils.jdbc;

import ch.enterag.utils.logging.IndentLogger;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.DriverPropertyInfo;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Logger;


public abstract class BaseDriver
        implements Driver {
    private static IndentLogger _il = IndentLogger.getIndentLogger(BaseDriver.class.getName());

    public static final String sJDBC_SCHEME = "jdbc";

    private static Map<String, Driver> _mapWrappedDrivers = new HashMap<>();

    private Driver _driverWrapped = null;


    private void throwUndefinedMethod(AbstractMethodError ame) throws SQLFeatureNotSupportedException {
        throw new SQLFeatureNotSupportedException("Undefined JDBC method!", ame);
    }


    private static String getJdbcDriver(String sUrl) {
        String sDriver = null;
        if (sUrl.startsWith("jdbc:")) {

            sDriver = sUrl.substring("jdbc:".length());
            int i = sDriver.indexOf(':');
            if (i > 0) {
                sDriver = sDriver.substring(0, i);
            } else {
                throw new IllegalArgumentException("Second part of JDBC URL could not be extracted!");
            }
        } else {
            throw new IllegalArgumentException("JDBC URL must start with \"jdbc:\"");
        }
        return sDriver;
    }


    public static void listRegisteredDrivers() {
        System.out.println("DriverManager:");
        Enumeration<Driver> enumDriver = DriverManager.getDrivers();
        while (enumDriver.hasMoreElements()) {

            Driver driver = enumDriver.nextElement();
            System.out.println("  " + driver.getClass().getName());
        }
        System.out.println("DriverMapping:");
        for (Iterator<String> iterDriver = _mapWrappedDrivers.keySet().iterator(); iterDriver.hasNext(); ) {

            String sDriver = iterDriver.next();
            Driver driverMapped = _mapWrappedDrivers.get(sDriver);
            System.out.println("  " + sDriver + ": " + driverMapped.getClass().getName());
        }
    }


    protected static void register(BaseDriver driverRegister, String sWrappedDriverClassName, String sUrl) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, SQLException {
        Driver driverWrapped = null;

        Enumeration<Driver> enumDriver = DriverManager.getDrivers();
        while (enumDriver.hasMoreElements()) {

            Driver driver = enumDriver.nextElement();
            if (driver.getClass().getName().equals(sWrappedDriverClassName) || driver
                    .getClass().equals(driverRegister.getClass())) {

                if (driver.getClass().getName().equals(sWrappedDriverClassName))
                    driverWrapped = driver;
                DriverManager.deregisterDriver(driver);
            }
        }
        if (driverWrapped == null) {


            Class<?> clsBaseDriver = Class.forName(sWrappedDriverClassName);
            driverWrapped = (Driver) clsBaseDriver.getConstructor(new Class[0]).newInstance(new Object[0]);
            DriverManager.deregisterDriver(driverWrapped);
        }
        if (driverWrapped != null) {

            _mapWrappedDrivers.put(getJdbcDriver(sUrl), driverWrapped);
            DriverManager.registerDriver(driverRegister);
        } else {

            throw new ClassNotFoundException("Driver for " + sWrappedDriverClassName + " could not be loaded for URL " + sUrl);
        }
    }


    public Connection connect(String url, Properties info) throws SQLException {
        _il.enter(new Object[]{url, info});
        Connection conn = null;
        if (acceptsURL("jdbc:" + getJdbcDriver(url) + ":anything")) {

            this._driverWrapped = _mapWrappedDrivers.get(getJdbcDriver(url));
            conn = this._driverWrapped.connect(url, info);
        }
        _il.exit(conn);
        return conn;
    }


    public boolean acceptsURL(String url) throws SQLException {
        _il.enter(new Object[]{url});
        _il.exit(String.valueOf(false));
        throw new IllegalArgumentException("acceptsURL must be overridden!");
    }


    public DriverPropertyInfo[] getPropertyInfo(String url, Properties info) throws SQLException {
        return this._driverWrapped.getPropertyInfo(url, info);
    }


    public int getMajorVersion() {
        return this._driverWrapped.getMajorVersion();
    }


    public int getMinorVersion() {
        return this._driverWrapped.getMinorVersion();
    }


    public boolean jdbcCompliant() {
        return this._driverWrapped.jdbcCompliant();
    }


    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        Logger loggerParent = null;
        try {
            loggerParent = this._driverWrapped.getParentLogger();
        } catch (AbstractMethodError ame) {
            throwUndefinedMethod(ame);
        }
        if (loggerParent == null)
            loggerParent = _il.getParent();
        return loggerParent;
    }
}
