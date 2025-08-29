package im.fooding.app.service.crawling.naverplace;

import im.fooding.app.dto.request.crawling.naverplace.CrawlingNaverPageRequest;
import im.fooding.app.dto.request.crawling.naverplace.CrawlingNaverPlaceCreateRequest;
import im.fooding.app.dto.request.crawling.naverplace.CrawlingNaverPlaceCreateRequest.Menu;
import im.fooding.app.dto.response.crawling.naverplace.CrawlingNaverPlaceResponse;
import im.fooding.core.common.PageInfo;
import im.fooding.core.common.PageResponse;
import im.fooding.core.service.naverplace.NaverPlaceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CrawlingNaverPlaceService {

    private final NaverPlaceService naverPlaceService;

    @Transactional
    public String create(@Valid CrawlingNaverPlaceCreateRequest request) {
        return naverPlaceService.create(
                request.getId(),
                request.getName(),
                request.getCategory(),
                request.getAddress(),
                request.getContact(),
                request.getMenus().stream()
                        .map(Menu::toMenu)
                        .toList()
        );
    }

    public PageResponse<CrawlingNaverPlaceResponse> getNaverPlaces(CrawlingNaverPageRequest request) {
        Page<CrawlingNaverPlaceResponse> naverPlaces = naverPlaceService.getNaverPlaces(request.getPageable(), request.getIsUploaded())
                .map(CrawlingNaverPlaceResponse::from);
        return PageResponse.of(naverPlaces.toList(), PageInfo.of(naverPlaces));
    }

    /**
     * 단일 NaverPlace 조회
     */
    public CrawlingNaverPlaceResponse getNaverPlace(String id) {
        return naverPlaceService.findById(id)
                .map(CrawlingNaverPlaceResponse::from)
                .orElseThrow(() -> new RuntimeException("NaverPlace를 찾을 수 없습니다: " + id));
    }
}
