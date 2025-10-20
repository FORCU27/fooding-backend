package im.fooding.app.dto.response.admin.pointshop;

import com.fasterxml.jackson.annotation.JsonInclude;
import im.fooding.app.dto.response.file.FileResponse;
import im.fooding.core.model.coupon.ProvideType;
import im.fooding.core.model.pointshop.PointShop;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class AdminPointShopResponse {
    @Schema(description = "id", example = "1", requiredMode = RequiredMode.REQUIRED)
    private Long id;

    @Schema(description = "가게 id", example = "1", requiredMode = RequiredMode.REQUIRED)
    private Long storeId;

    @Schema(description = "가게명", example = "김가네", requiredMode = RequiredMode.REQUIRED)
    private String storeName;

    @Schema(description = "상품이름", example = "계란찜", requiredMode = RequiredMode.REQUIRED)
    private String name;

    @Schema(description = "100", example = "포인트", requiredMode = RequiredMode.REQUIRED)
    private int point;

    @Schema(description = "ALL(모든고객), REGULAR_CUSTOMER(단골전용)", example = "ALL", requiredMode = RequiredMode.REQUIRED)
    private ProvideType provideType;

    @Schema(description = "가게 id", example = "가게에서 사용 가능", requiredMode = RequiredMode.NOT_REQUIRED)
    private String conditions;

    @Schema(description = "판매 여부", example = "true", requiredMode = RequiredMode.REQUIRED)
    private Boolean isActive;

    @Schema(description = "총 수량", example = "100", requiredMode = RequiredMode.NOT_REQUIRED)
    private Integer totalQuantity;

    @Schema(description = "구매 수량", example = "10", requiredMode = RequiredMode.REQUIRED)
    private Integer issuedQuantity;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Schema(description = "사용 수량", example = "5", requiredMode = RequiredMode.NOT_REQUIRED)
    private Integer usedQuantity;

    @Schema(description = "교환 가능 시작일", example = "2025-07-01", requiredMode = RequiredMode.NOT_REQUIRED)
    private LocalDate issueStartOn;

    @Schema(description = "교환 가능 마감일", example = "2030-07-01", requiredMode = RequiredMode.NOT_REQUIRED)
    private LocalDate issueEndOn;

    @Schema(description = "이미지", requiredMode = RequiredMode.NOT_REQUIRED)
    private FileResponse image;

    @Schema(description = "등록일", example = "2025-03-16T05:17:04.069", requiredMode = RequiredMode.REQUIRED)
    private LocalDateTime createdAt;

    @Builder
    private AdminPointShopResponse(Long id, Long storeId, String storeName, String name, int point, ProvideType provideType, String conditions,
                                   Boolean isActive, Integer totalQuantity, Integer issuedQuantity, Integer usedQuantity, LocalDate issueStartOn, LocalDate issueEndOn, FileResponse image, LocalDateTime createdAt) {
        this.id = id;
        this.storeId = storeId;
        this.storeName = storeName;
        this.name = name;
        this.point = point;
        this.provideType = provideType;
        this.conditions = conditions;
        this.isActive = isActive;
        this.totalQuantity = totalQuantity;
        this.issuedQuantity = issuedQuantity;
        this.usedQuantity = usedQuantity;
        this.issueStartOn = issueStartOn;
        this.issueEndOn = issueEndOn;
        this.image = image;
        this.createdAt = createdAt;
    }

    public static AdminPointShopResponse of(PointShop pointShop, Integer usedQuantity) {
        return AdminPointShopResponse.builder()
                .id(pointShop.getId())
                .storeId(null != pointShop.getStore() ? pointShop.getStore().getId() : null)
                .storeName(null != pointShop.getStore() ? pointShop.getStore().getName() : null)
                .name(pointShop.getName())
                .point(pointShop.getPoint())
                .provideType(pointShop.getProvideType())
                .conditions(pointShop.getConditions())
                .isActive(pointShop.isActive())
                .totalQuantity(pointShop.getTotalQuantity())
                .issuedQuantity(pointShop.getIssuedQuantity())
                .usedQuantity(usedQuantity)
                .issueStartOn(pointShop.getIssueStartOn())
                .issueEndOn(pointShop.getIssueEndOn())
                .image(pointShop.getImage() != null ? FileResponse.of(pointShop.getImage()) : null)
                .createdAt(pointShop.getCreatedAt())
                .build();
    }

    public static AdminPointShopResponse of(PointShop pointShop) {
        return of(pointShop, null);
    }
}
