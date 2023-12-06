package com.wilb0t.aoc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Day6 {

  public record RaceRecord(long time, long dist) {
    public static List<RaceRecord> from(List<String> lines) {
      var timeStr = lines.get(0).split(":\\s+")[1];
      var times = Arrays.stream(timeStr.split("\\s+")).mapToInt(Integer::parseInt).toArray();

      var distStr = lines.get(1).split(":\\s+")[1];
      var dists = Arrays.stream(distStr.split("\\s+")).mapToInt(Integer::parseInt).toArray();

      var records = new ArrayList<RaceRecord>(times.length);
      for (var idx = 0; idx < times.length; idx++) {
        records.add(new RaceRecord(times[idx], dists[idx]));
      }
      return records;
    }

    public static RaceRecord flatFrom(List<String> lines) {
      var timeStr = lines.get(0).split(":\\s+")[1].replaceAll("\\s+", "");
      var distStr = lines.get(1).split(":\\s+")[1].replaceAll("\\s+", "");
      return new RaceRecord(Long.parseLong(timeStr), Long.parseLong(distStr));
    }

    public long getWinnerCount() {
      var winners = 0;
      for (var presstime = 0; presstime <= time; presstime++) {
        var pressdist = (time - presstime) * presstime;
        if (pressdist > dist) {
          winners += 1;
        }
      }
      return winners;
    }
  }

  public static long getWinners(List<RaceRecord> raceRecords) {
    return raceRecords.stream()
        .mapToLong(RaceRecord::getWinnerCount)
        .reduce((l, r) -> l * r)
        .orElseThrow();
  }
}
