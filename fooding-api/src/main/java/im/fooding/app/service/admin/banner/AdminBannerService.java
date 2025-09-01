package im.fooding.app.service.admin.banner;

import im.fooding.app.dto.request.admin.banner.AdminBannerCreateRequest;
import im.fooding.app.dto.request.admin.banner.AdminBannerUpdateRequest;
import im.fooding.app.dto.request.admin.banner.AdminBannerPageRequest;
import im.fooding.app.dto.response.admin.banner.AdminBannerResponse;
import im.fooding.core.common.PageInfo;
import im.fooding.core.common.PageResponse;
import im.fooding.core.model.banner.Banner;
import im.fooding.core.service.banner.BannerService;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminBannerService {

    private final BannerService bannerService;

    @Transactional
    public String createBanner(AdminBannerCreateRequest request) {
         return bannerService.createBanner(
                 request.getName(),
                 request.getDescription(),
                 request.getActive(),
                 request.getPriority(),
                 request.getLink(),
                 request.getLinkType()
         ).toString();
    }

    public AdminBannerResponse getBanner(String id) {
        return AdminBannerResponse.from(bannerService.getBanner(new ObjectId(id)));
    }

    public PageResponse<AdminBannerResponse> getBanners(AdminBannerPageRequest request) {
        Page<Banner> banners = bannerService.getBanners(request.getPageable());
        return PageResponse.of(
                banners.map(AdminBannerResponse::from).toList(),
                PageInfo.of(banners)
        );
    }

    @Transactional
    public void updateBanner(String id, AdminBannerUpdateRequest request) {
        bannerService.updateBanner(
                new ObjectId(id),
                request.getName(),
                request.getDescription(),
                request.getActive(),
                request.getPriority(),
                request.getLink(),
                request.getLinkType()
        );
    }

    @Transactional
    public void deleteBanner(String id, long deletedBy) {
        bannerService.deleteBanner(new ObjectId(id), deletedBy);
    }
}
