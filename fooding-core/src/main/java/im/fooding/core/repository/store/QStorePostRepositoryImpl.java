package im.fooding.core.repository.store;

import static im.fooding.core.model.store.QStorePost.storePost;

import com.querydsl.jpa.impl.JPAQueryFactory;
import im.fooding.core.model.store.StorePost;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
public class QStorePostRepositoryImpl implements QStorePostRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<StorePost> list(Long storeId, String searchString, Pageable pageable) {
        var predicate = storePost.deleted.isFalse();
        if (storeId != null) {
            predicate = predicate.and(storePost.store.id.eq(storeId));
        }
        if (searchString != null && !searchString.isBlank()) {
            predicate = predicate.and(storePost.title.containsIgnoreCase(searchString));
        }

        var base = queryFactory
                .selectFrom(storePost)
                .where(predicate)
                .orderBy(storePost.isFixed.desc(), storePost.updatedAt.desc());

        List<StorePost> results;
        if (pageable.isUnpaged()) {
            results = base.fetch();
        } else {
            results = base
                    .offset(pageable.getOffset())
                    .limit(pageable.getPageSize())
                    .fetch();
        }

        Long total = queryFactory
                .select(storePost.count())
                .from(storePost)
                .where(predicate)
                .fetchOne();

        return new PageImpl<>(results, pageable, total != null ? total : 0L);
    }
}

