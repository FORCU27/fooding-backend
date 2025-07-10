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
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
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

    @Transactional
    public void batchCreate(AdminRegionBatchCreateRequest request) {
        if (request.getData().size() > REGION_BATCH_INSERT_LIMIT) {
            throw new ApiException(ErrorCode.REGION_OVER_BATCH_INSERT_LIMIT);
        }

        List<String> parentIds = request.getData().stream()
                .map(AdminRegionCreateRequest::getParentRegionId)
                .toList();

        Map<String, Region> parentRegions = regionService.listById(parentIds)
                .stream()
                .collect(Collectors.toMap(Region::getId, Function.identity()));

        List<RegionCreateRequest> regionCreateRequests = request.getData().stream()
                .map(data ->
                        data.toRegionCreateRequest(parentRegions.get(data.getParentRegionId()))
                )
                .toList();

        regionService.batchCreate(regionCreateRequests);
    }
}
