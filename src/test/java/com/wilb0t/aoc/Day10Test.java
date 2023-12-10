package com.wilb0t.aoc;

import static com.wilb0t.aoc.Day10.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.Test;

public class Day10Test {

  private static final Scan TEST_INPUT1 = Input.TEST.parse(Scan::from);
  private static final Scan TEST_INPUT2 = new Input("-test2").parse(Scan::from);
  private static final Scan PUZZLE_INPUT = Input.PUZZLE.parse(Scan::from);

  @Test
  void testGetMaxDist_testInput() {
    assertThat(Day10.getMaxDist(TEST_INPUT1), is(4));
    assertThat(Day10.getMaxDist(TEST_INPUT2), is(8));
  }

  @Test
  void testGetMaxDist_puzzleInput() {
    assertThat(Day10.getMaxDist(PUZZLE_INPUT), is(7012));
  }

  @Test
  void testGetIntCount_testInput() {
    var scan = new Input("-test3").parse(Scan::from);
    assertThat(Day10.getIntCount(scan), is(10));
  }

  @Test
  void testGetIntCount_puzzleInput() {
    assertThat(Day10.getIntCount(PUZZLE_INPUT), is(395));
  }
}
