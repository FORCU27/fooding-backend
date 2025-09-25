package im.fooding.app.controller.user.recommend;

import im.fooding.app.dto.request.user.recommend.UserRecommendKeywordRequest;
import im.fooding.app.service.user.recommend.UserRecommendService;
import im.fooding.core.common.ApiResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/user/recommend-keywords")
@Tag(name = "UserRecommendKeywordController", description = "유저 검색어 추천 컨트롤러")
public class UserRecommendKeywordController {
    private final UserRecommendService service;

    @GetMapping
    @Operation(summary = "검색어 추천 목록 조회")
    public ApiResult<List<String>> list(@Valid UserRecommendKeywordRequest request) {
        return ApiResult.ok(service.list(request));
    }
}
