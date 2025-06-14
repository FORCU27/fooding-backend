package im.fooding.core.repository.store;

import im.fooding.core.model.store.StoreMember;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StoreMemberRepository extends JpaRepository<StoreMember, Long> {

    Page<StoreMember> findByUserIdAndDeletedFalse(Long userId, Pageable pageable);

    Optional<StoreMember> findByStoreIdAndUserId(long storeId, long userId);
}
