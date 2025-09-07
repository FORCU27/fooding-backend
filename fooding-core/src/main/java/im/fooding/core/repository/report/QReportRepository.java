package im.fooding.core.repository.report;

import im.fooding.core.model.report.Report;
import im.fooding.core.model.report.ReportStatus;
import im.fooding.core.model.report.ReportTargetType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QReportRepository {
    public Page<Report> list(
            Pageable pageable,
            String searchString,
            ReportStatus status,
            ReportTargetType targetType,
            Long referenceId,
            Long reporterId,
            Long chargerId
    );

}
