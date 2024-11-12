package ch.admin.bar.dbexception;

import ch.admin.bar.dbexception.handler.DatabaseExceptionHandler;
import ch.admin.bar.dbexception.vendor.ErrorCode;

import java.sql.SQLException;

public class DatabaseExceptionHandlerHelper {

    private DatabaseExceptionHandlerHelper() {
    }

    public static void doHandleSqlException(String databaseProductName, String methodHint, SQLException sqlException) {
        if (databaseProductName == null || databaseProductName.isEmpty()) return;
        ErrorCode.findByNameAndException(databaseProductName, methodHint, sqlException)
                .ifPresent(errorCode -> {
                    ApplicationException applicationException = new ApplicationException(sqlException, errorCode);
                    DatabaseExceptionHandler exceptionHandler = DatabaseExceptionHandlerFactory.getHandler(databaseProductName);
                    exceptionHandler.handleException(applicationException);
                });
    }
}
