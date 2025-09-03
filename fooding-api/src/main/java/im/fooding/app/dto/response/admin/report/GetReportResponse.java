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
    private long reporterId;
    private String reporterName;
    private ReportStatus status;
    private long chargerId;
    private String chargerName;

    public static GetReportResponse of(Report report){
        return GetReportResponse.builder()
                .id( report.getId() )
                .referenceId( report.getReferenceId() )
                .targetType( report.getTargetType() )
                .description( report.getDescription() )
                .memo( report.getMemo() )
                .reporterId( report.getReporter().getId() )
                .reporterName( report.getReporter().getName() )
                .status( report.getStatus() )
                .chargerId( report.getCharger().getId() )
                .chargerName( report.getCharger().getName() )
                .build();
    }
}
