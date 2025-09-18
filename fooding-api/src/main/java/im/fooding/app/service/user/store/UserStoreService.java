package im.fooding.app.service.user.store;

import im.fooding.app.dto.request.user.store.UserImmediateEntryStoreRequest;
import im.fooding.app.dto.request.user.store.UserSearchStoreRequest;
import im.fooding.app.dto.response.user.store.UserStoreListResponse;
import im.fooding.app.dto.response.user.store.UserStoreResponse;
import im.fooding.core.common.PageInfo;
import im.fooding.core.common.PageResponse;
import im.fooding.core.global.UserInfo;
import im.fooding.core.global.exception.ApiException;
import im.fooding.core.global.exception.ErrorCode;
import im.fooding.core.global.util.Util;
import im.fooding.core.model.bookmark.Bookmark;
import im.fooding.core.model.store.Store;
import im.fooding.core.model.store.StoreStatus;
import im.fooding.core.model.store.document.StoreDocument;
import im.fooding.core.model.store.information.StoreDailyOperatingTime;
import im.fooding.core.model.store.information.StoreOperatingHour;
import im.fooding.core.model.waiting.WaitingSetting;
import im.fooding.core.model.waiting.WaitingStatus;
import im.fooding.core.service.bookmark.BookmarkService;
import im.fooding.core.service.store.StoreOperatingHourService;
import im.fooding.core.service.store.StoreService;
import im.fooding.core.service.store.document.StoreDocumentService;
import im.fooding.core.service.waiting.WaitingSettingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserStoreService {
    private final StoreService storeService;
    private final StoreOperatingHourService storeOperatingHourService;
    private final WaitingSettingService waitingSettingService;
    private final BookmarkService bookmarkService;
    private final StoreDocumentService storeDocumentService;

    @Transactional(readOnly = true)
    public PageResponse<UserStoreListResponse> list(UserSearchStoreRequest request, UserInfo userInfo) {
        Set<StoreStatus> userVisibleStatuses = EnumSet.of(
                StoreStatus.APPROVED
        );

        Page<Store> stores = storeService.list(request.getPageable(), request.getSortType(), request.getSortDirection(), request.getRegionIds(), request.getCategory(), false, userVisibleStatuses, null);
        List<UserStoreListResponse> list = stores.getContent().stream().map(store -> UserStoreListResponse.of(store, null)).toList();

        if (list != null && !list.isEmpty()) {
            // 영업상태 세팅
            setOperatingStatus(list);

            // 북마크 여부 세팅
            if (userInfo != null) {
                setBookmarked(list, userInfo.getId());
            }
        }
        return PageResponse.of(list, PageInfo.of(stores));
    }

    @Transactional(readOnly = true)
    public PageResponse<UserStoreListResponse> list_v2(UserSearchStoreRequest request, UserInfo userInfo) {
        try {
            Page<StoreDocument> stores = storeDocumentService.fullTextSearch(request.getSearchString(), request.getSortType(), request.getSortDirection(), request.getRegionIds(), request.getCategory(), request.getPageable());

            List<Long> ids = stores.getContent().stream().map(StoreDocument::getId).toList();

            List<UserStoreListResponse> list = storeService.list(ids).stream()
                    .map(store -> UserStoreListResponse.of(store, null))
                    .toList();

            if (list != null && !list.isEmpty()) {
                // 영업상태 세팅
                setOperatingStatus(list);

                // 북마크 여부 세팅
                if (userInfo != null) {
                    setBookmarked(list, userInfo.getId());
                }
            }

            return PageResponse.of(list, PageInfo.of(stores));

        } catch (IOException e) {
            e.printStackTrace();
            throw new ApiException(ErrorCode.ELASTICSEARCH_SEARCH_FAILED);
        }
    }

    @Transactional
    public UserStoreResponse retrieve(Long id, UserInfo userInfo) {
        Set<StoreStatus> userVisibleStatuses = EnumSet.of(
                StoreStatus.APPROVED,
                StoreStatus.REJECTED,
                StoreStatus.SUSPENDED,
                StoreStatus.CLOSED
        );
        Store store = storeService.retrieve(id, userVisibleStatuses);
        storeService.increaseVisitCount(store);
        UserStoreResponse userStoreResponse = UserStoreResponse.of(store, null);

        // 영업상태 세팅
        setOperatingStatus(userStoreResponse);

        // 북마크 여부 세팅
        if (userInfo != null) {
            setBookmarked(userStoreResponse, userInfo.getId());
        }

        return userStoreResponse;
    }

    @Transactional(readOnly = true)
    public PageResponse<UserStoreListResponse> retrieveImmediateEntry(UserImmediateEntryStoreRequest request) {
        Page<WaitingSetting> waitingSettings = waitingSettingService.list(null, WaitingStatus.IMMEDIATE_ENTRY, request.getPageable());

        List<UserStoreListResponse> content = waitingSettings.getContent().stream()
                .map(waitingSetting -> waitingSetting.getStoreService().getStore())
                .map(this::mapStoreToResponse)
                .toList();

        return PageResponse.of(content, PageInfo.of(waitingSettings));
    }

    private UserStoreListResponse mapStoreToResponse(Store store) {
        //TODO: 이미지 추가
        return UserStoreListResponse.of(
                store,
                getEstimatedWaitingTime(store)
        );
    }

    private Integer getEstimatedWaitingTime(Store store) {
        //TODO: n + 1 이슈있음 예상 웨이팅 시간 어떻게할지
        return waitingSettingService.findActiveSetting(store)
                .map(WaitingSetting::getEstimatedWaitingTimeMinutes)
                .orElse(null);
    }

    private void setOperatingStatus(UserStoreResponse userStoreResponse) {
        StoreOperatingHour operatingHour = storeOperatingHourService
                .findByIdsInOperatingTime(List.of(userStoreResponse.getId()), Util.getDayOfWeek())
                .stream()
                .findFirst()
                .orElse(null);
        if (operatingHour != null && !operatingHour.getDailyOperatingTimes().isEmpty()) {
            StoreDailyOperatingTime time = operatingHour.getDailyOperatingTimes().get(0);
            userStoreResponse.setFinished(!time.isOperatingNow());
        }
    }

    private void setOperatingStatus(List<UserStoreListResponse> list) {
        List<Long> storeIds = list.stream()
                .map(UserStoreListResponse::getId)
                .toList();

        Map<Long, StoreOperatingHour> operatingHourMap = storeOperatingHourService
                .findByIdsInOperatingTime(storeIds, Util.getDayOfWeek())
                .stream()
                .collect(Collectors.toMap(
                        it -> it.getStore().getId(),
                        Function.identity()
                ));

        for (UserStoreListResponse store : list) {
            StoreOperatingHour operatingHour = operatingHourMap.get(store.getId());
            if (operatingHour != null && !operatingHour.getDailyOperatingTimes().isEmpty()) {
                StoreDailyOperatingTime time = operatingHour.getDailyOperatingTimes().get(0);
                store.setFinished(!time.isOperatingNow());
            }
        }
    }

    private void setBookmarked(List<UserStoreListResponse> list, Long userId) {
        List<Bookmark> userBookmarks = bookmarkService.findAllByUserId(userId);
        Set<Long> bookmarkedStoreIds = userBookmarks.stream().map(bookmark -> bookmark.getStore().getId()).collect(Collectors.toSet());
        list.forEach(response -> response.setBookmarked(bookmarkedStoreIds.contains(response.getId())));
    }

    private void setBookmarked(UserStoreResponse userStoreResponse, Long userId) {
        List<Bookmark> userBookmarks = bookmarkService.findAllByUserId(userId);
        Set<Long> bookmarkedStoreIds = userBookmarks.stream().map(bookmark -> bookmark.getStore().getId()).collect(Collectors.toSet());
        userStoreResponse.setBookmarked(bookmarkedStoreIds.contains(userId));
    }
}
