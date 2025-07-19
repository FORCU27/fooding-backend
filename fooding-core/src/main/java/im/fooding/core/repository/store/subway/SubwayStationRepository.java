package im.fooding.core.repository.store.subway;

import im.fooding.core.model.store.subway.SubwayStation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubwayStationRepository extends JpaRepository<SubwayStation, Long> {
    SubwayStation findByNameAndLine( String name, String line );
}
