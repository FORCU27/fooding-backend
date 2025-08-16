package im.fooding.app.controller.crawling.catchtable;

import im.fooding.app.dto.request.crawling.catchtable.CatchTableMenuRequest;
import im.fooding.app.dto.request.crawling.catchtable.CatchTableStoreRequest;
import im.fooding.app.dto.response.crawling.GetStoreDocumentRequest;
import im.fooding.app.dto.response.crawling.GetStoreDocumentResponse;
import im.fooding.app.service.crawling.catchtable.CrawlingCatchtableService;
import im.fooding.core.common.ApiResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/crawling/catchtable")
@Tag(name = "CrawlingCatchtableController", description = "캐치테이블 스토어 정보 크롤링 컨트롤러")
@Slf4j
public class CrawlingCatchtableController {
    private final CrawlingCatchtableService service;

    @PostMapping("/shops")
    @Operation( description = "스토어 정보 저장" )
    public ApiResult<String> saveStore(
            @RequestBody CatchTableStoreRequest request
    ) {
        String storeDocumentId = service.saveDocuments( request );
        return ApiResult.ok( storeDocumentId );
    }

    @PostMapping("/menus")
    @Operation( description = "스토어 메뉴 저장" )
    public ApiResult<Void> saveStoreMenu(
            @RequestBody CatchTableMenuRequest request
    ){
        service.saveMenuDocument( request );
        return ApiResult.ok();
    }

    @GetMapping( "/shops" )
    @Operation( description = "크롤링 목록 확인" )
    public ApiResult<Page<GetStoreDocumentResponse>> showCrawlingStores(
            @RequestBody GetStoreDocumentRequest request
    ) {
        return ApiResult.ok( service.getDocuments( request.getPageable() ) );
    }

    @GetMapping( "/shops/{id}" )
    @Operation( description = "특정 ID의 도큐먼트 확인" )
    public ApiResult<GetStoreDocumentResponse> getDocumentById(
            @PathVariable String id
    ){
        return ApiResult.ok( service.findDocumentById( id ) );
    }

    @PostMapping( "/shops/{id}/upload" )
    @Operation( description = "id에 맞는 도큐먼트 저장" )
    public ApiResult<Void> saveCrawlingStores(
            @PathVariable String id
    ) {
        service.create( id );
        return ApiResult.ok();
    }
}
