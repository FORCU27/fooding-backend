package im.fooding.app.dto.response.user.store;

import im.fooding.core.model.store.StorePostComment;
import im.fooding.core.model.user.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class UserStorePostCommentReplyResponse {
    @Schema(description = "id", example = "2", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long id;

    @Schema(description = "부모 댓글 id", example = "1", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Long parentId;

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

    @Schema(description = "댓글 작성자 여부", example = "false", requiredMode = Schema.RequiredMode.REQUIRED)
    private Boolean isParentWriter;

    @Schema(description = "등록일", example = "2025-03-16T05:17:04.069", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createdAt;

    @Builder
    private UserStorePostCommentReplyResponse(Long id, Long parentId, String content, int likeCount, String writerNickname, String writerProfileImageUrl, Boolean isOwner, Boolean isParentWriter, LocalDateTime createdAt) {
        this.id = id;
        this.parentId = parentId;
        this.content = content;
        this.likeCount = likeCount;
        this.writerNickname = writerNickname;
        this.writerProfileImageUrl = writerProfileImageUrl;
        this.isOwner = isOwner;
        this.isParentWriter = isParentWriter;
        this.createdAt = createdAt;
        this.isLiked = false;
    }

    public static UserStorePostCommentReplyResponse of(StorePostComment reply) {
        Long parentId = reply.getParent() != null ? reply.getParent().getId() : null;
        User user = reply.getWriter();

        return UserStorePostCommentReplyResponse.builder()
                .id(reply.getId())
                .parentId(parentId)
                .content(reply.getContent())
                .likeCount(reply.getLikeCount())
                .writerNickname(user.getNickname())
                .writerProfileImageUrl(user.getProfileImage())
                .isOwner(reply.isOwner())
                .isParentWriter(reply.isParentWriter())
                .createdAt(reply.getCreatedAt())
                .build();
    }

    public void setIsLiked(Boolean isLiked) {
        this.isLiked = isLiked;
    }
}
