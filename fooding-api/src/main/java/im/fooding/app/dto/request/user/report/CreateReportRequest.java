package im.fooding.app.dto.request.user.report;

import im.fooding.core.model.report.ReportTargetType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CreateReportRequest {
    @Schema( description = "대상 ID" )
    private long referenceId;

    @Schema( description = "대상 타입" )
    private ReportTargetType targetType;

    @Schema( description = "신고 내용" )
    private String description;

    @Schema( description = "신고자 ID" )
    private long reporterId;
}
