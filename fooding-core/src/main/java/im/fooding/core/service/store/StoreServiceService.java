package im.fooding.core.service.store;

import im.fooding.core.global.exception.ApiException;
import im.fooding.core.global.exception.ErrorCode;
import im.fooding.core.model.store.Store;
import im.fooding.core.model.store.StoreService;
import im.fooding.core.model.store.StoreServiceType;
import im.fooding.core.repository.store.StoreServiceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class StoreServiceService {
    private final StoreServiceRepository repository;

    /**
     * 스토어 서비스 생성
     *
     * @param store
     * @param type
     */
    public void create(Store store, StoreServiceType type){
        StoreService storeService = StoreService.builder()
                .store( store )
                .type( type )
                .build();
        repository.save( storeService );
    }

    /**
     * 스토어 서비스 전제 조회
     *
     * @param searchString
     * @param pageable
     * @return Page<StoreService>
     */
    public Page<StoreService> list(String searchString, Pageable pageable ){
        return repository.list( searchString, pageable );
    }

    /**
     * 스토어 서비스 해지
     *
     * @param id
     */
    @Transactional
    public void inactive( Long id ){
        StoreService storeService = repository.findById( id ).orElseThrow(
                () -> new ApiException(ErrorCode.STORE_SERVICE_NOT_FOUND)
        );
        storeService.inactive();
    }

    /**
     * 스토어 서비스 삭제
     *
     * @param id
     */
    @Transactional
    public void delete( Long id, Long deletedBy ){
        StoreService storeService = repository.findById( id ).orElseThrow(
                () -> new ApiException(ErrorCode.STORE_SERVICE_NOT_FOUND)
        );
        storeService.delete( deletedBy );
    }

}
