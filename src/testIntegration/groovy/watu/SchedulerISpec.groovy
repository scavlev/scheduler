package watu

import org.spockframework.spring.SpringBean
import org.spockframework.spring.SpringSpy
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Primary
import org.springframework.test.context.ActiveProfiles
import spock.lang.Specification
import spock.mock.DetachedMockFactory

import java.time.Clock
import java.time.Instant
import java.time.ZoneId
import java.time.temporal.ChronoUnit
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

@SpringBootTest
@ActiveProfiles('test')
class SchedulerISpec extends Specification {

  @TestConfiguration
  static class Config {
    @Bean
    @Primary
    ExecutionScheduleLoader mockExecutionScheduleLoader() {
      new DetachedMockFactory().Mock(ExecutionScheduleLoader)
    }
  }

  @Autowired
  ExecutionScheduleLoader executionScheduleLoader

  @SpringSpy
  SchedulerAction schedulerAction

  @SpringBean
  Clock fixedClock = Clock.fixed(Instant.now().truncatedTo(ChronoUnit.MINUTES), ZoneId.of('Europe/Riga'))

  void 'should fire an action during schedule invocation'() {
    given:
    def mockTime = Instant.now(fixedClock).atZone(fixedClock.zone)
    def mockExecutionSchedule = new ExecutionSchedule(
        mockTime.hour as byte, mockTime.minute as byte, EnumSet.of(mockTime.dayOfWeek)
    )
    executionScheduleLoader.loadExecutionSchedules(_) >> [mockExecutionSchedule]

    def latch = new CountDownLatch(1)
    schedulerAction.fire(mockExecutionSchedule) >> {
      latch.countDown()
      callRealMethod()
    }

    when:
    latch.await(10, TimeUnit.SECONDS)

    then:
    latch.count == 0
  }

}
