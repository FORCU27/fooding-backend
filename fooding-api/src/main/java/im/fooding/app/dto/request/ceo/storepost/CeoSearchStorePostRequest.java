package im.fooding.app.dto.request.ceo.storepost;

import im.fooding.core.common.BasicSearch;
import im.fooding.core.model.store.StorePostSortType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CeoSearchStorePostRequest extends BasicSearch {
    @NotNull
    @Schema(description = "가게 ID", example = "1")
    private Long storeId;

    @Schema(
            description = "정렬 타입(RECENT, OLD)",
            example = "RECENT",
            allowableValues = {"RECENT", "OLD"}
    )
    private StorePostSortType sortType = StorePostSortType.RECENT;
}
