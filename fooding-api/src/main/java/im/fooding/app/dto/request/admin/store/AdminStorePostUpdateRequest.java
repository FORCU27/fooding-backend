package im.fooding.app.dto.request.admin.store;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Value;

import java.util.List;

@Value
public class AdminStorePostUpdateRequest {

    @Schema(description = "가게 ID")
    @NotNull
    Long storeId;

    @Schema(description = "제목")
    @NotBlank
    String title;

    @Schema(description = "내용")
    @NotBlank
    String content;

    @Schema(description = "태그 리스트")
    List<String> tags;

    @Schema(description = "상단 고정 여부")
    @NotNull
    Boolean isFixed;

    @Schema(description = "공지 여부")
    @NotNull
    Boolean isNotice = false;

    @Schema(description = "삭제 이미지 ids", example = "[\"002f4860-3b13-4033-8b5f-6cf91a9816e7\"]")
    private List<String> deleteImageIds;

    @Schema(description = "소식 이미지 업로드 하고 받은 ID", example = "[\"002f4860-3b13-4033-8b5f-6cf91a9816e7\", \"002f4860-3b13-4033-8b5f-6cf91a9816e7\"]")
    private List<String> imageIds;
}
