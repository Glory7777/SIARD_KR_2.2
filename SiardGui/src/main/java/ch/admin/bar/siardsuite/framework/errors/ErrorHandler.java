package ch.admin.bar.siardsuite.framework.errors;

import ch.admin.bar.siardsuite.framework.i18n.DisplayableText;
import ch.admin.bar.siardsuite.framework.i18n.keys.I18nKey;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import javafx.application.Platform;

import java.util.List;
import java.util.Optional;

/**
 * Service for handling errors and mapping them into failures (which contains displayable information for users)
 */
@Slf4j
@RequiredArgsConstructor
public class ErrorHandler {

    static final I18nKey UNEXPECTED_ERROR_TITLE = I18nKey.of("errors.unexpected.title");
    static final I18nKey UNEXPECTED_ERROR_MESSAGE = I18nKey.of("errors.unexpected.message");
    static final I18nKey VIDEO_FILE_DETECTED_TITLE = I18nKey.of("errors.videoFileDetected.title");
    static final I18nKey VIDEO_FILE_DETECTED_MESSAGE = I18nKey.of("errors.videoFileDetected.message");

    private final FailureDisplay failureDisplay;
    private final List<HandlingInstruction> generalHandlingInstructions;

    /**
     * Handles a throwable by mapping it to a failure and displaying it.
     *
     * @param throwable The throwable to handle.
     */
    public void handle(final Throwable throwable) {
        val definition = mapToFailure(throwable);

        // JavaFX UI 스레드에서 다이얼로그 표시
        Platform.runLater(() -> {
            try {
                failureDisplay.displayFailure(definition);
            } catch (Exception e) {
                log.error("Failed to display error dialog", e);
            }
        });
    }

    /**
     * Maps a throwable to a failure representation.
     *
     * @param throwable The throwable to map.
     * @return The mapped failure.
     */
    public Failure mapToFailure(Throwable throwable) {
        // 동영상 파일 관련 오류 감지
        if (isVideoFileError(throwable)) {
            log.error("Video file detected in data processing", throwable);
            return Failure.builder()
                    .title(DisplayableText.of(VIDEO_FILE_DETECTED_TITLE))
                    .message(DisplayableText.of(VIDEO_FILE_DETECTED_MESSAGE))
                    .throwable(Optional.of(throwable))
                    .build();
        }
        
        return tryFindMatchingWarningDefinition(throwable)
                .map(handlingInstruction -> Failure.builder()
                        .title(handlingInstruction.getTitle())
                        .message(handlingInstruction.getMessage())
                        .throwable(Optional.of(throwable))
                        .build())
                .orElseGet(() -> {
                    log.error("Unhandled exception", throwable);
                    return Failure.builder()
                            .title(DisplayableText.of(UNEXPECTED_ERROR_TITLE))
                            .message(DisplayableText.of(UNEXPECTED_ERROR_MESSAGE))
                            .throwable(Optional.of(throwable))
                            .build();
                });
    }

    /**
     * Tries to find a matching handling instruction for the given throwable.
     * <p>
     * It is a recursive search: If no matching {@link HandlingInstruction} is found
     * for a given {@link Throwable}, but the {@link Throwable#getCause()} does return a cause,
     * then the registered handling instructions are searched for a match with that cause.
     *
     * @param throwable The throwable to find a matching handling instruction for.
     * @return The optional matching handling instruction.
     */
    private Optional<HandlingInstruction> tryFindMatchingWarningDefinition(final Throwable throwable) {
        val matching = generalHandlingInstructions.stream()
                .filter(handlingInstruction -> handlingInstruction.getMatcher().test(throwable))
                .findFirst();

        if (!matching.isPresent()) {
            return Optional.ofNullable(throwable.getCause())
                    .flatMap(this::tryFindMatchingWarningDefinition);
        }

        return matching;
    }
    
    /**
     * 동영상 파일 관련 오류인지 확인
     * @param throwable 확인할 예외
     * @return 동영상 파일 관련 오류이면 true
     */
    private boolean isVideoFileError(Throwable throwable) {
        if (throwable == null) {
            return false;
        }
        
        String message = throwable.getMessage();
        if (message != null && (message.contains("VIDEO_FILE_DETECTED") || 
                               message.contains("동영상 파일이 포함되어 있어 처리할 수 없습니다"))) {
            return true;
        }
        
        // 원인 예외도 재귀적으로 확인
        return isVideoFileError(throwable.getCause());
    }
}
