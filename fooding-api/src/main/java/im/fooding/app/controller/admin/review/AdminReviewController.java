package im.fooding.app.controller.admin.review;

import im.fooding.app.dto.request.admin.review.AdminCreateReviewRequest;
import im.fooding.app.dto.request.admin.review.AdminReviewRequest;
import im.fooding.app.dto.request.admin.review.AdminUpdateReviewRequest;
import im.fooding.app.dto.response.admin.review.AdminReviewResponse;
import im.fooding.app.service.admin.review.AdminReviewService;
import im.fooding.core.common.ApiResult;
import im.fooding.core.common.PageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping( "/admin/review" )
@Tag( name = "AdminReviewController", description = "Admin들의 Review 작업을 위한 컨트롤러" )
public class AdminReviewController {
    private final AdminReviewService service;

    @GetMapping()
    @Operation( summary = "리뷰 조회" )
    public ApiResult<PageResponse<AdminReviewResponse>> list(
            @ModelAttribute AdminReviewRequest request
    ){
        return ApiResult.ok( service.list( request ) );
    }

    @GetMapping( "/{id}" )
    @Operation( summary = "특정 리뷰 상세 조회" )
    public ApiResult<AdminReviewResponse> findById(
            @PathVariable Long id
    ){
        return ApiResult.ok( service.findById(id) );
    }

    @PostMapping()
    @Operation( summary = "리뷰 생성" )
    public ApiResult<Void> create(
            @RequestBody AdminCreateReviewRequest request
    ){
        service.create( request );
        return ApiResult.ok();
    }

    @PatchMapping("/{id}")
    @Operation( summary = "리뷰 수정" )
    public ApiResult<Void> update(
            @Valid @RequestBody AdminUpdateReviewRequest request,
            @PathVariable Long id
    ){
        service.update( id, request );
        return ApiResult.ok();
    }

    @DeleteMapping( "/{id}" )
    @Operation( summary = "리뷰 삭제" )
    public ApiResult<Void> delete(
        @PathVariable Long id,
        @RequestParam Long deletedBy
    ){
        service.delete( id, deletedBy );
        return ApiResult.ok();
    }
}
