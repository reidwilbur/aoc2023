package com.wilb0t.aoc;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.not;

import java.util.List;
import org.junit.jupiter.api.Test;

public class Day10Test {
  private static final List<Day10.Instr> PUZZLE_INPUT = Input.PUZZLE.load(Day10.Instr::from);

  private static final List<Day10.Instr> TEST_INPUT = Input.TEST.load(Day10.Instr::from);
  
  @Test
  public void testGetSignalSum_test() {
    assertThat(Day10.getSignalSum(TEST_INPUT), is(13140));
  }

  @Test
  public void testGetSignalSum_puzzle() {
    assertThat(Day10.getSignalSum(PUZZLE_INPUT), is(14920));
  }
  
  @Test
  public void testGetDisplay_test() {
    assertThat(Day10.display(TEST_INPUT), is(not(empty())));
  }
  
  @Test
  public void testGetDisplay_puzzle() {
    assertThat(Day10.display(PUZZLE_INPUT), is(not(empty())));
  }
}
