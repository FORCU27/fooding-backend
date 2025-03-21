package im.fooding.core.common;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class PageResponse<T> {
    private List<T> list;

    private PageInfo pageInfo;

    @Builder
    private PageResponse(List<T> list, PageInfo pageInfo) {
        this.list = list;
        this.pageInfo = pageInfo;
    }

    public static <T> PageResponse<T> of(List<T> list, PageInfo pageInfo) {
        return PageResponse.<T>builder()
                .list(list)
                .pageInfo(pageInfo)
                .build();
    }
}
