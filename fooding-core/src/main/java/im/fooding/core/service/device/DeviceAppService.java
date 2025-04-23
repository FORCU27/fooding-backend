package im.fooding.core.service.device;

import im.fooding.core.model.device.Device;
import im.fooding.core.model.device.DeviceApp;
import im.fooding.core.repository.device.DeviceAppRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class DeviceAppService {
    private final DeviceAppRepository deviceAppRepository;

    /**
     * 디바이스 앱 생성
     *
     * @param device
     * @param version
     * @param packageName
     */
    public void create(Device device, String version, String packageName){
        DeviceApp deviceApp = DeviceApp.builder()
                .device( device )
                .version( version )
                .packageName( packageName )
                .build();
        deviceAppRepository.save( deviceApp );
    }
}
