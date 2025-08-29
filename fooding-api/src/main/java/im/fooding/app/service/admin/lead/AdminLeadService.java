package im.fooding.app.service.admin.lead;

import im.fooding.app.dto.request.admin.lead.AdminLeadPageRequest;
import im.fooding.app.dto.request.admin.lead.AdminLeadUploadRequest;
import im.fooding.app.dto.request.crawling.naverplace.CrawlingNaverPageRequest;
import im.fooding.app.dto.response.admin.lead.AdminLeadResponse;
import im.fooding.app.dto.response.crawling.naverplace.CrawlingNaverPlaceResponse;
import im.fooding.app.service.crawling.naverplace.CrawlingNaverPlaceService;
import im.fooding.core.common.PageInfo;
import im.fooding.core.common.PageResponse;
import im.fooding.core.model.region.Region;
import im.fooding.core.model.user.User;
import im.fooding.core.service.naverplace.NaverPlaceService;
import im.fooding.core.service.region.RegionService;
import im.fooding.core.service.user.UserService;
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
    private final NaverPlaceService naverPlaceService;
    private final UserService userService;
    private final RegionService regionService;

    public PageResponse<AdminLeadResponse> list(AdminLeadPageRequest request) {
        CrawlingNaverPageRequest crawlingReq = new CrawlingNaverPageRequest(
                request.getSearchString(),
                request.getPageNum(),
                request.getPageSize(),
                request.getIsUploaded()
        );

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
    
    /**
     * NaverPlace를 Store로 업로드
     */
    @Transactional
    public Long upload(Long naverPlaceId, AdminLeadUploadRequest request) {
        User owner = userService.findById(request.getOwnerId());
        Region region = regionService.findById(request.getRegionId());
        
        return naverPlaceService.upload(naverPlaceId, owner, region);
    }
}
