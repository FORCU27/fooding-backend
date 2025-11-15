package im.fooding.core.service.store;

import im.fooding.core.global.exception.ApiException;
import im.fooding.core.global.exception.ErrorCode;
import im.fooding.core.model.store.StorePost;
import im.fooding.core.model.store.StorePostImage;
import im.fooding.core.repository.store.image.StorePostImageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class StorePostImageService {
    private final StorePostImageRepository storePostImageRepository;

    public StorePostImage create(StorePost storePost, String imageId, String imageUrl) {
        StorePostImage storeImage = new StorePostImage(storePost, imageId, imageUrl);
        return storePostImageRepository.save(storeImage);
    }

    public StorePostImage findById(long id) {
        return storePostImageRepository.findById(id)
                .filter(it -> !it.isDeleted())
                .orElseThrow(() -> new ApiException(ErrorCode.STORE_POST_IMAGE_NOT_FOUND));
    }

    public List<StorePostImage> list(long storePostId) {
        return storePostImageRepository.findAllByStorePostId(storePostId);
    }

    public void delete(String imageId, long deletedBy) {
        StorePostImage storePostImage = storePostImageRepository.findByImageId(imageId)
                .filter(it -> !it.isDeleted())
                .orElseThrow(() -> new ApiException(ErrorCode.STORE_POST_IMAGE_NOT_FOUND));

        storePostImage.delete(deletedBy);
    }

    public void update(StorePostImage storePostImage, String imageUrl) {
        storePostImage.update(imageUrl);
    }
}
