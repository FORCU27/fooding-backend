package im.fooding.app.controller.ceo.post;

import im.fooding.app.dto.request.ceo.post.CeoPostListRequest;
import im.fooding.app.dto.response.ceo.post.CeoPostResponse;
import im.fooding.app.service.ceo.post.CeoPostService;
import im.fooding.core.common.ApiResult;
import im.fooding.core.common.PageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ceo/posts")
@Tag(name = "CeoPostController", description = "CEO 게시글 컨트롤러")
@Slf4j
public class CeoPostController {
    private final CeoPostService ceoPostService;

    @GetMapping
    @Operation(summary = "게시글 목록 조회")
    public ApiResult<PageResponse<CeoPostResponse>> list(@Valid CeoPostListRequest request) {
        return ApiResult.ok(ceoPostService.list(request));
    }
}
