package im.fooding.core.repository.coupon;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import im.fooding.core.model.coupon.CouponStatus;
import im.fooding.core.model.coupon.UserCoupon;
import im.fooding.core.model.coupon.UserCouponStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;

import static im.fooding.core.model.coupon.QCoupon.coupon;
import static im.fooding.core.model.coupon.QUserCoupon.userCoupon;
import static im.fooding.core.model.store.QStore.store;
import static im.fooding.core.model.store.QStoreImage.storeImage;
import static im.fooding.core.model.user.QUser.user;

@RequiredArgsConstructor
public class QUserCouponRepositoryImpl implements QUserCouponRepository {
    private final JPAQueryFactory query;

    @Override
    public Page<UserCoupon> list(Long userId, Long storeId, Long couponId, Boolean used, UserCouponStatus status, Pageable pageable) {
        List<UserCoupon> results = query
                .select(userCoupon)
                .from(userCoupon)
                .join(userCoupon.user, user).fetchJoin()
                .leftJoin(userCoupon.coupon, coupon).fetchJoin()
                .leftJoin(userCoupon.store, store).fetchJoin()
                .leftJoin(store.images, storeImage)
                .where(
                        userCoupon.deleted.isFalse(),
                        user.deleted.isFalse(),
                        couponDeletedIfStoreExists(),
                        storeDeletedIfStoreExists(),
                        storeImageDeletedIfStoreImageExists(),
                        searchUser(userId),
                        searchStore(storeId),
                        searchCoupon(couponId),
                        searchUsed(used),
                        searchStatus(status)
                )
                .orderBy(userCoupon.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<UserCoupon> countQuery = query
                .select(userCoupon)
                .from(userCoupon)
                .join(userCoupon.user, user).fetchJoin()
                .leftJoin(userCoupon.coupon, coupon).fetchJoin()
                .leftJoin(userCoupon.store, store).fetchJoin()
                .where(
                        userCoupon.deleted.isFalse(),
                        user.deleted.isFalse(),
                        couponDeletedIfStoreExists(),
                        storeDeletedIfStoreExists(),
                        searchCoupon(couponId),
                        searchUser(userId),
                        searchStore(storeId),
                        searchUsed(used),
                        searchStatus(status)
                );

        return PageableExecutionUtils.getPage(results, pageable, countQuery::fetchCount);
    }

    private BooleanExpression searchStore(Long storeId) {
        return null != storeId ? store.id.eq(storeId) : null;
    }

    private BooleanExpression searchUser(Long userId) {
        return null != userId ? user.id.eq(userId) : null;
    }

    private BooleanExpression searchCoupon(Long couponId) {
        return null != couponId ? userCoupon.coupon.id.eq(couponId) : null;
    }

    private BooleanExpression storeDeletedIfStoreExists() {
        return store.id.isNull().or(store.deleted.isFalse());
    }

    private BooleanExpression couponDeletedIfStoreExists() {
        return coupon.id.isNull().or(coupon.deleted.isFalse());
    }

    private BooleanExpression storeImageDeletedIfStoreImageExists() {
        return storeImage.id.isNull().or(storeImage.deleted.isFalse());
    }

    private BooleanExpression searchUsed(Boolean used) {
        if (null == used) {
            return null;
        }

        if (used) {
            return userCoupon.status.in(List.of(UserCouponStatus.REQUESTED, UserCouponStatus.USED));
        } else {
            return userCoupon.status.eq(UserCouponStatus.AVAILABLE);
        }
    }

    private BooleanExpression searchStatus(UserCouponStatus status) {
        return null != status ? userCoupon.status.eq(status) : null;
    }
}
