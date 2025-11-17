package im.fooding.app.dto.request.ceo.review;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CeoReplyUpdateRequest {
    @NotNull
    private String content;
}
