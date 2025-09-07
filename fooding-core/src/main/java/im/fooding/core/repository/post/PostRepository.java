package im.fooding.core.repository.post;

import im.fooding.core.model.post.Post;
import im.fooding.core.model.post.PostType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>, QPostRepository {
}
