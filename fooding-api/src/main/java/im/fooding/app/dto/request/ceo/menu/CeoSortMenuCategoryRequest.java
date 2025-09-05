package im.fooding.app.dto.request.ceo.menu;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CeoSortMenuCategoryRequest {

    @NotEmpty
    @Schema(description = "정렬된 순서로 나열된 메뉴 카테고리 ID", example = "[5,3,4,2,1]")
    private List<Long> menuCategoryIds;

}
