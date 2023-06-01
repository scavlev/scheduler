package watu;

import java.time.Clock;
import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@ConfigurationPropertiesScan
@SpringBootApplication
class Main {

  public static void main(String... args) {
    new SpringApplicationBuilder(Main.class)
        .bannerMode(Banner.Mode.OFF)
        .run(args);
  }

  @Bean
  Clock clock() {
    return Clock.systemDefaultZone();
  }

}