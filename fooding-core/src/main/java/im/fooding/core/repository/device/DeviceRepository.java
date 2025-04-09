package im.fooding.core.repository.device;

import im.fooding.core.model.device.Device;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DeviceRepository extends JpaRepository<Device, Long>, QDeviceRepository {
    Page<Device> findAllByNotDeleted( Pageable pageable );
}
