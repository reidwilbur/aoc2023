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
}
