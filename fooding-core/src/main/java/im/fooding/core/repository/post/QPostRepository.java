package im.fooding.core.repository.post;

import im.fooding.core.model.post.Post;
import im.fooding.core.model.post.PostType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QPostRepository {
    Page<Post> list(String searchString, Pageable pageable, PostType type);

    Page<Post> list(String searchString, Pageable pageable, PostType type, Boolean isVisibleOnCeo);
}
