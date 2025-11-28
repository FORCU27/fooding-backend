package im.fooding.realtime;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

@EnableR2dbcRepositories(basePackages = "im.fooding.realtime.app.repository")
@SpringBootApplication(exclude = {
        WebMvcAutoConfiguration.class,
        ErrorMvcAutoConfiguration.class
})
@ComponentScan(
        basePackages = {
                "im.fooding.realtime",
                "im.fooding.core.global.exception",
                "im.fooding.core.global.infra",
                "im.fooding.core.common"
        }
)
public class FoodingRealtimeApplication {
    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(FoodingRealtimeApplication.class);
        application.setWebApplicationType(WebApplicationType.REACTIVE);
        application.run(args);
    }
}
