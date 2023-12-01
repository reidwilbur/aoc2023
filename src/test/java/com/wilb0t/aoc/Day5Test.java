package com.wilb0t.aoc;

import static com.wilb0t.aoc.Day5.Instr;
import static com.wilb0t.aoc.Util.Pair;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Deque;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class Day5Test {

  private static Pair<List<Deque<Character>>, List<Instr>> PUZZLE_INPUT;
  
  private static Pair<List<Deque<Character>>, List<Instr>> TEST_INPUT;
  
  @BeforeEach
  public void beforeEach() {
    PUZZLE_INPUT = Input.PUZZLE.parse(Day5::parse);
    TEST_INPUT = Input.TEST.parse(Day5::parse);
  }

  @Test
  public void testParse() {
    assertThat(TEST_INPUT.second().size(), is(4));
    assertThat(TEST_INPUT.first().size(), is(3));
    assertThat(TEST_INPUT.first().get(1), hasItems('M', 'C', 'D'));
    
    assertThat(PUZZLE_INPUT.first().size(), is(9));
    assertThat(PUZZLE_INPUT.second().size(), is(502));
  }
  
  @Test
  public void testRunProc_testInput() {
    assertThat(Day5.runProc(TEST_INPUT), is("CMZ"));
  }

  @Test
  public void testRunProc_puzzleInput() {
    assertThat(Day5.runProc(PUZZLE_INPUT), is("RLFNRTNFB"));
  }

  @Test
  public void testRunProc9001_testInput() {
    assertThat(Day5.runProc9001(TEST_INPUT), is("MCD"));
  }

  @Test
  public void testRunProc9001_puzzleInput() {
    assertThat(Day5.runProc9001(PUZZLE_INPUT), is("MHQTLJRLB"));
  }
}
