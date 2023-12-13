package com.wilb0t.aoc;

import static com.wilb0t.aoc.Day13.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;

public class Day13Test {

  private static final List<Pattern> TEST_INPUT = Input.TEST.parse(Pattern::from);
  private static final List<Pattern> PUZZLE_INPUT = Input.PUZZLE.parse(Pattern::from);

  @Test
  void testGetHRef() {
    assertThat(TEST_INPUT.getFirst().getHRefs(), is(Set.of(4)));
    assertThat(TEST_INPUT.get(1).getHRefs(), is(Set.of()));
  }

  @Test
  void testGetVRef() {
    assertThat(TEST_INPUT.getFirst().getVRefs(), is(Set.of()));
    assertThat(TEST_INPUT.get(1).getVRefs(), is(Set.of(3)));
  }

  @Test
  void testGetSummarySmudge() {
    assertThat(TEST_INPUT.getFirst().getSummarySmudge(), is(300));
    assertThat(TEST_INPUT.get(1).getSummarySmudge(), is(100));
  }

  @Test
  void testGetReflectionSummary_testInput() {
    assertThat(Day13.getReflectionSummary(TEST_INPUT), is(405));
  }

  @Test
  void testGetReflectionSummary_puzzleInput() {
    assertThat(Day13.getReflectionSummary(PUZZLE_INPUT), is(37561));
  }

  @Test
  void testGetReflectionSummarySmudge_testInput() {
    assertThat(Day13.getReflectionSummarySmudge(TEST_INPUT), is(400));
  }

  @Test
  void testGetReflectionSummarySmudge_puzzleInput() {
    assertThat(Day13.getReflectionSummarySmudge(PUZZLE_INPUT), is(31108));
  }
}
