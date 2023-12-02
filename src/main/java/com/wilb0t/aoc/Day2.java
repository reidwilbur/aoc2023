package com.wilb0t.aoc;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

class Day2 {

  public record Reveal(List<Util.Pair<String, Integer>> cubes) {
    public static Reveal from(String line) {
      var parts = line.split(", ");
      var cubes =
          Arrays.stream(parts)
              .map(
                  clrstr -> {
                    var clrparts = clrstr.split(" ");
                    return new Util.Pair<>(clrparts[1], Integer.valueOf(clrparts[0]));
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
      var id = Integer.valueOf(parts[0].split(" ")[1]);
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
  }

  public static int getValidGames(List<Game> games) {
    var limits = Map.of("red", 12, "green", 13, "blue", 14);

    return games.stream().filter(game -> game.isValid(limits)).mapToInt(Game::id).sum();
  }
}
