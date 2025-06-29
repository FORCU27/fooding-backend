package im.fooding.app.service.admin.region;

import im.fooding.app.dto.request.admin.region.AdminRegionCreateRequest;
import im.fooding.app.dto.request.admin.region.AdminRegionListRequest;
import im.fooding.app.dto.response.admin.region.AdminRegionResponse;
import im.fooding.app.dto.request.admin.region.AdminRegionUpdateRequest;
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
public class AdminRegionService {

    private final RegionService regionService;

    @Transactional
    public void create(AdminRegionCreateRequest request) {
        Region parentRegion = null;
        if (request.getParentRegionId() != null) {
            parentRegion = regionService.get(request.getParentRegionId());
        }

        regionService.create(request.toRegionCreateRequest(parentRegion));
    }

    public AdminRegionResponse get(String id) {
        return AdminRegionResponse.from(regionService.get(id));
    }

    public PageResponse<AdminRegionResponse> list(@Valid AdminRegionListRequest request) {
        Page<Region> regions = regionService.list(request.getPageable());
        return PageResponse.of(
                regions.stream().map(AdminRegionResponse::from).toList(),
                PageInfo.of(regions)
        );
    }

    @Transactional
    public void update(String id, @Valid AdminRegionUpdateRequest request) {
        Region parentRegion = null;
        if (request.getParentRegionId() != null) {
            parentRegion = regionService.get(request.getParentRegionId());
        }

        regionService.update(request.toRegionUpdateRequest(id, parentRegion));
    }

    public void delete(String id, long deletedBy) {
        regionService.delete(deletedBy, id);
    }
}
