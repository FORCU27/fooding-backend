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
public class StorePostResponse {
    @Schema(description = "소식 id", example = "1")
    private long id;

    @Schema(description = "소식 제목", example = "새로운 메뉴 출시 안내")
    private String title;

    @Schema(description = "소식 내용", example = "저희 가게에 새로운 메뉴가 출시되었습니다!")
    private String content;

    @Schema(description = "태그 목록", example = "[\"대표\", \"소식\"]")
    private List<String> tags;

    @Schema(description = "상단 고정 여부", example = "true")
    private boolean isFixed;

    @Schema(description = "등록 일자", example = "2025-04-25 12:00:00")
    private LocalDateTime createdAt;

    @Builder
    private StorePostResponse(
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

    public static StorePostResponse from(StorePost storePost) {
      return new StorePostResponse(
              storePost.getId(),
              storePost.getTitle(),
              storePost.getContent(),
              storePost.getTags(),
              storePost.isFixed(),
              storePost.getCreatedAt()
      );
    }
}
