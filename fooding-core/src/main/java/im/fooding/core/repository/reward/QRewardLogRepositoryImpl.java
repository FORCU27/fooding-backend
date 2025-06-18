package im.fooding.core.repository.reward;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import im.fooding.core.model.reward.RewardLog;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.util.StringUtils;

import java.util.List;

import static im.fooding.core.model.reward.QRewardLog.rewardLog;

@RequiredArgsConstructor
public class QRewardLogRepositoryImpl implements QRewardLogRepository {
    private final JPAQueryFactory query;
    @Override
    public Page<RewardLog> list(String searchString, Pageable pageable, Long storeId, String phoneNumber) {
        BooleanBuilder condition = new BooleanBuilder();
        condition.and( rewardLog.deleted.isFalse() );
        if( storeId != null ) condition.and( rewardLog.store.id.eq( storeId ) );
        if( phoneNumber != null ) condition.and( rewardLog.phoneNumber.eq( phoneNumber ) );

        List<RewardLog> results = query
                .select( rewardLog )
                .from( rewardLog )
                .where( condition )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        JPAQuery<Long> countQuery = query
                .select( rewardLog.count() )
                .from( rewardLog )
                .where( condition );
        return PageableExecutionUtils.getPage(
                results, pageable, countQuery::fetchCount
        );
    }

    private BooleanExpression search( String searchString ){
        return StringUtils.hasText( searchString )
                ? rewardLog.store.name.contains( searchString ) : null;
    }
}
