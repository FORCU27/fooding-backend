package im.fooding.app.service.user.recommend;

import im.fooding.app.dto.request.user.recommend.UserRecommendKeywordRequest;
import im.fooding.core.service.keyword.SearchKeywordService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserRecommendService {
    private final SearchKeywordService service;

    @Transactional(readOnly = true)
    public List<String> list(UserRecommendKeywordRequest request) {
        return service.recommendKeywords(request.getKeyword().strip());
    }
}
