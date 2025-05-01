package im.fooding.core.repository.store;

import im.fooding.core.model.store.StorePost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StorePostRepository extends JpaRepository<StorePost, Long> {
    List<StorePost> findByStoreIdOrderByIsFixedDescUpdatedAtDesc(Long storeId);
}
