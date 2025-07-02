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

    public static String generateListToString(List<String> strings) {
        if (strings == null || strings.isEmpty()) {
            return "";
        }
        return strings.stream()
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.joining(","));
    }

    public static DayOfWeek getDayOfWeek() {
        return LocalDate.now().getDayOfWeek();
    }
}
