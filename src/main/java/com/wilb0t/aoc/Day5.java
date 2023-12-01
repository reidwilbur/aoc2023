package com.wilb0t.aoc;

import com.wilb0t.aoc.Util.Pair;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class Day5 {
  
  public static String runProc(Pair<List<Deque<Character>>, List<Instr>> puzzle) {
    for (var instr : puzzle.second()) {
      var src = puzzle.first().get(instr.src - 1);
      var dest = puzzle.first().get(instr.dest - 1);
      IntStream.range(0, instr.count).forEach(i -> dest.push(src.pop()));
    }
    return puzzle.first().stream()
        .map(stack -> stack.pop().toString())
        .collect(Collectors.joining(""));
  }
  
  public static String runProc9001(Pair<List<Deque<Character>>, List<Instr>> puzzle) {
    for (var instr : puzzle.second()) {
      var src = puzzle.first().get(instr.src - 1);
      var dest = puzzle.first().get(instr.dest - 1);
      var tmp = new ArrayDeque<Character>(instr.count);
      IntStream.range(0, instr.count).forEach(i -> tmp.push(src.pop()));
      IntStream.range(0, instr.count).forEach(i -> dest.push(tmp.pop()));
    }
    return puzzle.first().stream()
        .map(stack -> stack.pop().toString())
        .collect(Collectors.joining(""));
  }

  public static Pair<List<Deque<Character>>, List<Instr>> parse(List<String> lines) {
    var blankIdx = lines.indexOf("");
    var stackNums = lines.get(blankIdx - 1).split("\s+");
    var numStacks = Integer.parseInt(stackNums[stackNums.length - 1]);

    var stacks = 
        IntStream.range(0, numStacks)
            .mapToObj(i -> (Deque<Character>)new ArrayDeque<Character>())
            .collect(Collectors.toList());
    
    for (var idx = blankIdx - 2; idx >= 0; idx--) {
      for (var sidx = 0; sidx < numStacks; sidx++) {
        var charIdx = 1 + (sidx * 4);
        var charVal = lines.get(idx).charAt(charIdx);
        if (charVal != ' ') {
          stacks.get(sidx).push(charVal);
        }
      }
    }
    
    var instrs = 
        lines.subList(blankIdx + 1, lines.size()).stream()
            .map(Instr::from)
            .collect(Collectors.toList());
    
    return new Pair<>(stacks, instrs);
  }
  
  public record Instr(int count, int src, int dest) {
    public static Instr from(String line) {
      var parts = line.split("\s+");
      var count = Integer.parseInt(parts[1]);
      var src = Integer.parseInt(parts[3]);
      var dest = Integer.parseInt(parts[5]);
      return new Instr(count, src, dest);
    }
  }
}
