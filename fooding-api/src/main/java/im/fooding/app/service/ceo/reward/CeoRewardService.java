package im.fooding.app.service.ceo.reward;

import im.fooding.app.dto.response.ceo.reward.CeoRewardHistoryResponse;
import im.fooding.core.model.coupon.UserCoupon;
import im.fooding.core.model.reward.RewardChannel;
import im.fooding.core.model.reward.RewardHistory;
import im.fooding.core.model.reward.RewardHistoryStatus;
import im.fooding.core.model.reward.RewardLog;
import im.fooding.core.service.coupon.UserCouponService;
import im.fooding.core.service.reward.RewardHistoryService;
import im.fooding.core.service.reward.RewardLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CeoRewardService {
    private final RewardHistoryService rewardHistoryService;
    private final UserCouponService userCouponService;
    private final RewardLogService rewardLogService;

    // 해당 가게의 로그 목록 조회
    public List<CeoRewardHistoryResponse> retrieveCouponHistoryByStoreId( long storeId, Boolean isCoupon ){
        List<RewardHistory> histories = rewardHistoryService.list( storeId, null, isCoupon );
        return histories.stream().map( history -> {
            CeoRewardHistoryResponse.CeoRewardHistoryResponseBuilder builder = CeoRewardHistoryResponse.builder();
            builder.id( history.getId() );
            builder.phoneNumber(history.getPhoneNumber());
            builder.createdAt( history.getCreatedAt() );
            String operation = history.isUsing() ? "사용 " : "취소 ";
            switch( history.getStatus() ){
                case APPLIED -> operation = operation + "승인";
                case REQUEST -> operation = operation + "요청";
                case CANCELED -> operation = operation + "반려";
                default -> operation = "";
            }
            builder.operation( operation );
            if( history.getStore() != null ) builder.storeId( history.getStore().getId() );
            builder.channel( "온라인" );
            if( history.isCoupon() ){
                UserCoupon coupon = userCouponService.findById( history.getTargetId() );
                builder.rewardType( "쿠폰" );
                builder.category( "쿠폰" );
                builder.target( coupon.getCoupon().getName() );
            }
            else {
                RewardLog rewardLog = rewardLogService.findById( history.getTargetId() );
                builder.rewardType( "포인트" );
                if( rewardLog.getChannel() == RewardChannel.STORE ) builder.category( "방문" );
                else builder.category( "이벤트" );
                builder.target( Integer.toString( rewardLog.getPoint() ) );
            }
            return builder.build();
        }).toList();
    }
}
