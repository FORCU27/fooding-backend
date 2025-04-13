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
     * 디바이스 목록 조회
     *
     * @param searchString
     * @param storeId
     * @param pageable
     * @return Page<Device>
     */
    public Page<Device> list(String searchString, long storeId, Pageable pageable){
        return deviceRepository.list(searchString, pageable, storeId);
    }
}
