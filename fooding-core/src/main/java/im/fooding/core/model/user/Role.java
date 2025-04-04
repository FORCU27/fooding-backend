package im.fooding.core.model.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    USER("ROLE_USER"), CEO("ROLE_CEO"), ADMIN("ROLE_ADMIN");

    private final String value;
}
