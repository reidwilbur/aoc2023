package com.wilb0t.aoc;

import static com.wilb0t.aoc.Day12.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;

public class Day12Test {

  private static final List<SpringRecord> TEST_INPUT = Input.TEST.load(SpringRecord::from);
  private static final List<SpringRecord> PUZZLE_INPUT = Input.PUZZLE.load(SpringRecord::from);

//  @Test
//  void testCountValidPatterns() {
//    var record = SpringRecord.from("#.#.### 1,1,3");
//    assertThat(record.countValidPatterns(), is(1));
//
//    record = SpringRecord.from(".??..??...?##. 1,1,3");
//    assertThat(record.countValidPatterns(), is(4));
//
//    record = SpringRecord.from("?###???????? 3,2,1");
//    assertThat(record.countValidPatterns(), is(10));
//  }

  @Test
  void testGetValidPatterns() {
    var record = SpringRecord.from("#.#.### 1,1,3");
    assertThat(Day12.getValidPatterns(record.status(), record.grpSizes()), is(1));

    record = SpringRecord.from(".??..??...?##. 1,1,3");
    assertThat(Day12.getValidPatterns(record.status(), record.grpSizes()), is(4));

    record = SpringRecord.from("?###???????? 3,2,1");
    assertThat(Day12.getValidPatterns(record.status(), record.grpSizes()), is(10));
  }

  @Test
  void testCountValidPatterns_testInput() {
    assertThat(Day12.getValidPatterns(TEST_INPUT), is(21));
  }

  @Test
  void testCountValidPatterns_puzzleInput() {
    assertThat(Day12.getValidPatterns(PUZZLE_INPUT), is(6488));
  }

  @Test
  void testCountValidPatternsExpanded_testInput() {
    assertThat(Day12.getValidPatternsExpanded(TEST_INPUT), is(525152));
  }

  //@Test
  void testCountValidPatternsExpanded_puzzleInput() {
    assertThat(Day12.getValidPatternsExpanded(PUZZLE_INPUT), is(525152));
  }

}
