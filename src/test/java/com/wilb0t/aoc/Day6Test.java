package com.wilb0t.aoc;

import static com.wilb0t.aoc.Day6.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;

public class Day6Test {

  private static final List<RaceRecord> TEST_INPUT = Input.TEST.parse(RaceRecord::from);
  private static final List<RaceRecord> PUZZLE_INPUT = Input.PUZZLE.parse(RaceRecord::from);

  @Test
  void testGetWinneCount_testInput() {
    assertThat(Day6.getWinners(TEST_INPUT), is(288L));
  }

  @Test
  void testGetWinneCount_puzzleInput() {
    assertThat(Day6.getWinners(PUZZLE_INPUT), is(840336L));
  }

  @Test
  void testGetWinneSingleRace_testInput() {
    var input = Input.TEST.parse(RaceRecord::flatFrom);
    assertThat(Day6.getWinners(List.of(input)), is(71503L));
  }

  @Test
  void testGetWinneSingleRace_puzzleInput() {
    var input = Input.PUZZLE.parse(RaceRecord::flatFrom);
    assertThat(Day6.getWinners(List.of(input)), is(41382569L));
  }
}
