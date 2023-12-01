package com.wilb0t.aoc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class Day3 {
  public static int getPrioSum(String[] rucksacks) {
    return Arrays.stream(rucksacks)
        .flatMap(contents -> getRepeat(contents).stream())
        .mapToInt(chr -> (chr < 'a') ? chr - 'A' + 27 : chr - 'a' + 1)
        .sum();
  }
  
  static Optional<Character> getRepeat(String contents) {
    var leftChars = new HashSet<Character>(contents.length() / 2);
    contents
        .substring(0, contents.length() / 2)
        .chars()
        .forEach(c -> leftChars.add((char) c));
    return contents
        .substring(contents.length() / 2)
        .chars()
        .mapToObj(c -> (char) c)
        .flatMap(c -> (leftChars.contains(c)) ? Stream.of(c) : Stream.empty())
        .findFirst();
  }
  
  public static int getBadgePrioSum(String[] rucksacks) {
    var grouped = new ArrayList<Set<Character>>(rucksacks.length / 3);
    for (var idx = 0; idx < rucksacks.length; idx++) {
      var chars = rucksacks[idx].chars().mapToObj(c -> (char) c).collect(Collectors.toSet());
      if (idx % 3 == 0) {
        grouped.add(chars);
      } else {
        grouped.get(grouped.size() - 1).retainAll(chars);
      }
    }
    
    return grouped.stream()
        .flatMap(chars -> chars.stream().findFirst().stream())
        .mapToInt(c -> (c < 'a') ? c - 'A' + 27 : c - 'a' + 1)
        .sum();
  }
}
