package com.wilb0t.aoc;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.Set;

class Day18 {

  public enum Dir {
    U, R, D, L;

    public Pos next(Pos p) {
      return switch(this) {
        case U -> new Pos(p.r - 1, p.c);
        case R -> new Pos(p.r, p.c + 1);
        case D -> new Pos(p.r + 1, p.c);
        case L -> new Pos(p.r, p.c - 1);
      };
    }
  }

  public record Pos(int r, int c) {
    public boolean isValid(int height, int width) {
      return r >= 0 && r < height && c >= 0 && c < width;
    }
  }

  public record Instr(Dir dir, int len, String color) {
    public static Instr from(String line) {
      var parts = line.split("\\s+");
      var dir = Dir.valueOf(parts[0]);
      int len = Integer.parseInt(parts[1]);
      return new Instr(dir, len, parts[2]);
    }
  }

  public static int getLagoonArea(List<Instr> instrs) {
    var trench = new HashSet<Pos>();
    var pos = new Pos(0, 0);
    trench.add(pos);
    for (var instr : instrs) {
      for (int idx = 0; idx < instr.len; idx++) {
        pos = instr.dir.next(pos);
        trench.add(pos);
      }
    }

    var rstats = trench.stream().mapToInt(Pos::r).summaryStatistics();
    var cstats = trench.stream().mapToInt(Pos::c).summaryStatistics();
    var queue = new ArrayDeque<Pos>();
    queue.add(new Pos(1, 1));
    while (!queue.isEmpty()) {
      var cur = queue.pollFirst();
      if (!trench.contains(cur)) {
        trench.add(cur);
        var next = List.of(Dir.U.next(cur), Dir.R.next(cur), Dir.D.next(cur), Dir.L.next(cur));
        queue.addAll(next);
      }
    }
    return trench.size();
  }

  public static void print(Set<Pos> inside, IntSummaryStatistics rstats, IntSummaryStatistics cstats) {
    for (int row = rstats.getMin(); row <= rstats.getMax(); row++) {
      var sb = new StringBuilder();
      for (int col = cstats.getMin(); col <= cstats.getMax(); col++) {
        sb.append(inside.contains(new Pos(row, col)) ? '#' : '.');
      }
      System.out.println(sb);
    }
    System.out.println();
  }
}
