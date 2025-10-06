package im.fooding.app.service.ceo.post;

import im.fooding.app.dto.request.ceo.post.CeoPostListRequest;
import im.fooding.app.dto.response.ceo.post.CeoPostResponse;
import im.fooding.core.common.PageInfo;
import im.fooding.core.common.PageResponse;
import im.fooding.core.model.post.Post;
import im.fooding.core.service.post.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class CeoPostService {
    private final PostService postService;

    public PageResponse<CeoPostResponse> list(CeoPostListRequest request) {
        Page<Post> page = postService.list(
                request.getSearchString(),
                request.getPageable(),
                request.getType(),
                true
        );

        List<CeoPostResponse> responses = page.stream()
                .map(CeoPostResponse::from)
                .toList();

        return PageResponse.of(responses, PageInfo.of(page));
    }
}
