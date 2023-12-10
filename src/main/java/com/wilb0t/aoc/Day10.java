package com.wilb0t.aoc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class Day10 {

  public enum Pipe {
    H('-'), V('|'), WS('7'), WN('J'), EN('L'), ES('F');

    private final char cval;

    private static final Map<Character, Pipe> PIPES =
        Arrays.stream(Pipe.values())
            .collect(Collectors.toMap(p -> p.cval, Function.identity()));

    Pipe(char c) {
      cval = c;
    }

    static boolean isPipe(char c) {
      return PIPES.containsKey(c);
    }

    static Pipe of(char c) {
      return PIPES.get(c);
    }

    public List<Pos> ends(Pos p) {
      return switch(this) {
        case H -> List.of(new Pos(p.r, p.c - 1), new Pos(p.r, p.c + 1));
        case V -> List.of(new Pos(p.r + 1, p.c), new Pos(p.r - 1, p.c));
        case WS -> List.of(new Pos(p.r, p.c - 1), new Pos(p.r + 1, p.c));
        case WN -> List.of(new Pos(p.r, p.c - 1), new Pos(p.r - 1, p.c));
        case EN -> List.of(new Pos(p.r, p.c + 1), new Pos(p.r - 1, p.c));
        case ES -> List.of(new Pos(p.r, p.c + 1), new Pos(p.r + 1, p.c));
      };
    }
  }

  public record Pos(int r, int c) {
    public List<Pos> nbors(int rows, int cols) {
      return Stream.of(new Pos(r - 1, c), new Pos(r, c + 1), new Pos(r + 1, c), new Pos(r, c - 1))
          .filter(p -> p.r >= 0 && p.r < rows && p.c >= 0 && p.c < cols)
          .toList();
    }
  }

  public record Scan(List<String> map, Pos start) {
    public static Scan from (List<String> input) {
      var start = new Pos(-1, -1);
      for (var r = 0; r < input.size(); r++) {
        for (var c = 0; c < input.getFirst().length(); c++) {
          if (input.get(r).charAt(c) == 'S') {
            start = new Pos(r, c);
          }
        }
      }
      return new Scan(input, start);
    }

    public char at(Pos pos) {
      return map.get(pos.r).charAt(pos.c);
    }

    public boolean isPipe(Pos pos) {
      return Pipe.isPipe(at(pos));
    }

    public Pos next(Pos cur, Pos last) {
      if (cur.equals(start)) {
        return cur.nbors(map.size(), map.getFirst().length()).stream()
            .filter(this::isPipe).filter(Predicate.not(last::equals))
            .filter(np -> Pipe.of(at(np)).ends(np).contains(start))
            .findFirst()
            .orElseThrow();
      } else if (isPipe(cur)) {
        return Pipe.of(at(cur)).ends(cur).stream()
            .filter(Predicate.not(last::equals))
            .findFirst()
            .orElseThrow();
      } else {
        throw new RuntimeException(STR."not on pipe cur=\{cur} last=\{last}}");
      }
    }
  }

  public static int getMaxDist(Scan scan) {
    var last = scan.start;
    var cur = scan.next(scan.start, last);
    var steps = 1;
    while(!cur.equals(scan.start)) {
      var next = scan.next(cur, last);
      last = cur;
      cur = next;
      steps += 1;
    }
    return (steps / 2) + (steps % 2);
  }
}
