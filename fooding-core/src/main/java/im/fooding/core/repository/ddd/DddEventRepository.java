package im.fooding.core.repository.ddd;

import im.fooding.core.model.ddd.DddEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DddEventRepository extends JpaRepository<DddEvent, Long> {
}
