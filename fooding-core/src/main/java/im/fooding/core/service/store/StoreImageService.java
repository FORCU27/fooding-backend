package im.fooding.core.service.store;

import im.fooding.core.global.exception.ApiException;
import im.fooding.core.global.exception.ErrorCode;
import im.fooding.core.model.store.Store;
import im.fooding.core.model.store.StoreImage;
import im.fooding.core.repository.store.image.StoreImageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class StoreImageService {
    private final StoreImageRepository storeImageRepository;

    /**
     * 가게 대표 이미지 조회
     *
     * @param storeId
     * @return StoreImage
     */
    public StoreImage findByStore(long storeId) {
        return storeImageRepository.findByStore(storeId)
                .orElseThrow(() -> new ApiException(ErrorCode.STORE_IMAGE_NOT_FOUND));
    }

    /**
     * 가게 이미지 추가
     *
     * @param store
     * @param imageUrl
     * @param sortOrder
     * @param tags
     */
    public StoreImage create(Store store, String imageUrl, int sortOrder, String tags) {
        StoreImage storeImage = StoreImage.builder()
                .store(store)
                .imageUrl(imageUrl)
                .sortOrder(sortOrder)
                .tags(tags)
                .build();
        return storeImageRepository.save(storeImage);
    }

    /**
     * id로 조회
     *
     * @param id
     * @return StoreImage
     */
    public StoreImage findById(long id) {
        return storeImageRepository.findById(id)
                .filter(it -> !it.isDeleted())
                .orElseThrow(() -> new ApiException(ErrorCode.STORE_IMAGE_NOT_FOUND));
    }

    /**
     * 리스트 조회
     *
     * @param storeId
     * @param searchTag
     * @param pageable
     * @return Page<StoreImage>
     */
    public Page<StoreImage> list(long storeId, String searchTag, Pageable pageable) {
        return storeImageRepository.list(storeId, searchTag, pageable);
    }

    /**
     * 삭제
     *
     * @param storeImage
     * @param deletedBy
     */
    public void delete(StoreImage storeImage, long deletedBy) {
        storeImage.delete(deletedBy);
    }

    /**
     * 수정
     *
     * @param storeImage
     * @param imageUrl
     * @param sortOrder
     * @param tags
     */
    public void update(StoreImage storeImage, String imageUrl, int sortOrder, String tags) {
        storeImage.update(imageUrl, sortOrder, tags);
    }
}
