package com.wilb0t.aoc;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;

public class Day15Test {

  public static List<String> TEST_INPUT = Input.TEST.parse(Day15::parse);
  public static List<String> PUZZLE_INPUT = Input.PUZZLE.parse(Day15::parse);

  @Test
  void testHash() {
    assertThat(Day15.hash("HASH"), is(52));
    assertThat(Day15.hash(TEST_INPUT.getFirst()), is(30));
    assertThat(Day15.hash(TEST_INPUT.getLast()), is(231));
  }

  @Test
  void testGetHashSum_testInput() {
    assertThat(Day15.getHashSum(TEST_INPUT), is(1320));
  }

  @Test
  void testGetHashSum_puzzleInput() {
    assertThat(Day15.getHashSum(PUZZLE_INPUT), is(511215));
  }

  @Test
  void testGetFocusPower_testInput() {
    assertThat(Day15.getFocusPower(TEST_INPUT), is(145));
  }

  @Test
  void testGetFocusPower_puzzleInput() {
    assertThat(Day15.getFocusPower(PUZZLE_INPUT), is(236057));
  }
}
