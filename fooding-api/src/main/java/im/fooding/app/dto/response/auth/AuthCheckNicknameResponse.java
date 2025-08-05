package im.fooding.app.dto.response.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AuthCheckNicknameResponse {
    @Schema(description = "중복 여부", example = "false", requiredMode = Schema.RequiredMode.REQUIRED)
    private Boolean isDuplicated;

    public AuthCheckNicknameResponse(Boolean isDuplicated) {
        this.isDuplicated = isDuplicated;
    }
}
