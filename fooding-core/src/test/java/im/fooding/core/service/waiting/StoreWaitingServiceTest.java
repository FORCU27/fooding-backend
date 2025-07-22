package im.fooding.core.service.waiting;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import im.fooding.core.TestConfig;
import im.fooding.core.dto.request.waiting.StoreWaitingRegisterRequest;
import im.fooding.core.global.exception.ApiException;
import im.fooding.core.global.exception.ErrorCode;
import im.fooding.core.model.region.Region;
import im.fooding.core.model.store.Store;
import im.fooding.core.model.waiting.StoreWaiting;
import im.fooding.core.model.waiting.StoreWaitingChannel;
import im.fooding.core.model.waiting.Waiting;
import im.fooding.core.model.waiting.WaitingStatus;
import im.fooding.core.model.waiting.WaitingUser;
import im.fooding.core.repository.region.RegionRepository;
import im.fooding.core.repository.store.StoreRepository;
import im.fooding.core.repository.waiting.StoreWaitingRepository;
import im.fooding.core.repository.waiting.WaitingRepository;
import im.fooding.core.common.BasicSearch;
import im.fooding.core.dto.request.waiting.StoreWaitingFilter;
import im.fooding.core.model.waiting.StoreWaitingStatus;
import im.fooding.core.repository.waiting.WaitingUserRepository;
import im.fooding.core.support.dummy.RegionDummy;
import im.fooding.core.support.dummy.StoreDummy;
import im.fooding.core.support.dummy.StoreWaitingDummy;
import im.fooding.core.support.dummy.WaitingUserDummy;
import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.TestConstructor.AutowireMode;

@TestConstructor(autowireMode = AutowireMode.ALL)
@RequiredArgsConstructor
class StoreWaitingServiceTest extends TestConfig {

    private final StoreWaitingService storeWaitingService;
    private final StoreWaitingRepository storeWaitingRepository;
    private final StoreRepository storeRepository;
    private final WaitingUserRepository waitingUserRepository;
    private final WaitingRepository waitingRepository;
    private final RegionRepository regionRepository;

    @AfterEach
    void tearDown() {
        storeWaitingRepository.deleteAll();
        storeRepository.deleteAll();
        waitingUserRepository.deleteAll();
        waitingRepository.deleteAll();
        regionRepository.deleteAll();
    }

    @Test
    @DisplayName("가게 id로 특정 상태의 가게의 웨이팅을 모두 가져올 수 있다.")
    void  get_all_store_waiting_with_specific_status() {
        // given
        Region region = regionRepository.save(RegionDummy.create());
        Store store = storeRepository.save(StoreDummy.create(region));
        WaitingUser waitingUser = waitingUserRepository.save(WaitingUserDummy.create(store));

        StoreWaiting storeWaiting = StoreWaiting.builder()
                .waitingUser(waitingUser)
                .store(store)
                .callNumber(1)
                .status(StoreWaitingStatus.WAITING)
                .channel(StoreWaitingChannel.IN_PERSON)
                .infantChairCount(1)
                .infantCount(1)
                .adultCount(1)
                .memo("")
                .build();

        StoreWaiting cancledStoreWaiting = StoreWaiting.builder()
                .waitingUser(waitingUser)
                .store(store)
                .callNumber(2)
                .status(StoreWaitingStatus.WAITING)
                .channel(StoreWaitingChannel.IN_PERSON)
                .infantChairCount(1)
                .infantCount(1)
                .adultCount(1)
                .memo("")
                .build();
        cancledStoreWaiting.cancel();

        storeWaitingRepository.save(storeWaiting);
        storeWaitingRepository.save(cancledStoreWaiting);

        BasicSearch search = new BasicSearch();

        StoreWaitingFilter waitingFilter = StoreWaitingFilter.builder()
                .storeId(store.getId())
                .status(StoreWaitingStatus.WAITING)
                .build();

        // when
        Page<StoreWaiting> waitingStoreWaitings = storeWaitingService.list(
                waitingFilter,
                search.getPageable()
        );

        StoreWaitingFilter cancledFilter = StoreWaitingFilter.builder()
                .storeId(store.getId())
                .status(StoreWaitingStatus.CANCELLED)
                .build();
        Page<StoreWaiting> cancledStoreWaitings = storeWaitingService.list(
                cancledFilter,
                search.getPageable()
        );

        // then
        StoreWaitingStatus waitingStoreWaitingStatus = waitingStoreWaitings.getContent()
                .get(0)
                .getStatus();
        StoreWaitingStatus cancledStoreWaitingStatus = cancledStoreWaitings.getContent()
                .get(0)
                .getStatus();

        Assertions.assertThat(waitingStoreWaitings)
                .hasSize(1);
        Assertions.assertThat(waitingStoreWaitingStatus)
                .isEqualTo(StoreWaitingStatus.WAITING);
        Assertions.assertThat(cancledStoreWaitings)
                .hasSize(1);
        Assertions.assertThat(cancledStoreWaitingStatus)
                .isEqualTo(StoreWaitingStatus.CANCELLED);
    }

