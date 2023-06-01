package watu;

import java.io.IOException;
import java.time.Clock;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
class Scheduler {

  Clock clock;
  ExecutionScheduleLoader executionScheduleLoader;
  SchedulerConfig schedulerConfig;
  SchedulerAction schedulerAction;

  @Scheduled(cron = "${watu.scheduler.cron}")
  public void run() throws IOException {
    ZonedDateTime targetTimeRounded = TimeUtils.roundTimeToNearestMinute(
        ZonedDateTime.now(
            clock.withZone(
                ZoneId.of(schedulerConfig.getTargetTimezone())
            )
        )
    );

    log.info(
        "Scheduler ran. Target time zone [{}] time: {}",
        schedulerConfig.getTargetTimezone(),
        targetTimeRounded
    );

    List<ExecutionSchedule> executionSchedules = executionScheduleLoader
        .loadExecutionSchedules(schedulerConfig.getDataFilePath());

    Optional<ExecutionSchedule> matchingSchedule = executionSchedules.stream()
        .filter(schedule ->
            schedule.hours() == targetTimeRounded.getHour()
                && schedule.minutes() == targetTimeRounded.getMinute()
                && schedule.daysOfWeek().contains(targetTimeRounded.getDayOfWeek())
        )
        .findFirst();

    matchingSchedule.ifPresent(schedulerAction::fire);
  }

}
