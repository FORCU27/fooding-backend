package im.fooding.app.service.user.store;

import im.fooding.app.dto.response.user.bookmark.UserBookmarkResponse;
import im.fooding.core.common.BasicSearch;
import im.fooding.core.common.PageInfo;
import im.fooding.core.common.PageResponse;
import im.fooding.core.global.util.Util;
import im.fooding.core.model.bookmark.Bookmark;
import im.fooding.core.model.store.Store;
import im.fooding.core.model.store.information.StoreDailyOperatingTime;
import im.fooding.core.model.store.information.StoreOperatingHour;
import im.fooding.core.model.user.User;
import im.fooding.core.service.bookmark.BookmarkService;
import im.fooding.core.service.store.StoreOperatingHourService;
import im.fooding.core.service.store.StoreService;
import im.fooding.core.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserBookmarkService {
    private final BookmarkService bookmarkService;
    private final StoreService storeService;
    private final UserService userService;
    private final StoreOperatingHourService storeOperatingHourService;

    @Transactional
    public Long add(long storeId, long userId) {
        Store store = storeService.findById(storeId);
        User user = userService.findById(userId);
        storeService.increaseBookmarkCount(store);
        return bookmarkService.create(store, user).getId();
    }

    @Transactional
    public void delete(long storeId, long userId) {
        Store store = storeService.findById(storeId);
        storeService.decreaseBookmarkCount(store);
        bookmarkService.delete(store.getId(), userId);
    }

    @Transactional(readOnly = true)
    public PageResponse<UserBookmarkResponse> list(long userId, BasicSearch search) {
        Page<Bookmark> list = bookmarkService.list(null, userId, search.getSearchString(), search.getPageable());
        List<UserBookmarkResponse> bookmarks = list.getContent().stream()
                .map(bookmark -> UserBookmarkResponse.of(bookmark, null))
                .toList();
        updateOperatingStatus(bookmarks);

        return PageResponse.of(
                bookmarks,
                PageInfo.of(list)
        );
    }

    private void updateOperatingStatus(List<UserBookmarkResponse> list) {
        List<Long> storeIds = list.stream()
                .map(UserBookmarkResponse::getStoreId)
                .toList();

        Map<Long, StoreOperatingHour> operatingHourMap = storeOperatingHourService
                .findByIdsInOperatingTime(storeIds, Util.getDayOfWeek())
                .stream()
                .collect(Collectors.toMap(
                        it -> it.getStore().getId(),
                        Function.identity()
                ));

        for (UserBookmarkResponse store : list) {
            StoreOperatingHour operatingHour = operatingHourMap.get(store.getId());
            if (operatingHour != null && operatingHour.getDailyOperatingTimes().isEmpty()) {
                StoreDailyOperatingTime time = operatingHour.getDailyOperatingTimes().get(0);
                store.setFinished(!time.isOperatingNow());
            }
        }
    }
}
