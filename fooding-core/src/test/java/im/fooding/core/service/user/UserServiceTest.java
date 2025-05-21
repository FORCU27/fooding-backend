package im.fooding.core.service.user;

import im.fooding.core.TestConfig;
import im.fooding.core.common.BasicSearch;
import im.fooding.core.global.exception.ApiException;
import im.fooding.core.global.exception.ErrorCode;
import im.fooding.core.model.user.*;
import im.fooding.core.repository.user.UserAuthorityRepository;
import im.fooding.core.repository.user.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.tuple;
import static org.junit.jupiter.api.Assertions.*;

@Sql(statements = "ALTER TABLE \"user\" ALTER COLUMN ID RESTART WITH 1", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class UserServiceTest extends TestConfig {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserAuthorityRepository userAuthorityRepository;

    @Test
    @DisplayName("회원가입을 성공적으로 성공한다.")
    public void testCreate() {
        //given
        String email = "admin@gmail.com";
        String nickname = "관리자";
        String password = "1234";

        //when
        userService.create(email, nickname, password);

        //then
        assertTrue(userRepository.findByEmailAndProvider(email, AuthProvider.FOODING).isPresent());
    }

    @Test
    @DisplayName("회원가입을 이메일 중복으로 실패한다.")
    public void testCreate_fail_DuplicatedEmail() {
        //given
        String email = "admin@gmail.com";
        String nickname = "관리자";
        String password = "1234";
        Role role = Role.ADMIN;
        saveUser(email, nickname, role);

        String nickname2 = "관리자2";

        // when && then
        ApiException apiException =
                assertThrows(ApiException.class, () -> {
                    userService.create(email, nickname2, password);
                });
        assertEquals(ErrorCode.DUPLICATED_REGISTER_EMAIL, apiException.getErrorCode());
    }

    @Test
    @DisplayName("회원가입을 닉네임 중복으로 실패한다.")
    public void testCreate_fail_duplicatedNickname() {
        //given
        String email = "admin@gmail.com";
        String nickname = "관리자";
        String password = "1234";
        Role role = Role.ADMIN;
        saveUser(email, nickname, role);

        String email2 = "admin2@gmail.com";

        // when && then
        ApiException apiException =
                assertThrows(ApiException.class, () -> {
                    userService.create(email2, nickname, password);
                });
        assertEquals(ErrorCode.DUPLICATED_NICKNAME, apiException.getErrorCode());
    }

    @Test
    @DisplayName("유저 조회를 성공적으로 성공한다.")
    public void testFindById() {
        //given
        String email = "admin@gmail.com";
        String nickname = "관리자";
        Role role = Role.ADMIN;
        User savedUser = saveUser(email, nickname, role);
        long id = 1L;

        //when
        User user = userService.findById(id);

        //then
        assertEquals(user.getId(), savedUser.getId());
        assertEquals(user.getNickname(), savedUser.getNickname());
        assertEquals(user.getEmail(), savedUser.getEmail());
    }

    @Test
    @DisplayName("유저 수정을 성공적으로 성공한다.")
    public void testUpdate() {
        //given
        String email = "admin@gmail.com";
        String nickname = "관리자";
        Role role = Role.ADMIN;
        User savedUser = saveUser(email, nickname, role);

        String newNickname = "관리자2";

        //when
        userService.update(savedUser.getId(), newNickname, "010-1234-5678", Gender.MALE, null, true);

        //then
        assertEquals(newNickname, savedUser.getNickname());
    }

    @Test
    @DisplayName("유저 삭제를 성공적으로 성공한다.")
    public void testDelete() {
        //given
        String email = "admin@gmail.com";
        String nickname = "관리자";
        Role role = Role.ADMIN;
        User savedUser = saveUser(email, nickname, role);

        //when
        userService.delete(savedUser.getId(), savedUser.getId());

        // when && then
        ApiException apiException =
                assertThrows(ApiException.class, () -> {
                    userService.findById(savedUser.getId());
                });
        assertEquals(ErrorCode.USER_NOT_FOUND, apiException.getErrorCode());
    }

    @Test
    @DisplayName("유저 리스트 조회를 성공적으로 성공한다.")
    public void testList() {
        //given
        String email = "admin@gmail.com";
        String nickname = "관리자";
        String email2 = "admin2@gmail.com";
        String nickname2 = "관리자2";
        BasicSearch search = new BasicSearch();
        Role role = Role.ADMIN;

        User savedUser1 = saveUser(email, nickname, role);
        User savedUser2 = saveUser(email2, nickname2, role);

        //when
        Page<User> users = userRepository.list(search.getSearchString(), search.getPageable(), role);

        // then
        assertThat(users.getContent()).hasSize(2)
                .extracting("id", "email", "nickname")
                .containsExactlyInAnyOrder(
                        tuple(savedUser2.getId(), savedUser2.getEmail(), savedUser2.getNickname()),
                        tuple(savedUser1.getId(), savedUser1.getEmail(), savedUser1.getNickname())
                );
    }

    private User saveUser(String email, String nickname, Role role) {
        User user = User.builder()
                .email(email)
                .nickname(nickname)
                .password("1234")
                .gender(Gender.NONE)
                .provider(AuthProvider.FOODING)
                .build();
        userRepository.save(user);

        UserAuthority userAuthority = UserAuthority.builder()
                .user(user)
                .role(role)
                .build();
        userAuthorityRepository.save(userAuthority);
        return user;
    }
}
