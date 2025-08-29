package im.fooding.app.dto.response.user.waiting;

import lombok.Value;

@Value
public class UserStoreWaitingCreateResponse {
    Long storeWaitingId;
    String planId;
}
