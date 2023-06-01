# Watu Java Task

## Intro

The app runs a scheduler (by default each minute) which prints an info log about current time in specified time zone (
configurable), parses a csv file of time information (by default - file in classpath) and prints a warn message if
current time matches with
one found in the file.

## Commands

```shell
# to run
./gradlew bootRun

# to build
./gradlew build

# to test
./gradlew check

# run with custom csv file
./gradlew bootRun --args='--watu.scheduler.data-file-path="/some/custom/path"'
```

`! Other properties can also be overriden`

## Side note

I would rather use Quartz scheduler for this kind of task :)