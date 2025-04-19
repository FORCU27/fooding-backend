package im.fooding.core.model.waiting;

import static org.junit.jupiter.api.Assertions.*;

import im.fooding.core.global.exception.ApiException;
import im.fooding.core.global.exception.ErrorCode;
import im.fooding.core.model.store.Store;
import im.fooding.core.support.dummy.StoreDummy;
import im.fooding.core.support.dummy.StoreWaitingDummy;
import im.fooding.core.support.dummy.WaitingUserDummy;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class StoreWaitingTest {

    @Test
    @DisplayName("착석할 수 있다.")
    public void testSeat() {
        // given
        StoreWaiting storeWaiting = StoreWaiting.builder().build();

        // when
        storeWaiting.seat();

        // then
        Assertions.assertThat(storeWaiting.getStatus())
                .isEqualTo(StoreWaitingStatus.SEATED);
    }

    @Test
    @DisplayName("웨이팅 상태가 아닌 경우 착석 처리할 수 없다.")
    public void testSeat_fail_whenStatusIsNotWaiting() {
        // given
        StoreWaiting storeWaiting = StoreWaiting.builder().build();

        // when
        storeWaiting.seat();

        // then
        ApiException e = assertThrows(ApiException.class, storeWaiting::seat);
        Assertions.assertThat(e.getErrorCode())
                .isEqualTo(ErrorCode.STORE_WAITING_ILLEGAL_STATE_SEAT);
    }
}
