package im.fooding.app.service.ceo.device;

import im.fooding.core.model.device.DeviceLogType;
import im.fooding.core.model.device.ServiceType;
import im.fooding.core.model.device.StoreDevice;
import im.fooding.core.service.device.DeviceAppService;
import im.fooding.core.service.device.DeviceLogService;
import im.fooding.core.service.device.DeviceService;
import im.fooding.core.service.device.StoreDeviceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
        if( storeDevice != null ) storeDeviceService.delete( deviceId, deletedBy );

        // 로그 기록
        logService.logging( deviceId, DeviceLogType.DISCONNECTED );
    }

    /**
     * 디바이스의 담당 서비스 변경
     *
     * @param id
     * @param serviceType
     */
    @Transactional( readOnly = false )
    public void updateDeviceServiceType( long id, ServiceType serviceType ) {
        storeDeviceService.updateServiceType( id, serviceType );

        // 로그 기록
        if( serviceType == ServiceType.REWARD_MANAGEMENT || serviceType == ServiceType.REWARD_RECEIPT ) logService.logging( id, DeviceLogType.SERVICE_REWARD )
        else logService.logging( id, DeviceLogType.SERVICE_REWARD );
    }

    /**
     * 디바이스 로그 조회
     *
     * @param request
     * @return StoreDeviceResponse
     */
}
