package watu

import spock.lang.Specification
import spock.lang.Unroll

import static java.time.DayOfWeek.*

class DayOfWeekBitConverterSpec extends Specification {

  @Unroll
  void 'should convert from bitmask [#bitmask] into day of week enum set of #expected'() {
    when:
    def daySet = DayOfWeekBitConverter.fromBitValues(bitmask)

    then:
    daySet == expected

    where:
    bitmask | expected
    1       | EnumSet.of(MONDAY)
    2       | EnumSet.of(TUESDAY)
    4       | EnumSet.of(WEDNESDAY)
    8       | EnumSet.of(THURSDAY)
    16      | EnumSet.of(FRIDAY)
    32      | EnumSet.of(SATURDAY)
    64      | EnumSet.of(SUNDAY)
    5       | EnumSet.of(MONDAY, WEDNESDAY)
    7       | EnumSet.of(MONDAY, TUESDAY, WEDNESDAY)
    65      | EnumSet.of(MONDAY, SUNDAY)
    96      | EnumSet.of(SATURDAY, SUNDAY)
    127     | EnumSet.of(MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY)
  }

  void 'should throw exception on invalid bitmask'() {
    when:
    DayOfWeekBitConverter.fromBitValues(777)

    then:
    thrown(IllegalArgumentException)
  }

}
