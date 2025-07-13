package im.fooding.app.dto.request.ceo.store;

import im.fooding.core.dto.request.store.StoreFilter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Value;

@Value
public class CeoSearchStoreRequest {

    @Schema(description = "지역 ID", example = "KR-11")
    String regionId;

    public StoreFilter toStoreFilter() {
        return new StoreFilter(regionId);
    }
}
