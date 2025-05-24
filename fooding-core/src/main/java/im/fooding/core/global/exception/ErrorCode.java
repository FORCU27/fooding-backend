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
    FILE_NOT_FOUND(HttpStatus.BAD_REQUEST, "0015", "파일의 정보가 없습니다."),
    FILE_SIZE_INVALID(HttpStatus.BAD_REQUEST, "0016", "파일 크기가 허용된 최대 크기를 초과했습니다."),
    FILE_UPLOAD_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "0017", "파일 업로드에 실패했습니다."),

    // 회원
    USER_NOT_FOUND(HttpStatus.BAD_REQUEST, "1000", "가입된 정보가 없습니다."),
    LOGIN_FAILED(HttpStatus.BAD_REQUEST, "1001", "로그인에 실패하셨습니다."),
    DUPLICATED_REGISTER_EMAIL(HttpStatus.BAD_REQUEST, "1002", "이미 가입된 이메일입니다."),
    DUPLICATED_NICKNAME(HttpStatus.BAD_REQUEST, "1003", "이미 가입된 닉네임입니다."),
    UNSUPPORTED_SOCIAL(HttpStatus.BAD_REQUEST, "1004", "지원하지 않는 소셜로그인 입니다."),
    EMAIL_CONSENT_REQUIRED(HttpStatus.BAD_REQUEST, "1005", "이메일 제공 동의가 필요합니다."),
    SOCIAL_LOGIN_ONLY(HttpStatus.BAD_REQUEST, "1006", "유저는 소셜로그인으로만 가입 가능합니다."),

    // 가게
    STORE_NOT_FOUND(HttpStatus.BAD_REQUEST, "2000", "등록된 가게 정보가 없습니다."),
    STORE_SERVICE_NOT_FOUND(HttpStatus.BAD_REQUEST, "2100", "등록된 가게 서비스가 없습니다."),
    STORE_IMAGE_NOT_FOUND(HttpStatus.BAD_REQUEST, "2001", "등록된 가게 이미지가 없습니다."),
    STORE_POST_NOT_FOUND(HttpStatus.BAD_REQUEST,"2200", "등록된 가게 소식이 없습니다."),


    // 웨이팅
    WAITING_NOT_FOUND(HttpStatus.BAD_REQUEST, "3000", "등록된 웨이팅 정보가 없습니다."),
    WAITING_NOT_OPENED(HttpStatus.BAD_REQUEST, "3001", "웨이팅이 오픈상태가 아닙니다."),
    STORE_WAITING_NOT_FOUND(HttpStatus.BAD_REQUEST, "3002", "등록된 가게 웨이팅이 없습니다."),
    STORE_WAITING_ILLEGAL_STATE_CALL(HttpStatus.BAD_REQUEST, "3003", "호출할 가능한 웨이팅 상태가 아닙니다."),
    ACTIVE_WAITING_SETTING_NOT_FOUND(HttpStatus.BAD_REQUEST, "3004", "활성화된 웨이팅 세팅이 없습니다."),
    STORE_WAITING_ILLEGAL_STATE_CANCEL(HttpStatus.BAD_REQUEST, "3005", "취소 가능한 웨이팅 상태가 아닙니다."),
    STORE_WAITING_ILLEGAL_STATE_REVERT(HttpStatus.BAD_REQUEST, "3006", "되돌리기 가능한 웨이팅 상태가 아닙니다."),
    STORE_WAITING_ILLEGAL_STATE_SEAT(HttpStatus.BAD_REQUEST, "3007", "착성 가능한 웨이팅 상태가 아닙니다."),
    WAITING_STATUS_STORE_WAITING_EXIST(HttpStatus.BAD_REQUEST, "3008", "남아있는 대기중인 웨이팅이 존재합니다."),
    DUPLICATED_STORE_BY_WAITING(HttpStatus.BAD_REQUEST, "3009", "가게와 관련된 웨이팅 정보가 존재합니다."),
    ALREADY_EXIST_ACTIVE_WAITING_SETTING(HttpStatus.BAD_REQUEST, "3010", "이미 활성화된 웨이팅 세팅이 존재합니다."),
    WAITING_SETTING_NOT_FOUND(HttpStatus.BAD_REQUEST, "3011", "등록된 웨이팅 설정 정보가 없습니다."),
    WAITING_USER_PRIVACY_POLICY_AGREED_REQUIRED(HttpStatus.BAD_REQUEST, "3012", "서비스 이용약관 동의는 필수입니다."),
    WAITING_USER_TERMS_AGREED_AGREED_REQUIRED(HttpStatus.BAD_REQUEST, "3013", "개인정보 수집 및 동의는 필수입니다."),
    WAITING_USER_THIRD_PARTY_AGREED_REQUIRED(HttpStatus.BAD_REQUEST, "3014", "개인정보 제3자 제공 동의는 필수입니다."),
    WAITING_USER_NOT_FOUND(HttpStatus.BAD_REQUEST, "3015", "등록된 웨이팅 유저 정보가 없습니다."),
    WAITING_LOG_NOT_FOUND(HttpStatus.BAD_REQUEST, "3016", "기록된 웨이팅 로그 정보가 없습니다."),
    STORE_WAITING_EXCEEDS_MAXIMUM_CAPACITY(HttpStatus.BAD_REQUEST, "3017", "허용된 최대 인원을 초과했습니다."),
    STORE_WAITING_BELOW_MINIMUM_CAPACITY(HttpStatus.BAD_REQUEST, "3018", "허용된 최소 인원보다 적습니다."),

    // 리워드
    REWARD_NOT_FOUND( HttpStatus.BAD_REQUEST, "4000", "해당 리워드 정보가 없습니다." ),
    REWARD_POINT_NOT_ENOUGH( HttpStatus.BAD_REQUEST, "4001", "리워드 누적 포인트가 부족합니다." ),

    REWARD_LOG_NOT_FOUND( HttpStatus.BAD_REQUEST, "4100", "해당 로그가 존재하지 않습니다" ),
    // 디바이스
    DEVICE_NOT_FOUND(HttpStatus.BAD_REQUEST, "7000", "등록된 디바이스가 없습니다."),
    
    // 알림
    NOTIFICATION_NOT_FOUND(HttpStatus.BAD_REQUEST, "5000", "등록된 알림이 없습니다."),
    USER_NOTIFICATION_NOT_FOUND(HttpStatus.BAD_REQUEST, "5001", "사용자 알림을 찾을 수 없습니다."),
    USER_NOTIFICATION_FORBIDDEN(HttpStatus.FORBIDDEN, "5002", "해당 알림에 접근할 권한이 없습니다."),

    // 메뉴
    MENUCATEGORY_NOT_FOUND(HttpStatus.BAD_REQUEST, "6000", "등록된 메뉴 카테고리가 없습니다."),
    
    // 쿠폰
    COUPON_NOT_FOUND(HttpStatus.BAD_REQUEST, "8000", "등록된 쿠폰이 없습니다."),
    COUPON_ISSUE_QUANTITY_INVALID(HttpStatus.BAD_REQUEST, "8001", "쿠폰 발급 가능한 수량을 초과합니다"),
    COUPON_ISSUE_DATE_INVALID(HttpStatus.BAD_REQUEST, "8002", "쿠폰 발급 가능한 일자가 아닙니다."),
    USER_COUPON_NOT_FOUND(HttpStatus.BAD_REQUEST, "8003", "발급 받은 쿠폰이 없습니다."),
    USER_COUPON_EXPIRED(HttpStatus.BAD_REQUEST, "8004", "쿠폰 사용 가능한 일자가 아닙니다."),
    USER_COUPON_ALREADY_USE(HttpStatus.BAD_REQUEST, "8005", "이미 사용된 쿠폰입니다."),
  
    // 게시긓
    POST_NOT_FOUND(HttpStatus.BAD_REQUEST, "9000", "등록된 게시글이 없습니다.");

    private final HttpStatus status;

    private final String code;

    private String message;

    ErrorCode(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