    @Test
    @DisplayName("웨이팅을 조회할 수 있다.")
    public void testGetStoreWaiting() {
        // given
        Region region = regionRepository.save(RegionDummy.create());
        Store store = storeRepository.save(StoreDummy.create(region));
        WaitingUser user = waitingUserRepository.save(WaitingUserDummy.create(store));
        StoreWaiting storeWaiting = storeWaitingRepository.save(StoreWaitingDummy.create(user, store));

        // when & then
        Assertions.assertThatCode(() -> storeWaitingService.get(storeWaiting.getId()))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("없는 웨이팅을 조회할 수 있다.")
    public void testGetStoreWaiting_fail() {
        // when & then
        ApiException e = assertThrows(
                ApiException.class,
                () -> storeWaitingService.get(1L)
        );
        Assertions.assertThat(e.getErrorCode())
                .isEqualTo(ErrorCode.STORE_WAITING_NOT_FOUND);
    }

    @Test
    @DisplayName("웨이팅 등록시 가게의 현재 웨이팅 개수 + 1의 호출 번호로 등록된다.")
    public void callNumber_is_current_waiting_count_plus_1_when_register() {
        // given
        Region region = regionRepository.save(RegionDummy.create());
        Store store = storeRepository.save(StoreDummy.create(region));
        WaitingUser waitingUser1 = waitingUserRepository.save(WaitingUserDummy.createWithPhoneNumber(store, "01012345678"));
        WaitingUser waitingUser2 = waitingUserRepository.save(WaitingUserDummy.createWithPhoneNumber(store, "01023456789"));

        StoreWaitingRegisterRequest user1Request = StoreWaitingRegisterRequest.builder()
                .waitingUser(waitingUser1)
                .store(store)
                .channel(StoreWaitingChannel.IN_PERSON.getValue())
                .infantChairCount(1)
                .infantCount(1)
                .adultCount(1)
                .build();

        StoreWaitingRegisterRequest user2Request = StoreWaitingRegisterRequest.builder()
                .waitingUser(waitingUser2)
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
        Region region = regionRepository.save(RegionDummy.create());
        Store store = storeRepository.save(StoreDummy.create(region));
        Waiting waiting = waitingRepository.save(new Waiting(store, WaitingStatus.WAITING_OPEN));

        // when & then
        assertThatCode(() -> storeWaitingService.validate(waiting))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("웨이팅이 오픈 상태가 아닌 경우를 검증에 실패한다.")
    public void validate_when_not_opened() {
        // given
        Region region = regionRepository.save(RegionDummy.create());
        Store store = storeRepository.save(StoreDummy.create(region));
        Waiting waiting = waitingRepository.save(new Waiting(store, WaitingStatus.PAUSED));

        // when & then
        ApiException exception = assertThrows(
                ApiException.class,
                () -> storeWaitingService.validate(waiting)
        );

        assertThat(exception.getErrorCode())
                .isEqualTo(ErrorCode.WAITING_NOT_OPENED);
    }

    @Test
    @DisplayName("웨이팅을 취소할 수 있다.")
    public void testCancel() {
        // given
        Region region = regionRepository.save(RegionDummy.create());
        Store store = storeRepository.save(StoreDummy.create(region));
        WaitingUser user = waitingUserRepository.save(WaitingUserDummy.create(store));
        StoreWaiting storeWaiting = storeWaitingRepository.save(StoreWaitingDummy.create(user, store));

        // when
        storeWaitingService.cancel(storeWaiting.getId());

        // then
        Assertions.assertThat(storeWaiting.getStatus())
                .isEqualTo(StoreWaitingStatus.CANCELLED);
    }

    @Test
    @DisplayName("가게 웨이팅을 착석 처리할 수 있다.")
    public void testSeat() {
        // given
        Region region = regionRepository.save(RegionDummy.create());
        Store store = storeRepository.save(StoreDummy.create(region));
        WaitingUser user = waitingUserRepository.save(WaitingUserDummy.create(store));
        StoreWaiting storeWaiting = storeWaitingRepository.save(spy(StoreWaitingDummy.create(user, store)));

        // when
        storeWaitingService.seat(storeWaiting.getId());

        // then
        verify(storeWaiting).seat();
    }

    @Test
    @DisplayName("호출시 호출 횟수를 증가한다.")
    public void testCall() {
        // given
        Region region = regionRepository.save(RegionDummy.create());
        Store store = storeRepository.save(StoreDummy.create(region));
        WaitingUser user = waitingUserRepository.save(WaitingUserDummy.create(store));
        StoreWaiting storeWaiting = storeWaitingRepository.save(StoreWaitingDummy.create(user, store));

        // when
        storeWaitingService.call(storeWaiting.getId());

        // then
        Assertions.assertThat(storeWaiting.getCallCount())
                .isEqualTo(1);
    }
}
