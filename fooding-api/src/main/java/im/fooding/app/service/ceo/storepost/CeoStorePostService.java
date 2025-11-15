package im.fooding.app.service.ceo.storepost;

import im.fooding.app.dto.request.ceo.storepost.CeoCreateStorePostRequest;
import im.fooding.app.dto.request.ceo.storepost.CeoUpdateStorePostRequest;
import im.fooding.app.dto.response.ceo.storepost.CeoStorePostResponse;
import im.fooding.app.service.file.FileUploadService;
import im.fooding.core.model.file.File;
import im.fooding.core.model.store.Store;
import im.fooding.core.model.store.StorePost;
import im.fooding.core.service.store.StoreMemberService;
import im.fooding.core.service.store.StorePostImageService;
import im.fooding.core.service.store.StorePostService;
import im.fooding.core.service.store.StoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class CeoStorePostService {
    private final StorePostService storePostService;
    private final StoreService storeService;
    private final StorePostImageService storePostImageService;
    private final FileUploadService fileUploadService;
    private final StoreMemberService storeMemberService;

    public List<CeoStorePostResponse> list(Long storeId, Long ceoId) {
        checkMember(storeId, ceoId);
        List<StorePost> storePosts = storePostService.list(storeId, null);
        return storePosts.stream()
                .map(CeoStorePostResponse::from)
                .collect(Collectors.toList());
    }

    public CeoStorePostResponse retrieve(Long storeId, Long ceoId) {
        checkMember(storeId, ceoId);
        StorePost storePost = storePostService.findById(storeId);
        return CeoStorePostResponse.from(storePost);
    }

    @Transactional
    public Long create(CeoCreateStorePostRequest request, Long ceoId) {
        Store store = storeService.findById(request.getStoreId());
        checkMember(store.getId(), ceoId);
        StorePost storePost = StorePost.builder()
                .store(store)
                .title(request.getTitle())
                .content(request.getContent())
                .tags(request.getTags())
                .isFixed(request.getIsFixed())
                .isNotice(request.getIsNotice())
                .isCommentAvailable(request.getIsCommentAvailable())
                .build();
        storePostService.create(storePost);

        commitAndCreateImage(storePost, request.getImageIds());

        return storePost.getId();
    }

    @Transactional
    public void update(Long storePostId, CeoUpdateStorePostRequest request, Long ceoId) {
        StorePost storePost = storePostService.findById(storePostId);
        checkMember(storePost.getStore().getId(), ceoId);
        // 기존 이미지 삭제
        if (request.getDeleteImageIds() != null && !request.getImageIds().isEmpty()) {
            request.getDeleteImageIds().forEach(imageId -> {
                if (StringUtils.hasText(imageId)) {
                    storePostImageService.delete(imageId, ceoId);
                }
            });
        }

        commitAndCreateImage(storePost, request.getImageIds());

        storePostService.update(storePost, request.getTitle(), request.getContent(), request.getTags(), request.getIsFixed(), request.getIsNotice(), request.getIsCommentAvailable());
    }

    @Transactional
    public void delete(Long storePostId, Long deletedBy) {
        StorePost storePost = storePostService.findById(storePostId);
        checkMember(storePost.getStore().getId(), deletedBy);
        storePostService.delete(storePost, deletedBy);
    }

    @Transactional
    public void active(Long storePostId, Long ceoId) {
        StorePost storePost = storePostService.findById(storePostId);
        checkMember(storePost.getStore().getId(), ceoId);
        storePostService.active(storePost);
    }

    @Transactional
    public void inactive(Long storePostId, Long ceoId) {
        StorePost storePost = storePostService.findById(storePostId);
        checkMember(storePost.getStore().getId(), ceoId);
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

    private void checkMember(long storeId, long ceoId) {
        storeMemberService.checkMember(storeId, ceoId);
    }
}
