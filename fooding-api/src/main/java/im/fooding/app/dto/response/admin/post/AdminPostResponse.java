package im.fooding.app.dto.response.admin.post;

import com.fasterxml.jackson.annotation.JsonFormat;
import im.fooding.core.model.post.Post;
import im.fooding.core.model.post.PostType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class AdminPostResponse {
    @Schema(description = "게시글 ID", example = "1")
    private long id;

    @Schema(description = "게시글 제목", example = "신규 기능 출시 안내")
    private String title;

    @Schema(description = "게시글 내용", example = "새로운 예약 기능이 추가되어 안내드립니다.")
    private String content;

    @Schema(description = "게시글 타입", example = "NOTICE")
    private PostType type;

    @Schema(description = "홈페이지 노출 여부", example = "true")
    private Boolean isVisibleOnHomepage;

    @Schema(description = "POS 노출 여부", example = "false")
    private Boolean isVisibleOnPos;

    @Schema(description = "CEO앱 노출 여부", example = "true")
    private Boolean isVisibleOnCeo;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "작성일", example = "2025-05-11 14:30:00")
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "수정일", example = "2025-05-11 15:00:00")
    private LocalDateTime updatedAt;

    @Builder
    private AdminPostResponse(
            long id,
            String title,
            String content,
            PostType type,
            boolean isVisibleOnHomepage,
            boolean isVisibleOnPos,
            boolean isVisibleOnCeo,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
      this.id = id;
      this.title = title;
      this.content = content;
      this.type = type;
      this.isVisibleOnHomepage = isVisibleOnHomepage;
      this.isVisibleOnPos = isVisibleOnPos;
      this.isVisibleOnCeo = isVisibleOnCeo;
      this.createdAt = createdAt;
      this.updatedAt = updatedAt;
    }

    public static AdminPostResponse from(Post post) {
      return new AdminPostResponse(
              post.getId(),
              post.getTitle(),
              post.getContent(),
              post.getType(),
              post.isVisibleOnHomepage(),
              post.isVisibleOnPos(),
              post.isVisibleOnCeo(),
              post.getCreatedAt(),
              post.getUpdatedAt()
      );
    }
}
