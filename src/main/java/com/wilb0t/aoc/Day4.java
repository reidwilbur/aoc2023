package com.wilb0t.aoc;

import com.wilb0t.aoc.Util.Pair;
import java.util.List;

class Day4 {
  public static Pair<Section, Section> from(String line) {
    var idx = line.indexOf(',');
    return new Pair<>(Section.from(line.substring(0, idx)), Section.from(line.substring(idx + 1)));
  }

  public record Section(int start, int end) {
    public static Section from(String line) {
      var idx = line.indexOf('-');
      return new Section(Integer.parseInt(line.substring(0, idx)), Integer.parseInt(line.substring(idx + 1)));
    }
    
    public boolean isFullyContained(Section other) {
      return start >= other.start && start <= other.end
          && end >= other.start && end <= other.end;
    }
    
    public boolean overlaps(Section other) {
      return (start <= other.end) && (end >= other.start);
    }
  }

  public static int getContainCount(List<Pair<Section, Section>> sections) {
    return (int) sections.stream()
        .filter(pair -> pair.first().isFullyContained(pair.second()) || pair.second().isFullyContained(pair.first()))
        .count();
  }
  
  public static int getOverlapCount(List<Pair<Section, Section>> sections) {
    return (int) sections.stream()
        .filter(pair -> pair.first().overlaps(pair.second()))
        .count();
  }
}
