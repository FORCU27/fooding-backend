package im.fooding.core.repository.banner;

import im.fooding.core.model.banner.Banner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QBannerRepository {

    Page<Banner> list(Pageable pageable);

    Page<Banner> list(BannerFilter filter, Pageable pageable);
}
