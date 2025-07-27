package im.fooding.core.dto.request.menu;

import im.fooding.core.model.store.Store;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class MenuBoardUpdateRequest {
    long id;
    Store store;
    String title;
    String imageUrl;
}
