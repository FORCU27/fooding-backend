package im.fooding.core.service.user;

import im.fooding.core.global.exception.ApiException;
import im.fooding.core.global.exception.ErrorCode;
import im.fooding.core.model.user.AuthProvider;
import im.fooding.core.model.user.Gender;
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
        checkDuplicateEmail(email);
        if (checkDuplicatedNickname(nickname)) {
            throw new ApiException(ErrorCode.DUPLICATED_NICKNAME);
        }
        User user = User.builder()
                .email(email)
                .nickname(nickname)
                .password(password)
                .role(role)
                .provider(AuthProvider.FOODING)
                .gender(Gender.NONE)
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
    public Page<User> list(String searchString, Pageable pageable, Role role) {
        return userRepository.list(searchString, pageable, role);
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
     * 이메일로 조회
     *
     * @param email
     * @return User
     */
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ApiException(ErrorCode.USER_NOT_FOUND));
    }

    /**
     * 수정
     *
     * @param id
     * @param nickname
     */
    public void update(long id, String nickname, String phoneNumber, String profileImage) {
        User user = findById(id);
        if (!user.getNickname().equals(nickname) && checkDuplicatedNickname(nickname)) {
            throw new ApiException(ErrorCode.DUPLICATED_NICKNAME);
        }
        user.update(nickname, phoneNumber, profileImage);
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
     * 이메일로 유저 조회(없으면 생성 후 리턴)
     *
     * @param email
     * @return User
     */
    public User findByEmailOrCreateUser(String email, AuthProvider provider, Role role) {
        return userRepository.findByEmail(email).orElseGet(() -> {
            User user = User.builder()
                    .email(email)
                    .provider(provider)
                    .role(role)
                    .gender(Gender.NONE)
                    .build();
            return userRepository.save(user);
        });
    }

    /**
     * 이메일 중복체크
     *
     * @param email
     */
    private void checkDuplicateEmail(String email) {
        userRepository.findByEmail(email).ifPresent(it -> {
            throw new ApiException(ErrorCode.DUPLICATED_REGISTER_EMAIL);
        });
    }
}
