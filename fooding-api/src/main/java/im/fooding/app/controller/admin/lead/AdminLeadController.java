package im.fooding.app.controller.admin.lead;

import im.fooding.app.dto.request.admin.lead.AdminLeadPageRequest;
import im.fooding.app.dto.response.admin.lead.AdminLeadResponse;
import im.fooding.app.service.admin.lead.AdminLeadService;
import im.fooding.core.common.ApiResult;
import im.fooding.core.common.PageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/leads")
@Tag(name = "AdminLeadController", description = "관리자 리드 컨트롤러")
public class AdminLeadController {

    private final AdminLeadService adminLeadService;

    @GetMapping
    @Operation(summary = "리드 전체 조회")
    public ApiResult<PageResponse<AdminLeadResponse>> list(@Valid AdminLeadPageRequest request) {
        return ApiResult.ok(adminLeadService.list(request));
    }
}

