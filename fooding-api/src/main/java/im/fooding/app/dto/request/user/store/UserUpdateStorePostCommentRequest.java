package im.fooding.app.dto.request.user.store;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserUpdateStorePostCommentRequest {
    @NotBlank
    @Schema(description = "내용", example = "소식 좋아요")
    private String content;
}
