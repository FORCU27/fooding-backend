package im.fooding.core.model.store;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum StoreCategory {
    KOREAN("한식"),
    JAPANESE("일식"),
    CHINESE("중식"),
    WESTERN("양식"),
    ASIAN("아시안푸드"),

    CHICKEN("치킨"),
    PORK_FEET("족발보쌈"),
    BBQ("고기"),
    STREET_FOOD("분식"),
    BURGER_SALAD("햄버거샐러드"),
    LUNCHBOX_PORRIDGE("도시락죽"),

    SEAFOOD("수산물"),
    SNACKS("술안주"),
    CAFE_DESSERT("카페디저트");

    private final String name;
}
