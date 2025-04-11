package im.fooding.core.service.waiting;

import im.fooding.core.TestConfig;
import im.fooding.core.dto.request.waiting.WaitingLogFilter;
import im.fooding.core.model.store.Store;
import im.fooding.core.model.waiting.StoreWaiting;
import im.fooding.core.model.waiting.WaitingLog;
import im.fooding.core.model.waiting.WaitingLogType;
import im.fooding.core.model.waiting.WaitingUser;
import im.fooding.core.repository.waiting.StoreRepository;
import im.fooding.core.repository.waiting.StoreWaitingRepository;
import im.fooding.core.repository.waiting.WaitingLogRepository;
import im.fooding.core.repository.waiting.WaitingUserRepository;
import im.fooding.core.support.dummy.StoreDummy;
import im.fooding.core.support.dummy.StoreWaitingDummy;
import im.fooding.core.support.dummy.WaitingUserDummy;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.TestConstructor.AutowireMode;


@TestConstructor(autowireMode = AutowireMode.ALL)
@RequiredArgsConstructor
class WaitingLogServiceTest extends TestConfig {

    private final WaitingLogService waitingLogService;
    private final WaitingLogRepository waitingLogRepository;
    private final WaitingUserRepository waitingUserRepository;
    private final StoreWaitingRepository storeWaitingRepository;
    private final StoreRepository storeRepository;

    @AfterEach
    void tearDown() {
        waitingLogRepository.deleteAll();
        waitingUserRepository.deleteAll();
        storeWaitingRepository.deleteAll();
        storeRepository.deleteAll();
    }

    @Test
    @DisplayName("특정 가게 웨이팅 관련 로그를 모두 조회할 수 있다.")
    public void get_all_waiting_logs_for_specific_store() {
        // given
        Store store = storeRepository.save(StoreDummy.create());
        WaitingUser user = waitingUserRepository.save(WaitingUserDummy.create(store));
        StoreWaiting storeWaiting = storeWaitingRepository.save(StoreWaitingDummy.create(user, store));

        waitingLogRepository.save(new WaitingLog(storeWaiting, WaitingLogType.WAITING_REGISTRATION));
        waitingLogRepository.save(new WaitingLog(storeWaiting, WaitingLogType.ENTRY));

        // when
        WaitingLogFilter filter = new WaitingLogFilter(storeWaiting.getId(), null);
        List<WaitingLog> allByStoreWaitingId = waitingLogService.list(filter);

        // then
        Assertions.assertThat(allByStoreWaitingId)
                .hasSize(2);
    }
}
