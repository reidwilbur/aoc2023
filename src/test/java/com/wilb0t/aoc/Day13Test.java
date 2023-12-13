package com.wilb0t.aoc;

import static com.wilb0t.aoc.Day13.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;

public class Day13Test {

  private static final List<Pattern> TEST_INPUT = Input.TEST.parse(Pattern::from);
  private static final List<Pattern> PUZZLE_INPUT = Input.PUZZLE.parse(Pattern::from);

  @Test
  void testGetHRef() {
    assertThat(TEST_INPUT.getFirst().getHRef(), is(5));
    assertThat(TEST_INPUT.get(1).getHRef(), is(0));
  }

  @Test
  void testGetVRef() {
    assertThat(TEST_INPUT.getFirst().getVRef(), is(0));
    assertThat(TEST_INPUT.get(1).getVRef(), is(4));
  }

  @Test
  void testGetReflectionSummary_testInput() {
    assertThat(Day13.getReflectionSummary(TEST_INPUT), is(405));
  }

  @Test
  void testGetReflectionSummary_puzzleInput() {
    assertThat(Day13.getReflectionSummary(PUZZLE_INPUT), is(37561));
  }
}
