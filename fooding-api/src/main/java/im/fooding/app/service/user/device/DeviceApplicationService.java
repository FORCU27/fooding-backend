package im.fooding.app.service.user.device;

import im.fooding.app.dto.request.admin.device.RetrieveAllDeviceRequest;
import im.fooding.app.dto.request.user.device.CreateDeviceRequest;
import im.fooding.app.dto.request.user.device.RetrieveStoreDeviceRequest;
import im.fooding.app.dto.response.user.device.StoreDeviceResponse;
import im.fooding.core.common.PageInfo;
import im.fooding.core.common.PageResponse;
import im.fooding.core.model.device.Device;
import im.fooding.core.model.user.User;
import im.fooding.core.service.device.DeviceAppService;
import im.fooding.core.service.device.DeviceService;
import im.fooding.core.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DeviceApplicationService {
    private final DeviceService deviceService;
    private final DeviceAppService deviceAppService;
    private final UserService userService;

    /**
     * store id로 조회
     *
     * @param request
     * @return StoreDeviceResponse
     */
    @Transactional(readOnly = true)
    public PageResponse<StoreDeviceResponse> retrieveStoreDevice(RetrieveStoreDeviceRequest request ){
        Pageable pageable = PageRequest.of(request.getPageNum(), request.getPageSize() );
        Page<Device> result = deviceService.list(request.getSearchString(), request.getStoreId(), pageable );
        PageInfo pageInfo = PageInfo.of( result );
        return PageResponse.of( result.getContent().stream().map(StoreDeviceResponse::of).collect(Collectors.toList()), pageInfo );
    }

    /**
     * 모든 디바이스 조회
     *
     * @param request
     * @return StoreDeviceResponse
     */
    @Transactional(readOnly = true)
    public PageResponse<StoreDeviceResponse> retrieveAllDevice(RetrieveAllDeviceRequest request ){
        Pageable pageable = PageRequest.of(request.getPageNum(), request.getPageSize() );
        Page<Device> result = deviceService.list(null, Long.MIN_VALUE, pageable);
        return PageResponse.of( result.getContent().stream().map(StoreDeviceResponse::of).collect(Collectors.toList()), PageInfo.of( result ) );
    }

    /**
     * 비로그인 유저의 디바이스 접속
     *
     * @param request
     */
    @Transactional
    public void createDevice(CreateDeviceRequest request ){
        Device device = deviceService.create( request.name(), request.type(), request.osVersion() );
        deviceAppService.create( device, request.appVersion(), request.packageName() );
    }

    /**
     * 로그인 유저의 디바이스 접속
     *
     * @param userId
     * @param deviceId
     * @return StoreDeviceResponse
     */
    @Transactional
    public void connectUser( Long userId, Long deviceId ){
        User user = userService.findById( userId );
        deviceService.updateUser( user, deviceId );
    }
}
