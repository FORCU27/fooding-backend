package im.fooding.app.dto.response.user.store;

import im.fooding.core.model.store.StorePost;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class UserStorePostResponse {
    @Schema(description = "소식 ID", requiredMode = RequiredMode.REQUIRED, example = "1")
    private long id;

    @Schema(description = "소식 제목", requiredMode = RequiredMode.REQUIRED, example = "점심시간 예약 관련 공지")
    private String title;

    @Schema(description = "소식 내용", requiredMode = RequiredMode.REQUIRED, example = "점심시간에는 예약 없이 방문 시 대기시간이 길어질 수 있습니다.")
    private String content;

    @Schema(description = "이미지 목록", requiredMode = RequiredMode.REQUIRED)
    private List<UserStorePostImageResponse> images;

    @Schema(description = "태그 목록", requiredMode = RequiredMode.REQUIRED, example = "[\"대표\", \"소식\"]")
    private List<String> tags;

    @Schema(description = "상단 고정 여부", requiredMode = RequiredMode.REQUIRED, example = "true")
    private Boolean isFixed;

    @Schema(description = "상단 고정 여부", requiredMode = RequiredMode.REQUIRED, example = "true")
    private Boolean isNotice;

    @Schema(description = "좋아요 수", requiredMode = RequiredMode.REQUIRED, example = "1")
    private int likeCount;

    @Schema(description = "댓글 수", requiredMode = RequiredMode.REQUIRED, example = "1")
    private int commentCount;

    @Schema(description = "좋아요 여부", requiredMode = RequiredMode.REQUIRED, example = "false")
    private Boolean isLiked;

    @Schema(description = "등록 일자", requiredMode = RequiredMode.REQUIRED, example = "2025-04-25 12:00:00")
    private LocalDateTime createdAt;

    @Builder
    public UserStorePostResponse(long id, String title, String content, List<UserStorePostImageResponse> images, List<String> tags, Boolean isFixed, Boolean isNotice, int likeCount, int commentCount, LocalDateTime createdAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.images = images;
        this.tags = tags;
        this.isFixed = isFixed;
        this.isNotice = isNotice;
        this.likeCount = likeCount;
        this.commentCount = commentCount;
        this.createdAt = createdAt;
        this.isLiked = false;
    }

    public static UserStorePostResponse from(StorePost storePost) {
        List<UserStorePostImageResponse> images = storePost.getImages().stream()
                .filter(it -> !it.isDeleted())
                .map(UserStorePostImageResponse::from)
                .toList();

        return UserStorePostResponse.builder()
                .id(storePost.getId())
                .title(storePost.getTitle())
                .content(storePost.getContent())
                .images(images)
                .tags(storePost.getTags())
                .isFixed(storePost.isFixed())
                .isNotice(storePost.isNotice())
                .likeCount(storePost.getLikeCount())
                .commentCount(storePost.getCommentCount())
                .createdAt(storePost.getCreatedAt())
                .build();
    }

    public void setIsLiked(Boolean isLiked) {
        this.isLiked = isLiked;
    }
}
