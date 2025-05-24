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
import com.querydsl.core.BooleanBuilder;

@RequiredArgsConstructor
public class QStoreRepositoryImpl implements QStoreRepository {

    private final JPAQueryFactory query;

    @Override
    public Page<Store> list(Pageable pageable, StoreSortType sortType, SortDirection sortDirection, boolean includeDeleted) {
        OrderSpecifier<?> orderSpecifier = getOrderSpecifier(sortType, sortDirection);

        BooleanBuilder builder = new BooleanBuilder();
        if (!includeDeleted) {
            builder.and(store.deleted.eq(false));
        }

        List<Store> content = query
                .select(store)
                .from(store)
                .leftJoin(review).on(review.store.eq(store))
                .where(builder)
                .groupBy(store.id)
                .orderBy(orderSpecifier)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPQLQuery<Long> countQuery = query
                .select(store.count())
                .from(store)
                .where(builder);

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    private OrderSpecifier<?> getOrderSpecifier(StoreSortType sortType, SortDirection direction) {
        if (sortType == null) {
            return new OrderSpecifier<>(direction == SortDirection.ASCENDING ? ASC : DESC, store.id);
        }
        return switch (sortType) {
            case REVIEW -> new OrderSpecifier<>(direction == SortDirection.ASCENDING ? ASC : DESC, review.count());
            case RECENT -> new OrderSpecifier<>(direction == SortDirection.ASCENDING ? ASC : DESC,
                    store.createdAt);
        };
    }
}
