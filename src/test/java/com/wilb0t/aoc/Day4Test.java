package com.wilb0t.aoc;

import static com.wilb0t.aoc.Day4.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;

public class Day4Test {

  private static final List<Card> TEST_INPUT = Input.TEST.load(Card::from);

  private static final List<Card> PUZZLE_INPUT = Input.PUZZLE.load(Card::from);

  @Test
  void testParse() {
    assertThat(TEST_INPUT.size(), is(6));
    assertThat(
        TEST_INPUT.get(0),
        is(new Card(1, List.of(41, 48, 83, 86, 17), Set.of(83, 86, 6, 31, 17, 9, 48, 53))));
  }

  @Test
  void testPoints_testInput() {
    assertThat(TEST_INPUT.get(0).points(), is(8));
    assertThat(TEST_INPUT.get(1).points(), is(2));
    assertThat(TEST_INPUT.get(2).points(), is(2));
    assertThat(TEST_INPUT.get(3).points(), is(1));
    assertThat(TEST_INPUT.get(4).points(), is(0));
    assertThat(TEST_INPUT.get(5).points(), is(0));
  }

  @Test
  void testGetTotalPoints_testInput() {
    assertThat(Day4.getTotalPoints(TEST_INPUT), is(13));
  }

  @Test
  void testGetTotalPoints_puzzleInput() {
    assertThat(Day4.getTotalPoints(PUZZLE_INPUT), is(19855));
  }

  @Test
  void testGetCardCounts_testInput() {
    assertThat(Day4.getCardCounts(TEST_INPUT), is(30));
  }

  @Test
  void testGetCardCounts_puzzleInput() {
    assertThat(Day4.getCardCounts(PUZZLE_INPUT), is(10378710));
  }
}
