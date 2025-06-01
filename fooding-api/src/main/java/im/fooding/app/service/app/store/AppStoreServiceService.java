package im.fooding.app.service.app.store;

import im.fooding.app.dto.response.admin.service.StoreServiceResponse;
import im.fooding.core.service.store.StoreService;
import im.fooding.core.service.store.StoreServiceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class AppStoreServiceService {
    private final StoreServiceService service;

    public List<StoreServiceResponse> list( long storeId ){
        return service.findByStoreId( storeId ).stream().map(StoreServiceResponse::of).collect(Collectors.toList());
    }
}
