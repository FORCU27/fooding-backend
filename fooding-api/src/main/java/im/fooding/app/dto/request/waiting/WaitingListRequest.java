package im.fooding.app.dto.request.waiting;

import im.fooding.core.common.BasicSearch;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.data.domain.Pageable;

public record WaitingListRequest(
        @Schema(description = "search 정보")
        BasicSearch search,

        @Schema(description = "상태 (WAITING, SEATED, CANCELLED)", example = "WAITING")
        String status
) {

    public WaitingListRequest {
        if (search == null) {
            search = new BasicSearch();
        }
    }

    public Pageable pageable() {
        return search.getPageable();
    }
}
