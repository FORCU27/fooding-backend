package im.fooding.app.service.admin.region;

import im.fooding.app.dto.request.admin.region.AdminRegionBatchCreateRequest;
import im.fooding.app.dto.request.admin.region.AdminRegionCreateRequest;
import im.fooding.app.dto.request.admin.region.AdminRegionListRequest;
import im.fooding.app.dto.response.admin.region.AdminRegionResponse;
import im.fooding.app.dto.request.admin.region.AdminRegionUpdateRequest;
import im.fooding.core.common.PageInfo;
import im.fooding.core.common.PageResponse;
import im.fooding.core.dto.request.region.RegionCreateRequest;
import im.fooding.core.global.exception.ApiException;
import im.fooding.core.global.exception.ErrorCode;
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

    private static final int REGION_BATCH_INSERT_LIMIT = 100;

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
        Region parentRegion = null;
        if (request.getParentRegionId() != null) {
            parentRegion = regionService.get(request.getParentRegionId());
        }

        Page<Region> regions = regionService.list(parentRegion, null, request.getSearchString(), request.getPageable());
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

    @Transactional
    public void batchCreate(AdminRegionBatchCreateRequest request) {
        if (request.getData().size() > REGION_BATCH_INSERT_LIMIT) {
            throw new ApiException(ErrorCode.REGION_OVER_BATCH_INSERT_LIMIT);
        }

        request.getData()
                .forEach(adminRegionCreateRequest -> {
                    Region parentRegion = getParentRegion(adminRegionCreateRequest);
                    RegionCreateRequest regionCreateRequest = adminRegionCreateRequest.toRegionCreateRequest(parentRegion);
                    regionService.create(regionCreateRequest);
                });
    }

    private Region getParentRegion(AdminRegionCreateRequest adminRegionCreateRequest) {
        String parentRegionId = adminRegionCreateRequest.getParentRegionId();

        // hack: KR-36110(세종특별자치시)
        if (parentRegionId == null || parentRegionId.equals("KR-36")) {
            return null;
        }

        return regionService.get(parentRegionId);
    }
}
