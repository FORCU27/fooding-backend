package im.fooding.core.model.waiting;

import im.fooding.core.global.exception.ApiException;
import im.fooding.core.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum StoreWaitingChannel {

    IN_PERSON("IN_PERSON"),
    ONLINE("ONLINE")
    ;

    private final String value;

    public static StoreWaitingChannel of(String value) {
        try {
            return valueOf(value);
        } catch (IllegalArgumentException e) {
            throw new ApiException(ErrorCode.METHOD_ARGUMENT_NOT_VALID);
        }
    }
    }
