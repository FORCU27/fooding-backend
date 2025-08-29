package im.fooding.app.controller.crawling.naverplace;

import im.fooding.app.dto.request.crawling.naverplace.CrawlingNaverPlaceCreateRequest;
import im.fooding.app.service.crawling.naverplace.CrawlingNaverPlaceService;
import im.fooding.core.common.ApiResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/crawling/naver-places")
@Tag(name = "CrawlingNaverPlaceController", description = "Crawling NaverPlace Controller")
public class CrawlingNaverPlaceController {

    private final CrawlingNaverPlaceService crawlingNaverPlaceService;

    @PostMapping
    @Operation(summary = "naver-plcae crawling 데이터 저장")
    public ApiResult<String> create(@Valid @RequestBody CrawlingNaverPlaceCreateRequest request) {
        return ApiResult.ok(crawlingNaverPlaceService.create(request));
    }

}
