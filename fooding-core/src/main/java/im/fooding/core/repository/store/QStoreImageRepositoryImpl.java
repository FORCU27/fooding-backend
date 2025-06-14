package im.fooding.core.repository.store;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import im.fooding.core.model.store.StoreImage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

import static im.fooding.core.model.store.QStore.store;
import static im.fooding.core.model.store.QStoreImage.storeImage;

@RequiredArgsConstructor
public class QStoreImageRepositoryImpl implements QStoreImageRepository {

    private final JPAQueryFactory query;

    @Override
    public Optional<StoreImage> findByStore(Long storeId) {
        return Optional.ofNullable(query
                .selectFrom(storeImage)
                .where(storeImage.store.id.eq(storeId), storeImage.deleted.isFalse())
                .orderBy(storeImage.sortOrder.asc(), storeImage.id.desc())
                .limit(1)
                .fetchOne()
        );
    }

    @Override
    public Page<StoreImage> list(long storeId, String searchTag, Pageable pageable) {
        List<StoreImage> images = query
                .selectFrom(storeImage)
                .where(
                        storeImage.store.id.eq(storeId),
                        searchTag(searchTag)
                )
                .orderBy(storeImage.sortOrder.asc(), storeImage.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPQLQuery<Long> countQuery = query
                .select(store.count())
                .from(store)
                .where(
                        storeImage.store.id.eq(storeId),
                        searchTag(searchTag)
                );

        return PageableExecutionUtils.getPage(images, pageable, countQuery::fetchOne);
    }

    private BooleanExpression searchTag(String tag) {
        if (!StringUtils.hasText(tag)) {
            return null;
        }
        return storeImage.tags.contains(tag);
    }
}
