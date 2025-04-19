package im.fooding.core.global.exception;

import lombok.Getter;

@Getter
public class ApiException extends RuntimeException {
    private ErrorCode errorCode;
    private String detailMessage;

    public boolean isNotifyTarget() {
        return switch (errorCode) {
            case OAUTH_FAILED, INTERNAL_SERVER_ERROR, DATABASE_EXCEPTION -> true;
            default -> false;
        };
    }

    public ApiException(ErrorCode e) {
        super(e.getMessage());
        this.errorCode = e;
    }

    public ApiException(ErrorCode errorCode, String detailMessage) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
        this.detailMessage = detailMessage;
    }
}
