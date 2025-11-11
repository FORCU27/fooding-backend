package im.fooding.app.dto.response.admin.storeNotification;

import im.fooding.core.model.store.StoreNotification;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class AdminStoreNotificationResponse {

    @Schema(description = "ID", example = "1", requiredMode = RequiredMode.REQUIRED)
    Long id;

    @Schema(description = "가게 ID", example = "1", requiredMode = RequiredMode.REQUIRED)
    Long storeId;

    @Schema(description = "제목", example = "홍길동님이 예약하셨습니다", requiredMode = RequiredMode.REQUIRED)
    String title;

    @Schema(description = "내용", example = "홍길동님이 예약하셨습니다", requiredMode = RequiredMode.REQUIRED)
    String content;

    @Schema(description = "종류", example = "RESERVATION", requiredMode = RequiredMode.REQUIRED)
    String category;

    @Schema(description = "관련 URL", example = "https://stage.fooding.im", requiredMode = RequiredMode.REQUIRED)
    String linkUrl;

    public static AdminStoreNotificationResponse from(StoreNotification storeNotification) {
        return AdminStoreNotificationResponse.builder()
                .id(storeNotification.getId())
                .storeId(storeNotification.getStore().getId())
                .title(storeNotification.getTitle())
                .content(storeNotification.getContent())
                .category(storeNotification.getCategory())
                .linkUrl(storeNotification.getLinkUrl())
                .build();
    }
}
