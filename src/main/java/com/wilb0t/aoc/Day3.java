package com.wilb0t.aoc;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

class Day3 {

  public record Schematic(int[][] layout, List<Integer> parts) {

    public int rows() {
      return layout.length;
    }

    public int cols() {
      return layout[0].length;
    }

    public boolean isSymbol(int r, int c) {
      var cellVal = layout[r][c];
      return cellVal < 0 && cellVal != -'.';
    }

    public boolean isPartIndex(int r, int c) {
      return validPos(r, c) && layout[r][c] >= 0;
    }

    public boolean validPos(int r, int c) {
      return (r >= 0) && (r < rows()) && (c >= 0) && (c < cols());
    }

    public boolean isGearSymbol(int r, int c) {
      return layout[r][c] == -'*';
    }

    public static Schematic from(String[] input) {
      var parts = new ArrayList<Integer>();
      var partNum = 0;
      var partIdx = -1;
      var inpart = false;
      int[][] layout = new int[input.length][input[0].length()];

      for (var r = 0; r < layout.length; r++) {
        for (var c = 0; c < layout[0].length; c++) {
          var posChr = input[r].charAt(c);
          var posVal = posChr - '0';
          if (posVal >= 0 && posVal <= 9) {
            if (!inpart) {
              inpart = true;
              partNum = posVal;
              partIdx += 1;
            } else {
              partNum = (partNum * 10) + posVal;
            }
          } else {
            if (inpart) {
              inpart = false;
              parts.add(partNum);
            }
          }
          layout[r][c] = (inpart) ? partIdx : -posChr;
        }
        if (inpart) {
          inpart = false;
          parts.add(partNum);
        }
      }

      return new Schematic(layout, parts);
    }
  }

  public static int getPartNums(String[] input) {
    var schematic = Schematic.from(input);
    var partIdxs = new HashSet<Integer>();
    for (var r = 0; r < schematic.rows(); r++) {
      for (var c = 0; c < schematic.cols(); c++) {
        if (schematic.isSymbol(r, c)) {
          for (var nr = r - 1; nr <= r + 1; nr++) {
            for (var nc = c - 1; nc <= c + 1; nc++) {
              var nborVal = schematic.layout[nr][nc];
              if (schematic.isPartIndex(nr, nc)) {
                partIdxs.add(nborVal);
              }
            }
          }
        }
      }
    }
    var validParts = partIdxs.stream().map(schematic.parts::get).toList();
    return validParts.stream().mapToInt(i -> i).sum();
  }

  public static long getGearRatios(String[] input) {
    var schematic = Schematic.from(input);
    var ratio = 0L;
    for (var r = 0; r < schematic.rows(); r++) {
      for (var c = 0; c < schematic.cols(); c++) {
        if (schematic.isGearSymbol(r, c)) {
          var partIdxs = new HashSet<Integer>();
          for (var nr = r - 1; nr <= r + 1; nr++) {
            for (var nc = c - 1; nc <= c + 1; nc++) {
              var nborVal = schematic.layout[nr][nc];
              if (schematic.isPartIndex(nr, nc)) {
                partIdxs.add(nborVal);
              }
            }
          }
          if (partIdxs.size() == 2) {
            var parts = partIdxs.stream().map(schematic.parts::get).toList();
            ratio += (long) parts.get(0) * parts.get(1);
          }
        }
      }
    }
    return ratio;
  }
}
