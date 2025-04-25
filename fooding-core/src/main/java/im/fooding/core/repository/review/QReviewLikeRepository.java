package im.fooding.core.repository.review;

import java.util.List;
import java.util.Map;

public interface QReviewLikeRepository {
    /**
     *
     * @param reviewIds
     * @return
     */
    Map<Long, Long> list(List<Long> reviewIds);


}
