package im.fooding.core.service.waiting;

import im.fooding.core.TestConfig;
import im.fooding.core.dto.request.waiting.WaitingUserRegisterRequest;
import im.fooding.core.model.store.Store;
import im.fooding.core.model.waiting.WaitingUser;
import im.fooding.core.repository.store.StoreRepository;
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
class WaitingUserServiceTest extends TestConfig {

    private final WaitingUserService waitingUserService;
    private final WaitingUserRepository waitingUserRepository;
    private final StoreRepository storeRepository;

    @AfterEach
    void tearDown() {
        waitingUserRepository.deleteAll();
        storeRepository.deleteAll();
    }

    @Test
    @DisplayName("웨이팅 유저가 없는 경우 새로 등록한다.")
    void register_when_not_exist_waiting_user() {
        // given
        Store store = storeRepository.save(StoreDummy.create());

        WaitingUserRegisterRequest request = WaitingUserRegisterRequest.builder()
                .store(store)
                .name("name")
                .phoneNumber("01012345678")
                .termsAgreed(true)
                .privacyPolicyAgreed(true)
                .thirdPartyAgreed(true)
                .marketingConsent(true)
                .build();

        // when & then
        Assertions.assertThat(waitingUserService.getOrElseRegister(request))
                .isNotNull();
        Assertions.assertThat(waitingUserRepository.findAll())
                .hasSize(1);
    }

    @Test
    @DisplayName("웨이팅 유저가 있다면 존재하는 유저를 가져온다.")
    void get_when_exist_waiting_user() {
        // given
        Store store = storeRepository.save(StoreDummy.create());
        WaitingUser waitingUser = waitingUserRepository.save(WaitingUserDummy.createWithPhoneNumber(store, "01012345678"));

        WaitingUserRegisterRequest request = WaitingUserRegisterRequest.builder()
                .store(store)
                .name("name")
                .phoneNumber(waitingUser.getPhoneNumber())
                .termsAgreed(true)
                .privacyPolicyAgreed(true)
                .thirdPartyAgreed(true)
                .marketingConsent(true)
                .build();

        // when & then
        Assertions.assertThat(waitingUserService.getOrElseRegister(request))
                .isNotNull();
        Assertions.assertThat(waitingUserRepository.findAll())
                .hasSize(1);
    }
}
