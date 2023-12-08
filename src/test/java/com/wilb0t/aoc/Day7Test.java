package com.wilb0t.aoc;

import static com.wilb0t.aoc.Day7.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;

import java.util.List;
import org.junit.jupiter.api.Test;

public class Day7Test {

  private static final List<Hand> TEST_INPUT = Input.TEST.load(Hand::from);
  private static final List<Hand> PUZZLE_INPUT = Input.PUZZLE.load(Hand::from);

  @Test
  void testType() {
    assertThat(Type.from("AAAAA"), is(Type.FIVE_KIND));
    assertThat(Type.from("AA8AA"), is(Type.FOUR_KIND));
    assertThat(Type.from("23332"), is(Type.FULL_HOUSE));
    assertThat(Type.from("TTT98"), is(Type.THREE_KIND));
    assertThat(Type.from("23432"), is(Type.TWO_PAIR));
    assertThat(Type.from("A23A4"), is(Type.ONE_PAIR));
    assertThat(Type.from("23456"), is(Type.HIGH_CARD));
  }

  @Test
  void testHandCompare() {
    assertThat(Hand.from("AAAAA 0").compareTo(Hand.from("AA8AA 0")), greaterThan(0));
    assertThat(Hand.from("AA8AA 0").compareTo(Hand.from("23332 0")), greaterThan(0));
    assertThat(Hand.from("23332 0").compareTo(Hand.from("TTT98 0")), greaterThan(0));
    assertThat(Hand.from("TTT98 0").compareTo(Hand.from("23432 0")), greaterThan(0));
    assertThat(Hand.from("23432 0").compareTo(Hand.from("A23A4 0")), greaterThan(0));
    assertThat(Hand.from("A23A4 0").compareTo(Hand.from("23456 0")), greaterThan(0));
    assertThat(Hand.from("AAAAA 0").compareTo(Hand.from("23456 0")), greaterThan(0));

    assertThat(Hand.from("33332 0").compareTo(Hand.from("2AAAA 0")), greaterThan(0));
    assertThat(Hand.from("77888 0").compareTo(Hand.from("77788 0")), greaterThan(0));

    assertThat(Hand.from("77788 0").compareTo(Hand.from("77788 0")), is(0));
  }

  @Test
  void TestTypeWithJokers() {
    assertThat(Type.fromJoker(JokerHand.toCards("32T3K")), is(Type.ONE_PAIR));
    assertThat(Type.fromJoker(JokerHand.toCards("T55J5")), is(Type.FOUR_KIND));
    assertThat(Type.fromJoker(JokerHand.toCards("KK677")), is(Type.TWO_PAIR));
    assertThat(Type.fromJoker(JokerHand.toCards("KTJJT")), is(Type.FOUR_KIND));
    assertThat(Type.fromJoker(JokerHand.toCards("QQQJA")), is(Type.FOUR_KIND));
    assertThat(Type.fromJoker(JokerHand.toCards("JJJJJ")), is(Type.FIVE_KIND));
  }

  @Test
  void testGetWinnings_testInput() {
    assertThat(Day7.getWinnings(TEST_INPUT), is(6440L));
  }

  @Test
  void testGetWinnings_puzzleInput() {
    assertThat(Day7.getWinnings(PUZZLE_INPUT), is(246163188L));
  }

  @Test
  void testGetJokerWinnings_testInput() {
    assertThat(Day7.getJokerWinnings(Input.TEST.load(JokerHand::from)), is(5905L));
  }

  @Test
  void testGetJokerWinnings_PuzzleInput() {
    assertThat(Day7.getJokerWinnings(Input.PUZZLE.load(JokerHand::from)), is(245794069L));
  }

}
