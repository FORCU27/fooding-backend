package im.fooding.core.repository.banner;

import com.querydsl.core.types.dsl.BooleanExpression;
import im.fooding.core.model.banner.Banner;
import im.fooding.core.model.banner.QBanner;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.support.SpringDataMongodbQuery;
import org.springframework.util.StringUtils;

@RequiredArgsConstructor
public class QBannerRepositoryImpl implements QBannerRepository {

    private final MongoTemplate mongoTemplate;

    public Page<Banner> list(Pageable pageable) {
        return list(BannerFilter.non(), pageable);
    }

    @Override
    public Page<Banner> list(BannerFilter filter, Pageable pageable) {
        QBanner banner = QBanner.banner;

        // 조건 생성
        BooleanExpression condition = banner.deleted.isFalse();
        if (filter.active() != null) {
            condition = condition.and(banner.active.eq(filter.active()));
        }
        if (StringUtils.hasText(filter.service())) {
            condition = condition.and(banner.service.equalsIgnoreCase(filter.service()));
        }
        if (StringUtils.hasText(filter.placement())) {
            condition = condition.and(banner.placement.equalsIgnoreCase(filter.placement()));
        }
        if (StringUtils.hasText(filter.searchString())) {
            String keyword = filter.searchString();
            condition = condition.and(
                    banner.name.containsIgnoreCase(keyword)
                            .or(banner.description.containsIgnoreCase(keyword))
                            .or(banner.service.containsIgnoreCase(keyword))
                            .or(banner.placement.containsIgnoreCase(keyword))
            );
        }

        SpringDataMongodbQuery<Banner> query = new SpringDataMongodbQuery<>(mongoTemplate, Banner.class);
        query = query.where(condition);

        long total = query.fetchCount();
        query = query.offset(pageable.getOffset())
                .limit(pageable.getPageSize());

        // 결과 조회
        List<Banner> content = query.fetch();

        return new PageImpl<>(content, pageable, total);
    }
}
