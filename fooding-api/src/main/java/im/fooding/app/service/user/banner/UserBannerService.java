package im.fooding.app.service.user.banner;

import im.fooding.app.dto.request.user.banner.UserBannerPageRequest;
import im.fooding.app.dto.response.user.banner.UserBannerResponse;
import im.fooding.core.common.PageInfo;
import im.fooding.core.common.PageResponse;
import im.fooding.core.model.banner.Banner;
import im.fooding.core.repository.banner.BannerFilter;
import im.fooding.core.service.banner.BannerService;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.cache.annotation.Cacheable;
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

    @Cacheable(
            value = "BannerList",
            key = "'bannerList_' + (#request.placement != null ? #request.placement : 'default')",
            cacheManager = "contentCacheManager",
            sync = true // Cache Stampede 방지 -> 단일 스레드로 직렬화 해서 여러 요청이 동시에 들어와도 DB 한번만 조회
    )
    public PageResponse<UserBannerResponse> list(UserBannerPageRequest request) {
        BannerFilter filter = BannerFilter.builder()
                .active(true)
                .service("USER")
                .placement(request.getPlacement())
                .searchString(request.getSearchString())
                .build();

        Page<Banner> banners = bannerService.list(filter, request.getPageable());
        return PageResponse.of(
                banners.map(UserBannerResponse::from).toList(),
                PageInfo.of(banners)
        );
    }
}
