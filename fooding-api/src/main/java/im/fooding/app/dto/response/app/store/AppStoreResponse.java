package im.fooding.app.dto.response.app.store;

import im.fooding.core.model.store.Store;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;

public record AppStoreResponse(

        @Schema(description = "id", requiredMode = RequiredMode.REQUIRED)
        Long id,

        @Schema(description = "가게 이름", requiredMode = RequiredMode.REQUIRED)
        String name,

        @Schema(description = "매장 위치", requiredMode = RequiredMode.REQUIRED)
        String city,

        @Schema(description = "매장 주소", requiredMode = RequiredMode.REQUIRED)
        String address,

        @Schema(description = "매장 카테코리", requiredMode = RequiredMode.REQUIRED)
        String category,

        @Schema(description = "매장 소개", requiredMode = RequiredMode.REQUIRED)
        String description,

        @Schema(description = "평균 가격대", requiredMode = RequiredMode.REQUIRED)
        String priceCategory,

        @Schema(description = "매장 이벤트 소개")
        String eventDescription,

        @Schema(description = "매장 전화번호", requiredMode = RequiredMode.REQUIRED)
        String contactNumber,

        @Schema(description = "찾아오는 방법", requiredMode = RequiredMode.REQUIRED)
        String direction,

        @Schema(description = "안내 및 유의 사항", requiredMode = RequiredMode.REQUIRED)
        String information,

        @Schema(description = "주차 가능 여부", requiredMode = RequiredMode.REQUIRED)
        boolean isParkingAvailable,

        @Schema(description = "매장 신규오픈 여부", requiredMode = RequiredMode.REQUIRED)
        boolean isNewOpen,

        @Schema(description = "매장 테이크아웃 여부", requiredMode = RequiredMode.REQUIRED)
        boolean isTakeOut
) {

    public static AppStoreResponse from(Store store) {
        return new AppStoreResponse(
                store.getId(),
                store.getName(),
                store.getCity(),
                store.getAddress(),
                store.getCategory(),
                store.getDescription(),
                store.getPriceCategory(),
                store.getEventDescription(),
                store.getContactNumber(),
                store.getDirection(),
                store.getInformation(),
                store.isParkingAvailable(),
                store.isNewOpen(),
                store.isTakeOut()
        );
    }
}
