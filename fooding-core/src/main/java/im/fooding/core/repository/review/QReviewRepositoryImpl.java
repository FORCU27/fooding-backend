package im.fooding.core.repository.review;

import static com.querydsl.core.types.Order.ASC;
import static com.querydsl.core.types.Order.DESC;
import static im.fooding.core.model.review.QReview.review;
import static im.fooding.core.model.store.QStore.store;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import im.fooding.core.model.review.Review;
import im.fooding.core.model.review.ReviewSortType;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.hibernate.query.SortDirection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

@RequiredArgsConstructor
public class QReviewRepositoryImpl implements QReviewRepository {

    private final JPAQueryFactory query;

    @Override
    public Page<Review> list(
            Long storeId,
            Pageable pageable,
            ReviewSortType sortType,
            SortDirection sortDirection
    ) {
        OrderSpecifier<?> orderSpecifier = getOrderSpecifier(sortType, sortDirection);

        List<Review> results = query
                .select(review)
                .from(review)
                .where(review.store.id.eq(storeId))
                .orderBy(orderSpecifier)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPQLQuery<Long> countQuery = query
                .select(review.count())
                .where(review.store.id.eq(storeId))
                .from(review);

        return PageableExecutionUtils.getPage(results, pageable, countQuery::fetchOne);
    }

    private OrderSpecifier<?> getOrderSpecifier(ReviewSortType sortType, SortDirection direction) {
        return switch (sortType) {
            case REVIEW -> new OrderSpecifier<>
                    (direction == SortDirection.ASCENDING ? ASC : DESC, review.score.total);
            case RECENT -> new OrderSpecifier<>
                    (direction == SortDirection.ASCENDING ? ASC : DESC, store.createdAt);
        };
    }
}
