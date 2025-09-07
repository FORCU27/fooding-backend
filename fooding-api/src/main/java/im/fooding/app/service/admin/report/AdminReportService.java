package im.fooding.app.service.admin.report;

import im.fooding.app.dto.request.admin.report.AdminCreateReportRequest;
import im.fooding.app.dto.request.admin.report.AdminUpdateReportRequest;
import im.fooding.app.dto.request.admin.report.GetReportRequest;
import im.fooding.app.dto.response.admin.report.GetReportResponse;
import im.fooding.core.common.PageInfo;
import im.fooding.core.common.PageResponse;
import im.fooding.core.model.report.Report;
import im.fooding.core.model.report.ReportStatus;
import im.fooding.core.model.user.User;
import im.fooding.core.service.report.ReportService;
import im.fooding.core.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminReportService {
    private final ReportService reportService;
    private final UserService userService;

    public void createReport(AdminCreateReportRequest request){
        User reporter = userService.findById( request.getReporterId() );
        reportService.create(
                request.getTargetType(),
                request.getReferenceId(),
                request.getDescription(),
                request.getMemo(),
                reporter
        );
    }

    public void deleteReport(long id, long deletedBy){
        reportService.delete( id, deletedBy );
    }

    @Transactional
    public void update( long reportId, AdminUpdateReportRequest request){
        if( request.getMemo() != null ) updateMemo( reportId, request.getMemo() );
        if( request.getStatus() != null ) updateStatus( reportId, request.getChargerId(), request.getStatus() );
    }
    private void updateMemo( long reportId, String memo ){reportService.updateMemo( reportId, memo );}
    @Transactional
    public void updateStatus(long reportId, long chargerId, ReportStatus status){
        // Set Charger
        User charger = userService.findById( chargerId );
        reportService.updateCharger( reportId, charger );

        reportService.updateStatus( reportId, status );

    }

    public PageResponse<GetReportResponse> list(GetReportRequest request){
        Page<Report> page = reportService.list(
                "", request.getStatus(), request.getTargetType(),
                request.getReferenceId(), request.getReporterId(), request.getChargerId(),
                request.getPageable()
        );
        return PageResponse.of(
                page.map( GetReportResponse::of ).toList(),
                PageInfo.of( page )
        );
    }

    public GetReportResponse findById( long id ){return GetReportResponse.of( reportService.findById( id ) );}
}
