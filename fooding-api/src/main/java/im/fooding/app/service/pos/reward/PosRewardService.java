package im.fooding.app.service.pos.reward;

import im.fooding.app.dto.request.pos.reward.GetPosRewardRequest;
import im.fooding.app.dto.request.pos.reward.UpdateRewardLogRequest;
import im.fooding.app.dto.response.pos.reward.GetPosRewardResponse;
import im.fooding.core.common.PageInfo;
import im.fooding.core.common.PageResponse;
import im.fooding.core.model.reward.RewardLog;
import im.fooding.core.model.reward.RewardStatus;
import im.fooding.core.service.reward.RewardLogService;
import im.fooding.core.service.reward.RewardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional( readOnly = true )
public class PosRewardService {
    private final RewardLogService logService;
    private final RewardService rewardService;

    public PageResponse<GetPosRewardResponse> list(GetPosRewardRequest request){
        Page<GetPosRewardResponse> result = logService.list(
                request.getSearchString(),
                request.getPageable(),
                request.getStoreId(),
                request.getPhoneNumber(),
                request.getStatus()
        ).map( GetPosRewardResponse::of );
        return PageResponse.of( result.stream().toList(), PageInfo.of( result ) );
    }

    @Transactional
    public void cancel(Long rewardLogId, UpdateRewardLogRequest request){
        RewardLog log = logService.findById( rewardLogId );
        // 이미 취소되어 있는 경우 무시
        if( log.getStatus() == RewardStatus.CANCELED ) return;

        // 취소하는 로그 생성
        RewardLog cancelLog = RewardLog.builder()
                        .store( log.getStore() )
                        .phoneNumber( log.getPhoneNumber() )
                        .point( log.getPoint() )
                        .status( RewardStatus.CANCELED )
                        .type( log.getType() )
                        .channel( log.getChannel() )
                        .memo( request.getMemo() )
                        .build();
        logService.save( cancelLog );
        rewardService.usePoint(log.getPhoneNumber(), log.getStore().getId(), log.getPoint());
    }

    @Transactional
    public void approve( Long rewardLogId, UpdateRewardLogRequest request ){
        RewardLog log = logService.findById( rewardLogId );
        // 이미 승인되어 있는 경우 무시
        if( log.getStatus() == RewardStatus.EARNED ) return;
        
        // 승인하는 로그 생성
        RewardLog approveLog = RewardLog.builder()
                .store( log.getStore() )
                .phoneNumber( log.getPhoneNumber() )
                .point( log.getPoint() )
                .status( RewardStatus.EARNED )
                .type( log.getType() )
                .channel( log.getChannel() )
                .memo( request.getMemo() )
                .build();
        logService.save( approveLog );
        rewardService.addPoint( log.getPhoneNumber(), log.getStore().getId(), log.getPoint() );
    }

    @Transactional
    public void updateMemo( Long id, String memo ){
        RewardLog log = logService.findById( id );
        log.updateMemo( memo );
    }
}
