package im.fooding.core.repository.menu;

import static im.fooding.core.model.menu.QMenuCategory.menuCategory;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import im.fooding.core.model.menu.MenuCategory;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.util.StringUtils;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class QMenuCategoryRepositoryImpl implements QMenuCategoryRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public int getMaxSortOrder(Long storeId) {
        Integer maxSortOrder = queryFactory
                .select(menuCategory.sortOrder.max())
                .from(menuCategory)
                .where(
                        menuCategory.store.id.eq(storeId),
                        menuCategory.deleted.isFalse()
                )
                .fetchOne();

        return maxSortOrder != null ? maxSortOrder : 0;
    }

    @Override
    public Page<MenuCategory> list(Long storeId, String searchString, Pageable pageable) {
        BooleanBuilder conditions = new BooleanBuilder();
        conditions.and(menuCategory.deleted.isFalse());
        if (storeId != null) {
            conditions.and(menuCategory.store.id.eq(storeId));
        }
        if (StringUtils.hasText(searchString)) {
            conditions.and(menuCategory.name.containsIgnoreCase(searchString));
        }

        List<MenuCategory> results = queryFactory
                .selectFrom(menuCategory)
                .where(conditions)
                .orderBy(menuCategory.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(menuCategory.count())
                .from(menuCategory)
                .where(conditions);

        return PageableExecutionUtils.getPage(results, pageable, countQuery::fetchCount);
    }
}
