package im.fooding.app.dto.request.user.bookmark;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserAddBookmarkRequest {
    @NotNull
    @Schema(description = "가게 id", example = "1")
    private Long storeId;
}
