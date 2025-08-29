package im.fooding.core.repository.region;

import static im.fooding.core.model.region.QRegion.region;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import im.fooding.core.model.region.Region;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

@RequiredArgsConstructor
public class QRegionRepositoryImpl implements QRegionRepository {

    private final JPAQueryFactory query;

    @Override
    public Page<Region> listActive(Region parentRegion, Integer level, String searchString, Pageable pageable) {
        BooleanBuilder conditions = new BooleanBuilder();
        conditions.and(parentRegionEq(parentRegion));
        conditions.and(levelEq(level));
        conditions.and(nameContains(searchString));
        conditions.and(region.deleted.isFalse());

        List<Region> results = query
                .select(region)
                .from(region)
                .where(conditions)
                .orderBy(region.id.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = query
                .select(region.count())
                .from(region)
                .where(conditions);

        return PageableExecutionUtils.getPage(results, pageable, countQuery::fetchCount);
    }

    private static BooleanExpression parentRegionEq(Region parentRegion) {
        if (parentRegion == null) {
            return null;
        }
        return region.parentRegion.eq(parentRegion);
    }

    private static BooleanExpression levelEq(Integer level) {
        if (level == null) {
            return null;
        }
        return region.level.eq(level);
    }

    private static BooleanExpression nameContains(String searchString) {
        if (searchString == null || searchString.isBlank()) {
            return null;
        }
        return region.name.containsIgnoreCase(searchString);
    }
}
