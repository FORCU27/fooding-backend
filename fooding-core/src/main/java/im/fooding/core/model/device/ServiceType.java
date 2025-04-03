package im.fooding.core.model.device;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ServiceType {
    REWARD_MANAGEMENT, REWARD_RECEIPT, WAITING_MANAGEMENT, WAITING_RECEIPT
}