package com.wilb0t.aoc;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

class Day2 {

  public enum Color {
    RED,
    GREEN,
    BLUE;

    public static Color from(String clr) {
      return Color.valueOf(clr.toUpperCase(Locale.ENGLISH));
    }
  }

  public record Cube(Color color, int count) {}

  public record Reveal(List<Cube> cubes) {
    public static Reveal from(String line) {
      var parts = line.split(", ");
      var cubes =
          Arrays.stream(parts)
              .map(
                  clrstr -> {
                    var clrparts = clrstr.split(" ");
                    return new Cube(Color.from(clrparts[1]), Integer.parseInt(clrparts[0]));
                  })
              .toList();
      return new Reveal(cubes);
    }

    public boolean isValid(Map<Color, Integer> limits) {
      return cubes.stream().allMatch(cube -> limits.getOrDefault(cube.color(), 0) >= cube.count());
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

    public boolean isValid(Map<Color, Integer> limits) {
      return reveals.stream().allMatch(reveal -> reveal.isValid(limits));
    }

    public long getPower() {
      var colorMax = new HashMap<>(Map.of(Color.RED, 0L, Color.GREEN, 0L, Color.BLUE, 0L));
      reveals.forEach(
          reveal ->
              reveal.cubes.forEach(
                  cube ->
                      colorMax.compute(
                          cube.color(), (k, max) -> (cube.count() > max) ? cube.count() : max)));
      return colorMax.values().stream().reduce(1L, (l, r) -> l * r);
    }
  }

  public static int getValidGames(List<Game> games) {
    var limits = Map.of(Color.RED, 12, Color.GREEN, 13, Color.BLUE, 14);
    return games.stream().filter(game -> game.isValid(limits)).mapToInt(Game::id).sum();
  }

  public static long getPower(List<Game> games) {
    return games.stream().mapToLong(Game::getPower).sum();
  }
}
