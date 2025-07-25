package im.fooding.app.dto.request.admin.menu;

import im.fooding.core.dto.request.menu.MenuBoardUpdateRequest;
import im.fooding.core.model.store.Store;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Value;

@Value
public class AdminMenuBoardUpdateRequest {

        @Schema(description = "가게 ID", example = "1")
        @NotNull
        Long storeId;

        @Schema(description = "메뉴 카테고리 제목", example = "식사류")
        String title;

        @Schema(description = "이미지 업로드 후 받은 ID", example = "819f4bca-2739-46ca-9156-332c86eda619")
        String imageId;

        public MenuBoardUpdateRequest toMenuBoardUpdateRequest(long id, Store store, String imageUrl) {
                return MenuBoardUpdateRequest.builder()
                        .id(id)
                        .store(store)
                        .title(title)
                        .imageUrl(imageUrl)
                        .build();
        }
}
