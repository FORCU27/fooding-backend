package im.fooding.core.repository.review;

import static im.fooding.core.model.review.QReview.review;

import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import im.fooding.core.model.review.Review;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

@RequiredArgsConstructor
public class QReviewRepositoryImpl implements QReviewRepository {

    private final JPAQueryFactory query;

    @Override
    public Page<Review> list(
            Long storeId,
            Pageable pageable
    ) {
        List<Review> results = query
                .select(review)
                .from(review)
                .where(review.store.id.eq(storeId))
                .where(review.deleted.isFalse())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPQLQuery<Long> countQuery = query
                .select(review.count())
                .where(review.store.id.eq(storeId))
                .from(review);

        return PageableExecutionUtils.getPage(results, pageable, countQuery::fetchOne);
    }
}
