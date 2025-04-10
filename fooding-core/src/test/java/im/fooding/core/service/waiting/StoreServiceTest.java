package im.fooding.core.service.waiting;

import im.fooding.core.TestConfig;
import im.fooding.core.model.store.Store;
import im.fooding.core.repository.store.StoreRepository;
import im.fooding.core.support.dummy.StoreDummy;
import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.TestConstructor;


@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@RequiredArgsConstructor
class StoreServiceTest extends TestConfig {

    private final StoreRepository storeRepository;
    private final StoreService storeService;

    @AfterEach
    void tearDown() {
        storeRepository.deleteAll();
    }

    @Test
    @DisplayName("id로 가게를 조회할 수 있다.")
    public void get_store_by_id() {
        // given
        Store store = storeRepository.save(StoreDummy.create());

        // when & then
        Assertions.assertThat(storeService.getById(store.getId()))
                .isNotNull();
    }
}
