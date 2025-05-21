package im.fooding.core.repository.user;

import im.fooding.core.model.user.UserAuthority;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserAuthorityRepository extends JpaRepository<UserAuthority, Long> {
    List<UserAuthority> findAllByUserId(Long id);
}
