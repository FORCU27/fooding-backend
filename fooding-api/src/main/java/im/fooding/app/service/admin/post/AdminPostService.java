package im.fooding.app.service.admin.post;

import im.fooding.app.dto.request.admin.post.AdminCreatePostRequest;
import im.fooding.app.dto.request.admin.post.AdminPostListRequest;
import im.fooding.app.dto.request.admin.post.AdminUpdatePostRequest;
import im.fooding.app.dto.response.admin.post.AdminPostResponse;
import im.fooding.core.model.post.Post;
import im.fooding.core.model.post.PostType;
import im.fooding.core.service.post.PostService;
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
public class AdminPostService {
    private final PostService postService;

    public List<AdminPostResponse> list(AdminPostListRequest request) {
        List<Post> posts = postService.list(request.getSearchString(), request.getPageable(), request.getType());
        return posts.stream()
                .map(AdminPostResponse::from)
                .collect(Collectors.toList());
    }

    public AdminPostResponse retrieve(Long postId) {
      Post post = postService.findById(postId);
      return AdminPostResponse.from(post);
    }

    @Transactional
    public Long create(AdminCreatePostRequest request) {
      Post post = Post.builder()
              .title(request.getTitle())
              .content(request.getContent())
              .type(request.getType())
              .isVisibleOnHomepage(request.getIsVisibleOnHomepage())
              .isVisibleOnPos(request.getIsVisibleOnPos())
              .isVisibleOnCeo(request.getIsVisibleOnCeo())
              .build();

      return postService.create(post).getId();
    }

    @Transactional
    public void update(Long postId, AdminUpdatePostRequest request) {
      postService.update(
              postId,
              request.getTitle(),
              request.getContent(),
              request.getIsVisibleOnHomepage(),
              request.getIsVisibleOnPos(),
              request.getIsVisibleOnCeo()
      );
    }

    @Transactional
    public void delete(Long postId, Long deletedBy) {
      postService.delete(postId, deletedBy);
    }
}
