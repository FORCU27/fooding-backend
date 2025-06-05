package im.fooding.app.service.app.device;

import im.fooding.app.dto.request.user.device.ConnectDeviceRequest;
import im.fooding.app.dto.request.user.device.RetrieveDeviceRequest;
import im.fooding.app.dto.response.user.device.StoreDeviceResponse;
import im.fooding.core.common.PageInfo;
import im.fooding.core.common.PageResponse;
import im.fooding.core.model.device.Device;
import im.fooding.core.model.store.Store;
import im.fooding.core.model.user.User;
import im.fooding.core.service.device.DeviceService;
import im.fooding.core.service.store.StoreService;
import im.fooding.core.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
// TODO: CEO 분리
public class AppDeviceService {
    private final DeviceService deviceService;
    private final UserService userService;
    private final StoreService storeService;

    /**
     * 디바이스 목록 조회
     *
     * @param request
     * @return StoreDeviceResponse
     */
    @Transactional(readOnly = true)
    public PageResponse<StoreDeviceResponse> list(String service, RetrieveDeviceRequest request) {
        Page<Device> result = deviceService.list(null, request.getStoreId(), null, request.getPageable());
        PageInfo pageInfo = PageInfo.of(result);
        return PageResponse.of(result.getContent().stream().map(StoreDeviceResponse::of).collect(Collectors.toList()), pageInfo);
    }

    /**
     * 유저의 디바이스 접속
     *
     * @param request
     */
    @Transactional
    public void connect(ConnectDeviceRequest request, Long userId) {
        Store store = request.storeId() != null ? storeService.findById(request.storeId()) : null;

        // uuid, store, packageName으로 기존 디바이스 조회
        Device existingDevice = deviceService.findByUuidAndStoreAndPackageName(request.uuid(), store, request.packageName());
        if (existingDevice != null) {
            // 기존 디바이스가 있으면 업데이트
            // TODO: 기타 메타데이터도 업데이트
            existingDevice.update(request.name());
            existingDevice.updateOsVersion(request.osVersion());
            existingDevice.connectDevice();
            if (userId != null) {
                User user = userService.findById(userId);
                existingDevice.updateUser(user);
            }
        } else {
            // 없으면 새로 생성
            Device device = deviceService.create(request.uuid(), request.name(), request.type(), request.osVersion(), request.packageName(), store);
            if (userId != null) {
                User user = userService.findById(userId);
                device.updateUser(user);
            }
        }
    }
}
