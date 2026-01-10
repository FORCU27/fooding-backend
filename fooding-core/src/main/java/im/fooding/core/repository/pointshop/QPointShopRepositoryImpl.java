package im.fooding.core.repository.pointshop;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import im.fooding.core.model.pointshop.PointShop;
import im.fooding.core.model.pointshop.PointShopSortType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.time.LocalDate;
import java.util.List;

import static im.fooding.core.model.file.QFile.file;
import static im.fooding.core.model.pointshop.QPointShop.pointShop;
import static im.fooding.core.model.store.QStore.store;

@RequiredArgsConstructor
public class QPointShopRepositoryImpl implements QPointShopRepository {
    private final JPAQueryFactory query;

    @Override
    public Page<PointShop> list(Long storeId, Boolean isActive, LocalDate now, PointShopSortType sortType, String searchString, Pageable pageable) {
        List<PointShop> results = query
                .select(pointShop)
                .from(pointShop)
                .leftJoin(pointShop.store, store).fetchJoin()
                .leftJoin(pointShop.image, file).fetchJoin()
                .where(
                        pointShop.deleted.isFalse(),
                        isActive(isActive),
                        storeDeletedIfStoreExists(),
                        searchStore(storeId),
                        search(searchString),
                        isIssuableAt(now)
                )
                .orderBy(sortType.getOrder(pointShop))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<PointShop> countQuery = query
                .select(pointShop)
                .from(pointShop)
                .where(
                        pointShop.deleted.isFalse(),
                        isActive(isActive),
                        storeDeletedIfStoreExists(),
                        searchStore(storeId),
                        search(searchString),
                        isIssuableAt(now)
                );

        return PageableExecutionUtils.getPage(results, pageable, countQuery::fetchCount);
    }

    @Override
    public List<PointShop> list(Long storeId, Boolean isActive, LocalDate now, PointShopSortType sortType) {
        return query
                .select(pointShop)
                .from(pointShop)
                .leftJoin(pointShop.store, store).fetchJoin()
                .leftJoin(pointShop.image, file).fetchJoin()
                .where(
                        pointShop.deleted.isFalse(),
                        isActive(isActive),
                        storeDeletedIfStoreExists(),
                        searchStore(storeId),
                        isIssuableAt(now)
                )
                .orderBy(sortType.getOrder(pointShop))
                .fetch();
    }

    private BooleanExpression searchStore(Long storeId) {
        return null != storeId ? pointShop.store.id.eq(storeId) : null;
    }

    private BooleanExpression search(String searchString) {
        return searchString != null ? pointShop.name.contains(searchString).or(store.name.contains(searchString)) : null;
    }

    private BooleanExpression storeDeletedIfStoreExists() {
        return store.id.isNull().or(store.deleted.isFalse());
    }

    private BooleanExpression isIssuableAt(LocalDate now) {
        return now != null ? pointShop.issueStartOn.isNull().or(pointShop.issueStartOn.loe(now))
                .and(pointShop.issueEndOn.isNull().or(pointShop.issueEndOn.goe(now))) : null;
    }

    private BooleanExpression isActive(Boolean isActive) {
        return isActive != null ? pointShop.isActive.eq(isActive) : null;
    }
}
