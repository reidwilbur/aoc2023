package com.wilb0t.aoc;

import static com.wilb0t.aoc.Day2.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;

public class Day2Test {

  private static final List<Game> PUZZLE_INPUT = Input.PUZZLE.parse(Game::from);

  private static final List<Game> TEST_INPUT = Input.TEST.parse(Game::from);

  @Test
  void testParse_testInput() {
    var exp =
        new Day2.Game(
            1,
            List.of(
                new Reveal(List.of(new Cube(Color.BLUE, 3), new Cube(Color.RED, 4))),
                new Reveal(
                    List.of(
                        new Cube(Color.RED, 1), new Cube(Color.GREEN, 2), new Cube(Color.BLUE, 6))),
                new Reveal(List.of(new Cube(Color.GREEN, 2)))));

    assertThat(TEST_INPUT.get(0), is(exp));
  }

  @Test
  void testGetValidGames_testInput() {
    assertThat(Day2.getValidGames(TEST_INPUT), is(8));
  }

  @Test
  void testGetValidGames_puzzleInput() {
    assertThat(Day2.getValidGames(PUZZLE_INPUT), is(2679));
  }

  @Test
  void testGetPower_testInput() {
    assertThat(Day2.getPower(TEST_INPUT), is(2286L));
  }

  @Test
  void testGetPower_puzzleInput() {
    assertThat(Day2.getPower(PUZZLE_INPUT), is(77607L));
  }
}
