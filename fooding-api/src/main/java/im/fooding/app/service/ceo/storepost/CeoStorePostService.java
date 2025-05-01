package im.fooding.app.service.ceo.storepost;

import im.fooding.app.dto.response.ceo.storepost.StorePostResponse;
import im.fooding.core.model.store.StorePost;
import im.fooding.core.service.store.StorePostService;
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

    public List<StorePostResponse> list(Long storeId) {
      List<StorePost> storePosts = storePostService.list(storeId);
      return storePosts.stream()
              .map(StorePostResponse::from)
              .collect(Collectors.toList());
    }
}