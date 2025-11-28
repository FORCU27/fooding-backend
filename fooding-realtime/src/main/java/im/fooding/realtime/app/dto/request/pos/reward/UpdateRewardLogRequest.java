package im.fooding.realtime.app.dto.request.pos.reward;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UpdateRewardLogRequest {
    @Schema( description = "메모" )
    private String memo;
}
