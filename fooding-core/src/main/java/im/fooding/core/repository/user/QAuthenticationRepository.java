package im.fooding.core.repository.user;

import im.fooding.core.model.user.Authentication;

import java.util.List;

public interface QAuthenticationRepository {
    List<Authentication> list( String email, String phoneNumber, int code, boolean isSuccess );
}
