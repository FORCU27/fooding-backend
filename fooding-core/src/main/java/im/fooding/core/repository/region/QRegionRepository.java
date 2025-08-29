package im.fooding.core.repository.region;

import im.fooding.core.model.region.Region;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QRegionRepository {

    // Extended: add searchString for name like filtering
    Page<Region> listActive(Region parentRegion, Integer level, String searchString, Pageable pageable);
}
