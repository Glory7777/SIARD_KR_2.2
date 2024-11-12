package ch.admin.bar.dbexception;

import ch.admin.bar.dbexception.vendor.ErrorCode;
import lombok.Getter;

import java.sql.SQLException;

@Getter
public class ApplicationException extends Exception {

    private final SQLException originalException;
    private final ErrorCode errorCode;

    public ApplicationException(SQLException sqlException, ErrorCode errorCode) {
        super(sqlException.getMessage(), sqlException.getCause());
        this.originalException = sqlException;
        this.errorCode = errorCode;
    }

}
