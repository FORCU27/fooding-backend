package im.fooding.app.dto.request.admin.waiting;

import im.fooding.core.dto.request.waiting.WaitingUserCreateRequest;
import im.fooding.core.model.store.Store;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AdminWaitingUserCreateRequest(

        @Schema(description = "가게 id", example = "1")
        @NotNull
        Long storeId,

        @Schema(description = "이름", example = "홍길동")
        String name,

        @Schema(description = "전화번호", example = "01012345678")
        String phoneNumber,

        @Schema(description = "서비스 이용약관 동의", example = "true")
        @NotNull
        Boolean termsAgreed,

        @Schema(description = "개인정보 수집 및 이용 동의", example = "true")
        @NotNull
        Boolean privacyPolicyAgreed,

        @Schema(description = "개인정보 제3자 제공 동의", example = "true")
        @NotNull
        Boolean thirdPartyAgreed,

        @Schema(description = "마케팅 정보 수신 동의", example = "true")
        @NotNull
        Boolean marketingConsent,

        @Schema(description = "방문 횟수", example = "1")
        @NotNull
        Integer count
) {

    public WaitingUserCreateRequest toWaitingUserCreateRequest(Store store) {
        return WaitingUserCreateRequest.builder()
                .store(store)
                .name(name)
                .phoneNumber(phoneNumber)
                .termsAgreed(termsAgreed)
                .privacyPolicyAgreed(privacyPolicyAgreed)
                .thirdPartyAgreed(thirdPartyAgreed)
                .marketingConsent(marketingConsent)
                .count(count)
                .build();
    }
}
