package im.fooding.app.dto.response.user.store;

import im.fooding.core.model.store.StorePost;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class UserStorePostResponse {
    @Schema(description = "유저 소식 ID", example = "1")
    private long id;

    @Schema(description = "소식 제목", example = "점심시간 예약 관련 공지")
    private String title;

    @Schema(description = "소식 내용", example = "점심시간에는 예약 없이 방문 시 대기시간이 길어질 수 있습니다.")
    private String content;

    @Schema(description = "태그 목록", example = "[\"대표\", \"소식\"]")
    private List<String> tags;

    @Schema(description = "상단 고정 여부", example = "true")
    private boolean isFixed;

    @Schema(description = "등록 일자", example = "2025-04-25 12:00:00")
    private LocalDateTime createdAt;

    @Builder
    private UserStorePostResponse(
            long id,
            String title,
            String content,
            List<String> tags,
            boolean isFixed,
            LocalDateTime createdAt
    ) {
      this.id = id;
      this.title = title;
      this.content = content;
      this.tags = tags;
      this.isFixed = isFixed;
      this.createdAt = createdAt;
    }

    public static UserStorePostResponse from(StorePost storePost) {
      return new UserStorePostResponse(
              storePost.getId(),
              storePost.getTitle(),
              storePost.getContent(),
              storePost.getTags(),
              storePost.isFixed(),
              storePost.getCreatedAt()
      );
    }
}
