package com.wilb0t.aoc;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class Day7 {

  // all this code is hot hot trash

  public static class StandardOrder implements Comparator<Character> {

    @Override
    public int compare(Character o1, Character o2) {
      return StandardCard.from(o1).compareTo(StandardCard.from(o2));
    }
  }

  public static class JokerOrder implements Comparator<Character> {

    @Override
    public int compare(Character o1, Character o2) {
      return JokerCard.from(o1).compareTo(JokerCard.from(o2));
    }
  }

  public enum StandardCard {
    TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING, ACE;

    public static StandardCard from(int chr) {
      return switch (chr) {
        case 'T' -> StandardCard.TEN;
        case 'J' -> StandardCard.JACK;
        case 'Q' -> StandardCard.QUEEN;
        case 'K' -> StandardCard.KING;
        case 'A' -> StandardCard.ACE;
        default -> {
          var numCard = chr - '2';
          if (numCard >= 0 && numCard < 8) {
            yield StandardCard.values()[numCard];
          }
          throw new RuntimeException("invalid card value " + chr);
        }
      };
    }
  }

  public enum JokerCard {
    JOKER, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, QUEEN, KING, ACE;

    public static JokerCard from(int chr) {
      return switch (chr) {
        case 'T' -> JokerCard.TEN;
        case 'J' -> JokerCard.JOKER;
        case 'Q' -> JokerCard.QUEEN;
        case 'K' -> JokerCard.KING;
        case 'A' -> JokerCard.ACE;
        default -> {
          var numCard = chr - '2';
          if (numCard >= 0 && numCard < 8) {
            yield JokerCard.values()[numCard + 1];
          }
          throw new RuntimeException("invalid card value " + chr);
        }
      };
    }
  }

  public enum Type {
    HIGH_CARD, ONE_PAIR, TWO_PAIR, THREE_KIND, FULL_HOUSE, FOUR_KIND, FIVE_KIND;

    public static Type from(String cards) {
      var groups = cards.chars().mapToObj(i -> (char)i).collect(Collectors.groupingBy(c -> c, Collectors.counting()));
      Integer maxGrpSize = (int)groups.values().stream().mapToLong(l -> l).max().orElseThrow();
      return switch (maxGrpSize) {
        case Integer mc when mc == 5 -> Type.FIVE_KIND;
        case Integer mc when mc == 4 -> Type.FOUR_KIND;
        case Integer mc when mc == 3 && groups.size() == 2 -> Type.FULL_HOUSE;
        case Integer mc when mc == 3 && groups.size() == 3 -> Type.THREE_KIND;
        case Integer mc when mc == 2 && groups.size() == 3 -> Type.TWO_PAIR;
        case Integer mc when mc == 2 && groups.size() == 4 -> Type.ONE_PAIR;
        default -> Type.HIGH_CARD;
      };
    }

    public static Type jokerType(List<JokerCard> cards) {
      var groups = cards.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
      Long maxGrpSize = groups.values().stream().mapToLong(l -> l).max().orElseThrow();
      int groupCount = groups.size();

      return switch (maxGrpSize) {
        case Long mc when mc == 5 -> Type.FIVE_KIND;
        case Long mc when mc == 4 -> Type.FOUR_KIND;
        case Long mc when mc == 3 && groupCount == 2 -> Type.FULL_HOUSE;
        case Long mc when mc == 3 && groupCount == 3 -> Type.THREE_KIND;
        case Long mc when mc == 2 && groupCount == 3 -> Type.TWO_PAIR;
        case Long mc when mc == 2 && groupCount == 4 -> Type.ONE_PAIR;
        default -> Type.HIGH_CARD;
      };
    }

    public static Type fromJoker(List<JokerCard> cards) {
      var groups = cards.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
      Long jokerCount = groups.getOrDefault(JokerCard.JOKER, 0L);

      var type = jokerType(cards);
      if (jokerCount == 0) {
        return type;
      }

      var comb = new ArrayList<>(IntStream.range(0, jokerCount.intValue()).mapToObj(i -> JokerCard.TWO.ordinal()).toList());
      var jokerType = Type.HIGH_CARD;
      while(comb.get(jokerCount.intValue() - 1) <= JokerCard.ACE.ordinal()) {
        var combidx = 0;
        var workCards = new ArrayList<>(cards);
        for (var cardidx = 0; cardidx < workCards.size(); cardidx++) {
          if (workCards.get(cardidx) == JokerCard.JOKER) {
            workCards.set(cardidx, JokerCard.values()[comb.get(combidx++)]);
          }
        }
        var testType = jokerType(workCards);
        jokerType = (testType.ordinal() > jokerType.ordinal()) ? testType : jokerType;

        comb.set(0, comb.get(0) + 1);
        for (var idx = 0; idx < comb.size() - 1; idx++) {
          if (comb.get(idx) > JokerCard.ACE.ordinal()) {
            comb.set(idx, JokerCard.TWO.ordinal());
            comb.set(idx + 1, comb.get(idx + 1) + 1);
          }
        }
      }
      return jokerType;
    }
  }

  public record Hand(String cards, Type type, int bid, Comparator<Character> comp) implements Comparable<Hand> {
    public static Hand from(String input) {
      var parts = input.split("\\s+");
      int bid = Integer.parseInt(parts[1]);
      var cards = parts[0];
      var type = Type.from(cards);
      return new Hand(cards, type, bid, new StandardOrder());
    }

    public static List<StandardCard> toCards(String input) {
      return input.chars().mapToObj(StandardCard::from).toList();
    }

    @Override
    public int compareTo(Hand other) {
      var typeComp = type.compareTo(other.type);
      if (typeComp != 0) {
        return typeComp;
      }
      for (var idx = 0; idx < cards.length(); idx++) {
        var cardComp = comp.compare(cards.charAt(idx), other.cards.charAt(idx));
        if (cardComp != 0) {
          return cardComp;
        }
      }
      return 0;
    }
  }

  public record JokerHand(List<JokerCard> cards, Type type, int bid) implements Comparable<JokerHand> {
    public static JokerHand from(String input) {
      var parts = input.split("\\s+");
      int bid = Integer.parseInt(parts[1]);
      var cards = toCards(parts[0]);
      var type = Type.fromJoker(cards);
      return new JokerHand(cards, type, bid);
    }

    public static List<JokerCard> toCards(String input) {
      return input.chars().mapToObj(JokerCard::from).toList();
    }

    @Override
    public int compareTo(JokerHand other) {
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

  public static long getJokerWinnings(List<JokerHand> hands) {
    var sorted = hands.stream().sorted().toList();
    long total = 0;
    for (var idx = 0; idx < hands.size(); idx++) {
      total += (long)sorted.get(idx).bid * (idx + 1);
    }
    return total;
  }
}
