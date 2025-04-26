package im.fooding.core.model.waiting;

import im.fooding.core.global.exception.ApiException;
import im.fooding.core.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum WaitingStatus {
    WAITING_OPEN("WAITING_OPEN"),
    IMMEDIATE_ENTRY("IMMEDIATE_ENTRY"),
    PAUSED("PAUSED"),
    WAITING_CLOSE("WAITING_CLOSE"),
    ;

    private final String value;

    public static WaitingStatus of(String value) {
        try {
            return valueOf(value);
        } catch (IllegalArgumentException e) {
            throw new ApiException(ErrorCode.METHOD_ARGUMENT_NOT_VALID);
        }
    }
}
