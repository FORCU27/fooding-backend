package im.fooding.core.service.device;

import im.fooding.core.global.exception.ApiException;
import im.fooding.core.global.exception.ErrorCode;
import im.fooding.core.model.device.Device;
import im.fooding.core.model.device.DeviceType;
import im.fooding.core.model.user.User;
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

    /**
     * 디바이스 생성
     *
     * @param name
     * @param type
     * @param osVersion
     * @return deviceId
     */
    public Device create(String name, DeviceType type, String osVersion ){
        Device device = Device.builder()
                .name( name )
                .type( type )
                .osVersion( osVersion )
                .build();
        return deviceRepository.save( device );
    }

    /**
     * 디바이스에 유저 연결
     *
     * @param user
     * @param deviceId
     */
    public void updateUser(User user, Long deviceId ){
        Device device = deviceRepository.findById( deviceId ).orElseThrow(
                () -> new ApiException(ErrorCode.DEVICE_NOT_FOUND)
        );
        device.updateUser( user );
    }

}
