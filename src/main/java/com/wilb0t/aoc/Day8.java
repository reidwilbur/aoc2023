package com.wilb0t.aoc;

class Day8 {

  public static int getVisibleCount(int[][] grid) {
    var width = grid[0].length;
    var height = grid.length;
    var visibleCount = (height * 2) + ((width - 2) * 2);

    for (var row = 1; row < height - 1; row++) {
      for (var col = 1; col < width - 1; col++) {
        var isVisible = isVisible(row, col, grid);
        visibleCount += (isVisible) ? 1 : 0;
      }
    }

    return visibleCount;
  }

  static int getMaxScenic(int[][] grid) {
    var width = grid[0].length;
    var height = grid.length;
    var max = 0;
    for (var row = 0; row < height; row++) {
      for (var col = 0; col < width; col++) {
        var score = getScore(row, col, grid);
        max = Math.max(score, max);
      }
    }
    return max;
  }

  static int getScore(int r, int c, int[][] grid) {
    var width = grid[r].length;
    var height = grid.length;

    var tcnt = 0;
    var rcnt = 0;
    var bcnt = 0;
    var lcnt = 0;

    var h = grid[r][c];
    var max = 0;
    for (var row = r - 1; row >= 0 && max < h; row--) {
      var tval = grid[row][c];
      tcnt += 1;
      max = Math.max(tval, max);
    }
    max = 0;
    for (var row = r + 1; row < height && max < h; row++) {
      var tval = grid[row][c];
      bcnt += 1;
      max = Math.max(tval, max);
    }
    max = 0;
    for (var col = c - 1; col >= 0 && max < h; col--) {
      var tval = grid[r][col];
      lcnt += 1;
      max = Math.max(tval, max);
    }
    max = 0;
    for (var col = c + 1; col < width && max < h; col++) {
      var tval = grid[r][col];
      rcnt += 1;
      max = Math.max(tval, max);
    }
    return tcnt * rcnt * bcnt * lcnt;
  }

  static boolean isVisible(int r, int c, int[][] grid) {
    var width = grid[r].length;
    var height = grid.length;

    var h = grid[r][c];
    var tvis = true;
    var rvis = true;
    var bvis = true;
    var lvis = true;
    for (var row = 0; row < r; row++) {
      tvis &= (grid[row][c] < h);
    }
    for (var row = height - 1; row > r; row--) {
      bvis &= (grid[row][c] < h);
    }
    for (var col = 0; col < c; col++) {
      lvis &= (grid[r][col] < h);
    }
    for (var col = width - 1; col > c; col--) {
      rvis &= (grid[r][col] < h);
    }
    return tvis || rvis || bvis || lvis;
  }
}
