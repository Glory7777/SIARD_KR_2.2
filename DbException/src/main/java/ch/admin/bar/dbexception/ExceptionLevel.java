package ch.admin.bar.dbexception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ExceptionLevel {

    INFO,       // Minor issues; log and continue
    WARNING,    // Issues that need attention but are not critical
    ERROR,      // Significant issues; may require alert
    CRITICAL,  // should rethrow
    ;
}
