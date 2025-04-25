package im.fooding.core.repository.menu;

import static im.fooding.core.model.menu.QMenu.menu;

import com.querydsl.jpa.impl.JPAQueryFactory;
import im.fooding.core.model.menu.Menu;
import java.util.List;
import lombok.RequiredArgsConstructor;

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
}
