package im.fooding.core.repository.coupon;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import im.fooding.core.model.coupon.Coupon;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;

import static im.fooding.core.model.coupon.QCoupon.coupon;
import static im.fooding.core.model.store.QStore.store;

@RequiredArgsConstructor
public class QCouponRepositoryImpl implements QCouponRepository {
    private final JPAQueryFactory query;

    @Override
    public Page<Coupon> list(Long storeId, String searchString, Pageable pageable) {
        List<Coupon> results = query
                .select(coupon)
                .from(coupon)
                .leftJoin(coupon.store, store).fetchJoin()
                .where(
                        coupon.deleted.isFalse(),
                        store.deleted.isFalse(),
                        searchStore(storeId),
                        search(searchString)
                )
                .orderBy(coupon.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Coupon> countQuery = query
                .select(coupon)
                .from(coupon)
                .where(
                        coupon.deleted.isFalse(),
                        store.deleted.isFalse(),
                        searchStore(storeId),
                        search(searchString)
                )
                .where();

        return PageableExecutionUtils.getPage(results, pageable, countQuery::fetchCount);
    }

    private BooleanExpression searchStore(Long storeId) {
        return null != storeId ? coupon.store.id.eq(storeId) : null;
    }

    private BooleanExpression search(String searchString) {
        return searchString != null ? coupon.name.contains(searchString).or(store.name.contains(searchString)) : null;
    }
}
