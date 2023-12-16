package com.wilb0t.aoc;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Input {

  public static Input PUZZLE = new Input("");
  public static Input TEST = new Input("-test");
  private final String suffix;

  public Input(String suffix) {
    this.suffix = suffix;
  }

  Stream<String> getInput(Class<?> caller) throws URISyntaxException, IOException {
    var pathStr = "/" + caller.getName().replace('.', '/').replace("Test", "") + suffix + ".txt";
    var path = Path.of(Objects.requireNonNull(Input.class.getResource(pathStr)).toURI());
    return Files.readAllLines(path, StandardCharsets.UTF_8).stream();
  }

  public int[] loadInts() {
    try {
      var caller =
          StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE).getCallerClass();
      return getInput(caller).mapToInt(Integer::parseInt).toArray();
    } catch (IOException | URISyntaxException e) {
      throw new RuntimeException(e);
    }
  }

  public int[][] loadIntGrid() {
    try {
      var caller =
          StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE).getCallerClass();
      return getInput(caller).map(s -> s.chars().map(i -> i - '0').toArray()).toArray(int[][]::new);
    } catch (IOException | URISyntaxException e) {
      throw new RuntimeException(e);
    }
  }

  public char[][] loadCharGrid() {
    try {
      var caller =
          StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE).getCallerClass();
      return getInput(caller).map(String::toCharArray).toArray(char[][]::new);
    } catch (IOException | URISyntaxException e) {
      throw new RuntimeException(e);
    }
  }

  public static int[][] copy(int[][] input) {
    var copy = new int[input.length][];
    for (var r = 0; r < input.length; r++) {
      copy[r] = Arrays.copyOf(input[r], input[r].length);
    }
    return copy;
  }

  public int[] loadIntsFlat() {
    try {
      var caller =
          StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE).getCallerClass();
      return getInput(caller)
          .flatMap(s -> Arrays.stream(s.split(",")))
          .mapToInt(Integer::parseInt)
          .toArray();
    } catch (IOException | URISyntaxException e) {
      throw new RuntimeException(e);
    }
  }

  public String[] loadStrings() {
    try {
      var caller =
          StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE).getCallerClass();
      return getInput(caller).toArray(String[]::new);
    } catch (IOException | URISyntaxException e) {
      throw new RuntimeException(e);
    }
  }

  public <T> List<T> load(Function<String, T> mapper) {
    try {
      var caller =
          StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE).getCallerClass();
      return getInput(caller).map(mapper).collect(Collectors.toList());
    } catch (IOException | URISyntaxException e) {
      throw new RuntimeException(e);
    }
  }

  public <T> List<T> loadFlat(Function<String, Stream<T>> mapper) {
    try {
      var caller =
          StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE).getCallerClass();
      return getInput(caller).flatMap(mapper).collect(Collectors.toList());
    } catch (IOException | URISyntaxException e) {
      throw new RuntimeException(e);
    }
  }

  public <T> T parse(Function<List<String>, T> parser) {
    try {
      var caller =
          StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE).getCallerClass();
      var lines = getInput(caller).collect(Collectors.toList());
      return parser.apply(lines);
    } catch (IOException | URISyntaxException e) {
      throw new RuntimeException(e);
    }
  }

  public <T> T parseStream(Function<Stream<String>, T> parser) {
    try {
      var caller =
          StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE).getCallerClass();
      var lines = getInput(caller);
      return parser.apply(lines);
    } catch (IOException | URISyntaxException e) {
      throw new RuntimeException(e);
    }
  }
}
