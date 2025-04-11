package im.fooding.core.repository.device;

import im.fooding.core.model.device.Device;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceRepository extends JpaRepository<Device, Long>, QDeviceRepository {

}
