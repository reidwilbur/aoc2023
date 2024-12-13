package com.wilb0t.aoc;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Set;

class Day18 {

  public enum Dir {
    U, R, D, L;

    public Pos next(Pos p, int len) {
      return switch(this) {
        case U -> new Pos(p.y - len, p.x);
        case R -> new Pos(p.y, p.x + len);
        case D -> new Pos(p.y + len, p.x);
        case L -> new Pos(p.y, p.x - len);
      };
    }
  }

  public record Pos(int y, int x) { }

  public record Instr(Dir dir, int len) {
    public static Instr from(String line) {
      var parts = line.split("\\s+");
      var dir = Dir.valueOf(parts[0]);
      int len = Integer.parseInt(parts[1]);
      return new Instr(dir, len);
    }

    public static Instr fromHex(String line) {
      var parts = line.split("\\s+");
      int len = Integer.parseInt(parts[2].substring(2, 7), 16);
      var dir = switch (parts[2].charAt(7)) {
        case '0' -> Dir.R;
        case '1' -> Dir.D;
        case '2' -> Dir.L;
        case '3' -> Dir.U;
        default -> throw new RuntimeException(STR."invalid char \{parts[2].charAt(7)}");
      };
      return new Instr(dir, len);
    }
  }

  public static int getLagoonArea(List<Instr> instrs) {
    var trench = new HashSet<Pos>();
    var pos = new Pos(0, 0);
    trench.add(pos);
    for (var instr : instrs) {
      for (int idx = 0; idx < instr.len; idx++) {
        pos = instr.dir.next(pos, 1);
        trench.add(pos);
      }
    }

    var rstats = trench.stream().mapToInt(Pos::y).summaryStatistics();
    var cstats = trench.stream().mapToInt(Pos::x).summaryStatistics();
    var queue = new ArrayDeque<Pos>();
    queue.add(new Pos(1, 1));
    while (!queue.isEmpty()) {
      var cur = queue.pollFirst();
      if (!trench.contains(cur)) {
        trench.add(cur);
        var next = List.of(Dir.U.next(cur, 1), Dir.R.next(cur, 1), Dir.D.next(cur, 1), Dir.L.next(cur, 1));
        queue.addAll(next);
      }
    }
    return trench.size();
  }

  public static long getLagoonAreaHex(List<Instr> instrs) {
    var trench = new ArrayList<Pos>();
    var pos = new Pos(0, 0);
    for (var instr : instrs) {
      pos = instr.dir.next(pos, instr.len);
      trench.add(pos);
    }

    var dirs = List.of(instrs.getFirst().dir, instrs.get(1).dir);
    if (dirs.equals(List.of(Dir.L, Dir.U)) || dirs.equals(List.of(Dir.U, Dir.R)) || dirs.equals(List.of(Dir.R, Dir.D)) || dirs.equals(List.of(Dir.D, Dir.L))) {
      Collections.reverse(trench);
    }

    long sum = 0;
    long subsum = 0;
    for (int idx = 0; idx < trench.size(); idx++) {
      int otherIdx = (idx + 1) % trench.size();
      sum += (long) trench.get(idx).y * trench.get(otherIdx).x;
      subsum += (long) trench.get(idx).x * trench.get(otherIdx).y;
    }
    return Math.abs(sum - subsum) / 2L;
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
