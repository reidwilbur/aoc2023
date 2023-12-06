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

  public record RangeResult(long value, long len) {}

  public record Range(long dstStart, long srcStart, long len) {
    public static Range from(String line) {
      var parts = Arrays.stream(line.split("\\s+")).map(Long::parseLong).toList();
      if (parts.size() != 3) {
        throw new RuntimeException("invalid string for range " + line);
      }
      return new Range(parts.get(0), parts.get(1), parts.get(2));
    }

    public RangeResult apply(long src) {
      if (src < srcStart) {
        return new RangeResult(-1, srcStart - src);
      }
      if (src < srcStart + len) {
        long value = (src - srcStart) + dstStart;
        long rlen = (srcStart + len) - src;
        return new RangeResult(value, rlen);
      }
      return new RangeResult(-1, Long.MAX_VALUE);
    }
  }

  public record RangeMap(String input, String output, List<Range> ranges) {
    public static RangeMap from(List<String> lines) {
      var parts = lines.get(0).substring(0, lines.get(0).length() - 5).split("-to-");
      var input = parts[0];
      var output = parts[1];
      var ranges = lines.subList(1, lines.size()).stream().map(Range::from).toList();
      return new RangeMap(input, output, ranges);
    }

    public RangeResult apply(long src) {
      return ranges.stream()
          .map(range -> range.apply(src))
          // all vals will be -1 or a single positive val, so take largest val
          // if all vals are -1, then want the one with the lowest len since will be using
          // this to determine how many values to skip in the range
          .min(Comparator.comparing(RangeResult::value).reversed().thenComparing(RangeResult::len))
          // dont actually return -1, replace with the src value here since -1 was a placeholder
          // for src val not in range
          .map(res -> (res.value == -1) ? new RangeResult(src, res.len) : res)
          .orElseThrow();
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

    public RangeResult getLocation(long seed) {
      var input = "seed";
      RangeResult result = new RangeResult(seed, Long.MAX_VALUE);
      while (maps.containsKey(input)) {
        var map = maps.get(input);
        var nextRes = map.apply(result.value);
        // need to keep the min len value seen so far since that's where we will need
        // to search next for the seed range case
        result = new RangeResult(nextRes.value, Math.min(result.len, nextRes.len));
        input = map.output;
      }
      return result;
    }

    public long getLowestLocation() {
      return seeds.stream().mapToLong(seed -> getLocation(seed).value).min().orElseThrow();
    }

    public long getLowest(SeedRange seedRange) {
      var lowest = new RangeResult(Long.MAX_VALUE, Long.MAX_VALUE);
      for (long seed = seedRange.first; seed <= seedRange.last; ) {
        var result = getLocation(seed);
        lowest = (result.value < lowest.value) ? result : lowest;
        if (result.len == Long.MAX_VALUE) {
          return lowest.value;
        }
        seed += result.len;
      }
      return lowest.value;
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
