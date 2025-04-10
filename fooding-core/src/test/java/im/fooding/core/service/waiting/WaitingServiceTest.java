package im.fooding.core.service.waiting;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.junit.jupiter.api.Assertions.*;

import im.fooding.core.TestConfig;
import im.fooding.core.global.exception.ApiException;
import im.fooding.core.global.exception.ErrorCode;
import im.fooding.core.model.store.Store;
import im.fooding.core.model.waiting.Waiting;
import im.fooding.core.model.waiting.WaitingStatus;
import im.fooding.core.repository.store.StoreRepository;
import im.fooding.core.repository.waiting.WaitingRepository;
import im.fooding.core.support.dummy.StoreDummy;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.TestConstructor;

@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@RequiredArgsConstructor
class WaitingServiceTest extends TestConfig {

    private final WaitingService waitingService;
    private final WaitingRepository waitingRepository;
    private final StoreRepository storeRepository;

    @AfterEach
    void tearDown() {
        waitingRepository.deleteAll();
        storeRepository.deleteAll();
    }

    @Test
    @DisplayName("웨이팅이 없으면 오픈 상태 검증을 할 수 없다.")
    public void throw_exception_when_validate_opened() {
        // given
        Store store = storeRepository.save(StoreDummy.create());

        // when & then
        ApiException exception = assertThrows(
                ApiException.class,
                () -> waitingService.validateOpened(store)
        );

        assertThat(exception.getErrorCode())
                .isEqualTo(ErrorCode.WAITING_NOT_FOUND);
    }

    @Test
    @DisplayName("웨이팅이 오픈 상태인 경우를 검증을 할 수 있다.")
    public void validate_when_opened() {
        // given
        Store store = storeRepository.save(StoreDummy.create());
        waitingRepository.save(new Waiting(store, WaitingStatus.WAITING_OPEN));

        // when & then
        assertThatCode(() -> waitingService.validateOpened(store))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("웨이팅이 오픈 상태가 아닌 경우를 검증을 할 수 있다.")
    public void validate_when_not_opened() {
        // given
        Store store = storeRepository.save(StoreDummy.create());
        waitingRepository.save(new Waiting(store, WaitingStatus.PAUSED));

        // when & then
        ApiException exception = assertThrows(
                ApiException.class,
                () -> waitingService.validateOpened(store)
        );

        assertThat(exception.getErrorCode())
                .isEqualTo(ErrorCode.WAITING_NOT_OPENED);
    }
}
