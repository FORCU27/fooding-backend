package im.fooding.core.service.post;

import im.fooding.core.global.exception.ApiException;
import im.fooding.core.global.exception.ErrorCode;
import im.fooding.core.model.post.Post;
import im.fooding.core.model.post.PostType;
import im.fooding.core.repository.post.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class PostService {
    private final PostRepository postRepository;

    public List<Post> list(PostType type) {
      return postRepository.findByTypeOrderByCreatedAtDesc(type);
    }

    public Post findById(Long id) {
      return postRepository.findById(id)
              .filter(it -> !it.isDeleted())
              .orElseThrow(() -> new ApiException(ErrorCode.POST_NOT_FOUND));
    }

    @Transactional
    public Post create(Post post) {
      return postRepository.save(post);
    }

    @Transactional
    public void update(Long id, String title, String content,
                       boolean isVisibleOnHomepage, boolean isVisibleOnPos, boolean isVisibleOnCeo) {
      Post post = findById(id);
      post.update(title, content, isVisibleOnHomepage, isVisibleOnPos, isVisibleOnCeo);
    }

    @Transactional
    public void delete(Long id, Long deletedBy) {
      Post post = findById(id);
      post.delete(deletedBy);
    }
}
