package im.fooding.core.service.waiting;

import static org.junit.jupiter.api.Assertions.assertThrows;

import im.fooding.core.TestConfig;
import im.fooding.core.global.exception.ApiException;
import im.fooding.core.global.exception.ErrorCode;
import im.fooding.core.model.store.Store;
import im.fooding.core.model.waiting.Waiting;
import im.fooding.core.model.waiting.WaitingSetting;
import im.fooding.core.repository.store.StoreRepository;
import im.fooding.core.repository.waiting.WaitingRepository;
import im.fooding.core.repository.waiting.WaitingSettingRepository;
import im.fooding.core.support.dummy.StoreDummy;
import im.fooding.core.support.dummy.WaitingDummy;
import im.fooding.core.support.dummy.WaitingSettingDummy;
import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.TestConstructor;

@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@RequiredArgsConstructor
class WaitingSettingServiceTest extends TestConfig {

    private final WaitingSettingService waitingSettingService;
    private final WaitingSettingRepository waitingSettingRepository;
    private final WaitingRepository waitingRepository;
    private final StoreRepository storeRepository;

    @AfterEach
    void tearDown() {
        waitingSettingRepository.deleteAll();
        waitingRepository.deleteAll();
        storeRepository.deleteAll();
    }

    @Test
    @DisplayName("활성화된 웨이팅 세팅을 조회할 수 있다.")
    public void testGetActive() {
        // given
        Store store = storeRepository.save(StoreDummy.create());
        Waiting waiting = waitingRepository.save(WaitingDummy.create(store));
        waitingSettingRepository.save(WaitingSettingDummy.create(waiting, true));

        // when
        WaitingSetting waitingSetting = waitingSettingService.getActiveSetting();

        // then
        Assertions.assertThat(waitingSetting.isActive())
                .isTrue();
    }

    @Test
    @DisplayName("활성화된 웨이팅 세팅이 존재하지 않는 경우 활성 웨이팅 세팅을 조회할 수 없다.")
    public void testGetActive_fail_whenNotFoundActiveSetting() {
        // given
        Store store = storeRepository.save(StoreDummy.create());
        Waiting waiting = waitingRepository.save(WaitingDummy.create(store));
        waitingSettingRepository.save(WaitingSettingDummy.create(waiting, false));

        // when & then
        ApiException e = assertThrows(ApiException.class, waitingSettingService::getActiveSetting);
        Assertions.assertThat(e.getErrorCode())
                .isEqualTo(ErrorCode.ACTIVE_WAITING_SETTING_NOT_FOUND);
    }
}
