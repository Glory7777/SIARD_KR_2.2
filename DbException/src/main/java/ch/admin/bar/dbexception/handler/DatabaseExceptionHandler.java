package ch.admin.bar.dbexception.handler;

import ch.admin.bar.dbexception.ApplicationException;

public interface DatabaseExceptionHandler {
    void handleException(ApplicationException applicationException);
}
