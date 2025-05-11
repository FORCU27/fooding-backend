package im.fooding.app.dto.request.admin.post;

import im.fooding.core.model.post.PostType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AdminCreatePostRequest {
    @NotBlank(message = "제목을 입력해주세요.")
    @Size(max = 50)
    @Schema(description = "게시글 제목", example = "신규 기능 출시 안내")
    private String title;

    @NotBlank(message = "내용을 입력해주세요.")
    @Schema(description = "게시글 내용", example = "새로운 예약 기능이 추가되어 안내드립니다.")
    private String content;

    @NotNull(message = "타입을 선택해주세요.")
    @Schema(description = "게시글 타입", example = "NOTICE")
    private PostType type;

    @Schema(description = "홈페이지 노출 여부", example = "true")
    private boolean isVisibleOnHomepage;

    @Schema(description = "POS 노출 여부", example = "false")
    private boolean isVisibleOnPos;

    @Schema(description = "CEO앱 노출 여부", example = "true")
    private boolean isVisibleOnCeo;
}
