package im.fooding.core.service.waiting;

import static org.junit.jupiter.api.Assertions.*;

import im.fooding.core.TestConfig;
import im.fooding.core.global.exception.ApiException;
import im.fooding.core.global.exception.ErrorCode;
import im.fooding.core.model.store.Store;
import im.fooding.core.model.waiting.StoreWaiting;
import im.fooding.core.model.waiting.WaitingUser;
import im.fooding.core.repository.waiting.StoreRepository;
import im.fooding.core.repository.waiting.StoreWaitingRepository;
import im.fooding.core.repository.waiting.WaitingUserRepository;
import im.fooding.core.support.dummy.StoreDummy;
import im.fooding.core.support.dummy.StoreWaitingDummy;
import im.fooding.core.support.dummy.WaitingUserDummy;
import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.TestConstructor.AutowireMode;

@RequiredArgsConstructor
@TestConstructor(autowireMode = AutowireMode.ALL)
class StoreWaitingServiceTest extends TestConfig {

    private final StoreWaitingService storeWaitingService;
    private final StoreWaitingRepository storeWaitingRepository;
    private final StoreRepository storeRepository;
    private final WaitingUserRepository waitingUserRepository;

    @AfterEach
    void tearDown() {
        storeWaitingRepository.deleteAll();
        storeRepository.deleteAll();
        waitingUserRepository.deleteAll();
    }

    @Test
    @DisplayName("웨이팅을 조회할 수 있다.")
    public void testGetStoreWaiting() {
        // given
        Store store = storeRepository.save(StoreDummy.create());
        WaitingUser user = waitingUserRepository.save(WaitingUserDummy.create(store));
        StoreWaiting storeWaiting = storeWaitingRepository.save(StoreWaitingDummy.create(user, store));

        // when & then
        Assertions.assertThatCode(() -> storeWaitingService.getStoreWaiting(storeWaiting.getId()))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("없는 웨이팅을 조회할 수 있다.")
    public void testGetStoreWaiting_fail() {
        // when & then
        ApiException e = assertThrows(
                ApiException.class,
                () -> storeWaitingService.getStoreWaiting(1L)
        );
        Assertions.assertThat(e.getErrorCode())
                .isEqualTo(ErrorCode.STORE_WAITING_NOT_FOUND);
    }
}
