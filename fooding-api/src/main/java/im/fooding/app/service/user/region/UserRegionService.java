package im.fooding.app.service.user.region;

import im.fooding.app.dto.request.user.region.UserRegionListRequest;
import im.fooding.app.dto.response.user.region.UserRegionResponse;
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
public class UserRegionService {

    private final RegionService regionService;

    public PageResponse<UserRegionResponse> list(@Valid UserRegionListRequest request) {
        Region parentRegion = null;
        if (request.getParentRegionId() != null) {
            parentRegion = regionService.get(request.getParentRegionId());
        }

        Page<Region> regions = regionService.list(parentRegion, request.getLevel(), request.getSearchString(), request.getPageable());
        return PageResponse.of(
                regions.stream().map(UserRegionResponse::from).toList(),
                PageInfo.of(regions)
        );
    }
}
