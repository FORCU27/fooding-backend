package im.fooding.core.global.util;

import im.fooding.core.global.UserInfo;
import im.fooding.core.global.exception.ApiException;
import im.fooding.core.global.exception.ErrorCode;
import im.fooding.core.model.user.Role;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

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

    public static void checkPermission(long targetId) {
        UserInfo userInfo = getUserInfo();
        if (Role.USER == userInfo.getRole() && targetId != userInfo.getId()) {
            throw new ApiException(ErrorCode.ACCESS_DENIED_EXCEPTION);
        }
    }
}
