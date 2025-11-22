package im.fooding.app.dto.response.user.store;

import im.fooding.core.model.store.StorePostComment;
import im.fooding.core.model.user.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class UserStorePostCommentResponse {
    @Schema(description = "id", example = "2", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long id;

    @Schema(description = "내용", example = "좋은 소식이네요.", requiredMode = Schema.RequiredMode.REQUIRED)
    private String content;

    @Schema(description = "좋아요 수", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private int likeCount;

    @Schema(description = "작성자명", example = "홍길동", requiredMode = Schema.RequiredMode.REQUIRED)
    private String writerNickname;

    @Schema(description = "작성자 프로필이미지", example = "https://...", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String writerProfileImageUrl;

    @Schema(description = "좋아요 여부", example = "false", requiredMode = Schema.RequiredMode.REQUIRED)
    private Boolean isLiked;

    @Schema(description = "사장님 댓글 여부", example = "false", requiredMode = Schema.RequiredMode.REQUIRED)
    private Boolean isOwner;

    @Schema(description = "대댓글", example = "false", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private List<UserStorePostCommentReplyResponse> replies;

    @Schema(description = "등록일", example = "2025-03-16T05:17:04.069", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createdAt;

    @Builder
    private UserStorePostCommentResponse(Long id, String content, int likeCount, String writerNickname, String writerProfileImageUrl, Boolean isOwner, List<UserStorePostCommentReplyResponse> replies, LocalDateTime createdAt) {
        this.id = id;
        this.content = content;
        this.likeCount = likeCount;
        this.writerNickname = writerNickname;
        this.writerProfileImageUrl = writerProfileImageUrl;
        this.isOwner = isOwner;
        this.replies = replies;
        this.createdAt = createdAt;
        this.isLiked = false;
    }

    public static UserStorePostCommentResponse of(StorePostComment comment) {
        User user = comment.getWriter();
        List<UserStorePostCommentReplyResponse> replies = new ArrayList<>();
        if (comment.getReplies() != null && !comment.getReplies().isEmpty()) {
            replies = comment.getReplies().stream()
                    .map(UserStorePostCommentReplyResponse::of)
                    .toList();
        }

        return UserStorePostCommentResponse.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .likeCount(comment.getLikeCount())
                .writerNickname(user.getNickname())
                .writerProfileImageUrl(user.getProfileImage())
                .isOwner(comment.isOwner())
                .replies(replies)
                .createdAt(comment.getCreatedAt())
                .build();
    }

    public void setIsLiked(Boolean isLiked) {
        this.isLiked = isLiked;
    }
}
