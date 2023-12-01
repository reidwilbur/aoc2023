package com.wilb0t.aoc;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.Test;

public class Day1Test {

  private static final String[] PUZZLE_INPUT = Input.PUZZLE.loadStrings();

  private static final String[] TEST_INPUT = Input.TEST.loadStrings();

  @Test
  void testGetMostCals_testInput() {
    assertThat(Day1.getMostCals(TEST_INPUT, 1), is(24000));
  }
  
  @Test
  void testGetMostCals_puzzleInput() {
    assertThat(Day1.getMostCals(PUZZLE_INPUT, 1), is(70374));
  }
  
  @Test
  void testGetMostCals_Top3_testInput() {
    assertThat(Day1.getMostCals(TEST_INPUT, 3), is(45000));
  }

  @Test
  void testGetMostCals_Top3_puzzleInput() {
    assertThat(Day1.getMostCals(PUZZLE_INPUT, 3), is(204610));
  }
}
