package im.fooding.app.controller.admin.service;

import im.fooding.app.dto.request.admin.service.CreateStoreServiceRequest;
import im.fooding.app.dto.request.admin.service.RetrieveStoreServiceRequest;
import im.fooding.app.dto.response.admin.service.StoreServiceResponse;
import im.fooding.app.service.admin.service.StoreServiceApplicationService;
import im.fooding.core.common.ApiResult;
import im.fooding.core.common.PageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/service" )
@Tag(name="Admin StoreService Controller", description = "관리자 서비스 관리 컨트롤러")
public class AdminStoreServiceController {
    private final StoreServiceApplicationService service;

    @PostMapping()
    @Operation(summary = "스토어 서비스 생성")
    public ApiResult<Void> create(
            @RequestBody CreateStoreServiceRequest request
    ){
        service.create( request );
        return ApiResult.ok();
    }

    @GetMapping()
    @Operation(summary = "모든 서비스 가입 내역 조회")
    public ApiResult<PageResponse<StoreServiceResponse>> list(
            @RequestBody RetrieveStoreServiceRequest request
    ){
        return ApiResult.ok( service.list( request ) );
    }

    @GetMapping("/{id}")
    @Operation(summary = "특정 스토어 서비스 상세 조회")
    public ApiResult<StoreServiceResponse> findById(
            @PathVariable Long id
    ){
        return ApiResult.ok( service.findById( id ) );
    }

    @PostMapping("/active/{id}")
    @Operation(summary = "스토어 서비스 활성화")
    public ApiResult<Void> activate(
            @PathVariable Long id
    ){
        service.active( id );
        return ApiResult.ok();
    }

    @PostMapping("/inactive/{id}")
    @Operation(summary = "스토어 서비스 비활성화")
    public ApiResult<Void> inactivate(
            @PathVariable Long id
    ){
        service.inactive( id );
        return ApiResult.ok();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "스토어 서비스 삭제")
    public ApiResult<Void> delete(
            @PathVariable Long id,
            @RequestParam Long deletedBy
    ){
        service.delete( id, deletedBy );
        return ApiResult.ok();
    }
}
