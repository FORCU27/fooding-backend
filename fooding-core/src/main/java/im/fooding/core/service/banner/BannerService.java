package im.fooding.core.service.banner;

import im.fooding.core.global.exception.ApiException;
import im.fooding.core.global.exception.ErrorCode;
import im.fooding.core.model.banner.Banner;
import im.fooding.core.repository.banner.BannerRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BannerService {

    private final BannerRepository bannerRepository;

    @Transactional
    public ObjectId createBanner(
            String name,
            String description,
            boolean active,
            int priority,
            String link,
            Banner.LinkType linkType
    ) {
        Banner newBanner = Banner.builder()
                .name(name)
                .description(description)
                .active(active)
                .priority(priority)
                .link(link)
                .linkType(linkType)
                .build();

        Banner savedBanner = bannerRepository.save(newBanner);

        return savedBanner.getId();
    }

    public Banner getBanner(ObjectId id) {
        return bannerRepository.findById(id)
                .filter(banner -> !banner.isDeleted())
                .orElseThrow(() -> new ApiException(ErrorCode.BANNER_NOT_FOUND));
    }

    public Banner getActiveBanner(ObjectId id) {
        return Optional.of(getBanner(id))
                .filter(Banner::isActive)
                .orElseThrow(() -> new ApiException(ErrorCode.BANNER_INACTIVE));
    }

    public Page<Banner> getBanners(Pageable pageable) {
        return bannerRepository.findAllByDeletedFalse(pageable);
    }

    public Page<Banner> getActiveBanners(Pageable pageable) {
        return bannerRepository.findAllByActiveTrueAndDeletedFalse(pageable);
    }

    @Transactional
    public void updateBanner(
            ObjectId id,
            String name,
            String description,
            boolean active,
            int priority,
            String link,
            Banner.LinkType linkType
    ) {
        Banner targetBanner = getBanner(id);

        targetBanner.update(
                name,
                description,
                active,
                priority,
                link,
                linkType
        );

        bannerRepository.save(targetBanner);
    }

    @Transactional
    public void deleteBanner(ObjectId id, long deletedBy) {
        Banner targetBanner = getBanner(id);

        targetBanner.delete(deletedBy);

        bannerRepository.save(targetBanner);
    }
}
