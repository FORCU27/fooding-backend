package im.fooding.app.dto.response.admin.service;

import com.fasterxml.jackson.annotation.JsonFormat;
import im.fooding.core.model.store.StoreService;
import im.fooding.core.model.store.StoreServiceType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class StoreServiceResponse {
    @Schema( description = "id" )
    private Long id;

    @Schema( description = "가게 ID" )
    private Long storeId;

    @Schema( description = "가게 이름" )
    private String storeName;

    @Schema( description = "가입 서비스" )
    private StoreServiceType type;

    @Schema( description = "활성화 여부" )
    private boolean activation;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema( description = "가입 날짜" )
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema( description = "해지 날짜" )
    private LocalDateTime endedAt;

    @Builder
    private StoreServiceResponse(
            Long id,
            Long storeId,
            String storeName,
            StoreServiceType type,
            boolean activation,
            LocalDateTime createdAt,
            LocalDateTime endedAt
    ){
        this.id = id;
        this.storeId = storeId;
        this.storeName = storeName;
        this.type = type;
        this.activation = activation;
        this.createdAt = createdAt;
        this.endedAt = endedAt;
    }

    public static StoreServiceResponse of( StoreService storeService ) {
        return StoreServiceResponse.builder()
                .id(storeService.getId())
                .storeId(storeService.getStore().getId())
                .storeName(storeService.getStore().getName())
                .type(storeService.getType())
                .activation(storeService.isActivation())
                .createdAt(storeService.getCreatedAt())
                .endedAt(storeService.getEndedAt())
                .build();
    }
}
