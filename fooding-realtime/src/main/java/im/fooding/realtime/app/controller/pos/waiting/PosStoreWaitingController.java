package im.fooding.realtime.app.controller.pos.waiting;

import im.fooding.core.common.ApiResult;
import im.fooding.core.common.PageInfo;
import im.fooding.core.common.PageResponse;
import im.fooding.realtime.app.dto.request.pos.waiting.PosStoreWaitingListRequest;
import im.fooding.realtime.app.dto.response.pos.waiting.PosStoreWaitingResponse;
import im.fooding.realtime.app.facade.pos.waiting.PosStoreWaitingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v2/pos/stores/{storeId}/waitings")
public class PosStoreWaitingController {

    private final PosStoreWaitingService posStoreWaitingService;

    @GetMapping
    @Operation(summary = "웨이팅 목록 조회")
    Mono<ApiResult<PageResponse<PosStoreWaitingResponse>>> list(
            @Parameter(description = "가게 ID", example = "1")
            @PathVariable long storeId,

            @ModelAttribute PosStoreWaitingListRequest request
    ) {
        return posStoreWaitingService.list(storeId, request)
                .map(pageResult -> {
                    PageInfo pageInfo = PageInfo.builder()
                            .pageNum(pageResult.getPageable().getPageNumber())
                            .pageSize(pageResult.getPageable().getPageSize())
                            .totalCount(pageResult.getTotalElements())
                            .totalPages(pageResult.getTotalPages())
                            .build();

                    PageResponse<PosStoreWaitingResponse> pageResponse =
                            PageResponse.of(pageResult.get().toList(), pageInfo);

                    return ApiResult.ok(pageResponse);
                });
    }
}
