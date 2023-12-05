package com.wilb0t.aoc;

import static com.wilb0t.aoc.Day5.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.List;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.Test;

public class Day5Test {

  private static final Almanac TEST_INPUT = Input.TEST.parse(Almanac::from);
  private static final Almanac PUZZLE_INPUT = Input.PUZZLE.parse(Almanac::from);

  @Test
  void testParse() {
    assertThat(TEST_INPUT.seeds(), is(List.of(79L, 14L, 55L, 13L)));
    assertThat(TEST_INPUT.maps().size(), is(7));
    assertThat(
        TEST_INPUT.maps().keySet(),
        is(Set.of("seed", "soil", "fertilizer", "water", "light", "temperature", "humidity")));
    assertThat(
        TEST_INPUT.maps().get("seed"),
        is(
            new RangeMap(
                "seed", "soil", List.of(new Range(52L, 50L, 48L), new Range(50L, 98L, 2L)))));
  }

  @Test
  void testGetMapping() {
    assertThat(TEST_INPUT.maps().get("seed").apply(79), is(Map.entry("soil", 81L)));
    assertThat(TEST_INPUT.maps().get("seed").apply(14), is(Map.entry("soil", 14L)));
    assertThat(TEST_INPUT.maps().get("seed").apply(55), is(Map.entry("soil", 57L)));
    assertThat(TEST_INPUT.maps().get("seed").apply(13), is(Map.entry("soil", 13L)));
  }

  @Test
  void testGetLocation() {
    assertThat(TEST_INPUT.getLocation(79), is(82L));
    assertThat(TEST_INPUT.getLocation(14), is(43L));
    assertThat(TEST_INPUT.getLocation(55), is(86L));
    assertThat(TEST_INPUT.getLocation(13), is(35L));
  }

  @Test
  void testGetLowestLocation_testInput() {
    assertThat(TEST_INPUT.getLowestLocation(), is(35L));
  }

  @Test
  void testGetLowestLocation_puzzleInput() {
    assertThat(PUZZLE_INPUT.getLowestLocation(), is(178159714L));
  }

  @Test
  void testGetLowestLocationSeedRanges_testInput() {
    assertThat(TEST_INPUT.getLowestLocationSeedRanges(), is(46L));
  }

  @Test
  void testGetLowestLocationSeedRanges_puzzleInput() {
    assertThat(PUZZLE_INPUT.getLowestLocationSeedRanges(), is(100165128L));
  }
}
