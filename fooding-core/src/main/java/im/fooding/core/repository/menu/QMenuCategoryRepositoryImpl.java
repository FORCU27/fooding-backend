package im.fooding.core.repository.menu;

import static im.fooding.core.model.menu.QMenuCategory.menuCategory;

import com.querydsl.jpa.impl.JPAQueryFactory;
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
}
