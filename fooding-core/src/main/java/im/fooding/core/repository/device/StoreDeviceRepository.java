package im.fooding.core.repository.device;

import im.fooding.core.model.device.StoreDevice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StoreDeviceRepository extends JpaRepository<StoreDevice, Long> {
    Optional<StoreDevice> findByDeviceId( long deviceId );
}
