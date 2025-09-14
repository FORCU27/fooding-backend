package im.fooding.core.repository.coupon;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import im.fooding.core.model.coupon.Coupon;
import im.fooding.core.model.coupon.CouponStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.time.LocalDate;
import java.util.List;

import static im.fooding.core.model.coupon.QCoupon.coupon;
import static im.fooding.core.model.pointshop.QPointShop.pointShop;
import static im.fooding.core.model.store.QStore.store;
import static im.fooding.core.model.store.QStoreImage.storeImage;

@RequiredArgsConstructor
public class QCouponRepositoryImpl implements QCouponRepository {
    private final JPAQueryFactory query;

    @Override
    public Page<Coupon> list(Long storeId, CouponStatus status, LocalDate now, String searchString, Pageable pageable) {
        List<Coupon> results = query
                .select(coupon)
                .from(coupon)
                .leftJoin(coupon.store, store).fetchJoin()
                .leftJoin(store.images, storeImage)
                .where(
                        coupon.deleted.isFalse(),
                        storeDeletedIfStoreExists(),
                        storeImageDeletedIfStoreImageExists(),
                        searchStore(storeId),
                        search(searchString),
                        searchStatus(status),
                        isIssuableAt(now)
                )
                .orderBy(coupon.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Coupon> countQuery = query
                .select(coupon)
                .from(coupon)
                .leftJoin(coupon.store, store).fetchJoin()
                .where(
                        coupon.deleted.isFalse(),
                        storeDeletedIfStoreExists(),
                        searchStore(storeId),
                        search(searchString),
                        searchStatus(status),
                        isIssuableAt(now)
                );

        return PageableExecutionUtils.getPage(results, pageable, countQuery::fetchCount);
    }

    private BooleanExpression searchStore(Long storeId) {
        return null != storeId ? coupon.store.id.eq(storeId) : null;
    }

    private BooleanExpression search(String searchString) {
        return searchString != null ? coupon.name.contains(searchString).or(store.name.contains(searchString)) : null;
    }

    private BooleanExpression storeDeletedIfStoreExists() {
        return store.id.isNull().or(store.deleted.isFalse());
    }

    private BooleanExpression storeImageDeletedIfStoreImageExists() {
        return storeImage.id.isNull().or(storeImage.deleted.isFalse());
    }

    private BooleanExpression searchStatus(CouponStatus status) {
        return null != status ? coupon.status.eq(status) : null;
    }

    private BooleanExpression isIssuableAt(LocalDate now) {
        return now != null ? coupon.issueStartOn.isNull().or(coupon.issueStartOn.loe(now))
                .and(coupon.issueEndOn.isNull().or(coupon.issueEndOn.goe(now))) : null;
    }
}
