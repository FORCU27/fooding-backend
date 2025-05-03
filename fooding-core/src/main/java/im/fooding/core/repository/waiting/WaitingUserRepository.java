package im.fooding.core.repository.waiting;

import im.fooding.core.model.store.Store;
import im.fooding.core.model.waiting.WaitingUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WaitingUserRepository extends JpaRepository<WaitingUser, Long> {

    Optional<WaitingUser> findByStoreAndPhoneNumber(Store store, String phoneNumber);

    Page<WaitingUser> findAllByDeletedFalse(Pageable pageable);
}
