package im.fooding.app.dto.request.ceo.place;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CeoPlaceSettingCreateRequest {

    @NotNull(message = "가게 ID는 필수입니다.")
    @Schema(description = "가게 ID", example = "1")
    private Long storeId;

    @Schema(description = "메타데이터(JSON)", example = "{\"theme\":\"default\"}")
    private String metadata;

    @NotBlank(message = "헤더 제목은 필수입니다.")
    @Schema(description = "헤더 제목", example = "홍가네")
    private String headerTitle;

    @Valid
    @NotNull(message = "본문 정보는 필수입니다.")
    private CeoPlaceSettingBodyRequest body;
}
