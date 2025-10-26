package im.fooding.app.service.ceo.user;

import im.fooding.app.dto.response.user.UserResponse;
import im.fooding.core.common.BasicSearch;
import im.fooding.core.common.PageInfo;
import im.fooding.core.common.PageResponse;
import im.fooding.core.model.user.Role;
import im.fooding.core.model.user.User;
import im.fooding.core.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CeoUserService {
    private final UserService userService;

    @Transactional(readOnly = true)
    public PageResponse<UserResponse> list(BasicSearch search, Long ceoId) {
        Page<User> list = userService.list(search.getSearchString(), search.getPageable(), Role.USER);
        return PageResponse.of(list.stream().map(UserResponse::from).toList(), PageInfo.of(list));
    }
}
