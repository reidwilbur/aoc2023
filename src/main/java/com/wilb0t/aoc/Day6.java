package com.wilb0t.aoc;

import java.util.HashMap;

class Day6 {
  
  public static int getStartOfPacketMarker(String packet) {
    return getMarker(packet, 4);
  }
  
  public static int getStartOfMsgMarker(String packet) {
    return getMarker(packet, 14);
  }

  public static int getMarker(String packet, int ulen) {
    var chars = new HashMap<Character, Integer>();
    for (var idx = 0; idx < packet.length(); idx++) {
      if (idx + 1 > ulen) {
        var toRemove = packet.charAt(idx - ulen);
        chars.computeIfPresent(toRemove, (k, v) -> (v == 1) ? null : v - 1);
      }
      chars.merge(packet.charAt(idx), 1, Integer::sum);
      if (chars.size() == ulen) {
        return idx + 1;
      }
    }
    throw new RuntimeException("Marker not found");
  }
}
