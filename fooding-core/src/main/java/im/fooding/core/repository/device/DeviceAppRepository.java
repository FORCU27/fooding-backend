package im.fooding.core.repository.device;

import im.fooding.core.model.device.DeviceApp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DeviceAppRepository extends JpaRepository<DeviceApp, Long> {
    Optional<DeviceApp> findByDeviceId(Long deviceId);
}
