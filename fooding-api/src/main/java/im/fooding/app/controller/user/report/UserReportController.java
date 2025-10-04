package im.fooding.app.controller.user.report;

import im.fooding.app.dto.request.user.report.CreateReportRequest;
import im.fooding.app.service.user.report.UserReportService;
import im.fooding.core.common.ApiResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user/report")
@Tag( name = "UserReportController", description = "User들의 신고 관련 컨트롤러" )
public class UserReportController {
    private final UserReportService service;

    @PostMapping()
    @Operation( summary = "User가 신고하는 API" )
    public ApiResult<Void> report(
            @RequestBody CreateReportRequest request
    ){
        service.createReport(request.getReferenceId(), request );
        return ApiResult.ok();
    }
}
