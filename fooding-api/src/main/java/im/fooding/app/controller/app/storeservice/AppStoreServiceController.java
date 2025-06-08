package im.fooding.app.controller.app.storeservice;

import im.fooding.app.dto.response.admin.service.StoreServiceResponse;
import im.fooding.app.service.admin.service.StoreServiceApplicationService;
import im.fooding.app.service.app.store.AppStoreServiceService;
import im.fooding.core.common.ApiResult;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping( "/app/store-service" )
public class AppStoreServiceController {
    private final AppStoreServiceService service;

    @GetMapping("/{storeId}" )
    @Operation( summary = "해당 가게에 대한 가입 스토어 서비스 목록 조회" )
    public ApiResult<List<StoreServiceResponse>> getSignedStoreService(
            @PathVariable Long storeId,
            @RequestParam Long userId
    ){
        List<StoreServiceResponse> response = service.list( storeId, userId );
        if( response == null ) return ApiResult.ok();
        return ApiResult.ok( response );
    }
}
