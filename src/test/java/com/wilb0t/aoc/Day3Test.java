package com.wilb0t.aoc;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.Test;

public class Day3Test {

  private static final String[] PUZZLE_INPUT = Input.PUZZLE.loadStrings();

  private static final String[] TEST_INPUT = Input.TEST.loadStrings();

  @Test
  public void testGetPrioSum_testInput() {
    assertThat(Day3.getPrioSum(TEST_INPUT), is(157));
  }

  @Test
  public void testGetPrioSum_puzzleInput() {
    assertThat(Day3.getPrioSum(PUZZLE_INPUT), is(8123));
  }

  @Test
  public void testGetBadgePrioSum_testInput() {
    assertThat(Day3.getBadgePrioSum(TEST_INPUT), is(70));
  }

  @Test
  public void testGetBadgePrioSum_puzzleInput() {
    assertThat(Day3.getBadgePrioSum(PUZZLE_INPUT), is(2620));
  }

}
