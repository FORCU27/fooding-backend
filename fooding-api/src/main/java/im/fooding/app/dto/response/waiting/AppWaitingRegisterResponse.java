package im.fooding.app.dto.response.waiting;

import io.swagger.v3.oas.annotations.media.Schema;

public record AppWaitingRegisterResponse(
        @Schema(description = "호출 번호", example = "1")
        long callNumber
) {


    public static AppWaitingRegisterResponse from(AppWaitingRegisterServiceResponse serviceResponse) {
        return new AppWaitingRegisterResponse(serviceResponse.callNumber());
    }
}
