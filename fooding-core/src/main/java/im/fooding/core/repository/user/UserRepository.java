package im.fooding.core.repository.user;

import im.fooding.core.model.user.Role;
import im.fooding.core.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByRefreshToken(String token);

    Optional<User> findByEmailAndRole(String email, Role role);
}
