package im.fooding.app.dto.request.admin.service;

import im.fooding.core.common.BasicSearch;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RetrieveStoreServiceRequest extends BasicSearch {
    
    @Schema(description = "가게 ID", example = "1")
    private Long storeId;
}
