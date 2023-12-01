package com.wilb0t.aoc;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

class Day15 {
  
  public record Point(int x, int y) {}
  
  public record Range(int left, int right) {
    public int size() {
      return right - left + 1;
    }
  }
  
  public record Sensor(Point loc, Point beacon) {
    public static Sensor from(String line) {
      var parts = line.split("[,:]* ");
      var xloc = Integer.parseInt(parts[2].split("=")[1]);
      var yloc = Integer.parseInt(parts[3].split("=")[1]);
      var xbloc = Integer.parseInt(parts[8].split("=")[1]);
      var ybloc = Integer.parseInt(parts[9].split("=")[1]);
      return new Sensor(new Point(xloc, yloc), new Point(xbloc, ybloc));
    }
    
    public Stream<Range> getNonBeacon(int y) {
      var vdist = Math.abs(loc.y - beacon.y);
      var hdist = Math.abs(loc.x - beacon.x);

      var rowOfs = Math.abs(loc.y - y);
      if (rowOfs > vdist + hdist) {
        return Stream.of();
      }

      var colOfs = (hdist + vdist) - rowOfs;
      if (y == beacon.y && beacon.x == loc.x - colOfs) {
        return Stream.of(new Range(loc.x - colOfs + 1, loc.x + colOfs));
      } else if (y == beacon.y) {
        return Stream.of(new Range(loc.x- colOfs, loc.x + colOfs - 1));
      } else {
        return Stream.of(new Range(loc.x - colOfs, loc.x + colOfs));
      }
    }
    
    public Stream<Range> getNonBeacon(int y, int xMax) {
      var vdist = Math.abs(loc.y - beacon.y);
      var hdist = Math.abs(loc.x - beacon.x);

      var rowOfs = Math.abs(loc.y - y);
      if (rowOfs > vdist + hdist) {
        return Stream.of();
      }

      var colOfs = (hdist + vdist) - rowOfs;
      var left = Math.max(0, loc.x - colOfs);
      var right = Math.min(xMax, loc.x + colOfs);
      return Stream.of(new Range(left, right));
    }
  }
  
  public static int getNonBeaconCount(List<Sensor> sensors, int row) {
    var ranges = new ArrayDeque<>(sensors.stream()
        .flatMap(s -> s.getNonBeacon(row))
        .sorted(Comparator.comparingInt(Range::left))
        .toList());
    
    var count = 0;
    while (!ranges.isEmpty()) {
      var range = ranges.pop();
      if (ranges.isEmpty()) {
        count += range.size();
      } else {
        var rrange = ranges.pop();
        if (rrange.left <= range.right + 1) {
          ranges.push(new Range(Math.min(rrange.left, range.left), Math.max(rrange.right, range.right)));
        } else {
          count += range.size();
          ranges.push(rrange);
        }
      }
    }
    return count;
  }
  
  public static long getBeaconFrequency(List<Sensor> sensors, int max) {
    for (var row = 0; row < max; row++) {
      var srow = row;
      var rangeStack = new ArrayDeque<>(sensors.stream()
          .flatMap(s -> s.getNonBeacon(srow, max))
          .sorted(Comparator.comparingInt(Range::left))
          .toList());
      
      var ranges = new ArrayList<Range>();
      while (!rangeStack.isEmpty()) {
        var range = rangeStack.pop();
        if (rangeStack.isEmpty()) {
          ranges.add(range);
        } else {
          var rrange = rangeStack.pop();
          if (rrange.left <= range.right + 1) {
            rangeStack.push(new Range(Math.min(rrange.left, range.left), Math.max(rrange.right, range.right)));
          } else {
            ranges.add(range);
            rangeStack.push(rrange);
          }
        }
      }
      
      if (ranges.size() == 2) {
        var x = ranges.get(0).right + 1;
        var y = row;
        return (x * 4000000L) + y;
      }
    }
    throw new RuntimeException("no beacon found");
  }
}
