package com.wilb0t.aoc;

import static com.wilb0t.aoc.Day17.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.Test;

public class Day17Test {

  private static HeatLossMap TEST_INPUT = new HeatLossMap(Input.TEST.loadIntGrid());

  @Test
  void testGetMinLoss_testInput() {
    assertThat(TEST_INPUT.getMinLoss(), is(102));
  }
}
