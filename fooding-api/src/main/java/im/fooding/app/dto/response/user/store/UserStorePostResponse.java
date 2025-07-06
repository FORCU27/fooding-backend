package im.fooding.app.dto.response.user.store;

import im.fooding.core.model.store.StorePost;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import lombok.AccessLevel;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Value;

@Value
@Builder(access = AccessLevel.PRIVATE)
public class UserStorePostResponse {
    @Schema(description = "유저 소식 ID", requiredMode = RequiredMode.REQUIRED, example = "1")
    long id;

    @Schema(description = "소식 제목", requiredMode = RequiredMode.REQUIRED, example = "점심시간 예약 관련 공지")
    String title;

    @Schema(description = "소식 내용", requiredMode = RequiredMode.REQUIRED, example = "점심시간에는 예약 없이 방문 시 대기시간이 길어질 수 있습니다.")
    String content;

    @Schema(description = "이미지 목록", requiredMode = RequiredMode.REQUIRED)
    List<UserStorePostImageResponse> images;

    @Schema(description = "태그 목록", requiredMode = RequiredMode.REQUIRED, example = "[\"대표\", \"소식\"]")
    List<String> tags;

    @Schema(description = "상단 고정 여부", requiredMode = RequiredMode.REQUIRED, example = "true")
    boolean isFixed;

    @Schema(description = "등록 일자", requiredMode = RequiredMode.REQUIRED, example = "2025-04-25 12:00:00")
    LocalDateTime createdAt;

    public static UserStorePostResponse from(StorePost storePost) {
        List<UserStorePostImageResponse> images = storePost.getImages().stream()
                .map(UserStorePostImageResponse::from)
                .toList();

        return UserStorePostResponse.builder()
                .id(storePost.getId())
                .title(storePost.getTitle())
                .content(storePost.getContent())
                .images(images)
                .tags(storePost.getTags())
                .isFixed(storePost.isFixed())
                .createdAt(storePost.getCreatedAt())
                .build();
    }
}
