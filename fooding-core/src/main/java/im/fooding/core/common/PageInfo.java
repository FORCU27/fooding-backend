package im.fooding.core.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.Page;

@Data
public class PageInfo {
    @Schema(description = "페이지번호", example = "1")
    private int pageNum;

    @Schema(description = "페이지사이즈", example = "10")
    private int pageSize;

    @Schema(description = "데이터합계", example = "13")
    private long totalCount;

    @Schema(description = "페이지수", example = "2")
    private int totalPages;

    @Builder
    public PageInfo(int pageNum, int pageSize, long totalCount, int totalPages) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.totalCount = totalCount;
        this.totalPages = totalPages;
    }

    public static PageInfo of(Page page) {
        return PageInfo.builder()
                .pageNum(page.getPageable().getPageNumber() + 1)
                .pageSize(page.getPageable().getPageSize())
                .totalCount(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .build();
    }

    public PageInfo() {
        this.pageNum = 1;
        this.pageSize = 10;
        this.totalCount = 0;
        this.totalPages = 0;
    }
}
