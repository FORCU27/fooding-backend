package im.fooding.app.service.user.store;

import im.fooding.app.dto.request.user.store.UserCreateStorePostCommentRequest;
import im.fooding.app.dto.request.user.store.UserSearchStorePostCommentRequest;
import im.fooding.app.dto.request.user.store.UserUpdateStorePostCommentRequest;
import im.fooding.app.dto.response.user.store.UserStorePostCommentReplyResponse;
import im.fooding.app.dto.response.user.store.UserStorePostCommentResponse;
import im.fooding.core.common.PageInfo;
import im.fooding.core.common.PageResponse;
import im.fooding.core.global.UserInfo;
import im.fooding.core.model.store.StorePost;
import im.fooding.core.model.store.StorePostComment;
import im.fooding.core.model.store.StorePostCommentLike;
import im.fooding.core.model.user.User;
import im.fooding.core.service.store.StorePostCommentLikeService;
import im.fooding.core.service.store.StorePostCommentService;
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
public class UserStorePostCommentService {
    private final StorePostCommentService storePostCommentService;
    private final UserService userService;
    private final StorePostService storePostService;
    private final StorePostCommentLikeService storePostCommentLikeService;

    @Transactional
    public Long write(UserCreateStorePostCommentRequest request, UserInfo userInfo) {
        User user = userService.findById(userInfo.getId());
        StorePostComment parent = null;
        if (request.getParentId() != null) {
            parent = storePostCommentService.findById(request.getParentId());
        }
        StorePost storePost = storePostService.findById(request.getStorePostId());
        storePost.increaseCommentCount();
        return storePostCommentService.create(storePost, parent, user, request.getContent(), false).getId();
    }

    @Transactional
    public void update(Long id, UserUpdateStorePostCommentRequest request, UserInfo userInfo) {
        User user = userService.findById(userInfo.getId());
        storePostCommentService.update(id, user, request.getContent());
    }

    @Transactional
    public void delete(Long id, UserInfo userInfo) {
        User user = userService.findById(userInfo.getId());
        StorePostComment storePostComment = storePostCommentService.findById(id);
        storePostCommentService.delete(storePostComment, user, user.getId());

        StorePost storePost = storePostComment.getStorePost();
        storePost.decreaseCommentCount();
    }

    @Transactional
    public void like(Long id, UserInfo userInfo) {
        StorePostComment storePostComment = storePostCommentService.findById(id);
        User user = userService.findById(userInfo.getId());
        storePostCommentLikeService.create(storePostComment, user);
        storePostCommentService.increaseLikeCount(storePostComment);
    }

    @Transactional
    public void unlike(Long id, UserInfo userInfo) {
        StorePostComment storePostComment = storePostCommentService.findById(id);
        User user = userService.findById(userInfo.getId());
        storePostCommentLikeService.delete(storePostComment, user);
        storePostCommentService.decreaseLikeCount(storePostComment);
    }

    @Transactional
    public PageResponse<UserStorePostCommentResponse> list(UserSearchStorePostCommentRequest search, UserInfo userInfo) {
        Page<StorePostComment> comments = storePostCommentService.list(search.getStorePostId(), search.getSearchString(), search.getPageable());
        List<UserStorePostCommentResponse> list = comments.stream().map(UserStorePostCommentResponse::of).toList();

        if (list != null && !list.isEmpty() && userInfo != null) {

            List<StorePostCommentLike> userLikes = storePostCommentLikeService.findByUserId(userInfo.getId());
            Set<Long> likedCommentIds = userLikes.stream()
                    .map(it -> it.getStorePostComment().getId())
                    .collect(Collectors.toSet());

            //좋아요 여부 세팅
            setLiked(list, UserStorePostCommentResponse::getId, UserStorePostCommentResponse::setIsLiked, likedCommentIds);

            //대댓글 좋아요 여부 세팅
            list.forEach(it -> {
                List<UserStorePostCommentReplyResponse> replies = it.getReplies();
                if (replies != null && !replies.isEmpty()) {
                    setLiked(replies, UserStorePostCommentReplyResponse::getId, UserStorePostCommentReplyResponse::setIsLiked, likedCommentIds);
                }
            });
        }

        return PageResponse.of(list, PageInfo.of(comments));
    }

    private <T> void setLiked(List<T> list, Function<T, Long> idExtractor, BiConsumer<T, Boolean> likedSetter, Set<Long> likedCommentIds) {
        list.forEach(response ->
                likedSetter.accept(response, likedCommentIds.contains(idExtractor.apply(response)))
        );
    }
}
