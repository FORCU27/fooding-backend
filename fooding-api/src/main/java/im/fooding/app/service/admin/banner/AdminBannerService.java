package im.fooding.app.service.admin.banner;

import im.fooding.app.dto.request.admin.banner.AdminBannerCreateRequest;
import im.fooding.app.dto.request.admin.banner.AdminBannerUpdateRequest;
import im.fooding.app.dto.request.admin.banner.AdminBannerPageRequest;
import im.fooding.app.dto.response.admin.banner.AdminBannerResponse;
import im.fooding.app.service.file.FileUploadService;
import im.fooding.core.common.PageInfo;
import im.fooding.core.common.PageResponse;
import im.fooding.core.model.banner.Banner;
import im.fooding.core.model.file.File;
import im.fooding.core.repository.banner.BannerFilter;
import im.fooding.core.service.banner.BannerService;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminBannerService {

    private final BannerService bannerService;
    private final FileUploadService fileUploadService;

    @Transactional
    public String create(AdminBannerCreateRequest request) {
        String imageUrl = null;
        if (StringUtils.hasText(request.getImageId())) {
            File file = fileUploadService.commit(request.getImageId());
            imageUrl = file.getUrl();
        }

        String service = StringUtils.hasText(request.getService()) ? request.getService() : null;
        String placement = StringUtils.hasText(request.getPlacement()) ? request.getPlacement() : null;

        return bannerService.create(
                request.getName(),
                request.getDescription(),
                request.getActive(),
                request.getPriority(),
                request.getLink(),
                request.getLinkType(),
                imageUrl,
                service,
                placement,
                request.getParameters()
        ).toString();
    }

    public AdminBannerResponse get(String id) {
        return AdminBannerResponse.from(bannerService.get(new ObjectId(id)));
    }

    public PageResponse<AdminBannerResponse> list(AdminBannerPageRequest request) {
        BannerFilter filter = BannerFilter.builder()
                .active(request.getActive())
                .service(request.getService())
                .placement(request.getPlacement())
                .searchString(request.getSearchString())
                .build();

        Page<Banner> banners = bannerService.list(filter, request.getPageable());
        return PageResponse.of(
                banners.map(AdminBannerResponse::from).toList(),
                PageInfo.of(banners)
        );
    }

    @Transactional
    public void update(String id, AdminBannerUpdateRequest request) {
        Banner banner = bannerService.get(new ObjectId(id));

        String imageUrl = banner.getImageUrl();
        if (StringUtils.hasText(request.getImageId())) {
            File file = fileUploadService.commit(request.getImageId());
            imageUrl = file.getUrl();
        }

        String service = request.getService() != null
                ? (StringUtils.hasText(request.getService()) ? request.getService() : null)
                : banner.getService();
        String placement = request.getPlacement() != null
                ? (StringUtils.hasText(request.getPlacement()) ? request.getPlacement() : null)
                : banner.getPlacement();
        var parameters = request.getParameters() != null ? request.getParameters() : banner.getParameters();

        bannerService.update(
                new ObjectId(id),
                request.getName(),
                request.getDescription(),
                request.getActive(),
                request.getPriority(),
                request.getLink(),
                request.getLinkType(),
                imageUrl,
                service,
                placement,
                parameters
        );
    }

    @Transactional
    public void delete(String id, long deletedBy) {
        bannerService.delete(new ObjectId(id), deletedBy);
    }
}
