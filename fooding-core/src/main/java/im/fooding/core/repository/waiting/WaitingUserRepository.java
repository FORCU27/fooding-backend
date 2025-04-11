package im.fooding.core.repository.waiting;

import im.fooding.core.model.waiting.WaitingUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WaitingUserRepository extends JpaRepository<WaitingUser, Long> {
}
