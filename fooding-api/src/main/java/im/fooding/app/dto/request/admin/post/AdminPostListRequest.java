package im.fooding.app.dto.request.admin.post;

import im.fooding.core.common.BasicSearch;
import im.fooding.core.model.post.PostType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AdminPostListRequest extends BasicSearch {
    @Schema(description = "게시글 타입", example = "NOTICE")
    private PostType type;
}