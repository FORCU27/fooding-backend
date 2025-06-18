package im.fooding.app.dto.response.app.waiting;

import im.fooding.core.model.waiting.StoreWaiting;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppWaitingStatusResponse {
    @Schema(name = "웨이팅 사용자 전화번호" )
    private String phoneNumber;

    @Schema(name = "웨이팅 사용자 이름")
    private String name;

    @Schema(name = "웨이팅이 접수된 시간")
    private LocalDateTime createdAt;

    public static AppWaitingStatusResponse of(StoreWaiting storeWaiting){
        return AppWaitingStatusResponse.builder()
                .phoneNumber( storeWaiting.getUser().getPhoneNumber() )
                .name( storeWaiting.getUser().getName() )
                .createdAt( storeWaiting.getCreatedAt() )
                .build();
    }
}
