package im.fooding.core.repository.reward;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import im.fooding.core.model.reward.RewardHistory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static im.fooding.core.model.reward.QRewardHistory.rewardHistory;

@RequiredArgsConstructor
public class QRewardHistoryRepositoryImpl implements QRewardHistoryRepository{
    private final JPAQueryFactory query;

    @Override
    public List<RewardHistory> list(Long storeId, String phoneNumber, Boolean isCoupon) {
        BooleanBuilder condition = new BooleanBuilder();
        if( storeId != null ) condition.and( rewardHistory.store.id.eq(storeId) );
        if( phoneNumber != null ) condition.and( rewardHistory.phoneNumber.eq(phoneNumber) );
        if( isCoupon != null ) condition.and( rewardHistory.isCoupon.eq(isCoupon) );

        List<RewardHistory> results = query
                .select( rewardHistory )
                .from( rewardHistory )
                .where( condition )
                .fetch();
         return results;
    }
}
