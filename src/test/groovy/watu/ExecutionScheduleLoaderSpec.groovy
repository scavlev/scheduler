package watu

import spock.lang.Specification

import static java.time.DayOfWeek.*

class ExecutionScheduleLoaderSpec extends Specification {

  void 'should load and parse a csv'() {
    when:
    def schedules = new ExecutionScheduleLoader()
        .loadExecutionSchedules('classpath:test-data.csv')

    then:
    schedules != null
    schedules.size() == 2
    schedules[0].with {
      assert it.hours() == 11 as byte
      assert it.minutes() == 22 as byte
      assert it.daysOfWeek() == EnumSet.of(MONDAY, WEDNESDAY)
    }
    schedules[1].with {
      assert it.hours() == 10 as byte
      assert it.minutes() == 44 as byte
      assert it.daysOfWeek() == EnumSet.of(MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY)
    }
  }

}
