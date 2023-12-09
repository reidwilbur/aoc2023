package com.wilb0t.aoc;

import static com.wilb0t.aoc.Day9.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.Test;

public class Day9Test {

  private static final Report TEST_INPUT = Input.TEST.parse(Report::from);
  private static final Report PUZZLE_INPUT = Input.PUZZLE.parse(Report::from);

  @Test
  void testExtrapolateRight_testInput() {
    assertThat(Day9.extrapolateRight(TEST_INPUT), is(114));
  }

  @Test
  void testExtrapolateRight_puzzleInput() {
    assertThat(Day9.extrapolateRight(PUZZLE_INPUT), is(1772145754));
  }

  @Test
  void testExtrapolateLeft_testInput() {
    assertThat(Day9.extrapolateLeft(TEST_INPUT), is(2));
  }

  @Test
  void testExtrapolateLeft_puzzleInput() {
    assertThat(Day9.extrapolateLeft(PUZZLE_INPUT), is(867));
  }
}
