package im.fooding.core.repository.store;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import im.fooding.core.dto.request.store.StoreFilter;
import im.fooding.core.model.store.Store;
import im.fooding.core.model.store.StoreCategory;
import im.fooding.core.model.store.StoreSortType;
import im.fooding.core.model.store.StoreStatus;
import lombok.RequiredArgsConstructor;
import org.hibernate.query.SortDirection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.querydsl.core.types.Order.ASC;
import static com.querydsl.core.types.Order.DESC;
import static im.fooding.core.model.store.QStore.store;
import static im.fooding.core.model.store.QStoreImage.storeImage;
import static im.fooding.core.model.store.QStoreMember.storeMember;

@RequiredArgsConstructor
public class QStoreRepositoryImpl implements QStoreRepository {

    private final JPAQueryFactory query;

    @Override
    public Page<Store> list(Pageable pageable, StoreSortType sortType, SortDirection sortDirection, List<String> regionIds, StoreCategory category, boolean includeDeleted, Set<StoreStatus> statuses, String searchString) {
        OrderSpecifier<?> orderSpecifier = getOrderSpecifier(sortType, sortDirection);

        List<Store> content = query
                .select(store)
                .from(store)
                .leftJoin(store.images, storeImage)
                .where(
                        isStoreDeleted(includeDeleted),
                        storeImageDeletedIfExists(),
                        statusesCondition(statuses),
                        nameContains(searchString),
                        searchRegionIds(regionIds),
                        searchCategory(category)
                )
                .orderBy(orderSpecifier)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPQLQuery<Long> countQuery = query
                .select(store.count())
                .from(store)
                .where(
                        isStoreDeleted(includeDeleted),
                        statusesCondition(statuses),
                        nameContains(searchString),
                        searchRegionIds(regionIds),
                        searchCategory(category)
                );

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    @Override
    public List<Store> listByUserId(long userId, StoreFilter filter) {
        return query
                .select(store)
                .from(store)
                .innerJoin(storeMember).on(store.id.eq(storeMember.store.id))
                .where(
                        storeMember.user.id.eq(userId),
                        regionIdEq(filter.getRegionId()),
                        store.deleted.isFalse()
                )
                .orderBy(store.id.desc())
                .fetch();
    }

    @Override
    public Optional<Store> retrieve(long storeId, Set<StoreStatus> statuses) {
        return Optional.ofNullable(query
                .selectFrom(store)
                .leftJoin(store.images, storeImage).fetchJoin()
                .where(
                        store.id.eq(storeId),
                        store.deleted.isFalse(),
                        storeImageDeletedIfExists(),
                        statusesCondition(statuses)
                )
                .fetchOne()
        );
    }

    @Override
    public List<Store> list(List<Long> ids) {
        if (ids.isEmpty()) {
            return Collections.emptyList();
        }

        // FIELD() 함수용 문자열 생성
        String joinedIds = ids.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(", "));

        OrderSpecifier<String> orderSpecifier =
                Expressions.stringTemplate("FIELD({0}, " + joinedIds + ")", store.id).asc();

        return query
                .select(store)
                .from(store)
                .leftJoin(store.images, storeImage)
                .where(
                        isStoreDeleted(false),
                        storeImageDeletedIfExists(),
                        store.id.in(ids)
                )
                .orderBy(orderSpecifier)
                .fetch();
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

    private BooleanExpression regionIdEq(String regionId) {
        return regionId != null ? store.region.id.eq(regionId) : null;
    }

    private BooleanExpression statusesCondition(Set<StoreStatus> statuses) {
        return statuses != null && !statuses.isEmpty() ? store.status.in(statuses) : null;
    }

    private BooleanExpression nameContains(String searchString) {
        return (searchString != null && !searchString.isBlank()) ? store.name.containsIgnoreCase(searchString) : null;
    }

    private BooleanExpression searchRegionIds(List<String> regionIds) {
        if (regionIds == null || regionIds.isEmpty()) {
            return null;
        }
        return store.region.id.in(regionIds);
    }

    private BooleanExpression searchCategory(StoreCategory category) {
        return category != null ? store.category.eq(category) : null;
    }
}
