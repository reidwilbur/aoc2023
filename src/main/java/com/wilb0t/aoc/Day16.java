package com.wilb0t.aoc;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.stream.Stream;

class Day16 {

  public enum Element {
    SPACE, HSPLIT, VSPLIT, MIRR45, MIRR135;

    public static Element from(int c) {
      return switch ((char) c) {
        case '.' -> SPACE;
        case '-' -> HSPLIT;
        case '|' -> VSPLIT;
        case '/' -> MIRR45;
        case '\\' -> MIRR135;
        default -> throw new IllegalArgumentException(STR."No mapping for char '\{(char) c}'");
      };
    }

    public Stream<State> next(State cur) {
      var posStream = switch (this) {
        case SPACE -> Stream.of(new State(cur.dir.next(cur.pos), cur.dir));
        case HSPLIT -> {
          if (cur.dir().equals(Dir.RT) || cur.dir().equals(Dir.LT)) {
            yield Stream.of(new State(cur.dir.next(cur.pos), cur.dir));
          } else {
            yield Stream.of(new State(Dir.LT.next(cur.pos), Dir.LT), new State(Dir.RT.next(cur.pos), Dir.RT));
          }
        }
        case VSPLIT -> {
          if (cur.dir().equals(Dir.UP) || cur.dir().equals(Dir.DN)) {
            yield Stream.of(new State(cur.dir.next(cur.pos), cur.dir));
          } else {
            yield Stream.of(new State(Dir.UP.next(cur.pos), Dir.UP), new State(Dir.DN.next(cur.pos), Dir.DN));
          }
        }
        case MIRR45 -> {
          var ndir = switch (cur.dir) {
            case Dir.UP -> Dir.RT;
            case Dir.LT -> Dir.DN;
            case Dir.RT -> Dir.UP;
            case Dir.DN -> Dir.LT;
          };
          yield Stream.of(new State(ndir.next(cur.pos()), ndir));
        }
        case MIRR135 -> {
          var ndir = switch (cur.dir) {
            case Dir.UP -> Dir.LT;
            case Dir.RT -> Dir.DN;
            case Dir.LT -> Dir.UP;
            case Dir.DN -> Dir.RT;
          };
          yield Stream.of(new State(ndir.next(cur.pos()), ndir));
        }
      };
      return posStream;
    }
  }

  public enum Dir {
    UP, RT, DN, LT;

    public Pos next(Pos cur) {
      return switch (this) {
        case UP -> new Pos(cur.row - 1, cur.col);
        case RT -> new Pos(cur.row, cur.col + 1);
        case DN -> new Pos(cur.row + 1, cur.col);
        case LT -> new Pos(cur.row, cur.col - 1);
      };
    }
  }

  public record State(Pos pos, Dir dir) {
  }

  public record Pos(int row, int col) {
    boolean isValid(int maxr, int maxc) {
      return col >= 0 && col < maxc && row >= 0 && row < maxr;
    }
  }

  public record Contraption(Element[][] grid) {
    public static Contraption parse(Stream<String> input) {
      var grid = input.map(line -> line.chars().mapToObj(Element::from).toArray(Element[]::new)).toArray(Element[][]::new);
      return new Contraption(grid);
    }

    public int getLitCount(State start) {
      var queue = new ArrayDeque<State>();
      var visited = new HashSet<State>();
      var height = grid.length;
      var width = grid[0].length;

      queue.add(start);
      while (!queue.isEmpty()) {
        var cur = queue.pollFirst();
        visited.add(cur);
        var el = grid[cur.pos.row][cur.pos.col];
        el.next(cur)
            .filter(s -> s.pos.isValid(height, width) && !visited.contains(s))
            .forEach(queue::add);
      }
      return (int) visited.stream().map(State::pos).distinct().count();
    }

    public int getMaxLitCount() {
      var maxlit = 0;
      var height = grid.length;
      var width = grid[0].length;

      var starts = new ArrayList<State>();
      for (var col = 0; col < width; col++) {
        starts.add(new State(new Pos(0, col), Dir.DN));
        starts.add(new State(new Pos(height - 1, col), Dir.UP));
      }
      for (var row = 0; row < height; row++) {
        starts.add(new State(new Pos(row, 0), Dir.RT));
        starts.add(new State(new Pos(row, width - 1), Dir.LT));
      }

      for (var start : starts) {
        maxlit = Math.max(maxlit, getLitCount(start));
      }
      return maxlit;
    }
  }

}
