package im.fooding.core.service.store;

import im.fooding.core.dto.request.store.StoreFilter;
import im.fooding.core.event.store.StoreCreatedEvent;
import im.fooding.core.event.store.StoreDeletedEvent;
import im.fooding.core.event.store.StoreUpdatedEvent;
import im.fooding.core.global.exception.ApiException;
import im.fooding.core.global.exception.ErrorCode;
import im.fooding.core.global.infra.slack.SlackClient;
import im.fooding.core.global.kafka.KafkaEventHandler;
import im.fooding.core.model.region.Region;
import im.fooding.core.model.store.Store;
import im.fooding.core.model.store.StoreSortType;
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
     */
    public Page<Store> list(Pageable pageable, StoreSortType sortType, SortDirection sortDirection, boolean includeDeleted) {
        return storeRepository.list(pageable, sortType, sortDirection, includeDeleted);
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
     * @return Store
     */
    public Store retrieve(long storeId) {
        return storeRepository.retrieve(storeId).orElseThrow(() -> new ApiException(ErrorCode.STORE_NOT_FOUND));
    }

    /**
     * 가게 아이디로 조회
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
            String city,
            String address,
            String category,
            String description,
            String priceCategory,
            String eventDescription,
            String contactNumber,
            String direction,
            String information,
            boolean isParkingAvailable,
            boolean isNewOpen,
            boolean isTakeOut,
            Double latitude,
            Double longitude
    ) {
        Store store = Store.builder()
                .owner(owner)
                .name(name)
                .region(region)
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
                .latitude(latitude)
                .longitude(longitude)
                .build();
        return storeRepository.save(store);
    }

    public Store update(
            long id,
            String name,
            Region region,
            String city,
            String address,
            String category,
            String description,
            String contactNumber,
            String priceCategory,
            String eventDescription,
            String direction,
            String information,
            boolean isParkingAvailable,
            boolean isNewOpen,
            boolean isTakeOut,
            Double latitude,
            Double longitude,
            List<SubwayStation> stations
    ) {
        Store store = findById(id);
        store.update(name, region, city, address, category, description, contactNumber, priceCategory, eventDescription,
                direction, information, isParkingAvailable, isNewOpen, isTakeOut, latitude, longitude);
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

    @KafkaEventHandler(StoreCreatedEvent.class)
    public void handleStoreCreatedEvent(StoreCreatedEvent storeCreatedEvent) {
        try {
            storeDocumentService.save(storeCreatedEvent.getId(), storeCreatedEvent.getName(), storeCreatedEvent.getCategory(),
                    storeCreatedEvent.getAddress(), storeCreatedEvent.getReviewCount(), storeCreatedEvent.getAverageRating(),
                    storeCreatedEvent.getVisitCount(), storeCreatedEvent.getCreatedAt()
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
                    storeUpdatedEvent.getVisitCount(), storeUpdatedEvent.getCreatedAt()
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
}
