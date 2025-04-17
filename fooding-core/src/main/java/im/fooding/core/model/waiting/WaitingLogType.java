package im.fooding.core.model.waiting;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum WaitingLogType {
    WAITING_REGISTRATION("WAITING_REGISTRATION"),
    ENTRY("ENTRY")
    ;

    private final String value;
}
