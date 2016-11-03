package streams;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Streams {

    private static Stream<String> filesLinesUnchecked(Path path) {
        try {
            return Files.lines(path);
        } catch (Exception x) {
            throw new RuntimeException(x);
        }
    }

    public static void main(String[] argv) {
        Pattern splitter = Pattern.compile("[^-A-Za-z']");
        List<String> words = Arrays.stream(argv)
            .map(name -> Paths.get(name))
            .flatMap(path -> filesLinesUnchecked(path))
            .flatMap(line -> splitter.splitAsStream(line))
            .map(word -> word.toLowerCase())
            .collect(Collectors.toList());
        Map<String, List<Integer>> indexes =
            IntStream.range(0, words.size()).boxed()
            .collect(Collectors.groupingBy(n -> words.get(n)));
        System.out.println("Something is running!");
        indexes.forEach((name, index) -> System.out.println(name + ": " + index));
    }
}
