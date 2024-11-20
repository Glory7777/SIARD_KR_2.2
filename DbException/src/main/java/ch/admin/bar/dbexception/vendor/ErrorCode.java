package ch.admin.bar.dbexception.vendor;

import ch.admin.bar.dbexception.ExceptionLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Optional;
import java.util.function.Predicate;

import static ch.admin.bar.dbexception.vendor.DatabaseVendor.*;
import static ch.admin.bar.dbexception.vendor.ProcessStep.ARCHIVE;
import static ch.admin.bar.dbexception.vendor.ProcessStep.UPLOAD;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    ACCESS_DENIED(MYSQL, 1044, ExceptionLevel.CRITICAL, "스키마에 대한 접근 권한이 부족합니다. 유저 권한을 확인해주세요", "", ARCHIVE),
    INSUFFICIENT_PRIVILEGE_CREATE_SESSION(ORACLE, 1045, ExceptionLevel.CRITICAL, "커넥션 세션에 대한 권한이 없습니다. 유저에 CREATE SESSION 권한을 부여해주세요", "", ARCHIVE),
    TABLE_OR_VIEW_NOT_FOUND(ORACLE, 942, ExceptionLevel.INFO, "테이블 사이즈를 읽어오는 데 실패했습니다. 유저에게 테이블 dba_segments에 대한 SELECT 권한이 필요합니다.", "getTableSize", ARCHIVE),
    CANNOT_COMMUNICATE_BROKER(CUBRID, -21003, ExceptionLevel.CRITICAL, "DB 서버(브로커)와의 통신에 문제가 발생했습니다. SIARD 프로그램을 종료 후 다시 시도해주세요.", "", ARCHIVE),
    INSUFFICIENT_AUTH(CUBRID, -494, ExceptionLevel.CRITICAL, "유저 권한이 부족합니다. 현재 유저에 _db_data_type 테이블에 대한 SELECT 권한이 필요합니다", "", ARCHIVE),

    INSUFFICIENT_PRIVILEGE_CREATE(MYSQL, 1044, ExceptionLevel.CRITICAL, "스키마에 대한 접근 권한이 부족합니다. 유저에 생성할 스키마에 대한 CREATE, SELECT, INSERT 권한을 부여해주세요", "createSchema", UPLOAD),
//    MISSING_DATA(ORACLE, "missing data (tables without columns present", CustomExceptionHandler.ExceptionLevel.SKIP),
//    FAILED_TO_CONNECT(10003, "failed to connect to DB", CustomExceptionHandler.ExceptionLevel.THROWS),
//    SQL_SYNTAX_ERROR(10005, "table already exists", CustomExceptionHandler.ExceptionLevel.THROWS),
//    SQL_EXCEPTION(10006, "SQL exception", CustomExceptionHandler.ExceptionLevel.THROWS)
    ;

    final DatabaseVendor vendor;
    final int vendorCode;
    final ExceptionLevel exceptionLevel;
    final String customMessage;
    final String methodHint;
    final ProcessStep processStep;

    public static Optional<ErrorCode> findByNameAndException(String databaseName, String methodHint, SQLException sqlException) {
        Predicate<ErrorCode> methodHintPredicate = e -> isNullOrEmpty(methodHint) || e.methodHint.equals(methodHint);
        return Arrays.stream(ErrorCode.values())
                .filter(
                        e -> e.vendor.getName().equals(databaseName.toLowerCase()) &&
                                e.vendorCode == sqlException.getErrorCode() &&
                                methodHintPredicate.test(e))
                .findFirst();

    }

    private static boolean isNullOrEmpty(String str) {
        return str == null || str.isEmpty();
    }

}
