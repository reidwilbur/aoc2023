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

  public enum Dir {
    N, E, S, W;

    static Dir from(Pos cur, Pos last, char pipechar) {
      if (cur.equals(last)) {
        throw new RuntimeException(STR."no direction change for \{cur} \{last}");
      }
      Dir dir = Dir.W;
      if (cur.r > last.r) {
        dir = S;
      }
      if (cur.r < last.r) {
        dir = N;
      }
      if (cur.c > last.c) {
        dir = E;
      }
      if (pipechar == 'J' && dir.equals(Dir.E)) {
        return Dir.N;
      }
      if (pipechar == 'L' && dir.equals(Dir.W)) {
        return Dir.N;
      }
      if (pipechar == 'F' && dir.equals(Dir.W)) {
        return Dir.S;
      }
      if (pipechar == '7' && dir.equals((Dir.E))) {
        return Dir.S;
      }
      return dir;
    }
  }

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

  public static int getIntCount(Scan scan) {
    var dirmap = initDirMap(scan);
    markDirMap(scan, dirmap);

    var intcount = 0;
    for (var r = 0; r < dirmap.length; r++) {
      var inside = false;
      var lastdir = '_';
      for (var c = 0; c < dirmap[0].length; c++) {
        var mapval = dirmap[r][c];
        if (inside) {
          if (mapval != 'n' && mapval != 's') {
            intcount += 1;
          } else {
            if (mapval != lastdir) {
              inside = false;
              lastdir = mapval;
            }
          }
        } else {
          if ((mapval == 'n' || mapval == 's') && mapval != lastdir) {
            inside = true;
            lastdir = mapval;
          }
        }
      }
    }
    return intcount;
  }

  static void printDirMap(char[][] dirmap) {
    for (var r = 0; r < dirmap.length; r++) {
      var sb = new StringBuilder(dirmap[0].length);
      for (var c = 0; c < dirmap[0].length; c++) {
        sb.append(dirmap[r][c]);
      }
      System.out.println(sb);
    }
    System.out.println();
  }

  static void markDirMap(Scan scan, char[][] dirmap) {
    var last = scan.start;
    var cur = scan.next(scan.start, last);
    var dir = Dir.E;
    while (!(dirmap[cur.r][cur.c] == 'n' || dirmap[cur.r][cur.c] == 's')) {
      var tmpdir = Dir.from(cur, last, scan.at(cur));
      dir = (tmpdir.equals(Dir.N) || tmpdir.equals(Dir.S)) ? tmpdir : dir;
      dirmap[cur.r][cur.c] = dir.toString().toLowerCase().charAt(0);
      var tmp = last;
      last = cur;
      cur = scan.next(last, tmp);
    }
  }

  static char[][] initDirMap(Scan scan) {
    var dirmap = new char[scan.map.size()][scan.map.getFirst().length()];
    for (var r = 0; r < dirmap.length; r++) {
      for (var c = 0; c < dirmap[0].length; c++) {
        dirmap[r][c] = scan.map.get(r).charAt(c);
      }
    }
    return dirmap;
  }
}
