package im.fooding.core.repository.store;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import im.fooding.core.model.store.Store;
import im.fooding.core.model.store.StoreSortType;
import lombok.RequiredArgsConstructor;
import org.hibernate.query.SortDirection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;
import java.util.Optional;

import static com.querydsl.core.types.Order.ASC;
import static com.querydsl.core.types.Order.DESC;
import static im.fooding.core.model.store.QStore.store;
import static im.fooding.core.model.store.QStoreImage.storeImage;
import static im.fooding.core.model.store.QStoreMember.storeMember;

@RequiredArgsConstructor
public class QStoreRepositoryImpl implements QStoreRepository {

    private final JPAQueryFactory query;

    @Override
    public Page<Store> list(Pageable pageable, StoreSortType sortType, SortDirection sortDirection, boolean includeDeleted) {
        OrderSpecifier<?> orderSpecifier = getOrderSpecifier(sortType, sortDirection);

        List<Store> content = query
                .select(store)
                .from(store)
                .join(store.images, storeImage).fetchJoin()
                .where(
                        isStoreDeleted(includeDeleted),
                        storeImageDeletedIfExists()
                )
                .orderBy(orderSpecifier)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPQLQuery<Long> countQuery = query
                .select(store.count())
                .from(store)
                .where(
                        isStoreDeleted(includeDeleted)
                );

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    @Override
    public List<Store> listByUserId(long userId) {
        return query
                .select(store)
                .from(store)
                .innerJoin(storeMember).on(store.id.eq(storeMember.store.id))
                .where(storeMember.user.id.eq(userId), store.deleted.isFalse())
                .orderBy(store.id.desc())
                .fetch();
    }

    @Override
    public Optional<Store> retrieve(long storeId) {
        return Optional.ofNullable(query
                .selectFrom(store)
                .leftJoin(store.images, storeImage).fetchJoin()
                .where(
                        store.id.eq(storeId),
                        store.deleted.isFalse(),
                        storeImage.deleted.isFalse()
                )
                .fetchOne()
        );
    }

    private OrderSpecifier<?> getOrderSpecifier(StoreSortType sortType, SortDirection direction) {
        Order order = direction == SortDirection.ASCENDING ? ASC : DESC;

        return switch (sortType) {
            case REVIEW -> new OrderSpecifier<>(order, store.reviewCount);
            case RECENT -> new OrderSpecifier<>(order, store.createdAt);
            case AVERAGE_RATING -> new OrderSpecifier<>(order, store.averageRating);
            case VISIT -> new OrderSpecifier<>(order, store.visitCount);
        };
    }

    private BooleanExpression isStoreDeleted(Boolean includeDeleted) {
        return null != includeDeleted ? store.deleted.eq(includeDeleted) : store.deleted.isFalse();
    }

    private BooleanExpression storeImageDeletedIfExists() {
        return storeImage.id.isNull().or(storeImage.deleted.isFalse());
    }
}
