package im.fooding.core.repository.store.image;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import im.fooding.core.model.store.StoreImage;
import im.fooding.core.model.store.StoreImageSortType;
import im.fooding.core.model.store.StoreImageTag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;
import java.util.Optional;

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
    public Page<StoreImage> list(long storeId, StoreImageTag tag, Boolean isMain, StoreImageSortType sortType, Pageable pageable) {
        List<StoreImage> images = query
                .selectFrom(storeImage)
                .where(
                        storeImage.deleted.isFalse(),
                        storeImage.store.id.eq(storeId),
                        searchTag(tag),
                        searchMain(isMain)
                )
                .orderBy(sortType.getOrder(storeImage))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPQLQuery<Long> countQuery = query
                .select(storeImage.count())
                .from(storeImage)
                .where(
                        storeImage.deleted.isFalse(),
                        storeImage.store.id.eq(storeId),
                        searchTag(tag),
                        searchMain(isMain)
                );

        return PageableExecutionUtils.getPage(images, pageable, countQuery::fetchOne);
    }

    private BooleanExpression searchTag(StoreImageTag tag) {
        return tag != null ? storeImage.tags.contains(tag.name()) : null;
    }

    private BooleanExpression searchMain(Boolean isMain) {
        return isMain != null ? storeImage.isMain.eq(isMain) : null;
    }
}
