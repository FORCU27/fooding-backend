package im.fooding.app.dto.request.ceo.store.bookmark;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CeoUpdateStoreBookmarkStarredRequest {
    @NotNull
    @Schema(description = "별 표시 여부", example = "true")
    private Boolean isStarred;
}
