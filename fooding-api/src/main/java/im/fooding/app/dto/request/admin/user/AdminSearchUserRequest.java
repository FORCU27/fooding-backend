package im.fooding.app.dto.request.admin.user;

import im.fooding.core.common.BasicSearch;
import im.fooding.core.model.user.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AdminSearchUserRequest extends BasicSearch {
    @Schema(description = "role", example = "ADMIN")
    private Role role;
    
    @Schema(description = "이메일 검색", example = "user@example.com")
    private String email;
}
