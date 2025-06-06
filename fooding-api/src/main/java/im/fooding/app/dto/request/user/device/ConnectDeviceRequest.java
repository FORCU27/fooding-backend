package im.fooding.app.dto.request.user.device;

import im.fooding.core.model.device.DevicePlatform;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record ConnectDeviceRequest(
        @Schema(description = "디바이스 고유 uuid")
        String uuid,

        @Schema(description = "디바이스 이름")
        String name,

        @NotNull
        @Schema(description = "디바이스 종류")
        DevicePlatform type,

        @NotNull
        @Schema(description = "OS 버젼")
        String osVersion,

        @NotNull
        @Schema(description = "앱 버젼")
        String appVersion,

        @NotNull
        @Schema(description = "패키지 이름")
        String packageName,

        @Schema(description = "디바이스 ID")
        Long deviceId,

        @Schema(description = "스토어 ID")
        Long storeId
) {

}
