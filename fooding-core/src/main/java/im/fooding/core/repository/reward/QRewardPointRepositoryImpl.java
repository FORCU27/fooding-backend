package im.fooding.core.repository.reward;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import im.fooding.core.model.reward.RewardPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.util.StringUtils;

import java.util.List;

import static im.fooding.core.model.reward.QRewardPoint.rewardPoint;

@RequiredArgsConstructor
public class QRewardPointRepositoryImpl implements QRewardPointRepository{
    private final JPAQueryFactory query;
    @Override
    public Page<RewardPoint> list(String searchString, Pageable pageable, Long storeId, String phoneNumber) {
        BooleanBuilder condition = new BooleanBuilder();
        condition.and( rewardPoint.deleted.isFalse() );

        List<RewardPoint> results = query
                .select( rewardPoint )
                .from( rewardPoint )
                .where( condition )
                .where( rewardPoint.store.id.eq( storeId ) )
                .where( rewardPoint.phoneNumber.eq( phoneNumber ) )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        JPAQuery<Long> countQuery = query
                .select( rewardPoint.count() )
                .from( rewardPoint )
                .where( condition )
                .where( rewardPoint.store.id.eq( storeId ) )
                .where( rewardPoint.phoneNumber.eq( phoneNumber ) );
        return PageableExecutionUtils.getPage(
                results, pageable, countQuery::fetchCount
        );
    }

    private BooleanExpression search( String searchString ){
        return StringUtils.hasText( searchString )
                ? rewardPoint.store.name.contains( searchString ) : null;
    }
}
