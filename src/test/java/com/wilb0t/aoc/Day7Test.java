package com.wilb0t.aoc;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.Test;

public class Day7Test {
  private static final String[] PUZZLE_INPUT = Input.PUZZLE.loadStrings();

  private static final String[] TEST_INPUT = Input.TEST.loadStrings();

  @Test
  public void testGetDirSizeSum_testInput() {
    assertThat(Day7.getDirSizeSum(TEST_INPUT), is(95437L));
  }

  @Test
  public void testGetDirSizeSum_puzzleInput() {
    assertThat(Day7.getDirSizeSum(PUZZLE_INPUT), is(2061777L));
  }

  @Test
  public void testGetDirSizeToDelete_testInput() {
    assertThat(Day7.getDirSizeToDelete(TEST_INPUT), is(24933642L));
  }

  @Test
  public void testGetDirSizeToDelete_puzzleInput() {
    assertThat(Day7.getDirSizeToDelete(PUZZLE_INPUT), is(4473403L));
  }
}
