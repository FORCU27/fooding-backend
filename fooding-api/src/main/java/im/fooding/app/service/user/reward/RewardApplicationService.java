package im.fooding.app.service.user.reward;


import im.fooding.app.dto.request.user.reward.GetRewardLogRequest;
import im.fooding.app.dto.request.user.reward.GetRewardPointRequest;
import im.fooding.app.dto.request.user.reward.UpdateRewardPointRequest;
import im.fooding.app.dto.response.user.reward.GetRewardLogResponse;
import im.fooding.app.dto.response.user.reward.GetRewardPointResponse;
import im.fooding.core.service.reward.RewardLogService;
import im.fooding.core.service.reward.RewardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RewardApplicationService {
    private final RewardLogService logService;
    private final RewardService pointService;

    /**
     * Reward Log 전체 조회
     *
     * @param request
     * @return Page<GetRewardLogResponse>
     */
    public Page<GetRewardLogResponse> getAllRewardLog(GetRewardLogRequest request){

    }

    /**
     * Reward Log 조건 조회
     *
     * @param request
     * @return StoreDeviceResponse
     */
    public Page<GetRewardLogResponse> getRewardLog(GetRewardLogRequest request){

    }

    /**
     * Reward Point 전체 조회
     *
     * @param request
     * @return Page<GetRewardPointResponse>
     */
    public Page<GetRewardPointResponse> getAllRewardPoint(GetRewardPointRequest request){

    }

    /**
     * Reward Point 조건 조회
     *
     * @param request
     * @return Page<GetRewardPointResponse>
     */
    public Page<GetRewardPointResponse> getRewardPoint(GetRewardPointRequest request){

    }

    /**
     * Reward 적립
     *
     * @param request
     */
    public void usePoint(UpdateRewardPointRequest request){

    }

    /**
     * Reward 사용
     *
     * @param request
     */
    public void getPoint(UpdateRewardPointRequest request){

    }
    
}
