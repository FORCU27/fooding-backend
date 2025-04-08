package im.fooding.app.dto.response.waiting;

import io.swagger.v3.oas.annotations.media.Schema;

public record WaitingRegisterResponse(
        @Schema(description = "호출 번호", example = "1")
        long callNumber
) {


    public static WaitingRegisterResponse from(WaitingRegisterServiceResponse serviceResponse) {
        return new WaitingRegisterResponse(serviceResponse.callNumber());
    }
}
