package com.wilb0t.aoc;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

class Day1 {
  private static final Map<String, Integer> WORD_TO_NUM =
      Map.of(
          "one", 1,
          "two", 2,
          "three", 3,
          "four", 4,
          "five", 5,
          "six", 6,
          "seven", 7,
          "eight", 8,
          "nine", 9);

  public static int decodeCalibration(String[] lines) {
    var sum = 0;

    for (var line : lines) {
      var decoded = 0;
      for (var idx = 0; idx < line.length(); idx++) {
        var tens = line.charAt(idx) - '0';
        if (tens >= 0 && tens <= 9) {
          decoded += 10 * tens;
          break;
        }
      }
      for (var idx = line.length() - 1; idx >= 0; idx--) {
        var ones = line.charAt(idx) - '0';
        if (ones >= 0 && ones <= 9) {
          decoded += ones;
          break;
        }
      }
      sum += decoded;
    }

    return sum;
  }

  public static int decodeCalibrationAlpha(String[] lines) {
    var sum = 0;
    for (var line : lines) {
      var tensDigit = 10 * getFirstNum(line);
      var onesDigit = getLastNum(line);
      sum += tensDigit + onesDigit;
    }
    return sum;
  }

  static int getFirstNum(String line) {
    for (var idx = 0; idx < line.length(); idx++) {
      var numChar = line.charAt(idx) - '0';
      if (numChar >= 0 && numChar <= 9) {
        return numChar;
      } else {
        var optNumStr = startsWith(line.substring(idx), WORD_TO_NUM.keySet());
        if (optNumStr.isPresent()) {
          return WORD_TO_NUM.get(optNumStr.get());
        }
      }
    }
    throw new RuntimeException("No number found for " + line);
  }

  static int getLastNum(String line) {
    for (var idx = line.length() - 1; idx >= 0; idx--) {
      var numChar = line.charAt(idx) - '0';
      if (numChar >= 0 && numChar <= 9) {
        return numChar;
      } else {
        var optNumStr = endsWith(line.substring(0, idx + 1), WORD_TO_NUM.keySet());
        if (optNumStr.isPresent()) {
          return WORD_TO_NUM.get(optNumStr.get());
        }
      }
    }
    throw new RuntimeException("No number found for " + line);
  }

  static Optional<String> startsWith(String str, Set<String> pats) {
    for (var pat : pats) {
      if (str.startsWith(pat)) {
        return Optional.of(pat);
      }
    }
    return Optional.empty();
  }

  static Optional<String> endsWith(String str, Set<String> pats) {
    for (var pat : pats) {
      if (str.endsWith(pat)) {
        return Optional.of(pat);
      }
    }
    return Optional.empty();
  }
}
