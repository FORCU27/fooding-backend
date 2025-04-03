package im.fooding.core.model.waiting;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum WaitingStatus {
    WAITING_OPEN, IMMEDIATE_ENTRY, PAUSED;
}
