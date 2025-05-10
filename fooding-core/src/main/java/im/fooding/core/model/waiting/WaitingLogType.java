package im.fooding.core.model.waiting;

import im.fooding.core.global.exception.ApiException;
import im.fooding.core.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum WaitingLogType {
    WAITING_REGISTRATION("WAITING_REGISTRATION"),
    ENTRY("ENTRY")
    ;

    private final String value;

    public static WaitingLogType of(String value) {
        try {
            return valueOf(value);
        } catch (IllegalArgumentException e) {
            throw new ApiException(ErrorCode.METHOD_ARGUMENT_NOT_VALID);
        }
    }
}
