package im.fooding.core.repository.user;

import im.fooding.core.model.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QUserRepository {
    Page<User> list(String searchString, Pageable pageable);
}
