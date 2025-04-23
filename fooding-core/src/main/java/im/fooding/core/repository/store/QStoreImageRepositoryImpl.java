package im.fooding.core.repository.store;

import static im.fooding.core.model.store.QStoreImage.storeImage;

import com.querydsl.jpa.impl.JPAQueryFactory;
import im.fooding.core.model.store.StoreImage;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class QStoreImageRepositoryImpl implements QStoreImageRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<StoreImage> findByStore(Long storeId) {

        StoreImage result = queryFactory
                .selectFrom(storeImage)
                .where(storeImage.store.id.eq(storeId))
                .orderBy(storeImage.sortOrder.asc())
                .limit(1)
                .fetchOne();

        return Optional.ofNullable(result);
    }
}
