package im.fooding.app.dto.request.ceo.device;

import im.fooding.core.common.BasicSearch;
import im.fooding.core.model.device.ServiceType;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetDeviceLogsRequest extends BasicSearch {
    @NotNull
    private Long deviceId;

    private ServiceType serviceType;
}
