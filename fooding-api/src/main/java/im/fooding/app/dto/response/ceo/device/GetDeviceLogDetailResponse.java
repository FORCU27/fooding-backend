package im.fooding.app.dto.response.ceo.device;

import im.fooding.core.model.device.Device;
import im.fooding.core.model.device.DeviceLog;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetDeviceLogDetailResponse {
    private Long logId;
    private String operation;
    private String deviceName;
    private String osVersion;
    private String uuid;
    private LocalDateTime installedAt;
    private LocalDateTime lastConnectedAt;

    public static GetDeviceLogDetailResponse of(DeviceLog deviceLog, Device device) {
        return GetDeviceLogDetailResponse.builder()
                .logId( deviceLog.getId() )
                .operation( deviceLog.getOperation() )
                .deviceName( device.getName() )
                .osVersion( device.getOsVersion() )
                .uuid( device.getUuid() )
                .installedAt( device.getInstalledAt() )
                .lastConnectedAt( device.getLastConnectedAt() )
                .build();
    }
}
