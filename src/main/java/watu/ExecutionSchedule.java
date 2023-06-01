package watu;

import java.time.DayOfWeek;
import java.util.EnumSet;

record ExecutionSchedule(byte hours, byte minutes, EnumSet<DayOfWeek> daysOfWeek) {
}
