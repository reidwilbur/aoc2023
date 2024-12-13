package com.wilb0t.aoc;

import static com.wilb0t.aoc.Day18.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;

public class Day18Test {

  private final static List<Instr> TEST_INPUT = Input.TEST.load(Instr::from);
  private final static List<Instr> PUZZLE_INPUT = Input.PUZZLE.load(Instr::from);

  @Test
  void testGetLagoonArea_testInput() {
    assertThat(Day18.getLagoonArea(TEST_INPUT), is(62));
  }

  @Test
  void testGetLagoonArea_puzzleInput() {
    assertThat(Day18.getLagoonArea(PUZZLE_INPUT), is(49578));
  }

  @Test
  void testGetLagoonAreaHex_testInput() {
    assertThat(Day18.getLagoonAreaHex(TEST_INPUT), is(62L));
  }
}
