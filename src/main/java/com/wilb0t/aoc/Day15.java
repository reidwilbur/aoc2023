package com.wilb0t.aoc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class Day15 {

  public static List<String> parse(List<String> input) {
    var line = String.join("", input);
    var parts = line.split(",");
    return Arrays.asList(parts);
  }

  public static int hash(String line) {
    return line.chars().reduce(0, (acc, c) -> ((acc + c) * 17) % 256);
  }

  public static int getHashSum(List<String> input) {
    return input.stream().mapToInt(Day15::hash).sum();
  }
}
