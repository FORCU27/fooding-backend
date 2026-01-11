package im.fooding.core.service.store;

import im.fooding.core.dto.request.store.StoreFilter;
import im.fooding.core.event.store.StoreAveragePriceUpdatedEvent;
import im.fooding.core.event.store.StoreCreatedEvent;
import im.fooding.core.event.store.StoreDeletedEvent;
import im.fooding.core.event.store.StoreUpdatedEvent;
import im.fooding.core.global.exception.ApiException;
import im.fooding.core.global.exception.ErrorCode;
import im.fooding.core.global.infra.slack.SlackClient;
import im.fooding.core.global.kafka.KafkaEventHandler;
import im.fooding.core.model.region.Region;
import im.fooding.core.model.store.Store;
import im.fooding.core.model.store.StoreCategory;
import im.fooding.core.model.store.StoreSortType;
import im.fooding.core.model.store.StoreStatus;
import im.fooding.core.model.store.subway.SubwayStation;
import im.fooding.core.model.user.User;
import im.fooding.core.repository.store.StoreRepository;
import im.fooding.core.service.store.document.StoreDocumentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.query.SortDirection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class StoreService {
    private final StoreRepository storeRepository;
    private final StoreDocumentService storeDocumentService;
    private final SlackClient slackClient;

    /**
     * 가게 목록 조회
     * TODO : 대기시간 순 정렬 추가
     *
     * @param pageable
     * @param sortType
     * @param sortDirection
     * @param regionIds
     * @param category
     * @param includeDeleted
     * @param statuses       조회할 상태들, null이면 모든 상태 조회
     * @param searchString
     * @param excludeStoreId 제외할 스토어 id
     */
    public Page<Store> list(Pageable pageable, StoreSortType sortType, SortDirection sortDirection, Double latitude, Double longitude, List<String> regionIds, StoreCategory category, boolean includeDeleted, Set<StoreStatus> statuses, String searchString, Long excludeStoreId) {
        return storeRepository.list(pageable, sortType, sortDirection, latitude, longitude, regionIds, category, includeDeleted, statuses, searchString, excludeStoreId);
    }

    public List<Store> list(List<Long> ids) {
        return storeRepository.list(ids);
    }

    /**
     * userId로 가게 목록 조회
     *
     * @param userId
     * @return List<Store>
     */
    public List<Store> list(long userId, StoreFilter filter) {
        return storeRepository.listByUserId(userId, filter);
    }

    /**
     * 가게 아이디로 조회(image 포함)
     *
     * @param storeId
     * @param statuses 조회할 상태들, null이면 모든 상태 조회
     * @return Store
     */
    public Store retrieve(long storeId, Set<StoreStatus> statuses) {
        return storeRepository.retrieve(storeId, statuses).orElseThrow(() -> new ApiException(ErrorCode.STORE_NOT_FOUND));
    }

    /**
     * 가게 아이디로 조회 (관리자용 - 모든 상태 포함)
     *
     * @param storeId
     * @return Store
     */
    public Store findById(long storeId) {
        return storeRepository.findById(storeId)
                .filter(it -> !it.isDeleted())
                .orElseThrow(() -> new ApiException(ErrorCode.STORE_NOT_FOUND));
    }

    /**
     * 가게 생성
     */
    public Store create(
            User owner,
            String name,
            Region region,
            String address,
            String addressDetail,
            StoreCategory category,
            String description,
            String contactNumber,
            String direction,
            boolean isNewOpen,
            boolean isTakeOut,
            Double latitude,
            Double longitude
    ) {
        Store store = Store.builder()
                .owner(owner)
                .name(name)
                .region(region)
                .address(address)
                .addressDetail(addressDetail)
                .category(category)
                .description(description)
                .contactNumber(contactNumber)
                .direction(direction)
                .isNewOpen(isNewOpen)
                .isTakeOut(isTakeOut)
                .latitude(latitude)
                .longitude(longitude)
                .build();
        return storeRepository.save(store);
    }

    public Store update(
            long id,
            String name,
            Region region,
            String address,
            String addressDetail,
            StoreCategory category,
            String description,
            String contactNumber,
            String direction,
            boolean isNewOpen,
            boolean isTakeOut,
            Double latitude,
            Double longitude,
            List<SubwayStation> stations
    ) {
        Store store = findById(id);
        store.update(name, region, address, addressDetail, category, description, contactNumber, direction, isNewOpen, isTakeOut, latitude, longitude, store.getStatus());
        store.setNearSubwayStations(stations);
        return store;
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

    public void increaseBookmarkCount(Store store) {
        store.increaseBookmarkCount();
    }

    public void decreaseBookmarkCount(Store store) {
        store.decreaseBookmarkCount();
    }

    public List<Store> findAll() {
        return storeRepository.findAll().stream()
                .filter(it -> !it.isDeleted())
                .toList();
    }

    public Store updateName(Long id, String name) {
        Store store = findById(id);
        store.updateName(name);
        return store;
    }

    @KafkaEventHandler(StoreCreatedEvent.class)
    public void handleStoreCreatedEvent(StoreCreatedEvent storeCreatedEvent) {
        try {
            storeDocumentService.save(storeCreatedEvent.getId(), storeCreatedEvent.getName(), storeCreatedEvent.getCategory(),
                    storeCreatedEvent.getAddress(), storeCreatedEvent.getReviewCount(), storeCreatedEvent.getAverageRating(),
                    storeCreatedEvent.getVisitCount(), storeCreatedEvent.getRegionId(), storeCreatedEvent.getStatus(),
                    storeCreatedEvent.getAveragePrice(), storeCreatedEvent.getLocation(), storeCreatedEvent.getCreatedAt()
            );
            slackClient.sendNotificationMessage("%s 가게 생성".formatted(storeCreatedEvent.getName()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @KafkaEventHandler(StoreUpdatedEvent.class)
    public void handleStoreUpdatedEvent(StoreUpdatedEvent storeUpdatedEvent) {
        try {
            storeDocumentService.save(storeUpdatedEvent.getId(), storeUpdatedEvent.getName(), storeUpdatedEvent.getCategory(),
                    storeUpdatedEvent.getAddress(), storeUpdatedEvent.getReviewCount(), storeUpdatedEvent.getAverageRating(),
                    storeUpdatedEvent.getVisitCount(), storeUpdatedEvent.getRegionId(), storeUpdatedEvent.getStatus(),
                    storeUpdatedEvent.getAveragePrice(), storeUpdatedEvent.getLocation(), storeUpdatedEvent.getCreatedAt()
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @KafkaEventHandler(StoreDeletedEvent.class)
    public void handleStoreDeletedEvent(StoreDeletedEvent storeDeletedEvent) {
        try {
            storeDocumentService.delete(storeDeletedEvent.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @KafkaEventHandler(StoreAveragePriceUpdatedEvent.class)
    public void handleStoreAveragePriceUpdatedEvent(StoreAveragePriceUpdatedEvent event) {
        try {
            storeRepository.findById(event.getId()).filter(it -> !it.isDeleted()).ifPresent(it -> {
                it.updateAveragePrice(event.getAveragePrice());
                storeRepository.save(it);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
