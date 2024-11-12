package ch.admin.bar.dbexception.handler;

import ch.admin.bar.dbexception.ApplicationException;

public abstract class AbstractExceptionHandler implements DatabaseExceptionHandler {

    public void handleException(ApplicationException e) {
        switch (e.getErrorCode().getExceptionLevel()) {
            case INFO:
                log(e);
                break;
            case WARNING:
                showWarningAlert(e);
                break;
            case ERROR:
                showErrorAlert(e);
                break;
            case CRITICAL:
                rethrowAsRuntimeException(e);
                break;
        }
    }

    protected void log(ApplicationException e) {
        String methodHint = e.getErrorCode().getMethodHint();
        System.out.println("예외 처리를 스킵합니다. 관련 메서드 :: " + methodHint + "/ 원본 메시지 :: " + e.getMessage());
    }

    protected void showWarningAlert(ApplicationException e) {
        // TODO :: add if necessary
    }

    protected void showErrorAlert(ApplicationException e) {
        // TODO :: add if necessary
    }

    protected void rethrowAsRuntimeException(ApplicationException e) {
        System.out.println("thrown by handler:: " + e.getMessage());
        throw new RuntimeException(buildCustomMessage(e), e.getOriginalException());
    }

    protected String buildCustomMessage(ApplicationException e) {
        return e.getErrorCode().getCustomMessage() + " / 원본 메시지 : " + e.getOriginalException().getMessage();
    }
}
