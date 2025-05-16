package im.fooding.app.controller.admin.post;

import im.fooding.app.dto.request.admin.post.AdminCreatePostRequest;
import im.fooding.app.dto.request.admin.post.AdminUpdatePostRequest;
import im.fooding.app.dto.response.admin.post.AdminPostResponse;
import im.fooding.app.service.admin.post.AdminPostService;
import im.fooding.core.common.ApiResult;
import im.fooding.core.global.UserInfo;
import im.fooding.core.model.post.PostType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/posts")
@Tag(name = "AdminPostController", description = "관리자 게시글 컨트롤러")
@Slf4j
public class AdminPostController {
    private final AdminPostService adminPostService;

    @GetMapping
    @Operation(summary = "게시글 목록 조회")
    public ApiResult<List<AdminPostResponse>> list(@RequestParam PostType type) {
    List<AdminPostResponse> posts = adminPostService.list(type);
    return ApiResult.ok(posts);
    }

    @GetMapping("/{id}")
    @Operation(summary = "게시글 상세 조회")
    public ApiResult<AdminPostResponse> retrieve(@PathVariable("id")  Long postId) {
      AdminPostResponse response = adminPostService.retrieve(postId);
      return ApiResult.ok(response);
    }

    @PostMapping
    @Operation(summary = "게시글 생성")
    public ApiResult<Long> create(@RequestBody @Valid AdminCreatePostRequest request) {
      Long id = adminPostService.create(request);
      return ApiResult.ok(id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "게시글 수정")
    public ApiResult<Void> update(@PathVariable("id") Long postId, @RequestBody @Valid AdminUpdatePostRequest request) {
      adminPostService.update(postId, request);
      return ApiResult.ok();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "게시글 삭제")
    public ApiResult<Void> delete(@PathVariable("id") Long postId, @AuthenticationPrincipal UserInfo userInfo) {
      adminPostService.delete(postId, userInfo.getId());
      return ApiResult.ok();
    }
}
