package im.fooding.core.repository.store.recent;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import im.fooding.core.model.store.Store;
import im.fooding.core.model.store.StoreStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;
import java.util.Set;

import static im.fooding.core.model.store.QRecentStore.recentStore;
import static im.fooding.core.model.store.QStore.store;
import static im.fooding.core.model.store.QStoreImage.storeImage;

@RequiredArgsConstructor
public class QRecentStoreRepositoryImpl implements QRecentStoreRepository {
    private final JPAQueryFactory query;

    @Override
    public Page<Store> findRecentStores(long userId, Set<StoreStatus> statuses, Pageable pageable) {
        List<Store> content = query
                .select(store)
                .from(store)
                .innerJoin(recentStore).on(store.id.eq(recentStore.store.id)).fetchJoin()
                .leftJoin(store.images, storeImage)
                .where(
                        recentStore.deleted.isFalse(),
                        store.deleted.isFalse(),
                        statusesCondition(statuses)
                )
                .orderBy(recentStore.viewedAt.desc())
                .fetch();

        JPQLQuery<Long> countQuery = query
                .select(store.count())
                .from(store)
                .innerJoin(recentStore).on(store.id.eq(recentStore.store.id)).fetchJoin()
                .leftJoin(store.images, storeImage)
                .where(
                        recentStore.deleted.isFalse(),
                        store.deleted.isFalse(),
                        statusesCondition(statuses)
                );

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    private BooleanExpression statusesCondition(Set<StoreStatus> statuses) {
        return statuses != null && !statuses.isEmpty() ? store.status.in(statuses) : null;
    }
}
