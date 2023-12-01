package com.wilb0t.aoc;

import java.util.function.Function;
import java.util.List;

class Day2 {
  
  public static record Round(char left, char right) {
    public int scoreForPlays() {
      var theirs = left - 'A';
      var mine = right - 'X';
      var outcome = (theirs == mine) ? 0 : (((theirs + 1) % 3) == mine) ? 1 : -1;
      return (mine + 1) + ((outcome + 1) * 3);
    }
    
    public int scoreForOutcome() {
      var theirs = left - 'A';
      var outcome = right - 'X' - 1;
      var mine = ((theirs + 3) + outcome) % 3;
      return (mine + 1) + ((outcome + 1) * 3);
    }
    
    public static Function<String, Round> mapper() {
      return line -> new Round(line.charAt(0), line.charAt(2));
    }
  }
  
  public static int getScore(List<Round> rounds) {
    return rounds.stream().map(Round::scoreForPlays).mapToInt(Integer::intValue).sum();
  }
  
  public static int getScoreForOutcome(List<Round> rounds) {
    return rounds.stream().map(Round::scoreForOutcome).mapToInt(Integer::intValue).sum();
  }
}
