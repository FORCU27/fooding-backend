package im.fooding;

import im.fooding.core.FoodingCoreConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@Import(FoodingCoreConfiguration.class)
@SpringBootApplication
public class FoodingApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(FoodingApiApplication.class, args);
    }
}
