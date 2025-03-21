package im.fooding.core.global.exception;

import lombok.Getter;

@Getter
public class ApiException extends RuntimeException {
    private ErrorCode errorCode;

    public ApiException(ErrorCode e) {
        super(e.getMessage());
        this.errorCode = e;
    }
}
