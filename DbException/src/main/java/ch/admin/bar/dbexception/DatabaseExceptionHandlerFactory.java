package ch.admin.bar.dbexception;

import ch.admin.bar.dbexception.handler.*;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class DatabaseExceptionHandlerFactory {

    private static final Map<String, Supplier<DatabaseExceptionHandler>> handlerMap = new HashMap<>();

    static {
        handlerMap.put("oracle", OracleExceptionHandler::new);
        handlerMap.put("mysql", MysqlExceptionHandler::new);
        handlerMap.put("cubrid", CubridExceptionHandler::new);
        handlerMap.put("default", GenericExceptionHandler::new);
    }

    public static DatabaseExceptionHandler getHandler(String databaseName) {
        return handlerMap.getOrDefault(databaseName.toLowerCase(), handlerMap.get("default")).get();
    }

}
