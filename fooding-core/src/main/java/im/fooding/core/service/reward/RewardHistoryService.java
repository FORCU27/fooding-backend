package im.fooding.core.service.reward;

import im.fooding.core.model.reward.RewardHistory;
import im.fooding.core.repository.reward.RewardHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RewardHistoryService {
    private final RewardHistoryRepository repository;

    // 로그 조회
    public List<RewardHistory> list( Long storeId, String phoneNumber, Boolean isCoupon ){
        return repository.list( storeId, phoneNumber, isCoupon );
    }

    // 로그 생성
    public void create( RewardHistory history ) { this.repository.save( history ); }

    // 로그 메모 업데이트
    @Transactional
    public void update( long historyId, String memo ){
        RewardHistory history = repository.findById( historyId ).orElse( null );
        if( history != null ) history.updateMemo( memo );
    }

    // 단일 로그 조회
    public RewardHistory findById( long historyId ) { return repository.findById( historyId ).orElse( null ); }
}
