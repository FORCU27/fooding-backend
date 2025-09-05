package im.fooding.app.dto.request.admin.service;

import im.fooding.core.common.BasicSearch;
import im.fooding.core.model.store.StoreServiceType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RetrieveStoreServiceRequest extends BasicSearch {
    @Schema(description = "가게 ID", example = "1")
    private Long storeId;
    
    @Schema(description = "서비스 타입", example = "WAITING")
    private StoreServiceType serviceType;
}
