package im.fooding.core.service.device;

import im.fooding.core.model.device.Device;
import im.fooding.core.repository.device.DeviceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

@Service
@RequiredArgsConstructor
@Slf4j
public class DeviceService {
    private final DeviceRepository deviceRepository;

    /**
     * Store ID로 리스트 조회
     *
     * @param storeId
     * @param pageable
     * @return Page<Device>
     */
    public Page<Device> findByStoreId(String searchString, long storeId, Pageable pageable){
        return deviceRepository.list(searchString, pageable, storeId);
    }

    /**
     * 모든 Device 리스트 조회
     *
     * @param pageable
     * @return Page<Device>
     */
    public Page<Device> findAllDevice(Pageable pageable){
        return deviceRepository.findAllByNotDeleted(pageable);
    }
}
