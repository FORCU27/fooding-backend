package im.fooding.app.dto.response.admin.waiting;

import im.fooding.core.model.waiting.WaitingUser;
import io.swagger.v3.oas.annotations.media.Schema;

public record AdminWaitingUserResponse(

        @Schema(description = "웨이팅 유저 id")
        Long id,

        @Schema(description = "가게 id", example = "1")
        Long storeId,

        @Schema(description = "이름", example = "홍길동")
        String name,

        @Schema(description = "전화번호", example = "01012345678")
        String phoneNumber,

        @Schema(description = "서비스 이용약관 동의", example = "true")
        Boolean termsAgreed,

        @Schema(description = "개인정보 수집 및 이용 동의", example = "true")
        Boolean privacyPolicyAgreed,

        @Schema(description = "개인정보 제3자 제공 동의", example = "true")
        Boolean thirdPartyAgreed,

        @Schema(description = "마케팅 정보 수신 동의", example = "true")
        Boolean marketingConsent,

        @Schema(description = "방문 횟수", example = "1")
        Integer count
) {

        public static AdminWaitingUserResponse from(WaitingUser waitingUser) {
                return new AdminWaitingUserResponse(
                        waitingUser.getId(),
                        waitingUser.getStoreId(),
                        waitingUser.getName(),
                        waitingUser.getPhoneNumber(),
                        waitingUser.isTermsAgreed(),
                        waitingUser.isPrivacyPolicyAgreed(),
                        waitingUser.isThirdPartyAgreed(),
                        waitingUser.isMarketingConsent(),
                        waitingUser.getCount()
                );
        }
}
