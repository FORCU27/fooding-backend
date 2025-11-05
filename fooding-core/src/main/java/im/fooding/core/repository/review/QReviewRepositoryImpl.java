package im.fooding.core.repository.review;

import static im.fooding.core.model.review.QReview.review;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import im.fooding.core.model.review.Review;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;

@RequiredArgsConstructor
public class QReviewRepositoryImpl implements QReviewRepository {

    private final JPAQueryFactory query;

    @Override
    public Page<Review> list(
            Long storeId,
            Long writerId,
            Long parentId,
            Pageable pageable
    ) {
        OrderSpecifier<?> orderSpecifier = getOrderSpecifier( pageable );

        BooleanBuilder whereClause = new BooleanBuilder();
        whereClause.and( review.deleted.isFalse() );
        if( storeId != null ) whereClause.and( review.store.id.eq( storeId ) );
        if( writerId != null ) whereClause.and( review.writer.id.eq( writerId ) );
        if( parentId != null ) whereClause.and( review.parent.id.eq( parentId ) );
        else if( parentId == 0 ) whereClause.and( review.parent.id.isNull() );
        JPAQuery<Review> jpaQuery = query
                .select(review)
                .from(review)
                .where( whereClause )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());
        if( orderSpecifier != null ) jpaQuery.orderBy( orderSpecifier );
        List<Review> results = jpaQuery.fetch();

        JPQLQuery<Long> countQuery = query
                .select(review.count())
                .from(review)
                .where(whereClause);

        return PageableExecutionUtils.getPage(results, pageable, countQuery::fetchOne);
    }

    private OrderSpecifier<?> getOrderSpecifier(Pageable pageable){
        if( !pageable.getSort().isSorted() ) return null;
        Order order = pageable.getSort().iterator().next().isAscending() ? Order.ASC : Order.DESC;
        return new OrderSpecifier<>( order, review.createdAt );
    }
}
