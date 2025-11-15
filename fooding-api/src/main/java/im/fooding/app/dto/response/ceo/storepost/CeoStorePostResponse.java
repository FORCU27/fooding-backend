package im.fooding.app.dto.response.ceo.storepost;

import im.fooding.core.model.store.StorePost;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class CeoStorePostResponse {
    @Schema(description = "소식 id", example = "1")
    private long id;

    @Schema(description = "소식 제목", example = "새로운 메뉴 출시 안내")
    private String title;

    @Schema(description = "소식 내용", example = "저희 가게에 새로운 메뉴가 출시되었습니다!")
    private String content;

    @Schema(description = "태그 목록", example = "[\"대표\", \"소식\"]")
    private List<String> tags;

    @Schema(description = "상단 고정 여부", example = "true")
    private Boolean isFixed;

    @Schema(description = "공지여부", example = "true")
    private Boolean isNotice;

    @Schema(description = "좋아요 수", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    int likeCount;

    @Schema(description = "댓글 수", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    int commentCount;

    @Schema(description = "소식 이미지")
    private List<CeoStorePostImageResponse> images;

    @Schema(description = "등록 일자", example = "2025-04-25 12:00:00")
    private LocalDateTime createdAt;

    @Builder
    private CeoStorePostResponse(
            long id,
            String title,
            String content,
            List<String> tags,
            boolean isFixed,
            boolean isNotice,
            int likeCount,
            int commentCount,
            List<CeoStorePostImageResponse> images,
            LocalDateTime createdAt
    ) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.tags = tags;
        this.isFixed = isFixed;
        this.isNotice = isNotice;
        this.likeCount = likeCount;
        this.commentCount = commentCount;
        this.images = images;
        this.createdAt = createdAt;
    }

    public static CeoStorePostResponse from(StorePost storePost) {
        List<CeoStorePostImageResponse> images = storePost.getImages().stream()
                .filter(it -> !it.isDeleted())
                .map(CeoStorePostImageResponse::from)
                .toList();

        return new CeoStorePostResponse(
                storePost.getId(),
                storePost.getTitle(),
                storePost.getContent(),
                storePost.getTags(),
                storePost.isFixed(),
                storePost.isNotice(),
                storePost.getLikeCount(),
                storePost.getCommentCount(),
                images,
                storePost.getCreatedAt()
        );
    }
}
