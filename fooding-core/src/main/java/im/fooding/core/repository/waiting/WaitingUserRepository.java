package im.fooding.core.repository.waiting;

import im.fooding.core.model.store.Store;
import im.fooding.core.model.waiting.WaitingUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WaitingUserRepository extends JpaRepository<WaitingUser, Long> {

    Optional<WaitingUser> findByStoreAndPhoneNumberAndDeletedFalse(Store store, String phoneNumber);
}
