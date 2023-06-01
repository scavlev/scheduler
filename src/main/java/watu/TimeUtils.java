package watu;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

class TimeUtils {

  /**
   * scheduler executions are imprecise and can execute twice in a minute like 11:30:00 and 11:30:59 instead of 11:31:00
   * to avoid skipping execution this method is used to aggressively round the minutes
   */
  public static ZonedDateTime roundTimeToNearestMinute(ZonedDateTime original) {
    if (original.getSecond() >= 30) {
      return original.truncatedTo(ChronoUnit.MINUTES).plus(1, ChronoUnit.MINUTES);
    }
    return original.truncatedTo(ChronoUnit.MINUTES);
  }

}
