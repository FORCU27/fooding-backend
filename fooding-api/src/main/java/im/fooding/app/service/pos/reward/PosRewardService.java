package im.fooding.app.service.pos.reward;

import im.fooding.app.dto.request.pos.reward.GetRewardRequest;
import im.fooding.app.dto.response.pos.reward.GetPosRewardResponse;
import im.fooding.core.model.reward.RewardLog;
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

    public Page<GetPosRewardResponse> list(GetRewardRequest request){
        return logService.list(
                request.getSearchString(),
                request.getPageable(),
                request.getStoreId(),
                request.getPhoneNumber()
        ).map( GetPosRewardResponse::of );
    }

    @Transactional
    public void cancelReward( Long rewardLogId ){
        RewardLog log = logService.findById( rewardLogId );
        rewardService.usePoint(log.getPhoneNumber(), log.getStore().getId(), log.getPoint());
    }
}
