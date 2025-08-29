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
        // AdminLeadPageRequest를 CrawlingNaverPageRequest로 변환
        CrawlingNaverPageRequest crawlingReq = CrawlingNaverPageRequest.builder()
                .searchString(request.getSearchString())
                .pageNum(request.getPageNum())
                .pageSize(request.getPageSize())
                .isUploaded(request.getIsUploaded())
                .build();

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
    public Long upload(String naverPlaceId, AdminLeadUploadRequest request) {
        User owner = userService.findById(request.getOwnerId());
        Region region = regionService.findById(request.getRegionId());
        
        return naverPlaceService.upload(naverPlaceId, owner, region);
    }

    /**
     * 리드 상세 조회
     */
    public AdminLeadResponse getLead(String id) {
        CrawlingNaverPlaceResponse naverPlace = crawlingNaverPlaceService.getNaverPlace(id);
        
        return AdminLeadResponse.builder()
                .id(naverPlace.getId())
                .name(naverPlace.getName())
                .phone(naverPlace.getContact())
                .source("NAVER_PLACE")
                .createdAt("") // NaverPlace에는 createdAt 필드가 없으므로 빈 문자열로 설정
                .build();
    }
}
