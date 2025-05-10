package im.fooding.core.model.waiting;

import static org.junit.jupiter.api.Assertions.*;

import im.fooding.core.global.exception.ApiException;
import im.fooding.core.global.exception.ErrorCode;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class StoreWaitingTest {

    @Test
    @DisplayName("웨이팅 상태로 되돌릴 수 있다.")
    void testRevert() {
        // given
        StoreWaiting storeWaiting = StoreWaiting.builder()
                .status(StoreWaitingStatus.SEATED)
                .build();

        // when
        storeWaiting.revert();

        // then
        Assertions.assertThat(storeWaiting.getStatus())
                .isEqualTo(StoreWaitingStatus.WAITING);
    }

    @Test
    @DisplayName("되돌릴 수 있는 상태가 아닌 경우 되돌릴 수 없다.")
    void testRevert_fail_whenAlreadyWaiting() {
        // given
        StoreWaiting storeWaiting = StoreWaiting.builder()
                .status(StoreWaitingStatus.WAITING)
                .build();

        // when & then
        ApiException e = assertThrows(ApiException.class, storeWaiting::revert);
        Assertions.assertThat(e.getErrorCode())
                .isEqualTo(ErrorCode.STORE_WAITING_ILLEGAL_STATE_REVERT);
    }

    @Test
    @DisplayName("착석할 수 있다.")
    public void testSeat() {
        // given
        StoreWaiting storeWaiting = StoreWaiting.builder()
                .status(StoreWaitingStatus.WAITING)
                .build();

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
        StoreWaiting storeWaiting = StoreWaiting.builder()
                .status(StoreWaitingStatus.SEATED)
                .build();

        // when & then
        ApiException e = assertThrows(ApiException.class, storeWaiting::seat);
        Assertions.assertThat(e.getErrorCode())
                .isEqualTo(ErrorCode.STORE_WAITING_ILLEGAL_STATE_SEAT);
    }

    @Test
    @DisplayName("웨이팅을 취소할 수 있다.")
    public void testCancel() {
        // given
        StoreWaiting storeWaiting = StoreWaiting.builder()
                .status(StoreWaitingStatus.WAITING)
                .build();

        // when
        storeWaiting.cancel();

        // then
        Assertions.assertThat(storeWaiting.getStatus())
                .isEqualTo(StoreWaitingStatus.CANCELLED);
    }

    @Test
    @DisplayName("웨이팅 상태가 아닌 경우 웨이팅을 취소할 수 없다.")
    public void testCancel_fail_whenStatusIsNotWaiting() {
        // given
        StoreWaiting storeWaiting = StoreWaiting.builder()
                .status(StoreWaitingStatus.CANCELLED)
                .build();

        // when & then
        ApiException e = assertThrows(
                ApiException.class,
                storeWaiting::cancel
        );
        Assertions.assertThat(e.getErrorCode())
                .isEqualTo(ErrorCode.STORE_WAITING_ILLEGAL_STATE_CANCEL);
    }
}
