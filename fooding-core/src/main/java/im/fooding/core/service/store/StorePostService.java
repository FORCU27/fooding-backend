package im.fooding.core.service.store;

import im.fooding.core.global.exception.ApiException;
import im.fooding.core.global.exception.ErrorCode;
import im.fooding.core.model.store.StorePost;
import im.fooding.core.repository.store.StorePostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class StorePostService {
    private final StorePostRepository storePostRepository;

    public List<StorePost> list(Long storeId) {
      return storePostRepository.findByStoreIdOrderByIsFixedDescUpdatedAtDesc(storeId);
    }

    public Page<StorePost> list(Pageable pageable) {
        return storePostRepository.findAllByDeletedFalse(pageable);
    }

    public StorePost findById(Long id) {
      return storePostRepository.findById(id)
              .filter(it -> !it.isDeleted())
              .orElseThrow(() -> new ApiException(ErrorCode.STORE_POST_NOT_FOUND));
    }

    @Transactional
    public StorePost create(StorePost storePost) {
      return storePostRepository.save(storePost);
    }

    @Transactional
    public void update(StorePost storePost, String title, String content, List<String> tags, boolean isFixed) {
      storePost.update(title, content, tags, isFixed);
    }

    @Transactional
      public void delete(Long id, Long deletedBy) {
        StorePost storePost = findById(id);
        storePost.delete(deletedBy);
      }
}
