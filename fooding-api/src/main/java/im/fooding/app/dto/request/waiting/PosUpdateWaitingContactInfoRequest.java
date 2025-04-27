package im.fooding.app.dto.request.waiting;

import io.swagger.v3.oas.annotations.media.Schema;

public record PosUpdateWaitingContactInfoRequest(
        @Schema(description = "웨이팅 유저 이름", example = "홍길동")
        String name,

        @Schema(description = "휴대폰 번호", example = "01012345678")
        String phoneNumber
) {
}
