package im.fooding.app.service.admin.store;

import im.fooding.app.dto.request.admin.store.image.AdminCreateStoreImageRequest;
import im.fooding.app.dto.request.admin.store.image.AdminSearchStoreImageRequest;
import im.fooding.app.dto.request.admin.store.image.AdminUpdateStoreImageRequest;
import im.fooding.app.dto.response.admin.store.AdminStoreImageResponse;
import im.fooding.app.service.file.FileUploadService;
import im.fooding.core.common.PageInfo;
import im.fooding.core.common.PageResponse;
import im.fooding.core.model.file.File;
import im.fooding.core.model.store.Store;
import im.fooding.core.model.store.StoreImage;
import im.fooding.core.service.store.StoreImageService;
import im.fooding.core.service.store.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminStoreImageService {

    private final StoreService storeService;
    private final StoreImageService storeImageService;
    private final FileUploadService fileUploadService;

    @Transactional
    public Long create(long storeId, AdminCreateStoreImageRequest request) {
        Store store = storeService.findById(storeId);
        File file = fileUploadService.commit(request.getImageId());
        return storeImageService.create(store, file.getUrl(), request.getSortOrder(), request.getTags()).getId();
    }

    public PageResponse<AdminStoreImageResponse> list(long storeId, AdminSearchStoreImageRequest search) {
        Page<StoreImage> images = storeImageService.list(storeId, search.getSearchTag(), search.getPageable());
        return PageResponse.of(images.stream().map(AdminStoreImageResponse::of).toList(), PageInfo.of(images));
    }

    @Transactional
    public void update(long storeId, long id, AdminUpdateStoreImageRequest request) {
        storeService.findById(storeId); // 존재 여부 확인
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
        storeService.findById(storeId); // 존재 여부 확인
        StoreImage storeImage = storeImageService.findById(id);
        storeImageService.delete(storeImage, userId);
    }
}

