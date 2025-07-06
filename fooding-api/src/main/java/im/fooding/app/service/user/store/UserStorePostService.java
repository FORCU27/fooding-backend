package im.fooding.app.service.user.store;

import im.fooding.app.dto.response.user.store.UserStorePostResponse;
import im.fooding.core.model.store.StorePost;
import im.fooding.core.model.store.StorePostImage;
import im.fooding.core.service.store.StorePostImageService;
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
public class UserStorePostService {
    private final StorePostService storePostService;
    private final StorePostImageService storePostImageService;

    public List<UserStorePostResponse> list(Long storeId) {

        return storePostService.list(storeId).stream()
                .map(UserStorePostResponse::from)
                .collect(Collectors.toList());
    }

    public UserStorePostResponse retrieve(Long storePostId) {
      StorePost storePost = storePostService.findById(storePostId);
      return UserStorePostResponse.from(storePost);
    }
}
