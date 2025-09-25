package im.fooding.app.dto.response.ceo.menu;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.NOT_REQUIRED;
import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

import im.fooding.core.model.menu.MenuBoard;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CeoMenuBoardResponse {

    @Schema(description = "ID", requiredMode = REQUIRED, example = "1")
    long id;

    @Schema(description = "가게 ID", requiredMode = REQUIRED, example = "1")
    long storeId;

    @Schema(description = "제목", requiredMode = NOT_REQUIRED, example = "제목")
    String title;

    @Schema(description = "이미지 업로드 ID", requiredMode = REQUIRED, example = "https://example.com/image.jpg")
    String imageUrl;

    public static CeoMenuBoardResponse from(MenuBoard menuBoard) {
        return CeoMenuBoardResponse.builder()
                .id(menuBoard.getId())
                .storeId(menuBoard.getStore().getId())
                .imageUrl(menuBoard.getImageUrl())
                .build();
    }
}
