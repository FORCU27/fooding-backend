package im.fooding.core.repository.user;

import im.fooding.core.model.user.AuthProvider;
import im.fooding.core.model.user.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>, QUserRepository {
    Optional<User> findByRefreshToken(String token);

    @EntityGraph(attributePaths = {"authorities"})
    Optional<User> findByEmailAndProvider(String email, AuthProvider provider);

    Optional<User> findByNickname(String nickname);

    Optional<User> findByPhoneNumber(String phoneNumber);

    @Override
    @EntityGraph(attributePaths = {"authorities"})
    Optional<User> findById(Long id);

    Optional<User> findByPhoneNumberAndName(String phoneNumber, String name);
}
