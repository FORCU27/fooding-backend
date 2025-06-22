package im.fooding.app.service.ceo.store;

import im.fooding.app.dto.request.ceo.store.CeoCreateStoreImageRequest;
import im.fooding.app.dto.request.ceo.store.CeoSearchStoreImageRequest;
import im.fooding.app.dto.request.ceo.store.CeoUpdateStoreImageRequest;
import im.fooding.app.dto.response.ceo.store.CeoStoreImageResponse;
import im.fooding.app.service.file.FileUploadService;
import im.fooding.core.common.PageInfo;
import im.fooding.core.common.PageResponse;
import im.fooding.core.model.file.File;
import im.fooding.core.model.store.Store;
import im.fooding.core.model.store.StoreImage;
import im.fooding.core.service.store.StoreImageService;
import im.fooding.core.service.store.StoreMemberService;
import im.fooding.core.service.store.StoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
@Slf4j
public class CeoStoreImageService {
    private final StoreService storeService;
    private final StoreMemberService storeMemberService;
    private final StoreImageService storeImageService;
    private final FileUploadService fileUploadService;

    @Transactional
    public Long create(long storeId, CeoCreateStoreImageRequest request, long userId) {
        Store store = storeService.findById(storeId);
        checkMember(store.getId(), userId);

        File file = fileUploadService.commit(request.getImageId());
        return storeImageService.create(store, file.getUrl(), request.getSortOrder(), request.getTags()).getId();
    }

    @Transactional(readOnly = true)
    public PageResponse<CeoStoreImageResponse> list(long storeId, CeoSearchStoreImageRequest search, long userId) {
        checkMember(storeId, userId);
        Page<StoreImage> images = storeImageService.list(storeId, search.getSearchTag(), search.getPageable());
        return PageResponse.of(images.stream().map(CeoStoreImageResponse::of).toList(), PageInfo.of(images));
    }

    @Transactional
    public void update(long storeId, long id, CeoUpdateStoreImageRequest request, long userId) {
        checkMember(storeId, userId);
        StoreImage storeImage = storeImageService.findById(id);

        String imageUrl = storeImage.getImageUrl();

        if (StringUtils.hasText(request.getImageId())) {
            File file = fileUploadService.commit(request.getImageId());
            imageUrl = file.getUrl();
        }

        storeImageService.update(storeImage, imageUrl, request.getSortOrder(), request.getTags());
    }

    @Transactional
    public void delete(long storeId, long id, long userId) {
        checkMember(storeId, userId);
        StoreImage storeImage = storeImageService.findById(id);
        storeImageService.delete(storeImage, userId);
    }

    private void checkMember(long storeId, long userId) {
        storeMemberService.checkMember(storeId, userId);
    }
}
