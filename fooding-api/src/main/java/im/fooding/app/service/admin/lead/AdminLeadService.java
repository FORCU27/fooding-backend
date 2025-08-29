package im.fooding.app.service.admin.lead;

import im.fooding.app.dto.request.admin.lead.AdminLeadPageRequest;
import im.fooding.app.dto.request.crawling.naverplace.CrawlingNaverPageRequest;
import im.fooding.app.dto.response.admin.lead.AdminLeadResponse;
import im.fooding.app.dto.response.crawling.naverplace.CrawlingNaverPlaceResponse;
import im.fooding.app.service.crawling.naverplace.CrawlingNaverPlaceService;
import im.fooding.core.common.PageInfo;
import im.fooding.core.common.PageResponse;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminLeadService {

    private final CrawlingNaverPlaceService crawlingNaverPlaceService;

    public PageResponse<AdminLeadResponse> list(AdminLeadPageRequest request) {
        CrawlingNaverPageRequest crawlingReq = new CrawlingNaverPageRequest();
        crawlingReq.setPageNum(request.getPageNum());
        crawlingReq.setPageSize(request.getPageSize());
        crawlingReq.setSearchString(request.getSearchString());

        PageResponse<CrawlingNaverPlaceResponse> naverPage = crawlingNaverPlaceService.getNaverPlaces(crawlingReq);

        List<AdminLeadResponse> list = naverPage.getList().stream()
                .map(n -> AdminLeadResponse.builder()
                        .id(n.getId())
                        .name(n.getName())
                        .phone(n.getContact())
                        .source("NAVER_PLACE")
                        .createdAt("")
                        .build())
                .collect(Collectors.toList());

        PageInfo pageInfo = naverPage.getPageInfo();
        return PageResponse.of(list, pageInfo);
    }
}
