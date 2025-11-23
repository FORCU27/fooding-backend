package im.fooding.core.repository.place;

import im.fooding.core.model.place.PlaceSetting;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaceSettingRepository extends JpaRepository<PlaceSetting, Long> {
    List<PlaceSetting> findByStoreIdAndDeletedFalse(Long storeId);
    Optional<PlaceSetting> findByIdAndDeletedFalse(Long id);
}
