package com.wilb0t.aoc;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

class Day4 {

  public record Card(int id, List<Integer> winningNums, Set<Integer> playNums) {
    public static Card from(String line) {
      var parts = line.split(":\\s+");
      var id = Integer.parseInt(parts[0].split("\\s+")[1]);
      var numParts = parts[1].split("\\s+\\|\\s+");
      var winningNums = Arrays.stream(numParts[0].split("\\s+")).map(Integer::parseInt).toList();
      var playNums = Arrays.stream(numParts[1].split("\\s+")).map(Integer::parseInt).collect(Collectors.toSet());
      return new Card(id, winningNums, playNums);
    }

    public int points() {
      var matched = winningNums.stream().filter(playNums::contains).count();
      if (matched == 0) {
        return 0;
      }
      var points = 1;
      for (var idx = 0; idx < matched - 1; idx++) {
        points <<= 1;
      }
      return points;
    }

    public long matches() {
      return winningNums.stream().filter(playNums::contains).count();
    }

    public int idx() {
      return id - 1;
    }
  }

  public static int getTotalPoints(List<Card> cards) {
    return cards.stream().mapToInt(Card::points).sum();
  }

  public static int getCardCount(List<Card> cards) {
    var idCounts = new int[cards.size()];
    Arrays.fill(idCounts, -1);
    for (var idx = 0; idx < cards.size(); idx++) {
      if (idCounts[idx] == -1) {
        getCount(cards.get(idx), idCounts, cards);
      }
    }
    return Arrays.stream(idCounts).sum();
  }

  static int getCount(Card card, int[] counts, List<Card> cards) {
    if (counts[card.idx()] != -1) {
      return counts[card.idx()];
    }
    var matches = card.matches();
    if (matches == 0) {
      counts[card.idx()] = 1;
      return 1;
    }
    var count = 1;
    for (var midx = 0; midx < matches; midx++) {
      count += getCount(cards.get(card.idx() + 1 + midx), counts, cards);
    }
    counts[card.idx()] = count;
    return count;
  }
}
