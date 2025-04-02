package im.fooding.core.model.waiting;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum WaitingSystemStatus {
    WAITING_OPEN, IMMEDIATE_ENTRY, PAUSED;
}
