package im.fooding.core.service.user;

import im.fooding.core.global.exception.ApiException;
import im.fooding.core.global.exception.ErrorCode;
import im.fooding.core.global.util.NicknameGenerator;
import im.fooding.core.model.user.AuthProvider;
import im.fooding.core.model.user.Gender;
import im.fooding.core.model.user.Role;
import im.fooding.core.model.user.User;
import im.fooding.core.repository.user.UserRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;

    /**
     * 유저 등록(ADMIN, CEO)
     *
     * @param email
     * @param nickname
     * @param password
     * @param phoneNumber
     * @param gender
     */
    public User create(String email, String nickname, String password, String phoneNumber, Gender gender, String name, String description, String referralCode, boolean marketingConsent) {
        checkDuplicateEmail(email, AuthProvider.FOODING);
        if (checkDuplicatedNickname(nickname)) {
            throw new ApiException(ErrorCode.DUPLICATED_NICKNAME);
        }
        if(StringUtils.hasText(phoneNumber)) {
            checkDuplicatePhoneNumber(phoneNumber);
        }

        User user = User.builder()
                .email(email)
                .nickname(nickname)
                .password(password)
                .phoneNumber(phoneNumber)
                .provider(AuthProvider.FOODING)
                .gender(gender)
                .name( name )
                .description( description )
                .referralCode(referralCode)
                .marketingConsent(marketingConsent)
                .build();
        return userRepository.save(user);
    }

    /**
     * 소셜 유저 등록(USER, CEO)
     *
     * @param email
     * @param provider
     */
    public User createSocialUser(String email, AuthProvider provider) {
        String nickname = NicknameGenerator.generateNickname();
        int attempts = 0;

        while (checkDuplicatedNickname(nickname)) {
            nickname = NicknameGenerator.generateNickname();
            attempts++;
            if (attempts > 100) {
                throw new ApiException(ErrorCode.NICKNAME_GENERATE_FAILED);
            }
        }

        User user = User.builder()
                .email(email)
                .provider(provider)
                .nickname(nickname)
                .gender(Gender.NONE)
                .build();

        return userRepository.save(user);
    }

    /**
     * 리스트 조회
     *
     * @param searchString
     * @param pageable
     * @param role
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
     * 수정
     *
     * @param id
     * @param nickname
     * @param phoneNumber
     * @param gender
     * @param referralCode
     * @param marketingConsent
     * @param pushAgreed
     */

    public void update(long id, String nickname, String phoneNumber, Gender gender, String referralCode, boolean marketingConsent, String description, boolean pushAgreed) {
        User user = findById(id);
        if( nickname != null ){
            if (!nickname.equals(user.getNickname()) && checkDuplicatedNickname(nickname)) {
                throw new ApiException(ErrorCode.DUPLICATED_NICKNAME);
            }
        }
        if( phoneNumber != null ){
            if (StringUtils.hasText(phoneNumber) && !phoneNumber.equals(user.getPhoneNumber())) {
                checkDuplicatePhoneNumber(phoneNumber);
            }
        }
        user.updateDescription( description );
        user.update(nickname, phoneNumber, gender, referralCode, marketingConsent, pushAgreed);
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
     * @return boolean
     */
    public boolean checkDuplicatedNickname(String nickname) {
        return userRepository.findByNickname(nickname).isPresent();
    }

    /**
     * email, provider로 유저 조회
     *
     * @param email
     * @param provider
     * @return User
     */
    public User findByEmailAndProvider(String email, AuthProvider provider) {
        return userRepository.findByEmailAndProvider(email, provider).orElse(null);
    }

    /**
     * 프로필 이미지 수정
     *
     * @param user
     * @param profileImage
     */
    public void updateProfileImage(User user, String profileImage) {
        user.updateProfileImage(profileImage);
    }

    /**
     * 이메일 중복체크
     *
     * @param email
     * @param provider
     */
    private void checkDuplicateEmail(String email, AuthProvider provider) {
        userRepository.findByEmailAndProvider(email, provider).ifPresent(it -> {
            throw new ApiException(ErrorCode.DUPLICATED_REGISTER_EMAIL);
        });
    }

    /**
     * 전화번호 중복체크
     *
     * @param phoneNumber
     */
    private void checkDuplicatePhoneNumber(String phoneNumber) {
        userRepository.findByPhoneNumber(phoneNumber).ifPresent(it -> {
            throw new ApiException(ErrorCode.DUPLICATED_PHONE_NUMBER);
        });
    }

    public User findByPhoneNumber(String phoneNumber) {
        return userRepository.findByPhoneNumber(phoneNumber)
                .filter(it -> !it.isDeleted())
                .orElseThrow(() -> new ApiException(ErrorCode.USER_NOT_FOUND));
    }

    public Optional<User> findOptionalByPhoneNumber(String phoneNumber) {
        return userRepository.findByPhoneNumber(phoneNumber)
                .filter(it -> !it.isDeleted());
    }
}
