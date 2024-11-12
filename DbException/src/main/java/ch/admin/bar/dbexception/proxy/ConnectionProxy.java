package ch.admin.bar.dbexception.proxy;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.SQLException;

import static ch.admin.bar.dbexception.DatabaseExceptionHandlerHelper.doHandleSqlException;

@Slf4j
public class ConnectionProxy implements InvocationHandler {

    private final Connection connection;

    public ConnectionProxy(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        try {
            return method.invoke(connection, args);
        } catch (Exception e) {
            log.error("caught by proxy. trying to handle exception");
            String databaseProductName = connection.getMetaData().getDatabaseProductName();
            handleSqlException(databaseProductName, e);
            throw e;
        }
    }

    public static Connection createProxy(Connection realConnection) {
        return (Connection) Proxy.newProxyInstance(realConnection.getClass().getClassLoader(),
                new Class<?>[]{Connection.class},
                new ConnectionProxy(realConnection));
    }

    private void handleSqlException(String databaseProductName, Throwable cause) {
        if (cause instanceof SQLException) {
            SQLException sqlException = (SQLException) cause;
            doHandleSqlException(databaseProductName, null, sqlException);
        }
    }

}
