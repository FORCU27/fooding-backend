package im.fooding.app.controller.ceo.menu;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Value;

@Value
public class CeoMenuBoardCreateRequest {

    @Schema(description = "title", example = "제목")
    String title;

    @Schema(description = "이미지 업로드 ID", example = "e4b7f1a2-...")
    String imageId;
}
