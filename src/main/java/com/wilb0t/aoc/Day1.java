package com.wilb0t.aoc;

import java.util.PriorityQueue;

class Day1 {

  public static int getMostCals(String[] cals, int topk) {
    var minHeap = new PriorityQueue<Integer>();
    var count = 0;
    for (var idx = 0; idx < cals.length; idx++) {
      var cal = cals[idx];
      if (!cal.isEmpty()) {
        count += Integer.parseInt(cal);
      }
      if (cal.isEmpty() || idx == cals.length - 1) {
        minHeap.add(count);
        if (minHeap.size() > topk) {
          minHeap.remove();
        }
        count = 0;
      }
    }
    
    return minHeap.stream().mapToInt(i -> i).sum();
  }
}
