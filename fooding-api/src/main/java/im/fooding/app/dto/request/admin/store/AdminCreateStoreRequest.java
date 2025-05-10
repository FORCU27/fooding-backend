package im.fooding.app.dto.request.admin.store;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class AdminCreateStoreRequest {
    @NotBlank(message = "가게 이름은 필수입니다.")
    private String name;

    @NotBlank(message = "도시는 필수입니다.")
    private String city;

    @NotBlank(message = "주소는 필수입니다.")
    private String address;

    @NotBlank(message = "카테고리는 필수입니다.")
    private String category;

    @NotBlank(message = "설명은 필수입니다.")
    private String description;

    @NotBlank(message = "가격 카테고리는 필수입니다.")
    private String priceCategory;

    private String eventDescription;

    @NotBlank(message = "연락처는 필수입니다.")
    private String contactNumber;

    @NotBlank(message = "오시는 길은 필수입니다.")
    private String direction;

    @NotBlank(message = "정보는 필수입니다.")
    private String information;

    @NotNull(message = "주차 가능 여부는 필수입니다.")
    private Boolean isParkingAvailable;

    @NotNull(message = "신규 오픈 여부는 필수입니다.")
    private Boolean isNewOpen;

    @NotNull(message = "포장 가능 여부는 필수입니다.")
    private Boolean isTakeOut;
}