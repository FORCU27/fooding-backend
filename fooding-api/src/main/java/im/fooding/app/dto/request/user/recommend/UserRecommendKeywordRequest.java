package im.fooding.app.dto.request.user.recommend;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserRecommendKeywordRequest {
    @NotBlank
    @Schema(description = "검색 키워드", example = "고기")
    private String keyword;
}
