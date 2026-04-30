package im.fooding.core.global.util;

import im.fooding.core.global.UserInfo;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Util {
    public static UserInfo getUserInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            Object userInfo = authentication.getPrincipal();
            if (userInfo instanceof UserInfo) {
                return (UserInfo) userInfo;
            }
        }
        return null;
    }

    public static List<String> generateStringToList(String value) {
        if (value == null || value.trim().isEmpty()) {
            return Collections.emptyList();
        }

        return Arrays.stream(value.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());
    }
    /**
     * 리스트를 쉼표로 연결한 문자열로 변환
     * - Enum이면 name() 사용
     * - 일반 객체면 toString() 사용
     */
    public static <T> String generateListToString(List<T> list) {
        if (list == null || list.isEmpty()) {
            return "";
        }
        return list.stream()
                .map(item -> {
                    if (item instanceof Enum<?>) {
                        return ((Enum<?>) item).name();
                    } else {
                        return item.toString();
                    }
                })
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.joining(","));
    }

    public static DayOfWeek getDayOfWeek() {
        return LocalDate.now().getDayOfWeek();
    }

    public static boolean isAllowedBackofficeEmails(String email) {
        List<String> allowedEmails = Arrays.asList(
                "karjyk@gmail.com", "yoon@hyperreality.gg", "jysjys7620@naver.com",
                "jin@hyperreality.gg", "seongje00416@gmail.com", "jeongyounghyeon1106@gmail.com",
                "cleo0718@gmail.com", "nononcrust@gmail.com", "leewj5192@gmail.com", "monee97101@gmail.com"
        );
        return allowedEmails.contains(email);
    }
}
