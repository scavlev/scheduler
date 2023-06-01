package watu;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
class SchedulerAction {

  void fire(ExecutionSchedule executionSchedule) {
    log.warn(
        "Scheduler action fired for {}:{} (hh:mm) on days {}",
        executionSchedule.hours(),
        executionSchedule.minutes(),
        executionSchedule.daysOfWeek()
    );
  }

}
