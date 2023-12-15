package com.wilb0t.aoc;

import static com.wilb0t.aoc.Day14.Dish;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.lessThan;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.util.List;
import org.junit.jupiter.api.Test;

public class Day14Test {

  public static Dish TEST_INPUT = Input.TEST.parse(Dish::from);
  public static Dish PUZZLE_INPUT = Input.PUZZLE.parse(Dish::from);

  @Test
  void testGetNorthLoad() {
    var ti = TEST_INPUT.copyOf();
    ti.cycleNorth();
    assertThat(ti.getLoad(0), is(34));
    assertThat(ti.getLoad(1), is(27));
    assertThat(ti.getLoad(2), is(17));
  }

  @Test
  void testGetNorthLoad_testInput() {
    var ti = TEST_INPUT.copyOf();
    ti.cycleNorth();
    assertThat(ti.getLoad(), is(136));
  }

  @Test
  void testGetNorthLoad_puzzleInput() {
    var pi = PUZZLE_INPUT.copyOf();
    pi.cycleNorth();
    assertThat(pi.getLoad(), is(108641));
  }

  @Test
  void testCycleNorth() {
    var lines =
        List.of(
            "OOOO.#.O..",
            "OO..#....#",
            "OO..O##..O",
            "O..#.OO...",
            "........#.",
            "..#....#.#",
            "..O..#.O.O",
            "..O.......",
            "#....###..",
            "#....#....");
    var expDish = Dish.from(lines);
    var ti = TEST_INPUT.copyOf();
    ti.cycleNorth();
    for (var row = 0; row < ti.state().length; row++) {
      assertArrayEquals(ti.state()[row], expDish.state()[row]);
    }
  }

  @Test
  void testCycle() {
    var lines =
        List.of(
            ".....#....",
            "....#...O#",
            "...OO##...",
            ".OO#......",
            ".....OOO#.",
            ".O#...O#.#",
            "....O#....",
            "......OOOO",
            "#...O###..",
            "#..OO#...."
        );
    var expDish = Dish.from(lines);
    var ti = TEST_INPUT.copyOf();
    ti.cycle();
    for (var row = 0; row < ti.state().length; row++) {
      assertArrayEquals(ti.state()[row], expDish.state()[row]);
    }

    lines = List.of(
        ".....#....",
        "....#...O#",
        ".....##...",
        "..O#......",
        ".....OOO#.",
        ".O#...O#.#",
        "....O#...O",
        ".......OOO",
        "#..OO###..",
        "#.OOO#...O");
    expDish = Dish.from(lines);
    ti.cycle();
    for (var row = 0; row < ti.state().length; row++) {
      assertArrayEquals(ti.state()[row], expDish.state()[row]);
    }

    lines = List.of(
        ".....#....",
        "....#...O#",
        ".....##...",
        "..O#......",
        ".....OOO#.",
        ".O#...O#.#",
        "....O#...O",
        ".......OOO",
        "#...O###.O",
        "#.OOO#...O");
    expDish = Dish.from(lines);
    ti.cycle();
    for (var row = 0; row < ti.state().length; row++) {
      assertArrayEquals(ti.state()[row], expDish.state()[row]);
    }
  }

  @Test
  void testSpinCycle_testInput() {
    assertThat(Day14.spinCycle(TEST_INPUT, 1_000_000_000), is(64));
  }

  @Test
  void testSpinCycle_puzzleInput() {
    assertThat(Day14.spinCycle(PUZZLE_INPUT, 1_000_000_000), is(84328));
  }
}
