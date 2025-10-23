package im.fooding.core.repository.device;

import im.fooding.core.model.device.Device;
import im.fooding.core.model.store.Store;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Long>, QDeviceRepository {
    Device findByUuidAndPackageName(String uuid, String packageName);
}
