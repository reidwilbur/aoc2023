package com.wilb0t.aoc;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

class Day14 {

  public enum Element {
    EMPTY, ROUND_ROCK, CUBE_ROCK;

    public static Element from(char c) {
      return switch(c) {
        case 'O' -> ROUND_ROCK;
        case '#' -> CUBE_ROCK;
        case '.' -> EMPTY;
        default -> throw new RuntimeException(STR."invalid element \{c}");
      };
    }
  }

  public record Dish(Element[][] state) {
    public static Dish from(List<String> lines) {
      Element[][] state = new Element[lines.size()][];
      for (var row = 0; row < lines.size(); row++) {
        state[row] = lines.get(row).chars().mapToObj(c -> Element.from((char)c)).toArray(Element[]::new);
      }
      return new Dish(state);
    }

    int getNorthLoad(int col) {
      var weight = state.length;
      var totalWeight = 0;
      for (var row = 0; row < state.length; row++) {
        var element = state[row][col];
        if (element == Element.ROUND_ROCK) {
          totalWeight += weight;
          weight -= 1;
        } else if (element == Element.CUBE_ROCK) {
          weight = state.length - (row + 1);
        }
      }
      return totalWeight;
    }

    public int getNorthLoad() {
      return IntStream.range(0, state[0].length).map(this::getNorthLoad).sum();
    }
  }
}
