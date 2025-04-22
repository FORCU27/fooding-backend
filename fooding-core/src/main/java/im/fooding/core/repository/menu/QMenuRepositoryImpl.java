package im.fooding.core.repository.menu;

import static im.fooding.core.model.menu.QMenu.menu;

import com.querydsl.jpa.impl.JPAQueryFactory;
import im.fooding.core.model.menu.Menu;
import im.fooding.core.model.menu.MenuCategory;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class QMenuRepositoryImpl implements QMenuRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Menu> list(MenuCategory menuCategory) {

        return queryFactory
                .selectFrom(menu)
                .where(menu.category.eq(menuCategory))
                .orderBy(menu.sortOrder.asc())
                .fetch();

    }
}
