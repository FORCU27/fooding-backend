package im.fooding.app.service.admin.store;

import im.fooding.app.dto.response.admin.store.AdminStoreBookmarkResponse;
import im.fooding.core.common.BasicSearch;
import im.fooding.core.common.PageInfo;
import im.fooding.core.common.PageResponse;
import im.fooding.core.model.bookmark.Bookmark;
import im.fooding.core.model.store.Store;
import im.fooding.core.service.bookmark.BookmarkService;
import im.fooding.core.service.store.StoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminStoreBookmarkService {
    private final BookmarkService bookmarkService;
    private final StoreService storeService;
    
    @Transactional(readOnly = true)
    public PageResponse<AdminStoreBookmarkResponse> list(Long storeId, BasicSearch search) {
        Page<Bookmark> list = bookmarkService.list(storeId, null, search.getSearchString(), search.getPageable());
        return PageResponse.of(list.stream().map(AdminStoreBookmarkResponse::of).toList(), PageInfo.of(list));
    }

    @Transactional
    public void delete(long storeId, long id, long ceoId) {
        Store store = storeService.findById(storeId);
        storeService.decreaseBookmarkCount(store);

        Bookmark bookmark = bookmarkService.findById(id);
        bookmarkService.delete(bookmark, ceoId);
    }
}
