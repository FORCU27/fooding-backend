package im.fooding.app.dto.request.admin.report;

import im.fooding.core.model.report.ReportStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminUpdateReportRequest {
    private String memo;
    private ReportStatus status;
    private long chargerId;
}
