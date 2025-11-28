package im.fooding.realtime;

import org.springframework.boot.SpringApplication;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

@EnableR2dbcRepositories(basePackages = "im.fooding.realtime.app.repository")
public class FoodingRealtimeApplication {
    public static void main(String[] args) {
        SpringApplication.run(FoodingRealtimeApplication.class, args);
    }
}
