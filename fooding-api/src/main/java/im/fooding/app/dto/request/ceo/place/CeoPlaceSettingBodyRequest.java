package im.fooding.app.dto.request.ceo.place;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CeoPlaceSettingBodyRequest {

    @NotNull(message = "소개글 사용 여부를 입력해주세요.")
    @Schema(description = "소개글 사용 여부", example = "true")
    private Boolean enabled;

    @Schema(description = "소개글 본문 HTML", example = "<p>어서오세요</p>")
    private String content;
}
