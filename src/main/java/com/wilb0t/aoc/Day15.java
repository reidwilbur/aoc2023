package com.wilb0t.aoc;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

class Day15 {

  public static class Lens {
    final String label;
    int flen;

    public Lens(String label, int flen) {
      this.label = label;
      this.flen = flen;
    }

    public void setFlen(int flen) {
      this.flen = flen;
    }
  }

  public static class Box {
    final Set<String> labels;
    final List<Lens> lenses;

    public Box() {
      labels = new HashSet<>();
      lenses = new LinkedList<>();
    }

    public void update(Instr instr) {
      if (labels.contains(instr.label)) {
        lenses.stream().filter(l -> l.label.equals(instr.label)).findFirst().orElseThrow().setFlen(instr.param);
      } else {
        labels.add(instr.label);
        lenses.add(new Lens(instr.label, instr.param));
      }
    }

    public void remove(Instr instr) {
      if (labels.contains(instr.label)) {
        labels.remove(instr.label);
        lenses.removeIf(l -> l.label.equals(instr.label));
      }
    }

    public int focusPower(int boxnum) {
      var idx = 1;
      var power = 0;
      for (var lens : lenses) {
        power += boxnum * idx * lens.flen;
        idx += 1;
      }
      return power;
    }
  }

  public record Instr(String label, char op, int param, int hash) {
    public static Instr from(String input) {
      var eqidx = input.indexOf('=');
      if (eqidx != -1) {
        var label = input.substring(0, eqidx);
        return new Instr(label, '=', Integer.parseInt(input.substring(eqidx + 1)), Day15.hash(label));
      } else {
        var label = input.substring(0, input.length() - 1);
        return new Instr(label, '-', 0, Day15.hash(label));
      }
    }
  }

  public static List<String> parse(List<String> input) {
    var line = String.join("", input);
    var parts = line.split(",");
    return Arrays.asList(parts);
  }

  public static int hash(String line) {
    return line.chars().reduce(0, (acc, c) -> ((acc + c) * 17) % 256);
  }

  public static int getHashSum(List<String> input) {
    return input.stream().mapToInt(Day15::hash).sum();
  }

  public static int getFocusPower(List<String> input) {
    var instrs = input.stream().map(Instr::from).toList();

    var boxes = IntStream.range(0, 256).mapToObj(i -> new Box()).toList();

    for (var instr : instrs) {
      if (instr.op == '-') {
        boxes.get(instr.hash).remove(instr);
      } else {
        boxes.get(instr.hash).update(instr);
      }
    }

    int focusPower = 0;
    for (var idx = 0; idx < boxes.size(); idx++) {
      focusPower += boxes.get(idx).focusPower(idx + 1);
    }

    return focusPower;
  }
}
