package im.fooding.core.global.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .components(new Components())
                .info(apiInfo());
    }

    private Info apiInfo() {
        return new Info()
                .title("Fooding API") // API의 제목
                .description("Fooding API") // API에 대한 설명
                .version("1.0.0"); // API의 버전
    }

    @Bean
    public GroupedOpenApi adminApi() {
        return GroupedOpenApi.builder()
                .group("Admin API")
                .packagesToScan("im.fooding.app.controller.admin")
                .build();
    }

    @Bean
    public GroupedOpenApi userApi() {
        return GroupedOpenApi.builder()
                .group("User API")
                .packagesToScan("im.fooding.app.controller.user")
                .build();
    }

    @Bean
    public GroupedOpenApi posApi() {
        return GroupedOpenApi.builder()
                .group("Pos API")
                .packagesToScan("im.fooding.app.controller.pos")
                .build();
    }

    @Bean
    public GroupedOpenApi appApi() {
        return GroupedOpenApi.builder()
                .group("App API")
                .packagesToScan("im.fooding.app.controller.app")
                .build();
    }

    @Bean
    public GroupedOpenApi ceoApi() {
        return GroupedOpenApi.builder()
                .group("Ceo API")
                .packagesToScan("im.fooding.app.controller.ceo")
                .build();
    }

    @Bean
    public GroupedOpenApi api() {
        return GroupedOpenApi.builder()
                .group("API")
                .packagesToScan("im.fooding.app.controller")
                .build();
    }
}
