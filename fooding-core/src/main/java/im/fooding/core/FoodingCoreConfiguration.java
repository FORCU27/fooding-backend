package im.fooding.core;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableJpaAuditing
@ComponentScan
@EnableAutoConfiguration
@EnableAsync
@EnableFeignClients
@EnableElasticsearchRepositories(basePackages = "im.fooding.core.repository.elasticsearch")
public class FoodingCoreConfiguration {
}
