package im.fooding.core.repository.coupon;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import im.fooding.core.model.coupon.UserCoupon;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;

import static im.fooding.core.model.coupon.QCoupon.coupon;
import static im.fooding.core.model.coupon.QUserCoupon.userCoupon;
import static im.fooding.core.model.store.QStore.store;

@RequiredArgsConstructor
public class QUserCouponRepositoryImpl implements QUserCouponRepository {
    private final JPAQueryFactory query;

    @Override
    public Page<UserCoupon> list(long userId, Long storeId, Pageable pageable) {
        List<UserCoupon> results = query
                .select(userCoupon)
                .from(userCoupon)
                .join(userCoupon.coupon, coupon).fetchJoin()
                .leftJoin(userCoupon.store, store).fetchJoin()
                .where(
                        userCoupon.deleted.isFalse(),
                        userCoupon.user.id.eq(userId),
                        searchStore(storeId)
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<UserCoupon> countQuery = query
                .select(userCoupon)
                .from(userCoupon)
                .where(
                        userCoupon.deleted.isFalse(),
                        userCoupon.user.id.eq(userId),
                        searchStore(storeId)
                )
                .where();

        return PageableExecutionUtils.getPage(results, pageable, countQuery::fetchCount);
    }

    private BooleanExpression searchStore(Long storeId) {
        return null != storeId ? userCoupon.store.id.eq(storeId) : null;
    }
}
