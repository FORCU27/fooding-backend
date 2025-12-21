package im.fooding.app.service.ceo.store;

import im.fooding.app.dto.request.ceo.store.CeoCreateStoreRequest;
import im.fooding.app.dto.request.ceo.store.CeoSearchStoreRequest;
import im.fooding.app.dto.request.ceo.store.CeoUpdateStoreNameRequest;
import im.fooding.app.dto.request.ceo.store.CeoUpdateStoreRequest;
import im.fooding.app.dto.response.ceo.store.CeoStoreResponse;
import im.fooding.core.event.store.StoreCreatedEvent;
import im.fooding.core.event.store.StoreDeletedEvent;
import im.fooding.core.event.store.StoreUpdatedEvent;
import im.fooding.core.global.kafka.EventProducerService;
import im.fooding.core.model.region.Region;
import im.fooding.core.model.store.Store;
import im.fooding.core.model.store.StoreCategory;
import im.fooding.core.model.store.StorePosition;
import im.fooding.core.model.store.information.StoreOperatingHour;
import im.fooding.core.model.store.subway.SubwayStation;
import im.fooding.core.model.user.User;
import im.fooding.core.service.region.RegionService;
import im.fooding.core.service.store.*;
import im.fooding.core.service.store.subway.SubwayStationService;
import im.fooding.core.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CeoStoreService {
    private final StoreService storeService;
    private final StoreMemberService storeMemberService;
    private final UserService userService;
    private final RegionService regionService;
    private final SubwayStationService subwayStationService;
    private final EventProducerService eventProducerService;
    private final StoreInformationService storeInformationService;
    private final StoreOperatingHourService storeOperatingHourService;
    private final StoreDailyOperatingTimeService storeDailyOperatingTimeService;

    @Transactional(readOnly = true)
    public List<CeoStoreResponse> list(long userId, CeoSearchStoreRequest search) {
        return storeService.list(userId, search.toStoreFilter()).stream()
                .map(CeoStoreResponse::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public CeoStoreResponse retrieve(Long id, long userId) {
        storeMemberService.checkMember(id, userId);
        return new CeoStoreResponse(storeService.findById(id));
    }

    @Transactional
    public Long create(CeoCreateStoreRequest request, long userId) {
        User user = userService.findById(userId);

        Store store = storeService.create(user, request.getName(), null, "", "", StoreCategory.KOREAN,
                "", "", "", false, false, null, null);

        storeMemberService.create(store, user, StorePosition.OWNER);
        initializeInformation(store);

        eventProducerService.publishEvent("StoreCreatedEvent", new StoreCreatedEvent(store));
        return store.getId();
    }

    @Transactional
    public void update(Long id, CeoUpdateStoreRequest request, long userId) {
        storeMemberService.checkMember(id, userId);
        // 주소를 통해 인근 지하철역 조회 ( 1km 반경 내 )
        List<SubwayStation> nearStations = subwayStationService.getNearStations(request.getLatitude(), request.getLongitude());

        Store store = storeService.update(id, request.getName(), getRegion(request.getRegionId()), request.getAddress(), request.getAddressDetail(), request.getCategory(), request.getDescription(),
                request.getContactNumber(), request.getDirection(), false, false, request.getLatitude(), request.getLongitude(), nearStations);

        eventProducerService.publishEvent("StoreUpdatedEvent", new StoreUpdatedEvent(store));
    }

    @Transactional
    public void delete(Long id, long deletedBy) {
        storeMemberService.checkMember(id, deletedBy);
        storeService.delete(id, deletedBy);
        eventProducerService.publishEvent("StoreDeletedEvent", new StoreDeletedEvent(id));
    }

    @Transactional
    public void updateName(Long id, CeoUpdateStoreNameRequest request, long userId) {
        storeMemberService.checkMember(id, userId);
        Store store = storeService.updateName(id, request.getName());
        eventProducerService.publishEvent("StoreUpdatedEvent", new StoreUpdatedEvent(store));
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
}
