package com.wilb0t.aoc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

class Day5 {

  public record SeedRange(long first, long last) {}

  public record Range(long dstStart, long srcStart, long len) {
    public static Range from(String line) {
      var parts = Arrays.stream(line.split("\\s+")).map(Long::parseLong).toList();
      if (parts.size() != 3) {
        throw new RuntimeException("invalid string for range " + line);
      }
      return new Range(parts.get(0), parts.get(1), parts.get(2));
    }

    public long apply(long src) {
      if ((src < srcStart + len) && src >= srcStart) {
        return (src - srcStart) + dstStart;
      }
      return -1;
    }
  }

  public record RangeMap(String input, String output, List<Range> ranges) {
    public static RangeMap from(List<String> lines) {
      var parts = lines.get(0).substring(0, lines.get(0).length() - 5).split("-to-");
      var input = parts[0];
      var output = parts[1];
      var ranges =
          lines.subList(1, lines.size()).stream()
              .map(Range::from)
              .sorted(Comparator.comparing(Range::srcStart))
              .toList();
      return new RangeMap(input, output, ranges);
    }

    public Map.Entry<String, Long> apply(long src) {
      return Map.entry(
          output,
          ranges.stream()
              .map(range -> range.apply(src))
              .filter(val -> val != -1)
              .findFirst()
              .orElse(src));
    }
  }

  public record Almanac(List<Long> seeds, Map<String, RangeMap> maps) {
    public static Almanac from(List<String> lines) {
      List<Long> seeds =
          Arrays.stream(lines.get(0).split(":\\s+")[1].split("\\s+")).map(Long::parseLong).toList();
      var maps = new HashMap<String, RangeMap>();

      var mapLines = new ArrayList<String>();
      for (var idx = 2; idx < lines.size(); idx++) {
        var line = lines.get(idx);

        if (!line.isEmpty()) {
          mapLines.add(line);
        } else {
          var map = RangeMap.from(mapLines);
          maps.put(map.input, map);
          mapLines.clear();
        }
      }
      if (!mapLines.isEmpty()) {
        var map = RangeMap.from(mapLines);
        maps.put(map.input, map);
      }

      return new Almanac(seeds, maps);
    }

    public long getLocation(long seed) {
      var input = "seed";
      var mapped = seed;
      while (maps.containsKey(input)) {
        var map = maps.get(input);
        var result = map.apply(mapped);
        input = result.getKey();
        mapped = result.getValue();
      }
      return mapped;
    }

    public long getLowestLocation() {
      return seeds.stream().mapToLong(this::getLocation).min().orElseThrow();
    }

    public long getLowest(SeedRange seedRange) {
      long jmp = 1000;
      long lowest = Long.MAX_VALUE;
      for (long seed = seedRange.first; seed <= seedRange.last; ) {
        long location = getLocation(seed);
        lowest = Math.min(location, lowest);
        long jmpLocation = getLocation(seed + jmp);
        if (jmpLocation == location + jmp) {
          seed += jmp;
        } else {
          seed += 1;
        }
      }
      return lowest;
    }

    public long getLowestLocationSeedRanges() {
      var seedRanges =
          IntStream.range(0, seeds.size() / 2)
              .mapToObj(
                  idx -> {
                    var first = seeds.get(idx * 2);
                    var last = first + seeds.get(idx * 2 + 1) - 1;
                    return new SeedRange(first, last);
                  })
              .toList();

      long lowest = Long.MAX_VALUE;
      for (var range : seedRanges) {
        lowest = Math.min(getLowest(range), lowest);
      }
      return lowest;
    }
  }
}
