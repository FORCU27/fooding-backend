package im.fooding.core.repository.store;

import im.fooding.core.model.store.StorePost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StorePostRepository extends JpaRepository<StorePost, Long>, QStorePostRepository {
    @Override
    @EntityGraph(attributePaths = {"store", "images"})
    Optional<StorePost> findById(Long id);

    List<StorePost> findByStoreIdOrderByIsFixedDescUpdatedAtDesc(Long storeId);

    Page<StorePost> findAllByDeletedFalse(Pageable pageable);   
}
