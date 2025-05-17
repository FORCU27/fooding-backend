package im.fooding.core.service.reward;

import im.fooding.core.global.exception.ApiException;
import im.fooding.core.global.exception.ErrorCode;
import im.fooding.core.model.reward.RewardChannel;
import im.fooding.core.model.reward.RewardLog;
import im.fooding.core.model.reward.RewardStatus;
import im.fooding.core.model.reward.RewardType;
import im.fooding.core.model.store.Store;
import im.fooding.core.repository.reward.RewardLogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional( readOnly = true )
public class RewardLogService {
    private final RewardLogRepository repository;

    /**
     * 리워드 로그 조회
     * @param storeId
     * @param phoneNumber
     * @param searchString
     * @param pageable
     * @return Page<RewardLog>
     */
    public Page<RewardLog> list(String searchString, Pageable pageable, Long storeId, String phoneNumber ){
        return repository.list( searchString, pageable, storeId, phoneNumber );
    }

    /**
     * 특정 리워드 로그 조회
     * @param logId
     * @return RewardLog
     */
    public RewardLog findById( Long logId ){
        return repository.findById( logId ).orElseThrow( () -> new ApiException(ErrorCode.REWARD_LOG_NOT_FOUND) );
    }

    /**
     * 리워드 로그 생성
     * @param store
     * @param phoneNumber
     * @param point
     * @param status
     * @param type
     * @param channel
     */
    public void create(
            Store store,
            String phoneNumber,
            int point,
            RewardStatus status,
            RewardType type,
            RewardChannel channel
    ){
        RewardLog rewardLog = RewardLog.builder()
                .store( store )
                .phoneNumber( phoneNumber )
                .point( point )
                .status( status )
                .type( type )
                .channel( channel )
                .build();
        repository.save( rewardLog );
    }

    /**
     * 리워드 로그 삭제
     * @param id
     * @param deletedBy
     */
    public void delete( Long id, Long deletedBy ){
        RewardLog log = repository.findById( id ).orElseThrow(
                () -> new ApiException(ErrorCode.REWARD_LOG_NOT_FOUND)
        );
        log.delete( deletedBy );
    }
}
