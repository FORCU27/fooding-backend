package im.fooding.core.service.waiting;

import static org.junit.jupiter.api.Assertions.*;

import im.fooding.core.TestConfig;
import im.fooding.core.model.store.Store;
import im.fooding.core.model.waiting.StoreWaiting;
import im.fooding.core.model.waiting.WaitingLog;
import im.fooding.core.model.waiting.WaitingLogType;
import im.fooding.core.model.waiting.WaitingUser;
import im.fooding.core.repository.store.StoreRepository;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.TestConstructor;

@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@RequiredArgsConstructor
class WaitingLogServiceTest extends TestConfig {

    private final WaitingLogService waitingLogService;
    private final WaitingLogRepository waitingLogRepository;
    private final WaitingUserRepository waitingUserRepository;
    private final StoreRepository storeRepository;
    private final StoreWaitingRepository storeWaitingRepository;

    @AfterEach
    void tearDown() {
        waitingLogRepository.deleteAll();
        waitingUserRepository.deleteAll();
        storeRepository.deleteAll();
        storeWaitingRepository.deleteAll();
    }

    @Test
    @DisplayName("register 로그를 남기면 WAITING_REGISTRATION 으로 로그가 남아야 한다.")
    public void save_WAITING_REGISTRATION_when_log_register() {
        // given
        Store store = storeRepository.save(StoreDummy.create());
        WaitingUser user = waitingUserRepository.save(WaitingUserDummy.createWithPhoneNumber(store, "01012345678"));
        StoreWaiting storeWaiting = storeWaitingRepository.save(StoreWaitingDummy.create(user, store));

        // when
        WaitingLog waitingLog = waitingLogService.logRegister(storeWaiting);

        // then
        List<WaitingLog> waitingLogs = waitingLogRepository.findAll();
        Assertions.assertThat(waitingLogs)
                .hasSize(1);

        Assertions.assertThat(waitingLog.getType())
                .isEqualTo(WaitingLogType.WAITING_REGISTRATION);
    }

    @Test
    @DisplayName("특정 가게 웨이팅과 관련된 로그를 모두 조회할 수 있다.")
    public void testList() {
        // given
        Store store = storeRepository.save(StoreDummy.create());
        WaitingUser user = waitingUserRepository.save(WaitingUserDummy.createWithPhoneNumber(store, "01012345678"));
        StoreWaiting storeWaiting = storeWaitingRepository.save(StoreWaitingDummy.create(user, store));
        List<WaitingLog> waitingLogs = List.of(
                new WaitingLog(storeWaiting),
                new WaitingLog(storeWaiting)
        );
        waitingLogRepository.saveAll(waitingLogs);

        // when
        Page<WaitingLog> page = waitingLogService.list(storeWaiting.getStoreId(), Pageable.unpaged());

        // then
        Assertions.assertThat(page.getContent().size())
                .isEqualTo(2);
    }
}
