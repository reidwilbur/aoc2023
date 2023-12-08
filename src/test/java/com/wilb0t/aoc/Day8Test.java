package com.wilb0t.aoc;

import static com.wilb0t.aoc.Day8.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;

import java.util.List;
import org.junit.jupiter.api.Test;

public class Day8Test {

  private static final DesertMap TEST_INPUT = Input.TEST.parse(DesertMap::from);
  private static final DesertMap TEST_INPUT2 = new Input("-test2").parse(DesertMap::from);
  private static final DesertMap PUZZLE_INPUT = Input.PUZZLE.parse(DesertMap::from);

  @Test
  void testGetSteps_testInput() {
    assertThat(Day8.getSteps(TEST_INPUT), is(2L));
    assertThat(Day8.getSteps(TEST_INPUT2), is(6L));
  }

  @Test
  void testGetSteps_puzzleInput() {
    assertThat(Day8.getSteps(PUZZLE_INPUT), is(20659L));
  }

  @Test
  void testGetGhostSteps_testInput() {
    var map = new Input("-test3").parse(DesertMap::from);
    assertThat(Day8.getGhostSteps(map), is(6L));
  }

  @Test
  void testGetGhostSteps_puzzleInput() {
    assertThat(Day8.getGhostSteps(PUZZLE_INPUT), is(15690466351717L));
  }
}
