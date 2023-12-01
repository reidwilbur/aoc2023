package com.wilb0t.aoc;

public class Util {
  public record Pair<A,B>(A first, B second) {
    public Pair<B,A> swap() {
      return new Pair<>(second, first);
    }
  }
}
