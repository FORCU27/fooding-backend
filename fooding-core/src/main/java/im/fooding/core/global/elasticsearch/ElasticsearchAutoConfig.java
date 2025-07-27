package im.fooding.core.global.elasticsearch;

import im.fooding.core.global.elasticsearch.setup.StoreIndexSetup;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ElasticsearchAutoConfig {
    private final StoreIndexSetup storeIndexSetup;

    public ElasticsearchAutoConfig(StoreIndexSetup storeIndexSetup) {
        this.storeIndexSetup = storeIndexSetup;
    }

    @Bean
    public ApplicationRunner initializeIndices() {
        return args -> {
            storeIndexSetup.createStoreIndex();
            // 다른 인덱스도 여기서 생성 가능
        };
    }
}
