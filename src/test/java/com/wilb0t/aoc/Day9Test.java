package com.wilb0t.aoc;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import static com.wilb0t.aoc.Day9.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;

public class Day9Test {
  private static final List<Direction> PUZZLE_INPUT = Input.PUZZLE.loadFlat(Direction::from);

  private static final List<Direction> TEST_INPUT = Input.TEST.loadFlat(Direction::from);
  
  private static final List<Direction> TEST_INPUT2 = 
      Stream.of(
          "R 5",
           "U 8",
           "L 8",
           "D 3",
           "R 17",
           "D 10",
           "L 25",
           "U 20")
          .flatMap(Direction::from).toList();

  @Test
  public void testGetTailPosCount1_testInput() {
    assertThat(Day9.getTailPosCount(TEST_INPUT, 1), is(13));
  }

  @Test
  public void testGetTailPosCount1_puzzleInput() {
    assertThat(Day9.getTailPosCount(PUZZLE_INPUT, 1), is(5779));
  }

  @Test
  public void testGetTailPosCount9_testInput() {
    assertThat(Day9.getTailPosCount(TEST_INPUT, 9), is(1));
  }
  
  @Test
  public void testGetTailPosCount9_testInput2() {
    assertThat(Day9.getTailPosCount(TEST_INPUT2, 9), is(36));
  }

  @Test
  public void testGetTailPosCount9_puzzleInput() {
    assertThat(Day9.getTailPosCount(PUZZLE_INPUT, 9), is(2331));
  }
  
  @Test
  public void testLoader() {
    var moves = Arrays.stream(Input.PUZZLE.loadStrings()).mapToInt(s -> Integer.parseInt(s.substring(2))).sum();
    assertThat(PUZZLE_INPUT.size(), is(moves));
  }
}
