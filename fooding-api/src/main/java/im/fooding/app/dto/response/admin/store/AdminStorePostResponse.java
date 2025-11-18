package im.fooding.app.dto.response.admin.store;

import im.fooding.core.model.store.StorePost;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.List;

@Value
@Builder(access = AccessLevel.PRIVATE)
public class AdminStorePostResponse {
    @Schema(description = "ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    Long id;

    @Schema(description = "가게 ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    Long storeId;

    @Schema(description = "제목", requiredMode = Schema.RequiredMode.REQUIRED, example = "소식")
    String title;

    @Schema(description = "내용", requiredMode = Schema.RequiredMode.REQUIRED, example = "내용")
    String content;

    @Schema(description = "태그 리스트", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "[\"소식\", \"공지\"]")
    List<String> tags;

    @Schema(description = "상단 고정 여부", requiredMode = Schema.RequiredMode.REQUIRED, example = "true")
    Boolean isFixed;

    @Schema(description = "공지 여부", requiredMode = Schema.RequiredMode.REQUIRED, example = "true")
    Boolean isNotice;

    @Schema(description = "댓글가능여부", requiredMode = Schema.RequiredMode.REQUIRED, example = "true")
    Boolean isCommentAvailable;

    @Schema(description = "공개여부", requiredMode = Schema.RequiredMode.REQUIRED, example = "true")
    Boolean isActive;

    @Schema(description = "좋아요 수", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    int likeCount;

    @Schema(description = "댓글 수", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    int commentCount;

    @Schema(description = "조회수", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    int viewCount;

    @Schema(description = "소식 이미지", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    List<AdminStorePostImageResponse> images;

    @Schema(description = "등록 일자", requiredMode = Schema.RequiredMode.REQUIRED, example = "2025-04-25 12:00:00")
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
                .isCommentAvailable(storePost.isCommentAvailable())
                .isActive(storePost.isActive())
                .likeCount(storePost.getLikeCount())
                .commentCount(storePost.getCommentCount())
                .viewCount(storePost.getViewCount())
                .createdAt(storePost.getCreatedAt())
                .build();
    }
}
