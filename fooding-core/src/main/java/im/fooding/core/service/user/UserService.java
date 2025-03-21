package im.fooding.core.service.user;

import im.fooding.core.global.exception.ApiException;
import im.fooding.core.global.exception.ErrorCode;
import im.fooding.core.model.user.Role;
import im.fooding.core.model.user.User;
import im.fooding.core.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;

    public void save(String email, String nickname, String password) {
        checkDuplicateEmail(email, Role.ADMIN);
        if (checkDuplicatedNickname(nickname)) {
            throw new ApiException(ErrorCode.DUPLICATED_NICKNAME);
        }
        User user = User.builder()
                .email(email)
                .nickname(nickname)
                .password(password)
                .role(Role.ADMIN)
                .build();
        userRepository.save(user);
    }

    public Page<User> list(String searchString, Pageable pageable) {
        return userRepository.list(searchString, pageable);
    }

    public User findById(long id) {
        return userRepository.findById(id).orElseThrow(() -> new ApiException(ErrorCode.USER_NOT_FOUND));
    }

    public User findByEmailAndRole(String email, Role role) {
        return userRepository.findByEmailAndRole(email, role)
                .orElseThrow(() -> new ApiException(ErrorCode.USER_NOT_FOUND));
    }

    public void update(long id, String nickname) {
        User user = findById(id);
        if (!user.getNickname().equals(nickname) && checkDuplicatedNickname(nickname)) {
            throw new ApiException(ErrorCode.DUPLICATED_NICKNAME);
        }
        user.update(nickname);
    }

    public void delete(long id, long deletedBy) {
        User user = findById(id);
        user.delete(deletedBy);
    }

    public boolean checkDuplicatedNickname(String nickname) {
        return userRepository.findByNickname(nickname).isPresent();
    }

    private void checkDuplicateEmail(String email, Role role) {
        userRepository.findByEmailAndRole(email, role).ifPresent(it -> {
            throw new ApiException(ErrorCode.DUPLICATED_REGISTER_EMAIL);
        });
    }

}
