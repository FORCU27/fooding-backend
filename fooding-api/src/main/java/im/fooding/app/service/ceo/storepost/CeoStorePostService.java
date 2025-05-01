package im.fooding.app.service.ceo.storepost;

import im.fooding.app.dto.request.ceo.storepost.CeoCreateStorePostRequest;
import im.fooding.app.dto.request.ceo.storepost.CeoUpdateStorePostRequest;
import im.fooding.app.dto.response.ceo.storepost.StorePostResponse;
import im.fooding.core.model.store.Store;
import im.fooding.core.model.store.StorePost;
import im.fooding.core.service.store.StorePostService;
import im.fooding.core.service.store.StoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class CeoStorePostService {
    private final StorePostService storePostService;
    private final StoreService storeService;

    public List<StorePostResponse> list(Long storeId) {
      List<StorePost> storePosts = storePostService.list(storeId);
      return storePosts.stream()
              .map(StorePostResponse::from)
              .collect(Collectors.toList());
    }

    @Transactional
    public Long create(CeoCreateStorePostRequest request) {
      Store store = storeService.findById(request.getStoreId());

      StorePost storePost = StorePost.builder()
              .store(store)
              .title(request.getTitle())
              .content(request.getContent())
              .tags(request.getTags())
              .isFixed(request.getIsFixed())
              .build();

      return storePostService.create(storePost).getId();

    }

    @Transactional
    public void update(Long storePostId, CeoUpdateStorePostRequest request) {
      StorePost storePost = storePostService.findById(storePostId);
      storePostService.update(storePost, request.getTitle(), request.getContent(), request.getTags(), request.getIsFixed());

    }

    @Transactional
    public void delete(Long storePostId, Long deletedBy) {
      storePostService.delete(storePostId, deletedBy);
    }
}