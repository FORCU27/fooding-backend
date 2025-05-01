package im.fooding.core.repository.store;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import im.fooding.core.model.store.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.util.StringUtils;

import java.util.List;

import static im.fooding.core.model.store.QStoreService.storeService;

@RequiredArgsConstructor
public class QStoreServiceRepositoryImpl implements QStoreServiceRepository{
    private final JPAQueryFactory query;

    @Override
    public Page<StoreService> list(String searchString, Pageable pageable) {
        List<StoreService> results = query
                .select( storeService )
                .from( storeService )
                .where(
                        storeService.deleted.isFalse(),
                        search( searchString )
                )
                .orderBy( storeService.id.desc() )
                .offset( pageable.getOffset() )
                .limit( pageable.getPageSize() )
                .fetch();
        JPAQuery countQuery = query
                .select( storeService.count() )
                .from( storeService )
                .where(
                        storeService.deleted.isFalse(),
                        search( searchString )
                );
        return PageableExecutionUtils.getPage( results, pageable, countQuery::fetchCount );
    }

    private BooleanExpression search( String searchString ){
        return StringUtils.hasText( searchString )
                ? storeService.store.name.contains( searchString )
                : null;
    }
}
