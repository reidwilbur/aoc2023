package com.wilb0t.aoc;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;

public class Day2Test {

  private static final List<Day2.Round> PUZZLE_INPUT = Input.PUZZLE.load(Day2.Round.mapper());

  private static final List<Day2.Round> TEST_INPUT = Input.TEST.load(Day2.Round.mapper());

  @Test
  void testGetScore_testInput() {
    assertThat(Day2.getScore(TEST_INPUT), is(15));
  }

  @Test
  void testGetScore_puzzleInput() {
    assertThat(Day2.getScore(PUZZLE_INPUT), is(14375));
  }

  @Test
  void testGetScoreForOutcome_testInput() {
    assertThat(Day2.getScoreForOutcome(TEST_INPUT), is(12));
  }

  @Test
  void testGetScoreForOutcome_puzzleInput() {
    assertThat(Day2.getScoreForOutcome(PUZZLE_INPUT), is(10274));
  }

}
