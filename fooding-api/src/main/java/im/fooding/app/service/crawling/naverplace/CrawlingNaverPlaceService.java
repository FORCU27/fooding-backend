package im.fooding.app.service.crawling.naverplace;

import im.fooding.app.dto.request.crawling.naverplace.CrawlingNaverPlaceCreateRequest;
import im.fooding.app.dto.request.crawling.naverplace.CrawlingNaverPlaceCreateRequest.Menu;
import im.fooding.core.service.naverplace.NaverPlaceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
                request.getName(),
                request.getCategory(),
                request.getAddress(),
                request.getContact(),
                request.getMenus().stream()
                        .map(Menu::toMenu)
                        .toList()
        );
    }
}
