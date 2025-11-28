package im.fooding.realtime.app.dto.request.pos.reward;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreatePosRewardRequest {
    @NotNull
    @Schema( description = "전화번호" )
    String phoneNumber;

    @NotNull
    @Schema( description = "적립 포인트" )
    int point;

    @Schema( description = "사용자 ID" )
    Long userId;

}
