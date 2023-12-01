package com.wilb0t.aoc;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

class Day7 {

  public static long getDirSizeSum(String[] lines) {
    var limit = 100000;
    var dirs = Dir.getDirs(lines);
    return dirs.stream().filter(dir -> dir.size <= limit).mapToLong(dir -> dir.size).sum();
  }

  public static long getDirSizeToDelete(String[] lines) {
    var dirs = Dir.getDirs(lines);
    var root =
        dirs.stream()
            .filter(dir -> dir.parent.isEmpty())
            .findFirst()
            .orElseThrow(() -> new RuntimeException("no root found"));
    var amountToFree = 30000000L - (70000000L - root.size);
    var toDelete =
        dirs.stream()
            .sorted(Comparator.comparing(dir -> dir.size))
            .filter(dir -> amountToFree <= dir.size)
            .findFirst();

    return toDelete
        .map(dir -> dir.size)
        .orElseThrow(() -> new RuntimeException("no dir size matches"));
  }
  
  private static class Dir {
    long size;
    String name;
    Optional<Dir> parent;

    public Dir(String name) {
      this.name = name;
      parent = Optional.empty();
    }

    public Dir(String name, Dir parent) {
      this(name);
      this.parent = Optional.of(parent);
    }
    
    static List<Dir> getDirs(String[] lines) {
      var idx = 0;
      var stack = new ArrayDeque<Dir>();
      var dirs = new ArrayList<Dir>();
      while (idx < lines.length) {
        var line = lines[idx];
        if (line.startsWith("$ cd ..")) {
          var dir = stack.pop();
          dir.parent.map(p -> p.size += dir.size);
          idx += 1;
        } else if (line.startsWith("$ cd ")) {
          var dirName = line.substring(5);
          var dir = (stack.isEmpty()) ? new Dir(dirName) : new Dir(dirName, stack.peek());
          dirs.add(dir);
          stack.push(dir);
          idx += 1;
        } else if (line.startsWith("$ ls")) {
          idx += 1;
          while (idx < lines.length && !lines[idx].startsWith("$")) {
            line = lines[idx];
            if (!line.startsWith("dir")) {
              stack.peek().size += Long.parseLong(line.split(" ")[0]);
            }
            idx += 1;
          }
        }
      }
      while (!stack.isEmpty()) {
        var dir = stack.pop();
        dir.parent.map(p -> p.size += dir.size);
      }
      return dirs;
    }
  }
}
