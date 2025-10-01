package im.fooding.app.dto.response.user.waiting;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Value;

@Value
public class UserWaitingServiceUsageResponse {

    @Schema(description = "줄서기 서비스 이용 여부", requiredMode = REQUIRED, example = "true")
    boolean usage;
}
