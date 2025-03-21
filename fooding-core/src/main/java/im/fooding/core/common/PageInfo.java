package im.fooding.core.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PageInfo {
    @Schema(description = "페이지번호", example = "1")
    private int pageNum;

    @Schema(description = "페이지사이즈", example = "10")
    private int pageSize;

    @Schema(description = "데이터합계", example = "13")
    private int totalCount;

    @Schema(description = "페이지수", example = "2")
    private int totalPages;

    @Builder
    public PageInfo(int pageNum, int pageSize, int totalCount, int totalPages) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.totalCount = totalCount;
        this.totalPages = totalPages;
    }

    public static PageInfo of(int pageNum, int pageSize, int totalCount, int totalPages) {
        return PageInfo.builder()
                .pageNum(pageNum)
                .pageSize(pageSize)
                .totalCount(totalCount)
                .totalPages(totalPages)
                .build();
    }
}
