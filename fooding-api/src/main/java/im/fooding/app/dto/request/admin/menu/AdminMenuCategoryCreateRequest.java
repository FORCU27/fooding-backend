package im.fooding.app.dto.request.admin.menu;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Value;

@Value
public class AdminMenuCategoryCreateRequest {

    @Schema(description = "가게 ID", example = "1")
    @NotNull
    Long storeId;

    @Schema(description = "메뉴 카테고리 이름", example = "식사류")
    @NotBlank
    String name;

    @Schema(description = "메뉴 카테고리 설명", example = "시그니처 대표메뉴!")
    @NotNull
    String description;

    @Schema(description = "카테고리 정렬", example = "1")
    @NotNull
    Integer sortOrder;
}
