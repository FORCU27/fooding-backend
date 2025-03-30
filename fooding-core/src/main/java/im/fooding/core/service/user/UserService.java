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

    /**
     * 등록
     *
     * @param email
     * @param nickname
     * @param password
     */
    public void save(String email, String nickname, String password, Role role) {
        checkDuplicateEmail(email, role);
        if (checkDuplicatedNickname(nickname)) {
            throw new ApiException(ErrorCode.DUPLICATED_NICKNAME);
        }
        User user = User.builder()
                .email(email)
                .nickname(nickname)
                .password(password)
                .role(role)
                .build();
        userRepository.save(user);
    }

    /**
     * 리스트 조회
     *
     * @param searchString
     * @param pageable
     * @return Page<User>
     */
    public Page<User> list(String searchString, Pageable pageable) {
        return userRepository.list(searchString, pageable);
    }

    /**
     * id로 조회
     *
     * @param id
     * @return User
     */
    public User findById(long id) {
        return userRepository.findById(id).filter(it -> !it.isDeleted())
                .orElseThrow(() -> new ApiException(ErrorCode.USER_NOT_FOUND));
    }

    /**
     * 이메일과 권한으로 조회
     *
     * @param email
     * @param role
     * @return User
     */
    public User findByEmailAndRole(String email, Role role) {
        return userRepository.findByEmailAndRole(email, role)
                .orElseThrow(() -> new ApiException(ErrorCode.USER_NOT_FOUND));
    }

    /**
     * 수정
     *
     * @param id
     * @param nickname
     */
    public void update(long id, String nickname) {
        User user = findById(id);
        if (!user.getNickname().equals(nickname) && checkDuplicatedNickname(nickname)) {
            throw new ApiException(ErrorCode.DUPLICATED_NICKNAME);
        }
        user.update(nickname);
    }

    /**
     * 삭제
     *
     * @param id
     * @param deletedBy
     */
    public void delete(long id, long deletedBy) {
        User user = findById(id);
        user.delete(deletedBy);
    }

    /**
     * 닉네임 중복체크
     *
     * @param nickname
     * @return
     */
    public boolean checkDuplicatedNickname(String nickname) {
        return userRepository.findByNickname(nickname).isPresent();
    }

    /**
     * 이메일 중복체크
     *
     * @param email
     * @param role
     */
    private void checkDuplicateEmail(String email, Role role) {
        userRepository.findByEmailAndRole(email, role).ifPresent(it -> {
            throw new ApiException(ErrorCode.DUPLICATED_REGISTER_EMAIL);
        });
    }
}
