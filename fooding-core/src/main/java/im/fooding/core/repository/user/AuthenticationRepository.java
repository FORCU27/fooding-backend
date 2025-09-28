package im.fooding.core.repository.user;

import im.fooding.core.model.user.Authentication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AuthenticationRepository extends JpaRepository<Authentication, Long>, QAuthenticationRepository {

}
