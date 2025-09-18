package im.fooding.core.service.store;

import im.fooding.core.global.exception.ApiException;
import im.fooding.core.global.exception.ErrorCode;
import im.fooding.core.model.store.Store;
import im.fooding.core.model.store.StoreService;
import im.fooding.core.model.store.StoreServiceType;
import im.fooding.core.repository.store.StoreServiceFilter;
import im.fooding.core.repository.store.StoreServiceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class StoreServiceService {
    private final StoreServiceRepository repository;

    /**
     * 스토어 서비스 생성
     *
     * @param store
     */
    public long create(Store store, StoreServiceType type){
        StoreService storeService = StoreService.builder()
                .store( store )
                .type( type )
                .build();
        return repository.save( storeService ).getId();
    }

    /**
     * 특정 스토어 서비스 상세 조회
     *
     * @param id
     */
    public StoreService findById( Long id ){
        return repository.findById( id ).orElseThrow(
                () -> new ApiException(ErrorCode.STORE_SERVICE_NOT_FOUND)
        );
    }

    /**
     * 스토어 서비스 전제 조회
     *
     * @param searchString
     * @param storeId 가게 ID (선택사항, null이면 전체 조회)
     * @param serviceType 서비스 타입 (선택사항, null이면 전체 조회)
     * @param pageable
     * @return Page<StoreService>
     */
    public Page<StoreService> list(String searchString, Long storeId, StoreServiceType serviceType, Pageable pageable ){
        return repository.list( searchString, storeId, serviceType, pageable );
    }

    /**
     * 가게에 가입된 스토어 서비스 조회
     *
     * @param storeId
     * @return StoreService[]
     */
    public List<StoreService> findByStoreId(Long storeId ) {
        return repository.findByStoreId( storeId ).stream().filter( storeService -> !storeService.isDeleted() ).collect(Collectors.toList());
    }

    public StoreService get(StoreServiceFilter filter) {
        return repository.find(filter)
                .orElseThrow(() -> new ApiException(ErrorCode.STORE_SERVICE_NOT_FOUND));
    }

    /**
     * 스토어 서비스 해지( 비활성화 )
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
     * 스토어 서비스 신청( 활성화 )
     *
     * @param id
     */
    @Transactional
    public void active( Long id ){
        StoreService storeService = repository.findById( id ).orElseThrow(
                () -> new ApiException(ErrorCode.STORE_SERVICE_NOT_FOUND)
        );
        storeService.activate();
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
