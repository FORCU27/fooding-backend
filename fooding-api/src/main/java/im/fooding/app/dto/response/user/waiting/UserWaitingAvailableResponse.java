package im.fooding.app.dto.response.user.waiting;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Value;

@Value
public class UserWaitingAvailableResponse {

    @Schema(description = "줄서기 가능 여부", requiredMode = REQUIRED, example = "true")
    boolean isAvailable;
}
