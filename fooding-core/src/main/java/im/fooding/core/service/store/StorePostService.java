package im.fooding.core.service.store;

import im.fooding.core.model.store.StorePost;
import im.fooding.core.repository.store.StorePostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class StorePostService {
    private final StorePostRepository storePostRepository;

    public List<StorePost> getStorePosts(Long storeId) {
      return storePostRepository.findByStoreIdOrderByIsFixedDescUpdatedAtDesc(storeId);
    }
}
