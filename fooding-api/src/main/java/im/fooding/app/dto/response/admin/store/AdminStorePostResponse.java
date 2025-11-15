package im.fooding.app.dto.response.admin.store;

import im.fooding.core.model.store.StorePost;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.List;

@Value
@Builder(access = AccessLevel.PRIVATE)
public class AdminStorePostResponse {
    @Schema(description = "ID")
    Long id;

    @Schema(description = "가게 ID")
    Long storeId;

    @Schema(description = "제목")
    String title;

    @Schema(description = "내용")
    String content;

    @Schema(description = "태그 리스트")
    List<String> tags;

    @Schema(description = "상단 고정 여부")
    Boolean isFixed;

    @Schema(description = "공지 여부")
    Boolean isNotice;

    @Schema(description = "좋아요 수", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    int likeCount;

    @Schema(description = "댓글 수", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    int commentCount;

    @Schema(description = "소식 이미지")
    List<AdminStorePostImageResponse> images;

    @Schema(description = "등록 일자", example = "2025-04-25 12:00:00")
    LocalDateTime createdAt;

    public static AdminStorePostResponse from(StorePost storePost) {
        List<AdminStorePostImageResponse> images = storePost.getImages().stream()
                .filter(it -> !it.isDeleted())
                .map(AdminStorePostImageResponse::from)
                .toList();

        return AdminStorePostResponse.builder()
                .id(storePost.getId())
                .title(storePost.getTitle())
                .content(storePost.getContent())
                .tags(storePost.getTags())
                .images(images)
                .isFixed(storePost.isFixed())
                .isNotice(storePost.isNotice())
                .likeCount(storePost.getLikeCount())
                .commentCount(storePost.getCommentCount())
                .createdAt(storePost.getCreatedAt())
                .build();
    }
}
