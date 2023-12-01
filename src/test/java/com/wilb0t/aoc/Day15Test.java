package com.wilb0t.aoc;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;

public class Day15Test {
  private static final List<Day15.Sensor> PUZZLE_INPUT = Input.PUZZLE.load(Day15.Sensor::from);

  private static final List<Day15.Sensor> TEST_INPUT = Input.TEST.load(Day15.Sensor::from);
  
  @Test
  public void testGetNonBeaconCount_test() {
    assertThat(TEST_INPUT.size(), is(14));

    assertThat(TEST_INPUT.get(6).getNonBeacon(10).findFirst().get().size(), is(12));
    assertThat(TEST_INPUT.get(6).getNonBeacon(10, Integer.MAX_VALUE).findFirst().get().size(), is(13));
    
    assertThat(Day15.getNonBeaconCount(TEST_INPUT, 10), is(26));
  }

  @Test
  public void testGetNonBeaconCount_puzzle() {
    assertThat(Day15.getNonBeaconCount(PUZZLE_INPUT, 2000000), is(4665948));
  }
  
  @Test
  public void testGetBeaconFreq_test() {
    assertThat(Day15.getBeaconFrequency(TEST_INPUT, 21), is(56000011L));
  }

  @Test
  public void testGetBeaconFreq_puzzle() {
    assertThat(Day15.getBeaconFrequency(PUZZLE_INPUT, 4000001), is(13543690671045L));
  }
}
