package im.fooding.core.model.store;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum StoreCategory {
    PORK("족발/보쌈"),
    MEAT("고기"),
    CHICKEN("치킨"),
    JAPANESE("일식"),
    WESTERN("양식"),
    CHINESE("중식"),
    KOREAN("한식"),
    ASIAN("아시안 푸드"),
    LUNCHBOX_PORRIDGE("도시락/죽"),
    CAFE_DESSERT("카페/디저트"),
    BURGER("햄버거"),
    SALAD("샐러드"),
    SNACK("분식"),
    SEAFOOD("수산물"),
    SIDE_DISH("술안주"),
    ;

    private final String name;
}
