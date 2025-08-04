package im.fooding.app.service.ceo.region;

import im.fooding.app.dto.request.ceo.region.CeoRegionListRequest;
import im.fooding.app.dto.response.ceo.region.CeoRegionResponse;
import im.fooding.core.common.PageInfo;
import im.fooding.core.common.PageResponse;
import im.fooding.core.model.region.Region;
import im.fooding.core.service.region.RegionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CeoRegionService {

    private final RegionService regionService;

    public PageResponse<CeoRegionResponse> list(@Valid CeoRegionListRequest request) {
        Page<Region> regions = regionService.list(request.getPageable());
        return PageResponse.of(
                regions.stream().map(CeoRegionResponse::from).toList(),
                PageInfo.of(regions)
        );
    }
}
