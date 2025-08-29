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
import im.fooding.core.model.store.subway.SubwayStation;
import im.fooding.core.model.user.Role;
import im.fooding.core.model.user.User;
import im.fooding.core.service.region.RegionService;
import im.fooding.core.service.store.StoreMemberService;
import im.fooding.core.service.store.StoreService;
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

    @Transactional(readOnly = true)
    public PageResponse<AdminStoreResponse> list(AdminSearchStoreRequest request) {
        Page<Store> result = storeService.list(request.getPageable(), request.getSortType(), request.getSortDirection(), false, null);
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
        eventProducerService.publishEvent("StoreCreatedEvent", new StoreCreatedEvent(store.getId(), store.getName(), store.getCategory(), store.getAddress(), store.getReviewCount(), store.getAverageRating(), store.getVisitCount(), store.getCreatedAt()));
        return store.getId();
    }

    @Transactional
    @CacheEvict(key = "#id", value = "AdminStore", cacheManager = "contentCacheManager")
    public void update(Long id, AdminUpdateStoreRequest request) {
        List<SubwayStation> nearStations = subwayStationService.getNearStations(request.getLatitude(), request.getLongitude());
        Store store = storeService.update(id, request.getName(), getRegion(request.getRegionId()), request.getAddress(), request.getAddressDetail(), request.getCategory(),
                request.getDescription(), request.getContactNumber(), request.getDirection(), false, false, request.getLatitude(), request.getLongitude(), nearStations);

        eventProducerService.publishEvent("StoreUpdatedEvent", new StoreCreatedEvent(store.getId(), store.getName(), store.getCategory(), store.getAddress(), store.getReviewCount(), store.getAverageRating(), store.getVisitCount(), store.getCreatedAt()));
    }

    @Transactional
    @CacheEvict(key = "#id", value = "AdminStore", cacheManager = "contentCacheManager")
    public void delete(Long id, Long deletedBy) {
        storeService.delete(id, deletedBy);
        eventProducerService.publishEvent("StoreDeletedEvent", new StoreCreatedEvent(id));
    }

    @Transactional
    @CacheEvict( key="#id", value="AdminStore", cacheManager="contentCacheManager" )
    public void approve(Long id) {
        Store store = storeService.findById(id);
        store.approve();
        
        try {
            storeDocumentService.save(StoreDocument.from(store));
        } catch (IOException e) {
            throw new ApiException(ErrorCode.ELASTICSEARCH_SAVE_FAILED);
        }
    }

    @Transactional
    @CacheEvict( key="#id", value="AdminStore", cacheManager="contentCacheManager" )
    public void reject(Long id) {
        Store store = storeService.findById(id);
        store.reject();
        
        try {
            storeDocumentService.save(StoreDocument.from(store));
        } catch (IOException e) {
            throw new ApiException(ErrorCode.ELASTICSEARCH_SAVE_FAILED);
        }
    }

    @Transactional
    @CacheEvict( key="#id", value="AdminStore", cacheManager="contentCacheManager" )
    public void suspend(Long id) {
        Store store = storeService.findById(id);
        store.suspend();
        
        try {
            storeDocumentService.save(StoreDocument.from(store));
        } catch (IOException e) {
            throw new ApiException(ErrorCode.ELASTICSEARCH_SAVE_FAILED);
        }
    }

    @Transactional
    @CacheEvict( key="#id", value="AdminStore", cacheManager="contentCacheManager" )
    public void close(Long id) {
        Store store = storeService.findById(id);
        store.close();
        
        try {
            storeDocumentService.save(StoreDocument.from(store));
        } catch (IOException e) {
            throw new ApiException(ErrorCode.ELASTICSEARCH_SAVE_FAILED);
        }
    }

    @Transactional
    @CacheEvict( key="#id", value="AdminStore", cacheManager="contentCacheManager" )
    public void setPending(Long id) {
        Store store = storeService.findById(id);
        store.setPending();
        
        try {
            storeDocumentService.save(StoreDocument.from(store));
        } catch (IOException e) {
            throw new ApiException(ErrorCode.ELASTICSEARCH_SAVE_FAILED);
        }
    private Region getRegion(String regionId) {
        return StringUtils.hasText(regionId) ? regionService.get(regionId) : null;
    }
}
