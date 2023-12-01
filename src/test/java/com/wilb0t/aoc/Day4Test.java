package com.wilb0t.aoc;

import static com.wilb0t.aoc.Util.Pair;
import static com.wilb0t.aoc.Day4.Section;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;

public class Day4Test {

  private static final List<Pair<Section, Section>> PUZZLE_INPUT = Input.PUZZLE.load(Day4::from);

  private static final List<Pair<Section, Section>> TEST_INPUT = Input.TEST.load(Day4::from);

  @Test
  public void testContainCount_testInput() {
    assertThat(Day4.getContainCount(TEST_INPUT), is(2));
  }

  @Test
  public void testContainCount_puzzleInput() {
    assertThat(Day4.getContainCount(PUZZLE_INPUT), is(433));
  }

  @Test
  public void testOverlapCount_testInput() {
    assertThat(Day4.getOverlapCount(TEST_INPUT), is(4));
  }

  @Test
  public void testOverlapCount_puzzleInput() {
    assertThat(Day4.getOverlapCount(PUZZLE_INPUT), is(852));
  }
}
