package im.fooding.core.repository.device;

import im.fooding.core.model.device.Device;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QDeviceRepository {
    Page<Device> list(String searchString, Long storeId, Long userId, Pageable pageable);
}
