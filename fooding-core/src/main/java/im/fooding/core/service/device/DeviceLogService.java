package im.fooding.core.service.device;

import im.fooding.core.model.device.DeviceLog;
import im.fooding.core.model.device.DeviceLogType;
import im.fooding.core.repository.device.DeviceLogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class DeviceLogService {
    private final DeviceLogRepository repository;

    // 로그 작성
    public void logging(long deviceId, DeviceLogType type ){
        // 로그 내용 작성
        String operation;
        switch (type){
            case SERVICE_MANAGEMENT -> operation = "서비스 변경 관리";
            case SERVICE_REWARD -> operation = "서비스 변경 리워드";
            case CONNECTED -> operation = "계정 연결";
            case DISCONNECTED -> operation = "연결 해제";
            default -> operation = "";
        }
        DeviceLog log = DeviceLog.builder()
                .deviceId( deviceId )
                .operation( operation )
                .build();
        repository.save(log);
    }

    // 로그 조회
    public Page<DeviceLog> getDeviceLogs(long deviceId, Pageable pageable) {
        return repository.findAllByDeviceId(deviceId, pageable);
    }
}
