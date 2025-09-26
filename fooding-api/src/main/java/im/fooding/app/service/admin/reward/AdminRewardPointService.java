package im.fooding.app.service.admin.reward;

import im.fooding.app.dto.request.admin.reward.AdminCreateRewardPointRequest;
import im.fooding.app.dto.request.admin.reward.AdminSearchRewardPointRequest;
import im.fooding.app.dto.request.admin.reward.AdminUpdateRewardPointRequest;
import im.fooding.app.dto.response.admin.reward.AdminRewardPointResponse;
import im.fooding.core.common.PageInfo;
import im.fooding.core.common.PageResponse;
import im.fooding.core.event.reward.RewardUseEvent;
import im.fooding.core.global.kafka.EventProducerService;
import im.fooding.core.model.reward.RewardPoint;
import im.fooding.core.model.store.Store;
import im.fooding.core.model.user.User;
import im.fooding.core.repository.reward.RewardPointRepository;
import im.fooding.core.repository.store.StoreRepository;
import im.fooding.core.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminRewardPointService {
    private final RewardPointRepository rewardPointRepository;
    private final StoreRepository storeRepository;
    private final UserRepository userRepository;
    private final EventProducerService eventProducerService;

    @Transactional(readOnly = true)
    public PageResponse<AdminRewardPointResponse> list(AdminSearchRewardPointRequest request) {
        Pageable pageable = request.getPageable();
        Page<RewardPoint> page = rewardPointRepository.list(
                request.getSearchString(), pageable, request.getStoreId(), request.getPhoneNumber()
        );
        return PageResponse.of(
                page.map(this::toResponse).toList(),
                PageInfo.of(page)
        );
    }

    @Transactional(readOnly = true)
    public AdminRewardPointResponse retrieve(Long id) {
        RewardPoint rewardPoint = rewardPointRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("리워드 포인트를 찾을 수 없습니다: " + id));
        
        return toResponse(rewardPoint);
    }

    @Transactional
    public Long create(AdminCreateRewardPointRequest request) {
        Store store = storeRepository.findById(request.getStoreId())
                .orElseThrow(() -> new RuntimeException("가게를 찾을 수 없습니다: " + request.getStoreId()));
        
        User user = null;
        if (request.getUserId() != null) {
            user = userRepository.findById(request.getUserId())
                    .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다: " + request.getUserId()));
        }
        
        RewardPoint rewardPoint = RewardPoint.builder()
                .store(store)
                .phoneNumber(request.getPhoneNumber())
                .user(user)
                .point(request.getPoint())
                .memo(request.getMemo())
                .build();
        
        RewardPoint saved = rewardPointRepository.save(rewardPoint);
        return saved.getId();
    }

    @Transactional
    public void update(Long id, AdminUpdateRewardPointRequest request) {
        RewardPoint rewardPoint = rewardPointRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("리워드 포인트를 찾을 수 없습니다: " + id));
        
        if (request.getStoreId() != null) {
            Store store = storeRepository.findById(request.getStoreId())
                    .orElseThrow(() -> new RuntimeException("가게를 찾을 수 없습니다: " + request.getStoreId()));
            // Store는 변경 불가능하므로 업데이트하지 않음
        }
        
        if (request.getPhoneNumber() != null) {
            // PhoneNumber는 변경 불가능하므로 업데이트하지 않음
        }
        
        if (request.getUserId() != null) {
            User user = userRepository.findById(request.getUserId())
                    .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다: " + request.getUserId()));
            // User는 변경 불가능하므로 업데이트하지 않음
        }
        
        if (request.getPoint() != null) {
            // Point는 변경 불가능하므로 업데이트하지 않음
        }
        
        if (request.getMemo() != null) {
            rewardPoint.updateMemo(request.getMemo());
        }
    }

    @Transactional
    public void delete(Long id, Long adminId) {
        RewardPoint rewardPoint = rewardPointRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("리워드 포인트를 찾을 수 없습니다: " + id));
        rewardPoint.delete(adminId);
        rewardPointRepository.save(rewardPoint);
    }

    @Transactional
    public void addPoint(Long id, int point) {
        RewardPoint rewardPoint = rewardPointRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("리워드 포인트를 찾을 수 없습니다: " + id));
        
        rewardPoint.addPoint(point);
    }

    @Transactional
    public void usePoint(Long id, int point) {
        RewardPoint rewardPoint = rewardPointRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("리워드 포인트를 찾을 수 없습니다: " + id));
        
        rewardPoint.usePoint(point);

        eventProducerService.publishEvent(
                RewardUseEvent.class.getSimpleName(),
                new RewardUseEvent(id, point, rewardPoint.getPoint())
        );
    }

    private AdminRewardPointResponse toResponse(RewardPoint rewardPoint) {
        return AdminRewardPointResponse.builder()
                .id(rewardPoint.getId())
                .storeId(rewardPoint.getStore().getId())
                .storeName(rewardPoint.getStore().getName())
                .phoneNumber(rewardPoint.getPhoneNumber())
                .userId(rewardPoint.getUser() != null ? rewardPoint.getUser().getId() : null)
                .userName(rewardPoint.getUser() != null ? rewardPoint.getUser().getName() : null)
                .point(rewardPoint.getPoint())
                .memo(rewardPoint.getMemo())
                .createdAt(rewardPoint.getCreatedAt())
                .updatedAt(rewardPoint.getUpdatedAt())
                .build();
    }
}
