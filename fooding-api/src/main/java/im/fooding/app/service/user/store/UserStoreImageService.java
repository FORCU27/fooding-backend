package im.fooding.app.service.user.store;

import im.fooding.app.dto.request.user.store.UserSearchStoreImageRequest;
import im.fooding.app.dto.response.user.store.UserStoreImageResponse;
import im.fooding.core.common.PageInfo;
import im.fooding.core.common.PageResponse;
import im.fooding.core.model.store.StoreImage;
import im.fooding.core.service.store.StoreImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserStoreImageService {
    private final StoreImageService storeImageService;

    @Transactional(readOnly = true)
    public PageResponse<UserStoreImageResponse> list(long storeId, UserSearchStoreImageRequest search) {
        Page<StoreImage> images = storeImageService.list(storeId, search.getTag(), search.getIsMain(), search.getPageable());
        return PageResponse.of(images.stream().map(UserStoreImageResponse::of).toList(), PageInfo.of(images));
    }
}
