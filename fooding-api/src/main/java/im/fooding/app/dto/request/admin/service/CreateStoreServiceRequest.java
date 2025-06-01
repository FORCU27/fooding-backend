package im.fooding.app.dto.request.admin.service;

import im.fooding.core.model.store.StoreServiceType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreateStoreServiceRequest {
    @NotNull
    @Schema( description = "Store ID" )
    private Long storeId;

    @NotNull
    @Schema( description = "가입 서비스 타입" )
    private StoreServiceType type;

    @NotNull
    @Schema( description = "서비스 등록자" )
    private Long userId;
}
