package ch.admin.bar.dbexception.vendor;

public class ErrorCodeMessage {

    private final ErrorCode errorCode;
    private final String originalMessage;

    public ErrorCodeMessage(ErrorCode errorCode, String originalMessage) {
        this.errorCode = errorCode;
        this.originalMessage = originalMessage;
    }

    public String buildErrorMessage() {
        return this.errorCode.getCustomMessage() + "원본 메시지 : " + originalMessage;
    }

}
