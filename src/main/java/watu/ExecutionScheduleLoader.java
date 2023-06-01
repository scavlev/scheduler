package watu;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

@Service
class ExecutionScheduleLoader {

  public List<ExecutionSchedule> loadExecutionSchedules(String dataFilePath) throws IOException {
    List<ExecutionSchedule> executionSchedules = new ArrayList<>();
    File file = ResourceUtils.getFile(dataFilePath);
    try (BufferedReader br = Files.newBufferedReader(file.toPath(), UTF_8)) {
      String line = br.readLine();
      while (line != null) {
        String[] columns = line.split(",");
        String[] time = columns[0].split(":");

        byte hours = Byte.parseByte(time[0]);
        byte minutes = Byte.parseByte(time[1]);
        EnumSet<DayOfWeek> daysOfWeek = DayOfWeekBitConverter.fromBitValues(Integer.parseInt(columns[1]));

        executionSchedules.add(new ExecutionSchedule(hours, minutes, daysOfWeek));
        line = br.readLine();
      }
    }
    return executionSchedules;
  }

}
