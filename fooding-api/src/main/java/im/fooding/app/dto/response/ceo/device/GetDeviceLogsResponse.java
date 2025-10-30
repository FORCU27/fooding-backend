package im.fooding.app.dto.response.ceo.device;

import im.fooding.core.model.device.DeviceLog;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetDeviceLogsResponse {
    private Long logId;
    private Long deviceId;
    private String date;
    private String time;
    private String operation;

    public static GetDeviceLogsResponse of(DeviceLog log){
        return GetDeviceLogsResponse.builder()
                .logId( log.getId() )
                .deviceId( log.getDeviceId() )
                .date( log.getDate() )
                .time( log.getTime() )
                .operation( log.getOperation() ).build();
    }
}
