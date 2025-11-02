package im.fooding.app.dto.request.ceo.store.bookmark;

import im.fooding.core.common.BasicSearch;
import im.fooding.core.model.bookmark.BookmarkSortType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CeoSearchStoreBookmarkRequest extends BasicSearch {
    @Schema(
            description = "정렬 타입(RECENT, OLD)",
            example = "RECENT",
            allowableValues = {"RECENT", "OLD", "STARRED"}
    )
    private BookmarkSortType sortType = BookmarkSortType.RECENT;
}
