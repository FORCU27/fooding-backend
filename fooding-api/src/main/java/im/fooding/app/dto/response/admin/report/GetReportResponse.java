package im.fooding.app.dto.response.admin.report;

import im.fooding.core.model.report.Report;
import im.fooding.core.model.report.ReportStatus;
import im.fooding.core.model.report.ReportTargetType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class GetReportResponse {
    private Long id;
    private long referenceId;
    private ReportTargetType targetType;
    private String description;
    private String memo;
    private Long reporterId;
    private String reporterName;
    private ReportStatus status;
    private Long chargerId;
    private String chargerName;

    public static GetReportResponse of(Report report){
        return GetReportResponse.builder()
                .id(report.getId())
                .referenceId(report.getReferenceId())
                .targetType(report.getTargetType())
                .description(report.getDescription())
                .memo(report.getMemo())
                .reporterId(report.getReporter() != null ? report.getReporter().getId() : null)
                .reporterName(report.getReporter() != null ? report.getReporter().getName() : null)
                .status(report.getStatus())
                .chargerId(report.getCharger() != null ? report.getCharger().getId() : null)
                .chargerName(report.getCharger() != null ? report.getCharger().getName() : null)
                .build();
    }
}
