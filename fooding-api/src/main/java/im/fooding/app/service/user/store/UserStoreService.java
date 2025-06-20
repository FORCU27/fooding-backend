package im.fooding.app.service.user.store;

import im.fooding.app.dto.request.user.store.UserSearchStoreRequest;
import im.fooding.app.dto.response.user.store.UserStoreListResponse;
import im.fooding.app.dto.response.user.store.UserStoreResponse;
import im.fooding.core.common.PageInfo;
import im.fooding.core.common.PageResponse;
import im.fooding.core.model.store.Store;
import im.fooding.core.model.store.information.StoreDailyOperatingTime;
import im.fooding.core.model.store.information.StoreOperatingHour;
import im.fooding.core.model.waiting.WaitingSetting;
import im.fooding.core.service.store.StoreOperatingHourService;
import im.fooding.core.service.store.StoreService;
import im.fooding.core.service.waiting.WaitingSettingService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserStoreService {
    private final StoreService storeService;
    private final StoreOperatingHourService storeOperatingHourService;
    private final WaitingSettingService waitingSettingService;

    @Transactional(readOnly = true)
    public PageResponse<UserStoreListResponse> list(UserSearchStoreRequest request) {
        Page<Store> stores = storeService.list(request.getPageable(), request.getSortType(), request.getSortDirection(), false);
        List<UserStoreListResponse> list = stores.getContent().stream().map(store -> UserStoreListResponse.of(store, null)).toList();
        updateOperatingStatus(list);
        return PageResponse.of(
                list,
                PageInfo.of(stores)
        );
    }

    @Transactional
    public UserStoreResponse retrieve(Long id) {
        Store store = storeService.retrieve(id);
        storeService.increaseVisitCount(store);
        UserStoreResponse userStoreResponse = UserStoreResponse.of(store, null);
        updateOperatingStatus(userStoreResponse);
        return userStoreResponse;
    }

    private Integer getEstimatedWaitingTime(Store store) {
        //TODO: n + 1 이슈있음 예상 웨이팅 시간 어떻게할지
        return waitingSettingService.findActiveSetting(store)
                .map(WaitingSetting::getEstimatedWaitingTimeMinutes)
                .orElse(null);
    }

    private void updateOperatingStatus(UserStoreResponse userStoreResponse) {
        DayOfWeek today = LocalDate.now().getDayOfWeek();

        StoreOperatingHour operatingHour = storeOperatingHourService
                .findByIdsInOperatingTime(List.of(userStoreResponse.getId()), today)
                .stream()
                .findFirst()
                .orElse(null);

        boolean isOperating = isOperatingNow(operatingHour);
        userStoreResponse.setFinished(!isOperating);
    }

    private void updateOperatingStatus(List<UserStoreListResponse> list) {
        DayOfWeek today = LocalDate.now().getDayOfWeek();

        List<Long> storeIds = list.stream()
                .map(UserStoreListResponse::getId)
                .toList();

        Map<Long, StoreOperatingHour> operatingHourMap = storeOperatingHourService
                .findByIdsInOperatingTime(storeIds, today)
                .stream()
                .collect(Collectors.toMap(
                        it -> it.getStore().getId(),
                        Function.identity()
                ));

        for (UserStoreListResponse store : list) {
            StoreOperatingHour operatingHour = operatingHourMap.get(store.getId());
            boolean isOperating = isOperatingNow(operatingHour);
            store.setFinished(!isOperating);
        }
    }

    private boolean isOperatingNow(StoreOperatingHour operatingHour) {
        if (operatingHour == null || operatingHour.getDailyOperatingTimes().isEmpty()) {
            return false;
        }

        // 금일 영업시간 정보 조회
        StoreDailyOperatingTime time = operatingHour.getDailyOperatingTimes().get(0);
        LocalTime now = LocalTime.now();

        // 영업시간 여부
        boolean isOpen = !now.isBefore(time.getOpenTime()) && now.isBefore(time.getCloseTime());

        // 브레이크타임 여부
        boolean inBreak = time.getBreakStartTime() != null && time.getBreakEndTime() != null &&
                !now.isBefore(time.getBreakStartTime()) && now.isBefore(time.getBreakEndTime());

        return isOpen && !inBreak;
    }
}
