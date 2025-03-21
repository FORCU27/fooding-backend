package im.fooding.core.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    // System Error
    ACCESS_DENIED_EXCEPTION(HttpStatus.UNAUTHORIZED, "0001", "권한이 없습니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "0002", "서버 에러 입니다."),
    NOT_FOUND(HttpStatus.NOT_FOUND, "0003", "요청하신 페이지를 찾을 수 없습니다."),
    METHOD_ARGUMENT_NOT_VALID(HttpStatus.BAD_REQUEST, "0005", "잘못된 파라미터 요청입니다."),
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "0006", "허용되지 않는 메소드입니다."),
    NOT_READABLE(HttpStatus.BAD_REQUEST, "0007", "JSON 형식에 오류가 있습니다."),
    MISSING_REQUEST_PARAMETER(HttpStatus.BAD_REQUEST, "0008", "필수 파라미터가 누락되었습니다."),
    ENCRYPT_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "0010", "암호화에 실패했습니다."),
    DECRYPT_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "0011", "복호화에 실패했습니다."),

    // 회원
    USER_NOT_FOUND(HttpStatus.BAD_REQUEST, "1000", "해당 회원의 정보가 없습니다."),
    MANAGER_NOT_FOUND(HttpStatus.BAD_REQUEST, "1001", "해당 관리자의 정보가 없습니다."),
    LOGIN_FAILED(HttpStatus.BAD_REQUEST, "1002", "로그인에 실패하셨습니다."),
    DUPLICATED_REGISTER_EMAIL(HttpStatus.BAD_REQUEST, "1003", "이미 가입된 이메일입니다.");

    private final HttpStatus status;

    private final String code;

    private String message;

    ErrorCode(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
