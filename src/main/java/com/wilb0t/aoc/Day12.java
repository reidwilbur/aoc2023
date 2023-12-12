package com.wilb0t.aoc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

class Day12 {

  public record SpringRecord(String status, List<Integer> grpSizes) {
    public static SpringRecord from(String line) {
      var parts = line.split("\\s+");
      var status = parts[0];
      var grpSizes = Arrays.stream(parts[1].split(",")).map(Integer::parseInt).toList();
      return new SpringRecord(status, grpSizes);
    }

    static boolean isValid(String pattern, List<Integer> grpSizes) {
      var brokenGrps = new ArrayList<Integer>();
      char last = '.';
      for (var idx = 0; idx < pattern.length(); idx++) {
        char cur = pattern.charAt(idx);
        if (cur  == '#') {
          if (last == '#' && !brokenGrps.isEmpty()) {
            brokenGrps.set(brokenGrps.size() - 1, brokenGrps.getLast() + 1);
          } else {
            brokenGrps.add(1);
          }
        }
        last = cur;
      }
      return brokenGrps.equals(grpSizes);
    }

    public int countValidPatterns() {
      var qcount = status.chars().filter(c -> c == '?').count();

      if (qcount == 0) {
        return 1;
      }

      var validcount = 0;
      var combs = (1 << qcount);
      for (var itr = 0; itr < combs; itr++) {
        var sb = new StringBuilder(status);
        var qidx = 0;
        for (var idx = 0; idx < sb.length(); idx++) {
          if (sb.charAt(idx) == '?') {
            char val = ((itr & (1L << qidx)) == 0) ? '.' : '#';
            sb.setCharAt(idx, val);
            qidx += 1;
          }
        }
        validcount += isValid(sb.toString(), grpSizes) ? 1 : 0;
      }

      return validcount;
    }
  }

  public static int getValidPatterns(List<SpringRecord> records) {
    return records.stream().mapToInt(SpringRecord::countValidPatterns).sum();
  }
}
