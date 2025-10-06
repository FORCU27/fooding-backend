package im.fooding.app.dto.response.ceo.post;

import com.fasterxml.jackson.annotation.JsonFormat;
import im.fooding.core.model.post.Post;
import im.fooding.core.model.post.PostType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class CeoPostResponse {
    @Schema(description = "게시글 ID", example = "1")
    private long id;

    @Schema(description = "게시글 제목", example = "신규 기능 안내")
    private String title;

    @Schema(description = "게시글 내용", example = "새로운 기능이 추가되었습니다.")
    private String content;

    @Schema(description = "게시글 타입", example = "NOTICE")
    private PostType type;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "작성일", example = "2025-05-11 14:30:00")
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "수정일", example = "2025-05-11 15:00:00")
    private LocalDateTime updatedAt;

    private CeoPostResponse(long id,
                            String title,
                            String content,
                            PostType type,
                            LocalDateTime createdAt,
                            LocalDateTime updatedAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.type = type;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static CeoPostResponse from(Post post) {
        return new CeoPostResponse(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getType(),
                post.getCreatedAt(),
                post.getUpdatedAt()
        );
    }
}
