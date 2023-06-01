package watu;

import lombok.Value;
import lombok.experimental.NonFinal;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Value
@NonFinal
@ConfigurationProperties(prefix = "watu.scheduler")
class SchedulerConfig {

  String dataFilePath;

  String targetTimezone;

}
