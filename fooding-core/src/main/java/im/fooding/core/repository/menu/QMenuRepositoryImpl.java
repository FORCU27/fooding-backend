package im.fooding.core.repository.menu;

import static im.fooding.core.model.menu.QMenu.menu;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import im.fooding.core.model.menu.Menu;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
public class QMenuRepositoryImpl implements QMenuRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Menu> list(List<Long> categoryIds) {
        return queryFactory
                .selectFrom(menu)
                .where(menu.category.id.in(categoryIds))
                .where(menu.deleted.isFalse())
                .fetch();
    }

    @Override
    public Page<Menu> list(MenuFilter filter, Pageable pageable) {
        var where = menu.deleted.isFalse();
        if (filter.menuCategoryIds() != null && !filter.menuCategoryIds().isEmpty()) {
            where = where.and(menu.category.id.in(filter.menuCategoryIds()));
        }
        if (filter.storeId() != null) {
            where = where.and(menu.store.id.eq(filter.storeId()));
        }

        return getMenus(where, pageable);
    }

    @Override
    public Page<Menu> list(Long storeId, String searchString, Pageable pageable) {
        var where = menu.deleted.isFalse();
        if (storeId != null) {
            where = where.and(menu.store.id.eq(storeId));
        }
        if (searchString != null && !searchString.isBlank()) {
            where = where.and(menu.name.containsIgnoreCase(searchString));
        }

        return getMenus(where, pageable);
    }

    @NotNull
    private PageImpl<Menu> getMenus(BooleanExpression where, Pageable pageable) {
        List<Menu> results = queryFactory
                .selectFrom(menu)
                .where(where)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(menu.id.desc())
                .fetch();

        Long total = queryFactory
                .select(menu.count())
                .from(menu)
                .where(where)
                .fetchOne();

        return new PageImpl<>(results, pageable, total != null ? total : 0L);
    }
}
