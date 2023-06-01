package watu;

import java.time.DayOfWeek;
import java.util.EnumSet;
import java.util.Map;

class DayOfWeekBitConverter {

  private static final Map<DayOfWeek, Integer> dayBits = Map.of(
      DayOfWeek.MONDAY, 1,
      DayOfWeek.TUESDAY, 2,
      DayOfWeek.WEDNESDAY, 4,
      DayOfWeek.THURSDAY, 8,
      DayOfWeek.FRIDAY, 16,
      DayOfWeek.SATURDAY, 32,
      DayOfWeek.SUNDAY, 64
  );

  public static EnumSet<DayOfWeek> fromBitValues(final int originalBitMask) {
    final EnumSet<DayOfWeek> daySet = EnumSet.noneOf(DayOfWeek.class);

    int bitMask = originalBitMask;

    for (final DayOfWeek val : DayOfWeek.values()) {
      if ((dayBits.get(val) & bitMask) == dayBits.get(val)) {
        bitMask &= ~dayBits.get(val);
        daySet.add(val);
      }
    }

    if (bitMask != 0) {
      throw
          new IllegalArgumentException(
              String.format(
                  "Bit mask value 0x%X(%d) has unsupported bits " +
                      "0x%X.  Extracted values: %s",
                  originalBitMask,
                  originalBitMask,
                  bitMask,
                  daySet
              )
          );
    }

    return daySet;
  }

}
