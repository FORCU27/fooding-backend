package im.fooding.app.dto.response.user.store;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserPopularStoresResponse {

    List<UserStoreListResponse> list;

    public UserPopularStoresResponse(List<UserStoreListResponse> list) {
        this.list = list;
    }
}
