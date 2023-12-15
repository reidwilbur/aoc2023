package com.wilb0t.aoc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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

    public Dish copyOf() {
      Element[][] copyState = new Element[state.length][];
      for (var row = 0; row < state.length; row++) {
        copyState[row] = Arrays.copyOf(state[row], state[row].length);
      }
      return new Dish(copyState);
    }

    public static Dish from(List<String> lines) {
      Element[][] state = new Element[lines.size()][];
      for (var row = 0; row < lines.size(); row++) {
        state[row] = lines.get(row).chars().mapToObj(c -> Element.from((char)c)).toArray(Element[]::new);
      }
      return new Dish(state);
    }

    int getLoad(int col) {
      var totalWeight = 0;
      for (var row = 0; row < state.length; row++) {
        var element = state[row][col];
        if (element == Element.ROUND_ROCK) {
          totalWeight += state.length - row;
        }
      }
      return totalWeight;
    }

    public int getLoad() {
      return IntStream.range(0, state[0].length).map(this::getLoad).sum();
    }


    public void cycleNorth() {
      boolean updated = true;
      int width = state[0].length;
      while (updated) {
        updated = false;
        for (var row = 1; row < state.length; row++) {
          for (var col = 0; col < width; col++) {
            var el = state[row][col];
            if (el == Element.ROUND_ROCK && state[row - 1][col] == Element.EMPTY) {
              state[row - 1][col] = Element.ROUND_ROCK;
              state[row][col] = Element.EMPTY;
              updated = true;
            }
          }
        }
      }
    }

    public void cycleWest() {
      boolean updated = true;
      int width = state[0].length;
      while (updated) {
        updated = false;
        for (var col = 1; col < width; col++) {
          for (var row = 0; row < state.length; row++) {
            var el = state[row][col];
            if (el == Element.ROUND_ROCK && state[row][col - 1] == Element.EMPTY) {
              state[row][col - 1] = Element.ROUND_ROCK;
              state[row][col] = Element.EMPTY;
              updated = true;
            }
          }
        }
      }
    }

    public void cycleSouth() {
      boolean updated = true;
      int width = state[0].length;
      while (updated) {
        updated = false;
        for (var row = state.length - 2; row >= 0; row--) {
          for (var col = 0; col < width; col++) {
            var el = state[row][col];
            if (el == Element.ROUND_ROCK && state[row + 1][col] == Element.EMPTY) {
              state[row + 1][col] = Element.ROUND_ROCK;
              state[row][col] = Element.EMPTY;
              updated = true;
            }
          }
        }
      }
    }

    public void cycleEast() {
      boolean updated = true;
      int width = state[0].length;
      while (updated) {
        updated = false;
        for (var col = width - 2; col >= 0; col--) {
          for (var row = 0; row < state.length; row++) {
            var el = state[row][col];
            if (el == Element.ROUND_ROCK && state[row][col + 1] == Element.EMPTY) {
              state[row][col + 1] = Element.ROUND_ROCK;
              state[row][col] = Element.EMPTY;
              updated = true;
            }
          }
        }
      }
    }

    public void cycle() {
      cycleNorth();
      cycleWest();
      cycleSouth();
      cycleEast();
    }
  }

  public record CycleInfo(int startIdx, int size) {}

  public static CycleInfo getCycle(List<Integer> vals) {
    var wsize = vals.size() / 2;
    var ofs = 0;
    for (; wsize > 3; wsize--) {
      for (ofs = 0; ofs < (vals.size() - (wsize * 2)); ofs++) {
        if (vals.subList(ofs, ofs + wsize).equals(vals.subList(ofs + wsize, ofs + (wsize * 2)))) {
          return new CycleInfo(ofs, wsize);
        }
      }
    }
    throw new RuntimeException("no cycle found");
  }

  public static int spinCycle(Dish dish, int cycles) {
    var workingDish = dish.copyOf();

    var sample = 250;
    var loads = new ArrayList<Integer>(sample);
    for (var idx = 0; idx < sample; idx++) {
      workingDish.cycle();
      loads.add(workingDish.getLoad());
    }

    var cycleInfo = getCycle(loads);
    cycles -= cycleInfo.startIdx + 1;
    var mod = cycles % cycleInfo.size;
    return loads.get(cycleInfo.startIdx + mod);
  }
}
