package com.wilb0t.aoc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

class Day7 {

  public enum Card {
    TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING, ACE;

    public static Card from(int chr) {
      return switch (chr) {
        case 'T' -> Card.TEN;
        case 'J' -> Card.JACK;
        case 'Q' -> Card.QUEEN;
        case 'K' -> Card.KING;
        case 'A' -> Card.ACE;
        default -> {
          var numCard = chr - '2';
          if (numCard >= 0 && numCard < 8) {
            yield Card.values()[numCard];
          }
          throw new RuntimeException("invalid card value " + chr);
        }
      };
    }
  }

  public enum Type {
    HIGH_CARD, ONE_PAIR, TWO_PAIR, THREE_KIND, FULL_HOUSE, FOUR_KIND, FIVE_KIND;

    public static Type from(List<Card> cards) {
      var groups = cards.stream().collect(Collectors.groupingBy(Function.identity()));
      Integer maxGrpCnt = groups.values().stream().mapToInt(List::size).max().orElseThrow();
      return switch (maxGrpCnt) {
        case Integer mc when mc == 5 -> Type.FIVE_KIND;
        case Integer mc when mc == 4 -> Type.FOUR_KIND;
        case Integer mc when mc == 3 && groups.size() == 2 -> Type.FULL_HOUSE;
        case Integer mc when mc == 3 && groups.size() == 3 -> Type.THREE_KIND;
        case Integer mc when mc == 2 && groups.size() == 3 -> Type.TWO_PAIR;
        case Integer mc when mc == 2 && groups.size() == 4 -> Type.ONE_PAIR;
        default -> Type.HIGH_CARD;
      };

    }
  }

  public record Hand(List<Card> cards, Type type, int bid) implements Comparable<Hand> {
    public static Hand from(String input) {
      var parts = input.split("\\s+");
      int bid = Integer.parseInt(parts[1]);
      var cards = toCards(parts[0]);
      var type = Type.from(cards);
      return new Hand(cards, type, bid);
    }

    public static List<Card> toCards(String input) {
      return input.chars().mapToObj(Card::from).toList();
    }

    @Override
    public int compareTo(Hand other) {
      var typeComp = type.compareTo(other.type);
      if (typeComp != 0) {
        return typeComp;
      }
      for (var idx = 0; idx < cards.size(); idx++) {
        var cardComp = cards.get(idx).compareTo(other.cards.get(idx));
        if (cardComp != 0) {
          return cardComp;
        }
      }
      return 0;
    }
  }

  public static long getWinnings(List<Hand> hands) {
    var sorted = hands.stream().sorted().toList();
    long total = 0;
    for (var idx = 0; idx < hands.size(); idx++) {
      total += (long)sorted.get(idx).bid * (idx + 1);
    }
    return total;
  }
}
