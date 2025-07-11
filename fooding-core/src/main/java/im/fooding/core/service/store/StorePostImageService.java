package im.fooding.core.service.store;

import im.fooding.core.global.exception.ApiException;
import im.fooding.core.global.exception.ErrorCode;
import im.fooding.core.model.store.StoreImage;
import im.fooding.core.model.store.StorePost;
import im.fooding.core.model.store.StorePostImage;
import im.fooding.core.repository.store.image.StorePostImageRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class StorePostImageService {
    private final StorePostImageRepository storePostImageRepository;

    public StorePostImage create(StorePost storePost, String imageUrl) {
        StorePostImage storeImage = new StorePostImage(storePost, imageUrl);
        return storePostImageRepository.save(storeImage);
    }

    public Optional<StorePostImage> findById(long id) {
        return storePostImageRepository.findById(id)
                .filter(it -> !it.isDeleted());
    }

    public List<StorePostImage> list(long storePostId) {
        return storePostImageRepository.findAllByStorePostId(storePostId);
    }

    public void delete(StoreImage storeImage, long deletedBy) {
        storeImage.delete(deletedBy);
    }

    public void update(StorePostImage storePostImage, String imageUrl) {
        storePostImage.update(imageUrl);
    }
}
