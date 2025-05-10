package im.fooding.core.service.store;

import im.fooding.core.global.exception.ApiException;
import im.fooding.core.global.exception.ErrorCode;
import im.fooding.core.model.store.Store;
import im.fooding.core.model.store.StoreSortType;
import im.fooding.core.repository.store.StoreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.query.SortDirection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class StoreService {

    private final StoreRepository storeRepository;

    /**
     * 가게 목록 조회
     * TODO : 대기시간 순 정렬 추가
     *
     * @param pageable
     * @param sortType
     * @param sortDirection
     */
    public Page<Store> list(
            Pageable pageable,
            StoreSortType sortType,
            SortDirection sortDirection) {
        return storeRepository.list(pageable, sortType, sortDirection);
    }

    /**
     * 가게 아이디로 조회
     * 
     * @param storeId
     * @return
     */
    public Store findById(long storeId) {
        return storeRepository.findById(storeId).filter(it -> !it.isDeleted())
                .orElseThrow(() -> new ApiException(ErrorCode.STORE_NOT_FOUND));
    }

    /**
     * 가게 생성
     * 
     * @param store
     * @return 생성된 가게의 ID
     */
    @Transactional
    public Long create(Store store) {
        Store savedStore = storeRepository.save(store);
        return savedStore.getId();
    }

    /**
     * 가게 삭제
     * 
     * @param store
     */
    @Transactional
    public void delete(Store store) {
        store.delete();
    }
}
