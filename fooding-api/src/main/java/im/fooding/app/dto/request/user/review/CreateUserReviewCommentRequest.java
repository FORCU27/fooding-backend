package im.fooding.app.dto.request.user.review;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateUserReviewCommentRequest {
    @Schema( description = "상위 댓글의 ID" )
    private Long parentId;
    @Schema( description = "댓글 내용" )
    private String comment;

}
