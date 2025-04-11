package im.fooding.core.repository.waiting;

import im.fooding.core.model.waiting.WaitingLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WaitingLogRepository extends JpaRepository<WaitingLog, Long>, QWaitingLogRepository {
}
