package im.fooding.app.dto.request.ceo.review;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CeoReplyRequest {
    @NotNull
    private String content;

    @NotNull
    private Long userId;
}
