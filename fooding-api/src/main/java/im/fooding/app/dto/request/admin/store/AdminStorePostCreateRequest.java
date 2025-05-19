package im.fooding.app.dto.request.admin.store;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Value;

import java.util.List;

@Value
public class AdminStorePostCreateRequest {

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
}
