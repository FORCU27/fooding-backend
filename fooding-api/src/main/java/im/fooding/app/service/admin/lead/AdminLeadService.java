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
import im.fooding.core.model.naverplace.NaverPlace;
import im.fooding.core.service.naverplace.NaverPlaceService;
import im.fooding.core.service.region.RegionService;
import im.fooding.core.service.user.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
        // Pageable 객체 생성
        Pageable pageable = PageRequest.of(request.getPageNum(), request.getPageSize());
        
        // naverPlaceService를 직접 사용하여 목록 조회
        Page<NaverPlace> naverPlaces = naverPlaceService.getNaverPlaces(pageable, request.getIsUploaded());

        List<AdminLeadResponse> list = naverPlaces.getContent().stream()
                .map(n -> AdminLeadResponse.builder()
                        .id(n.getId())
                        .name(n.getName())
                        .phone(n.getContact())
                        .source("NAVER_PLACE")
                        .createdAt("")
                        .isUploaded(n.isUploaded())
                        .build())
                .collect(Collectors.toList());

        PageInfo pageInfo = PageInfo.of(naverPlaces);
        return PageResponse.of(list, pageInfo);
    }
    
    /**
     * NaverPlace를 Store로 업로드
     */
    @Transactional
    public Long upload(Long naverPlaceId, AdminLeadUploadRequest request) {
        User owner = userService.findById(request.getOwnerId());
        Region region = regionService.get(request.getRegionId());
        
        return naverPlaceService.upload(naverPlaceId, owner, region);
    }

    /**
     * 리드 상세 조회
     */
    public AdminLeadResponse getLead(Long id) {
        CrawlingNaverPlaceResponse naverPlace = crawlingNaverPlaceService.getNaverPlace(id);
        
        return AdminLeadResponse.builder()
                .id(naverPlace.getId())
                .name(naverPlace.getName())
                .phone(naverPlace.getContact())
                .source("NAVER_PLACE")
                .createdAt("") // NaverPlace에는 createdAt 필드가 없으므로 빈 문자열로 설정
                .isUploaded(naverPlace.getIsUploaded())
                .build();
    }
}
