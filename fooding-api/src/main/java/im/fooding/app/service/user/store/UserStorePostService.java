package im.fooding.app.service.user.store;

import im.fooding.app.dto.request.user.store.UserSearchStorePostRequest;
import im.fooding.app.dto.response.user.store.UserStoreListResponse;
import im.fooding.app.dto.response.user.store.UserStorePostResponse;
import im.fooding.core.common.PageInfo;
import im.fooding.core.common.PageResponse;
import im.fooding.core.global.UserInfo;
import im.fooding.core.global.exception.ApiException;
import im.fooding.core.global.exception.ErrorCode;
import im.fooding.core.model.store.StorePost;
import im.fooding.core.model.store.StorePostLike;
import im.fooding.core.model.user.User;
import im.fooding.core.service.store.StorePostLikeService;
import im.fooding.core.service.store.StorePostService;
import im.fooding.core.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserStorePostService {
    private final StorePostService storePostService;
    private final StorePostLikeService storePostLikeService;
    private final UserService userService;

    @Transactional(readOnly = true)
    public PageResponse<UserStorePostResponse> list(UserSearchStorePostRequest search, UserInfo userInfo) {
        Page<StorePost> posts = storePostService.list(search.getStoreId(), true, search.getSortType(), search.getSearchString(), search.getPageable());
        List<UserStorePostResponse> list = posts.stream().map(UserStorePostResponse::from).toList();

        if (list != null && !list.isEmpty()) {
            //좋아요 여부 세팅
            if (userInfo != null) {
                setLiked(list, userInfo.getId(), UserStorePostResponse::getId, UserStorePostResponse::setIsLiked);
            }
        }
        return PageResponse.of(list, PageInfo.of(posts));
    }

    @Transactional
    public UserStorePostResponse retrieve(Long storePostId, UserInfo userInfo) {
        StorePost storePost = storePostService.findById(storePostId);
        if (!storePost.isActive()) {
            throw new ApiException(ErrorCode.STORE_POST_NOT_FOUND);
        }
        storePostService.increaseViewCount(storePost);
        UserStorePostResponse post = UserStorePostResponse.from(storePost);
        if (userInfo != null) {
            setLiked(post, userInfo.getId());
        }
        return post;
    }

    @Transactional
    public void like(Long storePostId, UserInfo userInfo) {
        StorePost storePost = storePostService.findById(storePostId);
        User user = userService.findById(userInfo.getId());
        storePostLikeService.create(storePost, user);
        storePostService.increaseLikeCount(storePost);
    }

    @Transactional
    public void unlike(Long storePostId, UserInfo userInfo) {
        StorePost storePost = storePostService.findById(storePostId);
        User user = userService.findById(userInfo.getId());
        storePostLikeService.delete(storePost, user);
        storePostService.decreaseLikeCount(storePost);
    }

    private <T> void setLiked(List<T> list, Long userId, Function<T, Long> idExtractor, BiConsumer<T, Boolean> likedSetter) {
        List<StorePostLike> userLikes = storePostLikeService.findByUserId(userId);
        Set<Long> likedPostIds = userLikes.stream()
                .map(it -> it.getStorePost().getId())
                .collect(Collectors.toSet());

        list.forEach(response ->
                likedSetter.accept(response, likedPostIds.contains(idExtractor.apply(response)))
        );
    }

    private void setLiked(UserStorePostResponse userStorePostResponse, Long userId) {
        List<StorePostLike> userLikes = storePostLikeService.findByUserId(userId);
        Set<Long> likedPostIds = userLikes.stream()
                .filter(it -> !it.isDeleted())
                .map(it -> it.getStorePost().getId())
                .collect(Collectors.toSet());
        userStorePostResponse.setIsLiked(likedPostIds.contains(userStorePostResponse.getId()));
    }
}
