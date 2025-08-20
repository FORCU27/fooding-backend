package im.fooding.app.dto.request.user.waiting;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Value;

@Value
public class UserStoreWaitingRegisterRequest {

    @NotNull
    @Schema(description = "가게 ID", example = "1")
    Long storeId;

    @NotNull
    @Schema(description = "서비스 이용약관 동의", example = "true")
    Boolean termsAgreed;

    @NotNull
    @Schema(description = "개인정보 수집 및 이용 동의", example = "true")
    Boolean privacyPolicyAgreed;

    @NotNull
    @Schema(description = "개인정보 제3자 제공 동의", example = "true")
    Boolean thirdPartyAgreed;

    @NotNull
    @PositiveOrZero
    @Schema(description = "필요한 유아용 의자 개수", example = "1")
    Integer infantChairCount;

    @NotNull
    @PositiveOrZero
    @Schema(description = "유아 입장 인원수", example = "1")
    Integer infantCount;

    @NotNull
    @PositiveOrZero
    @Schema(description = "성인 입장 인원수", example = "1")
    Integer adultCount;
}
