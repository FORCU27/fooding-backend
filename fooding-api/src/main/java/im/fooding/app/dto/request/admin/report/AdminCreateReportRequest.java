package im.fooding.app.dto.request.admin.report;

import im.fooding.core.model.report.ReportTargetType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminCreateReportRequest {
    private long referenceId;
    private ReportTargetType targetType;
    private String description;
    private String memo;
    private long reporterId;
}
