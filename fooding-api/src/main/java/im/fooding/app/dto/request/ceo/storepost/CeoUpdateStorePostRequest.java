package im.fooding.app.dto.request.ceo.storepost;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class CeoUpdateStorePostRequest {
    @NotBlank(message = "제목을 입력해주세요.")
    @Schema(description = "소식 제목", example = "신메뉴 출시 일정 변경 안내")
    private String title;

    @NotBlank(message = "내용을 입력해주세요.")
    @Schema(description = "소식 내용", example = "출시 일정이 변경되었습니다.")
    private String content;

    @Schema(description = "태그 목록", example = "[\"대표\", \"소식\"]")
    private List<String> tags;

    @NotNull
    @Schema(description = "상단 고정 여부", example = "false")
    private Boolean isFixed;
}
