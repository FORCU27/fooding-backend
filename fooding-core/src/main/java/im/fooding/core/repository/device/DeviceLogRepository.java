package im.fooding.core.repository.device;

import im.fooding.core.model.device.DeviceLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceLogRepository extends JpaRepository<DeviceLog, Long> {
    Page<DeviceLog> findAllByDeviceId(Long deviceId, Pageable pageable);
}
