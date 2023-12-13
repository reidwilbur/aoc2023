package com.wilb0t.aoc;

import java.util.ArrayList;
import java.util.List;

class Day13 {

  public record Pattern(List<String> lines) {
    public static List<Pattern> from(List<String> input) {
      var lines = new ArrayList<String>();
      var patterns = new ArrayList<Pattern>();
      for (var inputline : input) {
        if (inputline.isEmpty()) {
          patterns.add(new Pattern(List.copyOf(lines)));
          lines.clear();
        } else {
          lines.add(inputline);
        }
      }
      if (!lines.isEmpty()) {
        patterns.add(new Pattern(List.copyOf(lines)));
      }
      return patterns;
    }

    long getCol(int idx) {
      long lval = 0;
      for (var row = 0; row < lines.size(); row++) {
        if (lines.get(row).charAt(idx) == '#') {
          lval |= (1L << row);
        }
      }
      return lval;
    }

    long getRow(int idx) {
      long lval = 0;
      for (var col = 0; col < lines.get(idx).length(); col++) {
        if (lines.get(idx).charAt(col) == '#') {
          lval |= (1L << col);
        }
      }
      return lval;
    }

    int getVRef() {
      for (var row = 0; row < lines.size() - 1; row++) {
        if (lines.get(row).equals(lines.get(row + 1))) {
          var isrefl = true;
          for (var ridx = 1; row - ridx >= 0 && (row + 1 + ridx) < lines.size(); ridx++) {
            if (!lines.get(row - ridx).equals(lines.get(row + 1 + ridx))) {
              isrefl = false;
            }
          }
          if (isrefl) {
            return row + 1;
          }
        }
      }
      return 0;
    }

    int getHRef() {
      var width = lines.getFirst().length();
      for (var col = 0; col < width - 1; col++) {
        if (getCol(col) == getCol(col + 1)) {
          var isrefl = true;
          for (var cidx = 1; col - cidx >= 0 && (col + 1 + cidx) < width; cidx++) {
            if (getCol(col - cidx) != getCol(col + 1 + cidx)) {
              isrefl = false;
            }
          }
          if (isrefl) {
            return col + 1;
          }
        }
      }
      return 0;
    }

    public int getSummary() {
      var left = getHRef();
      var above = getVRef();
      return (100 * above) + left;
    }

    public int getSummarySmudge() {
      return 0;
    }
  }

  public static int getReflectionSummary(List<Pattern> patterns) {
    return patterns.stream().mapToInt(Pattern::getSummary).sum();
  }

  public static int getReflectionSummarySmudge(List<Pattern> patterns) {
    return 0;
  }
}
