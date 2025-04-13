package im.fooding.core.service.waiting;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.junit.jupiter.api.Assertions.assertThrows;

import im.fooding.core.TestConfig;
import im.fooding.core.dto.request.waiting.StoreWaitingRegisterRequest;
import im.fooding.core.global.exception.ApiException;
import im.fooding.core.global.exception.ErrorCode;
import im.fooding.core.model.store.Store;
import im.fooding.core.model.waiting.StoreWaiting;
import im.fooding.core.model.waiting.StoreWaitingChannel;
import im.fooding.core.model.waiting.Waiting;
import im.fooding.core.model.waiting.WaitingStatus;
import im.fooding.core.model.waiting.WaitingUser;
import im.fooding.core.repository.store.StoreRepository;
import im.fooding.core.repository.waiting.StoreWaitingRepository;
import im.fooding.core.repository.waiting.WaitingRepository;
import im.fooding.core.repository.waiting.WaitingUserRepository;
import im.fooding.core.support.dummy.StoreDummy;
import im.fooding.core.support.dummy.WaitingUserDummy;
import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.TestConstructor;

@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@RequiredArgsConstructor
class StoreWaitingServiceTest extends TestConfig {

    private final StoreWaitingService storeWaitingService;
    private final StoreWaitingRepository storeWaitingRepository;
    private final StoreRepository storeRepository;
    private final WaitingUserRepository waitingUserRepository;
    private final WaitingRepository waitingRepository;

    @AfterEach
    void tearDown() {
        storeWaitingRepository.deleteAll();
        storeRepository.deleteAll();
        waitingUserRepository.deleteAll();
    }

    @Test
    @DisplayName("웨이팅 등록시 가게의 현재 웨이팅 개수 + 1의 호출 번호로 등록된다.")
    public void callNumber_is_current_waiting_count_plus_1_when_register() {
        // given
        Store store = storeRepository.save(StoreDummy.create());
        WaitingUser user1 = waitingUserRepository.save(WaitingUserDummy.createWithPhoneNumber(store, "01012345678"));
        WaitingUser user2 = waitingUserRepository.save(WaitingUserDummy.createWithPhoneNumber(store, "01023456789"));

        StoreWaitingRegisterRequest user1Request = StoreWaitingRegisterRequest.builder()
                .user(user1)
                .store(store)
                .channel(StoreWaitingChannel.IN_PERSON.getValue())
                .infantChairCount(1)
                .infantCount(1)
                .adultCount(1)
                .build();

        StoreWaitingRegisterRequest user2Request = StoreWaitingRegisterRequest.builder()
                .user(user2)
                .store(store)
                .channel(StoreWaitingChannel.IN_PERSON.getValue())
                .infantChairCount(1)
                .infantCount(1)
                .adultCount(1)
                .build();

        // when
        StoreWaiting user1StoreWaiting = storeWaitingService.register(user1Request);
        StoreWaiting user2StoreWaiting = storeWaitingService.register(user2Request);

        // then
        Assertions.assertThat(user1StoreWaiting.getCallNumber())
                .isEqualTo(1);
        Assertions.assertThat(user2StoreWaiting.getCallNumber())
                .isEqualTo(2);
    }

    @Test
    @DisplayName("웨이팅이 오픈 상태인 경우를 검증에 성공한다.")
    public void validate_when_opened() {
        // given
        Store store = storeRepository.save(StoreDummy.create());
        Waiting waiting = waitingRepository.save(new Waiting(store, WaitingStatus.WAITING_OPEN));

        // when & then
        assertThatCode(() -> storeWaitingService.validate(waiting))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("웨이팅이 오픈 상태가 아닌 경우를 검증에 실패한다.")
    public void validate_when_not_opened() {
        // given
        Store store = storeRepository.save(StoreDummy.create());
        Waiting waiting = waitingRepository.save(new Waiting(store, WaitingStatus.PAUSED));

        // when & then
        ApiException exception = assertThrows(
                ApiException.class,
                () -> storeWaitingService.validate(waiting)
        );

        assertThat(exception.getErrorCode())
                .isEqualTo(ErrorCode.WAITING_NOT_OPENED);
    }
}
