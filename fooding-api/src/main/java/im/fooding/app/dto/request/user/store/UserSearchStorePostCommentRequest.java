package im.fooding.app.dto.request.user.store;

import im.fooding.core.common.BasicSearch;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSearchStorePostCommentRequest extends BasicSearch {
    @NotNull
    @Schema(description = "가게 소식 id", example = "1")
    private Long storePostId;
}
