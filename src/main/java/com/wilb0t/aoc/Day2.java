package com.wilb0t.aoc;

import static com.wilb0t.aoc.Util.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Day2 {

  public record Reveal(List<Pair<String, Integer>> cubes) {
    public static Reveal from(String line) {
      var parts = line.split(", ");
      var cubes =
          Arrays.stream(parts)
              .map(
                  clrstr -> {
                    var clrparts = clrstr.split(" ");
                    return new Pair<>(clrparts[1], Integer.valueOf(clrparts[0]));
                  })
              .toList();
      return new Reveal(cubes);
    }

    public boolean isValid(Map<String, Integer> limits) {
      return cubes.stream().allMatch(cube -> limits.getOrDefault(cube.first(), 0) >= cube.second());
    }
  }

  public record Game(int id, List<Reveal> reveals) {
    public static Game from(String line) {
      var parts = line.split(": ");
      int id = Integer.parseInt(parts[0].split(" ")[1]);
      var revealStrs = parts[1].split("; ");
      var reveals = Arrays.stream(revealStrs).map(Reveal::from).toList();
      return new Game(id, reveals);
    }

    public static List<Game> from(List<String> lines) {
      return lines.stream().map(Game::from).toList();
    }

    public boolean isValid(Map<String, Integer> limits) {
      return reveals.stream().allMatch(reveal -> reveal.isValid(limits));
    }

    public long getPower() {
      var max = new HashMap<>(Map.of("red", 0L, "green", 0L, "blue", 0L));
      reveals.forEach(
          reveal ->
              reveal.cubes.forEach(
                  cube ->
                      max.compute(
                          cube.first(), (k, v) -> (cube.second() > v) ? cube.second() : v)));
      return max.values().stream().reduce(1L, (l, r) -> l * r);
    }
  }

  public static int getValidGames(List<Game> games) {
    var limits = Map.of("red", 12, "green", 13, "blue", 14);

    return games.stream().filter(game -> game.isValid(limits)).mapToInt(Game::id).sum();
  }

  public static long getPower(List<Game> games) {
    return games.stream().mapToLong(Game::getPower).sum();
  }
}
