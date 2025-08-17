package im.fooding.core.repository.region;

import im.fooding.core.model.region.Region;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegionRepository extends JpaRepository<Region, String>, QRegionRepository{

    Page<Region> findAllByDeletedFalse(Pageable pageable);
}
