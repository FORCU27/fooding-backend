package im.fooding.app.dto.request.admin.post;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AdminUpdatePostRequest {
    @NotBlank(message = "제목을 입력해주세요.")
    @Size(max = 50)
    @Schema(description = "게시글 제목", example = "신규 예약 기능 사용법 안내")
    private String title;

    @NotBlank(message = "내용을 입력해주세요.")
    @Schema(description = "게시글 내용", example = "신규 예약 기능 안내는 고객센터 공지를 참고해주세요.")
    private String content;

    @Schema(description = "홈페이지 노출 여부", example = "true")
    private boolean isVisibleOnHomepage;

    @Schema(description = "POS 노출 여부", example = "true")
    private boolean isVisibleOnPos;

    @Schema(description = "CEO앱 노출 여부", example = "true")
    private boolean isVisibleOnCeo;
}
