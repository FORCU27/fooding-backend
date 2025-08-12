package im.fooding.app.dto.response.crawling.naverplace;

import im.fooding.core.model.naverplace.NaverPlace;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CrawlingNaverPlaceResponse {

    @Schema(description = "이름", example = "자연산 해담일식 대게마을")
    String id;

    @Schema(description = "가게 종류", example = "일식당")
    String name;

    @Schema(description = "주소", example = "서울 강남구 언주로93길 12 1층 해담일식")
    String category;

    @Schema(description = "연락처", example = "0507-1397-3305")
    String address;

    @Schema(description = "연락처", example = "0507-1397-3305")
    String contact;

    @Schema(description = "메뉴 리스트")
    List<Menu> menus;

    @Value
    public static class Menu {

        @Schema(description = "메뉴 이름", example = "점심세트 정식1인")
        String name;

        @Schema(description = "가격", example = "50000")
        Integer price;

        public static Menu from(NaverPlace.Menu menu) {
            return new Menu(menu.getName(), menu.getPrice());
        }
    }

    public static CrawlingNaverPlaceResponse from(NaverPlace naverPlace) {
        List<Menu> menus = naverPlace.getMenus()
                .stream()
                .map(Menu::from)
                .toList();

        return CrawlingNaverPlaceResponse.builder()
                .id(naverPlace.getId())
                .name(naverPlace.getName())
                .category(naverPlace.getCategory())
                .address(naverPlace.getAddress())
                .contact(naverPlace.getContact())
                .menus(menus)
                .build();
    }
}
