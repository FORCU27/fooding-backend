package im.fooding.app.dto.response.admin.menu;

import im.fooding.core.model.menu.MenuBoard;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class AdminMenuBoardResponse {

    @Schema(description = "ID", example = "1", requiredMode = RequiredMode.REQUIRED)
    long id;

    @Schema(description = "가게 ID", example = "1", requiredMode = RequiredMode.REQUIRED)
    long storeId;

    @Schema(description = "메뉴판 제목", example = "식사류")
    String title;

    @Schema(description = "메뉴판 사진 URL", requiredMode = RequiredMode.REQUIRED)
    String imageUrl;

    public static AdminMenuBoardResponse from(MenuBoard menuBoard) {
        return AdminMenuBoardResponse.builder()
                .id(menuBoard.getId())
                .storeId(menuBoard.getStore().getId())
                .title(menuBoard.getTitle())
                .imageUrl(menuBoard.getImageUrl())
                .build();
    }
}
