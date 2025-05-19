package im.fooding.app.service.admin.store;

import im.fooding.app.dto.request.admin.store.AdminStorePostCreateRequest;
import im.fooding.app.dto.request.admin.store.AdminStorePostUpdateRequest;
import im.fooding.app.dto.response.admin.store.AdminStorePostResponse;
import im.fooding.core.common.BasicSearch;
import im.fooding.core.common.PageInfo;
import im.fooding.core.common.PageResponse;
import im.fooding.core.model.store.Store;
import im.fooding.core.model.store.StorePost;
import im.fooding.core.service.store.StorePostService;
import im.fooding.core.service.store.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminStorePostService {

    private final StoreService storeService;
    private final StorePostService storePostService;

    @Transactional
    public void create(AdminStorePostCreateRequest request) {
        Store store = storeService.findById(request.getStoreId());
        StorePost storePost = StorePost.builder()
                .store(store)
                .title(request.getTitle())
                .content(request.getContent())
                .tags(request.getTags())
                .isFixed(request.getIsFixed())
                .build();

        storePostService.create(storePost);
    }

    public AdminStorePostResponse get(long id) {
        return AdminStorePostResponse.from(storePostService.findById(id));
    }

    @Transactional
    public PageResponse<AdminStorePostResponse> list(BasicSearch search) {
        Page<StorePost> storePosts = storePostService.list(search.getPageable());
        return PageResponse.of(storePosts.stream().map(AdminStorePostResponse::from).toList(), PageInfo.of(storePosts));
    }

    @Transactional
    public void update(long id, AdminStorePostUpdateRequest request) {
        StorePost storePost = storePostService.findById(id);

        storePostService.update(storePost, request.getTitle(), request.getContent(), request.getTags(), request.getIsFixed());
    }

    @Transactional
    public void delete(long id, long userId) {
        storePostService.delete(id, userId);
    }
}
