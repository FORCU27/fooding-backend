package im.fooding.core.service.device;

import im.fooding.core.model.device.Device;
import im.fooding.core.model.device.DeviceApp;
import im.fooding.core.model.device.ServiceType;
import im.fooding.core.repository.device.DeviceAppRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional( readOnly = true )
public class DeviceAppService {
    private final DeviceAppRepository repository;

    public void create(
        Device device,
        String version,
        String packageName
    ){
        // 현재 Token 기능은 무시
        DeviceApp deviceApp = DeviceApp.builder()
                .device( device )
                .version( version )
                .packageName( packageName )
                .build();
        repository.save(deviceApp);
    }

    public void delete( Long deviceAppId ){
        repository.deleteById(deviceAppId);
    }

    @Transactional( readOnly = false )
    public void updateVersion( Long deviceAppId, String version ){
        DeviceApp deviceApp = repository.findById( deviceAppId ).orElseThrow();
        deviceApp.updateVersion( version );
    }

    public DeviceApp findById( Long id ){
        return repository.findById( id ).orElseThrow();
    }

    public DeviceApp findByDeviceId( Long deviceId ){
        Optional<DeviceApp> deviceApp = repository.findByDeviceId( deviceId );
        return deviceApp.orElse(null);
    }

}
