package im.fooding.app.service.user.reward;


import im.fooding.app.dto.request.user.reward.GetRewardLogRequest;
import im.fooding.app.dto.request.user.reward.GetRewardPointRequest;
import im.fooding.app.dto.request.user.reward.UpdateRewardPointRequest;
import im.fooding.app.dto.response.user.reward.GetRewardLogResponse;
import im.fooding.app.dto.response.user.reward.GetRewardPointResponse;
import im.fooding.core.model.reward.RewardStatus;
import im.fooding.core.service.reward.RewardLogService;
import im.fooding.core.service.reward.RewardService;
import im.fooding.core.service.store.StoreService;
import im.fooding.core.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RewardApplicationService {
    private final RewardLogService logService;
    private final RewardService pointService;
    private final StoreService storeService;
    private final UserService userService;

    /**
     * Reward Log 조회
     *
     * @param request
     * @return StoreDeviceResponse
     */
    public Page<GetRewardLogResponse> getRewardLog(GetRewardLogRequest request){
        return logService.list(request.getSearchString(), request.getPageable(), request.getStoreId(), request.getPhoneNumber()).map(GetRewardLogResponse::of);
    }

    /**
     * Reward Point 조회
     *
     * @param request
     * @return Page<GetRewardPointResponse>
     */
    public Page<GetRewardPointResponse> getRewardPoint(GetRewardPointRequest request){
        return pointService.list( request.getSearchString(), request.getStoreId(), request.getPhoneNumber(), request.getPageable() ).map(GetRewardPointResponse::of);
    }

    /**
     * Reward 적립
     *
     * @param request
     */
    public void getPoint(UpdateRewardPointRequest request){
        Pageable pageable = PageRequest.of(0, 5);
        List<GetRewardPointResponse> result = pointService.list( null, request.getStoreId(), request.getPhoneNumber(), pageable ).map(GetRewardPointResponse::of).stream().collect(Collectors.toList());
        if( result.size() == 0 ) pointService.create(storeService.findById( request.getStoreId() ), request.getPhoneNumber(), request.getPoint(), null);
        else pointService.addPoint(request.getPhoneNumber(), request.getStoreId(), request.getPoint());
        // 로그 추가
        logService.create(
                storeService.findById(request.getStoreId()),
                request.getPhoneNumber(),
                request.getPoint(),
                RewardStatus.EARNED,
                request.getType(),
                request.getChannel()
        );
    }

    /**
     * Reward 사용
     *
     * @param request
     */
    public void usePoint(UpdateRewardPointRequest request){
        pointService.usePoint( request.getPhoneNumber(), request.getStoreId(), request.getPoint() );
        logService.create(
                storeService.findById(request.getStoreId()),
                request.getPhoneNumber(),
                request.getPoint(),
                RewardStatus.USED,
                request.getType(),
                request.getChannel()
        );
    }
    
}
