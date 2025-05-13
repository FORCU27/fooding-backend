package im.fooding.app.dto.response.app.store;

import im.fooding.core.model.store.Store;
import io.swagger.v3.oas.annotations.media.Schema;

public record AppStoreResponse(

        @Schema(name = "id")
        Long id,

        @Schema(name = "가게 이름")
        String name,

        @Schema(name = "매장 위치")
        String city,

        @Schema(name = "매장 주소")
        String address,

        @Schema(name = "매장 카테코리")
        String category,

        @Schema(name = "매장 소개")
        String description,

        @Schema(name = "평균 가격대")
        String priceCategory,

        @Schema(name = "매장 이벤트 소개")
        String eventDescription,

        @Schema(name = "매장 전화번호")
        String contactNumber,

        @Schema(name = "찾아오는 방법")
        String direction,

        @Schema(name = "안내 및 유의 사항")
        String information,

        @Schema(name = "주차 가능 여부")
        boolean isParkingAvailable,

        @Schema(name = "매장 신규오픈 여부")
        boolean isNewOpen,

        @Schema(name = "매장 테이크아웃 여부")
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
