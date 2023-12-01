package com.wilb0t.aoc;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.Test;

public class Day1Test {

  private static final String[] PUZZLE_INPUT = Input.PUZZLE.loadStrings();

  private static final String[] TEST_INPUT = Input.TEST.loadStrings();

  private static final String[] TEST_INPUT2 = new Input("-test2").loadStrings();

  @Test
  void testDecodeCal_testInput() {
    assertThat(Day1.decodeCalibration(TEST_INPUT), is(142));
  }

  @Test
  void testDecodeCal_puzzleInput() {
    assertThat(Day1.decodeCalibration(PUZZLE_INPUT), is(54304));
  }

  @Test
  void testDecodeCalAlpha_testInput() {
    assertThat(Day1.decodeCalibrationAlpha(TEST_INPUT2), is(281));
  }

  @Test
  void testDecodeCalAlpha_puzzleInput() {
    // 54412 too low
    assertThat(Day1.decodeCalibrationAlpha(PUZZLE_INPUT), is(54418));
  }
}
