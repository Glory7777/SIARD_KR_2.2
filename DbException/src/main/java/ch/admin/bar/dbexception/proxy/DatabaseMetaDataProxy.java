package ch.admin.bar.dbexception.proxy;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;

import static ch.admin.bar.dbexception.DatabaseExceptionHandlerHelper.doHandleSqlException;

@Slf4j
public class DatabaseMetaDataProxy implements InvocationHandler {

    private final DatabaseMetaData databaseMetaData;

    public DatabaseMetaDataProxy(DatabaseMetaData databaseMetaData) {
        this.databaseMetaData = databaseMetaData;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        try {
            return method.invoke(databaseMetaData, args);
        } catch (Exception e) {
            log.error("caught by proxy. trying to handle exception");
            String databaseProductName = databaseMetaData.getDatabaseProductName();
            handleSqlException(databaseProductName, e);
            throw e;
        }
    }

    public static DatabaseMetaData createProxy(DatabaseMetaData databaseMetaData) {
        return (DatabaseMetaData) Proxy.newProxyInstance(databaseMetaData.getClass().getClassLoader(),
                new Class<?>[]{Connection.class},
                new DatabaseMetaDataProxy(databaseMetaData));
    }

    private void handleSqlException(String databaseProductName, Throwable cause) {
        if (cause instanceof SQLException) {
            SQLException sqlException = (SQLException) cause;
            doHandleSqlException(databaseProductName, null, sqlException);
        }
    }
}
