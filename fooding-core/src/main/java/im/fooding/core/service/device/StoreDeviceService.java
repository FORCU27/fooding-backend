package im.fooding.core.service.device;

import im.fooding.core.model.device.Device;
import im.fooding.core.model.device.ServiceType;
import im.fooding.core.model.device.StoreDevice;
import im.fooding.core.model.store.Store;
import im.fooding.core.repository.device.StoreDeviceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional( readOnly = true )
public class StoreDeviceService {
    private final StoreDeviceRepository repository;

    public StoreDevice create(Store store, Device device, ServiceType serviceType) {
        StoreDevice storeDevice = StoreDevice.builder()
                .store( store )
                .device( device )
                .type( serviceType )
                .build();
        return repository.save( storeDevice );
    }

    public StoreDevice findByDeviceId( long deviceId ){
        return repository.findByDeviceId( deviceId ).orElse( null );
    }

    public StoreDevice findByStoreIdAndDeviceId( long storeId, long deviceId ) {
        return repository.findByDeviceIdAndStoreId( deviceId, storeId ).orElse( null );
    }

    @Transactional( readOnly = false )
    public void connectDevice( long id ){
        StoreDevice storeDevice = repository.findById( id ).orElseThrow();
        storeDevice.connectDevice();
    }

    @Transactional( readOnly = false )
    public void updateServiceType( long id, ServiceType serviceType ){
        StoreDevice storeDevice = repository.findById( id ).orElseThrow();
        storeDevice.updateServiceType( serviceType );
    }

    @Transactional( readOnly = false )
    public void delete( long id, long deletedBy ){
        StoreDevice storeDevice = repository.findById( id ).orElseThrow();
        storeDevice.delete( deletedBy );
    }
}
