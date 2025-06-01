package im.fooding.core.service.device;

import im.fooding.core.global.exception.ApiException;
import im.fooding.core.global.exception.ErrorCode;
import im.fooding.core.model.device.Device;
import im.fooding.core.model.device.DevicePlatform;
import im.fooding.core.model.store.Store;
import im.fooding.core.model.user.User;
import im.fooding.core.repository.device.DeviceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DeviceService {
    private final DeviceRepository deviceRepository;

    /**
     * 디바이스 목록 조회
     *
     * @param searchString 검색어
     * @param storeId 스토어 ID
     * @param userId 유저 ID
     * @param pageable 페이지 정보
     * @return Page<Device>
     */
    public Page<Device> list(String searchString, Long storeId, Long userId, Pageable pageable) {
        if (userId != null) {
            return deviceRepository.findAllByUserIdOrStoreId(userId, storeId, pageable);
        }
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
    public Device create(String uuid, String name, DevicePlatform type, String osVersion, String packageName, Store store){
        Device device = Device.builder()
                .uuid(uuid)
                .name(name)
                .type(type)
                .osVersion(osVersion)
                .packageName(packageName)
                .store(store)
                .build();

        return deviceRepository.save(device);
    }

    /**
     * 디바이스에 유저 연결
     *
     * @param user
     * @param deviceId
     */
    public void updateUser(User user, Long deviceId){
        Device device = deviceRepository.findById(deviceId).orElseThrow(
                () -> new ApiException(ErrorCode.DEVICE_NOT_FOUND)
        );
        device.updateUser(user);
    }

    /**
     * 디바이스 삭제
     *
     * @param deviceId
     */
    public void delete(Long deviceId, Long deletedBy){
        Device device = deviceRepository.findById(deviceId).orElseThrow(
                () -> new ApiException(ErrorCode.DEVICE_NOT_FOUND)
        );
        device.delete(deletedBy);
    }

    public Device findByUuidAndStoreAndPackageName(String uuid, Store store, String packageName) {
        return deviceRepository.findByUuidAndStoreAndPackageName(uuid, store, packageName);
    }
}
