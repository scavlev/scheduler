package watu

import spock.lang.Specification

import java.time.Clock
import java.time.Instant
import java.time.ZoneId
import java.time.temporal.ChronoUnit

class SchedulerSpec extends Specification {

  Clock fixedClock = Clock.fixed(Instant.now().truncatedTo(ChronoUnit.MINUTES), ZoneId.of('Europe/Riga'))
  ExecutionScheduleLoader executionScheduleLoader = Mock()
  SchedulerConfig schedulerConfig = Mock()
  SchedulerAction schedulerAction = Mock()

  void 'should match current time with the one found in csv and fire action'() {
    given:
    def mockTime = Instant.now(fixedClock).atZone(fixedClock.zone)
    def mockExecutionSchedule = new ExecutionSchedule(
        mockTime.hour as byte, mockTime.minute as byte, EnumSet.of(mockTime.dayOfWeek)
    )

    schedulerConfig.dataFilePath >> 'irrelevant'
    schedulerConfig.targetTimezone >> (fixedClock.zone as String)
    executionScheduleLoader.loadExecutionSchedules('irrelevant') >> [mockExecutionSchedule]

    def scheduler = new Scheduler(fixedClock, executionScheduleLoader, schedulerConfig, schedulerAction)

    when:
    scheduler.run()

    then:
    noExceptionThrown()
    1 * schedulerAction.fire(mockExecutionSchedule)
  }

}
