package com.wilb0t.aoc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Day9 {

  public record Report(List<List<Integer>> history) {
    public static Report from(List<String> input) {
      var hist = input.stream().map(line -> Arrays.stream(line.split("\\s+")).map(Integer::parseInt).toList()).toList();
      return new Report(hist);
    }

    List<List<Integer>> getDiffs(int idx) {
      var diffs = new ArrayList<List<Integer>>();
      diffs.add(history.get(idx));
      while (diffs.getLast().stream().anyMatch(i -> i != 0)) {
        var last = diffs.getLast();
        var work = new ArrayList<Integer>(last.size() - 1);
        for (var i = 1; i < last.size(); i++) {
          work.add(last.get(i) - last.get(i - 1));
        }
        diffs.add(work);
      }

      return diffs;
    }

    public int extrapolateRight(int idx) {
      return getDiffs(idx).stream()
          .map(List::getLast)
          .reduce(Integer::sum)
          .orElseThrow();
    }

    public int extrapolateLeft(int idx) {
      return getDiffs(idx).stream()
          .map(List::getFirst)
          .toList().reversed().stream()
          .reduce((l, r) -> r - l)
          .orElseThrow();
    }
  }

  public static int extrapolateRight(Report report) {
    var sum = 0;
    for (var idx = 0; idx < report.history.size(); idx++) {
      sum += report.extrapolateRight(idx);
    }
    return sum;
  }

  public static int extrapolateLeft(Report report) {
    var sum = 0;
    for (var idx = 0; idx < report.history.size(); idx++) {
      sum += report.extrapolateLeft(idx);
    }
    return sum;
  }
}
