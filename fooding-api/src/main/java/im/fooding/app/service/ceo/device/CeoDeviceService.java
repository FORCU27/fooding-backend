package im.fooding.app.service.ceo.device;

import im.fooding.app.dto.request.ceo.device.GetDeviceLogsRequest;
import im.fooding.app.dto.response.ceo.device.GetDeviceLogDetailResponse;
import im.fooding.app.dto.response.ceo.device.GetDeviceLogsResponse;
import im.fooding.core.common.PageInfo;
import im.fooding.core.common.PageResponse;
import im.fooding.core.global.exception.ApiException;
import im.fooding.core.global.exception.ErrorCode;
import im.fooding.core.model.device.*;
import im.fooding.core.model.store.Store;
import im.fooding.core.service.device.DeviceAppService;
import im.fooding.core.service.device.DeviceLogService;
import im.fooding.core.service.device.DeviceService;
import im.fooding.core.service.device.StoreDeviceService;
import im.fooding.core.service.store.StoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional( readOnly = true )
public class CeoDeviceService {
    private final DeviceService deviceService;
    private final DeviceAppService deviceAppService;
    private final StoreDeviceService storeDeviceService;

    private final DeviceLogService logService;
    private final StoreService storeService;

    /**
     * 디바이스와 계정의 연결을 해제하는 API
     *
     * @param deviceId
     */
    @Transactional( readOnly = false )
    public void disconnectWithUser( long deviceId, long deletedBy ){
        deviceService.updateUser( null, deviceId );
        // 유저와 연결이 해제될 때, 가게가 연결되어 있으면 가게도 제거
        StoreDevice storeDevice = storeDeviceService.findByDeviceId( deviceId );
        if( storeDevice != null ) storeDeviceService.delete( storeDevice.getId(), deletedBy );

        // 로그 기록
        logService.logging( deviceId, DeviceLogType.DISCONNECTED );
    }

    /**
     * 디바이스의 담당 서비스 변경
     *
     * @param id
     * @param storeId
     * @param serviceType
     */
    @Transactional( readOnly = false )
    public void updateDeviceServiceType( long id, Long storeId, ServiceType serviceType ) {
        Device device = deviceService.findById( id );
        StoreDevice storeDevice = storeDeviceService.findByDeviceId( device.getId() );
        if( storeDevice == null ){
            Store store = storeService.findById( storeId );
            if( store == null ) throw new ApiException(ErrorCode.STORE_NOT_FOUND);
            storeDevice = storeDeviceService.create(store, device, serviceType);
        }
        else storeDeviceService.updateServiceType( storeDevice.getId(), serviceType );

        // 로그 기록
        if( serviceType == ServiceType.REWARD_MANAGEMENT || serviceType == ServiceType.REWARD_RECEIPT ) logService.logging( storeDevice.getId(), DeviceLogType.SERVICE_REWARD );
        else logService.logging( device.getId(), DeviceLogType.SERVICE_REWARD );
    }

    /**
     * 디바이스 로그 조회
     *
     * @param request
     */
    public PageResponse<GetDeviceLogsResponse> retrieveLogs( GetDeviceLogsRequest request ) {
        // 로그 전달
        Pageable pageable = PageRequest.of( request.getPageNum()-1, request.getPageSize() );
        Page<DeviceLog> result = logService.getDeviceLogs( request.getDeviceId(), pageable );

        return PageResponse.of( result.stream().map(GetDeviceLogsResponse::of).toList(), PageInfo.of( result ) );
    }

    /**
     * 디바이스 로그 상세 조회
     *
     * @param logId
     */
    public GetDeviceLogDetailResponse getDeviceLogDetail( long logId ) {
        DeviceLog log = logService.getDeviceLog( logId );
        Device device = deviceService.findById( log.getDeviceId() );

        return GetDeviceLogDetailResponse.of( log, device );
    }
}
