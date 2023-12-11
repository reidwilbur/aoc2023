package com.wilb0t.aoc;

public class Util {

  static long gcd(long left, long right) {
    if (right == 0) return left;
    return gcd(right, left % right);
  }

  static long lcm(long left, long right) {
    var gcd = gcd(left, right);
    return (left * right) / gcd;
  }

  public interface IntGridVisitor {
    void apply(int r, int c, int val);
  }

  public static void forEach(int[][] grid, IntGridVisitor visitor) {
    for (int r = 0; r < grid.length; r++) {
      for (int c = 0; c < grid[0].length; c++) {
        visitor.apply(r, c, grid[r][c]);
      }
    }
  }
}
