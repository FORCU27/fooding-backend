package im.fooding.core.service.waiting;

import im.fooding.core.TestConfig;
import im.fooding.core.common.BasicSearch;
import im.fooding.core.model.store.Store;
import im.fooding.core.model.waiting.StoreWaiting;
import im.fooding.core.model.waiting.StoreWaitingChannel;
import im.fooding.core.model.waiting.StoreWaitingStatus;
import im.fooding.core.model.waiting.WaitingUser;
import im.fooding.core.repository.waiting.StoreRepository;
import im.fooding.core.repository.waiting.StoreWaitingRepository;
import im.fooding.core.repository.waiting.WaitingUserRepository;
import im.fooding.core.support.dummy.StoreDummy;
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
    private final WaitingUserRepository waitingUserRepository;
    private final StoreRepository storeRepository;

    @AfterEach
    void tearDown() {
        storeWaitingRepository.deleteAll();
        waitingUserRepository.deleteAll();
        storeRepository.deleteAll();
    }

    @Test
    @DisplayName("가게 id로 특정 상태의 가게의 웨이팅을 모두 가져올 수 있다.")
    void  get_all_store_waiting_with_specific_status() {
        // given
        Store store = storeRepository.save(StoreDummy.create());
        WaitingUser waitingUser = waitingUserRepository.save(WaitingUserDummy.create(store));

        StoreWaiting storeWaiting = StoreWaiting.builder()
                .user(waitingUser)
                .store(store)
                .callNumber(1)
                .channel(StoreWaitingChannel.IN_PERSON)
                .infantChairCount(1)
                .infantCount(1)
                .adultCount(1)
                .build();

        StoreWaiting cancledStoreWaiting = StoreWaiting.builder()
                .user(waitingUser)
                .store(store)
                .callNumber(2)
                .channel(StoreWaitingChannel.IN_PERSON)
                .infantChairCount(1)
                .infantCount(1)
                .adultCount(1)
                .build();
        cancledStoreWaiting.cancel();

        storeWaitingRepository.save(storeWaiting);
        storeWaitingRepository.save(cancledStoreWaiting);

        BasicSearch search = new BasicSearch();

        // when
        Page<StoreWaiting> waitingStoreWaitings = storeWaitingService.getAllByStoreIdAndStatus(
                store.getId(),
                StoreWaitingStatus.WAITING.getValue(),
                search.getPageable()
        );
        Page<StoreWaiting> cancledStoreWaitings = storeWaitingService.getAllByStoreIdAndStatus(
                store.getId(),
                StoreWaitingStatus.CANCELLED.getValue(),
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
}
