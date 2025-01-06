package ch.admin.bar.dbexception;


public class DbOutOfMemoryException extends OutOfMemoryError{

    private static final String DEFAULT_MESSAGE = "OutOfMemoryError 발생 : 메모리가 부족하여 작업을 완료할 수 없습니다.";

    public DbOutOfMemoryException() {
        super(DEFAULT_MESSAGE);
    }

}
