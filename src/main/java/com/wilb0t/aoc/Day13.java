package com.wilb0t.aoc;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    Set<Integer> getVRefs() {
      var reflidx = new HashSet<Integer>();
      for (var row = 0; row < lines.size() - 1; row++) {
        if (lines.get(row).equals(lines.get(row + 1))) {
          var isrefl = true;
          for (var ridx = 1; row - ridx >= 0 && (row + 1 + ridx) < lines.size(); ridx++) {
            if (!lines.get(row - ridx).equals(lines.get(row + 1 + ridx))) {
              isrefl = false;
            }
          }
          if (isrefl) {
            reflidx.add(row);
          }
        }
      }
      return reflidx;
    }

    Set<Integer> getHRefs() {
      var reflidx = new HashSet<Integer>();
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
            reflidx.add(col);
          }
        }
      }
      return reflidx;
    }

    public int getSummary() {
      var left = getHRefs().stream().findFirst().map(idx -> idx + 1).orElse(0);
      var above = getVRefs().stream().findFirst().map(idx -> idx + 1).orElse(0);
      return (100 * above) + left;
    }

    public Pattern smudge(int tr, int tc) {
      var smudged = new ArrayList<String>();
      for (var row = 0; row < lines.size(); row++) {
        if (row == tr) {
          var sb = new StringBuilder(lines.get(row));
          if (sb.charAt(tc) == '.') {
            sb.setCharAt(tc, '#');
          } else {
            sb.setCharAt(tc, '.');
          }
          smudged.add(sb.toString());
        } else {
          smudged.add(lines.get(row));
        }
      }
      return new Pattern(smudged);
    }

    public int getSummarySmudge() {
      var left = getHRefs();
      var above = getVRefs();

      for (var row = 0; row < lines.size(); row++) {
        for (var col = 0; col < lines.getFirst().length(); col++) {
          var smudged = smudge(row, col);
          var sleft = smudged.getHRefs();
          var sabove = smudged.getVRefs();

          if (!sleft.isEmpty() && !sleft.equals(left)) {
            sleft.removeAll(left);
            return sleft.stream().findFirst().orElseThrow() + 1;
          } else if (!sabove.isEmpty() && !sabove.equals(above)) {
            sabove.removeAll(above);
            return 100 * (sabove.stream().findFirst().orElseThrow() + 1);
          }
        }
      }
      return 0;
    }
  }

  public static int getReflectionSummary(List<Pattern> patterns) {
    return patterns.stream().mapToInt(Pattern::getSummary).sum();
  }

  public static int getReflectionSummarySmudge(List<Pattern> patterns) {
    return patterns.stream().mapToInt(Pattern::getSummarySmudge).sum();
  }
}
