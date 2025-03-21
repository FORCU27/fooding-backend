package im.fooding.core.common;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;


@Getter
@Setter
@NoArgsConstructor
public class BasicSearch {
    @Schema(description = "검색어", example = "홍길동")
    private String searchString;

    @Schema(description = "현재 페이지", example = "1")
    @Min(1)
    private int pageNum = 1;

    @Schema(description = "한 페이지당 로우", example = "10")
    @Min(1)
    @Max(100)
    private int pageSize = 10;

    @Schema(hidden = true)
    public Pageable getPageable() {
        return PageRequest.of(this.getPageNum() - 1, this.getPageSize());
    }

}
