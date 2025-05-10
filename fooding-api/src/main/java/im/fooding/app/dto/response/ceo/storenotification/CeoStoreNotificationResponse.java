package im.fooding.app.dto.response.ceo.storenotification;

import im.fooding.core.model.store.StoreNotification;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class CeoStoreNotificationResponse {
    @Schema(description = "가게 알림 id", example = "1")
    private Long id;

    @Schema(description = "알림 제목", example = "예약")
    private String title;

    @Schema(description = "알림 내용", example = "홍길동님이 예약하셨습니다.")
    private String content;

    @Schema(description = "알림 카테고리", example = "예약")
    private String category;

    @Schema(description = "알림 URL", example = "https://ceo.fooding.com/reservations/1234")
    private String linkUrl;

    @Schema(description = "등록 일자", example = "2025-05-05 12:00:00")
    private LocalDateTime createdAt;

    @Builder
    private CeoStoreNotificationResponse(
            Long id,
            String title,
            String content,
            String category,
            String linkUrl,
            LocalDateTime createdAt
    ){
      this.id = id;
      this.title = title;
      this.content = content;
      this.category = category;
      this.linkUrl = linkUrl;
      this.createdAt = createdAt;
    }

    public static CeoStoreNotificationResponse from(StoreNotification storeNotification) {
      return new CeoStoreNotificationResponse(
              storeNotification.getId(),
              storeNotification.getTitle(),
              storeNotification.getContent(),
              storeNotification.getCategory(),
              storeNotification.getLinkUrl(),
              storeNotification.getCreatedAt()
      );
    }
}
