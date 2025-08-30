package im.fooding.app.service.admin.store;

import im.fooding.app.dto.request.admin.store.AdminCreateStoreImageRequest;
import im.fooding.app.dto.request.admin.store.AdminSearchStoreImageRequest;
import im.fooding.app.dto.request.admin.store.AdminUpdateStoreImageRequest;
import im.fooding.app.dto.response.admin.store.AdminStoreImageResponse;
import im.fooding.core.common.PageInfo;
import im.fooding.core.common.PageResponse;
import im.fooding.core.model.store.Store;
import im.fooding.core.model.store.StoreImage;
import im.fooding.core.service.store.StoreImageService;
import im.fooding.core.service.store.StoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminStoreImageService {
    
    private final StoreImageService storeImageService;
    private final StoreService storeService;

    @Transactional(readOnly = true)
    public PageResponse<AdminStoreImageResponse> list(Long storeId, AdminSearchStoreImageRequest search) {
        // 스토어 존재 여부 확인
        if (storeId != null) {
            storeService.findById(storeId);
        }
        
        Page<StoreImage> images = storeImageService.list(
            storeId != null ? storeId : 0L, 
            search.getSearchTag(), 
            search.getPageable()
        );
        
        return PageResponse.of(
            images.getContent().stream()
                .map(AdminStoreImageResponse::of)
                .collect(Collectors.toList()),
            PageInfo.of(images)
        );
    }

    @Transactional(readOnly = true)
    public AdminStoreImageResponse retrieve(Long id) {
        StoreImage storeImage = storeImageService.findById(id);
        return AdminStoreImageResponse.of(storeImage);
    }

    @Transactional
    public Long create(AdminCreateStoreImageRequest request) {
        Store store = storeService.findById(request.getStoreId());
        StoreImage storeImage = storeImageService.create(
            store, 
            request.getImageUrl(), 
            request.getSortOrder(), 
            request.getTags()
        );
        return storeImage.getId();
    }

    @Transactional
    public void update(Long id, AdminUpdateStoreImageRequest request) {
        StoreImage storeImage = storeImageService.findById(id);
        storeImageService.update(
            storeImage, 
            request.getImageUrl(), 
            request.getSortOrder(), 
            request.getTags()
        );
    }

    @Transactional
    public void delete(Long id) {
        StoreImage storeImage = storeImageService.findById(id);
        // 관리자 권한으로 삭제하므로 deletedBy를 0으로 설정 (시스템 삭제)
        storeImageService.delete(storeImage, 0L);
    }
}
