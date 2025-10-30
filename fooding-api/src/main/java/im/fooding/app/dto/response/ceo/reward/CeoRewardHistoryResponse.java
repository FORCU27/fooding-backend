package im.fooding.app.dto.response.ceo.reward;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class CeoRewardHistoryResponse {
    private Long id;
    private String phoneNumber;
    private Long storeId;
    private String rewardType;      // Coupon or Point
    private String channel;         // 우선 온라인으로 픽스
    private String category;        // 방문 or 이벤트
    private LocalDateTime createdAt;
    private String target;          // 쿠폰 이름 or 포인트량
    private String operation;       // 사용 or 취소 / 요청 or 승인 or 반려
}
