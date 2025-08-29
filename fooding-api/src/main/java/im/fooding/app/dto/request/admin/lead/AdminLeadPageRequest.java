package im.fooding.app.dto.request.admin.lead;

import im.fooding.core.common.BasicSearch;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AdminLeadPageRequest extends BasicSearch {
    
    @Schema(description = "업로드 여부 필터링 (true: 업로드됨, false: 업로드 안됨, null: 전체)", example = "false")
    private Boolean isUploaded;
}

