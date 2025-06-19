package im.fooding.app.service.ceo.store;

import im.fooding.app.dto.request.ceo.store.information.CeoCreateStoreOperatingHourRequest;
import im.fooding.app.dto.request.ceo.store.information.CeoUpdateStoreOperatingHourRequest;
import im.fooding.app.dto.response.ceo.store.CeoStoreOperatingHourResponse;
import im.fooding.core.global.util.Util;
import im.fooding.core.model.store.Store;
import im.fooding.core.model.store.information.StoreOperatingHour;
import im.fooding.core.service.store.StoreDailyOperatingTimeService;
import im.fooding.core.service.store.StoreMemberService;
import im.fooding.core.service.store.StoreOperatingHourService;
import im.fooding.core.service.store.StoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CeoStoreOperatingHourService {
    private final StoreService storeService;
    private final StoreMemberService storeMemberService;
    private final StoreOperatingHourService storeOperatingHourService;
    private final StoreDailyOperatingTimeService storeDailyOperatingTimeService;

    @Transactional
    public Long create(long storeId, CeoCreateStoreOperatingHourRequest request, long userId) {
        Store store = storeService.findById(storeId);
        checkMember(storeId, userId);
        StoreOperatingHour storeOperatingHour = storeOperatingHourService.create(store, request.getHasHoliday(), request.getRegularHolidayType(), request.getRegularHoliday(), Util.generateListToString(request.getClosedNationalHolidays()), Util.generateListToString(request.getCustomHolidays()), request.getOperatingNotes());
        request.getDailyOperatingTimes().forEach(it -> {
            storeDailyOperatingTimeService.create(storeOperatingHour, it.getDayOfWeek(), it.getOpenTime(), it.getCloseTime(), it.getBreakStartTime(), it.getBreakEndTime());
        });
        return storeOperatingHour.getId();
    }

    @Transactional
    public void update(long storeId, long id, CeoUpdateStoreOperatingHourRequest request, long userId) {
        checkMember(storeId, userId);
        storeOperatingHourService.update(id, request.getHasHoliday(), request.getRegularHolidayType(), request.getRegularHoliday(), Util.generateListToString(request.getClosedNationalHolidays()), Util.generateListToString(request.getCustomHolidays()), request.getOperatingNotes());
        request.getDailyOperatingTimes().forEach(it -> {
            storeDailyOperatingTimeService.update(it.getId(), it.getDayOfWeek(), it.getOpenTime(), it.getCloseTime(), it.getBreakStartTime(), it.getBreakEndTime());
        });
    }

    @Transactional(readOnly = true)
    public CeoStoreOperatingHourResponse retrieve(long storeId, long userId) {
        checkMember(storeId, userId);
        StoreOperatingHour storeOperatingHour = storeOperatingHourService.findByStoreId(storeId);
        return null != storeOperatingHour ? CeoStoreOperatingHourResponse.of(storeOperatingHour) : null;
    }

    @Transactional
    public void delete(long storeId, long id, long userId) {
        checkMember(storeId, userId);
        storeOperatingHourService.delete(id ,userId);
    }

    private void checkMember(long storeId, long userId) {
        storeMemberService.checkMember(storeId, userId);
    }
}
