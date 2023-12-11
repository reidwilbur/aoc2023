package com.wilb0t.aoc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

class Day11 {

  public record Pos(int r, int c) {
  }

  public record Image(int[][] vals, List<Pos> galaxies) {
    public static Image from(List<String> input) {
      var vals = input.stream().map(s -> s.chars().toArray()).toArray(int[][]::new);
      var galaxies = new ArrayList<Pos>();
      Util.forEach(vals, (r, c, val) -> {
        if (val == '#') {
          galaxies.add(new Pos(r, c));
        }
      });
      return new Image(vals, galaxies);
    }
  }

  public static long pathSum(Image image, long expAmt) {
    var expRows = IntStream.range(0, image.vals.length)
        .filter(r -> Arrays.stream(image.vals[r]).allMatch(val -> val == '.'))
        .boxed().toList();
    var expCols = IntStream.range(0, image.vals[0].length)
        .filter(c -> IntStream.range(0, image.vals.length).allMatch(r -> image.vals[r][c] == '.'))
        .boxed().toList();

    return IntStream.range(0, image.galaxies.size()).mapToLong(
        idx -> {
          var galPos = image.galaxies.get(idx);
          return IntStream.range(idx + 1, image.galaxies.size()).mapToLong(
              oidx -> {
                var ogalPos = image.galaxies.get(oidx);

                long hdist = Math.abs(galPos.c - ogalPos.c);
                long hexp = expCols.stream()
                    .filter(c -> c >= Math.min(galPos.c, ogalPos.c) && c <= Math.max(galPos.c, ogalPos.c))
                    .count();
                hdist += hexp * (expAmt - 1);

                long vdist = Math.abs(galPos.r - ogalPos.r);
                long vexp = expRows.stream()
                    .filter(r -> r >= Math.min(galPos.r, ogalPos.r) && r <= Math.max(galPos.r, ogalPos.r))
                    .count();
                vdist += vexp * (expAmt - 1);

                return vdist + hdist;
              }).sum();
        }).sum();
  }
}
