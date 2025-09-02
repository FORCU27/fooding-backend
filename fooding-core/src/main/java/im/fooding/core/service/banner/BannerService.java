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
    public ObjectId create(
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

    public Banner get(ObjectId id) {
        return bannerRepository.findById(id)
                .filter(banner -> !banner.isDeleted())
                .orElseThrow(() -> new ApiException(ErrorCode.BANNER_NOT_FOUND));
    }

    public Banner getActive(ObjectId id) {
        return Optional.of(get(id))
                .filter(Banner::isActive)
                .orElseThrow(() -> new ApiException(ErrorCode.BANNER_INACTIVE));
    }

    public Page<Banner> list(Pageable pageable) {
        return bannerRepository.findAllByDeletedFalse(pageable);
    }

    public Page<Banner> listActive(Pageable pageable) {
        return bannerRepository.findAllByActiveTrueAndDeletedFalse(pageable);
    }

    @Transactional
    public void update(
            ObjectId id,
            String name,
            String description,
            boolean active,
            int priority,
            String link,
            Banner.LinkType linkType
    ) {
        Banner targetBanner = get(id);

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
    public void delete(ObjectId id, long deletedBy) {
        Banner targetBanner = get(id);

        targetBanner.delete(deletedBy);

        bannerRepository.save(targetBanner);
    }
}
