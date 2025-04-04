package im.fooding.core.service.user;

import im.fooding.core.TestConfig;
import im.fooding.core.common.BasicSearch;
import im.fooding.core.global.exception.ApiException;
import im.fooding.core.global.exception.ErrorCode;
import im.fooding.core.model.user.Role;
import im.fooding.core.model.user.User;
import im.fooding.core.repository.user.UserRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.tuple;
import static org.junit.jupiter.api.Assertions.*;

@Disabled // 클래스 전체 비활성화
@Sql(statements = "ALTER TABLE USERS ALTER COLUMN ID RESTART WITH 1", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class UserServiceTest extends TestConfig {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Test
    @DisplayName("회원가입 - 성공")
    public void saveManager() throws Exception {
        //given
        String email = "admin@gmail.com";
        String nickname = "관리자";
        String password = "1234";
        Role role = Role.ADMIN;

        //when
        userService.save(email, nickname, password, role);

        //then
        assertTrue(userRepository.findByEmailAndRole(email, role).isPresent());
    }

    @Test
    @DisplayName("회원가입 - 이메일 중복으로 에러")
    public void duplicatedEmail() throws Exception {
        //given
        String email = "admin@gmail.com";
        String nickname = "관리자";
        String password = "1234";
        Role role = Role.ADMIN;
        saveUser(email, nickname, role);

        String nickname2 = "관리자2";

        // when & then
        ApiException apiException =
                assertThrows(ApiException.class, () -> {
                    userService.save(email, nickname2, password, role);
                });
        assertEquals(ErrorCode.DUPLICATED_REGISTER_EMAIL, apiException.getErrorCode());
    }

    @Test
    @DisplayName("회원가입 - 닉네임 중복으로 에러")
    public void duplicatedNickname() throws Exception {
        //given
        String email = "admin@gmail.com";
        String nickname = "관리자";
        String password = "1234";
        Role role = Role.ADMIN;
        saveUser(email, nickname, role);

        String email2 = "admin2@gmail.com";

        // when & then
        ApiException apiException =
                assertThrows(ApiException.class, () -> {
                    userService.save(email2, nickname, password, role);
                });
        assertEquals(ErrorCode.DUPLICATED_NICKNAME, apiException.getErrorCode());
    }

    @Test
    @DisplayName("조회 - 성공")
    public void findById() throws Exception {
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
    @DisplayName("수정 - 성공")
    public void update() throws Exception {
        //given
        String email = "admin@gmail.com";
        String nickname = "관리자";
        Role role = Role.ADMIN;
        User savedUser = saveUser(email, nickname, role);

        String newNickname = "관리자2";

        //when
        userService.update(savedUser.getId(), newNickname, "010-1234-5678", "default-profile.png" );

        //then
        assertEquals(newNickname, savedUser.getNickname());
    }

    @Test
    @DisplayName("삭제 - 성공")
    public void delete() throws Exception {
        //given
        String email = "admin@gmail.com";
        String nickname = "관리자";
        Role role = Role.ADMIN;
        User savedUser = saveUser(email, nickname, role);

        //when
        userService.delete(savedUser.getId(), savedUser.getId());

        //then
        // when & then
        ApiException apiException =
                assertThrows(ApiException.class, () -> {
                    userService.findById(savedUser.getId());
                });
        assertEquals(ErrorCode.USER_NOT_FOUND, apiException.getErrorCode());
    }

    @Test
    @DisplayName("리스트 - 성공")
    public void list() throws Exception {
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
        Page<User> users = userRepository.list(search.getSearchString(), search.getPageable());

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
                .role(role)
                .password("1234")
                .build();
        return userRepository.save(user);
    }
}
