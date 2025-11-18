package im.fooding.app.service.admin.store;

import im.fooding.app.dto.request.admin.store.AdminStorePostCreateRequest;
import im.fooding.app.dto.request.admin.store.AdminStorePostUpdateRequest;
import im.fooding.app.dto.response.admin.store.AdminStorePostResponse;
import im.fooding.app.dto.request.admin.store.AdminStorePostListRequest;
import im.fooding.app.service.file.FileUploadService;
import im.fooding.core.common.PageInfo;
import im.fooding.core.common.PageResponse;
import im.fooding.core.model.file.File;
import im.fooding.core.model.store.Store;
import im.fooding.core.model.store.StorePost;
import im.fooding.core.model.store.StorePostSortType;
import im.fooding.core.service.store.StorePostImageService;
import im.fooding.core.service.store.StorePostService;
import im.fooding.core.service.store.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminStorePostService {

    private final StoreService storeService;
    private final StorePostService storePostService;
    private final StorePostImageService storePostImageService;
    private final FileUploadService fileUploadService;

    @Transactional
    public void create(AdminStorePostCreateRequest request) {
        Store store = storeService.findById(request.getStoreId());
        StorePost storePost = StorePost.builder()
                .store(store)
                .title(request.getTitle())
                .content(request.getContent())
                .tags(request.getTags())
                .isFixed(request.getIsFixed())
                .isNotice(request.getIsNotice())
                .build();

        commitAndCreateImage(storePost, request.getImageIds());

        storePostService.create(storePost);
    }

    public AdminStorePostResponse get(long id) {
        return AdminStorePostResponse.from(storePostService.findById(id));
    }

    public PageResponse<AdminStorePostResponse> list(AdminStorePostListRequest search) {
        Page<StorePost> page = storePostService.list(search.getStoreId(), null, StorePostSortType.RECENT, search.getSearchString(), search.getPageable());
        return PageResponse.of(page.stream().map(AdminStorePostResponse::from).toList(), PageInfo.of(page));
    }

    @Transactional
    public void update(long id, AdminStorePostUpdateRequest request, Long adminId) {
        StorePost storePost = storePostService.findById(id);
        // 기존 이미지 삭제
        if (request.getDeleteImageIds() != null && !request.getImageIds().isEmpty()) {
            request.getDeleteImageIds().forEach(imageId -> {
                if (StringUtils.hasText(imageId)) {
                    storePostImageService.delete(imageId, adminId);
                }
            });
        }

        commitAndCreateImage(storePost, request.getImageIds());

        storePostService.update(storePost, request.getTitle(), request.getContent(), request.getTags(), request.getIsFixed(), request.getIsNotice(), request.getIsCommentAvailable());
    }

    @Transactional
    public void delete(long id, long userId) {
        StorePost storePost = storePostService.findById(id);
        storePostService.delete(storePost, userId);
    }

    @Transactional
    public void active(Long id) {
        StorePost storePost = storePostService.findById(id);
        storePostService.active(storePost);
    }

    @Transactional
    public void inactive(Long id) {
        StorePost storePost = storePostService.findById(id);
        storePostService.inactive(storePost);
    }

    private void commitAndCreateImage(StorePost storePost, List<String> imageIds) {
        if (imageIds != null && !imageIds.isEmpty()) {
            imageIds.forEach(imageId -> {
                if (StringUtils.hasText(imageId)) {
                    File file = fileUploadService.commit(imageId);
                    storePostImageService.create(storePost, file.getId(), file.getUrl());
                }
            });
        }
    }
}
