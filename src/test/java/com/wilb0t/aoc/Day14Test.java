package com.wilb0t.aoc;

import static com.wilb0t.aoc.Day14.Dish;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.Test;

public class Day14Test {

  public static Dish TEST_INPUT = Input.TEST.parse(Dish::from);
  public static Dish PUZZLE_INPUT = Input.PUZZLE.parse(Dish::from);

  @Test
  void testGetNorthLoad() {
    assertThat(TEST_INPUT.getNorthLoad(0), is(34));
    assertThat(TEST_INPUT.getNorthLoad(1), is(27));
    assertThat(TEST_INPUT.getNorthLoad(2), is(17));
  }

  @Test
  void testGetNorthLoad_testInput() {
    assertThat(TEST_INPUT.getNorthLoad(), is(136));
  }

  @Test
  void testGetNorthLoad_puzzleInput() {
    assertThat(PUZZLE_INPUT.getNorthLoad(), is(108641));
  }
}
