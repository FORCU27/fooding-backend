package im.fooding.core.service.review;

import im.fooding.core.repository.review.ReviewLikeRepository;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class ReviewLikeService {

    private final ReviewLikeRepository reviewLikeRepository;

    /**
     *
     * @param reviewIds
     * @return
     */
    public Map<Long, Long> list(List<Long> reviewIds) {
       return reviewLikeRepository.list(reviewIds);
    }
}
