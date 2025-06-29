package im.fooding.core.global.util;

import java.util.List;
import java.util.Random;

public class NicknameGenerator {
    private static final List<String> ADJECTIVES = List.of(
            "예의바른", "친절한", "똑똑한", "용감한", "창의적인", "열정적인", "성실한", "배려하는", "낙천적인", "도전적인",
            "기분나쁜", "기분좋은", "신바람나는", "상쾌한", "짜릿한", "그리운", "자유로운", "서운한", "울적한", "비참한",
            "위축되는", "긴장되는", "두려운", "당당한", "배부른", "수줍은", "창피한", "멋있는", "열받은", "심심한",
            "잘생긴", "이쁜", "시끄러운"
    );

    private static final List<String> COLORS = List.of(
            "빨간", "파란", "노란", "초록", "보라", "주황", "핑크", "하얀", "검은", "회색"
    );

    private static final List<String> ANIMALS = List.of(
            "사자", "호랑이", "곰", "여우", "팬더", "코끼리", "사슴", "기린", "물고기", "오리",
            "원숭이", "뱀", "늑대", "너구리", "침팬치", "고릴라", "참새", "고슴도치", "강아지",
            "고양이", "거북이", "토끼", "앵무새", "하이에나", "돼지", "하마", "물소", "얼룩말", "치타",
            "악어", "수달", "염소", "다람쥐", "판다"
    );

    private static final Random RANDOM = new Random();

    private static <T> T getRandomElement(List<T> list) {
        return list.get(RANDOM.nextInt(list.size()));
    }

    public static String generateNickname() {
        return String.format("%s %s %s", getRandomElement(ADJECTIVES), getRandomElement(COLORS), getRandomElement(ANIMALS));
    }
}
