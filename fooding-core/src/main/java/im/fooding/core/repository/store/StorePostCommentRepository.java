package im.fooding.core.repository.store;

import im.fooding.core.model.store.StorePostComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StorePostCommentRepository extends JpaRepository<StorePostComment, Long>, QStorePostCommentRepository {
}
