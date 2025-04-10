package im.fooding.core.model.waiting;

import im.fooding.core.global.exception.ApiException;
import im.fooding.core.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum StoreWaitingStatus {

    WAITING("WAITING"),
    SEATED("SEATED"),
    CANCELLED("CANCELLED")
    ;

    private final String value;

    public static StoreWaitingStatus of(String value) {
        try {
            return valueOf(value);
        } catch (IllegalArgumentException e) {
            throw new ApiException(ErrorCode.METHOD_ARGUMENT_NOT_VALID);
        }
    }
}
