package im.fooding.core.repository.waiting;

import im.fooding.core.model.waiting.Waiting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WaitingRepository extends JpaRepository<Waiting, Long> {


}
