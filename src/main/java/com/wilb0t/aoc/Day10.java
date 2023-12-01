package com.wilb0t.aoc;

import java.util.ArrayDeque;
import java.util.List;
import java.util.stream.IntStream;

class Day10 {
  public record State(int time, int x) {}
  
  public sealed interface Instr {
    State exec(State cur);
    
    record Noop() implements Instr {
      public State exec(State cur) {
        return new State(cur.time() + 1, cur.x());
      }
    }
    record Addx(int add) implements Instr {
      public State exec(State cur) {
        return new State(cur.time() + 2, cur.x() + add);
      }
    }
    
    static Instr from(String line) {
      if (line.startsWith("noop")) {
        return new Noop();
      } else {
        return new Addx(Integer.parseInt(line.substring(5)));
      }
    }
  }
  
  public static int getSignalSum(List<Instr> instrs) {
    var samples = new ArrayDeque<>(List.of(20, 60, 100, 140, 180, 220));
    var strength = 0;
    var state = new State(1, 1);
    for (var instr : instrs) {
      var nextState = instr.exec(state);
      while (!samples.isEmpty() && nextState.time() > samples.peek()) {
        strength += state.x() * samples.pop();
      }
      state = nextState;
    }
    return strength;
  }
  
  public static List<String> display(List<Instr> instrs){
    var height = 6;
    var width = 40;
    var disp = 
        IntStream.range(0, height)
            .mapToObj(i -> new StringBuilder(" ".repeat(width)))
            .toList();

    var samples = new ArrayDeque<>(IntStream.range(1, width * height + 1).boxed().toList());
    var state = new State(1,1);
    for (var instr : instrs) {
      var nextState = instr.exec(state);
      while (!samples.isEmpty() && nextState.time() > samples.peek()) {
        int sample = samples.pop();
        int col = (sample - 1) % width;
        if (col >= state.x() - 1 && col <= state.x() + 1) {
          var row = (sample - 1) / width;
          disp.get(row).setCharAt(col, '█');
        }
      }
      state = nextState;
    }

    for (var line : disp) {
      System.out.println(line);
    }
    System.out.println();
    return disp.stream().map(Object::toString).toList();
  }
}
