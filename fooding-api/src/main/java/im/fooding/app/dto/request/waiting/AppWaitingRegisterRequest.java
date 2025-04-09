package im.fooding.app.dto.request.waiting;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AppWaitingRegisterRequest(
        @NotNull
        @Schema(description = "가게 아이디", example = "1")
        long storeId,

        @Schema(description = "웨이팅 유저 이름", example = "홍길동")
        String name,

        @NotBlank
        @Schema(description = "핸드폰 번호", example = "01012345678")
        String phoneNumber,

        @NotNull
        @Schema(description = "서비스 이용약관 동의", example = "true")
        boolean termsAgreed,

        @NotNull
        @Schema(description = "개인정보 수집 및 이용 동의", example = "true")
        boolean privacyPolicyAgreed,

        @NotNull
        @Schema(description = "개인정보 제3자 제공 동의", example = "true")
        boolean thirdPartyAgreed,

        @NotNull
        @Schema(description = "마케팅 정보 수신 동의", example = "true")
        boolean marketingConsent,

        @NotNull
        @Schema(description = "필요한 유아용 의자 개수", example = "1")
        int infantChairCount,

        @NotNull
        @Schema(description = "유아 입장 인원수", example = "1")
        int infantCount,

        @NotNull
        @Schema(description = "성인 입장 인원수", example = "1")
        int adultCount
) {

    public AppWaitingRegisterServiceRequest toWaitingRegisterServiceRequest() {
        return AppWaitingRegisterServiceRequest.builder()
                .storeId(storeId)
                .name(name)
                .phoneNumber(phoneNumber)
                .termsAgreed(termsAgreed)
                .privacyPolicyAgreed(privacyPolicyAgreed)
                .thirdPartyAgreed(thirdPartyAgreed)
                .marketingConsent(marketingConsent)
                .infantChairCount(infantChairCount)
                .infantCount(infantCount)
                .adultCount(adultCount)
                .build();
    }
}
