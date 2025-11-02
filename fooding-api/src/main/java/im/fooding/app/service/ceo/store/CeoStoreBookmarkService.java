package im.fooding.app.service.ceo.store;

import im.fooding.app.dto.request.ceo.store.bookmark.CeoSearchStoreBookmarkRequest;
import im.fooding.app.dto.response.ceo.store.CeoStoreBookmarkResponse;
import im.fooding.core.common.PageInfo;
import im.fooding.core.common.PageResponse;
import im.fooding.core.model.bookmark.Bookmark;
import im.fooding.core.model.store.Store;
import im.fooding.core.service.bookmark.BookmarkService;
import im.fooding.core.service.store.StoreMemberService;
import im.fooding.core.service.store.StoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CeoStoreBookmarkService {
    private final BookmarkService bookmarkService;
    private final StoreMemberService storeMemberService;
    private final StoreService storeService;

    @Transactional(readOnly = true)
    public PageResponse<CeoStoreBookmarkResponse> list(Long storeId, CeoSearchStoreBookmarkRequest search, long ceoId) {
        storeMemberService.checkMember(storeId, ceoId);
        Page<Bookmark> list = bookmarkService.list(storeId, null, search.getSortType(), search.getSearchString(), search.getPageable());
        return PageResponse.of(list.stream().map(CeoStoreBookmarkResponse::of).toList(), PageInfo.of(list));
    }

    @Transactional
    public void delete(long storeId, long id, long ceoId) {
        Store store = storeService.findById(storeId);
        storeMemberService.checkMember(store.getId(), ceoId);
        storeService.decreaseBookmarkCount(store);

        Bookmark bookmark = bookmarkService.findById(id);
        bookmarkService.delete(bookmark, ceoId);
    }

    @Transactional
    public void updateStarred(long storeId, long id, long ceoId, boolean isStarred) {
        storeMemberService.checkMember(storeId, ceoId);
        bookmarkService.updateStarred(id, isStarred);
    }
}
