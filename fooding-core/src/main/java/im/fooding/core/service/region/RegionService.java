package im.fooding.core.service.region;

import im.fooding.core.dto.request.region.RegionCreateRequest;
import im.fooding.core.dto.request.region.RegionUpdateRequest;
import im.fooding.core.global.exception.ApiException;
import im.fooding.core.global.exception.ErrorCode;
import im.fooding.core.model.region.Region;
import im.fooding.core.repository.region.RegionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RegionService {

    private final RegionRepository regionRepository;

    @Transactional
    public void create(RegionCreateRequest request) {
        regionRepository.save(request.toRegion());
    }

    public Region get(String id) {
        return regionRepository.findById(id)
                .filter(region -> !region.isDeleted())
                .orElseThrow(() -> new ApiException(ErrorCode.REGION_NOT_FOUND));
    }

    public Page<Region> list(Pageable pageable) {
        return regionRepository.findAllByDeletedFalse(pageable);
    }

    public Page<Region> list(Region parentRegion, Integer level, Pageable pageable) {
        return regionRepository.listActive(parentRegion, level, pageable);
    }

    @Transactional
    public void update(RegionUpdateRequest request) {
        Region region = get(request.id());
        region.update(
                request.parentRegion(),
                request.name(),
                request.timezone(),
                request.countryCode(),
                request.legalCode(),
                request.currency(),
                request.level()
        );
    }

    @Transactional
    public void delete(Long userId, String regionId) {
        Region region = get(regionId);
        region.delete(userId);
    }
}
