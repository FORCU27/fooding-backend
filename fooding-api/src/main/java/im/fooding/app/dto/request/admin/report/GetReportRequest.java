package im.fooding.app.dto.request.admin.report;

import im.fooding.core.common.BasicSearch;
import im.fooding.core.model.report.ReportStatus;
import im.fooding.core.model.report.ReportTargetType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetReportRequest extends BasicSearch {
    private Long referenceId;
    private ReportTargetType targetType;
    private Long reporterId;
    private ReportStatus status;
    private Long chargerId;
}
