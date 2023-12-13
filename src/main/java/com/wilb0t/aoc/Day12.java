package com.wilb0t.aoc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class Day12 {

  public record SpringRecord(String status, List<Integer> grpSizes) {
    public static SpringRecord from(String line) {
      var parts = line.split("\\s+");
      var status = parts[0];
      var grpSizes = Arrays.stream(parts[1].split(",")).map(Integer::parseInt).toList();
      return new SpringRecord(status, grpSizes);
    }

    public SpringRecord expand() {
      var expstat = IntStream.range(0, 5).mapToObj(i -> status).collect(Collectors.joining("?"));
      var grpSizes = IntStream.range(0, 5).boxed().flatMap(i -> grpSizes().stream()).toList();
      return new SpringRecord(expstat, grpSizes);
    }

//    static boolean isValid(String pattern, List<Integer> grpSizes) {
//      var brokenGrps = new ArrayList<Integer>();
//      char last = '.';
//      for (var idx = 0; idx < pattern.length(); idx++) {
//        char cur = pattern.charAt(idx);
//        if (cur  == '#') {
//          if (last == '#' && !brokenGrps.isEmpty()) {
//            brokenGrps.set(brokenGrps.size() - 1, brokenGrps.getLast() + 1);
//          } else {
//            brokenGrps.add(1);
//          }
//        }
//        last = cur;
//      }
//      return brokenGrps.equals(grpSizes);
//    }

//    public int countValidPatterns() {
//      var qcount = status.chars().filter(c -> c == '?').count();
//
//      if (qcount == 0) {
//        return 1;
//      }
//
//      var validcount = 0;
//      var combs = (1 << qcount);
//      for (var itr = 0; itr < combs; itr++) {
//        var sb = new StringBuilder(status);
//        var qidx = 0;
//        for (var idx = 0; idx < sb.length(); idx++) {
//          if (sb.charAt(idx) == '?') {
//            char val = ((itr & (1L << qidx)) == 0) ? '.' : '#';
//            sb.setCharAt(idx, val);
//            qidx += 1;
//          }
//        }
//        validcount += isValid(sb.toString(), grpSizes) ? 1 : 0;
//      }
//
//      return validcount;
//    }
  }

  static boolean reject(String candidate, List<Integer> grpSizes) {
    var lastq = candidate.indexOf('?');
    if (lastq == -1) {
      return false;
    }
    var end = candidate.substring(0, lastq).lastIndexOf('.');
    if (end == -1) {
      return false;
    }
    var csub = candidate.substring(0, end);

    var brokenGrps = new ArrayList<Integer>();
    char last = '.';
    for (var idx = 0; idx < csub.length(); idx++) {
      char cur = csub.charAt(idx);
      if (cur  == '#') {
        if (last == '#' && !brokenGrps.isEmpty()) {
          brokenGrps.set(brokenGrps.size() - 1, brokenGrps.getLast() + 1);
        } else {
          brokenGrps.add(1);
        }
      }
      last = cur;
    }

    var reject = false;
    for (var idx = 0; idx < Math.min(brokenGrps.size(), grpSizes.size()); idx++) {
      if (!brokenGrps.get(idx).equals(grpSizes.get(idx))) {
        reject = true;
      }
    }
    return reject;
  }

  static boolean accept(String candidate, List<Integer> grpSizes) {
    var brokenGrps = new ArrayList<Integer>();
    char last = '.';
    for (var idx = 0; idx < candidate.length(); idx++) {
      char cur = candidate.charAt(idx);
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

  static String replaceFirst(char c, char r, String s) {
    var idx = s.indexOf(c);
    if (idx == -1) {
      return s;
    }
    var sb = new StringBuilder(s);
    sb.setCharAt(idx, r);
    return sb.toString();
  }

  public static int getValidPatterns(String candidate, List<Integer> grpSizes) {
    if (!candidate.contains("?")) {
      return accept(candidate, grpSizes) ? 1 : 0;
    }

    if (reject(candidate, grpSizes)) {
      return 0;
    }

    var left = replaceFirst('?', '.', candidate);
    var right = replaceFirst('?', '#', candidate);

    var validcount = 0;
    validcount += getValidPatterns(left, grpSizes);
    validcount += getValidPatterns(right, grpSizes);

    return validcount;
  }

  public static int getValidPatterns(List<SpringRecord> records) {
    //return records.stream().mapToInt(SpringRecord::countValidPatterns).sum();
    return records.stream().mapToInt(sr -> getValidPatterns(sr.status, sr.grpSizes)).sum();
  }

  public static int getValidPatternsExpanded(List<SpringRecord> records) {
    return records.stream().map(SpringRecord::expand).mapToInt(sr -> getValidPatterns(sr.status, sr.grpSizes)).sum();
  }
}
