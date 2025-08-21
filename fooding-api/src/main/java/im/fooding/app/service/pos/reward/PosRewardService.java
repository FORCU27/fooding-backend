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
                request.getPhoneNumber()
        ).map( GetPosRewardResponse::of );
        return PageResponse.of( result.stream().toList(), PageInfo.of( result ) );
    }

    @Transactional
    public void cancel(Long rewardLogId, UpdateRewardLogRequest request){
        RewardLog log = logService.findById( rewardLogId );
        log.updateStatus( RewardStatus.CANCELED );
        log.updateMemo( request.getMemo() );
        rewardService.usePoint(log.getPhoneNumber(), log.getStore().getId(), log.getPoint());
    }

    @Transactional
    public void approve( Long rewardLogId, UpdateRewardLogRequest request ){
        RewardLog log = logService.findById( rewardLogId );
        log.updateStatus( RewardStatus.EARNED );
        log.updateMemo( request.getMemo() );
        rewardService.addPoint( log.getPhoneNumber(), log.getStore().getId(), log.getPoint() );
    }

    @Transactional
    public void updateMemo( Long id, String memo ){
        RewardLog log = logService.findById( id );
        log.updateMemo( memo );
    }
}
