package im.fooding.core.repository.store;

import static com.querydsl.core.types.Order.*;
import static im.fooding.core.model.review.QReview.review;
import static im.fooding.core.model.store.QStore.store;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import im.fooding.core.model.store.Store;
import im.fooding.core.model.store.StoreSortType;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.hibernate.query.SortDirection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

@RequiredArgsConstructor
public class QStoreRepositoryImpl implements QStoreRepository {

    private final JPAQueryFactory query;

    @Override
    public Page<Store> list(
            Pageable pageable,
            StoreSortType sortType,
            SortDirection sortDirection
    ) {
        OrderSpecifier<?> orderSpecifier = getOrderSpecifier(sortType, sortDirection);

        List<Store> content = query
                .select(store)
                .from(store)
                .leftJoin(review).on(review.store.eq(store))
                .groupBy(store.id)
                .orderBy(orderSpecifier)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPQLQuery<Long> countQuery = query
                .select(store.count())
                .from(store);

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    private OrderSpecifier<?> getOrderSpecifier(StoreSortType sortType, SortDirection direction) {
        return switch (sortType) {
            case REVIEW -> new OrderSpecifier<>
                    (direction == SortDirection.ASCENDING ? ASC : DESC, review.count());
            case RECENT -> new OrderSpecifier<>
                    (direction == SortDirection.ASCENDING ? ASC : DESC, store.createdAt);
        };
    }
}
