package im.fooding.app.controller.admin.report;

import im.fooding.app.dto.request.admin.report.AdminCreateReportRequest;
import im.fooding.app.dto.request.admin.report.AdminUpdateReportRequest;
import im.fooding.app.dto.request.admin.report.GetReportRequest;
import im.fooding.app.dto.response.admin.report.GetReportResponse;
import im.fooding.app.service.admin.report.AdminReportService;
import im.fooding.core.common.ApiResult;
import im.fooding.core.common.PageResponse;
import im.fooding.core.model.report.ReportStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/report")
@Tag( name = "AdminReportController", description = "Admin이 사용하는 신고 관련 컨트롤러" )
public class AdminReportController {
    private final AdminReportService service;

    @PostMapping()
    @Operation( summary = "신고 생성 API" )
    public ApiResult<Void> createReport(
            @ModelAttribute AdminCreateReportRequest request
    ){
        service.createReport( request );
        return ApiResult.ok();
    }

    @DeleteMapping("/{reportId}")
    @Operation( summary = "신고 삭제 API" )
    public ApiResult<Void> deleteReport(
            @PathVariable long reportId,
            @RequestParam long deletedBy
    ){
        service.deleteReport( reportId, deletedBy );
        return ApiResult.ok();
    }

    @PatchMapping("/{reportId}")
    @Operation( summary = "신고 수정 API" )
    public ApiResult<Void> updateReport(
            @PathVariable long reportId,
            @RequestBody AdminUpdateReportRequest request
    ){
        service.update( reportId, request );
        return ApiResult.ok();
    }

    @PostMapping("/{id}/reject" )
    @Operation( summary = "신고 반려 API" )
    public ApiResult<Void> rejectReport(
            @PathVariable long id,
            @RequestParam long chargerId
    ){
        service.updateStatus( id, chargerId, ReportStatus.DENY );
        return ApiResult.ok();
    }

    @PostMapping( "/{id}/approve" )
    @Operation( summary = "신고 승인 API" )
    public ApiResult<Void> approveReport(
            @PathVariable long id,
            @RequestParam long chargerId
    ){
        service.updateStatus( id, chargerId, ReportStatus.PROCESS );
        return ApiResult.ok();
    }

    @PostMapping( "/{id}/success" )
    @Operation( summary = "신고 처리 완료 API" )
    public ApiResult<Void> successReport(
            @PathVariable long id,
            @RequestParam long chargerId
    ){
        service.updateStatus( id, chargerId, ReportStatus.SUCCESS );
        return ApiResult.ok();
    }

    @GetMapping()
    @Operation( summary = "신고 목록 조회 API" )
    public ApiResult<PageResponse<GetReportResponse>> list(
            @ModelAttribute GetReportRequest request
    ){
        return ApiResult.ok( service.list( request ) );
    }

    @GetMapping( "/{id}" )
    @Operation( summary = "신고 상세 조회 API" )
    public ApiResult<GetReportResponse> findById(
            @PathVariable long id
    ){
        return ApiResult.ok( service.findById( id ) );
    }
}
