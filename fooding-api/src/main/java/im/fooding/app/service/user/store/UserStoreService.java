package im.fooding.app.service.user.store;

import im.fooding.app.dto.request.user.store.UserImmediateEntryStoreRequest;
import im.fooding.app.dto.request.user.store.UserSearchStoreRequest;
import im.fooding.app.dto.response.user.store.UserPopularStoresResponse;
import im.fooding.app.dto.response.user.store.UserStoreListResponse;
import im.fooding.app.dto.response.user.store.UserStoreResponse;
import im.fooding.core.common.BasicSearch;
import im.fooding.core.common.PageInfo;
import im.fooding.core.common.PageResponse;
import im.fooding.core.event.keyword.SearchKeywordSavedEvent;
import im.fooding.core.global.UserInfo;
import im.fooding.core.global.exception.ApiException;
import im.fooding.core.global.exception.ErrorCode;
import im.fooding.core.global.kafka.EventProducerService;
import im.fooding.core.global.util.Util;
import im.fooding.core.model.bookmark.Bookmark;
import im.fooding.core.model.store.Store;
import im.fooding.core.model.store.StoreSortType;
import im.fooding.core.model.store.StoreStatus;
import im.fooding.core.model.store.document.StoreDocument;
import im.fooding.core.model.store.information.StoreDailyBreakTime;
import im.fooding.core.model.store.information.StoreDailyOperatingTime;
import im.fooding.core.model.store.information.StoreOperatingHour;
import im.fooding.core.model.store.popular.PopularStore;
import im.fooding.core.model.waiting.WaitingSetting;
import im.fooding.core.model.waiting.WaitingStatus;
import im.fooding.core.repository.user.UserRepository;
import im.fooding.core.service.bookmark.BookmarkService;
import im.fooding.core.service.store.RecentStoreService;
import im.fooding.core.service.store.StoreOperatingHourService;
import im.fooding.core.service.store.StoreService;
import im.fooding.core.service.store.document.StoreDocumentService;
import im.fooding.core.service.store.popular.PopularStoreService;
import im.fooding.core.service.store.view.StoreViewService;
import im.fooding.core.service.waiting.WaitingSettingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.query.SortDirection;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.time.LocalTime;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserStoreService {
    private final StoreService storeService;
    private final PopularStoreService popularStoreService;
    private final StoreOperatingHourService storeOperatingHourService;
    private final WaitingSettingService waitingSettingService;
    private final BookmarkService bookmarkService;
    private final StoreDocumentService storeDocumentService;
    private final RecentStoreService recentStoreService;
    private final UserRepository userRepository;
    private final EventProducerService eventProducerService;
    private final StoreViewService storeViewService;

    @Transactional(readOnly = true)
    public PageResponse<UserStoreListResponse> list(UserSearchStoreRequest request, UserInfo userInfo) {
        Set<StoreStatus> userVisibleStatuses = EnumSet.of(
                StoreStatus.APPROVED
        );

        Page<Store> stores = storeService.list(request.getPageable(), request.getSortType(), request.getSortDirection(), request.getLatitude(), request.getLongitude(), request.getRegionIds(), request.getCategory(), false, userVisibleStatuses, null, null);
        List<UserStoreListResponse> list = stores.getContent().stream().map(store -> UserStoreListResponse.of(store, null)).toList();

        setOperatingStatusAndBookmarked(userInfo, list);
        return PageResponse.of(list, PageInfo.of(stores));
    }

    @Transactional
    public PageResponse<UserStoreListResponse> list_v2(UserSearchStoreRequest request, UserInfo userInfo) {
        try {
            Page<StoreDocument> stores = storeDocumentService.fullTextSearch(request.getSearchString(), request.getSortType(), request.getSortDirection(), request.getRegionIds(), request.getCategory(), request.getLatitude(), request.getLongitude(), request.getPageable());

            List<Long> ids = stores.getContent().stream().map(StoreDocument::getId).toList();

            List<UserStoreListResponse> list = storeService.list(ids).stream()
                    .map(store -> UserStoreListResponse.of(store, null))
                    .toList();

            setOperatingStatusAndBookmarked(userInfo, list);

            if (StringUtils.hasText(request.getSearchString())) {
                eventProducerService.publishEvent("SearchKeywordSavedEvent", new SearchKeywordSavedEvent(request.getSearchString()));
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
                StoreStatus.APPROVED
        );

        Store store = storeService.retrieve(id, userVisibleStatuses);
        storeService.increaseVisitCount(store);

        long viewingCount = (userInfo != null)
                ? storeViewService.addViewAndGetCount(store.getId(), userInfo.getId())
                : storeViewService.addUnknownViewAndGetCount(store.getId());

        UserStoreResponse userStoreResponse = UserStoreResponse.of(store, null, viewingCount);

        // 영업상태 세팅
        setOperatingStatus(userStoreResponse);

        // 북마크 여부 세팅
        if (userInfo != null) {
            setBookmarked(userStoreResponse, userInfo.getId());
            userRepository.findById(userInfo.getId()).ifPresent(user -> {
                //최근에 본 식당 update
                recentStoreService.update(user, store);
            });
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

    @Transactional(readOnly = true)
    public PageResponse<UserStoreListResponse> retrieveRecentStores(UserInfo userInfo) {
        if (userInfo != null) {
            Set<StoreStatus> userVisibleStatuses = EnumSet.of(
                    StoreStatus.APPROVED
            );
            BasicSearch search = new BasicSearch();
            Page<Store> stores = recentStoreService.findRecentStores(userInfo.getId(), userVisibleStatuses, search.getPageable());
            List<UserStoreListResponse> list = stores.getContent().stream()
                    .map(store -> UserStoreListResponse.of(store, null))
                    .toList();

            if (list != null && !list.isEmpty()) {
                setOperatingStatus(list, UserStoreListResponse::getId, UserStoreListResponse::setFinished);
                setBookmarked(list, userInfo.getId(), UserStoreListResponse::getId, UserStoreListResponse::setBookmarked);
            }

            return PageResponse.of(list, PageInfo.of(stores));
        }

        return PageResponse.empty();
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
                .findByIdsInOperatingTime(List.of(userStoreResponse.getId()))
                .stream()
                .findFirst()
                .orElse(null);

        //영업시간
        StoreDailyOperatingTime todayOperating = operatingHour.getDailyOperatingTimes().stream()
                .filter(it -> it.getDayOfWeek() == Util.getDayOfWeek())
                .findFirst()
                .orElse(null);

        //휴게시간
        StoreDailyBreakTime todayBreak = operatingHour.getDailyBreakTimes().stream()
                .filter(it -> it.getDayOfWeek() == Util.getDayOfWeek())
                .findFirst()
                .orElse(null);

        LocalTime now = LocalTime.now();
        boolean isFinished;

        if (operatingHour != null && todayOperating != null) {
            isFinished = !todayOperating.isOperatingNow(now);
            if (!isFinished) {
                if (todayBreak != null) {
                    isFinished = todayBreak.isBreakNow(now);
                }
            }
            userStoreResponse.setFinished(isFinished);
        }
    }

    private void setBookmarked(UserStoreResponse userStoreResponse, Long userId) {
        List<Bookmark> userBookmarks = bookmarkService.findAllByUserId(userId);
        Set<Long> bookmarkedStoreIds = userBookmarks.stream().map(bookmark -> bookmark.getStore().getId()).collect(Collectors.toSet());
        userStoreResponse.setBookmarked(bookmarkedStoreIds.contains(userStoreResponse.getId()));
    }

    private <T> void setBookmarked(List<T> list, Long userId, Function<T, Long> idExtractor, BiConsumer<T, Boolean> bookmarkedSetter) {
        List<Bookmark> userBookmarks = bookmarkService.findAllByUserId(userId);
        Set<Long> bookmarkedStoreIds = userBookmarks.stream()
                .map(bookmark -> bookmark.getStore().getId())
                .collect(Collectors.toSet());

        list.forEach(response ->
                bookmarkedSetter.accept(response, bookmarkedStoreIds.contains(idExtractor.apply(response)))
        );
    }

    private <T> void setOperatingStatus(List<T> list, Function<T, Long> idExtractor, BiConsumer<T, Boolean> finishedSetter) {
        List<Long> storeIds = list.stream()
                .map(idExtractor)
                .toList();

        Map<Long, StoreOperatingHour> operatingHourMap = storeOperatingHourService
                .findByIdsInOperatingTime(storeIds)
                .stream()
                .collect(Collectors.toMap(
                        it -> it.getStore().getId(),
                        Function.identity()
                ));

        LocalTime now = LocalTime.now();

        for (T store : list) {
            StoreOperatingHour operatingHour = operatingHourMap.get(idExtractor.apply(store));

            //영업시간
            StoreDailyOperatingTime todayOperating = operatingHour.getDailyOperatingTimes().stream()
                    .filter(it -> it.getDayOfWeek() == Util.getDayOfWeek())
                    .findFirst()
                    .orElse(null);

            //휴게시간
            StoreDailyBreakTime todayBreak = operatingHour.getDailyBreakTimes().stream()
                    .filter(it -> it.getDayOfWeek() == Util.getDayOfWeek())
                    .findFirst()
                    .orElse(null);

            boolean finished = true;

            if (operatingHour != null && todayOperating != null) {
                finished = !todayOperating.isOperatingNow(now);
                if (!finished) {
                    if (todayBreak != null) {
                        finished = todayBreak.isBreakNow(now);
                    }
                }
            }
            finishedSetter.accept(store, finished);
        }
    }

    @Transactional(readOnly = true)
    public UserPopularStoresResponse retrievePopular(UserInfo userInfo) {
        List<PopularStore> popularStores = popularStoreService.getPopularStores();
        List<UserStoreListResponse> popularStoreList = popularStores.stream()
                .map(popularStore -> UserStoreListResponse.of(popularStore, null)).toList();

        setOperatingStatusAndBookmarked(userInfo, popularStoreList);
        return new UserPopularStoresResponse(popularStoreList);
    }

    private void setOperatingStatusAndBookmarked(UserInfo userInfo, List<UserStoreListResponse> popularStoreList) {
        if (popularStoreList != null && !popularStoreList.isEmpty()) {
            // 영업상태 세팅
            setOperatingStatus(popularStoreList, UserStoreListResponse::getId, UserStoreListResponse::setFinished);

            // 북마크 여부 세팅
            if (userInfo != null) {
                setBookmarked(popularStoreList, userInfo.getId(), UserStoreListResponse::getId, UserStoreListResponse::setBookmarked);
            }
        }
    }

    @Transactional(readOnly = true)
    @Cacheable(
            value = "AlsoViewedStoreList",
            key = "'alsoViewedStoreList_' + #id",
            cacheManager = "contentCacheManager",
            sync = true // Cache Stampede 방지 -> 단일 스레드로 직렬화 해서 여러 요청이 동시에 들어와도 DB 한번만 조회
    )
    public PageResponse<UserStoreListResponse> retrieveAlsoViewed(Long id, UserInfo userInfo) {
        //일단 스토어가 없으니 인기순으로 표출
        //TODO: 추후 스토어 id로 해당 스토어와 category같은 것 같은 조건문 추가할것

        Set<StoreStatus> userVisibleStatuses = EnumSet.of(
                StoreStatus.APPROVED
        );

        Page<Store> stores = storeService.list(Pageable.ofSize(10), StoreSortType.RECOMMENDED, SortDirection.DESCENDING, null, null, null, null, false, userVisibleStatuses, null, id);
        List<UserStoreListResponse> list = stores.getContent().stream().map(store -> UserStoreListResponse.of(store, null)).toList();

        if (list != null && !list.isEmpty()) {
            // 영업상태 세팅
            setOperatingStatus(list, UserStoreListResponse::getId, UserStoreListResponse::setFinished);

            // 북마크 여부 세팅
            if (userInfo != null) {
                setBookmarked(list, userInfo.getId(), UserStoreListResponse::getId, UserStoreListResponse::setBookmarked);
            }
        }

        return PageResponse.of(list, PageInfo.of(stores));
    }
}
