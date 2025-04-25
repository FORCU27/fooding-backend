package im.fooding.core.repository.review;

import static im.fooding.core.model.review.QReviewLike.reviewLike;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class QReviewLikeRepositoryImpl implements QReviewLikeRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Map<Long, Long> list(List<Long> reviewIds) {

        return queryFactory
                .select(reviewLike.review.id, reviewLike.count())
                .from(reviewLike)
                .where(reviewLike.review.id.in(reviewIds))
                .groupBy(reviewLike.review.id)
                .fetch()
                .stream()
                .collect(Collectors.toMap(
                        tuple -> tuple.get(reviewLike.review.id),
                        tuple -> Optional.ofNullable(tuple.get(reviewLike.count())).orElse(0L)
                ));
    }
}
