package im.fooding.core.service.store;

import im.fooding.core.global.exception.ApiException;
import im.fooding.core.global.exception.ErrorCode;
import im.fooding.core.model.store.Store;
import im.fooding.core.model.store.StoreSortType;
import im.fooding.core.model.user.User;
import im.fooding.core.repository.store.StoreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.query.SortDirection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
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
    public Page<Store> list(Pageable pageable, StoreSortType sortType, SortDirection sortDirection, boolean includeDeleted) {
        return storeRepository.list(pageable, sortType, sortDirection, includeDeleted);
    }

    /**
     * userId로 가게 목록 조회
     *
     * @param userId
     * @return List<Store>
     */
    public List<Store> list(long userId) {
        return storeRepository.listByUserId(userId);
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
     */
    public Store create(User owner, String name, String city, String address, String category, String description,
                        String priceCategory, String eventDescription, String contactNumber, String direction,
                        String information, boolean isParkingAvailable, boolean isNewOpen, boolean isTakeOut) {
        Store store = Store.builder()
                .owner(owner)
                .name(name)
                .city(city)
                .address(address)
                .category(category)
                .description(description)
                .priceCategory(priceCategory)
                .eventDescription(eventDescription)
                .contactNumber(contactNumber)
                .direction(direction)
                .information(information)
                .isParkingAvailable(isParkingAvailable)
                .isNewOpen(isNewOpen)
                .isTakeOut(isTakeOut)
                .build();
        return storeRepository.save(store);
    }

    public void update(long id, String name, String city, String address, String category, String description,
                       String contactNumber, String priceCategory, String eventDescription, String direction,
                       String information, boolean isParkingAvailable, boolean isNewOpen, boolean isTakeOut) {
        Store store = findById(id);
        store.update(name, city, address, category, description, contactNumber, priceCategory, eventDescription,
                direction, information, isParkingAvailable, isNewOpen, isTakeOut);
    }

    public void delete(long id, Long deletedBy) {
        Store store = findById(id);
        store.delete(deletedBy);
    }

    public Page<Store> list(Pageable pageable) {
        return storeRepository.findAll(pageable);
    }

    public void increaseVisitCount(Store store) {
        store.increaseVisitCount();
    }

    public void increaseReviewCount(Store store) {
        store.increaseReviewCount();
    }

    public void decreaseReviewCount(Store store) {
        store.decreaseReviewCount();
    }

    public void updateAverageRating(Store store, double averageRating) {
        store.updateAverageRating(averageRating);
    }
}
