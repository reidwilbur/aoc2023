package com.wilb0t.aoc;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

class Day8 {

  public record Node(String name, String left, String right) {
    public static Node from(String line) {
      var parts = line.split("\\s+=\\s+");
      var name = parts[0];

      var nextParts = parts[1].split(",\\s+");
      return new Node(
          name, nextParts[0].substring(1), nextParts[1].substring(0, nextParts[1].length() - 1));
    }

    public String nextNode(char dir) {
      return (dir == 'L') ? left : right;
    }
  }

  public record DesertMap(String instrs, Map<String, Node> nodes) {
    public static DesertMap from(List<String> lines) {
      var instrs = lines.getFirst();

      var nodes =
          lines.subList(2, lines.size()).stream()
              .map(Node::from)
              .collect(Collectors.toMap(Node::name, Function.identity()));

      return new DesertMap(instrs, nodes);
    }

    public char dirForStep(int step) {
      return instrs.charAt(step % instrs.length());
    }
  }

  public static int getSteps(DesertMap map) {
    var curNode = "AAA";
    var end = "ZZZ";

    var steps = 0;
    while (!curNode.equals(end)) {
      var node = map.nodes.get(curNode);
      curNode = node.nextNode(map.dirForStep(steps));
      steps += 1;
    }
    return steps;
  }
}
