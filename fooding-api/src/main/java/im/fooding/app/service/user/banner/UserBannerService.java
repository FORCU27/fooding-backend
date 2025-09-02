package im.fooding.app.service.user.banner;

import im.fooding.app.dto.request.user.banner.UserBannerPageRequest;
import im.fooding.app.dto.response.user.banner.UserBannerResponse;
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
public class UserBannerService {

    private final BannerService bannerService;

    public UserBannerResponse get(String id) {
        return UserBannerResponse.from(bannerService.getActive(new ObjectId(id)));
    }

    public PageResponse<UserBannerResponse> list(UserBannerPageRequest request) {
        Page<Banner> banners = bannerService.listActive(request.getPageable());
        return PageResponse.of(
                banners.map(UserBannerResponse::from).toList(),
                PageInfo.of(banners)
        );
    }
}
