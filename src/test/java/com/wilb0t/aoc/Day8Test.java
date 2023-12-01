package com.wilb0t.aoc;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.Test;

public class Day8Test {
  private static final int[][] PUZZLE_INPUT = Input.PUZZLE.loadIntGrid();

  private static final int[][] TEST_INPUT = Input.TEST.loadIntGrid();

  @Test
  public void testGetVisibleCount_testInput() {
    assertThat(Day8.getVisibleCount(TEST_INPUT), is(21));
  }

  @Test
  public void testGetVisibleCount_puzzleInput() {
    assertThat(Day8.getVisibleCount(PUZZLE_INPUT), is(1693));
  }

  @Test
  public void testGetMaxScenic_testInput() {
    assertThat(Day8.getMaxScenic(TEST_INPUT), is(8));
  }

  @Test
  public void testGetMaxScenic_puzzleInput() {
    assertThat(Day8.getMaxScenic(PUZZLE_INPUT), is(422059));
  }

  @Test
  public void testGetScore() {
    assertThat(Day8.getScore(1, 2, TEST_INPUT), is(4));
    assertThat(Day8.getScore(3, 2, TEST_INPUT), is(8));
  }
}
