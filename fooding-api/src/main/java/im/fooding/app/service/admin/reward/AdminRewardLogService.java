package im.fooding.app.service.admin.reward;

import im.fooding.app.dto.request.admin.reward.AdminCreateRewardLogRequest;
import im.fooding.app.dto.request.admin.reward.AdminSearchRewardLogRequest;
import im.fooding.app.dto.request.admin.reward.AdminUpdateRewardLogRequest;
import im.fooding.app.dto.response.admin.reward.AdminRewardLogResponse;
import im.fooding.core.common.PageInfo;
import im.fooding.core.common.PageResponse;
import im.fooding.core.model.reward.RewardLog;
import im.fooding.core.model.reward.RewardStatus;
import im.fooding.core.model.store.Store;
import im.fooding.core.repository.reward.RewardLogRepository;
import im.fooding.core.repository.store.StoreRepository;
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
public class AdminRewardLogService {
    private final RewardLogRepository rewardLogRepository;
    private final StoreRepository storeRepository;

    @Transactional(readOnly = true)
    public PageResponse<AdminRewardLogResponse> list(AdminSearchRewardLogRequest request) {
        Pageable pageable = request.getPageable();
        Page<RewardLog> page = rewardLogRepository.list(
                request.getSearchString(), pageable, request.getStoreId(), request.getPhoneNumber(), request.getStatus()
        );
        return PageResponse.of(
                page.map(this::toResponse).toList(),
                PageInfo.of(page)
        );
    }

    @Transactional(readOnly = true)
    public AdminRewardLogResponse retrieve(Long id) {
        RewardLog rewardLog = rewardLogRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("리워드 로그를 찾을 수 없습니다: " + id));
        
        return toResponse(rewardLog);
    }

    @Transactional
    public Long create(AdminCreateRewardLogRequest request) {
        Store store = storeRepository.findById(request.getStoreId())
                .orElseThrow(() -> new RuntimeException("가게를 찾을 수 없습니다: " + request.getStoreId()));
        
        RewardLog rewardLog = RewardLog.builder()
                .store(store)
                .phoneNumber(request.getPhoneNumber())
                .point(request.getPoint())
                .status(request.getStatus())
                .type(request.getType())
                .channel(request.getChannel())
                .memo(request.getMemo())
                .build();
        
        RewardLog saved = rewardLogRepository.save(rewardLog);
        return saved.getId();
    }

    @Transactional
    public void update(Long id, AdminUpdateRewardLogRequest request) {
        RewardLog rewardLog = rewardLogRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("리워드 로그를 찾을 수 없습니다: " + id));
        
        if (request.getStoreId() != null) {
            Store store = storeRepository.findById(request.getStoreId())
                    .orElseThrow(() -> new RuntimeException("가게를 찾을 수 없습니다: " + request.getStoreId()));
            // Store는 변경 불가능하므로 업데이트하지 않음
        }
        
        if (request.getPhoneNumber() != null) {
            // PhoneNumber는 변경 불가능하므로 업데이트하지 않음
        }
        
        if (request.getPoint() != null) {
            // Point는 변경 불가능하므로 업데이트하지 않음
        }
        
        if (request.getStatus() != null) {
            rewardLog.updateStatus(request.getStatus());
        }
        
        if (request.getType() != null) {
            // Type은 변경 불가능하므로 업데이트하지 않음
        }
        
        if (request.getChannel() != null) {
            // Channel은 변경 불가능하므로 업데이트하지 않음
        }
        
        if (request.getMemo() != null) {
            rewardLog.updateMemo(request.getMemo());
        }
    }

    @Transactional
    public void delete(Long id, Long adminId) {
        RewardLog rewardLog = rewardLogRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("리워드 로그를 찾을 수 없습니다: " + id));
        rewardLog.delete(adminId);
        rewardLogRepository.save(rewardLog);
    }

    @Transactional
    public void updateStatus(Long id, String status) {
        RewardLog rewardLog = rewardLogRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("리워드 로그를 찾을 수 없습니다: " + id));
        
        try {
            RewardStatus rewardStatus = RewardStatus.valueOf(status.toUpperCase());
            rewardLog.updateStatus(rewardStatus);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("유효하지 않은 상태값입니다: " + status);
        }
    }

    private AdminRewardLogResponse toResponse(RewardLog rewardLog) {
        return AdminRewardLogResponse.builder()
                .id(rewardLog.getId())
                .storeId(rewardLog.getStore().getId())
                .storeName(rewardLog.getStore().getName())
                .phoneNumber(rewardLog.getPhoneNumber())
                .point(rewardLog.getPoint())
                .status(rewardLog.getStatus())
                .type(rewardLog.getType())
                .channel(rewardLog.getChannel())
                .memo(rewardLog.getMemo())
                .createdAt(rewardLog.getCreatedAt())
                .updatedAt(rewardLog.getUpdatedAt())
                .build();
    }
}
