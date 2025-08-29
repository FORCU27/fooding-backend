package im.fooding.app.dto.request.admin.reward;

import im.fooding.core.common.BasicSearch;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminSearchRewardPointRequest extends BasicSearch {
    private Long storeId;
    private String phoneNumber;
    private Long userId;
    private String memo;
}
