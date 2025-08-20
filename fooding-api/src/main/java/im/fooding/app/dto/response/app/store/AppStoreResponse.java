package im.fooding.app.dto.response.app.store;

import im.fooding.core.model.store.Store;
import im.fooding.core.model.store.StoreCategory;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;

public record AppStoreResponse(

        @Schema(description = "id", requiredMode = RequiredMode.REQUIRED)
        Long id,

        @Schema(description = "가게 이름", requiredMode = RequiredMode.REQUIRED)
        String name,

        @Schema(description = "매장 주소", requiredMode = RequiredMode.REQUIRED)
        String address,

        @Schema(description = "매장 주소 상세", requiredMode = RequiredMode.NOT_REQUIRED)
        String addressDetail,

        @Schema(description = "매장 카테코리", requiredMode = RequiredMode.REQUIRED)
        StoreCategory category,

        @Schema(description = "매장 소개", requiredMode = RequiredMode.REQUIRED)
        String description,

        @Schema(description = "매장 전화번호", requiredMode = RequiredMode.REQUIRED)
        String contactNumber,

        @Schema(description = "찾아오는 방법", requiredMode = RequiredMode.REQUIRED)
        String direction
) {

    public static AppStoreResponse from(Store store) {
        return new AppStoreResponse(
                store.getId(),
                store.getName(),
                store.getAddress(),
                store.getAddressDetail(),
                store.getCategory(),
                store.getDescription(),
                store.getContactNumber(),
                store.getDirection()
        );
    }
}
