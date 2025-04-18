package im.fooding.app.dto.response.user.device;

import im.fooding.core.model.device.Device;
import im.fooding.core.model.device.DeviceType;
import im.fooding.core.model.device.ServiceType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class StoreDeviceResponse {
    @Schema(description = "id", example = "1" )
    private long id;

    @Schema(description = "디바이스 종류", example = "IOS")
    private DeviceType deviceType;

    @Schema(description = "디바이스 이름", example = "iPad 12")
    private String name;

    @Schema(description = "디바이스 OS 버젼", example = "iOS 17")
    private String osVersion;

    @Schema( description = "설치 일자", example="2025-04-07 17:52:33")
    private LocalDateTime installedAt;

    @Schema(description = "마지막 접속 일자", example="2025-04-09 18:20:45" )
    private LocalDateTime lastConnectedAt;

    @Schema(description = "현재 서비스 타입", example="REWARD_MANAGEMENT")
    private ServiceType serviceType;

    @Builder
    private StoreDeviceResponse(
            long id, DeviceType deviceType,
            String name,
            String osVersion,
            LocalDateTime installedAt,
            LocalDateTime lastConnectedAt,
            ServiceType serviceType
    ){
        this.id = id;
        this.deviceType = deviceType;
        this.name = name;
        this.osVersion = osVersion;
        this.installedAt = installedAt;
        this.lastConnectedAt = lastConnectedAt;
        this.serviceType = serviceType;
    }

    public static StoreDeviceResponse of(Device device){
        return StoreDeviceResponse.builder()
                .id(device.getId())
                .deviceType(device.getType())
                .name(device.getName())
                .osVersion(device.getOsVersion())
                .installedAt(device.getInstalledAt())
                .lastConnectedAt(device.getLastConnectedAt())
                .serviceType(device.getServiceType())
                .build();
    }
}
