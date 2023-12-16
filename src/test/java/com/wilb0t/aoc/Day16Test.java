package com.wilb0t.aoc;

import static com.wilb0t.aoc.Day16.Contraption;
import static com.wilb0t.aoc.Day16.Dir;
import static com.wilb0t.aoc.Day16.Pos;
import static com.wilb0t.aoc.Day16.State;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.Test;

public class Day16Test {

  private static final Contraption TEST_INPUT = Input.TEST.parseStream(Contraption::parse);
  private static final Contraption PUZZLE_INPUT = Input.PUZZLE.parseStream(Contraption::parse);

  @Test
  void testGetLitCount_testInput() {
    assertThat(TEST_INPUT.getLitCount(new State(new Pos(0, 0), Dir.RT)), is(46));
  }

  @Test
  void testGetLitCount_puzzleInput() {
    assertThat(PUZZLE_INPUT.getLitCount(new State(new Pos(0, 0), Dir.RT)), is(7415));
  }

  @Test
  void testGetMaxListCount_testInput() {
    assertThat(TEST_INPUT.getMaxLitCount(), is(51));
  }

  @Test
  void testGetMaxListCount_puzzleInput() {
    assertThat(PUZZLE_INPUT.getMaxLitCount(), is(7943));
  }
}
