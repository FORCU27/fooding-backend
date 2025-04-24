package im.fooding.core.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    // System Error
    ACCESS_DENIED_EXCEPTION(HttpStatus.UNAUTHORIZED, "0001", "권한이 없습니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "0002", "서버 에러 입니다."),
    NOT_FOUND(HttpStatus.NOT_FOUND, "0003", "요청하신 페이지를 찾을 수 없습니다."),
    METHOD_ARGUMENT_NOT_VALID(HttpStatus.BAD_REQUEST, "0004", "잘못된 파라미터 요청입니다."),
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "0005", "허용되지 않는 메소드입니다."),
    NOT_READABLE(HttpStatus.BAD_REQUEST, "0006", "JSON 형식에 오류가 있습니다."),
    MISSING_REQUEST_PARAMETER(HttpStatus.BAD_REQUEST, "0007", "필수 파라미터가 누락되었습니다."),
    ENCRYPT_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "0008", "암호화에 실패했습니다."),
    DECRYPT_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "0009", "복호화에 실패했습니다."),
    REFRESH_TOKEN_INVALID(HttpStatus.BAD_REQUEST, "0010", "잘못된 토큰 정보입니다."),
    REFRESH_TOKEN_FAILED(HttpStatus.BAD_REQUEST, "0011", "토큰 갱신에 실패하셨습니다."),
    DATABASE_EXCEPTION(HttpStatus.INTERNAL_SERVER_ERROR, "0012", "데이터베이스 에러 입니다."),
    FEIGN_UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "0013", "토큰이 만료되었습니다."),
    OAUTH_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "0014", "소셜로그인에 실패하셨습니다."),

    // 회원
    USER_NOT_FOUND(HttpStatus.BAD_REQUEST, "1000", "가입된 정보가 없습니다."),
    LOGIN_FAILED(HttpStatus.BAD_REQUEST, "1001", "로그인에 실패하셨습니다."),
    DUPLICATED_REGISTER_EMAIL(HttpStatus.BAD_REQUEST, "1002", "이미 가입된 이메일입니다."),
    DUPLICATED_NICKNAME(HttpStatus.BAD_REQUEST, "1003", "이미 가입된 닉네임입니다."),
    UNSUPPORTED_SOCIAL(HttpStatus.BAD_REQUEST, "1004", "지원하지 않는 소셜로그인 입니다."),
    EMAIL_CONSENT_REQUIRED(HttpStatus.BAD_REQUEST, "1005", "이메일 제공 동의가 필요합니다."),

    // 가게
    STORE_NOT_FOUND(HttpStatus.BAD_REQUEST, "2000", "등록된 가게 정보가 없습니다."),
    STORE_IMAGE_NOT_FOUND(HttpStatus.BAD_REQUEST, "2001", "등록된 가게 이미지가 없습니다."),

    // 웨이팅
    WAITING_NOT_FOUND(HttpStatus.BAD_REQUEST, "3000", "등록된 웨이팅 정보가 없습니다."),
    WAITING_NOT_OPENED(HttpStatus.BAD_REQUEST, "3001", "웨이팅이 오픈상태가 아닙니다."),
    STORE_WAITING_NOT_FOUND(HttpStatus.BAD_REQUEST, "3002", "등록된 가게 웨이팅이 없습니다."),
    STORE_WAITING_ILLEGAL_STATE_CALL(HttpStatus.BAD_REQUEST, "3003", "호출할 가능한 웨이팅 상태가 아닙니다."),
    ACTIVE_WAITING_SETTING_NOT_FOUND(HttpStatus.BAD_REQUEST, "3004", "활성화된 웨이팅 세팅이 없습니다."),
    STORE_WAITING_ILLEGAL_STATE_CANCEL(HttpStatus.BAD_REQUEST, "3005", "취소 가능한 웨이팅 상태가 아닙니다."),
    STORE_WAITING_ALREADY_WAITING(HttpStatus.BAD_REQUEST, "3006", "이미 가게 웨이팅 상태입니다."),
    STORE_WAITING_ILLEGAL_STATE_SEAT(HttpStatus.BAD_REQUEST, "3007", "착성 가능한 웨이팅 상태가 아닙니다."),
    WAITING_STATUS_STORE_WAITING_EXIST(HttpStatus.BAD_REQUEST, "3008", "남아있는 대기중인 웨이팅이 존재합니다."),

    // 디바이스
    DEVICE_NOT_FOUND(HttpStatus.BAD_REQUEST, "7000", "등록된 디바이스가 없습니다."),

    // 알림
    NOTIFICATION_NOT_FOUND(HttpStatus.BAD_REQUEST, "5000", "등록된 알림이 없습니다."),
    USER_NOTIFICATION_NOT_FOUND(HttpStatus.BAD_REQUEST, "5001", "사용자 알림을 찾을 수 없습니다."),
    USER_NOTIFICATION_FORBIDDEN(HttpStatus.FORBIDDEN, "5002", "해당 알림에 접근할 권한이 없습니다.");

    private final HttpStatus status;

    private final String code;

    private String message;

    ErrorCode(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
