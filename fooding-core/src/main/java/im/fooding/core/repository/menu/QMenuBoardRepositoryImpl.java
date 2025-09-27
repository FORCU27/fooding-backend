package im.fooding.core.repository.menu;

import static im.fooding.core.model.menu.QMenuBoard.menuBoard;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import im.fooding.core.model.menu.MenuBoard;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
public class QMenuBoardRepositoryImpl implements QMenuBoardRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<MenuBoard> listByStoreId(long storeId, Pageable pageable) {
        var where = menuBoard.deleted.isFalse();
        where = where.and(menuBoard.store.id.eq(storeId));

        return getMenuBoards(where, pageable);
    }

    @Override
    public long countByStoreId(long storeId) {
        var where = menuBoard.deleted.isFalse();
        where = where.and(menuBoard.store.id.eq(storeId));

        return countMenuBoards(where);
    }

    public long countMenuBoards(BooleanExpression where) {
        Long total = queryFactory
                .select(menuBoard.count())
                .from(menuBoard)
                .where(where)
                .fetchOne();

        if (total == null) {
            return 0L;
        }
        return total;
    }

    private PageImpl<MenuBoard> getMenuBoards(BooleanExpression where, Pageable pageable) {
        List<MenuBoard> results = queryFactory
                .selectFrom(menuBoard)
                .where(where)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(menuBoard.id.asc())
                .fetch();

        long total = countMenuBoards(where);

        return new PageImpl<>(results, pageable, total);
    }
}
