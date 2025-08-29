package im.fooding.app.dto.request.admin.lead;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AdminLeadUploadRequest {
    
    @NotNull(message = "소유자 ID는 필수입니다")
    @Schema(description = "Store 소유자 ID", example = "1")
    private Long ownerId;
    
    @NotNull(message = "지역 ID는 필수입니다")
    @Schema(description = "Store 지역 ID", example = "1")
    private Long regionId;
}
