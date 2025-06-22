package im.fooding.core.global.config;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@SecurityScheme(
        name = "Bearer Authentication",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement().addList("Bearer Authentication"))
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
