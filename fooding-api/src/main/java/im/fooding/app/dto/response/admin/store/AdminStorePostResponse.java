package im.fooding.app.dto.response.admin.store;

import im.fooding.core.model.store.StorePost;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder(access = AccessLevel.PRIVATE)
public class AdminStorePostResponse {

    @Schema(description = "ID")
    @NotNull
    Long id;

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

    public static AdminStorePostResponse from(StorePost storePost) {
        return AdminStorePostResponse.builder()
                .id(storePost.getId())
                .title(storePost.getTitle())
                .content(storePost.getContent())
                .tags(storePost.getTags())
                .isFixed(storePost.isFixed())
                .build();
    }
}
