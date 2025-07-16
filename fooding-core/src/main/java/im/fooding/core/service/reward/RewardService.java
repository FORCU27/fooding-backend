package im.fooding.core.service.reward;

import im.fooding.core.global.exception.ApiException;
import im.fooding.core.global.exception.ErrorCode;
import im.fooding.core.model.reward.RewardPoint;
import im.fooding.core.model.store.Store;
import im.fooding.core.model.user.User;
import im.fooding.core.repository.reward.RewardPointRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class RewardService {
    private final RewardPointRepository repository;

    /**
     * 리워드 조회
     *
     * @param storeId
     * @param phoneNumber
     * @param searchString
     * @param pageable
     * @return Page<RewardPoint>
     */
    public Page<RewardPoint> list(String searchString, Long storeId, String phoneNumber, Pageable pageable) {
        return repository.list(searchString, pageable, storeId, phoneNumber);
    }

    /**
     * 리워드 생성
     *
     * @param store
     * @param phoneNumber
     * @param point
     * @param user
     */
    public void create(Store store, String phoneNumber, int point, User user) {
        RewardPoint reward = RewardPoint.builder()
                .store(store)
                .phoneNumber(phoneNumber)
                .point(point)
                .user(user)
                .build();
        repository.save(reward);
    }

    /**
     * 리워드 포인트 추가
     *
     * @param phoneNumber
     * @param storeId
     * @param point
     */
    @Transactional
    public void addPoint(String phoneNumber, Long storeId, int point) {
        RewardPoint rewardPoint = repository.findByPhoneNumberAndStoreId(
                phoneNumber,
                storeId
        );
        rewardPoint.addPoint(point);
    }

    /**
     * 리워드 포인트 사용
     *
     * @param phoneNumber
     * @param storeId
     * @param point
     */
    @Transactional
    public void usePoint(String phoneNumber, Long storeId, int point) {
        RewardPoint rewardPoint = repository.findByPhoneNumberAndStoreId(
                phoneNumber,
                storeId
        );
        rewardPoint.usePoint(point);
    }

    /**
     * 리워드 정보 삭제
     *
     * @param id
     * @param deletedBy
     * @return
     */
    @Transactional
    public void delete(Long id, Long deletedBy) {
        RewardPoint rewardPoint = repository.findById(id).orElseThrow(
                () -> new ApiException(ErrorCode.REWARD_NOT_FOUND)
        );
        rewardPoint.delete(deletedBy);
    }

    public RewardPoint findByUserIdAndStoreId(long userId, long storeId) {
        return repository.findByUserIdAndStoreIdAndDeletedIsFalse(userId, storeId);
    }
}
