package im.fooding.app.service.admin.store;

import im.fooding.app.dto.request.admin.store.AdminCreateStoreRequest;
import im.fooding.app.dto.request.admin.store.AdminSearchStoreRequest;
import im.fooding.app.dto.request.admin.store.AdminUpdateStoreRequest;
import im.fooding.app.dto.response.admin.store.AdminStoreResponse;
import im.fooding.core.common.PageInfo;
import im.fooding.core.common.PageResponse;
import im.fooding.core.event.store.StoreCreatedEvent;
import im.fooding.core.global.kafka.EventProducerService;
import im.fooding.core.model.region.Region;
import im.fooding.core.model.store.Store;
import im.fooding.core.model.store.StorePosition;
import im.fooding.core.model.store.document.GeoPoint;
import im.fooding.core.model.store.information.StoreOperatingHour;
import im.fooding.core.model.store.subway.SubwayStation;
import im.fooding.core.model.user.Role;
import im.fooding.core.model.user.User;
import im.fooding.core.service.region.RegionService;
import im.fooding.core.service.store.*;
import im.fooding.core.service.store.document.StoreDocumentService;
import im.fooding.core.service.store.subway.SubwayStationService;
import im.fooding.core.service.user.UserAuthorityService;
import im.fooding.core.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminStoreService {
    private final StoreService storeService;
    private final StoreMemberService storeMemberService;
    private final UserService userService;
    private final UserAuthorityService userAuthorityService;
    private final RegionService regionService;
    private final SubwayStationService subwayStationService;
    private final EventProducerService eventProducerService;
    private final StoreInformationService storeInformationService;
    private final StoreOperatingHourService storeOperatingHourService;
    private final StoreDailyOperatingTimeService storeDailyOperatingTimeService;
    private final StoreDocumentService storeDocumentService;

    @Transactional(readOnly = true)
    public PageResponse<AdminStoreResponse> list(AdminSearchStoreRequest request) {
        Page<Store> result = storeService.list(
                request.getPageable(),
                request.getSortType(),
                request.getSortDirection(),
                request.getLatitude(),
                request.getLongitude(),
                request.getRegionIds(),
                request.getCategory(),
                Boolean.TRUE.equals(request.getIncludeDeleted()),
                request.getStatuses(),
                request.getSearchString(),
                null
        );
        PageInfo pageInfo = PageInfo.of(result);
        return PageResponse.of(
                result.getContent().stream().map(AdminStoreResponse::new).collect(Collectors.toList()),
                pageInfo);
    }

    @Cacheable(key = "#id", value = "AdminStore", cacheManager = "contentCacheManager")
    @Transactional(readOnly = true)
    public AdminStoreResponse retrieve(Long id) {
        Store store = storeService.findById(id);
        return new AdminStoreResponse(store);
    }

    @Transactional
    public Long create(AdminCreateStoreRequest request) {
        User user = userService.findById(request.getOwnerId());
        userAuthorityService.checkPermission(user.getAuthorities(), Role.CEO);

        Store store = storeService.create(user, request.getName(), getRegion(request.getRegionId()), request.getAddress(), request.getAddressDetail(), request.getCategory(),
                request.getDescription(), request.getContactNumber(), request.getDirection(), false, false,
                request.getLatitude(), request.getLongitude());

        storeMemberService.create(store, user, StorePosition.OWNER);

        initializeInformation(store);

        eventProducerService.publishEvent("StoreCreatedEvent", new StoreCreatedEvent(store));
        return store.getId();
    }

    @Transactional
    @CacheEvict(key = "#id", value = "AdminStore", cacheManager = "contentCacheManager")
    public void update(Long id, AdminUpdateStoreRequest request) {
        List<SubwayStation> nearStations = subwayStationService.getNearStations(request.getLatitude(), request.getLongitude());
        Store store = storeService.update(id, request.getName(), getRegion(request.getRegionId()), request.getAddress(), request.getAddressDetail(), request.getCategory(),
                request.getDescription(), request.getContactNumber(), request.getDirection(), false, false, request.getLatitude(), request.getLongitude(), nearStations);

        eventProducerService.publishEvent("StoreUpdatedEvent", new StoreCreatedEvent(store));
    }

    @Transactional
    @CacheEvict(key = "#id", value = "AdminStore", cacheManager = "contentCacheManager")
    public void delete(Long id, Long deletedBy) {
        storeService.delete(id, deletedBy);
        eventProducerService.publishEvent("StoreDeletedEvent", new StoreCreatedEvent(id));
    }

    @Transactional
    @CacheEvict(key = "#id", value = "AdminStore", cacheManager = "contentCacheManager")
    public void approve(Long id) {
        Store store = storeService.findById(id);
        store.approve();
    }

    @Transactional
    @CacheEvict(key = "#id", value = "AdminStore", cacheManager = "contentCacheManager")
    public void reject(Long id) {
        Store store = storeService.findById(id);
        store.reject();
    }

    @Transactional
    @CacheEvict(key = "#id", value = "AdminStore", cacheManager = "contentCacheManager")
    public void suspend(Long id) {
        Store store = storeService.findById(id);
        store.suspend();
    }

    @Transactional
    @CacheEvict(key = "#id", value = "AdminStore", cacheManager = "contentCacheManager")
    public void close(Long id) {
        Store store = storeService.findById(id);
        store.close();
    }

    @Transactional
    @CacheEvict(key = "#id", value = "AdminStore", cacheManager = "contentCacheManager")
    public void setPending(Long id) {
        Store store = storeService.findById(id);
        store.setPending();
    }

    private Region getRegion(String regionId) {
        return StringUtils.hasText(regionId) ? regionService.get(regionId) : null;
    }

    /**
     * 스토어 신규 생성시 부가정보, 영업시간/휴무일 기본값으로 초기 생성한다.
     *
     * @param store
     */
    private void initializeInformation(Store store) {
        storeInformationService.initialize(store);
        StoreOperatingHour storeOperatingHour = storeOperatingHourService.initialize(store);
        storeDailyOperatingTimeService.initialize(storeOperatingHour);
    }

    public void syncToElasticsearch() {
        List<Store> stores = storeService.findAll();
        stores.forEach(store -> {
            try {
                GeoPoint geoPoint = null;
                if (store.getLatitude() != null && store.getLatitude() != null) {
                    geoPoint = new GeoPoint(store.getLatitude(), store.getLatitude());
                }
                storeDocumentService.save(store.getId(), store.getName(), store.getCategory(),
                        store.getAddress(), store.getReviewCount(), store.getAverageRating(),
                        store.getVisitCount(), store.getRegionId(), store.getStatus(),
                        store.getAveragePrice(), geoPoint, store.getCreatedAt()
                );
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
