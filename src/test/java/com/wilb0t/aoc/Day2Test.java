package com.wilb0t.aoc;

import static com.wilb0t.aoc.Day2.*;
import static com.wilb0t.aoc.Util.Pair;
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
                new Reveal(List.of(new Pair<>("blue", 3), new Pair<>("red", 4))),
                new Reveal(
                    List.of(new Pair<>("red", 1), new Pair<>("green", 2), new Pair<>("blue", 6))),
                new Reveal(List.of(new Pair<>("green", 2)))));

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
