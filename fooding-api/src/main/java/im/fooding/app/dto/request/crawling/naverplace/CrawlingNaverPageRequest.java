package im.fooding.app.dto.request.crawling.naverplace;

import im.fooding.core.common.BasicSearch;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper = true)
public class CrawlingNaverPageRequest extends BasicSearch {
    
    @Schema(description = "업로드 여부 필터링 (true: 업로드됨, false: 업로드 안됨, null: 전체)", example = "false")
    private final Boolean isUploaded;
}
