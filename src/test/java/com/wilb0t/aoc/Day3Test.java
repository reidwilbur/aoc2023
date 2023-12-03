package com.wilb0t.aoc;

import static com.wilb0t.aoc.Day3.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.util.List;
import org.junit.jupiter.api.Test;

public class Day3Test {

  private static final String[] PUZZLE_INPUT = Input.PUZZLE.loadStrings();

  private static final String[] TEST_INPUT = Input.TEST.loadStrings();

  @Test
  void testParse_testInput() {
    var parts = List.of(467, 114, 35, 633, 617, 58, 592, 755, 664, 598);
    int[][] layout =
        new int[][] {
          new int[] {0, 0, 0, -'.', -'.', 1, 1, 1, -'.', -'.'},
          new int[] {-'.', -'.', -'.', -'*', -'.', -'.', -'.', -'.', -'.', -'.'},
          new int[] {-'.', -'.', 2, 2, -'.', -'.', 3, 3, 3, -'.'},
          new int[] {-'.', -'.', -'.', -'.', -'.', -'.', -'#', -'.', -'.', -'.'},
          new int[] {4, 4, 4, -'*', -'.', -'.', -'.', -'.', -'.', -'.'},
          new int[] {-'.', -'.', -'.', -'.', -'.', -'+', -'.', 5, 5, -'.'},
          new int[] {-'.', -'.', 6, 6, 6, -'.', -'.', -'.', -'.', -'.'},
          new int[] {-'.', -'.', -'.', -'.', -'.', -'.', 7, 7, 7, -'.'},
          new int[] {-'.', -'.', -'.', -'$', -'.', -'*', -'.', -'.', -'.', -'.'},
          new int[] {-'.', 8, 8, 8, -'.', 9, 9, 9, -'.', -'.'},
        };

    var schematic = Schematic.from(TEST_INPUT);
    assertThat(schematic.parts(), is(parts));

    assertThat(schematic.layout().length, is(layout.length));
    assertThat(schematic.layout()[0].length, is(layout[0].length));
    for (var idx = 0; idx < layout.length; idx++) {
      assertArrayEquals(layout[idx], schematic.layout()[idx]);
    }
  }

  @Test
  void testGetParNums_testInput() {
    assertThat(Day3.getPartNums(TEST_INPUT), is(4361));
  }

  @Test
  void testGetParNums_puzzleInput() {
    assertThat(Day3.getPartNums(PUZZLE_INPUT), is(532445));
  }

  @Test
  void testGetGearRatios_testInput() {
    assertThat(Day3.getGearRatios(TEST_INPUT), is(467835L));
  }

  @Test
  void testGetGearRatios_puzzleInput() {
    assertThat(Day3.getGearRatios(PUZZLE_INPUT), is(79842967L));
  }
}
