package im.fooding.app.dto.request.user.store;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserCreateStorePostCommentRequest {
    @NotNull
    @Schema(description = "가게 소식 id", example = "1")
    private Long storePostId;

    @Schema(description = "부모 댓글 id", example = "1")
    private Long parentId;

    @NotBlank
    @Schema(description = "내용", example = "소식 좋아요")
    private String content;
}
