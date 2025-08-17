package im.fooding.app.dto.request.crawling.naverplace;

import im.fooding.core.common.BasicSearch;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper = true)
public class CrawlingNaverPageRequest extends BasicSearch {
}
