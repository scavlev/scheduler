package watu

import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

import java.time.ZonedDateTime

class TimeUtilsSpec extends Specification {

  @Shared
  static def BASE_TIME = ZonedDateTime.parse('2007-12-03T10:15:00+01:00[Europe/Paris]')

  @Unroll
  void 'should round time #time to nearest minute #expectedRounded'() {
    when:
    def rounded = TimeUtils.roundTimeToNearestMinute(time)

    then:
    rounded == expectedRounded

    where:
    time                      | expectedRounded
    BASE_TIME                 | BASE_TIME
    BASE_TIME.plusSeconds(10) | BASE_TIME
    BASE_TIME.plusSeconds(29) | BASE_TIME
    BASE_TIME.plusSeconds(30) | BASE_TIME.plusMinutes(1)
    BASE_TIME.plusSeconds(59) | BASE_TIME.plusMinutes(1)
    BASE_TIME.plusSeconds(59) | BASE_TIME.plusMinutes(1)
  }

}
