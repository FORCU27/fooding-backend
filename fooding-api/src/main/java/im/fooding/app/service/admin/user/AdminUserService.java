package im.fooding.app.service.admin.user;

import im.fooding.app.dto.request.admin.user.AdminUpdateUserRequest;
import im.fooding.app.dto.response.admin.user.AdminUserResponse;
import im.fooding.core.common.PageInfo;
import im.fooding.core.common.PageResponse;
import im.fooding.core.model.user.User;
import im.fooding.core.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.query.SortDirection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminUserService {

    private final UserRepository userRepository;

    public PageResponse<AdminUserResponse> list(Pageable pageable, String sortType, SortDirection sortDirection) {
        Page<User> page = userRepository.findAll(pageable);
        return PageResponse.of(
                page.getContent().stream().map(AdminUserResponse::from).toList(),
                PageInfo.of(page));
    }

    public AdminUserResponse findById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + id));
        return AdminUserResponse.from(user);
    }

    @Transactional
    public void update(Long id, AdminUpdateUserRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + id));

        // TODO
    }
}