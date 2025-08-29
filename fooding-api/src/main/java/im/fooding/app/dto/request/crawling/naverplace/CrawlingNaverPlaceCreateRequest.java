package im.fooding.app.dto.request.crawling.naverplace;

import im.fooding.core.model.naverplace.NaverPlace;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.Value;

@Value
public class CrawlingNaverPlaceCreateRequest {

    @NotNull
    @Schema(description = "ID", example = "37792495")
    String id;

    @NotBlank
    @Schema(description = "이름", example = "자연산 해담일식 대게마을")
    String name;

    @NotBlank
    @Schema(description = "가게 종류", example = "일식당")
    String category;

    @NotBlank
    @Schema(description = "주소", example = "서울 강남구 언주로93길 12 1층 해담일식")
    String address;

    @NotBlank
    @Schema(description = "연락처", example = "0507-1397-3305")
    String contact;

    List<Menu> menus;

    @Value
    public static class Menu {

        @Schema(description = "메뉴 이름", example = "점심세트 정식1인")
        @NotBlank
        String name;

        @Schema(description = "가격", example = "50000")
        @NotNull
        Integer price;

        public NaverPlace.Menu toMenu() {
            return new NaverPlace.Menu(name, price);
        }
    }
}
