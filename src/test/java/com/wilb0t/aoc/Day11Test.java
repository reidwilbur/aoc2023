package com.wilb0t.aoc;

import static com.wilb0t.aoc.Day11.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.Test;

public class Day11Test {

  private static final Image TEST_INPUT = Input.TEST.parse(Image::from);
  private static final Image PUZZLE_INPUT = Input.PUZZLE.parse(Image::from);

  @Test
  void testPathSum_testInput() {
    assertThat(Day11.pathSum(TEST_INPUT, 2), is(374L));
    assertThat(Day11.pathSum(TEST_INPUT, 10), is(1030L));
    assertThat(Day11.pathSum(TEST_INPUT, 100), is(8410L));
  }

  @Test
  void testPathSum_puzzleInput() {
    assertThat(Day11.pathSum(PUZZLE_INPUT, 2), is(9418609L));
    assertThat(Day11.pathSum(PUZZLE_INPUT, 1_000_000), is(593821230983L));
  }
}
