package im.fooding.app.dto.request.admin.store;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AdminUpdateStoreImageMainRequest {
    @NotNull
    @Schema(description = "대표사진 여부", example = "true")
    private Boolean isMain;
}
