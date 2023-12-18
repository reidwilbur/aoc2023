package com.wilb0t.aoc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

class Day17 {

  public enum Dir {
    UP, RT, DN, LT;

    public Pos next(Pos p) {
      return switch(this) {
        case UP -> new Pos(p.r - 1, p.c);
        case RT -> new Pos(p.r, p.c + 1);
        case DN -> new Pos(p.r + 1, p.c);
        case LT -> new Pos(p.r, p.c - 1);
      };
    }
  }

  public record Pos(int r, int c) {
    public boolean isValid(int height, int width) {
      return r >= 0 && r < height && c >= 0 && c < width;
    }
  }

  public record Node(Pos p, Dir d) {
  }

  public record HeatLossMap(int[][] grid) {
    public List<Node> getNodes() {
      var height = grid.length;
      var width = grid[0].length;
      var nodes = new ArrayList<Node>();
      for (var r = 0; r < grid.length; r++) {
        for (var c = 0; c < grid[0].length; c++) {
          var pos = new Pos(r, c);
          if (Dir.UP.next(pos).isValid(height, width)) {
            nodes.add(new Node(pos, Dir.DN));
          }
          if (Dir.DN.next(pos).isValid(height, width)) {
            nodes.add(new Node(pos, Dir.UP));
          }
          if (Dir.LT.next(pos).isValid(height, width)) {
            nodes.add(new Node(pos, Dir.RT));
          }
          if (Dir.RT.next(pos).isValid(height, width)) {
            nodes.add(new Node(pos, Dir.LT));
          }
        }
      }
      return nodes;
    }

    public Map<Node, Map<Node, Integer>> getEdges(List<Node> nodes) {
      var edges = new HashMap<Node, Map<Node, Integer>>(nodes.size());
      return Map.of();
    }

    public int getMinLoss() {
      return 0;
    }
  }

}
