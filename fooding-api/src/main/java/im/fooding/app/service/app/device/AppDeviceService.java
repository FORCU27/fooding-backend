package im.fooding.app.service.app.device;

import im.fooding.app.dto.request.user.device.ConnectDeviceRequest;
import im.fooding.app.dto.request.user.device.RetrieveDeviceRequest;
import im.fooding.app.dto.response.user.device.StoreDeviceResponse;
import im.fooding.core.common.PageInfo;
import im.fooding.core.common.PageResponse;
import im.fooding.core.model.device.Device;
import im.fooding.core.model.device.DeviceApp;
import im.fooding.core.model.device.StoreDevice;
import im.fooding.core.model.store.Store;
import im.fooding.core.model.user.User;
import im.fooding.core.service.device.DeviceAppService;
import im.fooding.core.service.device.DeviceService;
import im.fooding.core.service.device.StoreDeviceService;
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
    private final DeviceAppService deviceAppService;
    private final StoreDeviceService storeDeviceService;

    /**
     * 디바이스 목록 조회
     *
     * @param request
     * @return StoreDeviceResponse
     */
    @Transactional(readOnly = true)
    public PageResponse<StoreDeviceResponse> list(RetrieveDeviceRequest request) {
        Page<Device> result = deviceService.list(request.getSearchString(), request.getUserId(), request.getPageable());
        PageInfo pageInfo = PageInfo.of(result);
        return PageResponse.of(result.getContent().stream().map(StoreDeviceResponse::of).collect(Collectors.toList()), pageInfo);
    }

    /**
     * 유저의 디바이스 접속
     *
     * @param request
     * @param userId
     */
    @Transactional
    public void connect(ConnectDeviceRequest request, Long userId) {
        log.info( "--------------------------- user Id: {}", userId );
        // uuid + packageName을 통해 디바이스 정보 존재 여부 확인
        Device existedDevice = deviceService.findByUuidAndPackageName( request.uuid(), request.packageName() );
        // 디바이스가 존재한다면 입력받은 정보로 업데이트
        if( existedDevice != null ) {
            // Device 업데이트
            deviceService.updateDevice(request.name(), request.osVersion(), request.appVersion(), existedDevice.getId() );
            // DeviceApp 업데이트
            DeviceApp deviceApp = deviceAppService.findByDeviceId( existedDevice.getId() );
            deviceAppService.updateVersion( deviceApp.getId(), request.appVersion() );
        }
        // 디바이스가 존재하지 않다면 새로운 디바이스 생성
        else {
            // Device 생성
            Device newDevice = deviceService.create( request.uuid(), request.name(), request.type(), request.osVersion(), request.packageName() );
            // DeviceApp 생성
            deviceAppService.create( newDevice, request.appVersion(), request.packageName() );
            existedDevice = newDevice;
        }

        if( userId == null ) return;

        // 해당 기기와 User를 연결
        if( userId != 0 ) {
            User user = userService.findById(userId);
            deviceService.updateUser( user, existedDevice.getId() );
        }

        // 해당 기기와 Store를 연결
        if( request.storeId() != 0 ) {
            StoreDevice storeDevice = storeDeviceService.findByStoreIdAndDeviceId(request.storeId(), request.deviceId());
            if (storeDevice == null) {
                Store store = storeService.findById(request.storeId());
                storeDeviceService.create(store, existedDevice, null);
            }
        }
    }
}
