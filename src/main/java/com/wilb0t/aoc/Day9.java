package com.wilb0t.aoc;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

class Day9 {
  
  public enum Direction {
    U, R, D, L;
    
    static Stream<Direction> from(String line) {
      var dist = Integer.parseInt(line.substring(2));
      return IntStream.range(0, dist)
          .mapToObj(i ->
              switch(line.charAt(0)) {
                case 'U' -> U;
                case 'R' -> R;
                case 'D' -> D;
                case 'L' -> L;
                default -> throw new RuntimeException("Wtf mate " + line);
              });
    }
  }

  public sealed interface Knot {
    int x();
    int y();
    
    record Head(int x, int y) implements Knot {
      Head move(Direction dir) {
        return switch(dir) {
          case U -> new Head(x, y + 1);
          case R -> new Head(x + 1, y);
          case D -> new Head(x, y - 1);
          case L -> new Head(x - 1, y);
        };
      }
    }

    record Tail(int x, int y) implements Knot {
      Tail move(Knot prev) {
        if (Math.abs(prev.x() - x) <= 1 && Math.abs(prev.y() - y) <= 1) {
          return this;
        }
        var xnext = (prev.x() == x) ? x : (prev.x() > x) ? x + 1 : x - 1;
        var ynext = (prev.y() == y) ? y : (prev.y() > y) ? y + 1 : y - 1;
        return new Tail(xnext, ynext);
      }
    }
  }
  
  record Rope(Knot.Head head, List<Knot.Tail> tails) {
    Rope move(Direction dir) {
      var nextHead = head.move(dir);
      Knot prev = nextHead;
      List<Knot.Tail> nextTails = new ArrayList<>(tails.size());
      for (var knot : tails) {
        var nextTail = knot.move(prev);
        nextTails.add(nextTail);
        prev = nextTail;
      }
      return new Rope(nextHead, nextTails);
    }
  }
 
  public static int getTailPosCount(List<Direction> dirs, int knots) {
    var tails = IntStream.range(0, knots).mapToObj(i -> new Knot.Tail(0, 0)).toList();
    var ropes = new ArrayDeque<>(List.of(new Rope(new Knot.Head(0,0), tails)));
    dirs.forEach(dir -> {
      var nextRope = ropes.peek().move(dir);
      ropes.push(nextRope);
    });
    return ropes.stream().map(rope-> rope.tails().get(rope.tails.size() - 1)).collect(Collectors.toSet()).size();
  }
}
